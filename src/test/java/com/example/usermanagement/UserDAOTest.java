package com.example.usermanagement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.usermanagement.dao.UserDAO;
import com.example.usermanagement.model.User;

class UserDAOTest {

    private static final String TEST_DB_URL = "jdbc:sqlite::memory:";
    private Connection conn;  // connexion partagée
    private UserDAO dao;

    @BeforeEach
    void setUp() throws Exception {
        conn = DriverManager.getConnection(TEST_DB_URL);

        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "email TEXT NOT NULL, " +
                    "phone TEXT, " +
                    "dateNaissance TEXT);");
        }

        dao = new UserDAO() {
            @Override
            protected Connection getConnection() throws SQLException {
                return conn;  // retourne toujours la même connexion
            }
        };
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Test
    void testAddAndListAll() {
        User user = new User(0, "Alice", "alice@example.com", "0601020304", LocalDate.of(2000, 1, 1));
        dao.add(user);

        List<User> users = dao.listAll();
        assertEquals(1, users.size());

        User result = users.get(0);
        assertEquals("Alice", result.getName());
        assertEquals("alice@example.com", result.getEmail());
        assertEquals("0601020304", result.getPhone());
        assertEquals(LocalDate.of(2000, 1, 1), result.getDateNaissance());
    }
}
