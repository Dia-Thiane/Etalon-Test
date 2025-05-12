package com.atos.etalonTest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/")
    public String mainEndpoint() {
        return "Appsolutely perfect";
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
