package com.poetry.presentation;

import com.google.firebase.auth.FirebaseAuthException;
import com.poetry.domain.user.Role;
import com.poetry.domain.user.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final UserManagementService userManagementService;

    @GetMapping(path = "/user-claims/{uid}")
    public void setUserClaims(@PathVariable String uid) throws FirebaseAuthException {
        userManagementService.addRole(uid, Role.CUSTOMER);
    }
}
