package com.projeto.cargos.cargos.app;

import com.projeto.cargos.cargos.app.controller.TrabalhadorController;
import com.projeto.cargos.cargos.app.dto.request.TrabalhadorRequest;
import com.projeto.cargos.cargos.app.dto.response.TrabalhadorResponse;
import com.projeto.cargos.cargos.core.mapper.TrabalhadorMapper;
import com.projeto.cargos.cargos.domain.entity.Cargo;
import com.projeto.cargos.cargos.domain.entity.Trabalhador;
import com.projeto.cargos.cargos.domain.exception.notfound.TrabalhadorNaoEncontradoException;
import com.projeto.cargos.cargos.domain.service.TrabalhadorService;
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
public class TrabalhadorControllerTest {
    private MockMvc mockMvc;
    @Mock
    private TrabalhadorService service;

    @Mock
    private TrabalhadorMapper trabalhadorMapper;

    @InjectMocks
    private TrabalhadorController controller;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }
    @Test
    public void getAllTrabalhadoresIsOk()throws Exception{
        TrabalhadorResponse trabalhadorResponse = TrabalhadorResponse.builder()
                .id(1l)
                .cpf("36974253826")
                .nome("Jose")
                .idCargo(1l)
                .build();

        when(trabalhadorMapper.toListDto(anyList())).thenReturn(Arrays.asList(trabalhadorResponse));
        mockMvc.perform(get("/trabalhador/trabalhadores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(trabalhadorResponse.getId()))
                .andExpect(jsonPath("$[0].cpf").value(trabalhadorResponse.getCpf()))
                .andExpect(jsonPath("$[0].nome").value(trabalhadorResponse.getNome()))
                .andExpect(jsonPath("$[0].idCargo").value(trabalhadorResponse.getIdCargo()))
                .andReturn();


    }

    @Test
    public void getTrabalhadorIsOk()throws Exception {
        Trabalhador trabalhador = Trabalhador.builder()
                .id(1l)
                .cpf("36974253826")
                .nome("José")
                .cargo(Cargo.builder().build())
                .build();
        TrabalhadorResponse trabalhadorResponse = TrabalhadorResponse.builder()
                .id(1l)
                .cpf("36974253826")
                .nome("Jose")
                .idCargo(1l)
                .build();

        when(service.getTrabalhadorId(anyLong())).thenReturn(trabalhador);
        when(trabalhadorMapper.toDto(trabalhador)).thenReturn(trabalhadorResponse);
        mockMvc.perform(get("/trabalhador/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(trabalhadorResponse.getId()))
                .andExpect(jsonPath("$.cpf").value(trabalhadorResponse.getCpf()))
                .andExpect(jsonPath("$.nome").value(trabalhadorResponse.getNome()))
                .andExpect(jsonPath("$.idCargo").value(trabalhadorResponse.getIdCargo()))
                .andReturn();
    }

    @Test
    public void getTrabalhadorNotOk()throws Exception {

        doThrow(TrabalhadorNaoEncontradoException.class).when(service).getTrabalhadorId(anyLong());
        mockMvc.perform(get("/trabalhador/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }
    @Test
    public void getTrabalhadorBadRequestNotCpf()throws Exception {

        Trabalhador trabalhador = Trabalhador.builder()
                .id(1l)
                //.cpf("36974253826")
                .nome("José")
                .cargo(Cargo.builder().build())
                .build();
        TrabalhadorResponse trabalhadorResponse = TrabalhadorResponse.builder()
                .id(1l)
                .cpf("36974253826")
                .nome("Jose")
                .idCargo(1l)
                .build();

        when(service.getTrabalhadorId(anyLong())).thenReturn(trabalhador);
        when(trabalhadorMapper.toDto(trabalhador)).thenReturn(trabalhadorResponse);
        mockMvc.perform(get("/trabalhador/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(trabalhadorResponse.getId()))
                .andExpect(jsonPath("$.cpf").value(trabalhadorResponse.getCpf()))
                .andExpect(jsonPath("$.nome").value(trabalhadorResponse.getNome()))
                .andExpect(jsonPath("$.idCargo").value(trabalhadorResponse.getIdCargo()))
                .andReturn();
    }

    @Test
    public void trabalhadorIsCreate()throws Exception {
        Trabalhador trabalhador = Trabalhador.builder()
                .id(1l)
                .cpf("36974253826")
                .nome("José")
                .cargo(Cargo.builder().build())
                .build();
        TrabalhadorResponse trabalhadorResponse = TrabalhadorResponse.builder()
                .id(1l)
                .cpf("36974253826")
                .nome("Jose")
                .idCargo(1l)
                .build();
        TrabalhadorRequest trabalhadorRequest = TrabalhadorRequest.builder()
                .cpf("36974253826")
                .nome("Jose")
                .idCargo(1l)
                .build();
        when(service.save(trabalhador)).thenReturn(trabalhador);
        when(trabalhadorMapper.toEntity(trabalhadorRequest)).thenReturn(trabalhador);
        when(trabalhadorMapper.toDto(trabalhador)).thenReturn(trabalhadorResponse);

        var content = asJsonString(trabalhadorRequest);
        mockMvc.perform(post("/trabalhador")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(trabalhadorResponse.getId()))
                .andExpect(jsonPath("$.cpf").value(trabalhadorResponse.getCpf()))
                .andExpect(jsonPath("$.nome").value(trabalhadorResponse.getNome()))
                .andExpect(jsonPath("$.idCargo").value(trabalhadorResponse.getIdCargo()));

    }
}
