package fin2.model;

import jupiterpa.util.IKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data @Builder
public class OperationsDocument implements IKey {
    static long id_index = 1;

    long operationsDocumentId;
    String documentDate;
    long salesDocumentId;
    List<OperationsDocumentItem> items = new ArrayList<>();


    public OperationsDocument() {
        setId();
    }
    public OperationsDocument(long operationsDocumentId, String documentDate, long salesDocumentId, List<OperationsDocumentItem> items) {
        this.operationsDocumentId = operationsDocumentId;
        setId();
        this.documentDate = documentDate;
        this.salesDocumentId = salesDocumentId;
        this.items = items;
    }

    private void setId() {
        if (operationsDocumentId == 0L)
            this.operationsDocumentId = id_index;
        id_index++;
    }

    public String getKey() {
        return String.format("%d",operationsDocumentId);
    }

}
