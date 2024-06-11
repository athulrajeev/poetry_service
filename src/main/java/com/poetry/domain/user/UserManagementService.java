package com.poetry.domain.user;

import java.util.List;

public interface UserManagementService {

    void addRole(String userId, Role role);

    List<User> getUsers();
}
