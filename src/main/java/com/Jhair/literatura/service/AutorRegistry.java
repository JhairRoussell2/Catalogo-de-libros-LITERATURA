package com.Jhair.literatura.service;

import com.Jhair.literatura.domain.model.Autor;
import com.Jhair.literatura.domain.model.Libro;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AutorRegistry {

    // Conserva orden de inserción y evita duplicados por nombre
    private final Map<String, Autor> data = new LinkedHashMap<>();

    private String keyFor(String nombre) {
        return Optional.ofNullable(nombre).orElse("").trim().toLowerCase();
    }

    /** Registra/mergea un autor por nombre (si existe, completa años faltantes). */
    public boolean register(Autor autor) {
        if (autor == null) return false;
        String key = keyFor(autor.getNombre());
        if (key.isBlank()) return false;

        Autor existing = data.get(key);
        if (existing == null) {
            data.put(key, autor);
            return true;
        }
        // Merge sencillo: si el nuevo tiene años y el viejo no, complétalos
        if (existing.getAnioNacimiento() == null && autor.getAnioNacimiento() != null) {
            existing.setAnioNacimiento(autor.getAnioNacimiento());
        }
        if (existing.getAnioFallecimiento() == null && autor.getAnioFallecimiento() != null) {
            existing.setAnioFallecimiento(autor.getAnioFallecimiento());
        }
        return false; // ya existía
    }

    /** Registra el autor de un libro (si existe). */
    public boolean registerFromLibro(Libro libro) {
        if (libro == null || libro.getAutor() == null) return false;
        return register(libro.getAutor());
    }

    public List<Autor> findAll() {
        return List.copyOf(data.values());
    }

    /** Autores vivos en 'year' según la regla: nacido <= year y (sin muerte o muerte > year). */
    public List<Autor> findAliveInYear(int year) {
        return data.values().stream()
                .filter(a -> {
                    Integer birth = a.getAnioNacimiento();
                    Integer death = a.getAnioFallecimiento();
                    boolean bornOK = (birth == null) || (birth <= year);
                    boolean deathOK = (death == null) || (death > year); // si murió ese año, NO se considera vivo
                    return bornOK && deathOK;
                })
                .toList();
    }
}
