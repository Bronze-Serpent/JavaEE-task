<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Progress</title>
</head>
<body>

<h1>Страница для управлением Progress</h1><br />


<h2>Создание нового progress</h2><br />

<form method="post" action="${pageContext.request.contextPath}/ProgCreateServlet">

    <label><input type="number" name="playerId"></label>playerID<br>
    <label><input type="number" name="id"></label>id<br>
    <label><input type="number" name="resourceId"></label>resourceId<br>
    <label><input type="number" name="score"></label>score<br>
    <label><input type="number" name="maxScore"></label>maxScore<br>
    <input type="submit" value="Ok" name="Ok"><br>
</form>


<h2>Удаление progress</h2><br />

<form method="post" action="${pageContext.request.contextPath}/ProgDeleteServlet">

    <label><input type="number" name="id"></label>id<br>

    <input type="submit" value="Ok" name="Ok"><br>
</form>


<h2>Считывание progress</h2><br />

<form method="post" action="${pageContext.request.contextPath}/ProgReadServlet">

    <label><input type="number" name="id"></label>id<br>
    <input type="submit" value="Ok" name="Ok"><br>

</form>

<c:if test="${requestScope.progress != null}">
    <h4>Считанный progress: <c:out value="${requestScope.progress}"/><h4>
</c:if>

</body>
</html>