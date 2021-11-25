package com.ndl.erp.services;

import com.ndl.erp.domain.RequisicionBodega;
import com.ndl.erp.domain.User;
import com.ndl.erp.dto.RequisicionBodegaDTO;
import com.ndl.erp.dto.RequisicionBodegaListDTO;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.repository.BodegaRepository;
import com.ndl.erp.repository.InventarioBodegaRepository;
import com.ndl.erp.repository.RequisicionBodegaRepository;
import com.ndl.erp.repository.UserRepository;
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

import static com.ndl.erp.constants.RequisicionBodegaConstants.*;


@Component
public class RequisicionBodegaService {

    @Autowired
    RequisicionBodegaRepository requisicionBodegaRepository;

    @Autowired
    InventarioBodegaRepository inventarioBodegaRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    BodegaRepository BodegaRepository;

    @Autowired
    GeneralParameterService generalParameterService;

    @Autowired
    BodegaManagerService bodegaManagerService;

    public RequisicionBodega getRequisicionById(RequisicionBodega requisicionBodega) {

        RequisicionBodega rb = this.requisicionBodegaRepository.findRequisicionBodegaById(requisicionBodega.getId());

        return rb;
    }




        private void congelarInventarioBodegaRequisicion(RequisicionBodega rb) throws Exception {


        RequisicionBodega oldRequisicionBodega;

        oldRequisicionBodega = this.requisicionBodegaRepository.findRequisicionBodegaById(rb.getId());

        if (oldRequisicionBodega.getEstado().equals(REQUISICION_BODEGA_ESTADO_EDICION) && rb.getEstado().equals(REQUISICION_BODEGA_ESTADO_APROBADO)) {

            this.bodegaManagerService.congelarIventarioRequisicionBodega(rb);

        }

    }


    public RequisicionBodega rechazarBodegaRequisicion(Integer id) throws Exception {
        RequisicionBodega rb = this.requisicionBodegaRepository.getOne(id);

        if (rb == null){
            throw new GeneralInventoryException("La requisicion no fué encontrada!");
        }

        if (rb.isSalidaBodega()){
           throw new GeneralInventoryException("No se permite rechazar una requisición que haya salido de bodega!");
        }

        if (!rb.getEstado().equals(REQUISICION_BODEGA_ESTADO_APROBADO)){
            throw new GeneralInventoryException("La requisicion  debe estar aprobada para poder ser rechazada!");
        }

        return this.bodegaManagerService.descongelarInventarioRequisicionBodega(rb);


    }



    public boolean permitirModificarRequisicionSegunEstadoAnterior(RequisicionBodega rb) throws Exception {

    boolean permitirModificar = false;
    RequisicionBodega oldRequisicionBodega;

    oldRequisicionBodega = this.requisicionBodegaRepository.findRequisicionBodegaById(rb.getId());

    if (oldRequisicionBodega == null || !oldRequisicionBodega.getEstado().equals(REQUISICION_BODEGA_ESTADO_APROBADO)) {
        permitirModificar = true;
    } else if (oldRequisicionBodega.getEstado().equals(REQUISICION_BODEGA_ESTADO_APROBADO)) {
        permitirModificar = false;
    }
    return permitirModificar;
    }



    void existeConsecutivoRequisicion(RequisicionBodega rb) throws Exception{
      Integer consecutivos = this.requisicionBodegaRepository.validarConsecutivoRequisicion(rb.getConsecutivo());
      if (consecutivos > 0) {
          //throw new RuntimeException("El consecutivo de la requisición ya está en uso en otro documento!");
          throw new GeneralInventoryException("El consecutivo de la requisición ya está en uso en otro documento!");
      }
    }

