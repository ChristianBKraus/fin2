package fin2.model;

import jupiterpa.util.IKey;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data @Builder
public class SalesDocument implements IKey {
    static long id_index = 1;

    long salesDocumentId;
    String documentDate;
    String customerId;
    List<SalesDocumentItem> items = new ArrayList<>();

    public SalesDocument(long salesDocumentId, String documentDate, String customerId,
            List<SalesDocumentItem> items) {
        this.salesDocumentId = salesDocumentId;
        setId();
        this.documentDate = documentDate;
        this.customerId = customerId;
        this.items = items;
    }

    public SalesDocument() {
        setId();
    }

    private void setId() {
        if (salesDocumentId == 0L)
            this.salesDocumentId = id_index;
        id_index++;
    }

    public String getKey() {
        return String.format("%d",salesDocumentId);
    }

}
