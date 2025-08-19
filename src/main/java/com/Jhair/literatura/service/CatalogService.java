package com.Jhair.literatura.service;

import com.Jhair.literatura.client.GutendexClient;
import com.Jhair.literatura.domain.model.Autor;
import com.Jhair.literatura.domain.model.Libro;
import com.Jhair.literatura.domain.repository.AutorRepository;
import com.Jhair.literatura.domain.repository.LibroRepository;
import com.Jhair.literatura.dto.BookDto;
import com.Jhair.literatura.dto.GutendexResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CatalogService {

    private final GutendexClient client;
    private final GutendexParser parser;
    private final DtoToDomainConverter converter;
    private final AutorRepository autorRepo;
    private final LibroRepository libroRepo;

    public CatalogService(GutendexClient client,
                          GutendexParser parser,
                          DtoToDomainConverter converter,
                          AutorRepository autorRepo,
                          LibroRepository libroRepo) {
        this.client = client;
        this.parser = parser;
        this.converter = converter;
        this.autorRepo = autorRepo;
        this.libroRepo = libroRepo;
    }

    /** Busca en Gutendex (primer resultado), convierte y persiste (autor+libro, evitando duplicados). */
    @Transactional
    public Libro buscarYGuardarPorTitulo(String titulo) {
        String json = client.searchBooksByTitle(titulo);
        GutendexResponse resp = parser.parseBooksResponse(json);

        if (resp.results() == null || resp.results().isEmpty()) {
            throw new RuntimeException("No se encontraron resultados para: " + titulo);
        }

        BookDto dto = resp.results().get(0);
        Libro tmp = converter.toLibro(dto); // modelo temporal (título, autor, idioma, descargas)

        // Autor existente o nuevo
        Autor tmpAutor = tmp.getAutor();
        if (tmpAutor == null || tmpAutor.getNombre() == null || tmpAutor.getNombre().isBlank()) {
            throw new RuntimeException("El libro sin autor no puede persistirse.");
        }

        Autor autor = autorRepo.findByNombreIgnoreCase(tmpAutor.getNombre())
                .map(a -> {
                    if (a.getAnioNacimiento() == null) a.setAnioNacimiento(tmpAutor.getAnioNacimiento());
                    if (a.getAnioFallecimiento() == null) a.setAnioFallecimiento(tmpAutor.getAnioFallecimiento());
                    return a;
                })
                .orElseGet(() -> autorRepo.save(
                        new Autor(tmpAutor.getNombre(), tmpAutor.getAnioNacimiento(), tmpAutor.getAnioFallecimiento())
                ));

        // Evita duplicado por (titulo + autor)
        return libroRepo.findByTituloIgnoreCaseAndAutor_NombreIgnoreCase(tmp.getTitulo(), autor.getNombre())
                .orElseGet(() -> libroRepo.save(new Libro(
                        tmp.getTitulo(), autor, tmp.getIdioma(), tmp.getDescargas()
                )));
    }

    public List<Libro> listarLibros() { return libroRepo.findAll(); }

    public List<Autor> listarAutores() { return autorRepo.findAll(); }

    public List<Libro> listarLibrosPorIdioma(String idioma) { return libroRepo.findByIdiomaIgnoreCase(idioma); }

    public long contarLibrosPorIdioma(String idioma) { return libroRepo.countByIdiomaIgnoreCase(idioma); }

    public List<Autor> autoresVivosEnAnio(int anio) { return autorRepo.findAliveInYear(anio); }
}
