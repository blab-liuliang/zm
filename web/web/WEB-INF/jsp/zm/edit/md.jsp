<%--
  Created by IntelliJ IDEA.
  User: ares
  Date: 30/01/2018
  Time: 3:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>


    <!--font awesome-->
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">

    <!--script type="text/javascript" src="/RES/js/editormd/jquery.min.js"></script-->
    <script type="text/javascript" src="/RES/js/editormd/editormd.min.js"></script>

    <script type="text/javascript">
        $(function() {
            editormd("my-editormd", {
                width   : "90%",
                height  : 640,
                syncScrolling : "single",
                path    : "/RES/js/editormd/lib/",//注意2：你的路径
                saveHTMLToTextarea : true//注意3：这个配置，方便post提交表单
            });
        });
    </script>

    <link rel="stylesheet" href="/RES/css/editormd/editormd.css" >
</head>
<body>
    <form:form method="post" action="/zm/modify_md" modelAttribute="markdown">
        <div id="my-editormd" >
            <form:textarea path="content" id="my-editormd-markdown-doc" name="my-editormd-markdown-doc" style="display:none;"></form:textarea>
            <!-- 注意：name属性的值-->
            <textarea id="my-editormd-html-code" name="my-editormd-html-code" style="display:none;"></textarea>
        </div>
        <form:input path="lessonUrl" hidden="true"></form:input>
        <form:input path="url" hidden="true"></form:input>
        <input type="submit" class="btn btn-primary" value="Submit">
    </form:form>
</body>
</html>
