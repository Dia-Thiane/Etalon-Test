package com.atos.etalonTest;

import com.atos.etalonTest.controller.AppController;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AppControllerTest {

    @Test
    void testMainEndpoint() {
        AppController controller = new AppController();
        String response = controller.mainEndpoint();
        assertThat(response).isEqualTo("Appsolutely perfect");
    }

}
