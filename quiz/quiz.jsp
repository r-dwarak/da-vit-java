<%@ page contentType="text/html;charset=UTF-8" %>
<%
    request.setCharacterEncoding("UTF-8");
    String name = request.getParameter("name");
    if (name != null && !name.isBlank()) {
        session.setAttribute("quizName", name.trim());
    }
    // If user refreshed or visited directly, keep existing name in session
    String quizTaker = (String) session.getAttribute("quizName");
    if (quizTaker == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    // In-memory quiz data
    // q[i], options[i][0..3], correct[i] = 'a'|'b'|'c'|'d'
    String[] q = {
        "1) Which keyword is used to inherit a class in Java?",
        "2) Which HTML tag is used to create a hyperlink?",
        "3) What is the value of 2 + 2 × 3 ?",
        "4) In DBMS, SQL stands for?",
        "5) Which is NOT a Java primitive type?"
    };
    String[][] options = {
        {"extends", "implements", "inherit", "super"},
        {"<a>", "<link>", "<href>", "<ul>"},
        {"8", "12", "6", "10"},
        {"Structured Query Language", "Simple Query Language", "System Query Language", "Sequential Query Language"},
        {"int", "float", "String", "boolean"}
    };
    char[] correct = {'a','a','a','a','c'};

    // Save answer key in session for result.jsp
    session.setAttribute("answerKey", correct);
%>
<!DOCTYPE html>
<html>
<head>
  <title>Quiz — Questions</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 24px; }
    .qcard { max-width: 720px; padding: 16px; border:1px solid #ddd; border-radius:8px; margin-bottom:16px; }
    h3 { margin: 0 0 8px 0; }
    .opts label { display:block; margin:4px 0; }
    .top { margin-bottom: 12px; }
    .actions { margin-top: 12px; }
    button { padding:8px 14px; }
  </style>
</head>
<body>
  <div class="qcard">
    <div class="top">
      <strong>Candidate:</strong> <%= quizTaker %>
    </div>

    <form method="post" action="result.jsp">
      <% for (int i = 0; i < q.length; i++) { %>
        <div class="qcard">
          <h3><%= q[i] %></h3>
          <div class="opts">
            <label><input type="radio" name="q<%=i%>" value="a" required> a) <%= options[i][0] %></label>
            <label><input type="radio" name="q<%=i%>" value="b"> b) <%= options[i][1] %></label>
            <label><input type="radio" name="q<%=i%>" value="c"> c) <%= options[i][2] %></label>
            <label><input type="radio" name="q<%=i%>" value="d"> d) <%= options[i][3] %></label>
          </div>
        </div>
      <% } %>

      <div class="actions">
        <button type="submit">Submit Answers</button>
      </div>
    </form>
  </div>
</body>
</html>
