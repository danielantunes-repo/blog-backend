package com.backend.montreal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.montreal.entity.Usuario;

public interface LoginRepository extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findByUsername(String login);

}
