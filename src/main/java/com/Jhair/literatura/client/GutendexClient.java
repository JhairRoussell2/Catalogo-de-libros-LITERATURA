package com.Jhair.literatura.client;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Component
public class GutendexClient {
    private static final String BASE_URL = "https://gutendex.com/books/";
    private final HttpClient http = HttpClient.newHttpClient();

    public String searchBooksByTitle(String title) {
        String q = URLEncoder.encode(title, StandardCharsets.UTF_8);
        String url = BASE_URL + "?search=" + q;
        return get(url);
    }

    public String listByLanguage(String langCode) {
        String url = BASE_URL + "?languages=" + URLEncoder.encode(langCode, StandardCharsets.UTF_8);
        return get(url);
    }

    private String get(String url) {
        try {
            HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                    .GET()
                    .header("Accept", "application/json")
                    .header("User-Agent", "LiteraturaApp/1.0")
                    .build();

            HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());

            if (res.statusCode() >= 200 && res.statusCode() < 300) {
                return res.body();
            } else {
                throw new RuntimeException("HTTP " + res.statusCode() + " al llamar " + url);
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error llamando a Gutendex: " + e.getMessage(), e);
        }
    }
}
