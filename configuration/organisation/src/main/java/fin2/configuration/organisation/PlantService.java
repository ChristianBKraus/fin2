package fin2.configuration.organisation;

import fin2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class PlantService {
    private static long nextId;
    private String nextId() {
        nextId++;
        return String.format("%d",nextId);
    }

    @Autowired
    PlantRepo repo;

    @Autowired
    CompanyRepo companyRepo;

    Plant post(Plant plant) throws Exception {

        if (repo.findByName(plant.getName()) != null)  {
            throw new Exception("Plant with name does already exist");
        }
        if (! companyRepo.findById(plant.getCompanyCode()).isPresent()) {
            throw new Exception("Company Code does not exist");
        }

        plant.setPlantId(nextId());

        plant = repo.save(plant);

        return plant;

    }
}
