package com.projeto.cargos.cargos.core.builder;

import com.projeto.cargos.cargos.domain.entity.Cargo;
import com.projeto.cargos.cargos.domain.entity.Trabalhador;
import lombok.Builder;

@Builder
public class TrabalhadorBuilder {
    private Long id;
    private String cpf;
    private String nome;
    private Cargo cargo;

    public Trabalhador toEntity(){
        return new Trabalhador(id, cpf, nome, cargo);
    }
}
