package fin2.sales.post;

import fin2.model.Material;
import fin2.model.SalesDocument;
import fin2.model.SalesDocumentItem;
import fin2.model.SalesOrganisation;
import fin2.serdes.MaterialSerdes;
import fin2.serdes.SalesDocumentItemSerdes;
import fin2.serdes.SalesDocumentSerdes;
import fin2.serdes.SalesOrganisationSerdes;
import lombok.var;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

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

            KStream<String,SalesDocumentItem> items = input
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
                .flatMapValues( (k,v) -> v.getItems() )
                .selectKey( (k,v) -> v.getMaterialId() )
                .join(material, (i,m) -> service.enrichBy(i,m)
                        , Joined.with(Serdes.String(), new SalesDocumentItemSerdes(), new MaterialSerdes() )
                );
                // group by Header and aggregate
            var collected =
                    new ItemCollector<SalesDocument,SalesDocumentItem>()
                            .collect(items, SalesDocument::getSalesDocumentId, "SalesPostMaterial");
            return collected
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
