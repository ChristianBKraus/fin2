package fin2.model;

import lombok.*;
import java.util.List;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class OperationsDocument {
    String operationsDocumentId;

    // Sales
    String salesDocumentId;
    String documentDate;
    String currency;

    // Operations
    String documentType;
    String postingDate;

    List<OperationsDocumentItem> items;
}
