package com.library.usermanagement.repository;

import com.library.usermanagement.exception.DataAccessException;
import com.library.usermanagement.model.RegularUser;

import java.io.*;
import java.util.*;

public class UserManagementRepository {
    private static final String FILE_PATH = "src/main/resources/database/users.txt";

    /**
     * Read all RegularUsers from the file.
     */
    public List<RegularUser> readAllUsers() {
        List<RegularUser> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 5) {
                    users.add(new RegularUser(p[0], p[1], p[2], p[3], p[4]));
                }
            }
        } catch (IOException e) {
            throw new DataAccessException("Failed to read users", e);
        }
        return users;
    }

    public Optional<RegularUser> findUserByUsername(String username) {
        return readAllUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    public Optional<RegularUser> findUserByEmail(String email) {
        return readAllUsers().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public Optional<RegularUser> findUserById(String id) {
        return readAllUsers().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    public void saveUser(RegularUser user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(String.join(",",
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getUserType().name()
            ));
            writer.newLine();
        } catch (IOException e) {
            throw new DataAccessException("Failed to save user", e);
        }
    }

    public void updateUser(RegularUser updatedUser) {
        List<RegularUser> users = readAllUsers();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (RegularUser u : users) {
                if (u.getId().equals(updatedUser.getId())) {
                    u = updatedUser;
                }
                writer.write(String.join(",",
                        u.getId(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getPassword(),
                        u.getUserType().name()
                ));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new DataAccessException("Failed to update user", e);
        }
    }

    public void deleteUser(String id) {
        List<RegularUser> users = readAllUsers();
        users.removeIf(u -> u.getId().equals(id));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (RegularUser u : users) {
                writer.write(String.join(",",
                        u.getId(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getPassword(),
                        u.getUserType().name()
                ));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new DataAccessException("Failed to delete user", e);
        }
    }
}
