package com.atos.etalonTest;

import com.atos.etalonTest.controller.AppController;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.springframework.http.ResponseEntity;


public class AppControllerTest {

    @Test
    void testMainEndpoint() {
    // Mock de la requête HTTP
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getHeader("User-Agent")).thenReturn("JUnit-Test-Agent");

        // Crée une instance du contrôleur
        AppController controller = new AppController();
        // Appelle la méthode
        ResponseEntity<String> response = controller.mainEndpoint(mockRequest);

        // Vérifie que le contenu est correct
        assertThat(response.getBody()).isEqualTo("Appsolutely perfect");
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }
    }


