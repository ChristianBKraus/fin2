package fin2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class OperationsDocumentItem {

    String operationsDocumentLine;

    String lineType;
    String product;
    String customerId;

    long amount;
}
