package com.hszg.backend.service;

import com.hszg.backend.api.error.PrivilegeDoesNotExistException;
import com.hszg.backend.data.model.Privilege;
import com.hszg.backend.repos.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    @Autowired
    public PrivilegeService(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    public List<Privilege> getAllPrivileges() {
        return privilegeRepository.findAll();
    }

    public Privilege getPivilegeByName(String name) {

        var privilege = privilegeRepository.findByName(name);
        if (privilege.isEmpty()) {
            throw new PrivilegeDoesNotExistException(name);
        }

        return privilege.get();

    }

    public Privilege getPrivilegeById(Long id) {

        var privilege = privilegeRepository.findById(id);
        if (privilege.isEmpty()) {
            throw new PrivilegeDoesNotExistException(id);
        }

        return privilege.get();

    }

}
