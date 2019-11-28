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
    private String nextId() {
        nextId++;
        return String.format("%d",nextId);
    }
    private String line(long line) {
        return String.format("%d",line);
    }

    SalesDocument create(String input) {
        List<SalesDocumentItem> items = new ArrayList<>();
        items.add( SalesDocumentItem.builder()
                .salesDocumentLine(line(1))
                .product(input)
                .amount(100L)
                .currency("EUR")
                .build() );
        return SalesDocument.builder()
                .salesDocumentId(nextId())
                .documentDate(Instant.now().toString().substring(0,10))
                .customerId("Customer")
                .salesOrganisationId("1")
                .items(items)
                .build();
    }

    SalesDocument enrichBy(SalesDocument doc, SalesOrganisation org) {
        doc.setCompanyCode(org.getCompanyCode());
        return doc;
    }

}
