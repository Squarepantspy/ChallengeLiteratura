## Desafio Alura Challenge Literatura
En este desafio desarrollamos una aplicacion con Spring Boot y trabajamos con la api de [Gutendex](https://gutendex.com/) , e insertamos los datos de consulta en nuestra base de datos PostgreSQL a traves de JPA y consultamos diversos datos con QueryJPQL o Querys de JPA.  
Las funcionalidades minimas requeridas son: 
- Registrar un libro en la base de datos asi como el Autor relacionado a ella
- Consultar los libros registrados
- Consultar los autores registrados listando los libros que fueron consultados
- Consultar los autores vivos en un determinado año teniendo en cuenta sus atributos de año de Nacimiento y Muerte dados por la API
- Listar libros por idiomas : dado un Idioma a traves del input listar los libros en ese idioma.

obs. Utilizamos variables de entorno para ocultar datos sensibles de la base de datos y manejo de Excepciones en caso de Entradas no compatibles.