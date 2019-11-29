package fin2.masterdata.costcenter;

import fin2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = CostCenterController.PATH)
@RestController
public class CostCenterController {
    static final String PATH = "/api/costcenter";

    @Autowired
    CostCenterRepo repo;
    @Autowired
    CostCenterService service;


    @GetMapping("")
    public List<CostCenter> getAll() {
        return repo.findAll();
    }

    @PostMapping("")
    public CostCenter post(@RequestBody CostCenter costCenter )  {
        return service.post(costCenter);
    }
}
