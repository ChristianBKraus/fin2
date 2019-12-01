package fin2.sales.post;

import fin2.model.HasHeader;
import fin2.model.HasItems;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;

import java.util.ArrayList;
import java.util.function.Function;


class ItemCollector<H extends HasItems<I>,I extends HasHeader<H>> {

    public KStream<String, H> collect(KStream<String, I> items,
                                      Function<H,String> getId,
                                      String store)
    {
        return items
                .selectKey( (k,v) -> getId.apply( v.accessHeader() ))
                // group by already defined key
                .groupByKey()
                // aggregate by using header of first item and add item
                .aggregate(() -> (H) null, (key, item, header) -> {
                    if (header == null) {
                        header = item.accessHeader();
                        header.setItems(new ArrayList<>());
                    }
                    header.addItem(item);
                    return header;
                }, Materialized.as(store))
                // convert to stream
                .toStream()
                // but only pass on when all items added
                .filter((k, v) -> v.getItems().size() == v.getNumberOfItems());
    }
}
