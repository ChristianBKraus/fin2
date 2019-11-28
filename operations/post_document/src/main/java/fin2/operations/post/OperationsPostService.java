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

                // Sales
                .customerId(salesDoc.getCustomerId())
                .companyCode(salesDoc.getCompanyCode())

                // Operations
                .lineType(LineItemType.Customer.toString())

                .build();
        items.add(customer_item);
        long index = 1;
        long amount = 0;
        for (SalesDocumentItem salesItem : salesDoc.getItems()) {
            index++;
            items.add( OperationsDocumentItem.builder()
                    .operationsDocumentLine(line(index))

                    // Sales
                    .salesDocumentLine(salesItem.getSalesDocumentLine())
                    .product(salesItem.getProduct())
                    .salesOrganisationId(salesDoc.getSalesOrganisationId())
                    .companyCode(salesDoc.getCompanyCode())
                    .amount((salesItem.getAmount()))

                    // Operations
                    .lineType(LineItemType.Cost.toString())

                    .build() );
            amount += salesItem.getAmount();
        }
        customer_item.setAmount(amount);

        return OperationsDocument.builder()
                .operationsDocumentId(nextId())

                // Sales
                .salesDocumentId(salesDoc.getSalesDocumentId())
                .documentDate(salesDoc.getDocumentDate())
                .currency(salesDoc.getCurrency())

                // Operations
                .documentType(DocumentType.SalesOrder.toString())
                .postingDate((salesDoc.getDocumentDate()))

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

                                // Header
                                .operationsDocumentId(doc.getOperationsDocumentId())
                                .documentType(doc.getDocumentType())
                                .documentDate(doc.getDocumentDate())
                                .postingDate(doc.getPostingDate())
                                .currency(doc.getCurrency())

                                // Item
                                .operationsDocumentLine(item.getOperationsDocumentLine())
                                .lineType(item.getLineType())
                                .customerId(item.getCustomerId())
                                .amount(item.getAmount())

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
