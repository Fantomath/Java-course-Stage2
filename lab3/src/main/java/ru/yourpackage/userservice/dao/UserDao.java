package ru.yourpackage.userservice.dao;

import ru.yourpackage.userservice.model.User;
import ru.yourpackage.userservice.util.DbConnectionManager;

import java.sql.*;
import java.util.Optional;

public class UserDao {

    private final DbConnectionManager connectionManager;

    public UserDao(DbConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public User save(User user) {
        if (user == null || user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("User/email must not be null/blank");
        }

        final String sql = "INSERT INTO users(email) VALUES (?) RETURNING id";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, user.getEmail());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user.setId(rs.getLong("id"));
                }
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save user", e);
        }
    }

    public Optional<User> findByEmail(String email) {
        final String sql = "SELECT id, email FROM users WHERE email = ?";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(new User(rs.getLong("id"), rs.getString("email")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find user by email", e);
        }
    }

    public void deleteByEmail(String email) {
        final String sql = "DELETE FROM users WHERE email = ?";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete user", e);
        }
    }

    public void deleteAll() {
        final String sql = "DELETE FROM users";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete all users", e);
        }
    }
}