    void validarModificacionConsecutivoRequisicion(RequisicionBodega rb)  throws Exception{

        RequisicionBodega oldRequisicionBodega = this.requisicionBodegaRepository.findRequisicionBodegaById(rb.getId());
        if (!rb.getConsecutivo().equals(oldRequisicionBodega.getConsecutivo())){
            existeConsecutivoRequisicion(rb);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public synchronized RequisicionBodega save(RequisicionBodega rb) throws Exception {

        if (rb.getId()==null) {

            this.setAuditoriaModificacionRequisicionBodega(rb);
            this.setAuditoriaCreacionRequisicionBodega(rb);
            this.generalParameterService.generateNextRequisicionNumber(rb);
            existeConsecutivoRequisicion(rb);
            rb.setSalidaBodega(false);

        } else {
            validarModificacionConsecutivoRequisicion(rb);
            if (permitirModificarRequisicionSegunEstadoAnterior(rb)) {
               this.congelarInventarioBodegaRequisicion(rb);
               this.setAuditoriaModificacionRequisicionBodega(rb);
           } else{
               //throw new RuntimeException("No se permite modificar requisiciones con estado anterior aprobado");
                throw new GeneralInventoryException("No se permite modificar requisiciones con estado anterior aprobado");
           }

        }

        return this.requisicionBodegaRepository.save(rb);
    }

    public void setAuditoriaCreacionRequisicionBodega(RequisicionBodega requisicionBodega) throws Exception{

        User u = this.userService.getCurrentLoggedUser();
        if (u==null) {
            //throw new NotFoundException("User is not logged");
            throw new GeneralInventoryException("El usuario no está logueado");
        }
        requisicionBodega.setFechaUltimaModificacion(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        requisicionBodega.setUserIngresadoPor(u);

    }

    public void setAuditoriaModificacionRequisicionBodega(RequisicionBodega requisicionBodega) throws Exception{

        requisicionBodega.setFechaUltimaModificacion(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));

    }

    public RequisicionBodegaListDTO getRequisicionBodegaList(String filter, String consecutivo, Integer bodegaId, Date startFecha, Date endFecha,
                                                            String estado, Integer pageNumber, Integer pageSize, String sortDirection, String sortField) {

        RequisicionBodegaListDTO c = new RequisicionBodegaListDTO();

        c.setPage((this.requisicionBodegaRepository.getFilterPageableRequisicionBodegaByConsecutivo(filter, consecutivo, bodegaId,
                startFecha, endFecha, estado, createPageable(pageNumber, pageSize, sortDirection, sortField))));

        c.setTotal(this.requisicionBodegaRepository.countFilterPageableRequisicionBodegaByConsecutivo(filter,consecutivo, bodegaId, startFecha, endFecha, estado));
        if (c.getTotal()>0) {
            c.setPagesTotal(c.getTotal() /pageSize);
        } else {
            c.setPagesTotal(0);
        }

        //Inicializar las listas del DTO
        c.setBodegaList(this.BodegaRepository.findAll());
        c.setEstadosList(this.getRequisicionBodegaEstados());
        c.setUserList(this.userRepository.findUsersActive());

        return c;

    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }


    public RequisicionBodegaDTO getRequisicionBodega(Integer id) throws Exception{
        RequisicionBodegaDTO requisicionBodega = this.getRequisicionBodega();
        RequisicionBodega rb  = requisicionBodegaRepository.findRequisicionBodegaById(id);
        if (rb == null) {
            return requisicionBodega;
        }else {
            requisicionBodega.setCurrent(rb);
            requisicionBodega.setEstadosList(getRequisicionBodegaEstados());
            requisicionBodega.setUserList(this.userRepository.findUsersActive());
            requisicionBodega.setBodegaList(this.BodegaRepository.findAll());
        }
        return requisicionBodega;
    }

    public RequisicionBodegaDTO getRequisicionBodega() throws Exception {

        RequisicionBodegaDTO rb = new RequisicionBodegaDTO();
        rb.setEstadosList(getRequisicionBodegaEstados());
        rb.setUserList(this.userRepository.findUsersActive());
        rb.setBodegaList(this.BodegaRepository.findAll());
        rb.setCostCenters(new ArrayList<>());
        rb.getCurrent().setBodega(rb.getBodegaList().get(0));
        return rb;
    }

    public List<String> getRequisicionBodegaEstados(){

        List<String> estadoList = new ArrayList<>();

        estadoList.add(REQUISICION_BODEGA_ESTADO_EDICION);
        estadoList.add(REQUISICION_BODEGA_ESTADO_PENDIENTE);
        estadoList.add(REQUISICION_BODEGA_ESTADO_APROBADO);
        estadoList.add(REQUISICION_BODEGA_ESTADO_DESPACHADO);
        estadoList.add(REQUISICION_BODEGA_ESTADO_RECHAZADO);

        return estadoList;
    }

    public RequisicionBodegaListDTO getRequisicion(String filter) {
        RequisicionBodegaListDTO  result = new RequisicionBodegaListDTO();
        result.setList(this.requisicionBodegaRepository.getFilter(filter));
        return result;
    }

    @Transactional(rollbackFor = {Exception.class})
    public RequisicionBodega changeRequisicionStatus(RequisicionBodega rb) throws Exception {
        //RequisicionBodega rb = this.requisicionBodegaRepository.findRequisicionBodegaById(id);

        //rb.setEstado(status);
        return this.save(rb);
    }
}
