package com.example.usermanagement.controller;

import com.example.usermanagement.dao.UserDAO;
import com.example.usermanagement.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> userList = userDAO.listAll();
        request.setAttribute("users", userList);
        request.getRequestDispatcher("listUsers.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String dateStr = request.getParameter("dateNaissance");

        LocalDate dateNaissance = LocalDate.parse(dateStr);

        User user = new User(0, name, email, phone, dateNaissance);
        userDAO.add(user);

        response.sendRedirect("users");
    }
}
