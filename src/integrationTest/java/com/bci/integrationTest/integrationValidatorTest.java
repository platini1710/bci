package com.bci.integrationTest;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bci.tareas.controllers.ControllerConsult;
import com.bci.tareas.controllers.ControllerRegistation;
import com.bci.tareas.dto.RespuestaDTO;
import com.bci.tareas.model.Usuario;
import com.bci.tareas.services.ConsultaUsuarioServiceImplement;
import com.bci.tareas.services.RegistraUsuarioServicesImpl;
import com.google.gson.Gson;

public class integrationValidatorTest   {
	
	private MockMvc mockMvc;
	private MockMvc mockMvc2;
	Usuario usuario1=new Usuario("98c6f2c2-287f-3c73-8ea3-d40ae7ec3ff2","augusto Espinoza","A23bbbccc@","aespinoza3010@gmail.com","12-08-1987","ass","assa",true);
	@Mock                         
	private ConsultaUsuarioServiceImplement consultaUsuarioServiceImplement;
	@Mock                         
	private RegistraUsuarioServicesImpl registraUsuarioServicesImpl;
	@InjectMocks
	private ControllerConsult controllerConsult;
	
	@Mock
    private ResponseEntity<RespuestaDTO> mockResponse;
	@InjectMocks
	private ControllerRegistation controllerRegistation;
	
	@BeforeEach
	public  void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders
		        .standaloneSetup(controllerConsult)
		        .build();
		mockMvc2 = MockMvcBuilders
		        .standaloneSetup(controllerRegistation)
		        .build();
	}
	
	

	@Test
	 void getAllRecordSucesfuul() throws Exception{
	List<Usuario> records=new ArrayList<>(Arrays.asList(usuario1));
		Mockito.when(consultaUsuarioServiceImplement.findAllUsuario()).thenReturn(records);
		mockMvc.perform(MockMvcRequestBuilders.get("/consult/usuario/allUsuarios").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())  
		.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].name",is("augusto Espinoza") )  ) ;

	}



	@Test
	public void savetUsuarioById() throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(usuario1);		
		when(registraUsuarioServicesImpl.save(usuario1)).thenReturn(usuario1);
		mockMvc2.perform(MockMvcRequestBuilders.post("/registro/usuario/sign-up").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.email",is("aespinoza3010@gmail.com")  )) ;;
	}
}