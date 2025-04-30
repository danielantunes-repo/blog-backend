package com.backend.montreal.dto;

import com.backend.montreal.entity.Usuario;

public class UsuarioDTO {

	private String name;
	private String username;
	private String password;

	public UsuarioDTO() {
	}

	public UsuarioDTO(Usuario usuario) {
		this.name = usuario.getName();
		this.username = usuario.getUsername();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
