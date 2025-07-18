import org.apache.camel.builder.RouteBuilder;
import io.qdrant.client.grpc.Collections;
import org.apache.camel.component.qdrant.Qdrant;
import org.apache.camel.component.qdrant.QdrantAction;
import org.apache.camel.spi.DataType;

public class CreateEmbeddingsCollection extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer://runOnce?repeatCount=1")
                .log("Creating collection")
                .setHeader(Qdrant.Headers.ACTION)
                .constant(QdrantAction.CREATE_COLLECTION)
                .setBody()
                    .constant(
                        Collections.VectorParams.newBuilder()
                                .setSize(384)
                                .setDistance(Collections.Distance.Cosine).build())
                .to("qdrant:embeddings")
                .log("Collection embeddings created");
    }
}