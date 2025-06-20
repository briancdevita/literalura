package com.alura.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IJsonConverter {

    <T> T obtenerDatos(String json, Class<T> clase) throws JsonProcessingException;
}
