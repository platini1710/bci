package com.bci.tareas.services;

import com.bci.tareas.model.Phone;
import com.bci.tareas.model.Usuario;

public interface RegistraUsuarioServices {
	  public Usuario save(Usuario usuario) ;
	  public Phone update(Phone phone) ;
		public void delete(String id);

}