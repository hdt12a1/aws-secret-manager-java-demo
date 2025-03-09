package com.example.awsdemo;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

public class SecretsManagerDemo {

    public static void main(String[] args) {
        System.out.println("AWS Secrets Manager Demo");
        try {
            String secret = getSecret();
            System.out.println("Successfully retrieved the secret");
            // For security reasons, we're not printing the actual secret value
            System.out.println("Secret length: " + secret.length());
        } catch (Exception e) {
            System.err.println("Error retrieving secret: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String getSecret() {
        String secretName = "service/test/infra/iduck";
        Region region = Region.of("ap-southeast-1");

        // Create a Secrets Manager client
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

        String secret = getSecretValueResponse.secretString();
        
        return secret;
    }
}
