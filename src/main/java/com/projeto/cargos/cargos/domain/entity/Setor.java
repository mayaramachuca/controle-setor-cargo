package com.projeto.cargos.cargos.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="setor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Setor  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @OneToMany(mappedBy = "setor" , cascade = CascadeType.ALL)
    private List<Cargo> cargos;

}
