package fin2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class SalesDocument {
    String salesDocumentId;

    String documentDate;
    String customerId;
    String salesOrganisationId;
    String companyCode;

    long numberOfItems;
    List<SalesDocumentItem> items;

    public void addItem(SalesDocumentItem item) {
        items.add(item);
    }
}
