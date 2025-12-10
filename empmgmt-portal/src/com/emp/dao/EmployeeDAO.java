package com.emp.dao;

import com.emp.model.Employee;
import com.emp.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public Employee findByCredentials(String username, String passwordPlain) throws SQLException {
        String sql = "SELECT id, username, full_name, email, role FROM employees WHERE username=? AND password_hash=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordPlain); // Replace with a hash comparison in real life
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Employee e = new Employee();
                    e.setId(rs.getInt("id"));
                    e.setUsername(rs.getString("username"));
                    e.setFullName(rs.getString("full_name"));
                    e.setEmail(rs.getString("email"));
                    e.setRole(rs.getString("role"));
                    return e;
                }
                return null;
            }
        }
    }

    public List<Employee> findAll() throws SQLException {
        String sql = "SELECT id, username, full_name, email, role FROM employees ORDER BY id";
        List<Employee> list = new ArrayList<>();
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Employee e = new Employee();
                e.setId(rs.getInt("id"));
                e.setUsername(rs.getString("username"));
                e.setFullName(rs.getString("full_name"));
                e.setEmail(rs.getString("email"));
                e.setRole(rs.getString("role"));
                list.add(e);
            }
        }
        return list;
    }

    public Employee findById(int id) throws SQLException {
        String sql = "SELECT id, username, full_name, email, role FROM employees WHERE id=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Employee e = new Employee();
                    e.setId(rs.getInt("id"));
                    e.setUsername(rs.getString("username"));
                    e.setFullName(rs.getString("full_name"));
                    e.setEmail(rs.getString("email"));
                    e.setRole(rs.getString("role"));
                    return e;
                }
                return null;
            }
        }
    }
}
