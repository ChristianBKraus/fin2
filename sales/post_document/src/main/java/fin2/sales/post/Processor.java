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
@EnableBinding(ISalesPostProcessor.class)
public class Processor {

    @Autowired ProcessorService service;

    @StreamListener
    @SendTo(ISalesPostProcessor.SALES_DOCUMENT)
    public KStream<String,SalesDocument> process(
            @Input(ISalesPostProcessor.INPUT) KStream<String,String> input,
            @Input(ISalesPostProcessor.SALES_ORGANISATION) KTable<String,SalesOrganisation> organisation ){

            return input
                .peek( (k,v) -> print("-0-"+v) )

            // Create SalesOrder from ProductString
                .mapValues( (k,v) -> service.create(v) )
                .selectKey( (k,v) -> v.getSalesOrganisationId() )
                .peek( (k,v) -> print("-1-"+v) )

            // Enrich with SalesOrganization Data
                .join(organisation, (doc,org) ->
                        service.enrichBy(doc,org)
                        ,Joined.with(Serdes.String(), new SalesDocumentSerdes(), new SalesOrganisationSerdes() )
                )
                .selectKey( (k,v) -> v.getSalesDocumentId() )

                .peek( (k,v) -> print("-2-"+v) );
    }

    private void print(String s) {
        System.out.println(s);
    }

}

interface ISalesPostProcessor {
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
