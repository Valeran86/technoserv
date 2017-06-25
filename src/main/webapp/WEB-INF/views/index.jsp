<!-- обратите внимание на spring тэги -->
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
  <title>Index Page</title>
</head>

<body>
<spring:form method="post"  modelAttribute="task" action="find-phrase">
  Выбрали для просмотра: "${task.wetUrl}" <br/> <spring:hidden path="wetUrl" />
  Фраза для поиска: <spring:input path="phrase"/> <br/>

  <spring:button>Найти</spring:button>

</spring:form>

</body>

</html>