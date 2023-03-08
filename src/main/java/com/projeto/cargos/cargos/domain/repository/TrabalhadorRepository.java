package com.projeto.cargos.cargos.domain.repository;

import com.projeto.cargos.cargos.domain.entity.Trabalhador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrabalhadorRepository  extends JpaRepository<Trabalhador, Long> {

    Optional<Trabalhador> findByCpf(String cpf);

}
