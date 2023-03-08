package com.projeto.cargos.cargos.core.serviceImpl;

import com.projeto.cargos.cargos.domain.entity.Trabalhador;
import com.projeto.cargos.cargos.domain.exception.notacceptable.CpfJaRegistradoException;
import com.projeto.cargos.cargos.domain.exception.notfound.TrabalhadorNaoEncontradoException;
import com.projeto.cargos.cargos.domain.repository.TrabalhadorRepository;
import com.projeto.cargos.cargos.domain.service.TrabalhadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("trabalhadorService")
public class TrabalhadorServiceImpl implements TrabalhadorService {

   @Autowired
   private TrabalhadorRepository trabalhadorRepository;

    @Override
    public Trabalhador save(Trabalhador trabalhador) {
        if(trabalhadorRepository.findByCpf(trabalhador.getCpf()).isPresent()){
            throw  new CpfJaRegistradoException("CPF já cadastrado!");
        }
        return trabalhadorRepository.save(trabalhador);
    }

    @Override
    public Trabalhador getTrabalhadorId(Long id) {
        Trabalhador trabalhador = trabalhadorRepository.findById(id).orElseThrow(()-> new TrabalhadorNaoEncontradoException("Trabalhador não cadastrado"));
        return trabalhador;
    }

    @Override
    public List<Trabalhador> getAllTrabalhadores() {

        return trabalhadorRepository.findAll();
    }
}
