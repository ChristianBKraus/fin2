package fin2.masterdata.costcenter;

import fin2.model.CostCenter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CostCenterRepo extends MongoRepository<CostCenter,String> {
}
