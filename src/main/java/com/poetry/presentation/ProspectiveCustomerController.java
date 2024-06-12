package com.poetry.presentation;


import com.poetry.domain.user.Role;
import com.poetry.domain.user.UserManagementService;
import com.poetry.presentation.dto.ProspectiveCustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/prospective-customer")
@PreAuthorize("hasRole('ROLE_ANONYMOUS')")
@RequiredArgsConstructor
public class ProspectiveCustomerController {

    private final UserManagementService userManagementService;

    @PostMapping("/assign-role")
    public void registerUser(@RequestBody ProspectiveCustomerDto prospectiveCustomer) {
        userManagementService.addRole(prospectiveCustomer.getId(), Role.PROSPECTIVE_CUSTOMER);
    }
}
