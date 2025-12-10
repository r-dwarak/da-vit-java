<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html><head><title>EMS Login</title></head>
<body>
<h2>Employee Management Portal — Login</h2>
<form method="post" action="login">
    <label>Username: <input type="text" name="username"/></label><br/>
    <label>Password: <input type="password" name="password"/></label><br/>
    <button type="submit">Login</button>
</form>
<p style="color:red;"><%= request.getAttribute("error") == null ? "" : request.getAttribute("error") %></p>
</body></html>
