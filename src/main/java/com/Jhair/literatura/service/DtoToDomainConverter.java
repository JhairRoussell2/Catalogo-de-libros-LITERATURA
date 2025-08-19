package com.Jhair.literatura.service;

import com.Jhair.literatura.domain.model.Autor;
import com.Jhair.literatura.domain.model.Libro;
import com.Jhair.literatura.dto.AuthorDto;
import com.Jhair.literatura.dto.BookDto;
import org.springframework.stereotype.Component;

@Component
public class DtoToDomainConverter {

    public Libro toLibro(BookDto dto) {
        if (dto == null) return null;

        Autor autor = null;
        if (dto.authors() != null && !dto.authors().isEmpty()) {
            AuthorDto a = dto.authors().get(0);
            autor = new Autor(a.name(), a.birthYear(), a.deathYear());
        }

        String idioma = (dto.languages() != null && !dto.languages().isEmpty())
                ? dto.languages().get(0)
                : null;

        return new Libro(dto.title(), autor, idioma, dto.downloadCount());
    }
}
