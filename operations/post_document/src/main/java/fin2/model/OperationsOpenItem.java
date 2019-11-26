package fin2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class OperationsOpenItem {

    String operationsDocumentId;
    String operationsDocumentLine;

    String customerId;

    long amount;
    String currency;
}
