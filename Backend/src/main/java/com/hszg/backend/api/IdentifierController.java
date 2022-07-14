package com.hszg.backend.api;

import com.hszg.backend.data.model.Identifier;
import com.hszg.backend.service.IdentifierService;
import com.hszg.backend.service.edit.IdentifierPropertiesEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/identifier")
public class IdentifierController {

    private final IdentifierService identifierService;

    @Autowired
    public IdentifierController(IdentifierService identifierService) {
        this.identifierService = identifierService;
    }

    @GetMapping("/all")
    public List<Identifier> getIdentifiers() {
        return identifierService.getIdentifiers();
    }

    @GetMapping("/id/{id}")
    public Identifier getIdentifierById(@PathVariable Long id) {
        return identifierService.getIdentifierById(id);
    }

    @GetMapping("/name/{name}")
    public Identifier getIdentifierByName(@PathVariable String name) {
        return identifierService.getIdentifierByName(name);
    }

    @PostMapping("/create")
    public Identifier createIdentifier(@RequestBody Identifier identifier) {
        return identifierService.createIdentifier(identifier);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteIdentifier(@PathVariable Long id) {
        identifierService.deleteIdentifier(id);
    }

    @PostMapping("/edit/{id}")
    public Identifier editIdentifier(@PathVariable Long id, @RequestBody IdentifierPropertiesEdit changes) {
        return identifierService.editIdentifier(id, changes);
    }

    @PostMapping("/upload/{name}")
    public Identifier uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String name) {
        return identifierService.uploadIdentifier(file, name);
    }

    @GetMapping("/get/{id}")
    public String getFile(@PathVariable Long id) throws IOException {
        return identifierService.getFile(id);
    }

}
