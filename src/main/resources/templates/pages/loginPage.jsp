<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <link rel="icon" href="<c:url value='/img/logo4.png'/>">
        <title>CSM Analytics Console</title>
        <jsp:include page="/WEB-INF/jspf/header.jsp" />
        <link href="<c:url value='/css/login.css'/>" rel="stylesheet" />
        <!-- Bootstrap core CSS -->
        <link href="<c:url value='/css/bootstrap/bootstrap.min.css'/>" rel="stylesheet">
        <!-- Custom styles for this template -->
        <link href="<c:url value='/css/jumbotron.css'/>" rel="stylesheet">
        <script src="<c:url value='/js/ie-emulation-modes-warning.js'/>"></script>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-sm-6 col-md-4 col-md-offset-4">
                    <div class="span9">

                    </div>
                    <div class="account-wall">
                        <div class="logotop">
                            <img class="profile-img" src="<c:url value='/img/logo4.png'/>" alt="">
                            <div class="profile-text" style="float: none !important">
                                <h1>CSM Analytics Console</h1>
                                <p>Callis Technologies</p>
                            </div>
                        </div>
                        <form class="form-signin" action="<c:url value='/j_spring_security_check'/>" method="post">
                            <input type="text"  name="username" class="form-control" placeholder="User">
                            <input type="password" name="password" class="form-control" placeholder="Password">
                            <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
                            <%
                                String invalidUsername = request.getParameter("error");
                                if (invalidUsername != null && Boolean.valueOf(invalidUsername)) {
                            %>
                            <div id="wrongPass" style="
                                 position: relative;
                                 top: 13px;
                                 text-align: center;
                                 "><span style="color: #F03;">The username or password is incorrect.

                                    Or you do not have access to this website.</span></div>
                                    <% }%>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- Bootstrap core JavaScript
================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="<c:url value='/js/jquery.min.js'/>"></script>
        <script src="<c:url value='/js/bootstrap/bootstrap.min.js'/>"></script>
        <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
        <script src="<c:url value='/js/ie10-viewport-bug-workaround.js'/>"></script>
    </body>
</html>
