package com.projeto.cargos.cargos.core.mapper;

import com.projeto.cargos.cargos.app.dto.request.SetorRequest;
import com.projeto.cargos.cargos.app.dto.response.SetorResponse;
import com.projeto.cargos.cargos.core.builder.SetorBuilder;
import com.projeto.cargos.cargos.domain.entity.Cargo;
import com.projeto.cargos.cargos.domain.entity.Setor;
import com.projeto.cargos.cargos.domain.repository.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SetorMapper {
    @Autowired
    private SetorRepository setorRepository;

    public Setor toEntity (SetorRequest setorRequest){

        return SetorBuilder.builder()
                .nome(setorRequest.getNome())
                .cargos(new ArrayList<Cargo>())
                .build().toEntity();
    }

    public SetorResponse toDto(Setor setor){
        CargoMapper cargoMapper = new CargoMapper();
        return SetorResponse.builder()
                .id(setor.getId())
                .nome(setor.getNome())
                .cargos(cargoMapper.toListDto(setor.getCargos()))
                .build();
    }

    public static SetorResponse internalToDto(Setor setor){
        CargoMapper cargoMapper = new CargoMapper();
        return SetorResponse.builder()
                .id(setor.getId())
                .nome(setor.getNome())
                .cargos(cargoMapper.toListDto(setor.getCargos()))
                .build();
    }
    public List<SetorResponse> toListDto(List<Setor>setores){
        return setores.stream()
                .map(SetorMapper::internalToDto)
                .collect(Collectors.toList());
    }
}
