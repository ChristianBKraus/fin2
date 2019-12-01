package fin2.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class SalesDocument extends HasItems<SalesDocumentItem> {
    String salesDocumentId;

    String documentDate;
    String customerId;
    String salesOrganisationId;
    String companyCode;

}
