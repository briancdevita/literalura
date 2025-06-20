package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAuthor(
       @JsonAlias("name") String nombreAutor,
       @JsonAlias("birth_year") Integer anioNacimiento,
       @JsonAlias("death_year") Integer anioFallecimiento
) {
}
