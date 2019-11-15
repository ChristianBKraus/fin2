package fin2.configuration.organisation;

import fin2.model.*;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class Processor {

    private static long nextId = 0L;

    @Bean
    public Function<KStream<String, String>, KStream<String, SalesOrganisation>> process() {

        return input -> input
                .peek( (key,value) -> System.out.println( value ))

                .map( (key, value) -> {

                    SalesOrganisation org = SalesOrganisation.builder()
                            .salesOrganisationId(nextId())
                            .name(value)
                            .companyCode("0001")
                            .build();

                        return getKeyValue(org);
                    } )

                .peek( (key,value) -> System.out.println( "-- " + value ));
    }

    private long nextId() {
        nextId++;
        return nextId;
    }
    private KeyValue<String,SalesOrganisation> getKeyValue(SalesOrganisation org) {
        return new KeyValue<>(String.format("%d",org.getSalesOrganisationId()),org);
    }

}
