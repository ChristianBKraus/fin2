package fin2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class OperationsOpenItem {

    // Header
    String operationsDocumentId;
    String documentType;
    String documentDate;
    String postingDate;
    String currency;

    // Item
    String operationsDocumentLine;
    String lineType;
    String customerId;
    long amount;
}
