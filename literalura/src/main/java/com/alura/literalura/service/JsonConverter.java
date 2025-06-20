package com.alura.literalura.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter  implements  IJsonConverter{
    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) throws JsonProcessingException {
        return objectMapper.readValue(json, clase);
    }
}
