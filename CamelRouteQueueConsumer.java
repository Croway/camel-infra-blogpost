import org.apache.camel.builder.RouteBuilder;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import org.apache.camel.component.qdrant.Qdrant;
import org.apache.camel.component.qdrant.QdrantAction;
import org.apache.camel.spi.DataType;

public class CamelRouteQueueConsumer extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        getCamelContext().getRegistry().bind("embedding-model", new AllMiniLmL6V2EmbeddingModel());

        from("jms:myQueue")
                // Do complex logic with the body ${body}
                .convertBodyTo(String.class)
                .to("langchain4j-embeddings:test")
                .transform(new DataType("qdrant:embeddings"))
                .setHeader(Qdrant.Headers.ACTION, constant(QdrantAction.UPSERT))
                .to("qdrant:embeddings")
                .log("Embedding inserted successfully");
    }
}