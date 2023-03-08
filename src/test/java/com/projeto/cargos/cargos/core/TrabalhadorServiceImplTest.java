package com.projeto.cargos.cargos.core;

import com.projeto.cargos.cargos.core.serviceImpl.TrabalhadorServiceImpl;
import com.projeto.cargos.cargos.domain.entity.Cargo;
import com.projeto.cargos.cargos.domain.entity.Setor;
import com.projeto.cargos.cargos.domain.entity.Trabalhador;
import com.projeto.cargos.cargos.domain.exception.notacceptable.CpfJaRegistradoException;
import com.projeto.cargos.cargos.domain.exception.notfound.TrabalhadorNaoEncontradoException;
import com.projeto.cargos.cargos.domain.repository.TrabalhadorRepository;
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
public class TrabalhadorServiceImplTest {
    @Mock
    private TrabalhadorRepository trabalhadorRepository;

    @InjectMocks
    private TrabalhadorServiceImpl service;

    @Test
    public void SaveTrabalhadorIsOk() {
        Trabalhador trabalhadorInput = Trabalhador.builder()
                .cpf("36974253826")
                .nome("Jose")
                .cargo(Cargo.builder()
                        .nome("RH")
                        .setor(Setor.builder().build())
                        .trabalhadores(Arrays.asList(Trabalhador.builder().build()))
                        .build())
                .build();

        Trabalhador trabalhadorExpected = Trabalhador.builder()
                .cpf("12345678900")
                .nome("Jose")
                .id(1l)
                .cargo(Cargo.builder()
                        .nome("RH")
                        .setor(Setor.builder().build())
                        .trabalhadores(Arrays.asList(Trabalhador.builder().build()))
                        .build())
                .build();

        when(trabalhadorRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(trabalhadorRepository.save(trabalhadorInput)).thenReturn(trabalhadorExpected);

        Trabalhador response = service.save(trabalhadorInput);
        assertEquals(trabalhadorExpected,response);
    }

    @Test
    public void NotSaveTrabalhadorCpf() {
        Trabalhador trabalhadorInput = Trabalhador.builder()
                .cpf("36974253826")
                .nome("Jose")
                .cargo(Cargo.builder()
                        .nome("RH")
                        .setor(Setor.builder().build())
                        .trabalhadores(Arrays.asList(Trabalhador.builder().build()))
                        .build())
                .build();

        when(trabalhadorRepository.findByCpf(anyString())).thenReturn(Optional.of(trabalhadorInput));
        assertThrows(CpfJaRegistradoException.class,()->service.save(trabalhadorInput));
    }


    @Test
    public void GetAllTrabalhadores() {
        Trabalhador trabalhadorInput = Trabalhador.builder().build();
        when(trabalhadorRepository.findAll()).thenReturn(Arrays.asList(trabalhadorInput));
        List<Trabalhador> trabalhadores = service.getAllTrabalhadores();
        assertEquals(trabalhadorInput,trabalhadores.get(0));
    }

    @Test
    public void GetTrabalhdorId() {
        Trabalhador trabalhadorInput = Trabalhador.builder()
                .id(1l)
                .cpf("36974253826")
                .nome("Jose")
                .cargo(Cargo.builder()
                        .nome("RH")
                        .setor(Setor.builder().build())
                        .trabalhadores(Arrays.asList(Trabalhador.builder().build()))
                        .build())
                .build();
        when(trabalhadorRepository.findById(anyLong())).thenReturn(Optional.of(trabalhadorInput));
        Trabalhador trabalhador = service.getTrabalhadorId(trabalhadorInput.getId());
        assertEquals(trabalhadorInput,trabalhador);
    }

    @Test
    public void NotGetTrabalhadorId() {
        Trabalhador trabalhadorInput = Trabalhador.builder()
                .cpf("36974253826")
                .nome("Jose")
                .cargo(Cargo.builder().build())
                .build();
        assertThrows(TrabalhadorNaoEncontradoException.class,()->service.getTrabalhadorId(trabalhadorInput.getId()));
    }
}
