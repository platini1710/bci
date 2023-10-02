package com.bci.tareas.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bci.tareas.model.Phone;
import com.bci.tareas.model.Usuario;
import com.bci.tareas.repositorio.PhoneDataRestRepository;
import com.bci.tareas.repositorio.UsuarioDataRestRepository;



@Component 
public class RegistraUsuarioServicesImpl implements  RegistraUsuarioServices{
	@Autowired
	private UsuarioDataRestRepository usuarioDataRestRepository; 
	@Autowired
	private PhoneDataRestRepository phoneDataRestRepository; 

	public Usuario save(Usuario usuario) {
		// TODO Auto-generated method stub
		usuarioDataRestRepository.save(usuario);
		  return usuario;
	}

	public Phone update(Phone phone) {
		// TODO Auto-generated method stub

		  return phoneDataRestRepository.save(phone);
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		usuarioDataRestRepository.deleteById( id);
	}


}