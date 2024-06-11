package com.poetry.domain.user;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.Set;

@Value
public class User {
    private final String id;
    private final String emailAddress;
    private final List<Role> role;
}
