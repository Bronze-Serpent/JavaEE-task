<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Item</title>
</head>
<body>

<h1>Страница для управлением Item</h1><br />


<h2>Создание нового item</h2><br />

<form method="post" action="${pageContext.request.contextPath}/itemCreateServlet">

    <label><input type="number" name="playerId"></label>playerID<br>
    <label><input type="number" name="id"></label>id<br>
    <label><input type="number" name="resourceId"></label>resourceId<br>
    <label><input type="number" name="count"></label>count<br>
    <label><input type="number" name="level"></label>level<br>
    <input type="submit" value="Ok" name="Ok"><br>
</form>


<h2>Удаление item</h2><br />

<form method="post" action="${pageContext.request.contextPath}/itemDeleteServlet">

    <label><input type="number" name="id"></label>id<br>

    <input type="submit" value="Ok" name="Ok"><br>
</form>


<h2>Считывание item</h2><br />

<form method="get" action="${pageContext.request.contextPath}/itemReadServlet">

    <label><input type="number" name="id"></label>id<br>
    <input type="submit" value="Ok" name="Ok"><br>

</form>

<c:if test="${requestScope.item != null}">
    <h4>Считанный item: <c:out value="${requestScope.item}"/><h4>
</c:if>

</body>
</html>