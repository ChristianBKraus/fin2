package fin2.configuration.organisation;

import fin2.model.Company;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CompanyProcessor {
    @Autowired
    CompanyService service;

    @Bean
    public Function<KStream<String, String>, KStream<String, Company>> processCompany() {

        return input -> input
                .peek( (k,v) -> print("-0-" + v) )

                .mapValues( (k,v) -> {
                    try {
                        return service.post(
                            Company.builder()
                                .name(v)
                                .companyCode(v)
                                .companyCodeCurrency("EUR")
                                .build() );
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })

                .peek( (k,v) -> print("-1-" + v));
    }

    void print(String s) { System.out.println(s); }

}
