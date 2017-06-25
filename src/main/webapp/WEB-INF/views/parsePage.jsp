<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Second Page</title>
</head>
<body>
Результаты поиска фразы: ${task.phrase};
<br/>
В wet-файле: ${task.wetUrl};
<br/>
#foreach($page in $searchResult.pages)
    <b>$page</b> <br>
#end
</body>
</html>