package com.projeto.cargos.cargos.core.mapper;

import com.projeto.cargos.cargos.app.dto.request.TrabalhadorRequest;
import com.projeto.cargos.cargos.app.dto.response.TrabalhadorResponse;
import com.projeto.cargos.cargos.core.builder.TrabalhadorBuilder;
import com.projeto.cargos.cargos.domain.entity.Trabalhador;
import com.projeto.cargos.cargos.domain.exception.notfound.CargoNaoEncontradoException;
import com.projeto.cargos.cargos.domain.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrabalhadorMapper {
    @Autowired
    private CargoRepository cargoRepository;

    public Trabalhador toEntity(TrabalhadorRequest trabalhadorRequest){
        var cargo = cargoRepository.findById(trabalhadorRequest.getIdCargo())
                .orElseThrow(()-> new CargoNaoEncontradoException("Cargo não encontrado"));

        return TrabalhadorBuilder.builder()
                .cpf(trabalhadorRequest.getCpf())
                .nome(trabalhadorRequest.getNome())
                .cargo(cargo)
                .build().toEntity();
    }

    private static TrabalhadorResponse internalToDto(Trabalhador trabalhador) {
        return TrabalhadorResponse.builder()
                .id(trabalhador.getId())
                .cpf(trabalhador.getCpf())
                .nome(trabalhador.getNome())
                .idCargo(trabalhador.getCargo().getId())
                .build();
    }

    public TrabalhadorResponse toDto(Trabalhador trabalhador){
        return TrabalhadorResponse.builder()
                .id(trabalhador.getId())
                .cpf(trabalhador.getCpf())
                .nome(trabalhador.getNome())
                .idCargo(trabalhador.getCargo().getId())
                .build();
    }

    public List<TrabalhadorResponse> toListDto(List<Trabalhador> trabalhadores) {
        return trabalhadores.stream()
                .map(TrabalhadorMapper::internalToDto)
                .collect(Collectors.toList());
    }
}