package com.projeto.cargos.cargos.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoResponse {

    private Long id;
    private String nome;
    private Long idSetor;
    private List<TrabalhadorResponse> trabalhadores;

}
