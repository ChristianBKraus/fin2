package fin2.masterdata.material;

import fin2.model.Material;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MaterialProcessor {
    @Autowired
    MaterialService service;

    @Bean
    public Function<KStream<String, String>, KStream<String, Material>> processMaterial() {

        return input -> input
                .peek( (k,v) -> print("-2-" + v))

                .mapValues( (k,v) -> {
                    try {
                        return service.post(Material.builder()
                                .name(v)
                                .price(10)
                                .currency("EUR")
                                .build());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                } )
                .selectKey((k,v) -> v.getId())

                .peek( (k,v) -> print("-1-" + v));
    }

    void print(String s) { System.out.println(s); }

}
