<%@page import="com.callistech.nap.frontend.session.SettingsFacade"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.callistech.nap.frontend.util.NAPConfigurationConstants"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>



<!DOCTYPE html>
<html lang="en">

    <head>
        <!-- JQuery -->
        <script src="<c:url value='/js/jquery.min.js'/>"></script>
        <!-- Procesamiento de datos para Highcharts -->
        <script src="<c:url value='/js/napcharts.js'/>"></script>        
        <!-- HIGHCHARTS-->
        <script src="<c:url value='/js/highcharts/highcharts.js'/>"></script>
        <script src="<c:url value='/js/highcharts/exporting.js'/>"></script>        
    </head>
    <body>

        <script type="text/javascript" >
            // Ejemplo:
            // https://localhost:8181/nap-frontend/singleQuery?templateId=80102&params={3:500,5:%22SCE%22,7:%22DOWNSTREAM%22,8:%22GBITS_PER_SECOND%22,9:%22LINES%22,10:%22YES%22}
            var offsetInMillis = <% out.print(request.getAttribute(SettingsFacade.TIME_ZONE_OFFSET));%>;
            if (offsetInMillis === null) {
                offsetInMillis = 0;
            }


            $(document).ready(function () {
                // Setea URL
                urlAPIPrefix = "${pageContext.request.contextPath}" + '<%=NAPConfigurationConstants.REST_AJAX_PROXY%>';
                // Parsea parametros
                var templateId = ${pageContext.request.getParameter("templateId")};
                var templateParams = ${pageContext.request.getParameter("params")};
                executeSingleQuery(templateId, templateParams);
            });

            function executeSingleQuery(templateId, templateParams) {
                // Carga vector con params para la llamada AJAX
                var vecParams = {};
                vecParams['templateId'] = templateId;
                vecParams['templateParams'] = JSON.stringify(templateParams);
                // Llamada AJAX de ejecucion del query
                $.ajax({type: "post",
                    dataType: "json",
                    async: false,
                    data: vecParams,
                    url: urlAPIPrefix + "/executeSingleQuery",
                    success: function (data) {
                        if (data !== null) {
                            var parsedResult = parseResult(data);

                            if ((parsedResult.series !== null) && (parsedResult.series !== undefined)) {
                                $('#panelNoResult').hide();
                                loadChart(parsedResult);
                            } else {
                                $('#panelNoResult').show();
                            }
                        }
                    },
                    error: function (response) {
                        console.log(JSON.stringify(response));
                    }
                });
            }

            function loadChart(resultQuery) {
                var offsetInMinutes = - (offsetInMillis / 1000 / 60);
                $(function () {
                    Highcharts.setOptions({
                        global: {
                            timezoneOffset: offsetInMinutes
                        }
                    });
                    $('#chartPanel').highcharts(resultQuery);
                });
            }

        </script>

        <!-- Panel principal del grafico -->        
        <div id="chartPanel">
            <!-- Mensaje de aviso de Result null -->
            <div id="panelNoResult" style="text-align: center">
                <a style="font-family: Georgia">No data found for current report template</a>
            </div>          
        </div>

    </body>

</html>
