package com.projeto.cargos.cargos.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CargoRequest {

    @NotBlank
    @Size(min=2, max=100)
    private String nome;
    @NotNull
    private Long idSetor;

}
