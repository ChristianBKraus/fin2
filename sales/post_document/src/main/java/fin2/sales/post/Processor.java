package fin2.sales.post;

import fin2.model.SalesDocument;
import fin2.model.SalesDocumentItem;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class Processor {

    public static final String INPUT_TOPIC = "input";
    public static final String OUTPUT_TOPIC = "SalesDocument";

    @Bean
    public Function<KStream<String, String>, KStream<String, SalesDocument>> process() {

        return input -> input
                .peek( (key,value) -> System.out.println( value ))

                .map( (key, value) -> {

                    long amount = Long.parseLong(value);
                    List<SalesDocumentItem> items = new ArrayList<>();
                    items.add( SalesDocumentItem.builder()
                                .salesDocumentLine(1)
                                .product("Product")
                                .amount(amount)
                                .build() );
                    SalesDocument doc = SalesDocument.builder()
                            .documentDate(Instant.now().toString().substring(0,10))
                            .customerId("Customer")
                            .items(items)
                            .build();

                        return new KeyValue<>(doc.getKey(), doc);
                    } )

                .peek( (key,value) -> System.out.println( "-- " + value ));
    }

}
