package com.alura.literalura.service;


import com.alura.literalura.Repository.AutorRepository;
import com.alura.literalura.Repository.LibroRepository;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.DatosLibro;
import com.alura.literalura.model.Libro;
import org.springframework.stereotype.Service;


@Service
public class LibroService {


    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;

    public LibroService(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    public void guardarLibroDesdeJosn(DatosLibro datos) {
        System.out.println(datos);
        Autor autor = autorRepository.findByNombre(datos.autores().get(0).nombreAutor());

        if (autor == null) {
            autor = new Autor();
            autor.setNombre(datos.autores().get(0).nombreAutor());
            autor.setAnioNacimiento(datos.autores().get(0).anioNacimiento());
            autor.setAnioFallecimiento(datos.autores().get(0).anioFallecimiento());
            autorRepository.save(autor);
        }


        Libro libro = new Libro();
        libro.setTitulo(datos.titulo());
        libro.setIdioma(datos.idiomas().get(0));
        libro.setCantidadDescargas(datos.cantidadDescargas());
        libro.setAutor(autor);
        libroRepository.save(libro);

    }

    public void listarLibrosPorIdioma(String idioma) {
        libroRepository.findByIdioma(idioma).forEach(libro -> {
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor().getNombre());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Cantidad de descargas: " + libro.getCantidadDescargas());
            System.out.println("-----------------------------");
        });
    }

}
