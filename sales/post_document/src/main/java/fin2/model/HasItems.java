package fin2.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HasItems<I> {
    long numberOfItems;
    List<I> items;

    public void addItem(I item) {
        items.add(item);
        numberOfItems++;
    }
    public void setItems(List<I> items) {
        this.items = items;
        numberOfItems = items.size();
    }
    public List<I> getItems() {
        return items;
    }
}