<%@page import="com.callistech.nap.frontend.util.NAPConfigurationConstants"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <jsp:include page="/WEB-INF/jspf/header.jsp" />
        <jsp:include page="/WEB-INF/jspf/toolbar.jsp" />
        <link href="<c:url value='/css/bubbleMap.css'/>" rel="stylesheet" />
        <!--SPLITPANE-->
        <script type="text/javascript" src="<c:url value='/js/splitpane/jquery-ui-latest.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/js/splitpane/jquery.layout-1.3.0.rc30.80.js'/>"></script>
        <link href="<c:url value='/css/splitpane/layout-default-latest.css'/>" rel="stylesheet" />
        <!--DATETIMEPICKER-->
        <link href="<c:url value='/css/datetimepicker/bootstrap-datetimepicker.css'/>" rel="stylesheet" type="text/css"></link>
        <script src="<c:url value='/js/datetimepicker/bootstrap-datetimepicker.min.js?t=20130302'/>"></script>
        <!--VIS-->
        <script type="text/javascript"  src="<c:url value='/js/vis/vis.js'/>"></script>
        <link href="<c:url value='/css/vis/vis.css'/>" rel="stylesheet" type="text/css" />
        <style type="text/css">
            #mynetwork {
                width: auto;
                height: 100%;
                border: 1px solid lightgray
            }
        </style>
        <script type="text/javascript" >
            /////////////////////// DEFINICION DE VARIABLES GLOBALES ///////////////////////////////////

            var urlAPIPrefix = null;
            var levels = null; // list<levelName>
            var layers = null; // map<levelName, List<layer>>
            var nodes = null;
            var edges = null;
            var network = null; // Contiene la data del grafico (network.nodes / network.edges)

            var LENGTH_MAIN = 100;
            var LENGTH_SUB = 115;
            var colors = ['darkgrey', 'red', 'yellow', 'green'];

            $(document).ready(function () {  // INICIO FUNCION READY
                // SETEO DE URL-PREFIX
                urlAPIPrefix = "${pageContext.request.contextPath}" + '<%=NAPConfigurationConstants.REST_AJAX_PROXY%>';

                // SPLIT-PANE
                $('#layoutNetwork').layout({
                    center__paneSelector: ".outer-center"
                    , west__paneSelector: ".outer-west"
                    , east__paneSelector: ".outer-east"
                    , west__size: 125
                    , east__size: 125
                    , spacing_open: 8  // ALL panes
                    , spacing_closed: 12 // ALL panes
                            //,	north__spacing_open:	0
                            //,	south__spacing_open:	0
                    , north__maxSize: 200
                    , south__maxSize: 200

                            // MIDDLE-LAYOUT (child of outer-center-pane)
                    , center__childOptions: {
                        center__paneSelector: ".middle-center"
                        , west__paneSelector: ".middle-west"
                        , east__paneSelector: ".middle-east"
                        , west__size: 350
                        , east__size: 100
                        , spacing_open: 8  // ALL panes
                        , spacing_closed: 12 // ALL panes
                    }
                });

                // Carga de los componentes a izquierda
                $(function () {
                    $('#datetime').datetimepicker({
                        autoclose: true,
                        todayBtn: true,
                        weekStart: 1 // Arranca lunes
                    });
                });
                getLevels();
                getLayers();
                loadLevels();
                loadLayers();
                $('#comboLevels').change(changeComboEvent);
            });// FIN FUNCION READY

            ////////////////////////////////// FUNCIONES JAVASCRIPT ///////////////////////////////////////////////////

            /**
             * @argument {string} url Es la url donde se apunta el Get Ajax
             * @return {resultado} Es el resultado de la ejecucion del Get Ajax
             */
            function executeGetAjax(url) {
                // LLAMADA AJAX GETs (levels)
                var resultado = null;
                $.ajax({type: "get",
                    dataType: "json",
                    async: false,
                    url: urlAPIPrefix + url,
                    success: function (data) {
                        resultado = data;
                    },
                    error: function (response) {
                        var str = response.responseText + "";
                        var pos = str.lastIndexOf("Login");
                        if (pos !== -1) {
                            window.location = window.location.href;
                        }
                    }
                });
                return resultado;
            }

            /*
             * @argument {string} url Es la url donde se apunta el Post Ajax
             * @argument {string} vecParams es el vector con los parametros para la ejeucion del Post Ajax
             * @return {resultado} Es el resultado de la ejecucion del Get Ajax
             */
            function executePostAjax(url, vecParams) {
                // LLAMADA AJAX POSTs
                var resultado = null;
                $.ajax({type: "post",
                    dataType: "json",
                    async: false,
                    data: vecParams,
                    url: urlAPIPrefix + url,
                    success: function (data) {
                        resultado = data;
                    },
                    error: function (response) {
                        var str = response.responseText + "";
                        var pos = str.lastIndexOf("Login");
                        if (pos !== -1) {
                            window.location = window.location.href;
                        }
                    }
                });
                return resultado;
            }

            /**
             * Obtiene los levels del server
             *
             */
            function getLevels() {
                var resultado = executeGetAjax('/getNetworkLevels');
                levels = [];
                for (var resultadoIndex in resultado) {
                    levels.push(resultado[resultadoIndex]);
                }
            }

            /**
             * Obtiene los layers
             *
             */
            function getLayers() {
                var resultado = null;
                var vecParams = null;
                var vector = null;
                layers = {};

                for (var levelIndex in levels) {
                    vecParams = {};
                    vecParams['level'] = levels[levelIndex];
                    resultado = executePostAjax('/getNetworkTemplatesByLevel', vecParams);
                    if ((resultado !== null) && (resultado !== undefined)) {
                        vector = [];
                        for (var resultadoIndex in resultado) {
                            for (var templateInfoIndex in resultado[resultadoIndex].templateInfos) {
                                vector.push({id: resultado[resultadoIndex].templateInfos[templateInfoIndex].id, name: resultado[resultadoIndex].templateInfos[templateInfoIndex].name});
                            }
                        }
                        layers[levels[levelIndex]] = vector;
                    }
                }
            }

            /**
             * Carga el combo de levels
             *
             */
            function loadLevels() {

                for (var levelIndex in levels) {
                    $('#comboLevels').append($('<option>', {
                        value: levels[levelIndex],
                        text: levels[levelIndex]
                    }));
                }

            }


            /**
             * Carga los radio buttons en funcion de lo que tenga el combo de levels
             *
             */
            function loadLayers() {
                var selectetdLevel = getSelectedLevel();
                for (var layerIndex in layers[selectetdLevel]) {
                    $('#formLayer').append($('<input>', {
                        checked: false,
                        value: layers[selectetdLevel][layerIndex].id,
                        type: 'radio',
                        name: 'radioButtonLayer'
                    }));
                    $('#formLayer').append(' ');
                    $('#formLayer').append('<span>' + layers[selectetdLevel][layerIndex].name + '</span>');
                    $('#formLayer').append('<br>');
                }

//                $('#formLayer :input')[0].attr("checked", true);
                $('#formLayer :input')[0].checked = true;
                loadCheckboxPonderateSite(selectetdLevel);
            }

            function loadCheckboxPonderateSite(selectetdLevel) {
                //<input type="checkbox" id="myCheck"> <span>etiqueta del checkbox</span>
                if (selectetdLevel === 'Devices') {
                    $('#formLayer').append('<br>');
                    $('#formLayer').append($('<input>', {
                        checked: false,
                        type: 'checkbox',
                        id: 'checkBoxSiteHighlight'
                    }));
                    $('#formLayer').append(' ');
                    $('#formLayer').append('<span>Ponderate Sites</span>');
                    $('#formLayer').append('<br>');
                }
            }

            function changeComboEvent() {
                clearLayers();
                loadLayers();
            }

            function clearLayers() {
                $('#formLayer input').remove();
                $('#formLayer span').remove();
                $('#formLayer br').remove();
            }

            function getSelectedLayer() {
                return $('input:checked', '#formLayer').val();
            }

            function getDateTime() {
                var milliseconds = null;
                var dateTime = $('#datetime :input').val();

                if ((dateTime !== null) && (dateTime !== undefined) && (dateTime !== "")) {
                    var dateArgs = dateTime.match(/\d{2,4}/g);
                    var year = dateArgs[0];
                    var month = parseInt(dateArgs[1]) - 1;
                    var day = dateArgs[2];
                    var hour = dateArgs[3];
                    var minutes = dateArgs[4];

                    milliseconds = new Date(year, month, day, hour, minutes).getTime();
                }

                return milliseconds;
            }

            function execute() {
                var layer = null;
                var dateTime = null;

                // Obtiene parametros para ejecutar el query
                layer = getSelectedLayer();
                dateTime = getDateTime();

                if (isValid(layer, dateTime)) {
                    var vecParams = {};
                    vecParams['templateId'] = layer;
                    vecParams['datetime'] = dateTime;

                    var resultado = executePostAjax('/executeNetworkReportTemplate', vecParams);
                    if ((resultado !== null) && (resultado !== null) && (resultado.elements !== null) && (resultado.elements !== undefined)) {
                        draw(resultado);
                    } else {
                        showDialogInformation();
                    }
                } else {
                    showDialogInformation();
                }
            }

            function draw(resultado) {
                var element = null;
                var options = null;

                nodes = [];
                edges = [];

                // create a network
                var container = document.getElementById('mynetwork');
                var data = {
                    nodes: nodes,
                    edges: edges
                };

                for (var elementIndex in resultado.elements) {

                    element = resultado.elements[elementIndex];
                    if (element.node.isSiteNode) {
                        drawSite(element, resultado.level);
                    } else {
                        drawDevice(element);
                    }

                }

                options = buildOptions();
                network = new vis.Network(container, data, options);
            }

            function drawSite(element, level) {
                var color = null;
                var radiusValue = null;
                var newData = null;


                radiusValue = 15;
                color = colors[0];
                if (level === 'Sites') {
                    radiusValue = calculateRadiusValue(element);
                    color = colors[element.quality];
                } else { // Level Devices
                    if ($('#checkBoxSiteHighlight').is(':checked')) {
                        radiusValue = calculateRadiusValue(element);
                        color = colors[element.quality];
                    }
                }

                newData = {
                    id: element.node.nodeId,
                    deviceId: element.node.device.id,
                    label: element.node.device.name,
                    color: {// DEPENDE DE LOS VALORES DE LAS CATEGORIAS
                        background: color,
                        border: '#666666'
                    },
                    fontSize: 20,
                    shape: 'dot',
                    radius: radiusValue * 2 // DEPENDE DEL VALOR RESULTANTE EN ESCALA (valor * 100)
                }; // alter the data as you want.

                nodes.push(newData);
                buildEdges(element);
            }

            function drawDevice(element) {
                var color = null;
                var radiusValue = null;
                var newData = null;

                radiusValue = calculateRadiusValue(element);
                color = colors[element.quality];

                newData = {
                    id: element.node.nodeId,
                    deviceId: element.node.device.id,
                    label: element.node.device.name + ' [' + element.node.device.ipAddress + ']',
                    color: {// DEPENDE DE LOS VALORES DE LAS CATEGORIAS
                        background: color,
                        border: '#666666'
                    },
                    fontSize: 14,
                    shape: 'dot',
                    radius: radiusValue * 2 // DEPENDE DEL VALOR RESULTANTE EN ESCALA (valor * 100)
                }; // alter the data as you want.

                nodes.push(newData);
                buildEdges(element);
            }

            function calculateRadiusValue(element) {
                var radiusValue = null;

                radiusValue = element.relativeValue;
                if (element.relativeValue === 0) {
                    radiusValue = 10;
                } else if (element.relativeValue < 1) {
                    radiusValue = element.relativeValue * 100;
                }

                return radiusValue;
            }

            function buildEdges(element) {
                var relatedNodeId = null;

                if (element.node.isSiteNode) {
                    // Site
                    // Site to Site
                    for (var relatedSiteIndex in element.node.relatedSites) {
                        relatedNodeId = element.node.relatedSites[relatedSiteIndex];
                        if (element.node.nodeId > relatedNodeId) {
                            edges.push({from: element.node.nodeId, to: relatedNodeId, length: LENGTH_MAIN, color: '#666666'});
                        }
                    }
                    // Site to Device
                    for (var relatedDeviceIndex in element.node.relatedDevices) {
                        relatedNodeId = element.node.relatedDevices[relatedDeviceIndex];
                        edges.push({from: element.node.nodeId, to: relatedNodeId, length: LENGTH_SUB, color: '#666666'});
                    }
                } else {
                    // Device
                    // Device to Device
                    for (var relatedDeviceIndex in element.node.relatedDevices) {
                        relatedNodeId = element.node.relatedDevices[relatedDeviceIndex];
                        if (element.node.nodeId > relatedNodeId) {
                            edges.push({from: element.node.nodeId, to: relatedNodeId, length: LENGTH_SUB, color: '#666666'});
                        }
                    }
                    // Device to Site
                }
            }

            function buildOptions() {
                var options = null;

                options = {
                    stabilize: true, // stabilize positions before displaying
                    navigation: true,
                    keyboard: true,
                    configurePhysics: false,
                    dataManipulation: {
                        enabled: false,
                        initiallyVisible: false
                    },
//                        onAdd: function (data, callback) {
//                            var newData = {
//                                label: 'sarasa',
//                                color: {
//                                    background: 'pink',
//                                    border: 'red'
//                                },
//                                shape: 'dot',
//                                radius: 30
//                            }; // alter the data as you want.
//                            callback(newData);  // call the callback to add a node.
//                        },
//                        onEdit: function (data, callback) {
//                            var newData = {}; // alter the data as you want.
//                            callback(newData);  // call the callback with the new data to edit the node.
//                        },
                };

                return options;
            }

            function isValid(layer, dateTime) {
                if ((dateTime === null) || (dateTime === undefined)) {
                    return false;
                }

                if ((layer === null) || (layer === undefined)) {
                    return false;
                }

                return true;
            }

            function showDialogInformation() {

            }

            /**
             *
             * @returns El val seleccionado en el combo de levels
             */
            function getSelectedLevel() {
                return $("#comboLevels").val();
            }

            /**
             * Guarda el grafico en un CSV
             * @returns {undefined}
             */
            function save() {
                console.log("NODES");
                console.log(network.nodes);
                console.log("EDGES");
                console.log(network.edges);

                var nodeId;
                var deviceId;
                var relatedNodeId;
                var relatedSites;
                var relatedDevices;
                var nodeLine;
                for (var nodeIndex in network.nodes) {
                    nodeLine = "NODE (";
                    nodeLine += "Id:";
                    nodeId = network.nodes[nodeIndex].id;
                    nodeLine += nodeId;
                    nodeLine += ", RelatedNodes: [";
                    for (var edgeIndex in network.nodes[nodeIndex].edges) {
                        relatedNodeId = network.nodes[nodeIndex].edges[edgeIndex].toId;
                        nodeLine += relatedNodeId;
                        nodeLine += " ";
                    }
                    nodeLine += "]";
                    console.log(nodeLine);
                }


            }

