package fin2.masterdata.businesspartner;

import fin2.model.*;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BusinessPartnerProcessor {
    @Bean
    public Function<KStream<String, String>, KStream<String, BusinessPartner>> process() {

        return input -> input
                .peek( (key,value) -> System.out.println( value ))

                .map( (key, value) -> {

                    BusinessPartner partner = BusinessPartner.builder()
                            .id(nextId())
                            .name(value)
                            .build();

                        return getKeyValue(partner);
                    } )

                .peek( (key,value) -> System.out.println( "-- " + value ));
    }

    private static long nextId;
    private String nextId() {
        nextId++;
        return String.format("%d",nextId);
    }
    private KeyValue<String,BusinessPartner> getKeyValue(BusinessPartner partner) {
        return new KeyValue<>(partner.getId(),partner);
    }

}
