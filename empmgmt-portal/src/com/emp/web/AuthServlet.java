package com.emp.web;

import com.emp.dao.EmployeeDAO;
import com.emp.model.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class AuthServlet extends HttpServlet {
    private final EmployeeDAO dao = new EmployeeDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String u = req.getParameter("username");
        String p = req.getParameter("password");

        if (u == null || p == null || u.isBlank() || p.isBlank()) {
            req.setAttribute("error", "Username and password are required.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        try {
            Employee emp = dao.findByCredentials(u, p);
            if (emp == null) {
                req.setAttribute("error", "Invalid credentials.");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
                return;
            }

            HttpSession session = req.getSession(true);
            session.setAttribute("user", emp);

            if ("ADMIN".equalsIgnoreCase(emp.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/admin/list");
            } else {
                resp.sendRedirect(req.getContextPath() + "/emp/profile");
            }
        } catch (Exception e) {
            req.setAttribute("msg", "Login error: " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
