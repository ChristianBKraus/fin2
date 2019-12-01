package fin2.serdes;

import fin2.model.SalesDocument;
import fin2.model.SalesDocumentItem;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class SalesDocumentItemSerdes
        extends Serdes.WrapperSerde<SalesDocumentItem>
        {
    public SalesDocumentItemSerdes() { super( new JsonSerializer<>(), new JsonDeserializer<>(SalesDocumentItem.class)); }
}
