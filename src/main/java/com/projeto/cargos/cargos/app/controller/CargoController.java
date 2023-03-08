package com.projeto.cargos.cargos.app.controller;

import com.projeto.cargos.cargos.app.dto.request.CargoRequest;
import com.projeto.cargos.cargos.app.dto.response.CargoResponse;
import com.projeto.cargos.cargos.core.mapper.CargoMapper;
import com.projeto.cargos.cargos.domain.entity.Cargo;
import com.projeto.cargos.cargos.domain.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("cargo")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    @Autowired
    private CargoMapper cargoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CargoResponse createCargo(@Valid @RequestBody CargoRequest cargoRequest){
        Cargo cargo = cargoService.save(cargoMapper.toEntity(cargoRequest));
        return cargoMapper.toDto(cargo);
    }
    @GetMapping(value = "/{id}")
    public CargoResponse getCargo(@PathVariable("id")Long id) {
        Cargo cargo = cargoService.getCargoId(id);
        var cargoResponse = cargoMapper.toDto(cargo);
        return cargoResponse;
    }
    @GetMapping(value = "/cargos")
    public List<CargoResponse> listarCargos() {

        return cargoMapper.toListDto(cargoService.getAllCargo());
    }


}
