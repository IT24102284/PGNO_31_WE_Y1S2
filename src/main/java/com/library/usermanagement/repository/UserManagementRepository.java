package com.library.usermanagement.repository;

import com.library.usermanagement.model.RegularUser;
import java.io.*;
import java.util.*;

public class UserManagementRepository {
    private static final String FILE_PATH = "src/main/resources/users.txt";

    public List<RegularUser> readAllUsers() {
        List<RegularUser> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    users.add(new RegularUser(parts[0], parts[1], parts[2], parts[3], parts[4]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void saveUser(RegularUser user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(String.join(",", user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getMembershipType()));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(RegularUser updatedUser) {
        List<RegularUser> users = readAllUsers();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (RegularUser user : users) {
                if (user.getId().equals(updatedUser.getId())) {
                    user = updatedUser;
                }
                writer.write(String.join(",", user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getMembershipType()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String userId) {
        List<RegularUser> users = readAllUsers();
        users.removeIf(user -> user.getId().equals(userId));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (RegularUser user : users) {
                writer.write(String.join(",", user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getMembershipType()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RegularUser findUserById(String id) {
        return readAllUsers().stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }

    public RegularUser findUserByUsername(String username) {
        return readAllUsers().stream().filter(u -> u.getUsername().equalsIgnoreCase(username)).findFirst().orElse(null);
    }

    public RegularUser login(String username, String password) {
        return readAllUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username) && u.getPassword().equals(password))
                .findFirst().orElse(null);
    }
}
