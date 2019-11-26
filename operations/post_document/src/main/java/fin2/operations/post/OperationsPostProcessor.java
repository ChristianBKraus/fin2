package fin2.operations.post;

import fin2.model.OperationsDocument;
import fin2.model.OperationsOpenItem;
import fin2.model.SalesDocument;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(IOperationsPostProcessor.class)
public class OperationsPostProcessor {

    @Autowired
    private OperationsPostService service;

    @StreamListener
    @SendTo(IOperationsPostProcessor.C_OperationsDocument)
    public KStream<String, OperationsDocument> createDocument(
            @Input(IOperationsPostProcessor.C_SalesDocument) KStream<String,SalesDocument> sales
    ) {

        return sales
                .peek( (k,v) -> print("-0-" + v) )

                // SalesDocument -> OperationsDocument
                .mapValues( (k,v) -> service.toDocument(v) )
                .selectKey( (k,v) -> v.getOperationsDocumentId() )
                .peek( (k,v) -> print("-1-" + v) );

    }

    @StreamListener
    @SendTo(IOperationsPostProcessor.C_OpenItem)
    public KStream<String,OperationsOpenItem> createOpenItem(
            @Input(IOperationsPostProcessor.C_OperationsDocument) KStream<String,OperationsDocument> doc
    ) {
        return doc
                .peek((k,v) -> print("-2-" + v))

                // OperationsDocument -> OperationsOpenItem
                .flatMapValues( (k,v) -> service.toOpenItems(v) )
                .selectKey( (k,v) -> v.getOperationsDocumentId() + v.getOperationsDocumentLine())
                .peek( (k,v) -> print("-3-" + v))
                ;


    }

    private void print(String s) {
        System.out.println(s);
    }
}

interface IOperationsPostProcessor {
    String C_SalesDocument = "SalesDocument";
    String C_OperationsDocument = "OperationsDocument";
    String C_OpenItem = "OpenItem";

    @Input(C_SalesDocument)
    KStream<String,SalesDocument> salesDocumentStream();

    @Output(C_OperationsDocument)
    KStream<String,OperationsDocument> operationsDocumentStream();

    @Output(C_OpenItem)
    KStream<String,OperationsOpenItem> openItemStream();
}
