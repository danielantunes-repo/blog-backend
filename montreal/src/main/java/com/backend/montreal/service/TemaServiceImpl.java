package com.backend.montreal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.montreal.entity.Tema;
import com.backend.montreal.repository.TemaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TemaServiceImpl implements TemaService {

    @Autowired
    private TemaRepository temaRepository;

    @Override
    public Tema saveTema(Tema tema) {
        return temaRepository.save(tema);
    }

    @Override
    public List<Tema> getAllTemas() {
        return temaRepository.findAll();
    }

    @Override
    public Tema getTemaById(Long id) {
        Optional<Tema> optionalTema = temaRepository.findById(id);
        if (optionalTema.isPresent()) {
            return optionalTema.get();
        } else {
            throw new EntityNotFoundException("Tema não encontrado");
        }
    }

    @Override
    public Tema updateTema(Long id, Tema updatedTema) {
        Optional<Tema> optionalTema = temaRepository.findById(id);
        if (optionalTema.isPresent()) {
            Tema existingTema = optionalTema.get();
            existingTema.setDescricao(updatedTema.getDescricao());
            return temaRepository.save(existingTema);
        } else {
            throw new EntityNotFoundException("Tema não encontrado para edição");
        }
    }

    @Override
    public void deleteTema(Long id) {
        Optional<Tema> optionalTema = temaRepository.findById(id);
        if (optionalTema.isPresent()) {
            temaRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Tema não encontrado para exclusão");
        }
    }
}
