package org.example.webhooksolver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.example.webhooksolver.service.WebhookService; // ADD THIS IMPORT

@SpringBootApplication
public class WebhookSolverApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(WebhookSolverApplication.class, args);
        WebhookService webhookService = context.getBean(WebhookService.class);
        webhookService.executeProcess();
    }
}