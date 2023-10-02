package com.bci.tareas.controllers;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bci.tareas.dto.RespuestaDTO;
import com.bci.tareas.exception.ResourceFoundException;
import com.bci.tareas.helper.Constantes;
import com.bci.tareas.model.Phone;
import com.bci.tareas.model.Usuario;
import com.bci.tareas.services.ConsultaUsuarioService;
import com.bci.tareas.services.RegistraUsuarioServices;
import com.global.tareas.helper.EmailValidatorSimple;
import com.global.tareas.helper.ErrorException;
import com.global.tareas.helper.ErrorResp;
import com.global.tareas.helper.PasswordValidator;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.ApiOperation;



@RestController
@RequestMapping("/registro/usuario")

public class ControllerRegistation {
	
	@Autowired
	private RegistraUsuarioServices registraUsuarioServices;
	@Autowired
	private ConsultaUsuarioService consultaUsuarioService;
	


	DateTimeFormatter ZDT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a z");
	private static final Logger logger = LoggerFactory.getLogger(ControllerRegistation.class);

	/**
	 * Update user response entity.
	 *
	 * @param producto the user id
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@ApiOperation(value = "guarda un registro de la tarea  en caso de que exista mandara el respectivo msg ", notes = "Return clase Respuesta "
			+ " retorna el resultado en campo Msg ")
	@RequestMapping(value = "/sign-up", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<RespuestaDTO> saveUsuario(@RequestBody Usuario usuario) {
		logger.info("grabar tareas");
		String str = ZDT_FORMATTER.format(ZonedDateTime.now());
		RespuestaDTO response = new RespuestaDTO();
		  Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		try {

			byte[] byte_id= usuario.getId().getBytes();
			UUID uuid = UUID.nameUUIDFromBytes(byte_id);
		

			usuario.setId(uuid.toString());
			logger.info("uuid " + uuid.toString());

			Usuario user = consultaUsuarioService.findUsuario(uuid.toString());

			logger.info("In prod" + user);

			if (user != null) {
				logger.debug("usuario existe");
				throw new ResourceFoundException(Constantes.msgFound);
			}
			//valida nombre
			if  (usuario.getName()==null) {
				logger.debug("password  es null");
				throw new ResourceFoundException(Constantes.nameNulo);
			}		
			if  (usuario.getPassword()==null) {
				logger.debug("nombre es null");
				throw new ResourceFoundException(Constantes.pwdNulo);
			}	
			//es valido el  email
			if  (usuario.getEmail()==null) {
				logger.debug("email null");
				throw new ResourceFoundException(Constantes.emailNulo);
			}
			//es valido el  email
			if  (!EmailValidatorSimple.isValid(usuario.getEmail())) {
				logger.debug("email invalido");
				throw new ResourceFoundException(Constantes.emailInvalid);
			}
			
			//existe ya el email
			if (consultaUsuarioService.findEmail(usuario.getEmail())!=null && consultaUsuarioService.findEmail(usuario.getEmail()).size()>0) {
				logger.debug("email ya existe");
				throw new ErrorException(Constantes.emailExiste);
			}	
			
	
			if  (!PasswordValidator.isValid(usuario.getPassword())) {
				logger.debug("password  invalida");
				throw new ResourceFoundException(Constantes.passwordInvalid);
			}
			logger.debug("antes de grabar");
			usuario.setCreated(str);
			logger.debug("str " +str);									
			usuario.setLastLogin(str);	
			logger.debug("uuid  " +uuid.toString());
			usuario.setToken(  getJWTToken(usuario.getName())  );
			logger.debug("token " + getJWTToken(usuario.getName()));		
			usuario.setActive(true);
			response.setUuid(uuid.toString());
			//response.setMsg(Constantes.insert);
			response.setPassword(usuario.getPassword());
			response.setName(usuario.getName());
			response.setLastLogin(usuario.getLastLogin());
			response.setCreated(str);
			response.setEmail(usuario.getEmail());
			response.setToken(usuario.getToken());
			response.setIsActive("true");
			
			if (usuario.getPhone()!=null) {
				logger.debug("phone  " );
				Phone phone =new Phone();
				phone.setId(uuid.toString());
				phone.setNumber(usuario.getPhone().getNumber());
				usuario.setPhone(phone);
				registraUsuarioServices.update(phone);
			}
			registraUsuarioServices.save(usuario);

		} catch (ResourceFoundException e) {
			logger.error(e.getMessage());
	
			ErrorResp error=new ErrorResp();
			error.setCodigo(HttpStatus.NOT_FOUND.value());
			error.setDetail(e.getMessage());
			error.setTimestamp(timestamp.toString());
			response.setErrorResp(error);;
			return new ResponseEntity<>( response, HttpStatus.NOT_FOUND);
		} catch (ErrorException e) {
			logger.error(e.getMessage());
	
			ErrorResp error=new ErrorResp();
			error.setCodigo(HttpStatus.BAD_REQUEST.value());
			error.setDetail(e.getMessage());
			error.setTimestamp(timestamp.toString());
			response.setErrorResp(error);;
			return new ResponseEntity<>( response, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.getMessage());
			
			response.setMsg(e.getStackTrace().toString());
			ErrorResp error=new ErrorResp();
			error.setCodigo(500);
			error.setDetail(e.getMessage());
			error.setTimestamp(timestamp.toString());
			response.setErrorResp(error);
			return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>( response, HttpStatus.OK);
	}

	

	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}
