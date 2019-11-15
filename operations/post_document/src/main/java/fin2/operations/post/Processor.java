package fin2.operations.post;

import fin2.model.*;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class Processor {


    @Autowired
    private Mapper mapper;

    @Bean
    public Function<KStream<String, SalesDocument>, KStream<String, OperationsDocument>> process() {

        return input -> input
                .peek( (key,value) -> System.out.println(value.toString()) )
                .map( (key, value) -> getKeyValue( mapper.map(value) ) )
                .peek( (key,value) -> System.out.println("-- " + value.toString()));
    }

    private KeyValue<String,OperationsDocument> getKeyValue(OperationsDocument doc) {
        String key = String.format("%d",doc.getOperationsDocumentId());
        return new KeyValue<>(key,doc);
    }


}
