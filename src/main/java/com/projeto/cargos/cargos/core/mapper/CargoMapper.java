package com.projeto.cargos.cargos.core.mapper;

import com.projeto.cargos.cargos.app.dto.request.CargoRequest;
import com.projeto.cargos.cargos.app.dto.response.CargoResponse;
import com.projeto.cargos.cargos.core.builder.CargoBuilder;
import com.projeto.cargos.cargos.domain.entity.Cargo;
import com.projeto.cargos.cargos.domain.entity.Trabalhador;
import com.projeto.cargos.cargos.domain.exception.notfound.SetorNaoEncontradoException;
import com.projeto.cargos.cargos.domain.repository.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CargoMapper {
    @Autowired
    private SetorRepository setorRepository;

    public Cargo toEntity (CargoRequest cargoRequest){
        var setor = setorRepository.findById(cargoRequest.getIdSetor())
                .orElseThrow(()-> new SetorNaoEncontradoException("Setor não cadastrado"));

//        return CargoBuilder.builder()
//                .nome(cargoRequest.getNome())
//                .setor(setor)
//                .trabalhadores(new ArrayList<Trabalhador>())
//                .build().toEntity();
        return Cargo.builder()
                .nome(cargoRequest.getNome())
                .setor(setor)
                .trabalhadores(new ArrayList<Trabalhador>())
                .build();
    }

    public CargoResponse toDto(Cargo cargo){
        TrabalhadorMapper trabalhadorMapper = new TrabalhadorMapper();
        return CargoResponse.builder()
                .id(cargo.getId())
                .nome(cargo.getNome())
                .idSetor(cargo.getSetor().getId())
                .trabalhadores(trabalhadorMapper.toListDto(cargo.getTrabalhadores()))
                .build();
    }
    public static CargoResponse internalToDto(Cargo cargo){
        TrabalhadorMapper trabalhadorMapper = new TrabalhadorMapper();
        return CargoResponse.builder()
                .nome(cargo.getNome())
                .idSetor(cargo.getSetor().getId())
                .id(cargo.getId())
                .trabalhadores(trabalhadorMapper.toListDto(cargo.getTrabalhadores()))
                .build();
    }

    public List<CargoResponse> toListDto(List<Cargo> cargos){
        return cargos.stream()
                .map(CargoMapper::internalToDto)
                .collect(Collectors.toList());
    }
}
