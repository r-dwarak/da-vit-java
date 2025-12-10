package com.emp.web;

import com.emp.dao.EmployeeDAO;
import com.emp.model.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/list")
public class AdminEmployeeListServlet extends HttpServlet {
    private final EmployeeDAO dao = new EmployeeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Employee current = (Employee) req.getSession().getAttribute("user");
        if (current == null || !"ADMIN".equalsIgnoreCase(current.getRole())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Admin only.");
            return;
        }
        try {
            List<Employee> all = dao.findAll();
            req.setAttribute("employees", all);
            req.getRequestDispatcher("/admin-list.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("msg", "Failed to load employees: " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
