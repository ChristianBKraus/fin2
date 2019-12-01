package fin2.serdes;

import fin2.model.Material;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class MaterialSerdes extends Serdes.WrapperSerde<Material> {
        public MaterialSerdes() { super( new JsonSerializer<>(), new JsonDeserializer<>(Material.class)); }
    }
