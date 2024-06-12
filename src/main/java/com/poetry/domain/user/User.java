package com.poetry.domain.user;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

@Value
public class User {

    @NonNull
    private final String id;

    @Nullable
    private final String displayName;

    @NonNull
    private final String emailAddress;

    @NonNull
    private final Set<Role> role;
}
