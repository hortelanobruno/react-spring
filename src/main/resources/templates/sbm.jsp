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
        <title>CAM Dashboard</title>
        <jsp:include page="/WEB-INF/jspf/header.jsp" />
        <link href="<c:url value='/css/dashboard/sb-admin-2.css'/>" rel="stylesheet">
        <link href="<c:url value='/css/morris.css'/>" rel="stylesheet">
        <link href="<c:url value='/css/font-awesome/css/font-awesome.min.css'/>" rel="stylesheet">
        <link href="<c:url value='/css/circle.css'/>" rel="stylesheet">
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="/WEB-INF/jspf/toolbar.jsp" />

            <div id="page-wrapper">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">CAM Dashboard</h1>
                    </div>
                </div>

                <div class="row" style="position: relative;left: 400px;margin-bottom: 20px;">
                    <div class="col-lg-1 col-md-6" id="fidelizationCircle">
                    </div>
                    <div class="col-lg-1 col-md-6" id="newUserCircle">
                    </div>
                    <div class="col-lg-1 col-md-6" id="mobileCircle">
                    </div>
                    <div class="col-lg-1 col-md-6" id="laptopCircle">
                    </div>
                    <div class="col-lg-1 col-md-6"id="maleCircle">
                    </div>
                    <div class="col-lg-1 col-md-6"id="femalecircle">
                    </div>
                </div>


                <div class="row">
                    <div class="col-lg-4">
                        <div class="panel panel-default" style="
                             max-height: 420px;
                             ">
                            <div class="panel-heading">
                                <i class="fa fa-th-list fa-fw"></i> Summary
                            </div>
                            <div class="panel-body">
                                <div class="list-group" id="summarySection">

                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-8 col-md-1">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <i class="fa fa-area-chart fa-fw"></i> Users And Connections
                            </div>
                            <div class="panel-body">
                                <div id="morris-area-chart"></div>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-4">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <i class="fa fa-users fa-fw"></i> Top Users
                            </div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="list-group" id="topUsersSection">


                                </div>
                                <!-- /.list-group -->
                            </div>
                            <!-- /.panel-body -->
                        </div>
                    </div>

                    <div class="col-lg-4">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <i class="fa fa-plug fa-fw"></i> Last Connection
                            </div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="list-group" id="lastConnectionSection">

                                </div>
                                <!-- /.list-group -->
                            </div>
                            <!-- /.panel-body -->
                        </div>
                    </div>

                    <div class="col-lg-4">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <i class="fa fa-pie-chart fa-fw"></i> Access per Age
                            </div>
                            <div class="panel-body">
                                <div id="morris-donut-chart"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <script src="<c:url value='/js/morris.min.js'/>"></script>
        <script src="<c:url value='/js/raphael-min.js'/>"></script>
        <script>
            var urlAPIPrefix;
            $(document).ready(function () {
                urlAPIPrefix = "${pageContext.request.contextPath}" + '<%=NAPConfigurationConstants.REST_AJAX_PROXY%>';
                getDashboardInfo();
            });
            function getDashboardInfo() {
                $.ajax({data: {
                        "dashboardCAMParams": "{'lastConnectionsParams':{'lastConnectionsResultQuantity':8},'topUsersParams':{'topUsersResultQuantity':8}}"
                    },
                    type: "get",
                    async: false,
                    url: urlAPIPrefix + "/getDashboardCAM",
                    success: function (data) {
                        if (data !== undefined && data !== "") {

                            if (data.lastConnectionsData !== undefined && data.lastConnectionsData !== "") {
                                if (data.lastConnectionsData.users !== undefined && data.lastConnectionsData.users !== "") {
                                    setLastConnection(data.lastConnectionsData.users);
                                }
                            } else {
                                setLastConnection("N/A");
                            }

                            if (data.topUsersData !== undefined && data.topUsersData !== "") {
                                if (data.topUsersData.users !== undefined && data.topUsersData.users !== "") {
                                    setTopUsers(data.topUsersData.users);
                                }
                            } else {
                                setTopUsers("N/A");
                            }

                            if (data.summaryData !== undefined && data.summaryData !== "") {
                                if (data.summaryData.dataList !== undefined && data.summaryData.dataList !== "") {
                                    setSummary(data.summaryData.dataList);
                                }
                            } else {
                                setSummary("N/A");
                            }

                            if (data.usersAndConnectionsData !== undefined && data.usersAndConnectionsData !== "") {
                                if (data.usersAndConnectionsData.periods !== undefined && data.usersAndConnectionsData.periods !== "") {
                                    areaChart(data.usersAndConnectionsData.periods);
                                }
                            } else {
                                areaChart("N/A");
                            }

                            if (data.connectionsStats !== undefined && data.connectionsStats !== "") {
                                if (data.connectionsStats.stats !== undefined && data.connectionsStats.stats !== "") {
                                    setCircles(data.connectionsStats.stats);
                                }
                            } else {
                                setCircles("N/A");
                            }

                            if (data.ageData !== undefined && data.ageData !== "") {
                                if (data.ageData.periods !== undefined && data.ageData.periods !== "") {
                                    donutChart(data.ageData.periods);
                                }
                            } else {
                                donutChart("N/A");
                            }

                        }
                    },
                    error: function (response) {
                                var str = response.responseText + "";
                                var pos = str.lastIndexOf("Login");
                                if (pos !== -1) {
                                    window.location = window.location.href;
                                }
                            }
                });
            }

            function donutChart(data) {
                var chartInfo = [];
                if (data !== undefined && data !== "" && data !== "N/A") {
                    $.each(data, function (i, item) {
                        chartInfo.push({
                            "label": data[i].periodDescription,
                            "value": data[i].periodPercentage
                        });
                    });
                    Morris.Donut({
                        element: 'morris-donut-chart',
                        data: chartInfo,
                        resize: true,
                        colors: ["#cccc00", "#cc0000", "#009933", "#0033cc", "#ff6600"],
                        formatter: function (value, data) {
                            return (value) + '%';
                        }
                    });
                }

                if (data === "N/A") {
                    $("#morris-donut-chart").append("<a class='list-group-item'> <i class='fa fa-exclamation-circle fa-fw'></i> Not Available <span class='pull-right text-muted small'><em></em> </span></a>");
                }
            }
            function areaChart(data) {
                var chartInfo = [];
                if (data !== undefined && data !== "" && data !== "N/A") {
                    $.each(data, function (i, item) {
                        var d = new Date(data[i].usersAndConnectionsChartPeriod * 1);
                        chartInfo.push({
                            "period": d.getTime(),
                            "Users": data[i].usersAndConnectionsChartUsers,
                            "Connections": data[i].usersAndConnectionsChartConnections
                        });
                    });
                    Morris.Area({
                        element: 'morris-area-chart',
                        data: chartInfo,
                        xkey: 'period',
                        ykeys: ['Users', 'Connections'],
                        labels: ['Users', 'Connections'],
                        pointSize: 4,
                        hideHover: 'auto',
                        resize: true,
                        lineColors: ["#ffff66", "#ff6666", "#66ff99", "#668cff", "#ffa366"]
                    });
                }

                if (data === "N/A") {
                    $("#morris-area-chart").append("<a class='list-group-item'> <i class='fa fa-exclamation-circle fa-fw'></i> Not Available <span class='pull-right text-muted small'><em></em> </span></a>");
                }


            }
            function setTopUsers(data) {
                if (data !== undefined && data !== "" && data !== "N/A") {
                    $.each(data, function (i, item) {
                        if (data[i].gender === "MALE") {
                            $("#topUsersSection").append("<a class='list-group-item' title='Gender: Male'> <i class='fa fa-male fa-fw'></i> " + data[i].userName + " <span class='pull-right text-muted small'><em>" + data[i].totalAccesses + " Total Accesses</em> </span></a>");
                        }

                        if (data[i].gender === "FEMALE") {
                            $("#topUsersSection").append("<a class='list-group-item' title='Gender: Female'> <i class='fa fa-female fa-fw'></i> " + data[i].userName + " <span class='pull-right text-muted small'><em>" + data[i].totalAccesses + " Total Accesses</em> </span></a>");
                        }

                        if (data[i].gender === "UNKNOWN") {
                            $("#topUsersSection").append("<a class='list-group-item' title='Gender: Unknown'> <i class='fa fa-question fa-fw'></i> " + data[i].userName + " <span class='pull-right text-muted small'><em>" + data[i].totalAccesses + " Total Accesses</em> </span></a>");
                        }
                    });
                }

                if (data === "N/A") {
                    $("#topUsersSection").append("<a class='list-group-item'> <i class='fa fa-exclamation-circle fa-fw'></i> Not Available <span class='pull-right text-muted small'><em></em> </span></a>");
                }
            }
            function setLastConnection(data) {
                if (data !== undefined && data !== "" && data !== "N/A") {
                    $.each(data, function (i, item) {
                        var d = new Date(data[i].timestamp * 1);
                        if (data[i].gender === "MALE") {
                            $("#lastConnectionSection").append("<a class='list-group-item' title='Gender: Male'> <i class='fa fa-male fa-fw'></i> " + data[i].userName + " <span class='pull-right text-muted small'><em>" + d.toLocaleDateString() + "</em> </span></a>");
                        }

                        if (data[i].gender === "FEMALE") {
                            $("#lastConnectionSection").append("<a class='list-group-item' title='Gender: Female'> <i class='fa fa-female fa-fw'></i> " + data[i].userName + " <span class='pull-right text-muted small'><em>" + d.toLocaleDateString() + "</em> </span></a>");
                        }

                        if (data[i].gender === "UNKNOWN") {
                            $("#lastConnectionSection").append("<a class='list-group-item' title='Gender: Unknown'> <i class='fa fa-question fa-fw'></i> " + data[i].userName + " <span class='pull-right text-muted small'><em>" + d.toLocaleDateString() + "</em> </span></a>");
                        }

                    });
                }

                if (data === "N/A") {
                    $("#lastConnectionSection").append("<a class='list-group-item'> <i class='fa fa-exclamation-circle fa-fw'></i> Not Available <span class='pull-right text-muted small'><em></em> </span></a>");
                }
            }
            function setSummary(data) {
                if (data !== undefined && data !== "" && data !== "N/A") {
                    $.each(data, function (i, item) {

                        if (data[i].summaryEventType === "SUMMARY_TODAY_NEW_USERS") {
                            $("#summarySection").append("<a class='list-group-item'> <div class='panel-red'> <div class='panel-heading fa'> <i class='fa fa-user fa-1x'></i> </div> " + data[i].summaryQuantity + " " + data[i].summaryDescription + " </div> </a>");
                        }
                        if (data[i].summaryEventType === "SUMMARY_TOTAL_USERS") {
                            $("#summarySection").append("<a class='list-group-item'> <div class='panel-red'> <div class='panel-heading fa'> <i class='fa fa-users fa-1x'></i> </div> " + data[i].summaryQuantity + " " + data[i].summaryDescription + " </div> </a>");
                        }
                        if (data[i].summaryEventType === "SUMMARY_TODAY_CONNECTIONS") {
                            $("#summarySection").append("<a class='list-group-item'> <div class='panel-info'> <div class='panel-heading fa'> <i class='fa fa-plug fa-1x'></i> </div> " + data[i].summaryQuantity + " " + data[i].summaryDescription + " </div> </a>");
                        }
                        if (data[i].summaryEventType === "SUMMARY_TOTAL_CONNECTIONS") {
                            $("#summarySection").append("<a class='list-group-item'> <div class='panel-info'> <div class='panel-heading fa'> <i class='fa fa-globe fa-1x'></i> </div> " + data[i].summaryQuantity + " " + data[i].summaryDescription + " </div> </a>");
                        }
                        if (data[i].summaryEventType === "SUMMARY_AVERAGE_NEW_USERS_PER_DAY") {
                            $("#summarySection").append("<a class='list-group-item'> <div class='panel-primary'> <div class='panel-heading fa'> <i class='fa fa-area-chart fa-1x'></i> </div> " + data[i].summaryQuantity + " " + data[i].summaryDescription + " </div> </a>");
                        }
                        if (data[i].summaryEventType === "SUMMARY_AVERAGE_CONNECTIONS_PER_USER") {
                            $("#summarySection").append("<a class='list-group-item'> <div class='panel-primary'> <div class='panel-heading fa'> <i class='fa fa-bar-chart fa-1x'></i> </div> " + data[i].summaryQuantity + " " + data[i].summaryDescription + " </div> </a>");
                        }

//LIMITADOR DE CANTIDAD DE SuMMARY MOSTRADOS
                        if (i === 5) {
                            return false;
                        }
                    });
                }

                if (data === "N/A") {
                    $("#summarySection").append("<a class='list-group-item'> <i class='fa fa-exclamation-circle fa-fw'></i> Not Available <span class='pull-right text-muted small'><em></em> </span></a>");
                }
            }
            function setCircles(data) {
                if (data !== undefined && data !== "" && data !== "N/A") {
                    $.each(data, function (i, item) {
                        if (data[i] !== undefined && data[i] !== "") {

                            if (data[i].type === "FIDELITY_CLIENTS") {
                                if (data[i].type !== undefined && data[i].type !== "") {
                                    $("#fidelizationCircle").append("<div class='c100 p" + data[i].percentage + " medium blue' title='Loyal Customers'><span> " + data[i].percentage + "% <i class='fa fa-list'></i></span><div class='slice'><div class='bar'></div><div class='fill'></div></div></div>");
                                } else {
                                    $("#fidelizationCircle").append("<div class='c100 p0 medium blue' title='Loyal Customers'><span> N/A </span><div class='slice'><div class='bar'></div><div class='fill'></div></div></div>");
                                }
                            }

                            if (data[i].type === "NEW_CLIENTS") {
                                if (data[i].type !== undefined && data[i].type !== "") {
                                    $("#newUserCircle").append("<div class='c100 p" + data[i].percentage + " medium blue'title='New Customers' ><span> " + data[i].percentage + "% <i class='fa fa-bullseye'></i></span><div class='slice'><div class='bar'></div><div class='fill'></div></div></div>");
                                } else {
                                    $("#newUserCircle").append("<div class='c100 p0 medium blue'title='New Customers' ><span> N/A </span><div class='slice'><div class='bar'></div><div class='fill'></div></div></div>");
                                }
                            }

                            if (data[i].type === "MOBILE") {
                                if (data[i].type !== undefined && data[i].type !== "") {
                                    $("#mobileCircle").append("<div class='c100 p" + data[i].percentage + " medium green'title='Mobile Connections' ><span> " + data[i].percentage + "% <i class='fa fa-mobile'></i></span><div class='slice'><div class='bar'></div><div class='fill'></div></div></div>");
                                } else {
                                    $("#mobileCircle").append("<div class='c100 p0 medium green'title='Mobile Connections' ><span> N/A </span><div class='slice'><div class='bar'></div><div class='fill'></div></div></div>");
                                }
                            }

                            if (data[i].type === "COMPUTER") {
                                if (data[i].type !== undefined && data[i].type !== "") {
                                    $("#laptopCircle").append("<div class='c100 p" + data[i].percentage + " medium green'title='Desktop Connections'><span> " + data[i].percentage + "% <i class='fa fa-laptop'></i></span><div class='slice'><div class='bar'></div><div class='fill'></div></div></div>");
                                } else {
                                    $("#laptopCircle").append("<div class='c100 p0 medium green'title='Desktop Connections'><span> N/A </span><div class='slice'><div class='bar'></div><div class='fill'></div></div></div>");
                                }
                            }

                            if (data[i].type === "MALE") {
                                if (data[i].type !== undefined && data[i].type !== "") {
                                    $("#maleCircle").append("<div class='c100 p" + data[i].percentage + " medium'title='Male Users'><span> " + data[i].percentage + "% <i class='fa fa-male'></i></span><div class='slice'><div class='bar'></div><div class='fill'></div></div></div>");
                                } else {
                                    $("#maleCircle").append("<div class='c100 p0 medium'title='Male Users'><span> N/A </span><div class='slice'><div class='bar'></div><div class='fill'></div></div></div>");
                                }
                            }

                            if (data[i].type === "FEMALE") {
                                if (data[i].type !== undefined && data[i].type !== "") {
                                    $("#femalecircle").append("<div class='c100 p" + data[i].percentage + " medium pink'title='Female Users'><span> " + data[i].percentage + "% <i class='fa fa-female'></i></span><div class='slice'><div class='bar'></div><div class='fill'></div></div></div>");
                                } else {
                                    $("#femalecircle").append("<div class='c100 p0 medium pink'title='Female Users'><span> N/A </span><div class='slice'><div class='bar'></div><div class='fill'></div></div></div>");
                                }
                            }

                        }
                    });
                }

                if (data === "N/A") {
                    $("#fidelizationCircle").append("<div class='c100 p0 medium blue' title='Loyal Customers'><span> N/A </span><div class='slice'><div class='bar'></div><div class='fill'></div></div></div>");
                    $("#newUserCircle").append("<div class='c100 p0 medium blue'title='New Customers' ><span> N/A </span><div class='slice'><div class='bar'></div><div class='fill'></div></div></div>");
                    $("#mobileCircle").append("<div class='c100 p0 medium green'title='Mobile Connections' ><span> N/A </span><div class='slice'><div class='bar'></div><div class='fill'></div></div></div>");
                    $("#laptopCircle").append("<div class='c100 p0 medium green'title='Desktop Connections'><span> N/A </span><div class='slice'><div class='bar'></div><div class='fill'></div></div></div>");
                    $("#maleCircle").append("<div class='c100 p0 medium'title='Male Users'><span> N/A </span><div class='slice'><div class='bar'></div><div class='fill'></div></div></div>");
                    $("#femalecircle").append("<div class='c100 p0 medium pink'title='Female Users'><span> N/A </span><div class='slice'><div class='bar'></div><div class='fill'></div></div></div>");
                }
            }
        </script>
    </body>
</html>
