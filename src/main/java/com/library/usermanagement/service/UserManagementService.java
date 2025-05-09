package com.library.usermanagement.service;

import com.library.usermanagement.exception.*;
import com.library.usermanagement.model.UserType;
import com.library.usermanagement.model.RegularUser;
import com.library.usermanagement.repository.UserManagementRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserManagementService {
    private final UserManagementRepository repository = new UserManagementRepository();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public RegularUser registerUser(RegularUser user) {
        if (repository.findUserByUsername(user.getUsername()).isPresent()) {
            throw new DuplicateUsernameException(user.getUsername());
        }
        if (repository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateEmailException(user.getEmail());
        }

        user.setUserType(UserType.AUDIENCE);
        String hashed = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashed);
        user.setId(UUID.randomUUID().toString());
        repository.saveUser(user);
        return user;
    }

    public RegularUser login(String username, String providedPassword) {
        RegularUser user = repository.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Invalid username or password"));

        if (!passwordEncoder.matches(providedPassword, user.getPassword())) {
            throw new UserNotFoundException("Invalid username or password");
        }
        return user;
    }

    public RegularUser findById(String id) {
        return repository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + id));
    }

    public RegularUser findByUsername(String username) {
        return repository.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
    }

    public List<RegularUser> getAllUsers() {
        return repository.readAllUsers();
    }

    public List<RegularUser> searchUsers(String query) {
        String lower = query.toLowerCase();
        return repository.readAllUsers().stream()
                .filter(u -> u.getUsername().toLowerCase().contains(lower)
                        || u.getId().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    public RegularUser updateUserPartially(String id, RegularUser updates) {
        RegularUser existing = findById(id);

        if (updates.getUsername() != null
                && !updates.getUsername().equalsIgnoreCase(existing.getUsername())) {
            repository.findUserByUsername(updates.getUsername())
                    .filter(u -> !u.getId().equals(id))
                    .ifPresent(u -> {
                        throw new DuplicateUsernameException(updates.getUsername());
                    });
            existing.setUsername(updates.getUsername());
        }

        if (updates.getEmail() != null
                && !updates.getEmail().equalsIgnoreCase(existing.getEmail())) {
            repository.findUserByEmail(updates.getEmail())
                    .filter(u -> !u.getId().equals(id))
                    .ifPresent(u -> {
                        throw new DuplicateEmailException(updates.getEmail());
                    });
            existing.setEmail(updates.getEmail());
        }

        if (updates.getPassword() != null) {
            existing.setPassword(passwordEncoder.encode(updates.getPassword()));
        }
        if (updates.getUserType() != null) {
            existing.setUserType(updates.getUserType());
        }

        repository.updateUser(existing);
        return existing;
    }

    public void deleteUser(String id) {
        repository.deleteUser(id);
    }
}
