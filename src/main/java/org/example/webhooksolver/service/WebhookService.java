package org.example.webhooksolver.service;

import org.example.webhooksolver.dto.WebhookResponse;
import org.example.webhooksolver.dto.SolutionRequest;
import org.example.webhooksolver.util.SqlProblemSolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService {

    @Autowired
    private RestTemplate restTemplate;

    public void executeProcess() {
        try {

            WebhookResponse webhookResponse = generateWebhook();

            if (webhookResponse != null && webhookResponse.getWebhook() != null && webhookResponse.getAccessToken() != null) {

                String regNo = "22BCE3168";
                String sqlQuery = SqlProblemSolver.solveProblem(regNo);

                submitSolution(webhookResponse.getWebhook(), webhookResponse.getAccessToken(), sqlQuery);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private WebhookResponse generateWebhook() {
        String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"name\": \"John Doe\", \"regNo\": \"REG12347\", \"email\": \"john@example.com\"}";
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<WebhookResponse> response = restTemplate.postForEntity(url, request, WebhookResponse.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void submitSolution(String webhookUrl, String accessToken, String sqlQuery) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);

        SolutionRequest solutionRequest = new SolutionRequest();
        solutionRequest.setFinalQuery(sqlQuery);

        HttpEntity<SolutionRequest> request = new HttpEntity<>(solutionRequest, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(webhookUrl, request, String.class);
            System.out.println("Solution submitted successfully: " + response.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}