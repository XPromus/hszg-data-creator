package com.hszg.backend.api;

import com.hszg.backend.data.model.Privilege;
import com.hszg.backend.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/privilege")
public class PrivilegeController {

    private final PrivilegeService privilegeService;

    @Autowired
    public PrivilegeController(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    /**
     * * Method was tested (✔)
     */
    @GetMapping("/all")
    public List<Privilege> getAllPrivileges() {
        return privilegeService.getAllPrivileges();
    }

    /**
     * Change in Frontend
     *  Method was tested (✔)
     */
    @PostMapping("/get/name")
    public Privilege getPrivilegeByName(@RequestBody String name) {
        return privilegeService.getPivilegeByName(name);
    }

    /**
     * * Method was tested (✔)
     */
    @GetMapping("/get/{id}")
    public Privilege getPrivilegeById(@PathVariable Long id) {
        return privilegeService.getPrivilegeById(id);
    }

}
