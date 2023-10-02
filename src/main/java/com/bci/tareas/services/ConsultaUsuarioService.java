package com.bci.tareas.services;

import java.util.List;

import com.bci.tareas.model.Usuario;




public interface  ConsultaUsuarioService {

	public Usuario findUsuario(String id) ;
	public List<Usuario> findEmail(String email) ;
	public List<Usuario> findAllUsuario() ;
}
