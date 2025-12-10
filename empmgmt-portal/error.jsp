<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html><head><title>Error</title></head>
<body>
<h3 style="color:red;">Error</h3>
<p><%= request.getAttribute("msg") %></p>
</body></html>
