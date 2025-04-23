package com.backend.montreal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.montreal.entity.Tema;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {
	 List<Tema> findByDescricao(String descricao); // Buscar temas pelo nom
}
