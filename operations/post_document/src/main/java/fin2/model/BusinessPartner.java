package fin2.model;

import lombok.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class BusinessPartner {
    String id;
    String name;
    String creditRating; // TODO
    String paymentTerms; // TODO
}
