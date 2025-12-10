<%@ page contentType="text/html;charset=UTF-8" %>
<%
    request.setCharacterEncoding("UTF-8");
    String quizTaker = (String) session.getAttribute("quizName");
    if (quizTaker == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    char[] answerKey = (char[]) session.getAttribute("answerKey");
    if (answerKey == null) {
        response.sendRedirect("quiz.jsp");
        return;
    }

    int total = answerKey.length;
    int correctCount = 0;

    // Collect submitted answers and score
    String[] userAns = new String[total];
    for (int i = 0; i < total; i++) {
        String a = request.getParameter("q" + i);
        userAns[i] = (a == null ? "" : a);
        if (!userAns[i].isEmpty() && userAns[i].charAt(0) == answerKey[i]) {
            correctCount++;
        }
    }

    // Optionally clear key so refresh doesn’t reuse — not necessary for a simple demo
    // session.removeAttribute("answerKey");
%>
<!DOCTYPE html>
<html>
<head>
  <title>Quiz — Result</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 24px; }
    .card { max-width: 720px; border:1px solid #ddd; padding:16px; border-radius:8px; }
    .summary { font-size: 18px; margin-bottom: 12px; }
    table { border-collapse: collapse; width: 100%; }
    th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
    th { background: #f7f7f7; }
    .ok { color: #0a7a0a; font-weight: bold; }
    .bad { color: #c01919; font-weight: bold; }
  </style>
</head>
<body>
  <div class="card">
    <div class="summary">
      Hello, <strong><%= quizTaker %></strong>!<br/>
      Your score: <strong><%= correctCount %></strong> / <%= total %>
    </div>

    <h3>Answer Summary</h3>
    <table>
      <tr><th>Q#</th><th>Your Answer</th><th>Correct</th><th>Status</th></tr>
      <% for (int i = 0; i < total; i++) { 
            String ua = userAns[i].isEmpty() ? "-" : userAns[i].toUpperCase();
            String ca = String.valueOf(answerKey[i]).toUpperCase();
            boolean ok = !userAns[i].isEmpty() && userAns[i].charAt(0) == answerKey[i];
      %>
        <tr>
          <td><%= (i+1) %></td>
          <td><%= ua %></td>
          <td><%= ca %></td>
          <td class="<%= ok ? "ok" : "bad" %>"><%= ok ? "Correct" : "Incorrect" %></td>
        </tr>
      <% } %>
    </table>

    <p style="margin-top:12px;">
      <a href="index.jsp">Take quiz again</a>
    </p>
  </div>
</body>
</html>
