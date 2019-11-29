package fin2.masterdata.material;

import fin2.model.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class MaterialService {
    private static long nextId;
    private String nextId() {
        nextId++;
        return String.format("%d",nextId);
    }

    @Autowired
    MaterialRepo repo;


    Material post(Material material)  {

        material.setId(nextId());
        material = repo.save(material);

        return material;
    }
}
