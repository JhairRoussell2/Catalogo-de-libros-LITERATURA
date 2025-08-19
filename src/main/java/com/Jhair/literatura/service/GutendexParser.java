package com.Jhair.literatura.service;

import com.Jhair.literatura.dto.GutendexResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class GutendexParser {
    private final ObjectMapper mapper;

    public GutendexParser() {
        this.mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public GutendexResponse parseBooksResponse(String json) {
        try {
            return mapper.readValue(json, GutendexResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("No se pudo parsear el JSON de Gutendex", e);
        }
    }
}
