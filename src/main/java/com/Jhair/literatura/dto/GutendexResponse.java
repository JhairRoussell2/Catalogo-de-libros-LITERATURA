package com.Jhair.literatura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexResponse(
        Integer count,
        String next,
        String previous,
        List<BookDto> results
) {}
