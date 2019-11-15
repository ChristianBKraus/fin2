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

    private static long nextId = 0L;

    @Bean
    public Function<KStream<String, String>, KStream<String, SalesDocument>> process() {

        return input -> input
                .peek( (key,value) -> System.out.println( value ))

                .map( (key, value) -> {

                    List<SalesDocumentItem> items = new ArrayList<>();
                    items.add( SalesDocumentItem.builder()
                                .salesDocumentLine(1)
                                .product(value)
                                .amount(100L)
                                .build() );
                    SalesDocument doc = SalesDocument.builder()
                            .salesDocumentId(nextId())
                            .documentDate(Instant.now().toString().substring(0,10))
                            .customerId("Customer")
                            .items(items)
                            .build();

                        return getKeyValue(doc);
                    } )

                .peek( (key,value) -> System.out.println( "-- " + value ));
    }

    private long nextId() {
        nextId++;
        return nextId;
    }
    private KeyValue<String,SalesDocument> getKeyValue(SalesDocument doc) {
        return new KeyValue<>(String.format("%d",doc.getSalesDocumentId()),doc);
    }

}
