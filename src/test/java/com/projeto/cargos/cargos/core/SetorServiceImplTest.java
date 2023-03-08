package com.projeto.cargos.cargos.core;

import com.projeto.cargos.cargos.core.serviceImpl.SetorServiceImpl;
import com.projeto.cargos.cargos.domain.entity.Cargo;
import com.projeto.cargos.cargos.domain.entity.Setor;
import com.projeto.cargos.cargos.domain.entity.Trabalhador;
import com.projeto.cargos.cargos.domain.exception.notacceptable.SetorJaRegistradoException;
import com.projeto.cargos.cargos.domain.exception.notfound.SetorNaoEncontradoException;
import com.projeto.cargos.cargos.domain.repository.SetorRepository;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SetorServiceImplTest {

    @Mock
    private SetorRepository setorRepository;

    @InjectMocks
    private SetorServiceImpl service;

    @Test
    public void SaveSetorIsOk() {
        Setor setorInput = Setor.builder()
                .nome("Jose")
                .cargos(Arrays.asList(Cargo.builder()
                        .nome("José").setor(Setor.builder().build())
                        .trabalhadores(Arrays.asList(Trabalhador.builder().build()))
                        .build()))
                .build();

        Setor setorExpected = Setor.builder()
                .id(1l)
                .nome("Jose")
                .cargos(Arrays.asList(Cargo.builder()
                        .nome("José").setor(Setor.builder().build())
                        .trabalhadores(Arrays.asList(Trabalhador.builder().build()))
                        .build()))
                .build();

        when(setorRepository.findByNome(anyString())).thenReturn(Optional.empty());
        when(setorRepository.save(setorInput)).thenReturn(setorExpected);

        Setor response = service.save(setorInput);
        assertEquals(setorExpected, response);
    }

    @Test
    public void NotSaveSetorCpf() {
        Setor setorInput = Setor.builder()
                .nome("Jose")
                .cargos(Arrays.asList(Cargo.builder()
                        .nome("José").setor(Setor.builder().build())
                        .trabalhadores(Arrays.asList(Trabalhador.builder().build()))
                        .build()))
                .build();

        when(setorRepository.findByNome(anyString())).thenReturn(Optional.of(setorInput));
        assertThrows(SetorJaRegistradoException.class, () -> service.save(setorInput));
    }


    @Test
    public void GetAllSetores() {
        Setor setorInput = Setor.builder().build();
        when(setorRepository.findAll()).thenReturn(Arrays.asList(setorInput));
        List<Setor> setores = service.getAllSetores();
        assertEquals(setorInput, setores.get(0));
    }

    @Test
    public void GetSetorId() {
        Setor setorInput = Setor.builder()
                .nome("Jose")
                .id(1l)
                .cargos(Arrays.asList(Cargo.builder()
                        .nome("José").setor(Setor.builder()
                                .id(1l)
                                .nome("RH")
                                .cargos(Arrays.asList(Cargo.builder().build()))
                                .build())
                        .trabalhadores(Arrays.asList(Trabalhador.builder().build()))
                        .build()))
                .build();

        when(setorRepository.findById(anyLong())).thenReturn(Optional.of(setorInput));
        Setor setor = service.getSetorId(setorInput.getId());
        assertEquals(setorInput, setor);
    }

    @Test
    public void DeleteSetorId() {
        Setor setorInput = Setor.builder()
                .nome("Jose")
                .id(1l)
                .cargos(Arrays.asList(Cargo.builder()
                        .nome("José").setor(Setor.builder()
                                .id(1l)
                                .nome("RH")
                                .cargos(Arrays.asList(Cargo.builder().build()))
                                .build())
                        .trabalhadores(Arrays.asList(Trabalhador.builder().build()))
                        .build()))
                .build();
        when(setorRepository.findById(anyLong())).thenReturn(Optional.of(Setor.builder().build()));
        doNothing().when(setorRepository).deleteById(1l);
    }


    @Test
    public void NotGetSetorId() {
        Setor setorInput = Setor.builder()
                .nome("Jose")
                .id(1l)
                .cargos(Arrays.asList(Cargo.builder()
                        .nome("José").setor(Setor.builder().build())
                        .trabalhadores(Arrays.asList(Trabalhador.builder().build()))
                        .build()))
                .build();
        assertThrows(SetorNaoEncontradoException.class, () -> service.getSetorId(setorInput.getId()));
    }
}
