package com.example.usermanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.usermanagement.model.User;

public class UserDAO {

    // Méthode que le test surcharge pour retourner la connexion partagée
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:users.db"); // connexion par défaut
    }

    // Ajouter un utilisateur en base
public void add(User user) {
    Connection conn = null;
    PreparedStatement ps = null;
    try {
        conn = getConnection();
        ps = conn.prepareStatement("INSERT INTO users(name, email, phone, dateNaissance) VALUES (?, ?, ?, ?)");
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPhone());
        ps.setString(4, user.getDateNaissance().toString());
        ps.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    } finally {
        if (ps != null) try { ps.close(); } catch (SQLException e) { /* ignore */ }
        // Ne ferme pas la connexion, car elle est gérée à l'extérieur (test)
    }
}


    // Lister tous les utilisateurs
    public List<User> listAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        LocalDate.parse(rs.getString("dateNaissance"))
                );
                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
