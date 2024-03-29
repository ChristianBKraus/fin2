package fin2.masterdata.businesspartner;

import fin2.model.*;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BusinessPartnerProcessor {
    @Autowired
    BusinessPartnerService service;

    @Bean
    public Function<KStream<String, String>, KStream<String, BusinessPartner>> processBusinessPartner() {

        return input -> input
                .peek( (k,v) -> print("-2-" + v))

                .mapValues( (k,v) -> {
                    try {
                        return service.post(BusinessPartner.builder()
                                .name(v)
                                .creditRating("0")
                                .paymentTerms(10)
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
