package com.projeto.cargos.cargos.app;

import com.projeto.cargos.cargos.app.controller.CargoController;
import com.projeto.cargos.cargos.app.dto.request.CargoRequest;
import com.projeto.cargos.cargos.app.dto.response.CargoResponse;
import com.projeto.cargos.cargos.app.dto.response.TrabalhadorResponse;
import com.projeto.cargos.cargos.core.mapper.CargoMapper;
import com.projeto.cargos.cargos.domain.entity.Cargo;
import com.projeto.cargos.cargos.domain.entity.Setor;
import com.projeto.cargos.cargos.domain.entity.Trabalhador;
import com.projeto.cargos.cargos.domain.exception.notfound.CargoNaoEncontradoException;
import com.projeto.cargos.cargos.domain.service.CargoService;
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
public class CargoControllerTest {
    private MockMvc mockMvc;
    @Mock
    private CargoService service;

    @Mock
    private CargoMapper cargoMapper;

    @InjectMocks
    private CargoController controller;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }
    @Test
    public void getAllCargoIsOk()throws Exception{
        CargoResponse cargoResponse = CargoResponse.builder()
                .id(1l)
                .nome("RH")
                .trabalhadores(Arrays.asList(TrabalhadorResponse.builder()
                        .id(1l)
                        .cpf("36974253826")
                        .nome("josé")
                        .idCargo(1l)
                        .build()))
                .build();

        when(cargoMapper.toListDto(anyList())).thenReturn(Arrays.asList(cargoResponse));
        mockMvc.perform(get("/cargo/cargos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(cargoResponse.getId()))
                .andExpect(jsonPath("$[0].idSetor").value(cargoResponse.getIdSetor()))
                .andExpect(jsonPath("$[0].nome").value(cargoResponse.getNome()))
                //   .andExpect(jsonPath("$[0].trabalhadores").value(cargoResponse.getTrabalhadores()))
                .andReturn();
    }

    @Test
    public void getCargoIsOk()throws Exception {
        Cargo cargo = Cargo.builder()
                .id(1l)
                .nome("RH")
                .setor(Setor.builder().build())
                .trabalhadores(Arrays.asList(Trabalhador.builder().build()))
                .build();
        CargoResponse cargoResponse = CargoResponse.builder()
                .id(1l)
                .nome("RH")
                .trabalhadores(Arrays.asList(TrabalhadorResponse.builder()
                        .id(1l)
                        .cpf("36974253826")
                        .nome("josé")
                        .idCargo(1l)
                        .build()))
                .build();

        when(service.getCargoId(anyLong())).thenReturn(cargo);
        when(cargoMapper.toDto(cargo)).thenReturn(cargoResponse);
        mockMvc.perform(get("/cargo/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cargoResponse.getId()))
                .andExpect(jsonPath("$.nome").value(cargoResponse.getNome()))
                .andExpect(jsonPath("$.idSetor").value(cargoResponse.getIdSetor()))
                //   .andExpect(jsonPath("$[0].trabalhadores").value(setorResponse.getTrabalhadores()))
                .andReturn();
    }

    @Test
    public void getCargoNotOk()throws Exception {
        doThrow(CargoNaoEncontradoException.class).when(service).getCargoId(anyLong());
        mockMvc.perform(get("/cargo/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getCargoBadRequestNotNome()throws Exception {

        Cargo cargo = Cargo.builder()
                .id(1l)
            //    .nome("RH")
                .setor(Setor.builder().build())
                .trabalhadores(Arrays.asList(Trabalhador.builder().build()))
                .build();
        CargoResponse cargoResponse = CargoResponse.builder()
                .id(1l)
                .nome("RH")
                .trabalhadores(Arrays.asList(TrabalhadorResponse.builder()
                        .id(1l)
                        .cpf("36974253826")
                        .nome("josé")
                        .idCargo(1l)
                        .build()))
                .build();

        when(service.getCargoId(anyLong())).thenReturn(cargo);
        when(cargoMapper.toDto(cargo)).thenReturn(cargoResponse);
        mockMvc.perform(get("/cargo/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cargoResponse.getId()))
                .andExpect(jsonPath("$.nome").value(cargoResponse.getNome()))
                .andExpect(jsonPath("$.idSetor").value(cargoResponse.getIdSetor()))
                //.andExpect(jsonPath("$[0].trabalhadores").value(setorResponse.getTrabalhadores()))
                .andReturn();
    }

    @Test
    public void setorIsCreate()throws Exception {
        Cargo cargo = Cargo.builder()
                .id(1l)
                //.nome("RH")
                .setor(Setor.builder().build())
                .trabalhadores(Arrays.asList(Trabalhador.builder().build()))
                .build();
        CargoResponse cargoResponse = CargoResponse.builder()
                .id(1l)
                .nome("RH")
                .trabalhadores(Arrays.asList(TrabalhadorResponse.builder()
                        .id(1l)
                        .cpf("36974253826")
                        .nome("josé")
                        .idCargo(1l)
                        .build()))
                .build();

        CargoRequest cargoRequest = CargoRequest.builder()
                .nome("Jose")
                .idSetor(1l)
                .build();

        when(service.save(cargo)).thenReturn(cargo);
        when(cargoMapper.toEntity(cargoRequest)).thenReturn(cargo);
        when(cargoMapper.toDto(cargo)).thenReturn(cargoResponse);

        var content = asJsonString(cargoRequest);
        mockMvc.perform(post("/cargo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(cargoResponse.getId()))
                .andExpect(jsonPath("$.nome").value(cargoResponse.getNome()))
                .andExpect(jsonPath("$.idSetor").value(cargoResponse.getIdSetor()));
        //      .andExpect(jsonPath("$.trabalhadores").value(cargoResponse.getTrabalhadores()));

    }
}
