package fin2.model;

import lombok.*;

import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SalesDocument {
    long salesDocumentId;
    String documentDate;
    String customerId;
    List<SalesDocumentItem> items;
}
