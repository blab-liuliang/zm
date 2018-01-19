<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Ares
  Date: 2017/12/21
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>智盟教育</title>
    <link rel="shortcut icon" href="/RES/img/logo.ico" type="image/x-icon" />
</head>
<body>
    <div class="container" >
        <c:forEach var="course" items="${courses}" varStatus="status">
            <a href="${course.link}">${course.name}</a>
        </c:forEach>
    </div>
</body>
</html>
