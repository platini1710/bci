package com.bci.tareas.controllers;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bci.tareas.dto.PhoneDto;
import com.bci.tareas.dto.RespuestaDTO;
import com.bci.tareas.helper.ErrorResp;
import com.bci.tareas.model.Usuario;
import com.bci.tareas.services.ConsultaUsuarioService;
import com.bci.tareas.services.RegistraUsuarioServices;

import io.swagger.annotations.ApiOperation;



@RestController
@RequestMapping("/consult/usuario")

public class ControllerConsult {

	@Autowired
	private RegistraUsuarioServices registraUsuarioServices;
	@Autowired
	private ConsultaUsuarioService consultaUsuarioService;

	private static final Logger logger = LoggerFactory.getLogger(ControllerConsult.class);
	DateTimeFormatter ZDT_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss a z");
	String str = "";
	@ApiOperation(value = "Find todos los Usuarios", notes = "Return clase Respuesta " + "resultado ")
	@RequestMapping(method = RequestMethod.GET,  value = "/allUsuarios")
	@ResponseBody
	public ResponseEntity<List<RespuestaDTO>>  getAllUsuarios() {
		logger.info("todo los usuarios");
		 str = ZDT_FORMATTER.format(ZonedDateTime.now());
		List<Usuario> listAllUsuarios = new ArrayList<>();
		List<RespuestaDTO> listRespuesta = new ArrayList<>();
		try {
			listAllUsuarios = consultaUsuarioService.findAllUsuario();
			listAllUsuarios.forEach(c -> {
				logger.info("entro al for each:::" + c.getName());
				RespuestaDTO r = new RespuestaDTO();
				r.setCreated(c.getCreated());
				r.setEmail(c.getEmail());
				r.setUuid(c.getId());
				if (c.getActive())
					r.setIsActive("true");
				else
					r.setIsActive("false");
				r.setName(c.getName());
				r.setToken(c.getToken());
				r.setLastLogin(c.getLastLogin());
				PhoneDto phone = new PhoneDto();
				if (c.getPhone()!=null) {
					phone.setCityCode(c.getPhone().getCityCode());
					phone.setNumber(c.getPhone().getNumber());
					phone.setCountryCode(c.getPhone().getCountryCode());
				}
				r.setPhone(phone);
				listRespuesta.add(r);
			}

			);

		} catch (Exception e) {
			RespuestaDTO respuestaDTO=new RespuestaDTO();
			respuestaDTO.setMsg("se ha producido un error ");
			listRespuesta.add(respuestaDTO);
			logger.error(e.getMessage(), e);
			return new ResponseEntity<List<RespuestaDTO>>(listRespuesta, HttpStatus.FORBIDDEN); // XXX
		
		}
		Optional<List<RespuestaDTO>> list=Optional.of(listRespuesta);
		
		return new ResponseEntity<List<RespuestaDTO>>(listRespuesta, HttpStatus.OK); // XXX
	}


	/**
	 * Gets users by id.
	 *
	 * @param userId the user id
	 * @return the users by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@ApiOperation(value = "Find un tarea por  id de la tarea", notes = "Return tarea "
			+ "resultado en campoProducto maneja su propias excepcion")
	@GetMapping("/login/{id}")
	@ResponseBody
	public ResponseEntity<?> getUsuariosById(@PathVariable(value ="id", required = true) String id) {
		str = ZDT_FORMATTER.format(ZonedDateTime.now());
		logger.info("id  <:::" + id);
		try {
			Usuario user = new Usuario(id);
			RespuestaDTO r=new RespuestaDTO();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		    HttpHeaders headers = new HttpHeaders();
	
			byte[] byte_id=user.getId().getBytes();
			UUID uuid = UUID.nameUUIDFromBytes(byte_id);
	
			logger.info("uuid id  <:::" + uuid.toString());
			logger.info("user  <:::" + user);
			Usuario usuario=consultaUsuarioService.findUsuario(uuid.toString());
		    if (usuario==null) {
				return getError();
		    } else 
			return 	 new ResponseEntity<>(
					usuario, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		} 

		catch (Exception e) {
		    ErrorResp errorResp=new ErrorResp();
		    errorResp.setCodigo( HttpStatus.INTERNAL_SERVER_ERROR.value());
		    errorResp.setDetail("se ha producido un error desconocido , revise el parametro");
		    errorResp.setTimestamp(str);
			logger.error(e.getMessage(),e);
			return new ResponseEntity<>(
					errorResp,  HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	public ResponseEntity<ErrorResp> getError() {
		logger.info("getError() method - start");
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("usuario", "no existe");
	    ErrorResp errorResp=new ErrorResp();
	    errorResp.setCodigo( HttpStatus.INTERNAL_SERVER_ERROR.value());
	    errorResp.setDetail("se ha producido un error, rut no encontrado ");
	    errorResp.setTimestamp(str);
	  //  Usuario.setMsg("se ha producido un error ");
		logger.info("getError() method - end");
		return new ResponseEntity<>(
				errorResp, headers, HttpStatus.INTERNAL_SERVER_ERROR);


}
}
