package com.bci.tareas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bci.tareas.model.Usuario;
import com.bci.tareas.repositorio.UsuarioDataRestRepository;
@Service
public class ConsultaUsuarioServiceImplement implements ConsultaUsuarioService {

	
	@Autowired
	private UsuarioDataRestRepository usuarioDataRestRepository; 
	  

		
		@Override
		public Usuario findUsuario(String id) {
			// TODO Auto-generated method stub
			if (usuarioDataRestRepository.findById(id).isPresent()) {
		    	return  usuarioDataRestRepository.findById(id).get();
			} else
				return null;
		}


		
		
		@Override
		public List<Usuario> findAllUsuario() {
			// TODO Auto-generated method stub

	    	return usuarioDataRestRepository.findAll();
		}




		@Override
		public List<Usuario>  findEmail(String email) {
			// TODO Auto-generated method stub
		 return	usuarioDataRestRepository.findEmail(email);
		}  
}
