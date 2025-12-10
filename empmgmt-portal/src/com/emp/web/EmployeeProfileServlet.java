package com.emp.web;

import com.emp.model.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/emp/profile")
public class EmployeeProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Employee current = (Employee) req.getSession().getAttribute("user");
        if (current == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        req.setAttribute("emp", current);
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }
}
