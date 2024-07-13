package com.aluracursos.challengeliteratura;

import com.aluracursos.challengeliteratura.principal.Principal;
import com.aluracursos.challengeliteratura.repository.AutorRepository;
import com.aluracursos.challengeliteratura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeliteraturaApplication implements CommandLineRunner {

	@Autowired //inyeccion de dependencias
	private LibroRepository repositoryLibro;

	@Autowired
	private AutorRepository repositoryAutor;

	public static void main(String[] args) {

		SpringApplication.run(ChallengeliteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositoryLibro, repositoryAutor);
		principal.muestraElMenu();
	}
}
