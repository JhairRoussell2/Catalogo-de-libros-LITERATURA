package com.Jhair.literatura.application;

import com.Jhair.literatura.domain.model.Autor;
import com.Jhair.literatura.domain.model.Libro;
import com.Jhair.literatura.service.CatalogService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Menu {

    private final CatalogService catalog;
    private final Scanner scanner = new Scanner(System.in);

    public Menu(CatalogService catalog) {
        this.catalog = catalog;
    }

    public void start() {
        int opcion;
        do {
            imprimirMenu();
            opcion = Input.readIntInRange(scanner, "Opción: ", 0, 5);
            try {
                switch (opcion) {
                    case 1 -> opcionBuscarYGuardarLibroPorTitulo();
                    case 2 -> opcionListarLibros();
                    case 3 -> opcionListarAutores();
                    case 4 -> opcionListarAutoresVivosEnAnio();
                    case 5 -> opcionListarLibrosPorIdioma();
                    case 0 -> System.out.println("👋 ¡Hasta luego!");
                }
            } catch (RuntimeException ex) {
                System.out.println("⚠ Error: " + ex.getMessage() + "\n");
            }
            if (opcion != 0) Input.pause(scanner);
        } while (opcion != 0);
    }

    private void imprimirMenu() {
        System.out.println("""
        ================== Literatura ==================
        Elija la opción a través de su número:
        1- buscar libro por título (y guardar)
        2- listar libros registrados
        3- listar autores registrados
        4- listar autores vivos en un determinado año
        5- listar libros por idioma
        0- salir
        ===============================================
        """);
    }

    private void opcionBuscarYGuardarLibroPorTitulo() {
        String titulo = Input.readNonEmptyLine(scanner, "Ingresa el título a buscar: ");
        Libro libro = catalog.buscarYGuardarPorTitulo(titulo);
        System.out.println("\n--- Guardado/Recuperado (BD) ---");
        System.out.println(libro);
    }

    private void opcionListarLibros() {
        List<Libro> libros = catalog.listarLibros();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base.\n");
            return;
        }
        System.out.println("\n=== Libros (BD) ===");
        int i = 1;
        for (Libro l : libros) {
            System.out.println("#" + (i++));
            System.out.println(l);
        }
    }

    private void opcionListarAutores() {
        List<Autor> autores = catalog.listarAutores();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados en la base.\n");
            return;
        }
        System.out.println("\n=== Autores (BD) ===");
        int i = 1;
        for (Autor a : autores) {
            System.out.printf("#%d %s%n", i++, a.toString());
        }
        System.out.println();
    }

    private void opcionListarAutoresVivosEnAnio() {
        int anio = Input.readIntInRange(scanner, "Año (ej. 1900): ", -4000, 3000);
        List<Autor> vivos = catalog.autoresVivosEnAnio(anio);
        if (vivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en " + anio + " (en la base).\n");
            return;
        }
        System.out.println("\n=== Autores vivos en " + anio + " (BD) ===");
        int i = 1;
        for (Autor a : vivos) {
            System.out.printf("#%d %s%n", i++, a.toString());
        }
        System.out.println();
    }

    private void opcionListarLibrosPorIdioma() {
        String code = Input.readNonEmptyLine(scanner, "Código de idioma (ej. es, en, fr, pt): ").toLowerCase();
        List<Libro> libros = catalog.listarLibrosPorIdioma(code);
        long total = catalog.contarLibrosPorIdioma(code);

        if (total == 0) {
            System.out.println("No hay libros en el idioma '" + code + "' (en la base).\n");
            return;
        }

        System.out.println("\n=== Libros en idioma '" + code + "' (" + total + ") (BD) ===");
        int i = 1;
        for (Libro l : libros) {
            System.out.println("#" + (i++));
            System.out.println(l);
        }
    }
}
