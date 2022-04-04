package com.hszg.backend.api;

import com.hszg.backend.data.model.Object;
import com.hszg.backend.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/object")
public class ObjectController {

    private final ObjectService objectService;

    @Autowired
    public ObjectController(ObjectService objectService) {
        this.objectService = objectService;
    }

    /**
    * * Method was tested (✔)
    */
    @GetMapping("/all")
    public List<Object> getObjects() {
        return objectService.getObjects();
    }

    /**
     * * Method was tested (✔)
     */
    @GetMapping("/id/{id}")
    public Object getObjectById(@PathVariable Long id) {
        return objectService.getObjectById(id);
    }

    /**
     * * Method was tested (✔)
     */
    @PostMapping("/create")
    public Object createObject(@RequestBody Object object) {
        return objectService.createObject(object);
    }

    /**
     * * Method was tested (✔)
     * @param objectId
     */
    @DeleteMapping("/delete/{objectId}")
    public void deleteObject(@PathVariable Long objectId) {
        objectService.deleteObject(objectId);
    }

}
