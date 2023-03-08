package com.projeto.cargos.cargos.domain.repository;

import com.projeto.cargos.cargos.domain.entity.Setor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SetorRepository extends JpaRepository<Setor, Long> {

    Optional<Setor> findByNome(String nome);
}
