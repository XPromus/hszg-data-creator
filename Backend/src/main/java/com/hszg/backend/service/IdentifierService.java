package com.hszg.backend.service;

import com.hszg.backend.data.model.Identifier;
import com.hszg.backend.repos.IdentifierRepository;
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
    private final FileService fileService;

    @Autowired
    public IdentifierService(IdentifierRepository identifierRepository, FileService fileService) {
        this.identifierRepository = identifierRepository;
        this.fileService = fileService;
    }

    public List<Identifier> getIdentifiers() {
        return identifierRepository.findAll();
    }

    public Identifier getIdentifierById(Long id) {
        return identifierRepository.getById(id);
    }

    @Transactional
    public Identifier createIdentifier(Identifier identifier) {
        return identifierRepository.save(identifier);
    }

    public void deleteIdentifier(Long id) {
        Optional<Identifier> optionalIdentifier = identifierRepository.findById(id);
        if (optionalIdentifier.isPresent()) {
            var path = optionalIdentifier.get().getUrl() + "/" + optionalIdentifier.get().getFilename();
            fileService.deleteFile(path);
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

}
