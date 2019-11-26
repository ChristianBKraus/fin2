package fin2.model;

import lombok.*;
import java.util.List;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class OperationsDocument {
    String operationsDocumentId;

    String documentDate;
    String documentType;

    String salesDocumentId;

    List<OperationsDocumentItem> items;
}
