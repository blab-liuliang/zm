<%--
  Created by IntelliJ IDEA.
  User: ares
  Date: 30/01/2018
  Time: 3:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <!--font awesome-->
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">

    <script type="text/javascript" src="/RES/js/editormd/jquery.min.js"></script>
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
    <div id="my-editormd" >
        <textarea id="my-editormd-markdown-doc" name="my-editormd-markdown-doc" style="display:none;">${md_content}</textarea>
        <!-- 注意：name属性的值-->
        <textarea id="my-editormd-html-code" name="my-editormd-html-code" style="display:none;"></textarea>
    </div>
</body>
</html>
