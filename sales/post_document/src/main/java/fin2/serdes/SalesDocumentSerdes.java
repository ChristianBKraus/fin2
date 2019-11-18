package fin2.serdes;

import fin2.model.SalesDocument;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class SalesDocumentSerdes extends Serdes.WrapperSerde<SalesDocument> {
    public SalesDocumentSerdes() { super( new JsonSerializer<>(), new JsonDeserializer<>(SalesDocument.class)); }
}
