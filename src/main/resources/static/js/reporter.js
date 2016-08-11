            if (offsetInMillis === null) {
                offsetInMillis = 0;
            }

            // Formato default de Date
            var DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";
            // Valores default de hoja
            var PAPER_WIDTH = 297;
            var PAPER_HEIGHT = 210;
            // Export Data Separator
            var SEPARATOR = ";";
            // URL
            var urlAPIPrefix;
            // Variables globales
            var templatesLoaded = {};
            var isHistogram = false;
            var cargarListaMultipleEvent = false; // Este flag se agrego para que cuando carga los reportes guardados, cargue la lista multiple dependiente del evento del combo
            var cargarComboEvent = false; // Idem anterior pero para combos
            var templateInstancesProcessing;


            $(document).ready(function () {
                // Setea URL
                urlAPIPrefix = "/ajax";
                // SPLIT-PANE
                $('#layoutNetwork').layout({
                    center__paneSelector: ".outer-center"
                    , west__paneSelector: ".outer-west"
                    , east__paneSelector: ".outer-east"
                    , west__size: 125
                    , east__size: 125
                    , spacing_open: 8  // ALL panes
                    , spacing_closed: 12 // ALL panes
                            // , north__spacing_open: 0
                            // , south__spacing_open: 0
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
                // Tree de templates
                buildTree();
                // Botones de Report
                setEnabledTemplateActions(false);
                setEnabledButtonStopQuery(false);
                loadReports();
                setEnterKeyForDialogs();
            });

            function setEnterKeyForDialogs() {
                // Esto se seta asi xq de otra forma cuando das enter en los dialogs de add o duplicate
                // rompen!
                // Dialog add report instance
                $('#addReportInstance').keypress(function (e) {
                    if (e.keyCode === 13) {
                        return false;
                    }
                });
                // Dialog duplicate report instance
                $('#duplicateReportInstance').keypress(function (e) {
                    if (e.keyCode === $.ui.keyCode.ENTER) {
                        return false;
                    }
                });
            }

            function getReportTemplates() {
                // LLAMADA AJAX PARA OBTENER LOS TEMPLATES
                var resultado = [];
                $.ajax({type: "get",
                    dataType: "json",
                    async: false,
                    url: urlAPIPrefix + "/getReportTemplates",
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

            function getReportTemplatesByDomain(domain) {
                // LLAMADA AJAX PARA OBTENER LOS TEMPLATES
                var resultado = [];
                var vecParams = {};
                vecParams['domain'] = domain;
                $.ajax({type: "post",
                    dataType: "json",
                    data: vecParams,
                    async: false,
                    url: urlAPIPrefix + "/getReportTemplatesByDomain",
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

            function getReportTemplatesDomains() {
                // LLAMADA AJAX PARA OBTENER LOS TEMPLATES
                var resultado = [];
                $.ajax({type: "get",
                    dataType: "json",
                    async: false,
                    url: urlAPIPrefix + "/getReportTemplatesDomains",
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

            function getObjectManager(domain) {
                // LLAMADA AJAX PARA OBTENER LOS TEMPLATES
                var resultado = [];
                var vecParams = {};
                vecParams['domain'] = domain;
                $.ajax({type: "post",
                    dataType: "json",
                    data: vecParams,
                    async: false,
                    url: urlAPIPrefix + "/getObjectManager",
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

            var objectManager;
            var reportTemplates;
            var easytree;
            function buildTree() {
                $("#demoTree ul").empty();
                reportTemplates = getReportTemplatesByDomain(reportTemplatesDomain);
                // Verifica que el valor tenga templates para el domain
                if (reportTemplates.length > 0) {
                    objectManager = getObjectManager(reportTemplatesDomain);
                    var strTree;
                    strTree += "<li data-uiicon='ui-icon-template-domain' class='isFolder isExpanded node-template-domain'>" + formatDomainName(reportTemplatesDomain);
                    for (var groupIndex in reportTemplates) {
                        if (reportTemplates[groupIndex].templateDomain === reportTemplatesDomain) {
                            strTree += "<ul>";
                            strTree += "<li class = 'isFolder isExpanded node-template-group' data-uiicon = 'ui-icon-template-group'>";
                            strTree += reportTemplates[groupIndex].name;
                            strTree += "<ul>";
                            for (var templateInfoIndex in reportTemplates[groupIndex].templateInfos) {
                                strTree += "<li data-uiicon='ui-icon-template-group' class='isFolder isExpanded node-template-group";
                                if (reportTemplates[groupIndex].templateInfos[templateInfoIndex].warningDescription !== null & reportTemplates[groupIndex].templateInfos[templateInfoIndex].warningDescription !== undefined) {
                                    strTree += " templateWarning'";
                                    strTree += " title='" + reportTemplates[groupIndex].templateInfos[templateInfoIndex].warningDescription + "'";
                                } else {
                                    strTree += "'";
                                }
                                strTree += ">";
                                strTree += reportTemplates[groupIndex].templateInfos[templateInfoIndex].name;

                                strTree += "</li>";
                            }
                            strTree += "</ul>";
                            strTree += "</li>";
                            strTree += "</ul>";
                        }
                    }
                    strTree += "</li>";
                    $("#demoTree ul").append(strTree);
                    // EASYTREE
                    easytree = $('#demoTree').easytree({stateChanged: easyTreeStateChange});
                }

            }

            /**
			 * Formatea el nombre recibido en el que se quiere mostrar
			 * 
			 * @param {type}
			 *            domainName
			 * @returns {undefined}
			 */
            function formatDomainName(domainName) {
                domainName = domainName.replace("_", " ");
                // Especial para In-Browser
                domainName = domainName.toUpperCase().replace("INBROWSER", "IN-BROWSER");
                return domainName;
            }

            function isTableTemplate(templateType) {
                return (templateType === "TABLE");
            }

            function easyTreeStateChange(nodes, nodesJson) {
                $('#addReportTemplateInstance').prop('disabled', true);
                $('#editReportTemplateInstance').prop('disabled', true);
                $('#deleteReportTemplateInstance').prop('disabled', true);
                $('#duplicateReportTemplateInstance').prop('disabled', true);
                $("#tableProperties tbody").empty();
                for (var nodeDomainIndex in nodes) {
                    for (var nodeGroupIndex in nodes[nodeDomainIndex].children) {
                        for (var nodeTemplateIndex in nodes[nodeDomainIndex].children[nodeGroupIndex].children) {
                            clearChart();
                            if (nodes[nodeDomainIndex].children[nodeGroupIndex].children[nodeTemplateIndex].isFolder && nodes[nodeDomainIndex].children[nodeGroupIndex].children[nodeTemplateIndex].isActive) {
                                // Habilita add report template instance
                                $('#addReportTemplateInstance').prop('disabled', false);
                            } else {
                                // Recorre hijos, habilita delete & duplicate
                                for (var nodeTemplateInstance in nodes[nodeDomainIndex].children[nodeGroupIndex].children[nodeTemplateIndex].children) {
                                    if (!nodes[nodeDomainIndex].children[nodeGroupIndex].children[nodeTemplateIndex].children[nodeTemplateInstance].isFolder && nodes[nodeDomainIndex].children[nodeGroupIndex].children[nodeTemplateIndex].children[nodeTemplateInstance].isActive) {
                                        // Habilita delete, duplicate report template instance
                                        $('#editReportTemplateInstance').prop('disabled', false);
                                        $('#deleteReportTemplateInstance').prop('disabled', false);
                                        $('#duplicateReportTemplateInstance').prop('disabled', false);
                                        // Deshabilita add templeta report instance
                                        $('#addReportTemplateInstance').prop('disabled', true);
                                        // Carga la template correspondiente
                                        var templateId = nodes[nodeDomainIndex].children[nodeGroupIndex].children[nodeTemplateIndex].children[nodeTemplateInstance].templateId;
                                        var templateInstanceId = nodes[nodeDomainIndex].children[nodeGroupIndex].children[nodeTemplateIndex].children[nodeTemplateInstance].templateInstanceId;
                                        var templateType = getTemplateTypeByTemplateId(templateId);
                                        if (isTableTemplate(templateType)) {
                                            loadTableTemplate(templateId, templateInstanceId);
                                        } else {
                                            loadTemplate(templateId, templateInstanceId);
                                            checkResultQueryToEnabledButtons(templatesLoaded[templateId].instances[templateInstanceId].result);
                                        }
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }



            }

            // DO NOT put these inside $(document).ready or they are not 'global'
            var outerLayout, orderLayout, engineLayout; // Order: Highcharts - Engine: TableReport
            var innerLayoutOptions = {
                center__paneSelector: ".inner-center"
                , west__paneSelector: ".inner-west"
                , east__paneSelector: ".inner-east"
                , west__size: .33
                , east__size: .33
                , spacing_open: 8  // ALL panes
                , spacing_closed: 12  // ALL panes
                , west__spacing_closed: 12
                , east__spacing_closed: 12
                , resizeWhileDragging: true
            };
            // EXAMPLES - customize default options
            var engineLayoutOptions = innerLayoutOptions; // no extra settings
            var orderLayoutOptions = $.extend({}, innerLayoutOptions, {// customize...
                east__size: .40
            });
            function toggleInnerLayout() {
                if (orderLayout && $("#order").is(":visible"))
                    showInnerLayout("engine");
                else
                    showInnerLayout("order");
                return false; // cancel hyperlink
            }

            function showInnerLayout(name) {
                var altName = name === "order" ? "engine" : "order";
                $("#" + altName).hide(); // hide OTHER layout container
                $("#" + name).show(); // show THIS layout container
                // if layout is already initialized, then just resize it
                if (window[ name + "Layout" ])
                    window[ name + "Layout" ].resizeAll();
                // otherwise init the layout the FIRST TIME
                else
                    window[ name + "Layout" ] = $("#" + name).layout(window[ name + "LayoutOptions" ]); // use per-layout options
                // window[ name +"Layout" ] = $("#"+ name).layout( innerLayoutOptions ); // use common options
            }

            function resizeInnerLayout() {
                if (orderLayout && $("#order").is(":visible"))
                    orderLayout.resizeAll();
                else if (engineLayout && $("#engine").is(":visible"))
                    engineLayout.resizeAll();
            }

            var oTableReportTemplate;
            function loadTableTemplate(templateId, templateInstanceId) {
                createTable(templateId, templateInstanceId);
                loadTableTemplateFilters(templateId, templateInstanceId);
                showInnerLayout("order");
                toggleInnerLayout();
            }

            function loadTableTemplateFilters(templateId, templateInstanceId) {
                var columnFilters;
                if (templatesLoaded[templateId] !== undefined) {
                    columnFilters = templatesLoaded[templateId].instances[templateInstanceId].property;
                    for (var columnIndex in columnFilters) {
                        // Setea valor en el footer
                        $('input', oTableReportTemplate.columns(columnIndex).footer()).val(columnFilters[columnIndex]);
                        // Aplica el filtro a la busqueda
                        oTableReportTemplate.columns(columnIndex).search(columnFilters[columnIndex]);
                    }
                    // Refresca la tabla
                    oTableReportTemplate.draw();
                }

            }

            function loadTemplate(templateId, templateInstanceId) {
                showInnerLayout("engine");
                toggleInnerLayout();
                // Inicializa template
                initTemplatesLoaded(templateId, templateInstanceId);
                // Carga properties en tabla
                cargarListaMultipleEvent = true;
                cargarComboEvent = true;
                loadTemplateProperties(templateId, templateInstanceId);
                cargarListaMultipleEvent = false;
                cargarComboEvent = false;
                // Carga chart si hay
                if (templatesLoaded[templateId].instances[templateInstanceId].result !== undefined) {
                    loadChart(templateId, templatesLoaded[templateId].instances[templateInstanceId].result);
                } else {
                    clearChart();
                }
                // Verifica si tiene todos los mandatories
                var fullMandatories = checkMandatoriesTemplateParams(templateId);
                // Verifica si se esta ejecutando
                var isProcessing = isTemplateInstanceProcessing(templateInstanceId);
                showIconLoading(isProcessing);
                setEnabledButtonStopQuery(isProcessing);
                setEnabledTemplateActions(!isProcessing && fullMandatories);
            }

            function loadTemplateProperties(templateId, templateInstanceId) {
                var params = getTemplateParams(templateId);
                $("#tableProperties tbody").empty();
                var row;
                var param;
                for (var paramIndex in params) {
                    param = params[paramIndex];
                    row = "";
                    if (param.type === "DATE_TIME") {
                        row += "<tr>";
                        row += "<td>" + param.paramName;
                        if (param.mandatory) {
                            row += "*";
                        }
                        row += "</td>";
                        row += "<td><a href='#' id='param" + param.paramId + "' data-type='datetime' data-pk='1' title='Select date & time'>";
                        if (templatesLoaded[templateId].instances[templateInstanceId].property[param.paramId] !== undefined) {
                            paramValue = templatesLoaded[templateId].instances[templateInstanceId].property[param.paramId];
                        } else {
                            paramValue = param.value;
                        }
                        var datetime;
                        if (paramValue !== undefined) {
                            datetime = JSON.parse(paramValue);
                            paramValue = convertJSONStringToDate(datetime);
                            row += paramValue;
                        }
                        row += "</a></td>";
                        row += "</tr>";
                    } else if (param.type === "COMBO") {
                        row += "<tr>";
                        row += "<td>" + param.paramName;
                        if (param.mandatory) {
                            row += "*";
                        }
                        row += "</td>";
                        row += "<td><a href='#' id='param" + param.paramId + "' data-type='select' data-pk='1' data-title='Select option'>";
                        row += "</a></td>";
                        row += "</tr>";
                    } else if (param.type === "LISTA_MULTIPLE") {
                        row += "<tr>";
                        row += "<td>" + param.paramName;
                        if (param.mandatory) {
                            row += "*";
                        }
                        row += "</td>";
                        row += "<td><a href='#' id='param" + param.paramId + "' data-type='checklist' data-pk='1' data-title='Select options'>";
                        row += "</a></td>";
                        row += "</tr>";
                    } else if ((param.type === "INTEGER") || (param.type === "STRING")) {
                        row += "<tr>";
                        row += "<td>" + param.paramName;
                        if (param.mandatory) {
                            row += "*";
                        }
                        row += "</td>";
                        row += "<td><a href='#' id='param" + param.paramId + "' data-type='text' data-pk='1' data-title='Enter value'>";
                        row += "</a></td>";
                        row += "</tr>";
                    } else if (param.type === "HIDDEN") {
                        // console.log("Hidden param: " + param.paramName);
                    } else {
                        alert("No existe " + param.type);
                    }
                    $("#tableProperties tbody").append(row);
                }
                // X-EDITABLE
                var paramValue;
                var paramSourceValue = null;
                var paramSourceIndex;
                var paramSourceParamId;
                var paramDestIndex;
                var paramDestValueType;
                var paramDestParamId;
                for (var paramIndex in params) {
                    param = params[paramIndex];
                    if (templatesLoaded[templateId].instances[templateInstanceId].property[param.paramId] !== undefined) {
                        paramValue = templatesLoaded[templateId].instances[templateInstanceId].property[param.paramId];
                    } else {
                        paramValue = param.value;
                    }
                    if (param.type === "DATE_TIME") {
                        $('#param' + param.paramId).editable({
                            format: 'yyyy-mm-dd hh:ii',
                            viewformat: 'yyyy-mm-dd hh:ii',
                            datetimepicker: {
                                autoclose: true,
                                todayBtn: true,
                                weekStart: 1 // Arranca lunes
                            },
                            placement: 'bottom',
                            showbuttons: null
// success: function (response, newValue) {
// checkMandatories();
// }
                        });
                    } else if (param.type === "COMBO") {
                        var data = [];
                        if (param.values.length > 0) {
                            // Carga enumerado
                            for (var optionIndex in param.values) {
                                data[optionIndex] = {};
                                data[optionIndex].value = param.values[optionIndex];
                                data[optionIndex].text = param.values[optionIndex];
                            }
                        } else {
                            // Carga del Object Loader
                            var oo;
                            for (var omIndex in objectManager.mapa[param.valueType]) {
                                oo = objectManager.mapa[param.valueType][omIndex];
                                data[omIndex] = {};
                                data[omIndex].value = oo.name;
                                data[omIndex].text = oo.name;
                            }

                        }
                        if (param.dynamic === "SOURCE") {
                            paramSourceIndex = paramIndex;
                            paramSourceParamId = param.paramId;
                            paramSourceValue = paramValue;
                            $('#param' + param.paramId).editable({
                                value: paramValue,
                                source: data,
                                mode: 'inline',
                                showbuttons: null,
                                success: function (response, newValue) {
                                    var cmtsId = getDeviceIdByName(newValue, params[paramSourceIndex], objectManager);
                                    var dynamicValues = getDeviceDynamicDestValues(cmtsId, params[paramSourceIndex], paramDestValueType, objectManager);
                                    $('#param' + paramDestParamId).editable('option', 'source', dynamicValues);
                                    $('#param' + paramDestParamId).editable('setValue', null);
                                }
                            });
                        } else if (param.dynamic === "DEST") {
                            paramDestIndex = paramIndex;
                            paramDestParamId = param.paramId;
                            paramDestValueType = param.valueType;
                            $('#param' + param.paramId).editable({
                                value: paramValue,
                                mode: 'inline',
                                showbuttons: null,
                                sourceError: 'Please, select value in previous list'
                            });
                            if (cargarComboEvent && paramSourceValue !== null && paramDestValueType !== null) {
                                var cmtsId = getDeviceIdByName(paramSourceValue, params[paramSourceIndex], objectManager);
                                if (cmtsId !== null) {
                                    var dynamicValues = getDeviceDynamicDestValues(cmtsId, params[paramSourceIndex], paramDestValueType, objectManager);
                                    $('#param' + paramDestParamId).editable('option', 'source', dynamicValues);
                                    $('#param' + paramDestParamId).editable('setValue', paramValue);
                                }
                            }
                        } else {
                            $('#param' + param.paramId).editable({
                                value: paramValue,
                                source: data,
                                mode: 'inline',
                                showbuttons: null
// success: function (response, newValue) {
// checkMandatories();
// }
                            });
                        }
                    } else if (param.type === "LISTA_MULTIPLE") {
                        var data = [];
                        if (param.values.length > 0) {
                            // Carga enumerado
                            for (var optionIndex in param.values) {
                                data[optionIndex] = {};
                                data[optionIndex].value = param.values[optionIndex];
                                data[optionIndex].text = param.values[optionIndex];
                            }
                        } else {
                            // Carga del Object Loader
                            var oo;
                            for (var omIndex in objectManager.mapa[param.valueType]) {
                                oo = objectManager.mapa[param.valueType][omIndex];
                                data[omIndex] = {};
                                data[omIndex].value = oo.name;
                                data[omIndex].text = oo.name;
                            }
                        }
                        if (param.dynamic === "DEST") {
                            paramDestIndex = paramIndex;
                            paramDestParamId = param.paramId;
                            paramDestValueType = param.valueType;
                            $('#param' + param.paramId).editable({
                                value: paramValue,
                                mode: 'inline',
                                sourceError: 'Please, select value in previous list'
                            });
                            if (cargarListaMultipleEvent && paramSourceValue !== null && paramDestValueType !== null) {
                                var cmtsId = getDeviceIdByName(paramSourceValue, params[paramSourceIndex], objectManager);
                                if (cmtsId !== null) {
                                    var dynamicValues = getDeviceDynamicDestValues(cmtsId, params[paramSourceIndex], paramDestValueType, objectManager);
                                    $('#param' + paramDestParamId).editable('option', 'source', dynamicValues);
                                    $('#param' + paramDestParamId).editable('setValue', paramValue);
                                }
                            }
                        } else {
                            $('#param' + param.paramId).editable({
                                value: paramValue,
                                source: data,
                                mode: 'inline'
// success: function (response, newValue) {
// checkMandatories();
// }
                            });
                        }

                    } else if (param.type === "STRING") {
                        $('#param' + param.paramId).editable({
                            value: paramValue,
                            source: data,
                            mode: 'inline',
                            showbuttons: null,
                            success: function (response, newValue) {
// checkMandatories();
                            }
                        });
                    } else if (param.type === "INTEGER") {
                        $('#param' + param.paramId).editable({
                            value: paramValue,
                            source: data,
                            mode: 'inline',
                            showbuttons: null,
                            success: function (response, newValue) {
                                if (!$.isNumeric(newValue)) {
                                    return "";
                                }
// checkMandatories();
                            }
                        });
                    }
                    $('#param' + param.paramId).on('save', function (e, params) {
                        $(".editable-submit").click();
// console.log($(this).data('editable'));
                        var paramSaving = $(this).data('editable').options.name;
                        // Verifica si tiene todos los mandatories
                        var fullMandatories = checkMandatoriesTemplateParams(templateId, paramSaving);
                        // Verifica si se esta ejecutando
                        var isProcessing = isTemplateInstanceProcessing(templateInstanceId);
                        showIconLoading(isProcessing);
                        setEnabledButtonStopQuery(isProcessing);
                        setEnabledTemplateActions(!isProcessing && fullMandatories);
                        // Setea el valor para persistir en la template
// var newParamIndex = paramSaving.replace("param", "");
                        var paramValue = params.newValue;
                        templatesLoaded[templateId].instances[templateInstanceId].property[param.paramId] = paramValue;
                    });
                }
            }

            function getDeviceIdByName(cmtsName, param, objectManager) {
                var devices = objectManager.mapa[param.valueType];
                for (var arrayIndex in devices) {
                    if (devices[arrayIndex].name === cmtsName) {
                        return devices[arrayIndex].index;
                    }
                }
                return null;
            }

            function getDeviceDynamicDestValues(cmtsId, param, paramDestValueType, objectManager) {
                var ifs = [];
                if (objectManager.mapaDynamic[param.valueType] !== undefined) {
                    var dynamicValues = objectManager.mapaDynamic[param.valueType][paramDestValueType][cmtsId];
                    for (var dynamicValueIndex in dynamicValues) {
                        ifs[dynamicValueIndex] = {};
                        ifs[dynamicValueIndex].value = dynamicValues[dynamicValueIndex].name;
                        ifs[dynamicValueIndex].text = dynamicValues[dynamicValueIndex].name;
                    }
                }
                return ifs;
            }

            function getTemplateParams(templateId) {
                for (var templateIndex in reportTemplates) {
                    for (var templateInfo in reportTemplates[templateIndex].templateInfos) {
                        if (reportTemplates[templateIndex].templateInfos[templateInfo].id === templateId) {
                            return reportTemplates[templateIndex].templateInfos[templateInfo].params;
                        }
                    }
                }
                return null;
            }

            function getTableTemplateColumnNames(templateId) {
                for (var templateIndex in reportTemplates) {
                    for (var templateInfo in reportTemplates[templateIndex].templateInfos) {
                        if (reportTemplates[templateIndex].templateInfos[templateInfo].id === templateId) {
                            for (var paramIndex in reportTemplates[templateIndex].templateInfos[templateInfo].params) {
                                if (reportTemplates[templateIndex].templateInfos[templateInfo].params[paramIndex].paramName === "Headers") {
                                    return reportTemplates[templateIndex].templateInfos[templateInfo].params[paramIndex].values;
                                }
                            }
                        }
                    }
                }
                return null;
            }

            function getTemplateIdByTemplateName(templateName) {
                for (var templateIndex in reportTemplates) {
                    for (var templateInfo in reportTemplates[templateIndex].templateInfos) {
                        if (reportTemplates[templateIndex].templateInfos[templateInfo].name === templateName) {
                            return reportTemplates[templateIndex].templateInfos[templateInfo].id;
                        }
                    }
                }
                return null;
            }

            function getSelectedTemplateId() {
                var nodes = easytree.getAllNodes();
                for (var nodeDomainIndex in nodes) {
                    for (var nodeGroupIndex in nodes[nodeDomainIndex].children) {
                        for (var nodeTemplateIndex in nodes[nodeDomainIndex].children[nodeGroupIndex].children) {
                            if (nodes[nodeDomainIndex].children[nodeGroupIndex].children[nodeTemplateIndex].isActive) {
                                var templateId = getTemplateIdByTemplateName(nodes[nodeDomainIndex].children[nodeGroupIndex].children[nodeTemplateIndex].text);
                                return templateId;
                            }
                        }
                    }
                }
            }

            function getTemplateVersionIdByTemplateId(templateId) {
                var templateVersionId = null;
                for (var reportTemplateIndex in reportTemplates) {
                    for (var templateInfoIndex in reportTemplates[reportTemplateIndex].templateInfos) {
                        if (templateId === reportTemplates[reportTemplateIndex].templateInfos[templateInfoIndex].id) {
                            templateVersionId = reportTemplates[reportTemplateIndex].templateInfos[templateInfoIndex].versionId;
                            break;
                        }
                    }
                    if (templateVersionId !== null) {
                        break;
                    }
                }

                return templateVersionId;
            }

            function getTemplateTypeByTemplateId(templateId) {
                var templateType = null;
                for (var reportTemplateIndex in reportTemplates) {
                    for (var templateInfoIndex in reportTemplates[reportTemplateIndex].templateInfos) {
                        if (templateId === reportTemplates[reportTemplateIndex].templateInfos[templateInfoIndex].id) {
                            templateType = reportTemplates[reportTemplateIndex].templateInfos[templateInfoIndex].graphType;
                            break;
                        }
                    }
                    if (templateType !== null) {
                        break;
                    }
                }

                return templateType;
            }

            function getSelectedTemplateNode() {
                var nodes = easytree.getAllNodes();
                for (var nodeDomainIndex in nodes) {
                    for (var nodeGroupIndex in nodes[nodeDomainIndex].children) {
                        for (var nodeTemplateIndex in nodes[nodeDomainIndex].children[nodeGroupIndex].children) {
                            if (nodes[nodeDomainIndex].children[nodeGroupIndex].children[nodeTemplateIndex].isActive) {
                                return nodes[nodeDomainIndex].children[nodeGroupIndex].children[nodeTemplateIndex];
                            } else {
                                // Recorre los report template instance
                                for (var nodeInstanceIndex in nodes[nodeDomainIndex].children[nodeGroupIndex].children[nodeTemplateIndex].children) {
                                    if (nodes[nodeDomainIndex].children[nodeGroupIndex].children[nodeTemplateIndex].children[nodeInstanceIndex].isActive) {
                                        return nodes[nodeDomainIndex].children[nodeGroupIndex].children[nodeTemplateIndex].children[nodeInstanceIndex];
                                    }
                                }
                            }
                        }
                    }
                }
                return null;
            }

            function checkMandatoriesTemplateParams(templateId, paramSaving) {
                var params = getTemplateParams(templateId);
                var param;

                for (var paramIndex in params) {
                    param = params[paramIndex];
                    if (param.mandatory) {
                        var a = "param" + param.paramId;
                        if (paramSaving === null || paramSaving === undefined || paramSaving !== a) {
                            if (!$("#param" + param.paramId).editable('getValue').hasOwnProperty("param" + param.paramId)) {
                                return false;
                            } else {
                                if ($("#param" + param.paramId).editable('getValue')["param" + param.paramId].length === 0) {
                                    return false;
                                }
                            }
                        }

                    }
                }
                return true;
            }

            function setEnabledButtonExecuteQuery(enabled) {
                if (enabled) {
                    $('#buttonExecuteQuery').removeClass('disabled');
                } else {
                    $('#buttonExecuteQuery').addClass('disabled');
                }
            }

            function setEnabledButtonStopQuery(enabled) {
                if (enabled) {
                    $('#buttonStopQuery').removeClass('disabled');
                } else {
                    $('#buttonStopQuery').addClass('disabled');
                }
            }

            function setEnabledButtonExportData(enabled) {
                if (enabled) {
                    $('#buttonExportData').removeClass('disabled');
                } else {
                    $('#buttonExportData').addClass('disabled');
                }
            }

            function setEnabledButtonExportHistogramData(enabled) {
                if (enabled && isHistogram) {
                    $('#buttonExportHistogramData').removeClass('disabled');
                } else {
                    $('#buttonExportHistogramData').addClass('disabled');
                }
            }

            function setEnabledButtonExportSingleQueryURL(enabled) {
                if (enabled) {
                    $('#buttonExportSingleQueryURL').removeClass('disabled');
                } else {
                    $('#buttonExportSingleQueryURL').addClass('disabled');
                }
            }

            function setEnabledTemplateActions(enabled) {
                setEnabledButtonExecuteQuery(enabled);
                setEnabledButtonExportData(enabled);
                setEnabledButtonExportHistogramData(enabled);
                setEnabledButtonExportSingleQueryURL(enabled);
            }

            function showIconLoading(show) {
                if (show) {
                    $('#iconLoading').show();
                } else {
                    $('#iconLoading').hide();
                }
            }

            function loadReports() {
                // LLAMADA AJAX PARA OBTENER LOS REPORTS GUARDADOS DEL USUARIO
                var resultado = [];
                $.ajax({type: "get",
                    dataType: "json",
                    async: false,
                    url: urlAPIPrefix + "/getReportsForUser",
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
                if (resultado !== undefined && resultado !== null) {
                    var templateId = null;
                    var templateInstanceId = null;
                    var templateInstanceName = null;
                    var templateType = null;
                    var csmReportResult = null;
                    var tableProperty = null;
                    var parsedResult = null;
                    var templateParentNode = null;
                    for (var reportIndex in resultado.reports) {
                        // Obtiene el id de template
                        templateId = resultado.reports[reportIndex].templateId;
                        for (var reportInstancesIndex in resultado.reports[reportIndex].reportInstances) {
                            // Obtiene el id de la instancia de template
                            templateInstanceId = resultado.reports[reportIndex].reportInstances[reportInstancesIndex].templateInstanceId;
                            // Obtiene el nombre de la instancia de template
                            templateInstanceName = resultado.reports[reportIndex].reportInstances[reportInstancesIndex].templateInstanceName;
                            // Obtiene el tipo de template
                            templateType = resultado.reports[reportIndex].reportInstances[reportInstancesIndex].templateType;
                            if (resultado.reports[reportIndex].reportInstances[reportInstancesIndex].csmReportResult !== undefined && resultado.reports[reportIndex].reportInstances[reportInstancesIndex].csmReportResult !== null) {
                                // Obtiene el resultado de la instancia de template
                                csmReportResult = resultado.reports[reportIndex].reportInstances[reportInstancesIndex].csmReportResult;
                            }
                            if (resultado.reports[reportIndex].reportInstances[reportInstancesIndex].tableProperty !== undefined && resultado.reports[reportIndex].reportInstances[reportInstancesIndex].tableProperty !== null) {
                                // Obtiene las property de la instancia de template
                                tableProperty = resultado.reports[reportIndex].reportInstances[reportInstancesIndex].tableProperty;
                            }
                            // Carga la instancia en templateLoaded
                            initTemplatesLoaded(templateId, templateInstanceId);
                            // Carga property de la instancia de template
                            if (tableProperty !== undefined && tableProperty !== null) {
                                templatesLoaded[templateId].instances[templateInstanceId].property = JSON.parse(tableProperty);
                            }
                            // Carga reult de la instancia de template
                            if (csmReportResult !== undefined && csmReportResult !== null) {
                                parsedResult = parseResult(csmReportResult);
                                // console.log(JSON.stringify(parsedResult));
                                templatesLoaded[templateId].instances[templateInstanceId].result = parsedResult;
                            }
                            // Obtiene el template node parent
                            templateParentNode = getTemplateNodeByTemplateName(getTemplateNameByTemplateId(templateId));
                            // Carga la instancia de template como un nuevo nodo en el tree
                            addReportTemplateInstanceNodeToParent(templateId, templateInstanceId, templateInstanceName, templateType, templateParentNode, false);
                        }
                    }
                }
            }

            function initTemplatesLoaded(templateId, templateInstanceId) {
                if (templatesLoaded[templateId] === undefined) {
                    // Inicializa template
                    templatesLoaded[templateId] = {};
                    // Inicializa instancias de template
                    templatesLoaded[templateId].instances = {};
                    // Inicializa una instancia
                    templatesLoaded[templateId].instances[templateInstanceId] = {};
                    // Inicializa property de la instancia de template
                    templatesLoaded[templateId].instances[templateInstanceId].property = {};
                    // Inicializa result de la instancia de template
                    templatesLoaded[templateId].instances[templateInstanceId].result = "";
                } else {
                    // Inicializa instancias de template
                    if (templatesLoaded[templateId].instances === undefined || templatesLoaded[templateId].instances === null) {
                        templatesLoaded[templateId].instances = {};
                    }
                    // Inicializa una instancia
                    if (templatesLoaded[templateId].instances[templateInstanceId] === undefined || templatesLoaded[templateId].instances[templateInstanceId] === null) {
                        templatesLoaded[templateId].instances[templateInstanceId] = {};
                    }
                    // Inicializa property de la instancia de template
                    if (templatesLoaded[templateId].instances[templateInstanceId].property === undefined || templatesLoaded[templateId].instances[templateInstanceId].property === null) {
                        templatesLoaded[templateId].instances[templateInstanceId].property = {};
                    }
                    // Inicializa result de la instancia de template
                    if (templatesLoaded[templateId].instances[templateInstanceId].result === undefined || templatesLoaded[templateId].instances[templateInstanceId].result === null) {
                        templatesLoaded[templateId].instances[templateInstanceId].result = "";
                    }
                }

            }

            function getTemplateNameByTemplateId(templateId) {
                var templateName = null;
                for (var templateIndex in reportTemplates) {
                    for (var templateInfo in reportTemplates[templateIndex].templateInfos) {
                        if (reportTemplates[templateIndex].templateInfos[templateInfo].id === templateId) {
                            templateName = reportTemplates[templateIndex].templateInfos[templateInfo].name;
                        }
                    }
                }


                return templateName;
            }

            function getTemplateNodeByTemplateName(templateName) {

                var nodes = easytree.getAllNodes();
                for (var nodeDomainIndex in nodes) {
                    for (var nodeGroupIndex in nodes[nodeDomainIndex].children) {
                        for (var nodeTemplateIndex in nodes[nodeDomainIndex].children[nodeGroupIndex].children) {
                            if (nodes[nodeDomainIndex].children[nodeGroupIndex].children[nodeTemplateIndex].text === templateName) {
                                return nodes[nodeDomainIndex].children[nodeGroupIndex].children[nodeTemplateIndex];
                            } else {
                                // Recorre los report template instance
                                for (var nodeInstanceIndex in nodes[nodeDomainIndex].children[nodeGroupIndex].children[nodeTemplateIndex].children) {
                                    if (nodes[nodeDomainIndex].children[nodeGroupIndex].children[nodeTemplateIndex].children[nodeInstanceIndex].text === templateName) {
                                        return nodes[nodeDomainIndex].children[nodeGroupIndex].children[nodeTemplateIndex].children[nodeInstanceIndex];
                                    }
                                }
                            }
                        }
                    }
                }

                return null;
            }

            function isTemplateInstanceProcessing(templateInstanceId) {
                if (templateInstancesProcessing !== null && templateInstancesProcessing !== undefined) {
                    var index = templateInstancesProcessing.indexOf(templateInstanceId);
                    if (index > -1) {
                        return true;
                    }
                }
                return false;
            }

            function addTemplateInstanceToProcess(templateInstanceId) {
                if (templateInstancesProcessing === null || templateInstancesProcessing === undefined) {
                    templateInstancesProcessing = [];
                }
                templateInstancesProcessing[templateInstancesProcessing.length] = templateInstanceId;
            }

            function removeTemplateInstanceFromProcess(templateInstanceId) {
                if (templateInstancesProcessing !== null && templateInstancesProcessing !== undefined) {
                    var index = templateInstancesProcessing.indexOf(templateInstanceId);
                    if (index > -1) {
                        templateInstancesProcessing.splice(index, 1);
                    }
                }
            }

            function executeReportTemplate() {
                var selectedNode = getSelectedTemplateNode();
                var templateInstanceId = selectedNode.templateInstanceId;
                // Indica el comienzo del procesamiento
                addTemplateInstanceToProcess(templateInstanceId);
                showIconLoading(true);
                setEnabledButtonStopQuery(true);
                setEnabledTemplateActions(false);
                // Obtiene el id de template seleccionado
                var templateId = selectedNode.templateId;
                // Obtiene params del template correspondiente
                var templateParams = getTemplateParams(templateId);
                // Carga vector con params para la llamada AJAX
                var vecParams = {};
                vecParams['templateId'] = templateId;
                var paramIndex, param, paramValue;
                for (paramIndex in templateParams) {
                    param = templateParams[paramIndex];
                    if ((param.paramName === "Start Time") || (param.paramName === "End Time")) {
                        if ($('#param' + param.paramId).editable('getValue')['param' + param.paramId] !== undefined) {
                            paramValue = convertLocalDateToTimezoneJSONString($('#param' + param.paramId).editable('getValue')['param' + param.paramId]);
                            vecParams[param.paramName] = paramValue;
                        }
                    } else {
                        paramValue = $('#param' + param.paramId).editable('getValue')['param' + param.paramId];
                        vecParams[param.paramName] = JSON.stringify(paramValue);
                    }
                    templatesLoaded[templateId].instances[templateInstanceId].property[param.paramId] = paramValue;
                }


                vecParams['templateLoaded'] = JSON.stringify(templatesLoaded[templateId].instances[templateInstanceId].property);
                vecParams['templateInstanceId'] = templateInstanceId;
                // Llamada AJAX de ejecucion del query
                $.ajax({type: "post",
                    dataType: "json",
                    data: vecParams,
                    url: urlAPIPrefix + "/executeReportTemplate",
                    success: function (data) {
                        if (data !== null) {
                            var parsedResult = parseResult(data);
                            // console.log(JSON.stringify(parsedResult));
                            if (getSelectedTemplateNode().templateInstanceId === templateInstanceId) {
                                loadChart(templateId, parsedResult);
                                // Indica el fin del procesamiento
                                removeTemplateInstanceFromProcess(templateInstanceId);
                                showIconLoading(false);
                                setEnabledButtonStopQuery(false);
                                setEnabledTemplateActions(true);
                                if ((parsedResult.series !== null) && (parsedResult.series !== undefined) && (parsedResult.series.length === 0)) {
                                    $('#alertReportInstance').modal('show');
                                } else if (isHistogram) {
                                    var showDialog = true;
                                    for (var indexSerie in parsedResult.series) {
                                        if ((parsedResult.series[indexSerie] !== null) && ((parsedResult.series[indexSerie] !== undefined))) {
                                            if ((parsedResult.series[indexSerie].data !== null) && ((parsedResult.series[indexSerie].data !== undefined))) {
                                                if (parsedResult.series[indexSerie].data.length > 0) {
                                                    showDialog = false;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    if (showDialog) {
                                        $('#alertReportInstance').modal('show');
                                    }
                                }
                            } else {
                                saveResults(templateId, parsedResult, templateInstanceId);
                            }
                        } else {
                            // Vino data null, debe destrabar el procesamiento
                            if (getSelectedTemplateNode().templateInstanceId === templateInstanceId) {
                                // Indica el fin del procesamiento
                                removeTemplateInstanceFromProcess(templateInstanceId);
                                showIconLoading(false);
                                setEnabledButtonStopQuery(false);
                                setEnabledTemplateActions(checkMandatoriesTemplateParams(templateId));
                            }
                        }
                    },
                    error: function (response) {
                        var str = response.responseText + "";
                        var pos = str.lastIndexOf("Login");
                        if (pos !== -1) {
                            window.location = window.location.href;
                        }
                        // Indica el fin del procesamiento
                        removeTemplateInstanceFromProcess(templateInstanceId);
                        showIconLoading(false);
                        setEnabledButtonStopQuery(false);
                        setEnabledTemplateActions(false);
                        setEnabledButtonExecuteQuery(checkMandatoriesTemplateParams(templateId));
                    }
                });
            }

            function convertLocalDateToTimezoneJSONString(strLocalDate) {
                // Date donde esta la maquina
                var localDate = new Date(strLocalDate);
                // Misma fecha y hora pero para el timezone configurado en UTC
                var utcDate = new Date(localDate.getTime() - offsetInMillis);
                // Convierte a JSON
                var strUTCDate = utcDate.toString("yyyy-MM-dd HH:mm"); // NO es DATE_FORMAT
                var jsonUTCDate = {"date": strUTCDate.split(" ")[0], "time": strUTCDate.split(" ")[1]};
                return JSON.stringify(jsonUTCDate);
            }

            function convertJSONStringToDate(jsonUTCDate) {
                // JSON recibido de DB
                var strUTCDate = jsonUTCDate.date + ' ' + jsonUTCDate.time;
                // Convierte a hora local a mostrar
                var utcDate = new Date(strUTCDate);
                var showDate = new Date(utcDate.getTime() + offsetInMillis);
                return showDate.toString("yyyy-MM-dd HH:mm"); // NO es DATE_FORMAT
            }

            function clearChart() {
                $(function () {
                    $('#chartPanel').empty();
                });
            }

            function loadChart(templateId, resultQuery) {
                var selectedNode = getSelectedTemplateNode();
                // Guarda el resultado
                initTemplatesLoaded(templateId, selectedNode.templateInstanceId);
                templatesLoaded[templateId].instances[selectedNode.templateInstanceId].result = resultQuery;
                // Muestra el grafico/tabla
                if (isTableGraphType(templatesLoaded[templateId].instances[selectedNode.templateInstanceId].property)) {
                    clearChart();
                    loadTableChart(templatesLoaded[templateId].instances[selectedNode.templateInstanceId].result);
                } else {
                    var offsetInMinutes = -(offsetInMillis / 1000 / 60);
                    $(function () {
                        Highcharts.setOptions({
                            global: {
                                timezoneOffset: offsetInMinutes
                            }
                        });
                        $('#chartPanel').highcharts(resultQuery);
                    });
                }
            }

            function saveResults(templateId, resultQuery, templateInstanceId) {
                initTemplatesLoaded(templateId, templateInstanceId);
                templatesLoaded[templateId].instances[templateInstanceId].result = resultQuery;
            }

            function isTableGraphType(properties) {
                for (var propertyIndex in properties) {
                    if (properties[propertyIndex] === "TABLE") {
                        return true;
                    }
                }
                return false;
            }

            function loadTableChart(result) {
                var serie;
                var tableData = [];

                // Limpia encabezados
                $('#tableResults thead tr').empty();
                // Carga segun el tipo de grafico
                if (result.zAxis.title.text !== undefined) { // Graficos XYZ
                    // Carga encabezados
                    $('#tableResults thead tr').append("<th>" + result.xAxis.title.text + "</th>");
                    $('#tableResults thead tr').append("<th>" + result.zAxis.title.text + "</th>");
                    $('#tableResults thead tr').append("<th>" + result.yAxis.title.text + "</th>");
                    // Crea matriz de resultados para cargar en datatable
                    for (var serieIndex in result.series) {
                        for (var serieDataIndex in result.series[serieIndex].data) {
                            serie = [result.series[serieIndex].data[serieDataIndex][0], result.series[serieIndex].name, result.series[serieIndex].data[serieDataIndex][1]];
                            tableData.push(serie);
                        }
                    }
                } else { // Graficos XY
                    // Carga encabezados
                    $('#tableResults thead tr').append("<th>" + result.xAxis.title.text + "</th>");
                    $('#tableResults thead tr').append("<th>" + result.yAxis.title.text + "</th>");
                    // Crea matriz de resultados para cargar en datatable
                    for (var serieIndex in result.series) {
                        for (var serieDataIndex in result.series[serieIndex].data) {
                            serie = [result.series[serieIndex].data[serieDataIndex][0], result.series[serieIndex].data[serieDataIndex][1]];
                            tableData.push(serie);
                        }
                    }
                }

                // Carga div con la tabla en el panel correspondiente
                $('#chartPanel').append($('#divResults').html());
                // Carga datatable con la info recibida
                $('#tableResults').DataTable({
                    "data": tableData,
                    "aoColumnDefs": [
                        {
                            "aTargets": [0], // Timestamp es siempre la primer columna
                            "mRender": function (value) {
                                if (isTimestamp(parseInt(value))) {
                                    // Aplica Offset en millis al timestamp recibido
                                    var localDate = new Date(parseInt(value));
                                    return new Date(localDate.getTime() + (localDate.getTimezoneOffset() * 60000) + offsetInMillis).toString(DATE_FORMAT);
                                } else {
                                    return value;
                                }
                            }
                        }
                    ]
                });
            }

            function checkResultQueryToEnabledButtons(resultQuery) {
                setEnabledButtonExportData(false);
                setEnabledButtonExportHistogramData(false);
                setEnabledButtonExportSingleQueryURL(false);
                if (resultQuery !== undefined && resultQuery !== null && resultQuery !== "" && resultQuery.series !== undefined && resultQuery.series.length > 0) {
                    setEnabledButtonExportData(true);
                    setEnabledButtonExportHistogramData(true);
                    setEnabledButtonExportSingleQueryURL(true);
                }
            }

            function killQuery() {
                // Obtiene la instancia a detener
                var templateInstanceId = getSelectedTemplateNode().templateInstanceId;

                // Carga vector con params para la llamada AJAX
                var vecParams = {};
                vecParams['templateInstanceId'] = templateInstanceId;
                // Llamada AJAX de ejecucion del query
                $.ajax({type: "post",
                    dataType: "json",
                    async: false,
                    data: vecParams,
                    url: urlAPIPrefix + "/killReportTemplate",
                    success: function (data) {
                        if (data !== null) {
                            console.log(data);
                            removeTemplateInstanceFromProcess(templateInstanceId);
                            showIconLoading(false);
                            setEnabledButtonStopQuery(false);
                            setEnabledTemplateActions(true);
                        }
                    }
                });
            }

            function exportSingleQueryURL() {
                // Obtiene los valores del server
                var scheme = "${pageContext.request.scheme}";
                var serverName = "${pageContext.request.serverName}";
                var serverPort = "${pageContext.request.serverPort}";
                var uri = "${requestScope['javax.servlet.forward.request_uri']}";
                uri = uri.replace("admin/reporter", "singleQuery");

                // Obtiene el id de template seleccionado
                var templateId = getSelectedTemplateNode().templateId;
                // Obtiene params del template correspondiente
                var templateParams = getTemplateParams(templateId);
                // Carga vector con params para la llamada AJAX
                var vecParams = {};
                var paramIndex, param, paramValue;
                for (paramIndex in templateParams) {
                    param = templateParams[paramIndex];
                    if ((param.paramName === "Start Time") || (param.paramName === "End Time")) {
                        if ($('#param' + param.paramId).editable('getValue')['param' + param.paramId] !== undefined) {
                            paramValue = convertLocalDateToTimezoneJSONString($('#param' + param.paramId).editable('getValue')['param' + param.paramId]);
                            vecParams[param.paramId] = paramValue;
                        }
                    } else {
                        paramValue = $('#param' + param.paramId).editable('getValue')['param' + param.paramId];
                        if (paramValue !== undefined && paramValue !== null && paramValue.length > 0) {
                            vecParams[param.paramId] = JSON.stringify(paramValue);
                        }
                    }
                }
                var urlParams = "templateId=" + templateId + "&params=" + JSON.stringify(vecParams);
                var url = scheme + "://" + serverName + ":" + serverPort + uri + "?" + urlParams;
                // Exporta el resultado armado en el archivo indicado
                var blob = new Blob([url], {type: "text"});
                saveAs(blob, "templateURL.txt");
            }

            function exportDataOptions() {
                // Muestra el dialog de opciones de Export
                $("#exportDataOptions").modal();
            }

            function exportTableDataOptions() {
                $("#exportColumns").empty();
                // Carga los checkbox para cada columna
                var templateId = getSelectedTemplateNode().templateId;
                var columns = getTableTemplateColumnNames(templateId);
                for (var columnIndex in columns) {
                    $("#exportColumns").append("<input type='checkbox' style='vertical-align: top' name='exportColumn' value='" + columnIndex + "' checked> " + columns[columnIndex] + "<br>");
                }
                // Muestra el dialog de opciones de Export
                $("#exportTableDataOptions").modal();
            }

            function exportData() {
                // Quita el dialog de opciones de Export
                $("#exportDataOptions").modal("hide");
                // Indica el comienzo del procesamiento
                showIconLoading(true);
                setEnabledButtonStopQuery(false);
                setEnabledTemplateActions(false);
                // Obtiene el template seleccionado
                var selectedNode = getSelectedTemplateNode();
                // Obtiene las opciones de export
                var includeTemplateInfo = $('input[name=includeTemplateInfo]', '#formExportDataOptions').is(':checked');
                var includeHeaders = $('input[name=includeHeaders]', '#formExportDataOptions').is(':checked');
                var timestampFormat = $('input[name=timestamp]:checked', '#formExportDataOptions').val();
                var dateRegex = $('input[name=dateRegex]', '#formExportDataOptions').val();
                var fileFormat = $('input[name=fileFormat]:checked', '#formExportDataOptions').val();
                var paperWidth = $('input[name=paperWidth]', '#formExportDataOptions').val();
                var paperHeight = $('input[name=paperHeight]', '#formExportDataOptions').val();
                // Obtiene el id de la template seleccionada
                var templateId = selectedNode.templateId;
                // Obtiene el id de la template instance seleccionada
                var templateInstanceId = selectedNode.templateInstanceId;
                // Obtiene el resultado de la template correspondiente
                var result = templatesLoaded[templateId].instances[templateInstanceId].result;
                // Exporta en el formato indicado
                switch (fileFormat) {
                    case 'csv':
                        exportDataToCSV(selectedNode, templateId, templateInstanceId, result, includeTemplateInfo, includeHeaders, timestampFormat, dateRegex);
                        break;
                    case 'pdf':
                        exportDataToPDF(selectedNode, templateId, result, includeTemplateInfo, includeHeaders, timestampFormat, dateRegex, paperWidth, paperHeight);
                        break;
                    default:
                        break;
                }
                // Indica el comienzo del procesamiento
                showIconLoading(false);
                setEnabledButtonStopQuery(false);
                setEnabledTemplateActions(true);
            }

            function exportDataToCSV(selectedNode, templateId, templateInstanceId, result, includeTemplateInfo, includeHeaders, timestampFormat, dateRegex) {
                // Comienza el armado del texto
                var strResult = "";
                var firstParam = true;
                var paramNames = "";
                var paramValues = "";
                var param;
                var paramValue;
                var datetime;

                if (includeTemplateInfo) {
                    strResult += "TEMPLATE INFO" + "\n";
                    strResult += "Domain: " + reportTemplatesDomain + "\n";
                    for (var groupIndex in reportTemplates) {
                        for (var templateInfoIndex in reportTemplates[groupIndex].templateInfos) {
                            if (reportTemplates[groupIndex].templateInfos[templateInfoIndex].id === templateId) {
                                strResult += "Group: " + reportTemplates[groupIndex].name + "\n";
                                strResult += "Template: " + reportTemplates[groupIndex].templateInfos[templateInfoIndex].name + "\n";
                                strResult += "Template Instance: " + selectedNode.text + "\n";
                                strResult += "\n";
                                strResult += "USER INPUTS" + "\n";
                                for (var paramIndex in reportTemplates[groupIndex].templateInfos[templateInfoIndex].params) {
                                    param = reportTemplates[groupIndex].templateInfos[templateInfoIndex].params[paramIndex];
                                    if (!firstParam) {
                                        paramNames += SEPARATOR;
                                        paramValues += SEPARATOR;
                                    }
                                    paramNames += reportTemplates[groupIndex].templateInfos[templateInfoIndex].params[paramIndex].paramName;
                                    if (templatesLoaded[templateId].instances[templateInstanceId].property[param.paramId] !== undefined && templatesLoaded[templateId].instances[templateInstanceId].property[param.paramId].length > 0) {
                                        if ((param.paramName === "Start Time") || (param.paramName === "End Time")) {
                                            datetime = JSON.parse(templatesLoaded[templateId].instances[templateInstanceId].property[param.paramId]);
                                            paramValue = convertJSONStringToDate(datetime);
                                        } else {
                                            paramValue = templatesLoaded[templateId].instances[templateInstanceId].property[param.paramId];
                                        }
                                        paramValues += paramValue;
// paramValues += templatesLoaded[templateId].instances[templateInstanceId].property[param.paramId];
                                    } else {
                                        paramValues += "All";
                                    }
                                    firstParam = false;
                                }
                                break;
                                break;
                            }
                        }
                    }
                    strResult += paramNames + "\n";
                    strResult += paramValues + "\n";
                    strResult += "\n";
                    strResult += "REPORT FORMAT" + "\n";
                }

                var firstColumn;
                if (includeHeaders) {
                    strResult += result.xAxis.title.text;
                    if (result.zAxis && result.zAxis.title.text) {
                        strResult += SEPARATOR;
                        strResult += result.zAxis.title.text;
                    }
                    strResult += SEPARATOR;
                    strResult += result.yAxis.title.text;
                    strResult += "\n";
                }
                // Recorre cada serie
                var series = result.series;
                var serieData;
                var serieName;
                var serieNameAdded;
                for (var serieIndex in series) {
                    serieData = series[serieIndex].data;
                    serieName = series[serieIndex].name;
                    // Recorre cada fila de la serie
                    for (var row in serieData) {
                        firstColumn = true;
                        serieNameAdded = false;
                        for (var column in serieData[row]) {
                            if (column !== "visible") { // Visible es un atributo de Highcharts
                                if (!firstColumn) { // Si no es la primera columna inserta el separador
                                    if (!serieNameAdded) {
                                        if ((serieName !== null) && (result.zAxis && result.zAxis.title.text)) {
                                            strResult += SEPARATOR + serieName;
                                            serieNameAdded = true;
                                        }
                                    }
                                    strResult += SEPARATOR;
                                }
                                strResult += getFormattedValue(serieData [row][column], column, result, timestampFormat, dateRegex);
                                firstColumn = false;
                            }
                        }
                        strResult += "\n";
                    }
                }
                // Exporta el resultado armado en el archivo indicado
                var blob = new Blob([strResult], {type: "text/csv"});
                saveAs(blob, "report.csv");
            }

            function exportDataToPDF(selectedNode, templateId, result, includeTemplateInfo, includeHeaders, timestampFormat, dateRegex, paperWidth, paperHeight) {
                // Parsea los valores de la hoja
                var docWidth = PAPER_WIDTH;
                if (paperWidth && $.isNumeric(paperWidth)) {
                    docWidth = parseInt(paperWidth);
                }
                var docHeight = PAPER_HEIGHT;
                if (paperHeight && $.isNumeric(paperHeight)) {
                    docHeight = parseInt(paperHeight);
                }

                // Crea la hoja del tamanio indicado
                var doc = new jsPDF('portrait', 'pt', [docWidth * 2.83465, docHeight * 2.83465]);
// var doc = new jsPDF('portrait', 'pt', );
                if (includeTemplateInfo) {
                    doc.setFontSize(14);
                    doc.text(40, 50, "TEMPLATE INFO"); // (Margen izquierdo, Margen superior, texto)
                    doc.setFontSize(12);
                    doc.text(40, 70, "Domain: " + reportTemplatesDomain);
                    for (var groupIndex in reportTemplates) {
                        for (var templateInfoIndex in reportTemplates[groupIndex].templateInfos) {
                            if (reportTemplates[groupIndex].templateInfos[templateInfoIndex].id === templateId) {
                                doc.text(40, 90, "Group: " + reportTemplates[groupIndex].name);
                                doc.text(40, 110, "Template: " + reportTemplates[groupIndex].templateInfos[templateInfoIndex].name);
                                doc.text(40, 130, "Template Instance: " + selectedNode.text);

                                doc.setFontSize(14);
                                doc.text(40, 180, "USER INPUTS");
                                var element = document.getElementById("tableProperties");
                                var res = doc.autoTableHtmlToJson(element);
                                // Arma la tabla con su enzabezado
                                doc.autoTable(["Property", "Value"], res.data, {
                                    margin: {top: 190}
                                });
                                break;
                                break;
                            }
                        }
                    }

                    // Results
                    doc.addPage();
                    doc.setFontSize(14);
                    doc.text(40, 50, "REPORT FORMAT");
                }

                var headers = [];
                var totalData = [];
                var rowData;
                // Para PDF los headers se cargan siempre, la opcion es para CSV
                headers.push(result.xAxis.title.text);
                if (result.zAxis && result.zAxis.title.text) {
                    headers.push(result.zAxis.title.text);
                }
                headers.push(result.yAxis.title.text);
                // Recorre cada serie
                var series = result.series;
                var serieData;
                var serieName;
                var serieNameAdded;
                for (var serieIndex in series) {
                    serieData = series[serieIndex].data;
                    serieName = series[serieIndex].name;
                    // Recorre cada fila de la serie
                    for (var row in serieData) {
                        serieNameAdded = false;
                        rowData = [];
                        for (var column in serieData[row]) {
                            if (column !== "visible") { // Visible es un atributo de Highcharts
                                rowData.push(getFormattedValue(serieData[row][column], column, result, timestampFormat, dateRegex));
                                if (!serieNameAdded) {
                                    if ((serieName !== null) && (result.zAxis && result.zAxis.title.text)) {
                                        rowData.push(serieName);
                                        serieNameAdded = true;
                                    }
                                }
                            }
                        }
                        totalData.push(rowData);
                    }
                }

                // Results
                doc.autoTable(headers, totalData, {
                    margin: {top: 60},
                    tableWidth: 'auto'
                });
                doc.save("report.pdf");
            }

            function getFormattedValue(value, columnIndex, result, timestampFormat, dateRegex) {
                var formattedValue;
                var type;

                // Obtiene la columna a la que pertenece el valor a procesar
                switch (columnIndex) {
                    case '0': // x
                        type = result.xAxis.type;
                        break;
                    case '1': // y
                        type = result.yAxis.type;
                        break;
                    case '2': // z
                        type = result.zAxis.type;
                        break;
                }

                // Obtiene el valor formateado de acuerdo al tipo
                if (type) {
                    switch (type) {
                        case 'datetime':
                            formattedValue = getTimestamp(value, timestampFormat, dateRegex);
                            break;
                        default:
                            formattedValue = value;
                            break;
                    }
                } else {
                    formattedValue = value;
                }

                return formattedValue;
            }

            function getTimestamp(timestamp, format, dateRegex) {
                switch (format) {
                    case 'milliseconds':
                        return timestamp;
                    case 'date':
                        if (!dateRegex) {
                            dateRegex = DATE_FORMAT;
                        }
                        // ANTERIOR ANDANDO
                        // return new Date(timestamp).toString(dateRegex);
                        // PARA INDIA LO DE ABAJO
                        // return new Date(timestamp).toUTCString();
                        // Obtiene date a partir de millis
                        return getTimezoneDateFromUTCMillis(timestamp).toString(dateRegex);
                }
            }

            function exportTableData() {
                // Quita el dialog de opciones de Export
                $("#exportTableDataOptions").modal("hide");
                // Indica el comienzo del procesamiento
                showIconLoading(true);
                setEnabledButtonStopQuery(false);
                setEnabledTemplateActions(false);
                // Obtiene la template seleccionado
                var selectedNode = getSelectedTemplateNode();
                // Obtiene las opciones de export
                var includeHeaders = $('input[name=includeHeaders]', '#formExportTableDataOptions').is(':checked');
                var timestampFormat = $('input[name=timestamp]:checked', '#formExportTableDataOptions').val();
                var dateRegex = $('input[name=dateRegex]', '#formExportTableDataOptions').val();
                var fileFormat = $('input[name=fileFormat]:checked', '#formExportTableDataOptions').val();
                var paperWidth = $('input[name=paperWidth]', '#formExportTableDataOptions').val();
                var paperHeight = $('input[name=paperHeight]', '#formExportTableDataOptions').val();
                var selectedColumns = [];
                $('#exportColumns :checked').each(function () {
                    selectedColumns.push($(this).val());
                });
                // Obtiene el id de la template seleccionada
                var templateId = selectedNode.templateId;
                // Obtiene el id de la template instance seleccionada
                var templateInstanceId = selectedNode.templateInstanceId;
                // Obtiene las columnas del template
                var templateColumnNames = getTableTemplateColumnNames(templateId);
                // Carga params para emular query de datatable
                var vecParams = {};
                vecParams['templateId'] = templateId;
                vecParams['templateInstanceId'] = templateInstanceId;
                for (var colIndex = 0; colIndex < templateColumnNames.length; colIndex++) {
                    vecParams['columns[' + colIndex + '][search][value]'] = $('#tableReport tfoot th input')[colIndex].value;
                }
                // Llamada AJAX de ejecucion del query
                $.ajax({type: "post",
                    dataType: "json",
                    async: false,
                    data: vecParams,
                    url: urlAPIPrefix + "/queryTableTemplate",
                    success: function (data) {
                        if (data !== null) {
                            var result = data.aaData;

                            // Exporta en el formato indicado
                            switch (fileFormat) {
                                case 'csv':
                                    exportTableDataToCSV(result, templateColumnNames, includeHeaders, timestampFormat, dateRegex, selectedColumns);
                                    break;
                                case 'pdf':
                                    exportTableDataToPDF(result, templateColumnNames, includeHeaders, timestampFormat, dateRegex, selectedColumns, paperWidth, paperHeight);
                                    break;
                                default:
                                    break;
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
                // Indica la finalizacion del procesamiento
                showIconLoading(false);
                setEnabledButtonStopQuery(false);
                setEnabledTemplateActions(true);
            }

            function exportTableDataToCSV(result, templateColumnNames, includeHeaders, timestampFormat, dateRegex, selectedColumns) {
                // Arma el string resultante
                var strResult = "";
                var firstColumn;
                // Escribre los headers si se seleccionaron
                if (includeHeaders) {
                    firstColumn = true;
                    for (var colIndex in selectedColumns) {
                        if (!firstColumn) { // Si no es la primera columna inserta el separador
                            strResult += SEPARATOR;
                        }
                        strResult += templateColumnNames[selectedColumns[colIndex]];
                        firstColumn = false;
                    }
                    strResult += "\n";
                }
                // Recorre cada fila para escribir el resultado
                for (var rowIndex in result) {
                    firstColumn = true;
                    for (var columnIndex in result[rowIndex]) {
                        if (selectedColumns.indexOf(columnIndex) > -1) {
                            if (!firstColumn) { // Si no es la primera columna inserta el separador
                                strResult += SEPARATOR;
                            }
                            // Formatea la columna de Timestamp (siempre es la primera)
                            if (columnIndex === '0') {
                                strResult += getTimestamp(parseFloat(result[rowIndex][columnIndex]), timestampFormat, dateRegex);
                            } else {
                                strResult += result[rowIndex][columnIndex];
                            }
                            firstColumn = false;
                        }
                    }
                    strResult += "\n";
                }
                // Exporta el resultado armado en el archivo indicado
                var blob = new Blob([strResult], {type: "text/csv"});
                saveAs(blob, "report.csv");
            }

            function exportTableDataToPDF(result, templateColumnNames, includeHeaders, timestampFormat, dateRegex, selectedColumns, paperWidth, paperHeight) {
                // Parsea los valores de la hoja
                var docWidth = PAPER_WIDTH;
                if (paperWidth && $.isNumeric(paperWidth)) {
                    docWidth = parseInt(paperWidth);
                }
                var docHeight = PAPER_HEIGHT;
                if (paperHeight && $.isNumeric(paperHeight)) {
                    docHeight = parseInt(paperHeight);
                }

                // Crea la hoja del tamanio indicado
                var doc = new jsPDF('landscape', 'pt', [docWidth * 2.83465, docHeight * 2.83465]);

                var headers = [];
                var totalData = [];
                var rowData;
                // Para PDF los headers se cargan siempre, la opcion es para CSV
                for (var colIndex = 0; colIndex < selectedColumns.length; colIndex++) {
                    headers.push(templateColumnNames[colIndex]);
                }
                // Recorre cada fila para escribir el resultado
                for (var rowIndex in result) {
                    rowData = [];
                    for (var columnIndex in result[rowIndex]) {
                        if (selectedColumns.indexOf(columnIndex) > -1) {
                            // Formatea la columna de Timestamp (siempre es la primera)
                            if (columnIndex === '0') {
                                rowData.push(getTimestamp(parseFloat(result[rowIndex][columnIndex]), timestampFormat, dateRegex));
                            } else {
                                rowData.push(result[rowIndex][columnIndex]);
                            }
                        }
                    }
                    totalData.push(rowData);
                }
                // Results
                doc.autoTable(headers, totalData, {
                    margin: {top: 50},
                    tableWidth: 'auto'
                });
                doc.save("report.pdf");
            }

            function exportHistogramData() {
                // Indica el comienzo del procesamiento
                showIconLoading(true);
                setEnabledButtonStopQuery(false);
                setEnabledTemplateActions(false);
                // Obtiene el template seleccionado
                var selectedNode = getSelectedTemplateNode();
                // Obtiene el id de la template seleccionada
                var templateId = selectedNode.templateId;
                // Obtiene el id de la template instance seleccionada
                var templateInstanceId = selectedNode.templateInstanceId;
                // Obtiene los params del template correspondiente
                var templateParams = getTemplateParams(templateId);
                // Arma el vector de params a enviar en la llamada AJAX
                var vecParams = {};
                vecParams['templateId'] = templateId;
                var paramIndex, param;
                for (paramIndex in templateParams) {
                    param = templateParams[paramIndex];
                    vecParams[param.paramName] = JSON.stringify(templatesLoaded[templateId].instances[templateInstanceId].property[param.paramId]);
                }
                var resultQuery = templatesLoaded[templateId].instances[templateInstanceId].result.series[1].data[0][0];
                vecParams['limitValue'] = resultQuery;
                // Lllamada AJAX para exportar la data especifica del histogram
                console.log('Result param: ' + JSON.stringify(vecParams));
                $.ajax({type: "post",
                    dataType: "json",
                    async: false,
                    data: vecParams,
                    url: urlAPIPrefix + "/exportData",
                    success: function (data) {
                        // Exporta el resultado armado en el archivo indicado
                        var blob = new Blob([data], {type: "text/csv"});
                        saveAs(blob, "report.csv");
                    },
                    error: function (response) {
                        var str = response.responseText + "";
                        var pos = str.lastIndexOf("Login");
                        if (pos !== -1) {
                            window.location = window.location.href;
                        }
                    }
                });
                // Indica la finalizacion del procesamiento
                showIconLoading(false);
                setEnabledButtonStopQuery(false);
                setEnabledTemplateActions(true);
            }

            function addReportTemplateInstance() {
                var reportTamplateInstanceName = null;
                reportTamplateInstanceName = $('#reportTemplateInstanceName').val();
                $('#dailogAddReportTemplateInstanceButtonClose').click();
                $('#reportTemplateInstanceName').val('');
                if ((reportTamplateInstanceName !== null) && (reportTamplateInstanceName !== undefined) && (reportTamplateInstanceName.length > 0)) {
                    addReportTemplateInstanceNode(reportTamplateInstanceName);
                }
            }

            function addReportTemplateInstanceNode(reportTemplateInstanceName) {
                var templateId = null;
                var templateParentNode = null;
                var templateInstanceId = null;
                var templateVersionId = null;
                var templateType = null;
                // Obtiene el id de la template a instanciar
                templateId = getSelectedTemplateId();
                // Obtiene la version de la template
                templateVersionId = getTemplateVersionIdByTemplateId(templateId);
                // Obtiene el tipo de template
                templateType = getTemplateTypeByTemplateId(templateId);
                // Obtiene el template nodo
                templateParentNode = getSelectedTemplateNode();
                // Llamada ajax para crear la instancia del reporte
                var vecParams = {};
                vecParams['templateId'] = templateId;
                vecParams['templateInstanceName'] = reportTemplateInstanceName;
                vecParams['templateVersionId'] = templateVersionId;
                vecParams['templateType'] = templateType;
                $.ajax({type: "post",
                    dataType: "json",
                    async: false,
                    data: vecParams,
                    url: urlAPIPrefix + "/addReportTemplateInstance",
                    success: function (data) {
                        templateInstanceId = data;
                    },
                    error: function (response) {
                        var str = response.responseText + "";
                        var pos = str.lastIndexOf("Login");
                        if (pos !== -1) {
                            window.location = window.location.href;
                        }
                    }
                });
                addReportTemplateInstanceNodeToParent(templateId, templateInstanceId, reportTemplateInstanceName, templateType, templateParentNode, true);
                // Carga la template correspondiente
                if (isTableTemplate(templateType)) {
                    loadTableTemplate(templateId, templateInstanceId);
                } else {
                    loadTemplate(templateId, templateInstanceId);
                }
            }

            // Crea la tabla correspondiente
            function createTable(templateId, templateInstanceId) {
                var columnNames = getTableTemplateColumnNames(templateId);

                if (oTableReportTemplate !== null && oTableReportTemplate !== undefined) {
                    oTableReportTemplate.destroy();
                }
                // Headers & Footers
                $('#tableReport').empty();
                $('#tableReport').append("<thead><tr></tr></thead>");
                $('#tableReport').append("<tfoot><tr></tr></tfoot>");
                for (var colIndex in columnNames) {
                    $('#tableReport thead tr').append("<th>" + columnNames[colIndex] + "</th>");
                    $('#tableReport tfoot tr').append("<th>" + columnNames[colIndex] + "</th>");
                }
                // Setea textfield en los footer de cada columna
                $('#tableReport tfoot th').each(function () {
                    var title = $('#tableReport thead th').eq($(this).index()).text();
                    $(this).html('<input type="text" index=' + $(this).index() + ' placeholder="' + title + '" />');
                });
                // Crea datatable
                oTableReportTemplate = $('#tableReport').DataTable({
                    "processing": true,
                    "serverSide": true,
                    "pageLength": 25,
                    "aoColumnDefs": [
                        {
                            "aTargets": [0], // Timestamp es siempre la primer columna
                            "mRender": function (timestamp) {
                                if (isTimestamp(timestamp)) {
                                    return getTimezoneDateFromUTCMillis(parseInt(timestamp)).toString(DATE_FORMAT);
                                } else {
                                    return timestamp;
                                }
                            }
                        }
                    ],
                    "ajax": {
                        "url": urlAPIPrefix + "/queryTableTemplate",
                        "data": function (d) {
                            d.templateId = templateId;
                            d.templateInstanceId = templateInstanceId;
                        }
                    }
                });
                // Aplica la busqueda por columna
                oTableReportTemplate.columns().every(function () {
                    var that = this;

                    $('input', this.footer()).on('keyup change', function () {
                        if (that.search() !== this.value) {
                            // Guarda en memoria
                            templatesLoaded[templateId].instances[templateInstanceId].property[$(this).attr("index")] = this.value;
                            // Refresca la tabla con el search
                            that.search(this.value).draw();
                        }
                    });
                });
            }

            // Verificar si el valor recibido es un timestamp en milliseconds o no
            function isTimestamp(value) {
                if (!isNaN(value) && value.toString().length === 13) {
                    return true;
                }
                return false;
            }

            // A partir de milliseconds en UTC, obtiene Date Time correspondiente al Timezone
            function getTimezoneDateFromUTCMillis(time) {
                var date = new Date(time);
                // convert to msec, add local time zone offset and get UTC time in msec
                var utc = date.getTime() + (date.getTimezoneOffset() * 60000);
                // create new Date object for different city using supplied offset
                return new Date(utc + offsetInMillis);
            }

            function addReportTemplateInstanceNodeToParent(templateId, templateInstanceId, templateInstanceName, templateType, templateParentNode, isActive) {
                // Genera el nodo a agregar en el tree
                var sourceNode = {};
                sourceNode.text = templateInstanceName;
                sourceNode.isFolder = false;
                sourceNode.isActive = isActive;
                sourceNode.isExpanded = true;
                sourceNode.isLazy = false;
                sourceNode.liClass = "node-template";
                sourceNode.uiIcon = "ui-icon-template";
                sourceNode.templateId = templateId;
                sourceNode.templateInstanceId = templateInstanceId;
                sourceNode.templateType = templateType;
                // Agrega el nodo
                if (templateParentNode !== undefined && templateParentNode !== null) {
                    templateParentNode.isActive = false;
                    easytree.addNode(sourceNode, templateParentNode.id);
                } else {
// easytree.addNode(sourceNode, templateParentNode);
                }


                easytree.rebuildTree();
            }

            function editReportTemplateInstance() {
                var templateInstanceName = $('#newReportTemplateInstanceName').val();
                $('#dialogEditReportTemplateInstanceButtonClose').click();
                $('#newReportTemplateInstanceName').val('');
                if (templateInstanceName !== null && templateInstanceName !== undefined && templateInstanceName.length > 0) {
                    var selectedNode = getSelectedTemplateNode();
                    if (selectedNode !== undefined && selectedNode !== null) {
                        var templateId = selectedNode.templateId;
                        var templateInstanceId = selectedNode.templateInstanceId;
                        // Llamada AJAX
                        var vecParams = {};
                        vecParams['templateId'] = templateId;
                        vecParams['templateInstanceId'] = templateInstanceId;
                        vecParams['templateInstanceName'] = templateInstanceName;
                        // Llamada ajax para duplicar la instancia del template
                        $.ajax({type: "post",
                            dataType: "json",
                            async: false,
                            data: vecParams,
                            url: urlAPIPrefix + "/renameReportTemplateInstance",
                            success: function (data) {
                                // Actualiza el nombre
                                selectedNode.text = templateInstanceName;
                                easytree.rebuildTree();
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
                }
            }

            function deleteReportTemplateInstance() {
                var selectedNode = null;
                $('#dailogDeleteReportTemplateInstanceButtonClose').click();
                // Obtiene el nodo seleccionado
                selectedNode = getSelectedTemplateNode();
                if (selectedNode !== null && selectedNode !== undefined) {
                    // Llamada ajax para borrar la instancia del reporte
                    var vecParams = {};
                    vecParams['templateId'] = selectedNode.templateId;
                    vecParams['templateInstanceId'] = selectedNode.templateInstanceId;
                    $.ajax({type: "post",
                        dataType: "json",
                        async: false,
                        data: vecParams,
                        url: urlAPIPrefix + "/deleteReportTemplateInstance",
                        success: function (data) {
                        },
                        error: function (response) {
                            var str = response.responseText + "";
                            var pos = str.lastIndexOf("Login");
                            if (pos !== -1) {
                                window.location = window.location.href;
                            }
                        }
                    });
                    // Busca el nodeTemplate
                    var templateParentNode = getTemplateNodeByTemplateName(getTemplateNameByTemplateId(selectedNode.templateId));
                    templateParentNode.isActive = true;
                    // Borra el nodo del tree
                    easytree.removeNode(selectedNode.id);
                    easytree.rebuildTree();
                    // Selecciona el nodeTemplate
                    easytree.activateNode(templateParentNode.id);
                    //
                    delete templatesLoaded[selectedNode.templateId].instances[selectedNode.templateInstanceId];
                    // clear del panel
                    clearChart();
                }
            }

            function setFalseChildren(node) {
                for (var index in node.children) {
                    node.children[index].isActive = false;
                }
            }

            function duplicateReportTemplateInstance() {
                var templateInstanceName = null;
                templateInstanceName = $('#reportTemplateInstanceNameDuplicated').val();
                $('#dailogDuplicateReportTemplateInstanceButtonClose').click();
                $('#reportTemplateInstanceNameDuplicated').val('');
                if (templateInstanceName !== null && templateInstanceName !== undefined && templateInstanceName.length > 0) {
                    var selectedNode = getSelectedTemplateNode();
                    if (selectedNode !== undefined && selectedNode !== null) {
                        var templateId = selectedNode.templateId;
                        var templateVersionId = getTemplateVersionIdByTemplateId(templateId);
                        var templateInstanceId = selectedNode.templateInstanceId;
                        var templateType = selectedNode.templateType;
                        var result = "";
                        var property = null;
                        var newTemplateInstanceId = null;
                        var templateParentNode = null;
                        // Result original
                        if (templatesLoaded[templateId].instances[templateInstanceId].result !== undefined && templatesLoaded[templateId].instances[templateInstanceId].result !== null && templatesLoaded[templateId].instances[templateInstanceId].result !== "") {
// result = templatesLoaded[templateId].instances[templateInstanceId].result;
                        }
                        // Property original
                        if (templatesLoaded[templateId].instances[templateInstanceId].property !== undefined && templatesLoaded[templateId].instances[templateInstanceId].property !== null) {
                            property = {};
                            for (var indexProperty in templatesLoaded[templateId].instances[templateInstanceId].property) {
                                property[indexProperty] = templatesLoaded[templateId].instances[templateInstanceId].property[indexProperty];
                            }
                        }
                        var vecParams = {};
                        vecParams['templateId'] = templateId;
                        vecParams['templateInstanceId'] = templateInstanceId;
                        vecParams['templateInstanceName'] = templateInstanceName;
                        vecParams['templateVersionId'] = templateVersionId;
                        vecParams['templateType'] = templateType;
                        // Llamada ajax para duplicar la instancia del template
                        $.ajax({type: "post",
                            dataType: "json",
                            async: false,
                            data: vecParams,
                            url: urlAPIPrefix + "/duplicateReportTemplateInstance",
                            success: function (data) {
                                newTemplateInstanceId = data;
                            },
                            error: function (response) {
                                var str = response.responseText + "";
                                var pos = str.lastIndexOf("Login");
                                if (pos !== -1) {
                                    window.location = window.location.href;
                                }
                            }
                        });
                        if (templatesLoaded[templateId].instances[newTemplateInstanceId] === undefined) {
                            // Inicializa una instancia
                            templatesLoaded[templateId].instances[newTemplateInstanceId] = {};
                            // Inicializa property de la instancia de template
                            templatesLoaded[templateId].instances[newTemplateInstanceId].property = {};
                            // Inicializa result de la instancia de template
                            templatesLoaded[templateId].instances[newTemplateInstanceId].result = "";
                        }
                        templatesLoaded[templateId].instances[newTemplateInstanceId].property = property;
                        templatesLoaded[templateId].instances[newTemplateInstanceId].result = result;
                        templateParentNode = getTemplateNodeByTemplateName(getTemplateNameByTemplateId(templateId));
                        setFalseChildren(templateParentNode);
                        addReportTemplateInstanceNodeToParent(templateId, newTemplateInstanceId, templateInstanceName, templateType, templateParentNode, true);
                        var templateType = getTemplateTypeByTemplateId(templateId);
                        if (isTableTemplate(templateType)) {
                            loadTableTemplate(templateId, templateInstanceId);
                        } else {
                            loadTemplate(templateId, templateInstanceId);
                        }
                        // Limpia el grafico
                        clearChart();
                    }
                }
            }

            function closeAddReportTemplateInstance() {
                $('#reportTemplateInstanceName').val('');
            }

            function closeEditReportTemplateInstance() {
                $('#newReportTemplateInstanceName').val('');
            }

            function closeDuplicateReportTemplateInstance() {
                $('#reportTemplateInstanceNameDuplicated').val('');
            }

            function closeExportDataOptions() {
                $('#exportDataOptions').modal("hide");
            }

            function closeExportTableDataOptions() {
                $('#exportTableDataOptions').modal("hide");
            }