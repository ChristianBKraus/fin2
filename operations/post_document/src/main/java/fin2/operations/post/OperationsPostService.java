package fin2.operations.post;

import fin2.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class OperationsPostService {

    OperationsDocument toDocument(SalesDocument salesDoc) {

        List<OperationsDocumentItem> items = new ArrayList<>();
        // Customer Line
        OperationsDocumentItem customer_item = OperationsDocumentItem.builder()
                .operationsDocumentLine(line(1))
                .customerId(salesDoc.getCustomerId())
                .build();
        items.add(customer_item);
        long index = 1;
        long amount = 0;
        for (SalesDocumentItem salesItem : salesDoc.getItems()) {
            index++;
            items.add( OperationsDocumentItem.builder()
                    .operationsDocumentLine(line(index))
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

    List<OperationsOpenItem> toOpenItems(OperationsDocument doc) {
        List<OperationsOpenItem> items = new ArrayList<>();
        for (OperationsDocumentItem item : doc.getItems()) {
            if (item.getLineType().equals("Customer")) {
                items.add(OperationsOpenItem.builder().operationsDocumentId(doc.getOperationsDocumentId())
                        .operationsDocumentLine(item.getOperationsDocumentLine()).customerId(item.getCustomerId())
                        .amount(item.getAmount()).currency(item.getCurrency()).build());
            }
        }
        return items;
    }

    private static long nextId = 0L;
    private String nextId() {
        nextId++;
        return String.format("%d",nextId);
    }
    private String line(long line) { return String.format("%d",line); }

}
