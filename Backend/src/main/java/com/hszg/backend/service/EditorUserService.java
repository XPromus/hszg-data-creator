package com.hszg.backend.service;

import com.hszg.backend.api.error.PrivilegeDoesNotExistException;
import com.hszg.backend.api.error.UserAlreadyExistsException;
import com.hszg.backend.api.error.UserDoesNotExistException;
import com.hszg.backend.data.model.EditorUser;
import com.hszg.backend.repos.PrivilegeRepository;
import com.hszg.backend.repos.EditorUserRepository;
import com.hszg.backend.service.edit.UserPropertiesEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EditorUserService {

    private final EditorUserRepository editorUserRepository;
    private final PrivilegeRepository privilegeRepository;

    @Autowired
    public EditorUserService(EditorUserRepository editorUserRepository, PrivilegeRepository privilegeRepository) {
        this.editorUserRepository = editorUserRepository;
        this.privilegeRepository = privilegeRepository;
    }

    @Transactional
    public EditorUser createUser(EditorUser editorUser) {

        var email = editorUser.getEmail();
        var duplicateUser = editorUserRepository.findByEmail(email);

        if (duplicateUser.isPresent()) {
            throw new UserAlreadyExistsException("The editorUser with the email " + email + " already exists");
        }

        return editorUserRepository.save(editorUser);

    }

    public boolean checkPermission(Long userId, String privilegeName) {

        var user = getUserById(userId);
        var privilege = privilegeRepository.findByName(privilegeName);
        if (privilege.isEmpty()) {
            throw new PrivilegeDoesNotExistException(privilegeName);
        }

        return user.hasPrivilege(privilege.get());

    }

    public EditorUser getUserByEmail(String email) {

        var user = editorUserRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserDoesNotExistException(email);
        }

        return user.get();

    }

    public EditorUser getUserById(Long userId) {

        var user = editorUserRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserDoesNotExistException(userId);
        }

        return user.get();

    }

    @Transactional
    public EditorUser editUser(Long userId, UserPropertiesEdit changes) {
        var user = getUserById(userId);
        changes.applyChanges(user);
        return editorUserRepository.save(user);
    }

    @Transactional
    public EditorUser addPrivilegeToEditorUser(EditorUser editorUser, String privilege) {

        var readPrivilege = privilegeRepository.findByName(privilege);
        if (readPrivilege.isPresent()) {
            editorUser.addPrivilege(readPrivilege.get());
            return editorUserRepository.save(editorUser);
        } else {
            throw new PrivilegeDoesNotExistException(privilege);
        }

    }

    @Transactional
    public EditorUser addPrivilegeToEditorUser(Long id, String privilegeName) {

        var user = getUserById(id);
        var privilege = privilegeRepository.findByName(privilegeName);
        if (privilege.isPresent()) {
            user.addPrivilege(privilege.get());
            return editorUserRepository.save(user);
        } else {
            throw new PrivilegeDoesNotExistException(privilegeName);
        }

    }

}
