<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Currency</title>
</head>
<body>

<h1>Страница для управлением Currency</h1><br />


<h2>Создание нового currency</h2><br />

<form method="post" action="${pageContext.request.contextPath}/CurCreateServlet">

    <label><input type="number" name="playerId"></label>playerID<br>
    <label><input type="number" name="id"></label>id<br>
    <label><input type="number" name="resourceId"></label>resourceId<br>
    <label><input type="text" name="name"></label>name<br>
    <label><input type="number" name="count"></label>count<br>
    <input type="submit" value="Ok" name="Ok"><br>
</form>


<h2>Удаление currency</h2><br />

<form method="post" action="${pageContext.request.contextPath}/CurDeleteServlet">

    <label><input type="number" name="id"></label>id<br>

    <input type="submit" value="Ok" name="Ok"><br>
</form>


<h2>Считывание currency</h2><br />

<form method="post" action="${pageContext.request.contextPath}/CurReadServlet">

    <label><input type="number" name="id"></label>id<br>
    <input type="submit" value="Ok" name="Ok"><br>

</form>

<c:if test="${requestScope.currency != null}">
    <h4>Считанный currency: <c:out value="${requestScope.currency}"/><h4>
</c:if>

</body>
</html>