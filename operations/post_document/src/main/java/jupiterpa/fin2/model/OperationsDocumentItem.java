package fin2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class OperationsDocumentItem {
    long operationsDocumentLine;
    String product;
    String customerId;
    long amount;
}
