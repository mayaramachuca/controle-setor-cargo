package com.projeto.cargos.cargos.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrabalhadorResponse {
    private Long id;
    private String cpf;
    private String nome;
    private Long idCargo;
}
