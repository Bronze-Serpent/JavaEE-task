<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Player</title>
</head>
<body>

<h1>Страница для управлением Player</h1><br />


<h2>Создание нового player</h2><br />

<form method="post" action="${pageContext.request.contextPath}/plCreateServlet">

    <label><input type="number" name="playerId"></label>playerID<br>

    <label><input type="text" name="name"></label>Name<br>

    <input type="submit" value="Ok" name="Ok"><br>
</form>


<h2>Удаление player</h2><br />

<form method="post" action="${pageContext.request.contextPath}/plDeleteServlet">

    <label><input type="number" name="playerId"></label>playerID<br>

    <input type="submit" value="Ok" name="Ok"><br>
</form>


<h2>Считывание player</h2><br />

<form method="get" action="${pageContext.request.contextPath}/plReadServlet">

    <label><input type="number" name="playerId"></label>playerID<br>
    <input type="submit" value="Ok" name="Ok"><br>

</form>

<c:if test="${requestScope.player != null}">
    <h4>Считанный player: <c:out value="${requestScope.player}"/><h4>
</c:if>

<h2>Изменение player</h2><br />

<form method="post" action="${pageContext.request.contextPath}/plUpdateServlet">

    <label><input type="number" name="playerId"></label>playerID<br>

    <label><input type="text" name="name"></label>New Name<br>

    <input type="submit" value="Ok" name="Ok"><br>
</form>

</body>
</html>