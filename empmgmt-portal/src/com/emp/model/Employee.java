package com.emp.model;

public class Employee {
    private int id;
    private String username;
    private String fullName;
    private String email;
    private String role; // "ADMIN" or "EMP"

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String u) { this.username = u; }
    public String getFullName() { return fullName; }
    public void setFullName(String fn) { this.fullName = fn; }
    public String getEmail() { return email; }
    public void setEmail(String e) { this.email = e; }
    public String getRole() { return role; }
    public void setRole(String r) { this.role = r; }
}
