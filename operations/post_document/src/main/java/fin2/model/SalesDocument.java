package fin2.model;

import lombok.*;

import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SalesDocument {
    String salesDocumentId;

    String documentDate;
    String customerId;
    String salesOrganisationId;
    String companyCode;
    String currency;

    List<SalesDocumentItem> items;
}
