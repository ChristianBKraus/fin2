package fin2.operations.post;

import fin2.model.*;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static fin2.model.EOperations.*;

@Service
class OperationsPostService {

    OperationsDocument toDocument(SalesDocument salesDoc) {

        List<OperationsDocumentItem> items = new ArrayList<>();
        // Customer Line
        OperationsDocumentItem customer_item = OperationsDocumentItem.builder()
                .operationsDocumentLine(line(1))
                .customerId(salesDoc.getCustomerId())
                .lineType(LineItemType.Customer.toString())
                .build();
        items.add(customer_item);
        long index = 1;
        long amount = 0;
        for (SalesDocumentItem salesItem : salesDoc.getItems()) {
            index++;
            items.add( OperationsDocumentItem.builder()
                    .operationsDocumentLine(line(index))
                    .lineType(LineItemType.Cost.toString())
                    .product(salesItem.getProduct())
                    .amount((salesItem.getAmount()))
                    .build() );
            amount += salesItem.getAmount();
        }
        customer_item.setAmount(amount);

        return OperationsDocument.builder()
                .operationsDocumentId(nextId())
                .documentType(DocumentType.SalesOrder.toString())
                .documentDate(salesDoc.getDocumentDate())
                .salesDocumentId(salesDoc.getSalesDocumentId())
                .currency(salesDoc.getCurrency())
                .items(items)
                .build();

    }

    List<OperationsOpenItem> toOpenItems(OperationsDocument doc) {
        List<OperationsOpenItem> items = new ArrayList<>();
        if (doc.getItems() != null) {
            for (OperationsDocumentItem item : doc.getItems()) {
                if (item.getLineType() != null) {
                    if (item.getLineType().equals(LineItemType.Customer.toString())) {
                        var op = OperationsOpenItem.builder()
                                .operationsDocumentId(doc.getOperationsDocumentId())
                                .operationsDocumentLine(item.getOperationsDocumentLine())
                                .customerId(item.getCustomerId())
                                .amount(item.getAmount())
                                .currency(doc.getCurrency())
                                .build();
                        items.add(op);
                    }
                }
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
