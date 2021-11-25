package com.ndl.erp.repository;

import com.ndl.erp.domain.SessionPos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public interface SessionPosRepository extends JpaRepository<SessionPos, Integer> {

    @Query(value="select count(t) from SessionPos t where t.estado = 'Abierta' and t.terminalUsuario.usuario.id = ?1 ")
    Integer  countSesionAbierta(Long userId);

    @Query(value="select t from SessionPos t where t.estado = 'Abierta' and t.terminalUsuario.usuario.id = ?1")
    SessionPos  getSesionAbierta(Long userId);


    @Query(value="select t from SessionPos t where t.id = ?1")
    SessionPos  findSessionById(Integer sessionId);


    @Query(value= "select sp from SessionPos sp where (?1 = null or  (sp.estado='Abierta' and sp.fechaApertura >= ?1) or (sp.estado='Cerrada' and sp.fechaCierre >= ?1 and sp.fechaApertura >= ?1)) " +
            "and (?2 = null or (sp.estado='Cerrada' and sp.fechaApertura <= ?2 and sp.fechaCierre <= ?2) or (sp.estado='Abierta' and sp.fechaApertura <= ?2))" +
                  " and (?3 = null or ?3 = '' or sp.estado = ?3)   " +
                  " and (?4 = 0 or ?4 = null or sp.terminalUsuario.usuario.id = ?4 ) "   +
                  " and (?5 = 0 or ?5 = null or sp.terminalUsuario.terminal.bodega.id = ?5 )"
    )
    Page<SessionPos> getPageableSessionsByDateAndEstadoAndUser(Date startFecha, Date endFecha, String estado,
                                                               Integer cajeroId, Integer bodegaId,
                                                               PageRequest pageable);

    @Query(value = "select sum(c.montoNeto) from SessionPos c where  " +
            " (?1 = null or  (c.estado='Abierta' and c.fechaApertura >= ?1) or (c.estado='Cerrada' and c.fechaCierre >= ?1)) " +
            " and (?2 = null or (c.estado='Cerrada' and c.fechaCierre <= ?2) or (c.estado='Cerrada' and c.fechaCierre <= ?2)) " +
            "and (?3='' or ?3=null or c.estado=?3)  " +
            "and  (?4=0 or ?4=null or c.userIngresadoPor.id=?4) " +
            "  and (?5 = 0 or ?5 = null or c.terminalUsuario.terminal.bodega.id = ?5 )"
    )
    Double getSumTotalByDatesAndEstado(
            Date startFecha, Date endFecha, String estado,
            Integer cajeroId, Integer bodegaId);

    @Query(value= "select count(sp.id) from SessionPos sp where " +
            " (?1 = null or  (sp.estado='Abierta' and sp.fechaApertura >= ?1) or (sp.estado='Cerrada' and sp.fechaCierre >= ?1)) " +
            " and (?2 = null or (sp.estado='Cerrada' and sp.fechaCierre <= ?2) or (sp.estado='Cerrada' and sp.fechaCierre <= ?2)) " +
            " and (?3 = null or ?3 = '' or sp.estado = ?3)   " +
            " and (?4 = null or sp.terminalUsuario.usuario.id = ?4 )" +
            "  and (?5 = 0 or ?5 = null or sp.terminalUsuario.terminal.bodega.id = ?5 )"
    )
    Integer countPageableSessionsByDateAndEstadoAndUser(Date FechaIni, Date FechaFin, String estado,
                                                        Integer usuarioId,Integer bodegaId);

    SessionPos getSessionPosById(Integer id);
}




