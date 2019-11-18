package fin2.sales.post;

import fin2.model.SalesDocument;
import fin2.model.SalesDocumentItem;
import fin2.model.SalesOrganisation;
import fin2.serdes.SalesDocumentSerdes;
import fin2.serdes.SalesOrganisationSerdes;
import org.apache.kafka.common.serialization.*;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

@Component
public class Processor {

    private static long nextId = 0L;

    @Bean
    public BiFunction<KStream<String, String>, KTable<String, SalesOrganisation>, KStream<String, SalesDocument>> process() {

        return (input,organisations) -> input
                .peek( (key,value) -> System.out.println( value ))

                // Create SalesOrder from ProductString
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
                            .salesOrganisationId(1L)
                            .items(items)
                            .build();

                        return getKeyValue(doc);
                    } )
                .peek( (key,value) -> System.out.println( "-1- " + value ))

                // Enrich with SalesOrganization Data
                .map( (key,value) -> new KeyValue<>(String.format("%d",value.getSalesOrganisationId()), value)  )
                .join(organisations, (doc,org) -> {
                            doc.setCompanyCode( org.getCompanyCode() );
                            return doc;
                        }
                        ,Joined.with(Serdes.String(), new SalesDocumentSerdes(), new SalesOrganisationSerdes() )
                         )
                .map( (key,value) -> new KeyValue<>(String.format("%d",value.getSalesDocumentId()), value)  )
                .peek( (key,value) -> System.out.println( "-2- " + value ));
    }

    private long nextId() {
        nextId++;
        return nextId;
    }
    private KeyValue<String,SalesDocument> getKeyValue(SalesDocument doc) {
        return new KeyValue<>(String.format("%d",doc.getSalesDocumentId()),doc);
    }

}
