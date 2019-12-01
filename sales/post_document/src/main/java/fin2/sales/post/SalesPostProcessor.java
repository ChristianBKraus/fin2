package fin2.sales.post;

import fin2.model.*;
import fin2.serdes.*;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.cloud.stream.annotation.*;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@EnableBinding(ISalesPostProcessor.class)
public class SalesPostProcessor {

    @Autowired
    SalesPostService service;

    @StreamListener
    @SendTo(ISalesPostProcessor.SALES_DOCUMENT)
    public KStream<String,SalesDocument> process(
            @Input(ISalesPostProcessor.INPUT) KStream<String,String> input,
            @Input(ISalesPostProcessor.SALES_ORGANISATION) KTable<String,SalesOrganisation> organisation,
            @Input(ISalesPostProcessor.MATERIAL) KTable<String,Material> material) {

            return input
                .peek( (k,v) -> print("-0-"+v) )

                // Create SalesOrder from ProductString
                .mapValues( (k,v) -> service.create(v) )
                .peek( (k,v) -> print("-1-"+v) )

                // Enrich with SalesOrganization Data
                .selectKey( (k,v) -> v.getSalesOrganisationId() )
                .join(organisation, (doc,org) ->
                        service.enrichBy(doc,org)
                        ,Joined.with(Serdes.String(), new SalesDocumentSerdes(), new SalesOrganisationSerdes() )
                )

                // Enrich every line with Material
                // Transform to Item Level
                .flatMapValues( (k,v) -> v.getItems() )
                // Join with Material
                .selectKey( (k,v) -> v.getMaterialId() )
                .join(material, (i,m) -> service.enrichBy(i,m)
                        , Joined.with(Serdes.String(), new SalesDocumentItemSerdes(), new MaterialSerdes() )
                )
                // group by Header and aggregate
                .selectKey( (k,v) -> v.getHeader().getSalesDocumentId() )

                .groupByKey()
                .aggregate(
                    SalesDocument::new,
                    (key,item,header) -> {
                        if (header.getSalesDocumentId() == null) {
                            header = item.getHeader();
                            header.setItems(new ArrayList<>());
                        }
                        header.addItem(item);
                        return header;
                    },
                    Materialized.as("SalesPostEnrichMaterial") )
                .toStream()
                // only pass when all items processed
                .filter( (k,v) -> v.getItems().size() == v.getNumberOfItems() )

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
    String MATERIAL = "Material";

    @Input(INPUT)
    KStream<?,String> inputStream();

    @Input(SALES_ORGANISATION)
    KTable<String,SalesOrganisation> salesOrganisationStream();

    @Input(MATERIAL)
    KTable<String,Material> materialStream();

    @Output(SALES_DOCUMENT)
    KStream<String,SalesDocument> salesDocumentStream();
}
