<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ares
  Date: 11/01/2018
  Time: 8:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${lesson.title}</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="/RES/img/logo.ico" type="image/x-icon" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>

    <!--font awesome-->
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/RES/css/zm_navbar.css" >
</head>
<body>
    <nav class="navbar navbar-light bg-faded mb-2">
        <a class="navbar-brand" href="${back_url}">
            <i class="fa fa-chevron-left" style="color:#666666"></i>
        </a>
    </nav>

    <div class="container" >
        <c:forEach var="exercise" items="${lesson.exercises}" varStatus="status">
            <c:choose>
                <c:when test="${exercise.type=='choice'}">
                    <h6>${exercise.question}</h6>
                    <form>
                        <c:forEach var="option" items="${exercise.options}" varStatus="optionStatus">
                            <input type="radio" name="single" value="${option}" /> ${option} <br />
                        </c:forEach>
                        <input type="hidden" value="${exercise.answer}" />
                        <input type="hidden" value="${exercise.hint}" />
                        <input type="submit" value="提交">
                    </form>
                </c:when>

                <c:when test="${exercise.type=='video'}">
                    <div class="mt-2 mb-2 border">
                        <c:if test="${is_edit}">
                            <nav class="navbar navbar-light bg-faded">
                                <a class="navbar-brand" href="#">
                                    <i class="fa fa-edit" style="color:#666666"></i>
                                </a>
                            </nav>
                        </c:if>

                        <div class="embed-responsive embed-responsive-16by9 border">
                            <iframe class="embed-responsive-item" src="${exercise.url}"></iframe>
                        </div>
                    </div>
                    <!--video src="${exercise.url}" controls="controls">
                        您的浏览器不支持 video 标签。
                    </video-->
                </c:when>

                <c:when test="${exercise.type=='markdown'}">
                    <div class="mt-2 mb-2 border">
                        <c:if test="${is_edit}">
                            <nav class="navbar navbar-light bg-faded">
                                <a class="navbar-brand " href="/zm/edit?course=${lesson.courseName}&unit=${lesson.unitName}&lesson=${lesson.lessonName}&md=${exercise.url}">
                                    <i class="fa fa-edit" style="color:#666666"></i>
                                </a>
                            </nav>
                        </c:if>

                        <span>${exercise.html}</span>
                    </div>
                </c:when>

                <c:otherwise>
                    Error Exercise Type [${exercise.type}] <br />
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>
</body>
</html>
