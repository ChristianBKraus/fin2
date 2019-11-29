package fin2.configuration.organisation;

import fin2.model.*;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public class SalesOrganisationProcessor {
    @Autowired
    SalesOrganisationService service;

    @Bean
    public Function<KStream<String, String>, KStream<String, SalesOrganisation>> processSalesOrganisation() {

        return input -> input
                .peek( (k,v) -> print("-0-" + v) )

                .mapValues( (k,v) -> {
                    try {
                        return service.post(
                            SalesOrganisation.builder()
                                .name(v)
                                .companyCode("0001")
                                .build() );
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .selectKey( (k,v) -> v.getSalesOrganisationId() )

                .peek( (k,v) -> print("-1-" + v));
    }

    void print(String s) { System.out.println(s); }

}
