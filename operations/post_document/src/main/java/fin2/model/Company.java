package fin2.model;

import lombok.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class Company {
    String companyCode;
    String name;
    String companyCodeCurrency; //TODO
}
