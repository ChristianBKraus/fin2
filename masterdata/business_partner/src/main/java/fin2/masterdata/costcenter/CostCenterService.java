package fin2.masterdata.costcenter;

import fin2.masterdata.businesspartner.*;
import fin2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class CostCenterService {
    private static long nextId;
    private String nextId() {
        nextId++;
        return String.format("%d",nextId);
    }

    @Autowired
    CostCenterRepo repo;


    CostCenter post(CostCenter costcenter)  {

        costcenter.setId(nextId());
        costcenter = repo.save(costcenter);

        return costcenter;
    }
}
