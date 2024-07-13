package com.aluracursos.challengeliteratura.model;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name= "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne(fetch = FetchType.EAGER)
    private Autor autor;
    private String idioma;
    private int descargas;

    public Libro(){

    }
    public Libro(DatosLibro datosLibro){
        this.titulo= datosLibro.titulo();
        List<Autor> autores = datosLibro.autores().stream().map(a -> new Autor(a.nombre(), a.anoNacimiento(), a.anoMuerte())).collect(Collectors.toList());
        this.autor = autores.get(0);
        this.idioma = datosLibro.idiomas()[0];
        this.descargas = Integer.parseInt(datosLibro.descargas());

    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }



    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getDescargas() {
        return descargas;
    }

    public void setDescargas(int descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {

        return "***********Libro***********\n" +
                "Titulo : " + titulo + '\n' +
                "Autor :" + autor + '\n' +
                "Idioma : " + idioma + '\n' +
                "Descargas : " + descargas + "\n";
    }
}
