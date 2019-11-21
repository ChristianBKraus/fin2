package fin2.sales.post;

import fin2.model.*;
import fin2.serdes.*;
import lombok.var;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.cloud.stream.annotation.*;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(IProcessor.class)
public class Processor {

    @Autowired ProcessorService service;

    @StreamListener
    @SendTo(IProcessor.SALES_DOCUMENT)
    public KStream<String,SalesDocument> process(
            @Input(IProcessor.INPUT) KStream<String,String> input,
            @Input(IProcessor.SALES_ORGANISATION) KTable<String,SalesOrganisation> organisation ){

            return input
                .peek( (k,v) -> print("0",v) )

            // Create SalesOrder from ProductString
                .map( (key, value) -> {
                    var doc = service.create(value);
                    return new KeyValue<>(getKey(doc.getSalesOrganisationId()),doc);
                } )

                .peek( (k,v) -> print("1",v) )

            // Enrich with SalesOrganization Data
                .join(organisation, (doc,org) ->
                        service.enrichBy(doc,org)
                        ,Joined.with(Serdes.String(), new SalesDocumentSerdes(), new SalesOrganisationSerdes() )
                )
                .map( (key,value) -> new KeyValue<>(getKey(value.getSalesDocumentId()), value)  )

                .peek( (k,v) -> print("2",v) );

    }


    private void print(String prefix, Object obj) {
        System.out.println("-"+prefix+"-: " + obj);
    }

    private String getKey(long id) {
        return String.format("%d",id);
    }
}

interface IProcessor {
    String INPUT = "input";
    String SALES_DOCUMENT = "SalesDocument";
    String SALES_ORGANISATION = "SalesOrganisation";

    @Input(INPUT)
    KStream<?,String> inputStream();

    @Input(SALES_ORGANISATION)
    KTable<String,SalesOrganisation> salesOrganisationStream();

    @Output(SALES_DOCUMENT)
    KStream<String,SalesDocument> salesDocumentStream();
}
