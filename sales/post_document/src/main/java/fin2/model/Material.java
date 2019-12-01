package fin2.model;

import lombok.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class Material {
    String id;
    String name;
    long price;
    String currency;
}
