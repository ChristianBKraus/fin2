package fin2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class SalesDocument {
    long salesDocumentId;
    String documentDate;
    String customerId;
    List<SalesDocumentItem> items;
}
