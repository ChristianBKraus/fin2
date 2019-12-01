package fin2.sales.post;

import fin2.model.Material;
import fin2.model.SalesDocument;
import fin2.model.SalesDocumentItem;
import fin2.model.SalesOrganisation;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
class SalesPostService {

    private static long nextId = 0L;
    private String nextId() {
        nextId++;
        return String.format("%d",nextId);
    }
    private String line(long line) {
        return String.format("%d",line);
    }

    SalesDocument create(String input) {
        long quantity = Long.parseLong(input);

        List<SalesDocumentItem> items = new ArrayList<>();
        items.add( SalesDocumentItem.builder()
                .salesDocumentLine(line(1))
                .materialId("1")
                .quantity(quantity)
                .unitOfMeasure("kg")
                .build() );
        SalesDocument header = SalesDocument.builder()
                .salesDocumentId(nextId())
                .documentDate(Instant.now().toString().substring(0,10))
                .customerId("1")
                .salesOrganisationId("1")
                .build();
        header.setItems(items);
        for (SalesDocumentItem item : header.getItems()) {
            item.provideHeader(header);
        }
        return header;
    }

    SalesDocument enrichBy(SalesDocument doc, SalesOrganisation org) {
        doc.setCompanyCode(org.getCompanyCode());
        return doc;
    }

    SalesDocumentItem enrichBy(SalesDocumentItem item, Material mat) {
        item.setCurrency( mat.getCurrency() );
        item.setAmount( item.getQuantity() * mat.getPrice() );
        return item;
    }

}
