package fin2.masterdata.material;

import fin2.model.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = MaterialController.PATH)
@RestController
public class MaterialController {
    static final String PATH = "/api/material";

    @Autowired
    MaterialRepo repo;
    @Autowired
    MaterialService service;


    @GetMapping("")
    public List<Material> getAll() {
        return repo.findAll();
    }

    @PostMapping("")
    public Material post(@RequestBody Material material )  {
        return service.post(material);
    }
}
