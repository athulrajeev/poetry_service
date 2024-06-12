package com.poetry.infrastructure.firebase;

import com.google.firebase.auth.*;
import com.poetry.domain.user.Role;
import com.poetry.domain.user.User;
import com.poetry.domain.user.UserManagementService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {

    private final FirebaseAuth firebaseAuth;
    public static final String CUSTOM_CLAIMS = "custom_claims";

    // This is a hardcoded email address for the admin user. Need to extract this to a configuration file.
    private final String adminEmailAddress = "poetry@admin.com";

    @PostConstruct
    public void init() {
        try {
            final UserRecord userRecord = firebaseAuth.getUserByEmail(adminEmailAddress);
            final Map<String, Object> currentCustomClaims = userRecord.getCustomClaims();
            // check if the currentCustomClaims already contains the ADMIN role
            if (extractRole(currentCustomClaims).contains(Role.ADMIN)) {
                return;
            }
            firebaseAuth.setCustomUserClaims(userRecord.getUid(), addRoleToCustomClaims(currentCustomClaims, Role.ADMIN));
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addRole(@NonNull final String userId, @NonNull final Role role) {
        try {
            final UserRecord userRecord = firebaseAuth.getUser(userId);
            final Map<String, Object> currentCustomClaims = userRecord.getCustomClaims();
            if (extractRole(currentCustomClaims).contains(role)) {
                return;
            }
            firebaseAuth.setCustomUserClaims(userId, addRoleToCustomClaims(currentCustomClaims, role));
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> addRoleToCustomClaims(Map<String, Object> customClaims, Role role) {
        Collection<String> roles = (Collection<String>) customClaims.getOrDefault(CUSTOM_CLAIMS, new ArrayList<>());
        roles.add(role.toString());
        return Map.of(CUSTOM_CLAIMS, roles);
    }


    public List<User> getUsers() {
        try {
            final List<User> users = new ArrayList<>();
            // Start listing users from the beginning, 1000 at a time.
            ListUsersPage page = firebaseAuth.listUsers(null);
            while (page != null) {
                for (ExportedUserRecord user : page.getValues()) {
                    users.add(new User(user.getUid(), user.getDisplayName(),
                            user.getEmail(), extractRole(user.getCustomClaims())));
                }
                page = page.getNextPage();
            }
            return users;

        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<Role> extractRole(Map<String, Object> customClaims) {
        Collection<String> roles = (Collection<String>) customClaims.getOrDefault(CUSTOM_CLAIMS, List.of());
        return roles.stream()
                .map(Role::valueOf).collect(Collectors.toSet());
    }
}