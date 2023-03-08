package com.projeto.cargos.cargos.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="trabalhador")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trabalhador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "CPF" , nullable = false , unique = true)
    private String cpf;
    private String nome;
    @ManyToOne
    @JoinColumn(name = "id_cargo",referencedColumnName = "id",nullable = false)
    private Cargo cargo;
}
