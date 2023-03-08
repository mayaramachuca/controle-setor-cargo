package com.projeto.cargos.cargos.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="cargo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @ManyToOne
    @JoinColumn(name = "id_setor",referencedColumnName = "id",nullable = false)
    private Setor setor;
    @OneToMany(mappedBy = "cargo" , cascade = CascadeType.ALL)
    private List<Trabalhador> trabalhadores;
}
