# Pact

This repository contains the setup for implementing Pact between a Consumer and a Provider.

## Steps to Run Pact in Local Environment

### Prerequisites
1. Install Windows Subsystem for Linux (WSL).
2. Install Docker container in the WSL.
3. Install Postgres in the WSL.
4. Start and check status of Docker
   ```bash
   sudo docker start
   ```
   ```bash
   sudo docker status
   ```
5. Build Docker image.
   ```bash
   sudo service docker build -t Pact-broker.yml .
    ```
6. Compose Docker image.
   ```bash
   sudo docker-compose up
     ```
7. Verify Docker image.
   ```bash
   sude docker ps
     ```
### Consumer Section

#### Step 1: Generate Pact Interaction File
1. Navigate to the `src/test/java/pact` directory.
2. Locate and run the `ConsumerTest` file.
3. The Pact interaction file is created in the `build` folder under the project structure, specifically in `build/pacts/samples`.

### Pact Broker Section

#### Step 2: Publish Pact Interaction File to Pact Broker
1. Check the `Pact-Broker.yml` file in the project root location.
2. Run the following command in the WSL command prompt under project location:
   ```bash
   ***docker compose -f Pact-broker.yml up -d***
   ```
3. If the Docker image is up and running, Pact Broker will be running in `http://localhost:9292/`.
4. run `gradle pactPublish` in cmd prompt.
5. Verify the interaction file get published in Pact Broker or not.

### Provider Section

#### Step 3: Run the Provider class file in the provider package in test module
1. Navigate to `src/test/java/pact` and Click on `ProviderTest` file
2. Run the `ProviderTest` file
3. Check in localhost:9292 for interaction get verified or not.

