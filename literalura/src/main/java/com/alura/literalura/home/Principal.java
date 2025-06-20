package com.alura.literalura.home;
import com.alura.literalura.Repository.LibroRepository;
import com.alura.literalura.model.DatosLibro;
import com.alura.literalura.model.DatosResponse;
import com.alura.literalura.model.Libro;
import com.alura.literalura.service.ApiClient;
import com.alura.literalura.service.JsonConverter;
import com.alura.literalura.service.LibroService;
import com.fasterxml.jackson.core.JsonProcessingException;


import java.util.*;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books/?";
    private  ApiClient apiClient = new ApiClient();
    private JsonConverter jsonConverter = new JsonConverter();


    private LibroRepository libroRepository;

    public Principal(LibroRepository libroRepository, LibroService libroService) {
        this.libroRepository = libroRepository;
        this.libroService = libroService;
    }

    private LibroService libroService;


    public void mostrarMenu() throws JsonProcessingException {

        while (true) {
            System.out.println("Bienvenido a Literalura");
            System.out.println("1.Buscar libro por titulo");
            System.out.println("2.Listar libros registrados");
            System.out.println("3.Listar autores registrados.");
            System.out.println("4.Listar autores vivos en un determinado año.");
            System.out.println("5.Listar libros por idioma.");
            System.out.println("0.Salir.");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    obtenerTodosLosLibros();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresDeterminadoAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    return;
                default:
                    return;
            }
        }

    }

    public void buscarLibroPorTitulo() throws  JsonProcessingException {
        System.out.println("1. Por favor ingrese el nombre del libro que desea buscar");
        var nombreLibro = scanner.nextLine();

        List<Libro> librosEnDb = libroRepository.findByTituloIgnoreCase(nombreLibro);


        if (!librosEnDb.isEmpty()) {
            System.out.println("El libro ya esta en la base de datos:");
            librosEnDb.forEach(libro -> {
                System.out.println("------------------- RESULTADO-----------------------");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor().getNombre());
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Descargas: " + libro.getCantidadDescargas());
                System.out.println("--------------------------------------------------");
            });
            return;
        }

        var json = apiClient.obtenerDatos(URL_BASE + "search=" + nombreLibro.replace(" ", "+"));
        var libros = jsonConverter.obtenerDatos(json, DatosResponse.class);

        Optional<DatosLibro> libroExacto = libros.results().stream()
                .filter(libro -> libro.titulo().equalsIgnoreCase(nombreLibro))
                .findFirst();

        if (libroExacto.isPresent()) {
            DatosLibro libro = libroExacto.get();
            System.out.println("Título: " + libro.titulo());
            System.out.println("Autor: " + libro.autores().get(0));
            System.out.println("Idioma: " + libro.idiomas().get(0));
            System.out.println("Descargas: " + libro.cantidadDescargas());
            System.out.println("--------------------------------------------------");

            libroService.guardarLibroDesdeJosn(libro);
            System.out.println("Libro guardado en la base de datos correctamente.");

        } else {
            System.out.println("No se encontro ningun libro con ese titulo exacto.");
        }
    }

    public void obtenerTodosLosLibros() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
        } else {
            System.out.println("Libros registrados:");
            libros.forEach(libro -> {
                System.out.println("------------------- RESULTADO-----------------------");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor().getNombre());
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Descargas: " + libro.getCantidadDescargas());
                System.out.println("--------------------------------------------------");
            });
        }
    }

    public void listarAutoresRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
        } else {
            Set<String> autoresUnicos = new HashSet<>();
            libros.forEach(libro -> {
                String nombreAutor = libro.getAutor().getNombre();
                if (autoresUnicos.add(nombreAutor)) {
                    System.out.println("------------------- RESULTADO-----------------------");
                    System.out.println("Autor: " + nombreAutor);
                    System.out.println("--------------------------------------------------");
                }
            });
        }
    }

    public void listarAutoresDeterminadoAnio() {
        System.out.println("Por favor ingrese el año que desea consultar");
        Integer anio = scanner.nextInt();
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
        } else {
            Set<String> autoresUnicos = new HashSet<>();
            libros.forEach(libro -> {
                String nombreAutor = libro.getAutor().getNombre();
                int anioNacimiento = libro.getAutor().getAnioNacimiento();
                int anioFallecimiento = libro.getAutor().getAnioFallecimiento();

                if (anioNacimiento <= anio && (anioFallecimiento == 0 || anioFallecimiento >= anio)) {
                    if (autoresUnicos.add(nombreAutor)) {
                        System.out.println("------------------- RESULTADO-----------------------");
                        System.out.println("Autor: " + nombreAutor);
                        System.out.println("Año de nacimiento: " + anioNacimiento);
                        System.out.println("Año de fallecimiento: " + (anioFallecimiento == 0 ? "Vivo" : anioFallecimiento));
                        System.out.println("--------------------------------------------------");
                    }
                }
            });
        }
    }

    public void listarLibrosPorIdioma() throws JsonProcessingException {
        System.out.println("Por favor ingrese el idioma que desea consultar");
        String idioma = scanner.nextLine();
        List<Libro> libros = libroRepository.findByIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en el idioma " + idioma + ".");
        } else {
            System.out.println("Libros en el idioma " + idioma + ":");
            libros.forEach(libro -> {
                System.out.println("------------------- RESULTADO-----------------------");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor().getNombre());
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Descargas: " + libro.getCantidadDescargas());
                System.out.println("--------------------------------------------------");
            });
        }
    }

    public void mostrarAutores() throws JsonProcessingException {
        System.out.println("Por favor ingrese el nombre del autor que desea buscar");
        var nombreAutor = scanner.nextLine();
        var json = apiClient.obtenerDatos(URL_BASE + "search=" + nombreAutor.replace(" ", "+"));
        var libros = jsonConverter.obtenerDatos(json, DatosResponse.class);


        Set<String> autoresUnicos = new HashSet<>();

        libros.results().forEach(libro -> {
            libro.autores().forEach(autor -> {
                String claveAutor = autor.nombreAutor();
                if (autoresUnicos.add(claveAutor)) {
                    System.out.println("Autor: " + autor.nombreAutor());
                    System.out.println("Nacimiento: " + autor.anioNacimiento());
                    System.out.println("Fallecimiento: " + autor.anioFallecimiento());
                    System.out.println("--------------------------------------------------");
                }
            });
        });
    }


}
