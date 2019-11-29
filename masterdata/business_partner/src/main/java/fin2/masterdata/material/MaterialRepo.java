package fin2.masterdata.material;

import fin2.model.*;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MaterialRepo extends MongoRepository<Material,String> {
}
