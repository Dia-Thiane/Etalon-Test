package com.atos.etalonTest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;



@RestController
public class AppController {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
    @GetMapping("/")
    public ResponseEntity<String> mainEndpoint(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        logger.info("This is an informative logging message, user agent [{}]", userAgent);
        return ResponseEntity.ok("Appsolutely perfect");
    }

    @GetMapping("/slow")
    public String slowEndpoint() throws InterruptedException {
        Thread.sleep(3000); // Simule un traitement long
        return "Réponse lente";
    }

    @GetMapping("/external")
    public String callExternalService() {
        // Simule un appel à une base de données ou API distante
        return "Réponse d’un service externe";
    }

}
