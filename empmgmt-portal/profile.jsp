<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.company.ems.model.Employee" %>
<!DOCTYPE html>
<html><head><title>My Profile</title></head>
<body>
<h2>My Profile</h2>
<%
  Employee e = (Employee) request.getAttribute("emp");
%>
<ul>
  <li>ID: <%= e.getId() %></li>
  <li>Username: <%= e.getUsername() %></li>
  <li>Full Name: <%= e.getFullName() %></li>
  <li>Email: <%= e.getEmail() %></li>
  <li>Role: <%= e.getRole() %></li>
</ul>
</body></html>
