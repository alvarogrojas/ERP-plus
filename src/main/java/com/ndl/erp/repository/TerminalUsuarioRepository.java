package com.ndl.erp.repository;

import com.ndl.erp.domain.TerminalUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TerminalUsuarioRepository extends JpaRepository<TerminalUsuario, Integer> {

    @Query("select tu from TerminalUsuario tu  where tu.terminal.estado = 'Cerrada'  and tu.usuario.id = ?1 and tu.estado = 'Activo' ")
    TerminalUsuario findTerminalUsuarioByUsuario(Long usuarioId);

    @Query("select tu from TerminalUsuario tu  where tu.terminal.id = ?1 and tu.estado = 'Activo' ")
    List<TerminalUsuario> findTerminalUsuarioByTerminal(Integer terminalId);

    @Query("select tu from TerminalUsuario tu  where tu.terminal.id = ?1  ")
    List<TerminalUsuario> findTerminalUsuarioByTerminalAll(Integer terminalId);

    @Query("select tu from TerminalUsuario tu  where tu.usuario.id = ?1  and tu.terminal.id = ?2")
    TerminalUsuario findTerminalUsuarioByUsuarioOnly(Long usuarioId, Integer terminalId);
}
