package com.projeto.cargos.cargos.domain.service;

import com.projeto.cargos.cargos.domain.entity.Setor;

import java.util.List;
import java.util.Optional;

public interface SetorService {

    Setor save (Setor setor);
    Setor getSetorId (Long id);
    List<Setor> getAllSetores();
    void deleteSetor(Long id);


}
