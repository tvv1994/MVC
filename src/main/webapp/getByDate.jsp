<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Поиск</title>
</head>
<body>

Введите даты:

<form action = "getDate" method="get">
    <p><input required type="date" name="date1" placeholder="от">
    <p><input required type="date" name="date2" placeholder="до">
    <input type="submit" value="Поиск">
</form>
</body>
</html>