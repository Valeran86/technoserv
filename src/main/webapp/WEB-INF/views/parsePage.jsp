<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Second Page</title>
</head>
<body>
Результаты поиска фразы: "${task.phrase}"
<br/>
В wet-файле: "${task.wetUrl}"
<br/>
Всего результатов: "${searchResult.pages.size()}"
<br/>
    <c:forEach items="${searchResult.pages}" var="page">
        <a href="${page}" target="_blank">${page}</a> <br>
    </c:forEach>
</body>
</html>