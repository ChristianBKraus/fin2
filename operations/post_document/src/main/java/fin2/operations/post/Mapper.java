package fin2.operations.post;

import fin2.model.OperationsDocument;
import fin2.model.OperationsDocumentItem;
import fin2.model.SalesDocument;
import fin2.model.SalesDocumentItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class Mapper {
    private static long nextId = 0L;
    private long nextId() {
        nextId++;
        return nextId;
    }

    OperationsDocument map(SalesDocument salesDoc) {

        List<OperationsDocumentItem> items = new ArrayList<>();
        // Customer Line
        OperationsDocumentItem customer_item = OperationsDocumentItem.builder()
                .operationsDocumentLine(1)
                .customerId(salesDoc.getCustomerId())
                .build();
        items.add(customer_item);
        long index = 1;
        long amount = 0;
        for (SalesDocumentItem salesItem : salesDoc.getItems()) {
            index++;
            items.add( OperationsDocumentItem.builder()
                    .operationsDocumentLine(index)
                    .product(salesItem.getProduct())
                    .amount((salesItem.getAmount()))
                    .build() );
            amount += salesItem.getAmount();
        }
        customer_item.setAmount(amount);

        return OperationsDocument.builder()
                .operationsDocumentId(nextId())
                .documentDate(salesDoc.getDocumentDate())
                .salesDocumentId(salesDoc.getSalesDocumentId())
                .items(items)
                .build();

    }
}
