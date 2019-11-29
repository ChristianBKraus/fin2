package fin2.configuration.organisation;

import fin2.model.Plant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlantRepo extends MongoRepository<Plant,String> {
    Plant findByName(String name);
}
