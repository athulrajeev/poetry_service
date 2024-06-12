package com.poetry.presentation;

import com.poetry.domain.user.Role;
import com.poetry.domain.user.User;
import com.poetry.domain.user.UserManagementService;
import com.poetry.presentation.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final UserManagementService userManagementService;

    @PostMapping("/assign-role")
    public void registerUser(@RequestBody RoleDto role) {
        userManagementService.addRole(role.getId(), Role.valueOf(role.getRole()));
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userManagementService.getUsers();
    }
}
