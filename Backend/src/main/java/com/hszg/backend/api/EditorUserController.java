package com.hszg.backend.api;

import com.hszg.backend.data.model.EditorUser;
import com.hszg.backend.service.EditorUserService;
import com.hszg.backend.service.edit.UserPropertiesEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/user")
public class EditorUserController {

    private final EditorUserService editorUserService;

    @Autowired
    public EditorUserController(EditorUserService editorUserService) {
        this.editorUserService = editorUserService;
    }

    @GetMapping("/all")
    public List<EditorUser> getAllUsers() {
        return editorUserService.getAllUsers();
    }

    /**
     * * Method was tested (✔)
     */
    @PostMapping("/create")
    public EditorUser createUser(@RequestBody EditorUser editorUser) {
        var user =  editorUserService.createUser(editorUser);
        return editorUserService.addPrivilegeToEditorUser(user, "READ_DATA");
    }

    /**
     * * Method was tested (✔)
     */
    @GetMapping("/get/email")
    public EditorUser getUserByEmail(@RequestBody String email) {
        return editorUserService.getUserByEmail(email);
    }

    /**
     * * Method was tested (✔)
     */
    @GetMapping("/get/{userId}")
    public EditorUser getUserById(@PathVariable Long userId) {
        return editorUserService.getUserById(userId);
    }

    /**
     * * Method was tested (✔)
     */
    @GetMapping("/permission/{userId}")
    public boolean checkPermission(@PathVariable Long userId, @RequestBody String privilege) {
        return editorUserService.checkPermission(userId, privilege);
    }

    /**
     * * Method was tested (✔)
     */
    @PostMapping("/edit/{userId}")
    public EditorUser editUser(@PathVariable Long userId, @RequestBody UserPropertiesEdit changes) {
        return editorUserService.editUser(userId, changes);
    }

    /**
     * * Method was tested (✔)
     */
    @PostMapping("/add/privilege/{userId}")
    public EditorUser addPrivilegeToUser(@PathVariable Long userId, @RequestBody String privilege) {
        return editorUserService.addPrivilegeToEditorUser(userId, privilege);
    }

}
