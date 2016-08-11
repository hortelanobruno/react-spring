<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>User Control</title>
        <jsp:include page="/WEB-INF/jspf/header.jsp" />
        <link href="<c:url value='/css/index.css'/>" rel="stylesheet">
        <jsp:include page="/WEB-INF/jspf/toolbar.jsp" />
        <link href="<c:url value='/css/UserControl.css'/>" rel="stylesheet">
        <style type="text/css">
            .error-template {padding: 40px 15px;text-align: center;}
            .error-actions {margin-top:15px;margin-bottom:15px;}
            .error-actions .btn { margin-right:10px; }
        </style>
    </head>
    <body>

        <div id="wrappercentral">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="error-template">
                            <h1>
                                Oops!</h1>
                            <h2>
                                Access Denied!</h2>
                            <div class="error-details">
                                Sorry, an error has occured, You do not have access to this site!
                            </div>
                            <div class="error-actions">
                                <a href="<c:url value='/'/>" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-home"></span>
                                    Take Me Home </a><a href="#" class="btn btn-default btn-lg"><span class="glyphicon glyphicon-envelope"></span> Contact Support </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="footer">
                <jsp:include page="/WEB-INF/jspf/footer.jsp" />
            </div>
        </div>

        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap/bootstrap.min.js"></script>
        <script src="js/Support.js"></script>

    </body>
</html>