package com.hszg.backend.service;

import com.hszg.backend.api.error.IdentifierNotFoundException;
import com.hszg.backend.data.model.Identifier;
import com.hszg.backend.repos.IdentifierRepository;
import com.hszg.backend.repos.ObjectRepository;
import com.hszg.backend.repos.YearRepository;
import com.hszg.backend.service.edit.IdentifierPropertiesEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

@Service
public class IdentifierService {

    private final IdentifierRepository identifierRepository;
    private final ObjectRepository objectRepository;
    private final YearRepository yearRepository;
    private final FileService fileService;
    private final ObjectService objectService;
    private final YearService yearService;

    @Autowired
    public IdentifierService(IdentifierRepository identifierRepository, ObjectRepository objectRepository, YearRepository yearRepository, FileService fileService, ObjectService objectService, YearService yearService) {
        this.identifierRepository = identifierRepository;
        this.objectRepository = objectRepository;
        this.yearRepository = yearRepository;
        this.fileService = fileService;
        this.objectService = objectService;
        this.yearService = yearService;
    }

    public List<Identifier> getIdentifiers() {
        return identifierRepository.findAll();
    }

    public Identifier getIdentifierById(Long id) {
        return checkIdentifierExistence(id);
    }

    @Transactional
    public Identifier createIdentifier(Identifier identifier) {
        return identifierRepository.save(identifier);
    }

    @Transactional
    public void deleteIdentifier(Long id) {
        Optional<Identifier> optionalIdentifier = identifierRepository.findById(id);
        if (optionalIdentifier.isPresent()) {
            var path = optionalIdentifier.get().getUrl() + "/" + optionalIdentifier.get().getFilename();
            fileService.deleteFile(path);

            var objects = objectService.findObjectsByIdentifierId(id);
            for (com.hszg.backend.data.model.Object object : objects) {
                object.setIdentifierId(null);
                object.setIdentifierResult(null);
                objectRepository.save(object);
            }

            var years = yearService.findYearsByIdentifierId(id);
            for (com.hszg.backend.data.model.Year year : years) {
                year.setIdentifierId(null);
                year.setIdentifierResult(null);
                yearRepository.save(year);
            }

            identifierRepository.deleteById(id);
        }
    }

    @Transactional
    public Identifier editIdentifier(Long id, IdentifierPropertiesEdit changes) {
        var identifier = getIdentifierById(id);
        changes.applyChanges(identifier);
        return identifierRepository.save(identifier);
    }

    @Transactional
    public Identifier uploadIdentifier(MultipartFile file, String name) {

        Identifier identifier;
        Optional<Identifier> optionalIdentifier = identifierRepository.findByIdentifierName(name);
        if (optionalIdentifier.isEmpty()) {
            identifier = new Identifier();
            identifier.setIdentifierName(name);
        } else {
            identifier = optionalIdentifier.get();
        }

        identifier.setFilename(file.getOriginalFilename());

        try {
            var path = fileService.save(file);
            identifier.setUrl(path);
            return identifierRepository.save(identifier);
        } catch (Exception e) {
            throw new RuntimeException("Upload failed");
        }

    }

    public String getFile(Long id) throws IOException {

        var identifier = identifierRepository.getById(id);
        var file = fileService.loadFromObject(identifier);
        var inputStream = file.getInputStream();
        var isReader = new InputStreamReader(inputStream);
        var reader = new BufferedReader(isReader);

        StringBuilder sb = new StringBuilder();
        String str;

        while ((str = reader.readLine()) != null) {
            sb.append(str);
        }

        return sb.toString();

    }

    public Identifier getIdentifierByName(String name) {
        return checkIdentifierExistence(name);
    }

    public Identifier checkIdentifierExistence(Long id) {
        Optional<Identifier> optionalIdentifier = identifierRepository.findById(id);
        if (optionalIdentifier.isEmpty()) {
            throw new IdentifierNotFoundException("Identifier with Name: " + id + " not found.");
        }

        return optionalIdentifier.get();
    }

    public Identifier checkIdentifierExistence(String name) {
        Optional<Identifier> optionalIdentifier = identifierRepository.findByIdentifierName(name);
        if (optionalIdentifier.isEmpty()) {
            throw new IdentifierNotFoundException("Identifier with Name: " + name + " not found.");
        }

        return optionalIdentifier.get();
    }

}
