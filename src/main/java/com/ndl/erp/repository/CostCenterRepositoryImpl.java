package com.ndl.erp.repository;


import com.ndl.erp.dto.CostCenterNoPODTO;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

//import org.apache.commons.lang.StringUtils;
//import org.hibernate.Criteria;
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.criterion.Projections;
//import org.hibernate.criterion.Restrictions;

//import org.springframework.data.jpa.repository.JpaRepository;

@Component
public class CostCenterRepositoryImpl {

//    @PersistenceContext
//    private EntityManager entityManager;
//
//
//    public List<CostCenterNoPODTO> getProyectoWithNoPO() {
//        Session session = entityManager.unwrap(Session.class);
//        Query query = session.createQuery(getQueryCustom(reciboId, facturaId, clienteId, fechaInicio, fechaFinal, enviadaHacienda));
//        if (pageNumber!=null && pageSize!=null) {
//            query.setFirstResult(pageSize * pageNumber);
//            query.setMaxResults(pageSize);
//        }
//
//        initQueryForTicaFilter(reciboId, facturaId, clienteId, fechaInicio, fechaFinal, enviadaHacienda, query);
//        return query.list();
//        return null;
//    }
//
//    private String getQueryCustom(Integer reciboId, Integer facturaId, Integer clienteId, Date fechaDesde, Date fechaHasta, Integer enviadaHacienda) {
//        String result = "select new com.rfs.dtos.FacturaReciboDTO(f.id, r.id,f.fechaFacturacion,u.nombre, r.cliente.nombre, f.enviadaHacienda, f.estado, f.cobroPorCuentaDe, f) from Factura f, Recibo r, Usuario u where f.reciboId=r.id and r.encargado.id=u.id ";
//        return result;
//    }

}
