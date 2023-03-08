package com.projeto.cargos.cargos.domain.service;

import com.projeto.cargos.cargos.domain.entity.Cargo;

import java.util.List;
import java.util.Optional;

public interface CargoService {

    Cargo save (Cargo cargo);
    Cargo getCargoId(Long id);
    List<Cargo> getAllCargo();
}
