package com.library.usermanagement.service;

import com.library.usermanagement.model.RegularUser;
import com.library.usermanagement.repository.UserManagementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserManagementService {

    private final UserManagementRepository repository = new UserManagementRepository();

    public RegularUser registerUser(RegularUser user) {
        user.setId(UUID.randomUUID().toString());
        repository.saveUser(user);
        return user;
    }

    public RegularUser login(String username, String password) {
        return repository.login(username, password);
    }

    public RegularUser findByUsername(String username) {
        return repository.findUserByUsername(username);
    }

    public RegularUser findById(String id) {
        return repository.findUserById(id);
    }

    public List<RegularUser> getAllUsers() {
        return repository.readAllUsers();
    }

    public void updateUser(RegularUser updatedUser) {
        repository.updateUser(updatedUser);
    }

    public void deleteUser(String id) {
        repository.deleteUser(id);
    }
}
