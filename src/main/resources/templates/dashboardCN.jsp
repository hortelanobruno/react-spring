<%@page import="java.util.List"%>
<%@page import="com.callistech.nap.frontend.util.NAPConfigurationConstants"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Network Dashboard</title>
        <jsp:include page="/WEB-INF/jspf/header.jsp" />
        <link href="<c:url value='/css/dashboard/sb-admin-2.css'/>" rel="stylesheet">
        <link href="<c:url value='/css/morris.css'/>" rel="stylesheet">
        <link href="<c:url value='/css/datatables/dataTableEditor.min.css'/>" rel="stylesheet">
        <link href="<c:url value='/css/font-awesome/css/font-awesome.min.css'/>" rel="stylesheet">
        <link href="<c:url value='/css/datatables/editor.bootstrap.css'/>" rel="stylesheet">
        <link href="<c:url value='/css/datatables/select.bootstrap.min.css'/>" rel="stylesheet">
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="/WEB-INF/jspf/toolbar.jsp" />

            <div id="page-wrapper">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Network Dashboard</h1>
                    </div>
                </div>

                <div class="row-fluid">
                    <div class="span12">
                        <div class="panel panel-default">
                            <div class="panel-body">

                                <table id="tableNetwork" class="table table-bordered table-style-custom" cellspacing="0" width="100%">
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th>Hub</th>
                                            <th>CMTS</th>
                                            <th>Utilization</th>
                                            <th>Hours of Actuation</th>
                                            <th>Virtual Link Id</th>
                                            <th>Ideal Provisioning</th>
                                            <th>Actual Provisioning </th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr>
                                            <th></th>
                                            <th>Hub</th>
                                            <th>CMTS</th>
                                            <th>Utilization</th>
                                            <th>Hours of Actuation</th>
                                            <th>Virtual Link Id</th>
                                            <th>Ideal Provisioning</th>
                                            <th>Actual Provisioning</th>
                                        </tr>
                                    </tfoot>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <script src="<c:url value='/js/datatables/dataTables.min.js'/>"></script>
    <script src="<c:url value='/js/jquery.sparkline.js'/>"></script>
    <script src="<c:url value='/js/datatables/dataTablesBootstrap3Integration.js'/>"></script>
    <script src="<c:url value='/js/datatables/dataTables.select.min.js'/>"></script>
    <script src="<c:url value='/js/datatables/dataTables.editor.min.js'/>"></script>
    <script src="<c:url value='/js/datatables/editor.bootstrap.js'/>"></script>
    <style>
        tfoot input {
            width: 100%;
            padding: 3px;
            box-sizing: border-box;
        }

        td.details-control {
            background: url('<c:url value='/img/details_open.png'/>') no-repeat center center;
            cursor: pointer;
        }
        tr.shown td.details-control {
            background: url('<c:url value='/img/details_close.png'/>') no-repeat center center;
        }
    </style>

    <script>
        var urlAPIPrefix;
        var table;
        var myvalues = [10, 8, 5, 7, 4, 4, 1];
        var editor;
        var connection;
        $(document).ready(function () {
            urlAPIPrefix = "${pageContext.request.contextPath}" + '<%=NAPConfigurationConstants.REST_AJAX_PROXY%>';
            WSconnect();
            $('#tableNetwork tfoot th').each(function () {
                var title = $(this).text();
                $(this).html('<input type="text" placeholder="Search ' + title + '" />');
            });

            editor = new $.fn.dataTable.Editor({
                "ajax": urlAPIPrefix + "/getDashboardCN",
                table: "#tableNetwork",
                fields: [{
                        label: "Ideal Provisioning",
                        name: "idealProvisioning"
                    }, {
                        label: "Actual Provisioning",
                        name: "actualProvisioning"
                    }
                ]
            });

            $('#tableNetwork').on('click', 'tbody td:not(:first-child)', function (e) {
                editor.inline(this, {
                    onBlur: 'submit'
                });
            });

            table = $('#tableNetwork').DataTable({
                "ajax": urlAPIPrefix + "/getDashboardCN",
                "columns": [
                    {
                        "className": 'details-control',
                        "orderable": false,
                        "data": null,
                        "defaultContent": ''
                    },
                    {"data": "name"},
                    {"data": "cmtsss"},
                    {
                        "mRender": function (value) {
                            return "22";
                        }

                    },
                    {"data": "actuacion"},
                    {
                        "mRender": function (value) {
                            return "5";
                        }

                    },
                    {"data": "idealProvisioning"},
                    {"data": "actualProvisioning"},
                    {"data": "id", "visible": false}
                ],
                select: {
                    style: 'os',
                    selector: 'td:first-child'
                },
                "order": [[1, 'asc']]
            });

            table.columns().every(function () {
                var that = this;

                $('input', this.footer()).on('keyup change', function () {
                    if (that.search() !== this.value) {
                        that
                                .search(this.value)
                                .draw();
                    }
                });
            });
        });

        $(window).unload(function () {
            connection.send("Close Connection");
            connection.close();
            console.log("Connection Closed");
        });

        $('#tableNetwork tbody').on('click', 'td.details-control', function () {
            var tr = $(this).closest('tr');
            var row = table.row(tr);

            if (row.child.isShown()) {
                row.child.hide();
                tr.removeClass('shown');
            } else {
                row.child(format(row.data())).show();
                tr.addClass('shown');
                $('.dynamicsparkline').sparkline(myvalues, {width: '50px', height: '50px'});
//
            }
        }
        );
        function format(d) {

            var data = [];

            $.each(d.cmtss, function (i, item) {

                data.push('<table cellpadding="5" class="table table-striped table-bordered table-style-custom" cellspacing="0" border="5" style="padding-left:50px; ">' +
                        '<tr>' +
                        '<td>' + d.cmtss[i].name + '</td>' +
                        '<td></td>' +
                        '<td>' + d.cmtss[i].utilization + '</td>' +
                        '<td>' + d.cmtss[i].actuacion + '</td>' +
                        '<td>' + d.cmtss[i].vlinkId + '</td>' +
                        '<td>' + d.cmtss[i].vlinkIdealPir + '</td>' +
                        '<td class="dynamicsparkline">' + d.cmtss[i].vlinkPir + '</td>' +
                        '</tr>' +
                        '</table>');
            });
            return data;
        }

        function WSconnect() {
            console.log("Connecting to WS...");
            connection = new WebSocket("wss://" + document.location.host + "/nap-frontend/websocket");

            connection.onerror = function (error) {
                console.log('WebSocket Error ');
                console.log(error);
            };
            connection.onmessage = function (e) {
                console.log('Server: ' + e.data);
//                table
//                        .clear()
//                        .draw();
                table.ajax.reload();
            };
            connection.onopen = function (e) {
                console.log("Connected to WS!");
                connection.send("New Connection");
            };
            connection.onclose = function (e) {
                console.log("Disconnected from WS");
            };
        }
    </script>
</body>
</html>
