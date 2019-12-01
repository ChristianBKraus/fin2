package fin2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class SalesDocumentItem {
    String salesDocumentLine;

    String materialId;

    long quantity;
    String unitOfMeasure;

    long amount;
    String currency;

    SalesDocument header;
}
