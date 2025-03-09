# AWS Secrets Manager Java Demo

This is a comprehensive Java project demonstrating how to use AWS Secrets Manager with the AWS SDK for Java v2. This guide provides detailed, step-by-step instructions for setting up, configuring, and running the project.

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Project Setup](#project-setup)
3. [AWS Credentials Setup](#aws-credentials-setup)
4. [Building the Project](#building-the-project)
5. [Running the Application](#running-the-application)
6. [Troubleshooting](#troubleshooting)
7. [Project Structure](#project-structure)
8. [Code Explanation](#code-explanation)

## Prerequisites

Before you begin, ensure you have the following installed on your system:

- **Java Development Kit (JDK)** - Version 11 or later
  - Verify with: `java -version`
  - If not installed, download from [Oracle's website](https://www.oracle.com/java/technologies/javase-downloads.html) or use a package manager

- **Maven** - For dependency management and project building
  - Verify with: `mvn -v`
  - If not installed on macOS, you can install it with Homebrew: `brew install maven`

- **AWS Account** - You'll need an AWS account with appropriate permissions to access Secrets Manager

## Project Setup

Follow these steps to set up the project from scratch:

1. **Create the project directory structure**:
   ```bash
   mkdir -p java-demo/src/main/java/com/example/awsdemo
   cd java-demo
   ```

2. **Create the Maven configuration file (pom.xml)**:
   - Create a file named `pom.xml` in the root directory
   - Add the AWS SDK dependencies and build configuration
   - Configure the Java version and Maven plugins

3. **Create the Java class file**:
   - Create `SecretsManagerDemo.java` in the `src/main/java/com/example/awsdemo` directory
   - Implement the code to interact with AWS Secrets Manager

## AWS Credentials Setup

To access AWS services, you need to configure your credentials. Choose one of these methods:

1. **Using AWS CLI (Recommended for development)**:
   ```bash
   aws configure
   ```
   This will prompt you to enter:
   - AWS Access Key ID
   - AWS Secret Access Key
   - Default region name (e.g., ap-southeast-1)
   - Default output format (e.g., json)

2. **Setting Environment Variables**:
   ```bash
   export AWS_ACCESS_KEY_ID=your_access_key
   export AWS_SECRET_ACCESS_KEY=your_secret_key
   export AWS_REGION=ap-southeast-1
   ```

3. **Manually Editing AWS Credentials File**:
   - Create or edit `~/.aws/credentials`:
     ```
     [default]
     aws_access_key_id=your_access_key
     aws_secret_access_key=your_secret_key
     ```
   - Create or edit `~/.aws/config`:
     ```
     [default]
     region=ap-southeast-1
     ```

4. **Using an Existing AWS Profile**:
   - If you have multiple AWS profiles, you can modify the code to use a specific profile:
     ```java
     // Add this import
     import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;

     // Then modify the client builder
     SecretsManagerClient client = SecretsManagerClient.builder()
             .region(region)
             .credentialsProvider(ProfileCredentialsProvider.create("your-profile-name"))
             .build();
     ```

## Building the Project

Build the project using Maven:

1. **Navigate to the project directory**:
   ```bash
   cd /path/to/java-demo
   ```

2. **Clean and compile the project**:
   ```bash
   mvn clean package
   ```
   This command will:
   - Download all required dependencies
   - Compile the Java code
   - Package the application into a JAR file

## Running the Application

After building the project with `mvn clean package`, you can run the application in several ways:

1. **Using Maven's exec plugin**:
   ```bash
   mvn exec:java
   ```
   This uses the configuration in your pom.xml that specifies the main class to run.

2. **Directly using Java with the JAR file**:
   ```bash
   java -cp target/aws-demo-1.0-SNAPSHOT.jar com.example.awsdemo.SecretsManagerDemo
   ```
   This runs the specific main class from your JAR file.

3. **Using Java with classpath to include all dependencies**:
   ```bash
   java -cp target/aws-demo-1.0-SNAPSHOT.jar:target/dependency/* com.example.awsdemo.SecretsManagerDemo
   ```
   This approach is useful if you need to manually specify dependencies.

4. **Creating an executable JAR** (requires additional configuration in pom.xml):
   If you configure your pom.xml with the maven-shade-plugin or maven-assembly-plugin to create an executable JAR, you can run:
   ```bash
   java -jar target/aws-demo-1.0-SNAPSHOT.jar
   ```

5. **Using an IDE's run configuration**:
   If you're using an IDE like IntelliJ IDEA or Eclipse, you can create and use run configurations to execute your application directly from the IDE.

6. **Running with specific JVM arguments**:
   ```bash
   java -Xmx512m -cp target/aws-demo-1.0-SNAPSHOT.jar com.example.awsdemo.SecretsManagerDemo
   ```
   This allows you to specify JVM arguments like memory settings.

## Troubleshooting

### Common Issues and Solutions

1. **AWS Credentials Not Found**:
   - Error: `Unable to load credentials from any of the providers in the chain`
   - Solution: Ensure AWS credentials are properly configured using one of the methods in the [AWS Credentials Setup](#aws-credentials-setup) section

2. **Secret Not Found**:
   - Error: `Secret with name 'service/test/infra/iduck' not found`
   - Solution: Verify the secret name exists in your AWS account and in the specified region

3. **Permission Issues**:
   - Error: `Access denied when accessing AWS Secrets Manager`
   - Solution: Ensure your AWS user/role has the necessary permissions (e.g., `secretsmanager:GetSecretValue`)

4. **Region Issues**:
   - Error: `The security token included in the request is invalid`
   - Solution: Make sure the region specified in the code matches the region where your secret is stored

## Project Structure

```
java-demo/
├── pom.xml                                              # Maven configuration file
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── example/
│                   └── awsdemo/
│                       └── SecretsManagerDemo.java      # Main application code
└── target/                                              # Compiled code (generated)
    └── aws-demo-1.0-SNAPSHOT.jar                       # Compiled JAR file
```

## Code Explanation

### Main Components

1. **SecretsManagerDemo Class**:
   - Contains the `main` method that serves as the entry point
   - Calls the `getSecret` method and handles any exceptions

2. **getSecret Method**:
   - Creates an AWS Secrets Manager client
   - Builds a request to retrieve a specific secret
   - Executes the request and returns the secret value

### Key AWS SDK Classes Used

- `Region`: Specifies the AWS region to connect to
- `SecretsManagerClient`: Client for interacting with AWS Secrets Manager
- `GetSecretValueRequest`: Request object for retrieving a secret
- `GetSecretValueResponse`: Response object containing the secret value

### Security Considerations

- The code does not print the actual secret value for security reasons
- Always follow AWS security best practices when handling secrets
- Consider using IAM roles instead of access keys in production environments
