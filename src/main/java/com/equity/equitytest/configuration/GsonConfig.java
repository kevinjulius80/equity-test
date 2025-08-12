package com.equity.equitytest.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GsonConfig {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting() // Mengaktifkan pretty-print
                .disableHtmlEscaping() // Menghindari encoding karakter khusus seperti ">" menjadi "\u003e"
                .create();
    }
}
