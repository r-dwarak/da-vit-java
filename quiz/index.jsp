<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Simple JSP Quiz</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 24px; }
    .card { max-width: 520px; padding: 16px; border: 1px solid #ddd; border-radius: 8px; }
    label { display:block; margin: 8px 0; }
    button { padding:8px 14px; }
  </style>
</head>
<body>
  <div class="card">
    <h2>Welcome to the Quiz</h2>
    <form method="post" action="quiz.jsp">
      <label>
        Your Name:
        <input type="text" name="name" required />
      </label>
      <button type="submit">Start Quiz</button>
    </form>
  </div>
</body>
</html>
