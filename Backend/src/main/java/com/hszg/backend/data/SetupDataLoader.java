package com.hszg.backend.data;

import com.hszg.backend.data.model.EditorUser;
import com.hszg.backend.data.model.Privilege;
import com.hszg.backend.data.privileges.Privileges;
import com.hszg.backend.repos.EditorUserRepository;
import com.hszg.backend.repos.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final String[] privileges = {
            Privileges.READ_PERMISSION,
            Privileges.WRITE_PERMISSION,
            Privileges.EDIT_PAGE_PERMISSION
    };

    private final String ADMIN_EMAIL = "admin";
    private final String ADMIN_PASSWORD = "TynZZFb2bA432L3x";

    @Autowired
    private PrivilegeRepository privilegeRepository;
    @Autowired
    private EditorUserRepository editorUserRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Set<Privilege> adminPrivileges = new HashSet<>();
        for (String privilege : privileges) {
            var newPrivilege = createPrivilegeIfNotFound(privilege);
            adminPrivileges.add(newPrivilege);
        }

        createUserIfNotFound(ADMIN_EMAIL, ADMIN_PASSWORD, adminPrivileges);

    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        var privilege = privilegeRepository.findByName(name);
        if (privilege.isEmpty()) {
            var newPrivilege = new Privilege(name);
            return privilegeRepository.save(newPrivilege);
        }

        return privilege.get();

    }

    @Transactional
    void createUserIfNotFound(String email, String password, Set<Privilege> privileges) {

        var user = editorUserRepository.findByEmail(email);
        if (user.isEmpty()) {
            var newUser = new EditorUser(email, password);
            newUser.setPrivileges(privileges);
            editorUserRepository.save(newUser);
        }

    }

}
