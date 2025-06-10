<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.usermanagement.model.User" %>
<html>
<head>
    <title>Liste des utilisateurs</title>
</head>
<body>
    <h1>Liste des utilisateurs</h1>

    <table border="1" cellpadding="5" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Email</th>
            <th>Téléphone</th>
            <th>Date de naissance</th>
        </tr>
        <%
            List<User> users = (List<User>) request.getAttribute("users");
            for (User u : users) {
        %>
        <tr>
            <td><%= u.getId() %></td>
            <td><%= u.getName() %></td>
            <td><%= u.getEmail() %></td>
            <td><%= u.getPhone() %></td>
            <td><%= u.getDateNaissance() %></td>
        </tr>
        <% } %>
    </table>

    <br>
    <a href="index.jsp">Ajouter un nouvel utilisateur</a>
</body>
</html>
