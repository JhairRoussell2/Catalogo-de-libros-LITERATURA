package com.Jhair.literatura.service;

import com.Jhair.literatura.domain.model.Autor;
import com.Jhair.literatura.domain.model.Libro;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class LibroRegistry {

    // Usamos LinkedHashMap para conservar el orden de inserción y evitar duplicados
    private final Map<String, Libro> data = new LinkedHashMap<>();

    private String keyFor(Libro l) {
        String t = Optional.ofNullable(l.getTitulo()).orElse("").trim().toLowerCase();
        String a = Optional.ofNullable(l.getAutor())
                .map(Autor::getNombre)
                .orElse("")
                .trim()
                .toLowerCase();
        return t + "::" + a;
    }

    /** Registra si no existe; devuelve true si se agregó, false si ya estaba. */
    public boolean register(Libro libro) {
        if (libro == null) return false;
        String k = keyFor(libro);
        if (k.isBlank()) return false;
        return data.putIfAbsent(k, libro) == null;
    }

    public List<Libro> findAll() {
        return List.copyOf(data.values());
    }

    public List<Libro> findByLanguage(String langCode) {
        if (langCode == null || langCode.isBlank()) return List.of();
        String code = langCode.trim().toLowerCase();
        return data.values().stream()
                .filter(b -> code.equalsIgnoreCase(b.getIdioma()))
                .toList();
    }

    public long countByLanguage(String langCode) {
        return findByLanguage(langCode).size();
    }
}