/////////////// INICIO FUNCION DRAW //////////////////////
//var nodes = null;
//var edges = null;
//var network = null;
//
//var LENGTH_MAIN = 85;
//var LENGTH_SUB = 100;
//
//function draw() {
//    // Create a data table with nodes.
//    nodes = [];
//    // Create a data table with links.
//    edges = [];
//
//    // create a network
//    var container = document.getElementById('mynetwork');
//    var data = {
//        nodes: nodes,
//        edges: edges
//    };
//
//    var newData;
//    var colors = ['red', 'yellow', 'green'];
//
//    for (var i = 1; i <= 50; i++) {
//        newData = {
//            id: i,
//            label: 'SCE ' + i,
//            color: {// DEPENDE DE LOS VALORES DE LAS CATEGORIAS
//                background: colors[i % 3],
//                border: '#666666'
//            },
//            fontSize: 30,
//            shape: 'dot',
//            radius: i * 2 // DEPENDE DEL VALOR RESULTANTE EN ESCALA (valor * 100)
//        }; // alter the data as you want.
//
//        nodes.push(newData);
//        edges.push({from: i, to: i + 1, length: LENGTH_MAIN, color: '#666666'});
//    }
//
//    var options = {
//        stabilize: true, // stabilize positions before displaying
//        navigation: true,
//        keyboard: true,
//        configurePhysics: false,
//        dataManipulation: {
//            enabled: false,
//            initiallyVisible: false
//        },
//        onAdd: function (data, callback) {
//            var newData = {
//                label: 'sarasa',
//                color: {
//                    background: 'pink',
//                    border: 'red'
//                },
//                shape: 'dot',
//                radius: 30
//            }; // alter the data as you want.
//            callback(newData);  // call the callback to add a node.
//        },
//        onEdit: function (data, callback) {
//            var newData = {}; // alter the data as you want.
//            callback(newData);  // call the callback with the new data to edit the node.
//        },
//    };
//
//    network = new vis.Network(container, data, options);
//    loadMap();
//}

            /////////////// fin //////////////////




            ////////////////////////////////// FIN FUNCIONES JAVASCRIPT ///////////////////////////////////////////////
        </script>
    </head>
    <body>

        <div id="layoutNetwork" class="layout-callis">
            <div class="outer-center"><!-- INICIO DIV PRINCIPAL-->
                <div id="panelCentral" class="middle-center">
                    <div id="mynetwork"></div>
                </div>
                <div class="middle-west" > <!--INICIO DIV COMPONENTES -->
                    <div class="optionsSection">
                        <label for="comboLevels" class="sectionTitle">Select level:</label>
                        <div class="sectionContent">
                            <select id="comboLevels">
                            </select>
                        </div>
                    </div>
                    <div class="optionsSection">
                        <label for="layer" class="sectionTitle">Select layer:</label>
                        <div class="sectionContent">
                            <form id="formLayer">
                            </form>
                        </div>
                    </div>
                    <div class="optionsSection">
                        <label for="layer" class="sectionTitle">Select time:</label>
                        <div class="sectionContent">
                            <div>
                                <div class='input-group date datetimepickerFormat' id='datetime'>
                                    <input type='text' class="form-control" />
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <a role="button" class="executeButton" onclick="execute()"><img width="48" height="48" alt="Get Data" src="<c:url value='/img/bubbles/executeDataMap.png'/>"></a>
                    <!--SOLO SE HABILITA CUANDO SE PUEDE EDITAR-->
                    <!--<input type="button" class="executeButton" value="SAVE" onclick="save()"/>-->
                </div> <!--FIN DIV COMPONENTES -->
            </div> <!-- FIN DIV PRINCIPAL-->
        </div>
        <jsp:include page="/WEB-INF/jspf/footer.jsp" />
    </body>
</html>
