package com.backend.montreal.service;

import java.util.List;
import com.backend.montreal.entity.Tema;

public interface TemaService {
    Tema saveTema(Tema tema);
    List<Tema> getAllTemas();
    Tema getTemaById(Long id);
    Tema updateTema(Long id, Tema updatedTema);
    void deleteTema(Long id);
}
