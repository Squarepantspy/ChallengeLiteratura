package com.aluracursos.challengeliteratura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title")  String titulo,
        @JsonAlias("authors") List<DatosAutor> autores,
        @JsonAlias("languages") String[] idiomas,
        @JsonAlias("download_count") String descargas
        ) {
        @Override
        public String toString() {
                return "DatosLibro{" +
                        "titulo='" + titulo + '\'' +
                        ", autores=" + autores +
                        ", idiomas=" + Arrays.toString(idiomas) +
                        ", descargas='" + descargas + '\'' +
                        '}';
        }
}
