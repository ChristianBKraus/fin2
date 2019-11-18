package fin2.serdes;

import fin2.model.SalesOrganisation;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class SalesOrganisationSerdes  extends Serdes.WrapperSerde<SalesOrganisation> {
        public SalesOrganisationSerdes() { super( new JsonSerializer<>(), new JsonDeserializer<>(SalesOrganisation.class)); }
    }
