package com.projeto.cargos.cargos.app.controller;

import com.projeto.cargos.cargos.app.dto.request.TrabalhadorRequest;
import com.projeto.cargos.cargos.app.dto.response.TrabalhadorResponse;
import com.projeto.cargos.cargos.core.mapper.TrabalhadorMapper;
import com.projeto.cargos.cargos.domain.entity.Trabalhador;
import com.projeto.cargos.cargos.domain.service.TrabalhadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("trabalhador")
public class TrabalhadorController {

    @Autowired
    private TrabalhadorService trabalhadorService;

    @Autowired
    private TrabalhadorMapper trabalhadorMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrabalhadorResponse createTrabalhador(@Valid @RequestBody TrabalhadorRequest trabalhadorRequest){
        Trabalhador trabalhador = trabalhadorService.save(trabalhadorMapper.toEntity(trabalhadorRequest));
        return trabalhadorMapper.toDto(trabalhador);
    }
    @GetMapping(value = "/{id}")
    public TrabalhadorResponse getTrabalhador(@PathVariable("id")Long id) {
        Trabalhador trabalhador = trabalhadorService.getTrabalhadorId(id);
        var trabalhadorResponse = trabalhadorMapper.toDto(trabalhador);
        return trabalhadorResponse;
    }
    @GetMapping(value = "/trabalhadores")
    public List<TrabalhadorResponse> listarTrabalhadores() {

        return trabalhadorMapper.toListDto(trabalhadorService.getAllTrabalhadores());
    }
}
