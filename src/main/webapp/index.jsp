<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
Выберите действие - <br>
нажав кнопку ниже:
<form action = "addSomething.jsp">
    <input type="submit" value="Добавить">
</form>
<form action = "getByDate.jsp" method="get">
    <input type="submit" value="Поиск">
</form>
<form action = "deleteSomething.jsp">
    <input type="submit" value="Удалить">
</form>
</body>
</html>