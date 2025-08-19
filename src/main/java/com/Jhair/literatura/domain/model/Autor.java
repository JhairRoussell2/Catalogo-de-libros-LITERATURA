package com.Jhair.literatura.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autores", uniqueConstraints = {
        @UniqueConstraint(name = "uk_autor_nombre", columnNames = "nombre")
})
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=300)
    private String nombre;

    @Column(name="anio_nacimiento")
    private Integer anioNacimiento;

    @Column(name="anio_fallecimiento")
    private Integer anioFallecimiento;

    public Autor() {}

    public Autor(String nombre, Integer anioNacimiento, Integer anioFallecimiento) {
        this.nombre = nombre;
        this.anioNacimiento = anioNacimiento;
        this.anioFallecimiento = anioFallecimiento;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getAnioNacimiento() { return anioNacimiento; }
    public void setAnioNacimiento(Integer anioNacimiento) { this.anioNacimiento = anioNacimiento; }
    public Integer getAnioFallecimiento() { return anioFallecimiento; }
    public void setAnioFallecimiento(Integer anioFallecimiento) { this.anioFallecimiento = anioFallecimiento; }

    @Override
    public String toString() {
        String n = (nombre != null && !nombre.isBlank()) ? nombre : "N/D";
        String nac = (anioNacimiento != null) ? anioNacimiento.toString() : "?";
        String fall = (anioFallecimiento != null) ? anioFallecimiento.toString() : "?";
        return n + " (" + nac + "–" + fall + ")";
    }
}
