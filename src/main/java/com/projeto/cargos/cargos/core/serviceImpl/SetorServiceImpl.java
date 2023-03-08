package com.projeto.cargos.cargos.core.serviceImpl;

import com.projeto.cargos.cargos.domain.entity.Setor;
import com.projeto.cargos.cargos.domain.exception.notacceptable.SetorJaRegistradoException;
import com.projeto.cargos.cargos.domain.exception.notfound.SetorNaoEncontradoException;
import com.projeto.cargos.cargos.domain.repository.SetorRepository;
import com.projeto.cargos.cargos.domain.service.SetorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("setorService")
public class SetorServiceImpl  implements SetorService {
    @Autowired
    SetorRepository setorRepository;

    @Override
    public Setor save(Setor setor) {
        if(setorRepository.findByNome(setor.getNome()).isPresent()){
            throw new SetorJaRegistradoException(("Setor já cadastrado"));
        }
        return setorRepository.save(setor);
    }

    @Override
    public Setor getSetorId(Long id) {
        Setor setor = setorRepository.findById(id).orElseThrow(()-> new SetorNaoEncontradoException("Setor não cadastrado"));
        return setor;
    }

    @Override
    public List<Setor> getAllSetores() {

        return setorRepository.findAll();
    }

    @Override
    public void deleteSetor(Long id) {
        Optional<Setor> setor = setorRepository.findById(id);
        setorRepository.deleteById(setor.get().getId());
    }
}
