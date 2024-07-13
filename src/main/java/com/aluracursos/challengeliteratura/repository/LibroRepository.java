package com.aluracursos.challengeliteratura.repository;

import com.aluracursos.challengeliteratura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String nombreLibro);

    List<Libro> findByIdiomaContainsIgnoreCase(String sigla);
}
