package fin2.model;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalesDocumentItem extends HasHeader<SalesDocument> {
    String salesDocumentLine;

    String materialId;

    long quantity;
    String unitOfMeasure;

    long amount;
    String currency;
}
