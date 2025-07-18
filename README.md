# Camel Project

This project demonstrates the use of Apache Camel with various components.

## How to run

### 1. Start Infrastructure

Start the required infrastructure services using Camel JBang:

```bash
# Start AWS S3
camel infra run aws s3

# Start Kafka
camel infra run kafka

# Start FTP server
camel infra run ftp

# Start ActiveMQ Artemis
camel infra run artemis

# Start Qdrant
camel infra run qdrant
```

### 2. Setup Qdrant Collection

Create the Qdrant collection for embeddings:

```bash
camel run CreateEmbeddingsCollection.java --dependency=camel-qdrant
```

### 3. Generate a Test File

Send a test file to the FTP server:

```bash
camel cmd send --body=file:test.txt --uri='ftp:admin@localhost:2221/myTestDirectory?password=admin'
```

### 4. Run the Camel Routes

Run the main Camel routes:

```bash
# Run the first route
camel run CamelRoute.java application.properties

# Run the second route (queue consumer)
camel run CamelRouteQueueConsumer.java application.properties --dependency=dev.langchain4j:langchain4j-embeddings-all-minilm-l6-v2:0.36.2,camel-qdrant
```
