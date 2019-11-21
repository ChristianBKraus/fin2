package fin2.sales.post;

import fin2.model.SalesDocument;
import fin2.model.SalesDocumentItem;
import fin2.model.SalesOrganisation;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
class ProcessorService {

    private static long nextId = 0L;
    private long nextId() {
        nextId++;
        return nextId;
    }

    SalesDocument create(String input) {
        List<SalesDocumentItem> items = new ArrayList<>();
        items.add( SalesDocumentItem.builder()
                .salesDocumentLine(1)
                .product(input)
                .amount(100L)
                .build() );
        return SalesDocument.builder()
                .salesDocumentId(nextId())
                .documentDate(Instant.now().toString().substring(0,10))
                .customerId("Customer")
                .salesOrganisationId(1L)
                .items(items)
                .build();
    }

    SalesDocument enrichBy(SalesDocument doc, SalesOrganisation org) {
        doc.setCompanyCode(org.getCompanyCode());
        return doc;
    }

}
