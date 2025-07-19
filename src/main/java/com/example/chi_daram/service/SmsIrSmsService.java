package com.example.chi_daram.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SmsIrSmsService {

    @Value("${smsir.api-key}")
    private String apiKey;

    @Value("${smsir.sender-number}") // Keep this, though often not strictly required for verify API
    private String senderNumber;

    @Value("${smsir.api-url}") // Expected: https://api.sms.ir/v1/send/verify
    private String apiUrl;

    @Value("${smsir.template-id}") // New: Template ID for OTP
    private String templateId;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public SmsIrSmsService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendSms(String toPhoneNumber, String otpCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "text/plain"); // As per documentation screenshot
        headers.set("X-API-KEY", apiKey);

        String formattedPhoneNumber = toPhoneNumber;
        if (formattedPhoneNumber.startsWith("09")) {
            formattedPhoneNumber = toPhoneNumber.substring(1);
        } else if (formattedPhoneNumber.startsWith("+989")) {
            formattedPhoneNumber = toPhoneNumber.substring(3);
        }

        // Construct parameters list for the template
        Map<String, String> parameter = new HashMap<>();
        // IMPORTANT: Changed name to "CODE" to match #CODE# in your template
        parameter.put("name", "CODE"); // This must match the placeholder in your template (e.g., %CODE% or #CODE#)
        parameter.put("value", otpCode);

        List<Map<String, String>> parameters = Collections.singletonList(parameter);

        // Construct the request body for /v1/send/verify
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("mobile", formattedPhoneNumber);
        requestBody.put("templateId", Integer.parseInt(templateId));
        requestBody.put("parameters", parameters);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            String response = restTemplate.postForObject(apiUrl, request, String.class);
            System.out.println("Sms.ir API Raw Response: " + response);

            if (response != null && !response.isEmpty()) {
                JsonNode rootNode = objectMapper.readTree(response);
                int status = rootNode.path("status").asInt(-1);
                String message = rootNode.path("message").asText("No message");

                if (status == 200 && "موفق".equals(message)) {
                    System.out.println("SMS sent successfully via Sms.ir Verify API. Message: " + message);
                } else {
                    System.err.println("SMS sending via Sms.ir Verify API failed. Response Message: " + message + ", Status: " + status);
                    throw new RuntimeException("Failed to send SMS via Sms.ir Verify API. Response: " + response);
                }
            } else {
                System.err.println("Sms.ir API returned an empty response.");
                throw new RuntimeException("Failed to send SMS via Sms.ir: Empty response.");
            }

        } catch (HttpClientErrorException e) {
            String errorBody = e.getResponseBodyAsString();
            System.err.println("HTTP Error sending SMS via Sms.ir to " + toPhoneNumber + ": " + e.getStatusCode() + " - " + errorBody);
            throw new RuntimeException("Failed to send SMS via Sms.ir: " + e.getStatusCode() + " - " + errorBody);
        } catch (Exception e) {
            System.err.println("Error sending SMS via Sms.ir to " + toPhoneNumber + ": " + e.getMessage());
            throw new RuntimeException("Failed to send SMS via Sms.ir: " + e.getMessage());
        }
    }
}
