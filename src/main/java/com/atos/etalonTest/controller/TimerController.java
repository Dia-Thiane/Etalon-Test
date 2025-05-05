package com.atos.etalonTest.controller;


import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class TimerController {

    public TimerController(MeterRegistry registry) {
        Timer timer = registry.timer("Time for operation");
        timer.record(() -> {
            int sum = 0;
            for (int i = 0; i <= 1000; i++) {
                sum += i;
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}


//
//    Vous pouvez utiliser des métriques personnalisées pour surveiller les paramètres importants pour vos charges de travail. Ces métriques sont automatiquement récupérées par Prometheus. Par exemple, vous pouvez utiliser un compteur pour mesurer le nombre de requêtes adressées à l'application ou un minuteur pour mesurer la latence.
//
//    À titre de démonstration, comptons la somme de tous les nombres jusqu'à 1 000 avec un intervalle de 10 ms :
//
//    Exécutez l'application. Dans actuator/prometheus, vous trouverez nos métriques personnalisées indiquant qu'il a fallu 12,1 secondes à l'application pour terminer la tâche.