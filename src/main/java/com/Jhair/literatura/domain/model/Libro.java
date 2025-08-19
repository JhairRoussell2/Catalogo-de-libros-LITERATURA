package com.Jhair.literatura.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros", uniqueConstraints = {
        @UniqueConstraint(name = "uk_libro_titulo_autor", columnNames = {"titulo", "autor_id"})
})
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=500)
    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "autor_id", foreignKey = @ForeignKey(name = "fk_libro_autor"))
    private Autor autor;

    @Column(length=10)
    private String idioma;   // ej. es, en

    @Column(name = "descargas")
    private Integer descargas;

    public Libro() {}

    public Libro(String titulo, Autor autor, String idioma, Integer descargas) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.descargas = descargas;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public Integer getDescargas() { return descargas; }
    public void setDescargas(Integer descargas) { this.descargas = descargas; }

    @Override
    public String toString() {
        String t = (titulo != null && !titulo.isBlank()) ? titulo : "N/D";
        String a = (autor != null) ? autor.toString() : "(sin autor)";
        String i = (idioma != null && !idioma.isBlank()) ? idioma : "N/D";
        String d = (descargas != null) ? descargas.toString() : "0";

        return """
               Título    : %s
               Autor     : %s
               Idioma    : %s
               Descargas : %s
               """.formatted(t, a, i, d);
    }
}
