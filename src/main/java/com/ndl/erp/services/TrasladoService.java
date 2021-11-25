package com.ndl.erp.services;


import com.ndl.erp.constants.TrasladoConstants;
import com.ndl.erp.domain.*;
import com.ndl.erp.dto.PosDTO;
import com.ndl.erp.dto.TrasladoDTO;
import com.ndl.erp.dto.TrasladosDTO;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.exceptions.NotFoundException;
import com.ndl.erp.repository.*;
import com.ndl.erp.services.bodega.BodegaManagerService;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ndl.erp.constants.CostCenterConstants.PARAMETER_DEFAULT_COST_CENTER_SELLING;
import static com.ndl.erp.constants.CostCenterConstants.PARAMETER_DEFAULT_COST_CENTER_TRASLADO;
import static com.ndl.erp.constants.InvoiceConstants.INVOICE_DEFAULT_CLIENT;
import static com.ndl.erp.constants.TrasladoConstants.*;


@Component
public class TrasladoService {

    @Autowired
    TrasladoRepository trasladoRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    GeneralParameterService generalParameterService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BodegaRepository bodegaRepository;

    @Autowired
    BodegaManagerService bodegaManagerService;

    @Autowired
    TipoDocumentoRepository tipoDocumentoRepository;

    @Autowired
    private TrasladoJustificacionRepository trasladoJustificacionRepository;

    @Autowired
    private GeneralParameterRepository generalParameterRepository;

    @Autowired
    private CentroCostosRepository centroCostosRepository;


    @Transactional(rollbackFor = {Exception.class})
    public synchronized Traslado save(Traslado traslado) throws Exception {

        if (traslado.getId()==null) {

            this.setAuditoriaCreacionTraslado(traslado);
            this.setAuditoriaModificacionTraslado(traslado);
            this.generalParameterService.generateNextTrasladoNumber(traslado);
            existeConsecutivoTraslado(traslado);

        } else {
            if (permitirModificarTrasladoSegunEstadoAnterior(traslado)) {
                this.setAuditoriaModificacionTraslado(traslado);
            } else{
                throw new RuntimeException("No se permite modificar Traslados con estado anterior Trasladado");
            }

        }

        return this.trasladoRepository.save(traslado);
    }

    private boolean permitirModificarTrasladoSegunEstadoAnterior(Traslado traslado) throws Exception {

        boolean permitirModificar = false;
        Traslado oldTraslado;

       oldTraslado = this.trasladoRepository.findTrasladoById(traslado.getId());

        if (oldTraslado == null || !oldTraslado.getEstado().equals(TRASLADO_STATUS_TRASLADADO)) {
            permitirModificar = true;
        } else if (oldTraslado.getEstado().equals(TRASLADO_STATUS_TRASLADADO)) {
            permitirModificar = false;
        }
        return permitirModificar;
    }

    public void setAuditoriaCreacionTraslado(Traslado traslado) throws Exception{

        User u = this.userService.getCurrentLoggedUser();
        if (u==null) {
            throw new NotFoundException("El usuario no está autenticado");
        }
        traslado.setFechaTraslado(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        traslado.setFechaUltimaModificacion(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        traslado.setIngresadoPor(u);

    }

    public void setAuditoriaModificacionTraslado(Traslado traslado) throws Exception{

        traslado.setFechaUltimaModificacion(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));

    }


    void existeConsecutivoTraslado(Traslado tr) throws Exception{
        Integer consecutivos = this.trasladoRepository.validarConsecutivoTraslado(tr.getConsecutivo());
        if (consecutivos > 0) {
            throw new RuntimeException("El consecutivo de la requisición ya está en uso en otro documento!");
        }
    }

    public TrasladoDTO getTraslado(Integer id) throws Exception{
        TrasladoDTO trasladoDTO= this.getTraslado();
        Traslado tr  = trasladoRepository.findTrasladoById(id);
        if (tr == null) {
            return trasladoDTO;
        }else {
            trasladoDTO.setCurrent(tr);
            trasladoDTO.setEstados(getTrasladoEstados());
            trasladoDTO.setUsers(this.userRepository.findUsersActive());
            trasladoDTO.setDefaulCostCenterTraslado(getDefaultCostCenterTraslado());
            trasladoDTO.setBodegas(this.bodegaRepository.findAll());
        }
        return trasladoDTO;
    }

    public TrasladoDTO getTraslado() throws Exception {

        TrasladoDTO tr = new TrasladoDTO();
        tr.setEstados(getTrasladoEstados());
        tr.setUsers(this.userRepository.findUsersActive());
        tr.setBodegas(this.bodegaRepository.findAll());
        tr.setDefaulCostCenterTraslado(getDefaultCostCenterTraslado());
        tr.setJustificaciones(this.trasladoJustificacionRepository.getByEstado("Activo"));
        return tr;
    }

    public CostCenter getDefaultCostCenterTraslado(){

        GeneralParameter parameterBodega = generalParameterService.getByCode(PARAMETER_DEFAULT_COST_CENTER_TRASLADO);
        if (parameterBodega==null) {
            throw new NotFoundException("No hay un Centro de Costos creador para realizar los traslados. " +
                    "El administrador  del sistema debe configurar el ERP, agregando un centro de costos, " +
                    "de lo contrario el sistema no podrá realizar ventas");
        }
        CostCenter defaultCc = this.centroCostosRepository.getById(parameterBodega.getIntVal());
        return defaultCc;
    }

    public List<String> getTrasladoEstados(){

        List<String> estados = new ArrayList<>();

        estados.add(TRASLADO_STATUS_EDICION);
        estados.add(TRASLADO_STATUS_TRASLADADO);
        estados.add(TRASLADO_STATUS_CANCELADO);

        return estados;
    }

    public TrasladosDTO getTraslados(String filter, String estado, Integer pageNumber, Integer pageSize, String sortDirection, String sortField) {

        TrasladosDTO tr = new TrasladosDTO();

        tr.setPage((this.trasladoRepository.getFilterPageableTrasladoByConsecutivoAndEstado(filter, estado, createPageable(pageNumber, pageSize, sortDirection, sortField))));

        tr.setTotal(this.trasladoRepository.countFilterPageableTrasladoByConsecutivoAndEstado(filter,estado));
        if (tr.getTotal()>0) {
            tr.setPagesTotal(tr.getTotal() /pageSize);
        } else {
            tr.setPagesTotal(0);
        }

        //Inicializar las listas del DTO
        tr.setBodegas(this.bodegaRepository.findAll());
        tr.setEstados(this.getTrasladoEstados());
        tr.setUsers(this.userRepository.findUsersActive());

        return tr;

    }


    //Felix Saborio 13/7/2021 : Traslados entre bodegas
    @Transactional(rollbackFor = {Exception.class})
    public Traslado  procesarTrasladoBodega(Integer id) throws Exception {
        return bodegaManagerService.trasladoBodega(id);
    }

    @Transactional(rollbackFor = {Exception.class})
    public Traslado  cancelarTraslado(Integer id) throws Exception {
        Traslado t = this.trasladoRepository.findTrasladoById(id);
        if (t==null) {
            throw new NotFoundException("No se encontro el traslado " + id);
        }
        t.setEstado(TRASLADO_STATUS_CANCELADO);
        this.save(t);
        return t;
    }


    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }







}
