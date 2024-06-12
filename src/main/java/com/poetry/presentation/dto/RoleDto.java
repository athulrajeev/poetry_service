package com.poetry.presentation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class RoleDto {

    @NonNull
    private  String id;

    @NonNull
    private  String role;
}
