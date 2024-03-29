package fin2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class SalesOrganisation {
    @Id
    String salesOrganisationId;
    String name;
    String companyCode;

}
