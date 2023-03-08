package com.projeto.cargos.cargos.app;

import com.projeto.cargos.cargos.app.controller.SetorController;
import com.projeto.cargos.cargos.app.dto.request.SetorRequest;
import com.projeto.cargos.cargos.app.dto.response.CargoResponse;
import com.projeto.cargos.cargos.app.dto.response.SetorResponse;
import com.projeto.cargos.cargos.app.dto.response.TrabalhadorResponse;
import com.projeto.cargos.cargos.core.mapper.SetorMapper;
import com.projeto.cargos.cargos.domain.entity.Cargo;
import com.projeto.cargos.cargos.domain.entity.Setor;
import com.projeto.cargos.cargos.domain.exception.notfound.SetorNaoEncontradoException;
import com.projeto.cargos.cargos.domain.service.SetorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Arrays;

import static com.projeto.cargos.cargos.utils.JsonConvertionUtils.asJsonString;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class SetorControllerTest {
    private MockMvc mockMvc;
    @Mock
    private SetorService service;

    @Mock
    private SetorMapper setorMapper;

    @InjectMocks
    private SetorController controller;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }
    @Test
    public void getAllSetoresIsOk()throws Exception{
        SetorResponse setorResponse = SetorResponse.builder()
                .id(1l)
                .nome("Jose")
                .cargos(Arrays.asList(CargoResponse.builder()
                                .id(1l)
                                .nome("josé")
                                .idSetor(1l)
                                .trabalhadores(Arrays.asList(TrabalhadorResponse
                                        .builder()
                                                .id(1l)
                                                .cpf("36974253826")
                                                .nome("josé")
                                                .idCargo(1l)
                                        .build()))
                        .build()))
                .build();

        when(setorMapper.toListDto(anyList())).thenReturn(Arrays.asList(setorResponse));
        mockMvc.perform(get("/setor/setores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(setorResponse.getId()))
                .andExpect(jsonPath("$[0].nome").value(setorResponse.getNome()))
             //   .andExpect(jsonPath("$[0].cargos").value(setorResponse.getCargos()))
                .andReturn();
    }

    @Test
    public void getSetorIsOk()throws Exception {
        Setor setor = Setor.builder()
                .id(1l)
                .nome("José")
                .cargos(Arrays.asList(Cargo.builder().build()))
                .build();
        SetorResponse setorResponse = SetorResponse.builder()
                .id(1l)
                .nome("Jose")
                .cargos(Arrays.asList(CargoResponse.builder().build()))
                .build();

        when(service.getSetorId(anyLong())).thenReturn(setor);
        when(setorMapper.toDto(setor)).thenReturn(setorResponse);
        mockMvc.perform(get("/setor/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(setorResponse.getId()))
                .andExpect(jsonPath("$.nome").value(setorResponse.getNome()))
             //   .andExpect(jsonPath("$[0].cargos").value(setorResponse.getCargos()))
                .andReturn();
    }

    @Test
    public void getSetorNotOk()throws Exception {
        doThrow(SetorNaoEncontradoException.class).when(service).getSetorId(anyLong());
        mockMvc.perform(get("/setor/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getSetorBadRequestNotNome()throws Exception {

        Setor setor = Setor.builder()
                .id(1l)
               // .nome("José")
                .cargos(Arrays.asList(Cargo.builder().build()))
                .build();
        SetorResponse setorResponse = SetorResponse.builder()
                .id(1l)
                .nome("Jose")
                .cargos(Arrays.asList(CargoResponse.builder().build()))
                .build();

        when(service.getSetorId(anyLong())).thenReturn(setor);
        when(setorMapper.toDto(setor)).thenReturn(setorResponse);
        mockMvc.perform(get("/setor/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(setorResponse.getId()))
                .andExpect(jsonPath("$.nome").value(setorResponse.getNome()))
           //     .andExpect(jsonPath("$.cargos").value(setorResponse.getCargos()))
                .andReturn();
    }

    @Test
    public void setorIsCreate()throws Exception {
        Setor setor = Setor.builder()
                .id(1l)
                .nome("José")
                .cargos(Arrays.asList(Cargo.builder().build()))
                .build();
        SetorResponse setorResponse = SetorResponse.builder()
                .id(1l)
                .nome("Jose")
                .cargos(Arrays.asList(CargoResponse.builder().build()))
                .build();

        SetorRequest setorRequest = SetorRequest.builder()
                .nome("Jose")
                .build();

        when(service.save(setor)).thenReturn(setor);
        when(setorMapper.toEntity(setorRequest)).thenReturn(setor);
        when(setorMapper.toDto(setor)).thenReturn(setorResponse);

        var content = asJsonString(setorRequest);
        mockMvc.perform(post("/setor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(setorResponse.getId()))
                .andExpect(jsonPath("$.nome").value(setorResponse.getNome()));
          //      .andExpect(jsonPath("$.cargos").value(setorResponse.getIdCargo()));

    }
}
