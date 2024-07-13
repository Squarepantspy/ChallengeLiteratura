package com.aluracursos.challengeliteratura.principal;

import com.aluracursos.challengeliteratura.model.Autor;
import com.aluracursos.challengeliteratura.model.Datos;
import com.aluracursos.challengeliteratura.model.DatosLibro;
import com.aluracursos.challengeliteratura.model.Libro;
import com.aluracursos.challengeliteratura.repository.AutorRepository;
import com.aluracursos.challengeliteratura.repository.LibroRepository;
import com.aluracursos.challengeliteratura.service.ConsumoAPI;
import com.aluracursos.challengeliteratura.service.ConvierteDatos;

import javax.swing.text.html.Option;
import java.net.URLEncoder;
import java.util.*;

public class Principal {
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private Scanner teclado = new Scanner(System.in);
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private LibroRepository repositorioLibro;
    private AutorRepository repositorioAutor;
    private List<Libro> listaLibros = new ArrayList<>();
    private List<Autor> listaAutores = new ArrayList<>();

    public Principal(LibroRepository repositoryLibro, AutorRepository repositoryAutor){
        this.repositorioLibro = repositoryLibro;
        this.repositorioAutor = repositoryAutor;
    }
    public void muestraElMenu(){

        var opcion=-1;
        while(opcion !=0){
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idiomas  
                    0 - Salir
                    """;
            System.out.println(menu);
            try{
            opcion = teclado.nextInt();
            teclado.nextLine();}
            catch (InputMismatchException e){
                System.out.println("No ha introducido un numero intente de nuevo");
                teclado.nextLine();
            }
            switch (opcion){
                case 1 :
                    buscarLibroPorTitulo();
                    break;
                case 2 :
                    listarLibrosRegistrados();
                    break;
                case 3 :
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresPorAnho();
                    break;
                case 5:
                    listarPorIdiomas();
                    break;
                case 0:
                    opcion = 0;
                    break;



            }
        }


    }


    private Datos getDatosPorTitulo() {
        System.out.println("Inserte el titulo del libro que desea buscar");
        var busquedaTitulo = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE+URLEncoder.encode(busquedaTitulo));
        System.out.println(json);
        return convierteDatos.obtenerDatos(json,Datos.class);
    }
    private void buscarLibroPorTitulo(){
        var datos = getDatosPorTitulo();
        //System.out.println(datos);
        List<DatosLibro> datosLibro = datos.resultados();
        System.out.println(datosLibro);
        Optional<Libro> libroEncontrado = datosLibro.stream().map(l -> new Libro(l)).findFirst();
        if(libroEncontrado.isPresent()){
            var libro = libroEncontrado.get();
            var autorLibro = libro.getAutor();
            Optional<Autor> autorExistente = repositorioAutor.findByNombreContainsIgnoreCase(autorLibro.getNombre());
            if(autorExistente.isPresent()){
                autorLibro = autorExistente.get();
            }else{
                repositorioAutor.save(autorLibro);
            }
            Optional<Libro> libroExistente = repositorioLibro.findByTituloContainsIgnoreCase(libro.getTitulo());
            if (libroExistente.isPresent()){
                System.out.println("Ya existe un libro con el titulo " + libroExistente.get().getTitulo());
            }else{
                autorLibro.addLibro(libro);
                repositorioLibro.save(libro);
                System.out.println(libro);
            }

        }else{
            System.out.println("No se encontraron resultados para la entrada solicitada");
        }
    }
    private void listarLibrosRegistrados() {
        listaLibros = repositorioLibro.findAll();
        listaLibros.forEach(System.out::println);

    }
    private void listarAutoresRegistrados() {
        listaAutores = repositorioAutor.findAll();
        listaAutores.forEach(a -> {
            String titulosLibros = a.getLibros().stream()
                    .map(Libro::getTitulo)
                    .reduce("", (acc, titulo) -> acc + titulo + " , ");

            System.out.printf("****************** \n Nombre del autor : %s \n Año de Nacimiento : %s \n Año de Muerte : %s \n Libros : %s \n ******************\n",
                    a.getNombre(), a.getAnoNacimiento(), a.getAnoMuerte(), titulosLibros);
        });
        //listaAutores.forEach(a -> System.out.printf("****************** \n Nombre del autor : %s \n Año de Nacimiento : %s \n Año de Muerte : %s \n ******************\n",a.getNombre(),a.getAnoNacimiento(),a.getAnoMuerte()));
    }

    private void listarAutoresPorAnho() {
        System.out.println("Ingrese el año que desea los autores vivos : ");
        try{
            var anoEspecifico = teclado.nextInt();

        List<Autor> autoresVivos = repositorioAutor.encontrarVivos(anoEspecifico);
        if(autoresVivos.isEmpty()){
            System.out.println("No se encontraron autores en el ano " + anoEspecifico);
        }else {
            autoresVivos.forEach(a -> System.out.printf("****************** \n Nombre del autor : %s \n Año de Nacimiento : %s \n Año de Muerte : %s \n ******************\n", a.getNombre(), a.getAnoNacimiento(), a.getAnoMuerte()));
        }
        }catch (InputMismatchException e){
            System.out.printf("No ha introducido un año,\n");
            teclado.nextLine();
        }
    }

    private void listarPorIdiomas() {
        String mensaje =
                """ 
                Ingrese el idioma para buscar los libros
                en - Ingles
                es - Español
                pt - Portugues
                fr - Frances
                """;
        System.out.println(mensaje);
        var idioma = teclado.nextLine();
        List<Libro> librosEcontrados = repositorioLibro.findByIdiomaContainsIgnoreCase(idioma);
        if (librosEcontrados.isEmpty()){
            System.out.println("No se encontraron libros en ese idioma o con esa sigla");
        }else{
            librosEcontrados.forEach(System.out::println);
        }
    }

}
