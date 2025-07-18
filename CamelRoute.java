import org.apache.camel.builder.RouteBuilder;

public class CamelRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("ftp:admin@localhost:2221/myTestDirectory?password=admin&noop=true")
                .to("seda:kafka")
                .setHeader("CamelAwsS3Key", simple("${headers.CamelFileName}"))
                .log("Uploading file ${headers.CamelFileName} to S3 with content ${body}")
                .to("jms:myQueue")
                .to("aws2-s3:my-bucket" +
                        "?accessKey=accesskey" +
                        "&secretKey=secretkey" +
                        "&region=us-east-1" +
                        "&overrideEndpoint=true" +
                        "&uriEndpointOverride=http://localhost:4566" +
                        "&forcePathStyle=true" +
                        "&autoCreateBucket=true");

        from("seda:kafka")
                .setBody(simple("Uploading ${headers.CamelFileNameOnly} with content ${body}"))
                .log("Sending event to Kafka Topic")
                .to("kafka:myTopic?brokers=localhost:9092");
    }
}