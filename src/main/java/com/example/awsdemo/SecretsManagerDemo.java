package com.example.awsdemo;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Iterator;
import java.util.Map;

public class SecretsManagerDemo {

    public static void main(String[] args) {
        System.out.println("AWS Secrets Manager Demo");
        try {
            getSecret(); // The method now prints the secret details
            System.out.println("Successfully retrieved the secret");
        } catch (Exception e) {
            System.err.println("Error retrieving secret: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String getSecret() {
        String secretName = "service/test/infra/iduck";
        Region region = Region.of("ap-southeast-1");

        // Create a Secrets Manager client using default credentials provider chain
        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(region)
                .build();

        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse getSecretValueResponse;

        try {
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            // For a list of exceptions thrown, see
            // https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
            throw e;
        }

        String secretString = getSecretValueResponse.secretString();
        
        // Print all keys and values from the secret JSON
        try {
            System.out.println("\nSecret contents:");
            System.out.println("---------------");
            
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(secretString);
            
            // Iterate through all fields in the JSON
            Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                System.out.println(field.getKey() + ": " + field.getValue().asText());
            }
            System.out.println("---------------\n");
        } catch (Exception e) {
            System.err.println("Error parsing secret JSON: " + e.getMessage());
            // If it's not valid JSON, just print the raw string
            System.out.println("Raw secret value: " + secretString);
        }
        
        return secretString;
    }
}
