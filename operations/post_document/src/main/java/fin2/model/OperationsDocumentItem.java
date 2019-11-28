package fin2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class OperationsDocumentItem {
    String operationsDocumentLine;

    // Sales
    String salesDocumentLine;
    String product;
    String customerId;
    String salesOrganisationId;
    String companyCode;
    long amount;

    // Operations
    String lineType;

}
