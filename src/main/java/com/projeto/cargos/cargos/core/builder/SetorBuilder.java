package com.projeto.cargos.cargos.core.builder;

import com.projeto.cargos.cargos.domain.entity.Cargo;
import com.projeto.cargos.cargos.domain.entity.Setor;
import lombok.Builder;

import java.util.List;

@Builder
public class SetorBuilder {
    private Long id;
    private String nome;
    private List<Cargo> cargos;
    public Setor toEntity(){
        return new Setor(id, nome, cargos);
    }
}
