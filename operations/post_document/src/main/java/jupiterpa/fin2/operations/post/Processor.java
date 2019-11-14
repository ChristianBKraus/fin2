package fin2.operations.post;

import fin2.model.OperationsDocument;
import fin2.model.SalesDocument;
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
                .map( (key, value) -> {

                        OperationsDocument doc = mapper.map(value);

                        return new KeyValue<>(doc.getKey(), doc);
                    } )
                .peek( (key,value) -> System.out.println("-- " + value.toString()));
    }


}
