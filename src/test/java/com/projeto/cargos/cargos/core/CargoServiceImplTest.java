package com.projeto.cargos.cargos.core;

import com.projeto.cargos.cargos.core.serviceImpl.CargoServiceImpl;
import com.projeto.cargos.cargos.domain.entity.Cargo;
import com.projeto.cargos.cargos.domain.entity.Setor;
import com.projeto.cargos.cargos.domain.entity.Trabalhador;
import com.projeto.cargos.cargos.domain.exception.notacceptable.CargoJaCadastradoException;
import com.projeto.cargos.cargos.domain.exception.notfound.CargoNaoEncontradoException;
import com.projeto.cargos.cargos.domain.repository.CargoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CargoServiceImplTest {

    @Mock
    private CargoRepository cargoRepository;

    @InjectMocks
    private CargoServiceImpl service;

    @Test
    public void SaveCargoIsOk() {
        Cargo cargoInput = Cargo.builder()
                .nome("Jose")
                .trabalhadores(Arrays.asList(Trabalhador.builder()
                        .nome("José")
                        .cpf("36974253826")
                        .cargo(Cargo.builder().build())
                        .build()))
                .build();

        Cargo cargoExpected = Cargo.builder()
                .id(1l)
                .nome("Jose")
                .trabalhadores(Arrays.asList(Trabalhador.builder()
                        .nome("José")
                        .cpf("36974253826")
                        .cargo(Cargo.builder().build())
                        .build()))
                .build();

        when(cargoRepository.findByNome(anyString())).thenReturn(Optional.empty());
        when(cargoRepository.save(cargoInput)).thenReturn(cargoExpected);

        Cargo response = service.save(cargoInput);
        assertEquals(cargoExpected, response);
    }

    @Test
    public void NotSaveCargoCpf() {
        Cargo cargoInput = Cargo.builder()
                .nome("Jose")
                .trabalhadores(Arrays.asList(Trabalhador.builder()
                        .nome("José")
                        .cpf("36974253826")
                        .cargo(Cargo.builder().build())
                        .build()))
                .build();

        when(cargoRepository.findByNome(anyString())).thenReturn(Optional.of(cargoInput));
        assertThrows(CargoJaCadastradoException.class, () -> service.save(cargoInput));
    }


    @Test
    public void GetAllCargos() {
        Cargo cargoInput = Cargo.builder().build();
        when(cargoRepository.findAll()).thenReturn(Arrays.asList(cargoInput));
        List<Cargo> cargos = service.getAllCargo();
        assertEquals(cargoInput, cargos.get(0));
    }

    @Test
    public void GetCargoId() {

        Cargo cargoInput = Cargo.builder()
                .nome("Jose")
                .id(1l)
                .setor(Setor.builder()
                        .build())
                .build();

        when(cargoRepository.findById(anyLong())).thenReturn(Optional.of(cargoInput));
        Cargo cargo = service.getCargoId(cargoInput.getId());
        assertEquals(cargoInput, cargo);
    }

    @Test
    public void NotGetCargoId() {
        Cargo cargoInput = Cargo.builder()
                .nome("Jose")
                .trabalhadores(Arrays.asList(Trabalhador.builder()
                        .nome("José")
                        .cpf("36974253826")
                        .cargo(Cargo.builder().build())
                        .build()))
                .build();
        assertThrows(CargoNaoEncontradoException.class, () -> service.getCargoId(cargoInput.getId()));
    }
}
