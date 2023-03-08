package com.projeto.cargos.cargos.core.serviceImpl;

import com.projeto.cargos.cargos.domain.entity.Cargo;
import com.projeto.cargos.cargos.domain.exception.notacceptable.CargoJaCadastradoException;
import com.projeto.cargos.cargos.domain.exception.notfound.CargoNaoEncontradoException;
import com.projeto.cargos.cargos.domain.repository.CargoRepository;
import com.projeto.cargos.cargos.domain.repository.SetorRepository;
import com.projeto.cargos.cargos.domain.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cargoService")
public class CargoServiceImpl implements CargoService {

    @Autowired
    SetorRepository setorRepository;
    @Autowired
    CargoRepository cargoRepository;

    @Override
    public Cargo save(Cargo cargo) {
        var cargoId = cargoRepository.findByNome(cargo.getNome());
        if (cargoId.isPresent()) {
            throw new CargoJaCadastradoException("Cargo já cadastrado");
        }
        return cargoRepository.save(cargo);
    }

    @Override
    public Cargo getCargoId(Long id) {
        Cargo cargo = cargoRepository.findById(id).orElseThrow(()-> new CargoNaoEncontradoException("Cargo não cadastrado"));
        return cargo;
    }

    @Override
    public List<Cargo> getAllCargo() {

        return cargoRepository.findAll();
    }
}
