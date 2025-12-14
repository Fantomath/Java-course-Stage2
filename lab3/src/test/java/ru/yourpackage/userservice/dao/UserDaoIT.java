package ru.yourpackage.userservice.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.yourpackage.userservice.model.User;
import ru.yourpackage.userservice.util.DbConnectionManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class UserDaoIT {

    @SuppressWarnings("resource")
    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    private UserDao userDao;

    @BeforeEach
    void setUp() throws Exception {
        DbConnectionManager connectionManager = new DbConnectionManager(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );

        userDao = new UserDao(connectionManager);

        // Читаем schema.sql БЕЗ утечек ресурсов
        String schemaSql;
        try (var inputStream = UserDaoIT.class
                .getClassLoader()
                .getResourceAsStream("schema.sql");
             var reader = new BufferedReader(
                     new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
            schemaSql = sb.toString();
        }

        // Применяем схему перед каждым тестом (изоляция)
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(schemaSql);
        }
    }

    @Test
    void save_shouldPersistUser_andFindByEmailWorks() {
        User saved = userDao.save(new User(null, "test@mail.com"));

        assertNotNull(saved.getId(), "User ID must be generated");

        User found = userDao.findByEmail("test@mail.com")
                .orElseThrow(() -> new AssertionError("User not found"));

        assertEquals(saved.getId(), found.getId());
        assertEquals("test@mail.com", found.getEmail());
    }

    @Test
    void deleteByEmail_shouldRemoveUser() {
        userDao.save(new User(null, "delete@mail.com"));

        userDao.deleteByEmail("delete@mail.com");

        assertTrue(
                userDao.findByEmail("delete@mail.com").isEmpty(),
                "User should be deleted"
        );
    }
}
