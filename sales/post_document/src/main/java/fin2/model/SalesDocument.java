package fin2.model;

import fin2.serdes.SalesDocumentSerdes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.List;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class SalesDocument {
    long salesDocumentId;
    String documentDate;
    String customerId;
    long salesOrganisationId;
    String companyCode;
    List<SalesDocumentItem> items;
}
