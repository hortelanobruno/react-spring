<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="<c:url value='/js/jquery.min.js'/>"></script>        
        <link href="<c:url value='/css/bootstrap/bootstrap.min.css'/>" rel="stylesheet">
        <script src="<c:url value='/js/bootstrap/bootstrap.min.js'/>"></script>

        <link href="<c:url value='/css/x-editable/bootstrap-editable.css'/>" rel="stylesheet"/>
        <script src="<c:url value='/js/x-editable/bootstrap-editable.min.js'/>"></script>
        <!--<link href="//cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet"/>-->
        <!--<script src="//cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/js/bootstrap-editable.min.js"></script>-->
        <link href="<c:url value='/css/datetimepicker/bootstrap-datetimepicker.css'/>" rel="stylesheet" type="text/css"></link> 
        <script src="<c:url value='/js/datetimepicker/bootstrap-datetimepicker.min.js'/>"></script>        

    </head>
    <body>
        <script type="text/javascript">
            $(document).ready(function () {
                $('#attributeText').editable();
                $('#attributeCombo').editable({
                    value: 2,
                    source: [
                        {value: 1, text: 'Active'},
                        {value: 2, text: 'Blocked'},
                        {value: 3, text: 'Deleted'}
                    ]
                });
                $('#attributeDatetime').editable({
                    format: 'yyyy-mm-dd hh:ii',
                    viewformat: 'dd/mm/yyyy hh:ii',
                    datetimepicker: {
                        weekStart: 1
                    }
                });
                $('#attributeChecklist').editable({
                    value: [2, 3],
                    source: [
                        {value: 1, text: 'option1'},
                        {value: 2, text: 'option2'},
                        {value: 3, text: 'option3'}
                    ]
                });

            });

        </script>
        <div>
            <br><br><br><br><br><br><br><br><br>
            <table id="tableProperties" class="table table-bordered">
                <thead>
                    <tr>
                        <th>
                            Property
                        </th> 
                        <th>
                            Value
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td></td>
                        <td>
                            <!--<span editable-text="user.name">empty</span>-->
                            <a href="#" id="attributeText" data-type="text" data-pk="1" data-title="Enter username">superuser</a>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><a href="#" id="attributeCombo" data-type="select" data-pk="1" data-title="Select status"></a></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><a href="#" id="attributeDatetime" data-type="datetime" data-pk="1" title="Select date & time">15/03/2013 12:45</a></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><a href="#" id="attributeChecklist" data-type="checklist" data-pk="1" data-title="Select options"></a></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </body>
</html>
