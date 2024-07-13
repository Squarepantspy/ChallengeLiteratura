package com.aluracursos.challengeliteratura.repository;

import com.aluracursos.challengeliteratura.model.Autor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {
    Optional<Autor> findByNombreContainsIgnoreCase(String nombreAutor);

    @Query("SELECT a FROM Autor a WHERE :ano >= a.anoNacimiento AND :ano <= a.anoMuerte ")
    List<Autor> encontrarVivos(Integer ano);
}
