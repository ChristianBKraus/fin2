package fin2.model;

import lombok.*;
import java.util.List;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class OperationsDocument {
    long operationsDocumentId;
    String documentDate;
    long salesDocumentId;
    List<OperationsDocumentItem> items;
}
