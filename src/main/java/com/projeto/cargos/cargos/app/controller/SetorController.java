package com.projeto.cargos.cargos.app.controller;

import com.projeto.cargos.cargos.app.dto.request.SetorRequest;
import com.projeto.cargos.cargos.app.dto.response.SetorResponse;
import com.projeto.cargos.cargos.core.mapper.SetorMapper;
import com.projeto.cargos.cargos.domain.entity.Setor;
import com.projeto.cargos.cargos.domain.service.SetorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("setor")
public class SetorController {

    @Autowired
    private SetorService setorService;

    @Autowired
    private SetorMapper setorMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SetorResponse createSetor(@Valid @RequestBody SetorRequest setorRequest){
        Setor setor = setorService.save(setorMapper.toEntity(setorRequest));
        return setorMapper.toDto(setor);
    }

    @GetMapping(value = "/{id}")
    public SetorResponse getSetor(@PathVariable("id")Long id) {
        Setor setor = setorService.getSetorId(id);
        var setorResponse = setorMapper.toDto(setor);
        return setorResponse;
    }
    @GetMapping(value = "/setores")
    public List<SetorResponse> listarSetores() {

        return setorMapper.toListDto(setorService.getAllSetores());
    }

    @DeleteMapping(value = "/{id}")
    public void deletarSetor(@PathVariable("id")Long id){
        Setor setor = setorService.getSetorId(id);
        setorService.deleteSetor(setor.getId());
    }

}
