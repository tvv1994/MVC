<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавить новые данные</title>
</head>
<body>

Добавление новых данных:

<form action = "add" method="post">
    <p><input required type="text" name="name" placeholder="Наименование">
    <p><input required type="text" name="number" placeholder="Номер">
    <p><input required type="text" name="tags" placeholder="Список тэгов">
    <input type="submit" value="Добавить">
</form>
</body>
</html>