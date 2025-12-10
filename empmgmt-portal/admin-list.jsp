<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*,com.company.ems.model.Employee" %>
<!DOCTYPE html>
<html><head><title>Employee List</title></head>
<body>
<h2>Admin — All Employees</h2>
<table border="1" cellpadding="6">
  <tr><th>ID</th><th>Username</th><th>Full Name</th><th>Email</th><th>Role</th></tr>
<%
  List<Employee> list = (List<Employee>) request.getAttribute("employees");
  if (list != null) {
    for (Employee e : list) {
%>
  <tr>
    <td><%= e.getId() %></td>
    <td><%= e.getUsername() %></td>
    <td><%= e.getFullName() %></td>
    <td><%= e.getEmail() %></td>
    <td><%= e.getRole() %></td>
  </tr>
<%  } } %>
</table>
</body></html>
