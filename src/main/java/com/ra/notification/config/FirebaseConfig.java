package com.ra.notification.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {
    @Value("${app.firebase.file}")
    private String fbFile = "";

    @Primary
    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(
                        new ClassPathResource("/firebase/" + fbFile).getInputStream()))
                .build();
        if (FirebaseApp.getApps().isEmpty())
            FirebaseApp.initializeApp(options);
        return FirebaseApp.getInstance();
    }
}
