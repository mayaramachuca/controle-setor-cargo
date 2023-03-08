package com.projeto.cargos.cargos.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrabalhadorRequest {

    @CPF
    private String cpf;
    @NotBlank
    @Size(min=2, max=100)
    private String nome;
    @NotNull
    private Long idCargo;
}
