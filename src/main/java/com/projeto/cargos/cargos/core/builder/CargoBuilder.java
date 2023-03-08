package com.projeto.cargos.cargos.core.builder;

import com.projeto.cargos.cargos.domain.entity.Cargo;
import com.projeto.cargos.cargos.domain.entity.Setor;
import com.projeto.cargos.cargos.domain.entity.Trabalhador;
import lombok.Builder;

import java.util.List;

@Builder
public class CargoBuilder {
    private Long id;
    private String nome;
    private Setor setor;
    private List<Trabalhador> trabalhadores;

    public Cargo toEntity(){
        return new Cargo(id, nome, setor, trabalhadores);
    }
}
