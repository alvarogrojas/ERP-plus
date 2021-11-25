package com.ndl.erp.repository;


import com.ndl.erp.domain.TareaEjecutada;

import java.util.List;

public interface TareaEjecutadaRepository {

    public TareaEjecutada getById(Integer id);

    TareaEjecutada addATareaEjecutada(TareaEjecutada pe);
    TareaEjecutada updateTareaEjecutada(TareaEjecutada pe);

    TareaEjecutada getTareaEjecutada(Integer id);
    public List<TareaEjecutada> getTareaEjecutadaList(Integer procesoEmisionId);


}
