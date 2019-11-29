package fin2.masterdata.costcenter;

import fin2.model.*;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CostCenterProcessor {
    @Autowired
    CostCenterService service;

    @Bean
    public Function<KStream<String, String>, KStream<String, CostCenter>> processCostCenter() {

        return input -> input
                .peek( (k,v) -> print("-2-" + v))

                .mapValues( (k,v) -> {
                    try {
                        return service.post(CostCenter.builder()
                                .name(v)
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
