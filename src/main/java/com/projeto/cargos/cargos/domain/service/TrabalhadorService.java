package com.projeto.cargos.cargos.domain.service;

import com.projeto.cargos.cargos.domain.entity.Trabalhador;

import java.util.List;
import java.util.Optional;

public interface TrabalhadorService {
    Trabalhador save (Trabalhador trabalhador);
    Trabalhador getTrabalhadorId (Long id);
    List<Trabalhador> getAllTrabalhadores();
}
