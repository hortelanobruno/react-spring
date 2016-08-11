/* 
 * Solo llamadas ajax y modificaciones en los paneles.
 */

var scabbVersions;
var CSVTable;
var cmtss;
var tableInterestConversionList;
var tableCounterConsumptionConfiguration;
var tableURLHits;
var tableConditionElement;
var tableCategories;
var tableCounters;

function createDataTables() {
    scabbVersions = $('#scabbVersions').DataTable();
    cmtss = $('#cmtss').DataTable();
    CSVTable = $('#CSVTable').DataTable();
}
function createDataTablesRTA() {
    tableInterestConversionList = $('#tableInterestConversionList').DataTable();
    tableCounterConsumptionConfiguration = $('#tableCounterConsumptionConfiguration').DataTable();
    tableURLHits = $('#tableURLHits').DataTable();
    tableConditionElement = $('#tableConditionElement').DataTable({ordering: false});
    tableCategories = $('#tableCategories').DataTable();
    tableCounters = $('#tableCounters').DataTable();
}
/////////////////////////////// Metodo retrive ///////////////////////////////////
var rtaConfig = {};
var periodicDataRecordTasks = [];

function getNapConfiguration() {
    var urlPrefix = $("#urlAPIPrefix").val();
    $.ajax({
        type: "post",
        url: urlPrefix + "/getNapConfiguration",
        async: false,
        success: function (data) {

            loandAllBlocksConfig(data);
        },
        error: function (response) {
            window.location = window.location.href;
        }
    });
}

function loandAllBlocksConfig(data) {
    if (data !== null) {
        var periodicTasks = "";
        // Collector Configuration
        // RDR Server Configuration
        loandCollectorConfiguration(data)
        // Data Record Configuration
        loandDataRecordConfiguration(data);

        //RMI
        if (typeof data.rmiConfiguration !== "undefined") {
            $("#RMIIP").val(data.rmiConfiguration.ipAddress);
            $("#RMIPort").val(data.rmiConfiguration.port);
            $("#RMIService").val(data.rmiConfiguration.serviceName);
        }
        //Res
        if (typeof data.restConfiguration !== "undefined") {
            $("#RestIP").val(data.restConfiguration.serverIpAddress);
            $("#RestPort").val(data.restConfiguration.serverPort);
        }
        //Sep
        if (typeof data.sepConfiguration !== "undefined") {
            $("#SepIP").val(data.sepConfiguration.ipAddress);
            $("#SepPort").val(data.sepConfiguration.port);
        }
        //SER
        if (typeof data.notificationServerConfiguration !== "undefined") {
            $("#SERIP").val(data.notificationServerConfiguration.ipAddress);
            $("#SERPort").val(data.notificationServerConfiguration.port);
        }
        //CSM
        if (typeof data.csmConfiguration !== "undefined") {
            $("#CSMIP").val(data.csmConfiguration.ipAddress);
            $("#CSMPort").val(data.csmConfiguration.port);
        }
        createDataTables();

        // PeriodicConfiguration --> periodicDataRecordTasks
        if (typeof data.periodicConfiguration.periodicDataRecordTasks !== "undefined") {
            periodicTasks = loandPeriodicDataRecordTasks(data.periodicConfiguration.periodicDataRecordTasks);
        }
        $("#selectPeriodicConfig").html(periodicTasks);
    }
}

function loandCollectorConfiguration(data) {
    var allowedTypes = "";
    var scabbVersions = "";
    var cmtss = "";
    if (typeof data.collectorConfiguration !== "undefined") {
        if (typeof data.collectorConfiguration.rdrServerConfiguration !== "undefined") {
            $("#RDRPort").val(data.collectorConfiguration.rdrServerConfiguration.port);
            $("#queueDepth").val(data.collectorConfiguration.rdrServerConfiguration.queueDepth);
            $("#timeout").val(data.collectorConfiguration.rdrServerConfiguration.timeout);
            if (typeof data.collectorConfiguration.rdrServerConfiguration.allowedTypes !== "undefined") {
                for (var i = 0; i < data.collectorConfiguration.rdrServerConfiguration.allowedTypes.length; i++) {
                    allowedTypes += "<option value='" + data.collectorConfiguration.rdrServerConfiguration.allowedTypes[i] + "' >" + data.collectorConfiguration.rdrServerConfiguration.allowedTypes[i] + "</option>";
                }
            }
            if (typeof data.collectorConfiguration.rdrServerConfiguration.defaultScabbVersion !== "undefined") {
                $("#defaultScabbVersion").val(data.collectorConfiguration.rdrServerConfiguration.defaultScabbVersion);
            }
            $('#enableAggregator').attr("checked", data.collectorConfiguration.rdrServerConfiguration.enableAggregator);
            if (typeof data.collectorConfiguration.rdrServerConfiguration.scabbVersions !== "undefined") {
                var o = data.collectorConfiguration.rdrServerConfiguration.scabbVersions;
                for (var k in o) {
                    scabbVersions += "<tr>";
                    scabbVersions += "<td>" + k + "</td>";
                    scabbVersions += "<td>" + o[k] + "</td>";
                    scabbVersions += "</tr>";
                }
            }
        }
        // IPDR Server Configuration
        $("#ipIPDR").val(data.collectorConfiguration.ipdrServerConfiguration.ip);
        $("#portIPDR").val(data.collectorConfiguration.ipdrServerConfiguration.port);
        $('#enableAgregatorIPDR').attr("checked", data.collectorConfiguration.ipdrServerConfiguration.enableAggregator);
        if (typeof data.collectorConfiguration.ipdrServerConfiguration.cmtss !== "undefined") {
            for (var i = 0; i < data.collectorConfiguration.ipdrServerConfiguration.cmtss.length; i++) {
                cmtss += "<tr>";
                cmtss += "<td>" + data.collectorConfiguration.ipdrServerConfiguration.cmtss[i].ip + "</td>";
                cmtss += "<td>" + data.collectorConfiguration.ipdrServerConfiguration.cmtss[i].port + "</td>";
                cmtss += "<td>" + data.collectorConfiguration.ipdrServerConfiguration.cmtss[i].vendor + "</td>";
                cmtss += "<td>" + data.collectorConfiguration.ipdrServerConfiguration.cmtss[i].ipdrEnabled + "</td>";
                cmtss += "</tr>";
            }
        }
    }
    $("#allowedTypes").html(allowedTypes);
    $("#scabbVersionsTbody").html(scabbVersions);
    $("#cmtssTbody").html(cmtss);

}

function loandDataRecordConfiguration(data) {
    var allowedTagsForPYC = "";
    var allowedTagsForCSVManager = "";
    var allowedTagsForDBManager = "";
    var allowedTagsForRTAManager = "";
    var defaultConfiguration = "";
    var adapters = "";

    if (typeof data.dataRecordConfiguration !== "undefined") {
        $('#PYCRenebleAgregator').attr("checked", data.dataRecordConfiguration.enablePYC);
        if (typeof data.dataRecordConfiguration.allowedTagsForPYC !== "undefined") {
            for (var i = 0; i < data.dataRecordConfiguration.allowedTagsForPYC.length; i++) {
                allowedTagsForPYC += "<option value='" + data.dataRecordConfiguration.allowedTagsForPYC[i] + "' >" + data.dataRecordConfiguration.allowedTagsForPYC[i] + "</option>";
            }
        }
        $("#AllowedTagsPYC").html(allowedTagsForPYC);
        $('#EnableCSV').attr("checked", data.dataRecordConfiguration.enableCSVManager);
        if (typeof data.dataRecordConfiguration.allowedTagsForCSVManager !== "undefined") {
            for (var i = 0; i < data.dataRecordConfiguration.allowedTagsForCSVManager.length; i++) {
                allowedTagsForCSVManager += "<option value='" + data.dataRecordConfiguration.allowedTagsForCSVManager[i] + "' >" + data.dataRecordConfiguration.allowedTagsForCSVManager[i] + "</option>";
            }
        }
        $("#AllowedTagsCSV").html(allowedTagsForCSVManager);
        $('#EnableDBManager').attr("checked", data.dataRecordConfiguration.enableDBManager);
        if (typeof data.dataRecordConfiguration.allowedTagsForDBManager !== "undefined") {
            for (var i = 0; i < data.dataRecordConfiguration.allowedTagsForDBManager.length; i++) {
                allowedTagsForDBManager += "<option value='" + data.dataRecordConfiguration.allowedTagsForDBManager[i] + "' >" + data.dataRecordConfiguration.allowedTagsForDBManager[i] + "</option>";
            }
        }
        $("#AllowedTagsDBManager").html(allowedTagsForDBManager);
        $('#enebleRTA').attr("checked", data.dataRecordConfiguration.enableRTAManager);
        if (typeof data.dataRecordConfiguration.allowedTagsForRTAManager !== "undefined") {
            for (var i = 0; i < data.dataRecordConfiguration.allowedTagsForRTAManager.length; i++) {
                allowedTagsForRTAManager += "<option value='" + data.dataRecordConfiguration.allowedTagsForRTAManager[i] + "' >" + data.dataRecordConfiguration.allowedTagsForRTAManager[i] + "</option>";
            }
        }
        $("#AllowedTagsRTA").html(allowedTagsForRTAManager);
        // CSV default Configuration  
        if (typeof data.dataRecordConfiguration.csvConfiguration.defaultConfiguration !== "undefined") {
            defaultConfiguration += "<tr>";
            defaultConfiguration += "<td>*</td>";
            defaultConfiguration += "<td>" + data.dataRecordConfiguration.csvConfiguration.defaultConfiguration.separator + "</td>";
            defaultConfiguration += "<td>" + data.dataRecordConfiguration.csvConfiguration.defaultConfiguration.includeRDRHeaderInfo + "</td>";
            defaultConfiguration += "<td>" + data.dataRecordConfiguration.csvConfiguration.defaultConfiguration.sourceIpFormat + "</td>";
            defaultConfiguration += "<td>" + data.dataRecordConfiguration.csvConfiguration.defaultConfiguration.timestampFormat + "</td>";
            defaultConfiguration += "<td>" + data.dataRecordConfiguration.csvConfiguration.defaultConfiguration.timestampFormatPattern + "</td>";
            defaultConfiguration += "</tr>";
        }
        // CSV Configuration  
        if (typeof data.dataRecordConfiguration.csvConfiguration.csvAdapterConfigurations !== "undefined") {
            // Se realiza de esta manera porque es un Mapa
            var obj = data.dataRecordConfiguration.csvConfiguration.csvAdapterConfigurations;
            for (var key in obj) {
                var objL = obj[key];
                defaultConfiguration += "<tr>";
                defaultConfiguration += "<td>" + key + "</td>";
                for (var keyL in objL) {
                    defaultConfiguration += "<td>" + objL[keyL] + "</td>";
                }
                defaultConfiguration += "</tr>";
            }
        }
        $("#CSVtbody").html(defaultConfiguration);
        // DB
        //Mysql
        $("#url").val(data.dataRecordConfiguration.dbConfiguration.url);
        $("#user").val(data.dataRecordConfiguration.dbConfiguration.user);
        $("#password").val(data.dataRecordConfiguration.dbConfiguration.password);
        //Mongo
        $("#DBName").val(data.dataRecordConfiguration.dbMongoConfiguration.interestsDBName);
        $("#tableName").val(data.dataRecordConfiguration.dbMongoConfiguration.interestsDBTableName);
        $("#SuscriberIdCol").val(data.dataRecordConfiguration.dbMongoConfiguration.interestsDBSubscriberIdColumn);
        $("#InterestIdCol").val(data.dataRecordConfiguration.dbMongoConfiguration.interestsDBInterestsColumn);

        // rtaConfiguration
        if (typeof data.dataRecordConfiguration.rtaConfiguration !== "undefined") {
            adapters = loandRTAConfiguration(data.dataRecordConfiguration.rtaConfiguration);
        }
        $("#selectRTAConfig").html(adapters);
    }
}

function loandPeriodicDataRecordTasks(periodicDataRecords) {
    var periodicTasks = "";
    try {
        periodicDataRecordTasks = periodicDataRecords;
        var i = 0;
        for (i; i < periodicDataRecords.length; i++) {
            if (i === 0) {
                loandPeriodicConfig(i);
                periodicTasks += "<option selected value='0'>Periodic Data Record Task " + i + "</option>";
            } else {
                periodicTasks += "<option value='" + i + "'>Periodic Data Record Task " + i + "</option>";
            }
        }
        if (i === 0) {
            $("#FormPeriodicConfig").hide();

        } else {
            $("#FormPeriodicConfig").show();
        }
    } catch (error) {
        $("#FormPeriodicConfig").hide();
    }
    return periodicTasks;
}

//Se utiliza solo para seleccionar PeriodicDataRecordTasks. Para que cargue el panel de la derecha
function selectPeriodicDataRecordTasks(select, idButtonDelete) {
    //Primero la cantidad para saber cuando dejo de seleccionar.
    var cantSelect = 0;
    //Despues cargo todas las opciones que me mado el select
    var l = select.options;
    //Realizo el recorrido para saber cual selecciono
    for (var q = 0; q < l.length; q++) {
        //si encuentro uno seleccionado
        if (l[q].selected) {
            //Sumo la cantidad
            cantSelect++;
            //me fijo que la opcion tenga valor
            if (typeof l[q].value !== "undefined") {
                try {
                    for (var i = 0; i < periodicDataRecordTasks.length; i++) {
                        if (i === q) {
                            //cargo el panel
                            loandPeriodicConfig(i);
                            $("#FormPeriodicConfig").show();
                            i = periodicDataRecordTasks.length;
                        }
                    }
                } catch (error) {
                    $("#FormPeriodicConfig").hide();
                }
            } else {
                $("#FormPeriodicConfig").hide();
            }
        }
    }
    // Desabilita el boton delete si no hay nada seleccionado
    if (cantSelect === 0 || cantSelect > 1) {
        $(idButtonDelete).prop('disabled', true);
    } else {
        $(idButtonDelete).prop('disabled', false);
    }
}

// Carga el panel de la derecha de PeriodicConfig 
function loandPeriodicConfig(index) {
    // Se ocultan los botones de Actions
    $("#RTAConfig-Actions").hide();
    var selectRecordTypes = "";
    // Objetos de todos los PeriodicConfig
    if (typeof periodicDataRecordTasks[index].enabled !== "undefined") {
        if (periodicDataRecordTasks[index].enabled == true) {
            $("#EnablePeriodicConfig").prop('checked', true);
        } else if (periodicDataRecordTasks[index].enabled == false) {
            $("#EnablePeriodicConfig").prop('checked', false);
        }
    } else {
        $("#EnablePeriodicConfig").prop('checked', false);
    }
    if (typeof periodicDataRecordTasks[index].type !== "undefined") {
        $("#typePeriodic").val(periodicDataRecordTasks[index].type);
    } else {
        $("#typePeriodic").val("");
    }

    if (typeof periodicDataRecordTasks[index].recordTypes !== "undefined") {
        for (var i = 0; i < periodicDataRecordTasks[index].recordTypes.length; i++) {
            selectRecordTypes += "<option value='" + periodicDataRecordTasks[index].recordTypes[i] + "' >" + periodicDataRecordTasks[index].recordTypes[i] + "</option>";
        }
    }
    if (typeof periodicDataRecordTasks[index].cronPattern !== "undefined") {
        $("#cronPattern").val(periodicDataRecordTasks[index].cronPattern);
    } else {
        $("#cronPattern").val("");
    }
    $("#selectRecordTypes").html(selectRecordTypes);

    // Objeto de PeriodicRemovalTask
    if (periodicDataRecordTasks[index].type == "PeriodicRemovalTask") {
        $("#PeriodicRemovalTask").show();
        if (typeof periodicDataRecordTasks[index].removalConfiguration.days !== "undefined") {
            $("#days").val(periodicDataRecordTasks[index].removalConfiguration.days);
        } else {
            $("#days").val("");
        }
    } else {
        $("#PeriodicRemovalTask").hide();
    }
}

function loandRTAConfiguration(rtaConfiguration) {
    createDataTablesRTA();
    var adapters = "";
    var cont = 0;
    if (typeof rtaConfiguration.adapters !== "undefined") {
        // Se realiza de esta manera porque es un Mapa
        var objRTA = rtaConfiguration.adapters;
        // Guardo el map en rtaConfig para futuras consultas
        rtaConfig = objRTA;

        var object0 = {};
        for (var keyRTA in objRTA) {
            if (cont === 0) {
                object0 = objRTA[keyRTA];
                adapters += "<option selected value='" + keyRTA + "' >" + keyRTA + "</option>";
            } else {
                adapters += "<option value='" + keyRTA + "' >" + keyRTA + "</option>";
            }
            cont++;
        }
        loandPanelRTA(object0);
    } else {
        $("#FormRTAConfig").hide();
    }
    if (cont === 0) {
        $("#FormRTAConfig").hide();
    } else {
        $("#FormRTAConfig").show();
    }
    return adapters;
}
//Se utiliza solo para seleccionar RTAs. Para que cargue el panel de la derecha
function selectRTAConfig(select, idButtonDelete) {
    //Primero la cantidad para saber cuando dejo de seleccionar.
    var cantSelect = 0;
    //Despues cargo todas las opciones que me mado el select
    var l = select.options;
    //Realizo el recorrido para saber cual selecciono
    for (var q = 0; q < l.length; q++) {
        //si encuentro uno seleccionado
        if (l[q].selected) {
            //Sumo la cantidad
            cantSelect++;
            //me fijo que la opcion tenga valor
            if (l[q].value != null) {
                var object0 = {};
                // Realizo un recorrido de objeto previamente guardado en el get inicial(es un map)
                for (var keyRTA in rtaConfig) {
                    //si la Key del map coincide con el valor de la opcion seleccionada lo guardo en object0
                    if (l[q].value == keyRTA) {
                        object0 = rtaConfig[keyRTA];
                    }
                }
                //cargo el panel del objeto
                loandPanelRTA(object0);
            }
            //sale del for para no seleccionar mas de una.
            q = l.length;
        }
    }
    // Desabilita el boton delete si no hay nada seleccionado
    if (cantSelect === 0) {
        $(idButtonDelete).prop('disabled', true);
    } else {
        $(idButtonDelete).prop('disabled', false);
        $("#FormRTAConfig").show();
    }
}

var qualityConditionsVec;
var groupURLHitsConfiguration;
// Carga el panel de la derecha de RTAConfig 
function loandPanelRTA(object) {
    $("#RTAConfig-Actions").hide();
    var selectSources = "";
    var allowedCategories = "";
    var services = "";
    var qualityConditions = "";
    // Objetos en comun de todos los RTA
    if (typeof object.enabled !== "undefined") {
        if (object.enabled == true) {
            $("#EnableRTAConfig").prop('checked', true);
        } else if (object.enabled == false) {
            $("#EnableRTAConfig").prop('checked', false);
        }
    } else {
        $("#EnableRTAConfig").prop('checked', false);
    }
    if (typeof object.id !== "undefined") {
        $("#RTAID").val(object.id);
    } else {
        $("#RTAID").val("");
    }
    if (typeof object.granularity !== "undefined") {
        $("#granularity").val(object.granularity);
    } else {
        $("#granularity").val("");
    }
    if (typeof object.sources !== "undefined") {
        for (var i = 0; i < object.sources.length; i++) {
            selectSources += "<option value='" + object.sources[i] + "' >" + object.sources[i] + "</option>";
        }
    }
    $("#selectSources").html(selectSources);
    // Se precenta un caso de gerarquia (clase abstracta) según el tipo de case se carga un panel diferente
    if (typeof object.type !== "undefined") {
        $("#typeRTA").val(object.type);
        //Hay tres Objetos que no tienen datos adicionales para completar.
        if (object.type == "RTAAdapterUserAgentConfiguration"
                || object.type == "RTAAdapterDeviceConfiguration"
                || object.type == "RTAAdapterDefaultConfiguration") {
            $("#liRTAadvancedConfig").hide();
            $("#RTAAdapterCategoryHitsConfiguration").hide();
            $("#RTAAdapterTopperConfiguration").hide();
            $("#RTAAdapterURLCategoryConsumptionConfiguration").hide();
            $("#RTAAdapterURLHitsConfiguration").hide();
            $("#RTAAdapterHeavyConsumptionConfiguration").hide();
            $("#RTAAdapterCategoryConfiguration").hide();
            $('.nav-tabs a[href="#rtaGeneralConfig"]').tab('show');
        } else {
            $("#liRTAadvancedConfig").show();
        }

        if (object.type == "RTAAdapterCategoryConfiguration") {
            $("#RTAAdapterCategoryHitsConfiguration").hide();
            $("#RTAAdapterTopperConfiguration").hide();
            $("#RTAAdapterURLCategoryConsumptionConfiguration").hide();
            $("#RTAAdapterURLHitsConfiguration").hide();
            $("#RTAAdapterHeavyConsumptionConfiguration").hide();
            $("#RTAAdapterFlavorUpdaterConfiguration").hide();
            $("#RTAAdapterCategoryConfiguration").show();
            // Metodo de dataTable para vaciar la tabla
            tableCategories.clear().draw(false);
            tableConditionElement.clear().draw(false);
            // Fin del metodo de dataTable para vaciar la tabla

            qualityConditionsVec = [];

            if (typeof object.defaultCategoryId !== "undefined") {
                $("#defaultCategoryId").val(object.defaultCategoryId);
            } else {
                $("#defaultCategoryId").val("");
            }
            if (typeof object.minDownstreamAllowed !== "undefined") {
                $("#minDownstreamAllowed").val(object.minDownstreamAllowed);
            } else {
                $("#minDownstreamAllowed").val("");
            }
            if (typeof object.minDurationAllowed !== "undefined") {
                $("#minDurationAllowed").val(object.minDurationAllowed);
            } else {
                $("#minDurationAllowed").val("");
            }
            if (typeof object.categoryId !== "undefined") {
                $("#categoryId").val(object.categoryId);
            } else {
                $("#categoryId").val("");
            }
            if (typeof object.services !== "undefined") {
                for (var i = 0; i < object.services.length; i++) {
                    services += "<option value='" + object.services[i] + "' >" + object.services[i] + "</option>";
                }
            }
            $("#services").html(services);

            if (typeof object.categories !== "undefined") {
                for (var i = 0; i < object.categories.length; i++) {
                    tableCategories.row.add([
                        object.categories[i].id,
                        object.categories[i].name,
                        object.categories[i].categoryParentId,
                        object.categories[i].minValue,
                        object.categories[i].maxValue
                    ]).draw(false);
                }
            }

            var conditionElementsVec = {};
            if (typeof object.qualityConditions !== "undefined") {
                // Se guardan temporalmente para que se puedan consultar sin realizar la llamada a la base
                qualityConditionsVec = object.qualityConditions;
                for (var i = 0; i < object.qualityConditions.length; i++) {
                    if (i === 0) {
                        conditionElementsVec = object.qualityConditions[0];
                        loandTableConditionElements(0);
                        qualityConditions += "<option selected value='" + i + "'>Quality Condition " + i + "</option>";
                    } else {
                        qualityConditions += "<option value='" + i + "'>Quality Condition " + i + "</option>";
                    }
                }
            } else {
                loandTableConditionElements(0);
            }
            $("#selectQualityConditions").html(qualityConditions);
            // Libero espacio en memoria
            groupURLHitsConfiguration = [];
            $('#alertForRTAConfig').hide();
        } else if (object.type == "RTAAdapterCategoryHitsConfiguration") {
            // Metodo de dataTable para vaciar la tabla
            tableInterestConversionList.clear().draw(false);
            // Fin del metodo de dataTable para vaciar la tabla
            $("#RTAAdapterCategoryConfiguration").hide();
            $("#RTAAdapterTopperConfiguration").hide();
            $("#RTAAdapterURLCategoryConsumptionConfiguration").hide();
            $("#RTAAdapterURLHitsConfiguration").hide();
            $("#RTAAdapterHeavyConsumptionConfiguration").hide();
            $("#RTAAdapterFlavorUpdaterConfiguration").hide();
            $("#RTAAdapterCategoryHitsConfiguration").show();
            if (typeof object.calculationGranularity !== "undefined") {
                $("#calculationGranularity").val(object.calculationGranularity);
            }
            if (typeof object.allowedCategories !== "undefined") {
                for (var i = 0; i < object.allowedCategories.length; i++) {
                    allowedCategories += "<option value='" + object.allowedCategories[i] + "' >" + object.allowedCategories[i] + "</option>";
                }
            }
            $("#allowedCategories").html(allowedCategories);

            if (typeof object.interestConversionList !== "undefined") {
                for (var i = 0; i < object.interestConversionList.length; i++) {
                    var calification = object.interestConversionList[i].calification;
                    var value = object.interestConversionList[i].value;
                    // Metodo de dataTable para añadir rows
                    tableInterestConversionList.row.add([
                        calification,
                        value
                    ]).draw(false);
                    // Fin del metodo de dataTable para añadir rows
                }
            }
            qualityConditionsVec = [];
            groupURLHitsConfiguration = [];
            $('#alertForRTAConfig').hide();
        } else if (typeof object.type == "RTAAdapterTopperConfiguration") {
            $("#RTAAdapterCategoryHitsConfiguration").hide();
            $("#RTAAdapterCategoryConfiguration").hide();
            $("#RTAAdapterURLCategoryConsumptionConfiguration").hide();
            $("#RTAAdapterURLHitsConfiguration").hide();
            $("#RTAAdapterHeavyConsumptionConfiguration").hide();
            $("#RTAAdapterFlavorUpdaterConfiguration").hide();
            $("#RTAAdapterTopperConfiguration").show();
            if (typeof object.processUniqSubs !== "undefined") {
                if (object.processUniqSubs == true) {
                    $("#processUniqSubs").prop('checked', true);
                }
                if (object.processUniqSubs == false) {
                    $("#processUniqSubs").prop('checked', false);
                }
            } else {
                $("#processUniqSubs").prop('checked', false);
            }
            if (typeof object.minAllowedHits !== "undefined") {
                $("#minAllowedHits").val(object.minAllowedHits);
            }
            qualityConditionsVec = [];
            groupURLHitsConfiguration = [];
            $('#alertForRTAConfig').hide();
        } else if (object.type == "RTAAdapterHeavyConsumptionConfiguration") {
            // Metodo de dataTable para vaciar la tabla
            tableCounterConsumptionConfiguration.clear().draw(false);
            // Fin del metodo de dataTable para vaciar la tabla
            $("#RTAAdapterCategoryConfiguration").hide();
            $("#RTAAdapterTopperConfiguration").hide();
            $("#RTAAdapterCategoryHitsConfiguration").hide();
            $("#RTAAdapterURLCategoryConsumptionConfiguration").hide();
            $("#RTAAdapterURLHitsConfiguration").hide();
            $("#RTAAdapterFlavorUpdaterConfiguration").hide();
            $("#RTAAdapterHeavyConsumptionConfiguration").show();
            var allowedPackageIds = "";
            if (typeof object.allowedPackageIds !== "undefined") {
                for (var i = 0; i < object.allowedPackageIds.length; i++) {
                    allowedPackageIds += "<option value='" + object.allowedPackageIds[i] + "' >" + object.allowedPackageIds[i] + "</option>";
                }
            }
            $("#allowedPackageIdsConsumptionConfiguration").html(allowedPackageIds);

            var objectCounter;
            for (var keyCounter in object.counters) {
                objectCounter = object.counters[keyCounter];
                if (typeof object.counters !== "undefined") {
                    var counterId = objectCounter.counterId;
                    var serviceIds = objectCounter.serviceIds.toString();
                    var allServices = objectCounter.allServices;
                    var filterByDownstream = objectCounter.filterByDownstream;
                    var minDownstreamAllowed = objectCounter.minDownstreamAllowed;
                    var filterByUpstream = objectCounter.filterByUpstream;
                    var minUpstreamAllowed = objectCounter.minUpstreamAllowed;

                    // Metodo de dataTable para añadir rows
                    tableCounterConsumptionConfiguration.row.add([
                        keyCounter,
                        counterId,
                        serviceIds,
                        allServices,
                        filterByDownstream,
                        minDownstreamAllowed,
                        filterByUpstream,
                        minUpstreamAllowed
                    ]).draw(false);
                }
            }
            // Libero espacio en memoria
            groupURLHitsConfiguration = [];
            // Se guardan temporalmente para que se puedan consultar sin realizar la llamada a la base
            qualityConditionsVec = [];
            $('#alertForRTAConfig').hide();
        } else if (object.type == "RTAAdapterURLCategoryConsumptionConfiguration") {
            $("#RTAAdapterCategoryConfiguration").hide();
            $("#RTAAdapterTopperConfiguration").hide();
            $("#RTAAdapterCategoryHitsConfiguration").hide();
            $("#RTAAdapterURLHitsConfiguration").hide();
            $("#RTAAdapterHeavyConsumptionConfiguration").hide();
            $("#RTAAdapterFlavorUpdaterConfiguration").hide();
            $("#RTAAdapterURLCategoryConsumptionConfiguration").show();

            // Metodo de dataTable para vaciar la tabla
            tableCounters.clear().draw(false);
            // Fin del metodo de dataTable para vaciar la tabla
            var allowedPackageIds = "";
            if (typeof object.allowedPackageIds !== "undefined") {
                for (var i = 0; i < object.allowedPackageIds.length; i++) {
                    allowedPackageIds += "<option value='" + object.allowedPackageIds[i] + "' >" + object.allowedPackageIds[i] + "</option>";
                }
            }
            $("#allowedPackageIds").html(allowedPackageIds);

            var objectCounter = [];
            for (var keyCounter in object.counters) {
                objectCounter = object.counters[keyCounter];
                if (typeof object.counters !== "undefined") {
                    var counterId = objectCounter.counterId;
                    var categoryIds = objectCounter.categoryIds.toString();
                    var allCategories = objectCounter.allCategories;
                    var filterByUsage = objectCounter.filterByUsage;
                    var minUsageAllowed = objectCounter.minUsageAllowed;
                    // Metodo de dataTable para añadir rows
                    tableCounters.row.add([
                        keyCounter,
                        counterId,
                        categoryIds,
                        allCategories,
                        filterByUsage,
                        minUsageAllowed
                    ]).draw(false);
                }
            }
            qualityConditionsVec = [];
            groupURLHitsConfiguration = [];
            $('#alertForRTAConfig').hide();
        } else if (object.type == "RTAAdapterURLHitsConfiguration") {
            $("#RTAAdapterCategoryConfiguration").hide();
            $("#RTAAdapterTopperConfiguration").hide();
            $("#RTAAdapterCategoryHitsConfiguration").hide();
            $("#RTAAdapterURLCategoryConsumptionConfiguration").hide();
            $("#RTAAdapterHeavyConsumptionConfiguration").hide();
            $("#RTAAdapterFlavorUpdaterConfiguration").hide();
            $("#RTAAdapterURLHitsConfiguration").show();

            if (typeof object.services !== "undefined") {
                for (var i = 0; i < object.services.length; i++) {
                    services += "<option value='" + object.services[i] + "' >" + object.services[i] + "</option>";
                }
            }
            $("#selectServices").html(services);
            var optionsURLHits = "";
            // Se guardan temporalmente para que se puedan consultar sin realizar la llamada a la base
            groupURLHitsConfiguration = [];
            if (typeof object.groupURLs !== "undefined") {
                groupURLHitsConfiguration = object.groupURLs;
                for (var i = 0; i < object.groupURLs.length; i++) {
                    if (i === 0) {
                        loandGroupURLHitsConfiguration(0);
                        optionsURLHits += "<option selected value='" + i + "'>" + object.groupURLs[i].name + "</option>";
                    } else {
                        optionsURLHits += "<option value='" + i + "'>" + object.groupURLs[i].name + "</option>";
                    }
                }
            } else {
                loandGroupURLHitsConfiguration(0);
            }
            $("#selectGroupURLHitsConfiguration").html(optionsURLHits);
            // Libero espacio en memoria 
            qualityConditionsVec = [];
            $('#alertForRTAConfig').hide();
        } else if (object.type == "RTAAdapterFlavorUpdaterConfiguration") {
            $("#RTAAdapterCategoryConfiguration").hide();
            $("#RTAAdapterTopperConfiguration").hide();
            $("#RTAAdapterCategoryHitsConfiguration").hide();
            $("#RTAAdapterURLCategoryConsumptionConfiguration").hide();
            $("#RTAAdapterHeavyConsumptionConfiguration").hide();
            $("#RTAAdapterURLHitsConfiguration").hide();
            $("#RTAAdapterFlavorUpdaterConfiguration").show();

            if (typeof object.sepIpAddress !== "undefined") {
                $("#sepIpAddress").val(object.sepIpAddress);
            } else {
                $("#sepIpAddress").val("");
            }
            if (typeof object.sepPort !== "undefined") {
                $("#sepPort").val(object.sepPort);
            } else {
                $("#sepPort").val("");
            }
            if (typeof object.defaultFlavorId !== "undefined") {
                $("#defaultFlavorId").val(object.defaultFlavorId);
            } else {
                $("#defaultFlavorId").val("");
            }
            if (typeof object.newFlavorsCountToApplyInSep !== "undefined") {
                $("#newFlavorsCountToApplyInSep").val(object.newFlavorsCountToApplyInSep);
            } else {
                $("#newFlavorsCountToApplyInSep").val("");
            }
            // Libero espacio en memoria 
            qualityConditionsVec = [];
            groupURLHitsConfiguration = [];
            $('#alertForRTAConfig').hide();
        }
    }
}

var selectedGroupURLHits;
// Se cargan todos los datos del objeto: GroupURLHitsConfiguration.
function loandGroupURLHitsConfiguration(l) {
    // Metodo de dataTable para vaciar la tabla    
    tableURLHits.clear().draw(false);
    // Fin del metodo de dataTable para vaciar la tabla

    try {
        if (typeof groupURLHitsConfiguration[l].id !== "undefined") {
            $('#groupURLsId').val(groupURLHitsConfiguration[l].id);
        } else {
            $('#groupURLsId').val("");
        }
        if (typeof groupURLHitsConfiguration[l].name !== "undefined") {
            $('#groupURLsName').val(groupURLHitsConfiguration[l].name);
        } else {
            $('#groupURLsName').val("");
        }
        if (typeof groupURLHitsConfiguration[l].categoryParentId !== "undefined") {
            $('#groupURLsCategoryId').val(groupURLHitsConfiguration[l].categoryParentId);
        } else {
            $('#groupURLsCategoryId').val("");
        }
        var urls = groupURLHitsConfiguration[l].urls;
        if (typeof urls !== "undefined") {
            for (var i = 0; i < urls.length; i++) {
                // Metodo de dataTable para añadir rows
                tableURLHits.row.add([
                    urls[i].source,
                    urls[i].serverIP,
                    urls[i].serverPort,
                    urls[i].accessString,
                    urls[i].revolveAccessString,
                    urls[i].infoString,
                    urls[i].clientIP,
                    urls[i].clientPort
                ]).draw(false);
                // Fin del metodo de dataTable para añadir rows
            }
        }
    } catch (err) {
        $('#groupURLsId').val("");
        $('#groupURLsName').val("");
        $('#groupURLsCategoryId').val("");
    }
    selectedGroupURLHits = l;
}

//Inicio de ABM QualityConditions: manejo de la variable global "groupURLHitsConfiguration" 
function selectGroupURLHitsConfiguration(select, idButtonDelete) {
    var cantSelect = 0;
    var selectGroupURLHit = $("#selectGroupURLHitsConfiguration").val();

    var l;
    if (select !== null) {
        l = select.options;
    } else {
        l = document.getElementById("selectGroupURLHitsConfiguration").options;
    }
    for (var q = 0; q < l.length; q++) {
        if (l[q].selected) {
            cantSelect++;
        }
    }
    if (cantSelect === 0) {
        $(idButtonDelete).prop('disabled', true);
    } else {
        $(idButtonDelete).prop('disabled', false);
    }

    if (typeof selectedGroupURLHits !== "undefined") {
        var boolean = saveGroupURLHitsConfigurationGeneral(selectedGroupURLHits);
        if (boolean !== false) {
            $('#alertForRTAConfig').hide();
            loandGroupURLHitsConfiguration(selectGroupURLHit);
        } else {
            $('#alertForRTAConfig').show();
            $("#selectGroupURLHitsConfiguration").val(selectedGroupURLHits);
        }
    } else {
        loandGroupURLHitsConfiguration(selectGroupURLHit);
    }
}

// Guarda los cambios de los inputs groupURLsId groupURLsName y groupURLsCategoryId
function saveGroupURLHitsConfigurationGeneral(selected) {
    var index = parseInt(selected);
    var boolean = true;
    var groupURLsId = $.trim($("#groupURLsId").val());
    var groupURLsName = $.trim($("#groupURLsName").val());
    var groupURLsCategoryId = $.trim($("#groupURLsCategoryId").val());
    var errorInfo = "<p>";
    if (groupURLsId !== '') {
        if (!/^\d+$/.test(groupURLsId)) {
            boolean = false;
            errorInfo += "The id entered is incorrect.</br>";
            alert_red("input-table1");
        } else if (groupURLsName === '') {
            boolean = false;
            errorInfo += "The name entered is incorrect.</br>";
        } else if (!/^\d+$/.test(groupURLsCategoryId)) {
            boolean = false;
            errorInfo += "The category id entered is incorrect.</br>";
        } else {
            var groupURL = null;
            if (typeof groupURLHitsConfiguration[index] !== "undefined") {
                groupURL = groupURLHitsConfiguration[index].urls;
            }
            var newGroup;
            if (groupURL === null) {
                newGroup = {id: groupURLsId, name: groupURLsName, categoryParentId: groupURLsCategoryId, urls: []};
            } else {
                newGroup = {id: groupURLsId, name: groupURLsName, categoryParentId: groupURLsCategoryId, urls: groupURL};
            }
            groupURLHitsConfiguration.splice(index, 1, newGroup);
            $("#selectGroupURLHitsConfiguration option[value='" + index + "']").text(groupURLsName);
        }
    }
    errorInfo += "</p>";
    $('#pForRTAConfig').html(errorInfo);
    return boolean;
}
// Crea al Group
function newGroupURLHitsConfiguration() {
    var boolean = true;
    var errorInfo = "<p>";
    var groupURLsId = $.trim($("#newGroupURLsId").val());
    var groupURLsName = $.trim($("#newGroupURLsName").val()) + "";
    var groupURLsCategoryId = $.trim($("#newGroupURLsCategoryId").val());
    if (groupURLsId !== '') {
        if (!/^\d+$/.test(groupURLsId)) {
            boolean = false;
            errorInfo += "The id value entered is incorrect.</br>";
            alert_red("newGroupURLsId");
        } else if (groupURLsName === '') {
            boolean = false;
            errorInfo += "The name entered is incorrect.</br>";
            alert_red("newGroupURLsName");
        } else if (!/^\d+$/.test(groupURLsCategoryId)) {
            boolean = false;
            errorInfo += "The category id entered is incorrect.</br>";
            alert_red("newGroupURLsCategoryId");
        } else {
            $("#groupURLsId").val(groupURLsId);
            $("#groupURLsName").val(groupURLsName);
            $("#groupURLsCategoryId").val(groupURLsCategoryId);
            //Se crea el Group URL Hits
            var newGroupURLHits = {id: groupURLsId, name: groupURLsName, categoryParentId: groupURLsCategoryId, urls: []};
            groupURLHitsConfiguration.push(newGroupURLHits);
            var i = groupURLHitsConfiguration.length - 1;
            var optionQualityCondition = "<option value='" + i + "' >" + groupURLsName + "</option>";
            $("#selectGroupURLHitsConfiguration").append(optionQualityCondition);
            $("#selectGroupURLHitsConfiguration").val(i);
            selectedGroupURLHits = i;
            selectGroupURLHitsConfiguration(null, "#removeGroupsURLHits");
            clearModalGroupURL();
            $('#alertAddGroupURLHits').hide();
        }
    } else {
        boolean = false;
        errorInfo += "Enter the id.</br>";
        alert_red("newGroupURLsId");
    }
    if (boolean) {
        $('#modalAddGroupURLHits').modal('hide');
    } else {
        $('#pAddGroupURLHits').html(errorInfo);
        $('#alertAddGroupURLHits').show();
    }
}
function deleteGroupURLHitsConfigurationInterno(selected) {
    var indexDelete = parseInt(selected);
    // Elimina de a uno. La variable a ingersar, es decir row, se cuenta desde 0 en adelante.
    groupURLHitsConfiguration.splice(indexDelete, 1);
}
// Crea los URL Hit     source serverIP  serverPort  accessString  revolveAccessString  infoString  clientIP  clientPort
function newURLHits(selected, source, serverIP, serverPort, accessString, revolveAccessString, infoString, clientIP, clientPort) {
    var urls = {source: source, serverIP: serverIP, serverPort: serverPort,
        accessString: accessString, revolveAccessString: revolveAccessString,
        infoString: infoString, clientIP: clientIP, clientPort: clientPort};
    groupURLHitsConfiguration[selected].urls.push(urls);
}

function deleteURLHitInterno(row, selected) {
    var indexDelete = parseInt(selected);
    // Elimina de a uno. La variable a ingersar, es decir row, se cuenta desde 0 en adelante.
    groupURLHitsConfiguration[indexDelete].urls.splice(row, 1);
}

// Edita de a uno. La variable a ingersar, es decir row, se cuenta desde 0 en adelante. Y con que se va a reemplasar.
function editURLHitInterno(selected, row, source, serverIP, serverPort, accessString, revolveAccessString, infoString, clientIP, clientPort) {
    var index = parseInt(selected);
    var urlHit;
    urlHit = {source: source, serverIP: serverIP, serverPort: serverPort,
        accessString: accessString, revolveAccessString: revolveAccessString,
        infoString: infoString, clientIP: clientIP, clientPort: clientPort};

    // splice fuciona para El ABM de elements. Puede añadir, borrar o editar. (row: es el index de la fila. 1: es la cantidad de elementos a editar dentro del vector. urlHit: es el elemento editado)
    groupURLHitsConfiguration[index].urls.splice(row, 1, urlHit);
}
// Fin de ABM QualityConditions: manejo del Objeto global "qualityConditionsVec" 


//Inicio de ABM QualityConditions: manejo la variable global "qualityConditionsVec" 
var selectedQualityCondition;
function selectQualityConditions(select, idButtonDelete) {
    var cantSelect = 0;
    var qualitySelected = $("#selectQualityConditions").val();

    var l;
    if (select !== null) {
        l = select.options;
    } else {
        l = document.getElementById("selectQualityConditions").options;
    }
    for (var q = 0; q < l.length; q++) {
        if (l[q].selected) {
            cantSelect++;
        }
    }
    if (cantSelect === 0) {
        $(idButtonDelete).prop('disabled', true);
    } else {
        $(idButtonDelete).prop('disabled', false);
    }

    if (typeof selectedQualityCondition !== "undefined") {
        var boolean = saveCategoryId(selectedQualityCondition);
        if (boolean !== false) {
            $("#labelCategoryId").hide();
            loandTableConditionElements(qualitySelected);
        } else {
            $("#labelCategoryId").show();
            $("#selectQualityConditions").val(selectedQualityCondition);
        }
    } else {
        loandTableConditionElements(qualitySelected);
    }
}
// Carga la tabla que tiene los Condition Elements
function loandTableConditionElements(l) {
    var index = parseInt(l);
    selectedQualityCondition = index;
    // Metodo de dataTable para vaciar la tabla
    tableConditionElement.clear().draw(false);
    // Fin del metodo de dataTable para vaciar la tabla
    try {
        $("#categoryId").val(qualityConditionsVec[index].categoryId);
        if (typeof qualityConditionsVec[index].conditionElements !== "undefined") {
            var elements = qualityConditionsVec[index].conditionElements;
            for (var i = 0; i < elements.length; i++) {
                tableConditionElement.row.add([
                    elements[i].categoryIds.toString(),
                    elements[i].operator,
                    elements[i].value
                ]).draw(false);
            }
        }
    } catch (err) {
        $("#categoryId").val("");
    }

}
// Guarda los cambios del input categoryId
function saveCategoryId(selected) {
    var index = parseInt(selected);
    var boolean = true;
    var categoryId = $.trim($("#categoryId").val());
    if (categoryId !== '') {
        if (!/^\d+$/.test(categoryId)) {
            boolean = false;
        } else {
            var conditionElements = null;
            if (typeof qualityConditionsVec[index] !== "undefined") {
                conditionElements = qualityConditionsVec[index].conditionElements;
            }
            var newQualityConditions;
            if (conditionElements === null) {
                newQualityConditions = {categoryId: categoryId, conditionElements: []};
            } else {
                newQualityConditions = {categoryId: categoryId, conditionElements: conditionElements};
            }
            qualityConditionsVec.splice(index, 1, newQualityConditions);
        }
    }
    return boolean;
}

function newQualityCondition() {
    var newQualityConditions = {categoryId: 0, conditionElements: []};
    qualityConditionsVec.push(newQualityConditions);
    var i = qualityConditionsVec.length - 1;
    var optionQualityCondition = "<option value='" + i + "' >Quality Condition " + i + "</option>";
    $("#selectQualityConditions").append(optionQualityCondition);
    $("#selectQualityConditions").val(i);
    selectedQualityCondition = i;
    $("#categoryId").val("0");
    selectQualityConditions(null, "#removeQualityCondition");
}
function deleteQualityConditionInterno(selected) {
    var index = parseInt(selected);
    // Elimina de a uno. La variable a ingersar, es decir row, se cuenta desde 0 en adelante.
    qualityConditionsVec.splice(index, 1);
}

function newConditionElement(selected, categoryIdsVec, operator, value) {
    var index = parseInt(selected);
    var conditionElementet;
    if (categoryIdsVec === null) {
        conditionElementet = {categoryIds: [], operator: operator, value: value};
    } else {
        conditionElementet = {categoryIds: categoryIdsVec, operator: operator, value: value};
    }

    qualityConditionsVec[index].conditionElements.push(conditionElementet);
}

function deleteConditionElementInterno(row, selected) {
    // Elimina de a uno. La variable a ingersar, es decir row, se cuenta desde 0 en adelante.
    qualityConditionsVec[selected].conditionElements.splice(row, 1);
}
// Edita de a uno. La variable a ingersar, es decir row, se cuenta desde 0 en adelante. Y con que se va a reemplasar.
function editConditionElementInterno(selected, row, categoryIds, operator, value) {
    var index = parseInt(selected);
    var conditionElementet;
    if (categoryIds === null) {
        conditionElementet = {categoryIds: [], operator: operator, value: value};
    } else {
        conditionElementet = {categoryIds: categoryIds, operator: operator, value: value};
    }
    // splice fuciona para El ABM de elements. Puede añadir, borrar o editar. (row: es el index de la fila. 1: es la cantidad de elementos a editar dentro del vector. conditionElementet: es el elemento editado)
    qualityConditionsVec[index].conditionElements.splice(row, 1, conditionElementet);
}
// Fin de ABM QualityConditions: manejo del Objeto global "qualityConditionsVec" 

/////////////////////////////// Metodos Post /////////////////////////////////
function saveIPDRSettings() {
    var urlPrefix = $("#urlAPIPrefix").val();
    var ipIPDR = $.trim($("#ipIPDR").val());
    var portIPDR = $.trim($("#portIPDR").val());
    if (validatorVariablesIPDR(ipIPDR, portIPDR)) {
        var enableAgregatorIPDR = $("#enableAgregatorIPDR").is(':checked') + "";
        //utilizando el api de DataTables. Se le solicita todos los datos para formar un vector.
        var tableCMTS = cmtss
                .rows()
                .data();
        var vecCMTS = [];
        for (var x = 0; x < tableCMTS.length; x++) {
            vecCMTS.push(tableCMTS[x][0]);
            vecCMTS.push(tableCMTS[x][1]);
            vecCMTS.push(tableCMTS[x][2]);
            vecCMTS.push(tableCMTS[x][3]);
        }
        var vecParams = {};
        vecParams['ipIPDR'] = ipIPDR;
        vecParams['portIPDR'] = portIPDR;
        vecParams['enableAgregatorIPDR'] = enableAgregatorIPDR;
        vecParams['cmtss'] = vecCMTS;
        $.ajax({data: vecParams,
            type: "post",
            dataType: "json",
            url: urlPrefix + "/saveIPDRSettings",
            success: function (data) {
                if (data.errorCode !== 1) {
                    hideEdit(1, 'FormIPDRSettings', '#action-IPDRSettings', '#CMTSActions', '', '', '');
                } else {
                    $('#pIPDR').html("<p>Internal server error.</p>");
                    $('#alertIPDR').show();
                }
            },
            error: function (response) {
                window.location = window.location.href;
            }
        });
    } else {
        $('#alertIPDR').show();
    }
}

function saveCSVManager() {
    var urlPrefix = $("#urlAPIPrefix").val();
    //utilizando el api de DataTables. Se le solicita todos los datos para formar un vector.
    var tableCSV = CSVTable
            .rows()
            .data();
    var vecCSV = [];
    for (var x = 0; x < tableCSV.length; x++) {
        vecCSV.push(tableCSV[x][0]);
        vecCSV.push(tableCSV[x][1]);
        vecCSV.push(tableCSV[x][2]);
        vecCSV.push(tableCSV[x][3]);
        vecCSV.push(tableCSV[x][4]);
        vecCSV.push(tableCSV[x][5]);
    }

    var vecParams = {};
    vecParams['CSVManager'] = vecCSV;
    $.ajax({data: vecParams,
        type: "post",
        dataType: "json",
        url: urlPrefix + "/saveCSVManager",
        success: function (data) {
            if (data.errorCode !== 1) {
                //si esta bien no hace nada
            } else {
                $('#pCSVManager').html("<p>Internal server error.</p>");
                $('#alertCSVManager').show();
            }
        },
        error: function (response) {
            window.location = window.location.href;
        }
    });
}

function saveDataRecordsConfig() {
    var urlPrefix = $("#urlAPIPrefix").val();
    var PYCRenebleAgregator = $("#PYCRenebleAgregator").is(':checked') + "";
    var AllowedTagsPYC = document.getElementById("AllowedTagsPYC");
    var vecAllowedTagsPYC = [];
    var i;
    for (i = 0; i < AllowedTagsPYC.length; i++) {
        vecAllowedTagsPYC.push(AllowedTagsPYC[i].value);
    }
    var EnableCSV = $("#EnableCSV").is(':checked') + "";
    var AllowedTagsCSV = document.getElementById("AllowedTagsCSV");
    var vecAllowedTagsCSV = [];
    var i;
    for (i = 0; i < AllowedTagsCSV.length; i++) {
        vecAllowedTagsCSV.push(AllowedTagsCSV[i].value);
    }
    var EnableDBManager = $("#EnableDBManager").is(':checked') + "";
    var AllowedTagsDBManager = document.getElementById("AllowedTagsDBManager");
    var vecAllowedTagsDBManager = [];
    var i;
    for (i = 0; i < AllowedTagsDBManager.length; i++) {
        vecAllowedTagsDBManager.push(AllowedTagsDBManager[i].value);
    }
    var enebleRTA = $("#enebleRTA").is(':checked') + "";
    var AllowedTagsRTA = document.getElementById("AllowedTagsRTA");
    var vecAllowedTagsRTA = [];
    var i;
    for (i = 0; i < AllowedTagsRTA.length; i++) {
        vecAllowedTagsRTA.push(AllowedTagsRTA[i].value);
    }
    var vecParams = {};
    vecParams['enableAggregator'] = PYCRenebleAgregator;
    vecParams['AllowedTagsPYC'] = vecAllowedTagsPYC;
    vecParams['EnableCSV'] = EnableCSV;
    vecParams['AllowedTagsCSV'] = vecAllowedTagsCSV;
    vecParams['EnableDBManager'] = EnableDBManager;
    vecParams['AllowedTagsDBManager'] = vecAllowedTagsDBManager;
    vecParams['enebleRTA'] = enebleRTA;
    vecParams['AllowedTagsRTA'] = vecAllowedTagsRTA;
    $.ajax({data: vecParams,
        type: "post",
        dataType: "json",
        url: urlPrefix + "/saveDataRecordsConfig",
        success: function (data) {
            if (data.errorCode !== 1) {
                hideEdit(1, 'FormCSVManager', '#action-CSV', '#PYCActions', '#CSVActions', '#DBActions', '#RTAactions');
            } else {
                $('#pCSV').html("<p>Internal server error.</p>");
                $('#alertCSV').show();
            }
        },
        error: function (response) {
            window.location = window.location.href;
        }
    });
}

function saveDBManager() {
    var urlPrefix = $("#urlAPIPrefix").val();
    // variables de Mysql
    var url = $.trim($("#url").val());
    var user = $.trim($("#user").val());
    var password = $.trim($("#password").val());
    // variables de Mongo Interest
    var DBName = $.trim($("#DBName").val());
    var tableName = $.trim($("#tableName").val());
    var SuscriberIdCol = $.trim($("#SuscriberIdCol").val());
    var InterestIdCol = $.trim($("#InterestIdCol").val());
    if (validatorVariablesDBManager(url, user, password, DBName, tableName, SuscriberIdCol, InterestIdCol)) {
        var vecParams = {};
        vecParams['url'] = url;
        vecParams['user'] = user;
        vecParams['password'] = password;
        vecParams['DBName'] = DBName;
        vecParams['tableName'] = tableName;
        vecParams['SuscriberIdCol'] = SuscriberIdCol;
        vecParams['InterestIdCol'] = InterestIdCol;
        $.ajax({data: vecParams,
            type: "post",
            dataType: "json",
            url: urlPrefix + "/saveDBManager",
            success: function (data) {
                if (data.errorCode !== 1) {
                    hideEdit(1, 'FormDBManager', '#DBManager-Actions', '', '', '', '');
                } else {
                    $('#pDBManager').html("<p>Internal server error.</p>");
                    $('#alertDBManager').show();
                }
            },
            error: function (response) {
                window.location = window.location.href;
            }
        });
    } else {
        $('#alertDBManager').show();
    }
}

function saveOthers() {
    var urlPrefix = $("#urlAPIPrefix").val();
    // variables de RMI Config
    var RMIIP = $.trim($("#RMIIP").val());
    var RMIPort = $.trim($("#RMIPort").val());
    var RMIService = $.trim($("#RMIService").val());
    // variables de Rest Config
    var RestIP = $.trim($("#RestIP").val());
    var RestPort = $.trim($("#RestPort").val());
    // variables de Sep Config
    var SepIP = $.trim($("#SepIP").val());
    var SepPort = $.trim($("#SepPort").val());
    // variables de Notification Server Config
    var SERIP = $.trim($("#SERIP").val());
    var SERPort = $.trim($("#SERPort").val());
    // variables de CSM Config
    var CSMIP = $.trim($("#CSMIP").val());
    var CSMPort = $.trim($("#CSMPort").val());
    if (validatorVariablesOthers(RMIIP, RMIPort, RMIService, RestIP, RestPort, SepIP, SepPort, SERIP, SERPort, CSMIP, CSMPort)) {
        var vecParams = {};
        vecParams['RMIIP'] = RMIIP;
        vecParams['RMIPort'] = RMIPort;
        vecParams['RMIService'] = RMIService;
        vecParams['RestIP'] = RestIP;
        vecParams['RestPort'] = RestPort;
        vecParams['SepIP'] = SepIP;
        vecParams['SepPort'] = SepPort;
        vecParams['SERIP'] = SERIP;
        vecParams['SERPort'] = SERPort;
        vecParams['CSMIP'] = CSMIP;
        vecParams['CSMPort'] = CSMPort;
        $.ajax({data: vecParams,
            type: "post",
            dataType: "json",
            url: urlPrefix + "/saveOthers",
            success: function (data) {
                if (data.errorCode !== 1) {
                    hideEdit(1, 'FormOthers', '#Others-Actions', '', '', '', '');
                } else {
                    $('#pOthers').html("<p>Internal server error.</p>");
                    $('#alertOthers').show();
                }
            },
            error: function (response) {
                window.location = window.location.href;
            }
        });
    } else {
        $('#alertOthers').show();
    }
}

function saveRDRSettings() {
    var urlPrefix = $("#urlAPIPrefix").val();
    var RDRPort = $.trim($("#RDRPort").val());
    var queueDepth = $.trim($("#queueDepth").val());
    var timeout = $.trim($("#timeout").val());
    if (validatorVariablesRDR(RDRPort, queueDepth, timeout)) {
        var allowedTypes = document.getElementById("allowedTypes");
        var vecallowedTypes = [];
        var i;
        for (i = 0; i < allowedTypes.length; i++) {
            vecallowedTypes.push(allowedTypes[i].value);
        }
        var enableAggregator = $("#enableAggregator").is(':checked') + "";
        var defaultScabbVersion = $("#defaultScabbVersion").val();
        //Trae todo el objeto usando el api de Data Table. Coloca en pares las variables ip y version.
        var ScabbVersion = scabbVersions
                .rows()
                .data();
        var vecScabbV = [];
        for (var x = 0; x < ScabbVersion.length; x++) {
            vecScabbV.push(ScabbVersion[x][0]);
            vecScabbV.push(ScabbVersion[x][1]);
        }
        var vecParams = {};
        vecParams['RDRPort'] = RDRPort;
        vecParams['queueDepth'] = queueDepth;
        vecParams['timeout'] = timeout;
        vecParams['allowedTypes'] = vecallowedTypes;
        vecParams['enableAggregator'] = enableAggregator;
        vecParams['defaultScabbVersion'] = defaultScabbVersion;
        vecParams['ScabbVersion'] = vecScabbV;

        $.ajax({data: vecParams,
            type: "post",
            dataType: "json",
            url: urlPrefix + "/saveRDRSetting",
            success: function (data) {
                if (data.errorCode !== 1) {
                    hideEdit(1, 'FormRDRSettings', '#actions-RDR', '#AllowedTypeActions', '#SCABBVersion', '', '');
                } else {
                    $('#pRDR').html("<p>Internal server error.</p>");
                    $('#alertRDR').show();
                }
            },
            error: function (response) {
                window.location = window.location.href;
            }
        });
    } else {
        $('#alertRDR').show();
    }
}

/////////////////////////////// Funcion de edicion de tablas /////////////////////////////////
// Añade la row según el tipo de tabla a la que se refiera el modal.
function newRowInTable() {
    var idTAble = $('#isEdit').val();
    if (idTAble == "true") {
        editRowInTable();
    } else {
        var idTAble = $('#typeModalForTable').val();
        var array = $('.identif input').map(function () {
            return $(this).val();
        }).get();
        switch (idTAble) {
            case "scabbVersions":
                var var0 = $.trim(array[0]);
                if (validatorVariablesTable(var0, 0, 2)) {
                    var select = $("#input-table1").val();
                    scabbVersions.row.add([
                        var0,
                        select
                    ]).draw(false);
                    $('#alertForTable').hide();
                    $('#modalForTable').modal('hide');
                } else {
                    $('#alertForTable').show();
                }
                break;

            case "cmtss":
                var select2 = $("#input-table2").val();
                array[3] = $("#input-table3").is(':checked') + "";
                var var0 = $.trim(array[0]);
                var var1 = $.trim(array[1]);
                if (validatorVariablesTable(var0, var1, 4)) {
                    cmtss.row.add([
                        var0,
                        var1, select2,
                        array[3]
                    ]).draw(false);
                    $('#modalForTable').modal('hide');
                } else {
                    $('#alertForTable').show();
                }
                break;

            case "CSVTable":
                var select0 = $("#input-table0").val();
                array[1] = $("#input-table2").is(':checked') + "";
                var select3 = $("#input-table3").val();
                var select4 = $("#input-table4").val();
                var var0 = $.trim(array[0]);
                var var2 = $.trim(array[2]);
                if (validatorVariablesTable(var0, var2, 6)) {
                    CSVTable.row.add([select0,
                        var0,
                        array[1],
                        select3,
                        select4,
                        var2
                    ]).draw(false);
                    saveCSVManager();
                    $('#modalForTable').modal('hide');
                } else {
                    $('#alertForTable').show();
                }
                break;

            case "tableInterestConversionList":
                var var0 = $.trim(array[0]);
                var var1 = $.trim(array[1]);
                if (validatorVariablesTableInterestConversionList(var0, var1)) {
                    tableInterestConversionList.row.add([
                        var0,
                        var1
                    ]).draw(false);
                    $('#modalForTable').modal('hide');
                } else {
                    $('#alertForTable').show();
                }
                break;

            case "tableCategories":
                var var0 = $.trim(array[0]);
                var var1 = $.trim(array[1]);
                var var2 = $.trim(array[2]);
                var var3 = $.trim(array[3]);
                var var4 = $.trim(array[4]);
                if (validatorVariablesTableCategories(var0, var1, var2, var3, var4)) {
                    tableCategories.row.add([
                        var0,
                        var1,
                        var2,
                        var3,
                        var4
                    ]).draw(false);
                    $('#modalForTable').modal('hide');
                } else {
                    $('#alertForTable').show();
                }
                break;

            case "tableConditionElement":
                var select0 = $.map($('#input-table0 option'), function (e) {
                    return e.value;
                });
                select0.join(',');
                var var1 = $.trim(array[0]);
                var var2 = $.trim(array[1]);
                var selected = $("#selectQualityConditions").val();
                if (validatorVariablesTableConditionElement(var1, var2)) {
                    tableConditionElement.row.add([
                        select0.toString(),
                        var1,
                        var2
                    ]).draw(false);
                    newConditionElement(selected, select0, var1, var2);
                    $('#modalForTable').modal('hide');
                } else {
                    $('#alertForTable').show();
                }
                break;

            case "tableCounterConsumptionConfiguration":
                var select0 = $.map($('#input-table1 option'), function (e) {
                    return e.value;
                });
                select0.join(',');
                var var0 = $.trim(array[0]);
                var var2 = $("#input-table2").is(':checked') + "";
                var var3 = $("#input-table3").is(':checked') + "";
                var var4 = $.trim(array[3]);
                var var5 = $("#input-table5").is(':checked') + "";
                var var6 = $.trim(array[5]);
                var idRow = tableCounterConsumptionConfiguration.rows().data().length + 1;
                if (validatorVariablesTableCounterConsumptionConfiguration(var0, var4, var6)) {
                    tableCounterConsumptionConfiguration.row.add([
                        idRow,
                        var0,
                        select0.toString(),
                        var2,
                        var3,
                        var4,
                        var5,
                        var6
                    ]).draw(false);
                    $('#modalForTable').modal('hide');
                } else {
                    $('#alertForTable').show();
                }
                break;

            case "tableCounters":
                var select1 = $.map($('#input-table1 option'), function (e) {
                    return e.value;
                });
                select1.join(',');
                var var0 = $.trim(array[0]);
                var var2 = $("#input-table2").is(':checked') + "";
                var var3 = $("#input-table3").is(':checked') + "";
                var var4 = $.trim(array[3]);
                var idRow = tableCounters.rows().data().length + 1;
                if (validatorVariablesTableCounters(var0, var4)) {
                    tableCounters.row.add([
                        idRow,
                        var0,
                        select1.toString(),
                        var2,
                        var3,
                        var4
                    ]).draw(false);
                    $('#modalForTable').modal('hide');
                } else {
                    $('#alertForTable').show();
                }
                break;

            case "tableURLHits":
                var selectGroupURLHit = $("#selectGroupURLHitsConfiguration").val();
                var source = $("#input-table0").val();
                var serverIP = $.trim(array[0]);
                var serverPort = $.trim(array[1]);
                var accessString = $.trim(array[2]);
                var revolveAccessString = $("#input-table4").is(':checked') + "";
                var infoString = $.trim(array[4]);
                var clientIP = $.trim(array[5]);
                var clientPort = $.trim(array[6]);
                if (validatorVariablesTableURLHits(serverIP, serverPort, accessString, clientIP, clientPort)) {
                    tableURLHits.row.add([
                        source,
                        serverIP,
                        serverPort,
                        accessString,
                        revolveAccessString,
                        infoString,
                        clientIP,
                        clientPort
                    ]).draw(false);
                    $('#modalForTable').modal('hide');
                    newURLHits(selectGroupURLHit, source, serverIP, serverPort, accessString, revolveAccessString, infoString, clientIP, clientPort);
                } else {
                    $('#alertForTable').show();
                }
                break;

        }
    }
}

//Borra filas segun la tabla     
function deleteRowInTable() {
    var idTAble = $('#typeModalForTableDelete').val();
    switch (idTAble) {
        case "scabbVersions":
            scabbVersions.row('.selected').remove().draw(false);
            break;
        case "cmtss":
            cmtss.row('.selected').remove().draw(false);
            break;
        case "CSVTable":
            CSVTable.row('.selected').remove().draw(false);
            saveCSVManager();
            break;
        case "tableInterestConversionList":
            tableInterestConversionList.row('.selected').remove().draw(false);
            break;
        case "tableCategories":
            tableCategories.row('.selected').remove().draw(false);
            break;
        case "tableConditionElement":
            var row = tableConditionElement.row(this).index();
            var selected = document.getElementById("selectQualityConditions").value;
            deleteConditionElementInterno(row, selected);
            tableConditionElement.row('.selected').remove().draw(false);
            break;
        case "tableCounterConsumptionConfiguration":
            tableCounterConsumptionConfiguration.row('.selected').remove().draw(false);
            break;
        case "tableCounters":
            tableCounters.row('.selected').remove().draw(false);
            break;
        case "tableURLHits":
            var selectGroupURLHit = $("#selectGroupURLHitsConfiguration").val();
            var row = tableURLHits.row(this).index();
            tableURLHits.row('.selected').remove().draw(false);
            deleteURLHitInterno(row, selectGroupURLHit);
            break;
    }
    $('#modalForTableDelete').modal('hide');
}

//Edita filas segun la tabla   
function editRowInTable() {
    var idTAble = $('#typeModalForTable').val();
    $('#typeModalForTableDelete').val(idTAble);

    var array = $('.identif input').map(function () {
        return $(this).val();
    }).get();
    switch (idTAble) {
        case "scabbVersions":
            deleteRowInTable();
            var var0 = $.trim(array[0]);
            if (validatorVariablesTable(var0, 0, 2)) {
                var select = $("#input-table1").val();
                scabbVersions.row.add([
                    var0,
                    select
                ]).draw(false);
                $('#alertForTable').hide();
                $('#modalForTable').modal('hide');
            } else {
                $('#alertForTable').show();
            }
            break;
        case "cmtss":
            deleteRowInTable();
            var select2 = $("#input-table2").val();
            array[3] = $("#input-table3").is(':checked') + "";
            var var0 = $.trim(array[0]);
            var var1 = $.trim(array[1]);
            if (validatorVariablesTable(var0, var1, 4)) {
                cmtss.row.add([
                    var0,
                    var1, select2,
                    array[3]
                ]).draw(false);
                $('#modalForTable').modal('hide');
            } else {
                $('#alertForTable').show();
            }
            break;
        case "CSVTable":
            deleteRowInTable();
            var select0 = $("#input-table0").val();
            array[1] = $("#input-table2").is(':checked') + "";
            var select3 = $("#input-table3").val();
            var select4 = $("#input-table4").val();
            var var0 = $.trim(array[0]);
            var var2 = $.trim(array[2]);
            if (validatorVariablesTable(var0, var2, 6)) {
                CSVTable.row.add([select0,
                    var0,
                    array[1],
                    select3,
                    select4,
                    var2
                ]).draw(false);
                saveCSVManager();
                $('#modalForTable').modal('hide');
            } else {
                $('#alertForTable').show();
            }
            break;
        case "tableInterestConversionList":
            deleteRowInTable();
            var var0 = $.trim(array[0]);
            var var1 = $.trim(array[1]);
            if (validatorVariablesTableInterestConversionList(var0, var1)) {
                tableInterestConversionList.row.add([
                    var0,
                    var1
                ]).draw(false);
                $('#modalForTable').modal('hide');
            } else {
                $('#alertForTable').show();
            }
            break;
        case "tableCategories":
            deleteRowInTable();
            var var0 = $.trim(array[0]);
            var var1 = $.trim(array[1]);
            var var2 = $.trim(array[2]);
            var var3 = $.trim(array[3]);
            var var4 = $.trim(array[4]);
            if (validatorVariablesTableCategories(var0, var1, var2, var3, var4)) {
                tableCategories.row.add([
                    var0,
                    var1,
                    var2,
                    var3,
                    var4
                ]).draw(false);
                $('#modalForTable').modal('hide');
            } else {
                $('#alertForTable').show();
            }
            break;
        case "tableConditionElement":
            var select0 = $.map($('#input-table0 option'), function (e) {
                return e.value;
            });
            select0.join(',');
            var var1 = $.trim(array[0]);
            var var2 = $.trim(array[1]);
            var selected = document.getElementById("selectQualityConditions").value;
            var row = tableConditionElement.row('.selected').index();
            if (validatorVariablesTableConditionElement(var1, var2)) {
                editConditionElementInterno(selected, row, select0, var1, var2);
                tableConditionElement.row.add([
                    select0.toString(),
                    var1,
                    var2
                ]).draw(false);
                tableConditionElement.row('.selected').remove().draw(false);
                $('#modalForTable').modal('hide');
            } else {
                $('#alertForTable').show();
            }
            break;
        case "tableCounterConsumptionConfiguration":
            deleteRowInTable();
            var select0 = $.map($('#input-table1 option'), function (e) {
                return e.value;
            });
            select0.join(',');
            var var0 = $.trim(array[0]);
            var var2 = $("#input-table2").is(':checked') + "";
            var var3 = $("#input-table3").is(':checked') + "";
            var var4 = $.trim(array[3]);
            var var5 = $("#input-table5").is(':checked') + "";
            var var6 = $.trim(array[5]);
            var idRow = tableCounterConsumptionConfiguration.rows().data().length + 1;
            if (validatorVariablesTableCounterConsumptionConfiguration(var0, var4, var6)) {
                tableCounterConsumptionConfiguration.row.add([
                    idRow,
                    var0,
                    select0.toString(),
                    var2,
                    var3,
                    var4,
                    var5,
                    var6
                ]).draw(false);
                $('#modalForTable').modal('hide');
            } else {
                $('#alertForTable').show();
            }
            break;
        case "tableCounters":
            deleteRowInTable();
            var select1 = $.map($('#input-table1 option'), function (e) {
                return e.value;
            });
            select1.join(',');
            var var0 = $.trim(array[0]);
            var var2 = $("#input-table2").is(':checked') + "";
            var var3 = $("#input-table3").is(':checked') + "";
            var var4 = $.trim(array[3]);
            var idRow = tableCounters.rows().data().length + 1;
            if (validatorVariablesTableCounters(var0, var4)) {
                tableCounters.row.add([
                    idRow,
                    var0,
                    select1.toString(),
                    var2,
                    var3,
                    var4
                ]).draw(false);
                $('#modalForTable').modal('hide');
            } else {
                $('#alertForTable').show();
            }
            break;
        case "tableURLHits":
            var selectGroupURLHit = $("#selectGroupURLHitsConfiguration").val();
            var row = tableURLHits.row('.selected').index();
            var source = $("#input-table0").val();
            var serverIP = $.trim(array[0]);
            var serverPort = $.trim(array[1]);
            var accessString = $.trim(array[2]);
            var revolveAccessString = $("#input-table4").is(':checked') + "";
            var infoString = $.trim(array[4]);
            var clientIP = $.trim(array[5]);
            var clientPort = $.trim(array[6]);
            if (validatorVariablesTableURLHits(serverIP, serverPort, accessString, clientIP, clientPort)) {
                editURLHitInterno(selectGroupURLHit, row, source, serverIP, serverPort, accessString, revolveAccessString, infoString, clientIP, clientPort);
                tableURLHits.row.add([
                    source,
                    serverIP,
                    serverPort,
                    accessString,
                    revolveAccessString,
                    infoString,
                    clientIP,
                    clientPort
                ]).draw(false);
                $('#modalForTable').modal('hide');
                tableURLHits.row('.selected').remove().draw(false);
            } else {
                $('#alertForTable').show();
            }
            break;
    }

}
// Fin de las funciones de edicion de las tablas.

//Crea un nuevo RTA
function newRTAAdapter() {
    var urlPrefix = $("#urlAPIPrefix").val();
    var selectKey = $('#RTAAdapterDetailRecordType').val();
    var selectValue = $('#RTAAdapterConfigurationType').val();
    var vecParams = {};
    vecParams['key'] = selectKey;
    vecParams['value'] = selectValue;
    $.ajax({data: vecParams,
        type: "post",
        dataType: "json",
        url: urlPrefix + "/newRTAAdapter",
        success: function (data) {
            if (data.errorCode === 0) {
                var adapter = "<option value='" + selectKey + "' >" + selectKey + "</option>";
                $('#selectRTAConfig').append(adapter);
                // El retorno de la llamada tiene que ser toda la config.
                if (typeof data.result.adapters !== "undefined") {
                    // Guardo el map en rtaConfig para futuras consultas
                    rtaConfig = data.result.adapters;
                }
            } else {
                $('#pRTAConfig').html("<p>Internal server error.</p>");
                $('#alertRTAConfig').show();
            }
        },
        error: function (response) {
            window.location = window.location.href;
        }
    });
}

function deleteRTAAdapters() {
    var urlPrefix = $("#urlAPIPrefix").val();
    $('#selectRTAConfig option:selected').remove().appendTo('#selectRTARemoves');
    $('#selectRTARemoves option').prop('selected', 'selected');
    var id = document.getElementById("selectRTARemoves").options;
    var vecId = [];
    for (var i = 0; i < id.length; i++) {
        vecId.push(id[i].value);
    }
    var vecParams = {};
    vecParams['keys'] = vecId;
    $.ajax({data: vecParams,
        type: "post",
        dataType: "json",
        url: urlPrefix + "/deleteRTAAdapters",
        success: function (data) {
            if (data.errorCode === 0) {
                $('#destino option').remove();
                var cont = document.getElementById("selectRTAConfig").options.length;
                if (cont === 0) {
                    $("#FormRTAConfig").hide();
                } else {
                    $("#FormRTAConfig").show();
                }
                // El retorno de la llamada tiene que ser toda la config.
                if (typeof data.result.adapters !== "undefined") {
                    // Guardo el map en rtaConfig para futuras consultas                     
                    rtaConfig = data.result.adapters;
                }
            } else {
                $('#pRTAConfig').html("<p>Internal server error.</p>");
                $('#alertRTAConfig').show();
            }
        },
        error: function (response) {
            window.location = window.location.href;
        }
    });
}

// Manager save RTA  
function saveRTAConfig() {
    var typeRTA = $("#typeRTA").val();
    if (typeRTA === "RTAAdapterCategoryHitsConfiguration") {
        saveRTAAdapterCategoryHitsConfiguration();
    } else if (typeRTA === "RTAAdapterTopperConfiguration") {
        saveRTAAdapterTopperConfiguration();
    } else if (typeRTA === "RTAAdapterCategoryConfiguration") {
        saveRTAAdapterCategoryConfiguration();
    } else if (typeRTA === "RTAAdapterHeavyConsumptionConfiguration") {
        saveRTAAdapterHeavyConsumptionConfiguration();
    } else if (typeRTA === "RTAAdapterURLCategoryConsumptionConfiguration") {
        saveRTAAdapterURLCategoryConsumptionConfiguration();
    } else if (typeRTA === "RTAAdapterURLHitsConfiguration") {
        saveRTAAdapterURLHitsConfiguration();
    } else if (typeRTA === "RTAAdapterFlavorUpdaterConfiguration") {
        saveRTAAdapterFlavorUpdaterConfiguration();
    } else {
        saveRTAAdapterGeneralConfig();
    }
    $("#RTAConfig-Actions").hide();
}

function saveRTAAdapterHeavyConsumptionConfiguration() {
    // General
    var keymap = $('#selectRTAConfig').val() + "";
    var urlPrefix = $("#urlAPIPrefix").val();
    var typeRTA = $("#typeRTA").val();
    var enabled = $("#EnableRTAConfig").is(':checked') + "";
    var id = $.trim($("#RTAID").val());
    var granularity = $.trim($("#granularity").val());
    var selectSources = document.getElementById("selectSources");
    var allSources = [];
    for (var i = 0; i < selectSources.length; i++) {
        allSources.push(selectSources[i].value);
    }

    // Advanced
    var allowedPackageIdsConsumptionConfiguration = [];
    var selectAllowedPackageIdsConsumptionConfiguration = document.getElementById("allowedPackageIdsConsumptionConfiguration");
    for (var i = 0; i < selectAllowedPackageIdsConsumptionConfiguration.length; i++) {
        allowedPackageIdsConsumptionConfiguration.push(selectAllowedPackageIdsConsumptionConfiguration[i].value);
    }
    //Trae todo el objeto usando el api de Data Table. Coloca en pares las variables.
    var table = tableCounterConsumptionConfiguration
            .rows()
            .data();
    var counterConsumptionConfiguration = [];
    for (var x = 0; x < table.length; x++) {
        counterConsumptionConfiguration.push(table[x][0]);
        counterConsumptionConfiguration.push(table[x][1]);
        counterConsumptionConfiguration.push(table[x][2]);
        counterConsumptionConfiguration.push(table[x][3]);
        counterConsumptionConfiguration.push(table[x][4]);
        counterConsumptionConfiguration.push(table[x][5]);
        counterConsumptionConfiguration.push(table[x][6]);
        counterConsumptionConfiguration.push(table[x][7]);
    }

    var vecParams = {};
    vecParams['key'] = keymap;
    vecParams['value'] = typeRTA;
    vecParams['enabled'] = enabled;
    vecParams['id'] = id;
    vecParams['granularity'] = granularity;
    vecParams['allSources'] = allSources;
    vecParams['allowedPackageIdsConsumptionConfiguration'] = allowedPackageIdsConsumptionConfiguration;
    vecParams['counterConsumptionConfiguration'] = counterConsumptionConfiguration;

    $.ajax({data: vecParams,
        type: "post",
        dataType: "json",
        url: urlPrefix + "/saveRTAAdapterAllConfiguration",
        success: function (data) {
            if (data.errorCode !== 1) {
                $('#alertRTAConfig').hide();
                hideEdit(1, 'FormRTAConfig', '#RTAConfig-Actions', '#RTAactionsSources', '#divCounterConsumptionConfiguration', '#divAllowedPackageIdsConsumptionConfiguration', '#divConsumptionConfiguration', '');
                // El retorno de la llamada tiene que ser toda la config.
                if (typeof data.result.adapters !== "undefined") {
                    // Guardo el map en rtaConfig para futuras consultas
                    rtaConfig = data.result.adapters;
                }
            } else {
                $('#pRTAConfig').html("<p>Internal server error.</p>");
                $('#alertRTAConfig').show();
            }
        },
        error: function (response) {
            window.location = window.location.href;
        }
    });
}

function saveRTAAdapterCategoryHitsConfiguration() {
    // General
    var keymap = $('#selectRTAConfig').val() + "";
    var urlPrefix = $("#urlAPIPrefix").val();
    var typeRTA = $("#typeRTA").val();
    var enabled = $("#EnableRTAConfig").is(':checked') + "";
    var id = $.trim($("#RTAID").val());
    var granularity = $.trim($("#granularity").val());
    var selectSources = document.getElementById("selectSources");
    var allSources = [];
    for (var i = 0; i < selectSources.length; i++) {
        allSources.push(selectSources[i].value);
    }

    // Advanced
    var calculationGranularity = $("#calculationGranularity").val();
    var allowedCategories = [];
    var selectAllowedCategories = document.getElementById("allowedCategories");
    for (var i = 0; i < selectAllowedCategories.length; i++) {
        allowedCategories.push(selectAllowedCategories[i].value);
    }
    //Trae todo el objeto usando el api de Data Table. Coloca en pares las variables.
    var table = tableInterestConversionList
            .rows()
            .data();
    var interestConversionKey = [];
    for (var x = 0; x < table.length; x++) {
        interestConversionKey.push(table[x][0]);
        interestConversionKey.push(table[x][1]);
    }

    var vecParams = {};
    vecParams['key'] = keymap;
    vecParams['value'] = typeRTA;
    vecParams['enabled'] = enabled;
    vecParams['id'] = id;
    vecParams['granularity'] = granularity;
    vecParams['allSources'] = allSources;
    vecParams['calculationGranularity'] = calculationGranularity;
    vecParams['allowedCategories'] = allowedCategories;
    vecParams['interestConversionKey'] = interestConversionKey;
    $.ajax({data: vecParams,
        type: "post",
        dataType: "json",
        url: urlPrefix + "/saveRTAAdapterAllConfiguration",
        success: function (data) {
            if (data.errorCode !== 1) {
                $('#alertRTAConfig').hide();
                hideEdit(1, 'FormRTAConfig', '#RTAConfig-Actions', '#RTAactionsSources', '#RTAactionsCategories', '#divInterestConversionKey', '', '');
                // El retorno de la llamada tiene que ser toda la config.
                if (typeof data.result.adapters !== "undefined") {
                    // Guardo el map en rtaConfig para mantener actualizadas las consultas
                    rtaConfig = data.result.adapters;
                }
            } else {
                $('#pRTAConfig').html("<p>Internal server error.</p>");
                $('#alertRTAConfig').show();
            }
        },
        error: function (response) {
            window.location = window.location.href;
        }
    });
}
function saveRTAAdapterURLHitsConfiguration() {
    // General
    var keymap = $('#selectRTAConfig').val() + "";
    var urlPrefix = $("#urlAPIPrefix").val();
    var typeRTA = $("#typeRTA").val();
    var enabled = $("#EnableRTAConfig").is(':checked') + "";
    var id = $.trim($("#RTAID").val());
    var granularity = $.trim($("#granularity").val());
    var selectSources = document.getElementById("selectSources");
    var allSources = [];
    for (var i = 0; i < selectSources.length; i++) {
        allSources.push(selectSources[i].value);
    }
    // Advanced  
    var selectServices = document.getElementById("selectServices");
    var allServices = [];
    for (var i = 0; i < selectServices.length; i++) {
        allServices.push(selectServices[i].value);
    }

    // groupsURLHits --> Es una lista de objetos.
    var groupsURLHits = JSON.stringify(groupURLHitsConfiguration);

    var vecParams = {};
    vecParams['key'] = keymap;
    vecParams['value'] = typeRTA;
    vecParams['enabled'] = enabled;
    vecParams['id'] = id;
    vecParams['granularity'] = granularity;
    vecParams['allSources'] = allSources;
    vecParams['allServices'] = allServices;
    vecParams['groupsURLHits'] = groupsURLHits;

    $.ajax({data: vecParams,
        type: "post",
        dataType: "json",
        url: urlPrefix + "/saveRTAAdapterAllConfiguration",
        success: function (data) {
            if (data.errorCode !== 1) {
                $('#alertRTAConfig').hide();
                hideEdit(1, 'FormRTAConfig', '#RTAConfig-Actions-RDR', '', '', '', '');
                // El retorno de la llamada tiene que ser toda la config.
                if (typeof data.result !== "undefined") {
                    // Guardo el map en rtaConfig para mantener actualizadas las consultas
                    if (data.result.adapters !== "undefined") {
                        rtaConfig = data.result.adapters;
                    }
                }
            } else {
                $('#pRTAConfig').html("<p>Internal server error.</p>");
                $('#alertRTAConfig').show();
            }
        },
        error: function (response) {
            window.location = window.location.href;
        }
    });
}
function saveRTAAdapterTopperConfiguration() {
    // General
    var keymap = $('#selectRTAConfig').val() + "";
    var urlPrefix = $("#urlAPIPrefix").val();
    var typeRTA = $("#typeRTA").val();
    var enabled = $("#EnableRTAConfig").is(':checked') + "";
    var id = $.trim($("#RTAID").val());
    var granularity = $.trim($("#granularity").val());
    var selectSources = document.getElementById("selectSources");
    var allSources = [];
    for (var i = 0; i < selectSources.length; i++) {
        allSources.push(selectSources[i].value);
    }
    // Advanced 
    var processUniqSubs = $("#processUniqSubs").is(':checked') + "";
    var minAllowedHits = $.trim($("#minAllowedHits").val());
    if (validatorVariablesRTAAdapterTopperConfiguration(minAllowedHits)) {
        var vecParams = {};
        vecParams['key'] = keymap;
        vecParams['value'] = typeRTA;
        vecParams['enabled'] = enabled;
        vecParams['id'] = id;
        vecParams['granularity'] = granularity;
        vecParams['allSources'] = allSources;
        vecParams['processUniqSubs'] = processUniqSubs;
        vecParams['minAllowedHits'] = minAllowedHits;
        $.ajax({data: vecParams,
            type: "post", dataType: "json",
            url: urlPrefix + "/saveRTAAdapterAllConfiguration",
            success: function (data) {
                if (data.errorCode !== 1) {
                    $('#alertForRTAConfig').hide();
                    $('#alertRTAConfig').hide();
                    hideEdit(1, 'FormRTAConfig', '#RTAConfig-Actions', '#RTAactionsSources', '', '', '', '');
                    // El retorno de la llamada tiene que ser toda la config.
                    if (typeof data.result.adapters !== "undefined") {
                        // Guardo el map en rtaConfig para mantener actualizadas las consultas
                        rtaConfig = data.result.adapters;
                    }
                } else {
                    $('#pRTAConfig').html("<p>Internal server error.</p>");
                    $('#alertRTAConfig').show();
                }
            },
            error: function (response) {
                window.location = window.location.href;
            }
        });
    } else {
        $('#alertForRTAConfig').show();
    }
}
function saveRTAAdapterCategoryConfiguration() {
    // General
    var keymap = $('#selectRTAConfig').val() + "";
    var urlPrefix = $("#urlAPIPrefix").val();
    var typeRTA = $("#typeRTA").val();
    var enabled = $("#EnableRTAConfig").is(':checked') + "";
    var id = $.trim($("#RTAID").val());
    var granularity = $.trim($("#granularity").val());
    var selectSources = document.getElementById("selectSources");
    var allSources = [];
    for (var i = 0; i < selectSources.length; i++) {
        allSources.push(selectSources[i].value);
    }
    // Advanced  tableCategories
    var defaultCategoryId = $.trim($("#defaultCategoryId").val());
    var minDownstreamAllowed = $.trim($("#minDownstreamAllowed").val());
    var minDurationAllowed = $.trim($("#minDurationAllowed").val());
    var selectServices = document.getElementById("services");
    var services = [];
    for (var i = 0; i < selectServices.length; i++) {
        services.push(selectServices[i].value);
    }

    //Trae todo el objeto usando el api de Data Table. Coloca en pares las variables.
    var table = tableCategories
            .rows()
            .data();
    var categories = [];
    for (var x = 0; x < table.length; x++) {
        categories.push(table[x][0]);
        categories.push(table[x][1]);
        categories.push(table[x][2]);
        categories.push(table[x][3]);
        categories.push(table[x][4]);
    }
    // qualityConditions --> Es una lista de objetos.
    var qualityConditions = JSON.stringify(qualityConditionsVec);
    var boolean = saveCategoryId(selectGroupURLHitsConfiguration);
    if (boolean !== false) {
        $("#labelCategoryId").hide();

        var vecParams = {};
        vecParams['key'] = keymap;
        vecParams['value'] = typeRTA;
        vecParams['enabled'] = enabled;
        vecParams['id'] = id;
        vecParams['granularity'] = granularity;
        vecParams['allSources'] = allSources;
        vecParams['defaultCategoryId'] = defaultCategoryId;
        vecParams['minDownstreamAllowed'] = minDownstreamAllowed;
        vecParams['minDurationAllowed'] = minDurationAllowed;
        vecParams['services'] = services;
        vecParams['categories'] = categories;
        vecParams['qualityConditions'] = qualityConditions;

        $.ajax({data: vecParams,
            type: "post",
            dataType: "json",
            url: urlPrefix + "/saveRTAAdapterAllConfiguration",
            success: function (data) {
                if (data.errorCode !== 1) {
                    $('#alertRTAConfig').hide();
                    hideEdit(1, 'FormRTAConfig', '#RTAConfig-Actions-RDR', '', '', '', '');
                    // El retorno de la llamada tiene que ser toda la config.
                    if (typeof data.result.adapters !== "undefined") {
                        // Guardo el map en rtaConfig para mantener actualizadas las consultas
                        rtaConfig = data.result.adapters;
                    }
                } else {
                    $('#pRTAConfig').html("<p>Internal server error.</p>");
                    $('#alertRTAConfig').show();
                }
            },
            error: function (response) {
                window.location = window.location.href;
            }
        });
    } else {
        $("#labelCategoryId").show();
        $("#selectQualityConditions").val(selectGroupURLHitsConfiguration);
    }
}

function saveRTAAdapterURLCategoryConsumptionConfiguration() {
    // General
    var keymap = $('#selectRTAConfig').val() + "";
    var urlPrefix = $("#urlAPIPrefix").val();
    var typeRTA = $("#typeRTA").val();
    var enabled = $("#EnableRTAConfig").is(':checked') + "";
    var id = $.trim($("#RTAID").val());
    var granularity = $.trim($("#granularity").val());
    var selectSources = document.getElementById("selectSources");
    var allSources = [];
    for (var i = 0; i < selectSources.length; i++) {
        allSources.push(selectSources[i].value);
    }

    // Advanced
    var allowedPackageIds = [];
    var selectAllowedPackageIds = document.getElementById("allowedPackageIds");
    for (var i = 0; i < selectAllowedPackageIds.length; i++) {
        allowedPackageIds.push(selectAllowedPackageIds[i].value);
    }
    //Trae todo el objeto usando el api de Data Table. Coloca en pares las variables.
    var table = tableCounters
            .rows()
            .data();
    var counters = [];
    for (var x = 0; x < table.length; x++) {
        counters.push(table[x][0]);
        counters.push(table[x][1]);
        counters.push(table[x][2]);
        counters.push(table[x][3]);
        counters.push(table[x][4]);
        counters.push(table[x][5]);
    }

    var vecParams = {};
    vecParams['key'] = keymap;
    vecParams['value'] = typeRTA;
    vecParams['enabled'] = enabled;
    vecParams['id'] = id;
    vecParams['granularity'] = granularity;
    vecParams['allSources'] = allSources;
    vecParams['allowedPackageIds'] = allowedPackageIds;
    vecParams['counters'] = counters;

    $.ajax({data: vecParams,
        type: "post",
        dataType: "json",
        url: urlPrefix + "/saveRTAAdapterAllConfiguration",
        success: function (data) {
            if (data.errorCode !== 1) {
                $('#alertRTAConfig').hide();
                // Este hide es diferente hay que cambiarlo
                hideEdit(1, 'FormRTAConfig', '#RTAConfig-Actions', '#RTAactionsSources', '#divCounterConsumptionConfiguration', '#divAllowedPackageIdsConsumptionConfiguration', '#divConsumptionConfiguration', '');
                // El retorno de la llamada tiene que ser toda la config.
                if (typeof data.result.adapters !== "undefined") {
                    // Guardo el map en rtaConfig para futuras consultas
                    rtaConfig = data.result.adapters;
                }
            } else {
                $('#pRTAConfig').html("<p>Internal server error.</p>");
                $('#alertRTAConfig').show();
            }
        },
        error: function (response) {
            window.location = window.location.href;
        }
    });
}
function saveRTAAdapterFlavorUpdaterConfiguration() {
    // General
    var keymap = $('#selectRTAConfig').val() + "";
    var urlPrefix = $("#urlAPIPrefix").val();
    var typeRTA = $("#typeRTA").val();
    var enabled = $("#EnableRTAConfig").is(':checked') + "";
    var id = $.trim($("#RTAID").val());
    var granularity = $.trim($("#granularity").val());
    var selectSources = document.getElementById("selectSources");
    var allSources = [];
    for (var i = 0; i < selectSources.length; i++) {
        allSources.push(selectSources[i].value);
    }
    // Advanced 
    var sepIpAddress = $.trim($("#sepIpAddress").val());
    var sepPort = $.trim($("#sepPort").val());
    var defaultFlavorId = $.trim($("#defaultFlavorId").val());
    var newFlavorsCountToApplyInSep = $.trim($("#newFlavorsCountToApplyInSep").val());
    if (validatorVariablesRTAAdapterFlavorUpdaterConfiguration(sepIpAddress, sepPort, defaultFlavorId, newFlavorsCountToApplyInSep)) {
        var vecParams = {};
        vecParams['key'] = keymap;
        vecParams['value'] = typeRTA;
        vecParams['enabled'] = enabled;
        vecParams['id'] = id;
        vecParams['granularity'] = granularity;
        vecParams['allSources'] = allSources;
        vecParams['sepIpAddress'] = sepIpAddress;
        vecParams['sepPort'] = sepPort;
        vecParams['defaultFlavorId'] = defaultFlavorId;
        vecParams['newFlavorsCountToApplyInSep'] = newFlavorsCountToApplyInSep;
        $.ajax({data: vecParams,
            type: "post", dataType: "json",
            url: urlPrefix + "/saveRTAAdapterAllConfiguration",
            success: function (data) {
                if (data.errorCode !== 1) {
                    $('#alertForRTAConfig').hide();
                    $('#alertRTAConfig').hide();
                    hideEdit(1, 'FormRTAConfig', '#RTAConfig-Actions', '#RTAactionsSources', '', '', '', '');
                    // El retorno de la llamada tiene que ser toda la config.
                    if (typeof data.result.adapters !== "undefined") {
                        // Guardo el map en rtaConfig para mantener actualizadas las consultas
                        rtaConfig = data.result.adapters;
                    }
                } else {
                    $('#pRTAConfig').html("<p>Internal server error.</p>");
                    $('#alertRTAConfig').show();
                }
            },
            error: function (response) {
                window.location = window.location.href;
            }
        });
    } else {
        $('#alertForRTAConfig').show();
    }
}
function saveRTAAdapterGeneralConfig() {
    // General
    var keymap = $('#selectRTAConfig').val() + "";
    var urlPrefix = $("#urlAPIPrefix").val();
    var typeRTA = $("#typeRTA").val();
    var enabled = $("#EnableRTAConfig").is(':checked') + "";
    var id = $.trim($("#RTAID").val());
    var granularity = $.trim($("#granularity").val());
    var selectSources = document.getElementById("selectSources");
    var allSources = [];
    for (var i = 0; i < selectSources.length; i++) {
        allSources.push(selectSources[i].value);
    }

    var vecParams = {};
    vecParams['value'] = typeRTA;
    vecParams['enabled'] = enabled;
    vecParams['id'] = id;
    vecParams['granularity'] = granularity;
    vecParams['allSources'] = allSources;
    vecParams['keymap'] = keymap;
    $.ajax({data: vecParams,
        type: "post",
        dataType: "json",
        url: urlPrefix + "/saveRTAAdapterGeneralConfig",
        success: function (data) {
            $('#alertRTAConfig').hide();
            if (data.errorCode !== 1) {
                hideEdit(1, 'FormRTAConfig', '#RTAConfig-Actions-RDR', '', '', '', '');
                // El retorno de la llamada tiene que ser toda la config.
                if (typeof data.result.adapters !== "undefined") {
                    // Guardo el map en rtaConfig para mantener actualizadas las consultas
                    rtaConfig = data.result.adapters;
                }
            } else {
                $('#pRTAConfig').html("<p>Internal server error.</p>");
                $('#alertRTAConfig').show();
            }
        },
        error: function (response) {
            window.location = window.location.href;
        }
    });
}

// En el id=input-table0 se inyecta las options de DetailRecordType no usadas.
function selectForTableCSV() {
    //utilizando el api de DataTables. Se le solicita todos los datos para formar un vector.
    var tableCSV = CSVTable
            .rows()
            .data();
    var vecCSV = [];

    //El primer grupo de la tabla es el que esta por default. No nos interesa porque no esta en el enumerado es un * .
    if (tableCSV.length > 5) {
        for (var i = 6; i < tableCSV.length; i = i + 6) {
            vecCSV.push(tableCSV[i][0]);
        }
    }
    //Inyecta todas las opciones
    $('#DetailRecordType option').clone().appendTo('#input-table0');
    //Las compara para marcar las que ya se usaron
    //Si el vector esta vacio no realiza la comparacion
    if (vecCSV.length !== 0) {
        for (var i = 0; i < vecCSV.length; i++) {
            deleteVariableSelecTableModal(vecCSV[i]);
        }
    }
}
function selectForTableURLHits() {
    //utilizando el api de DataTables. Se le solicita todos los datos para formar un vector.
    var table = tableURLHits
            .rows()
            .data();
    var vecTable = [];

    for (var i = 0; i < table.length; i = i + 8) {
        vecTable.push(table[i][0]);
    }

    //Las compara para marcar las que ya se usaron
    //Si el vector esta vacio no realiza la comparacion
    if (vecTable.length !== 0) {
        for (var i = 0; i < vecTable.length; i++) {
            deleteVariableSelecTableModal(vecTable[i]);
        }
    }
}

//Crea un nuevo RTA
function newPeriodicDataRecordTask() {
    var urlPrefix = $("#urlAPIPrefix").val();
    var selectNewPeriodic = $('#selectNewPeriodic').val() + "";
    var vecParams = {};
    vecParams['valueType'] = selectNewPeriodic;
    $.ajax({data: vecParams,
        type: "post",
        dataType: "json",
        url: urlPrefix + "/newPeriodic",
        success: function (data) {
            if (data.errorCode === 0) {
                var cont = document.getElementById("selectPeriodicConfig").options.length;
                var periodic = "<option value='" + cont + "' >Periodic Data Record Task " + cont + "</option>";
                $('#selectPeriodicConfig').append(periodic);
                hideEdit(1, 'FormPeriodicConfig', '#PeriodicConfig-Actions', '#actionsRecordTypes', '', '', '', '');
                // El retorno de la llamada tiene que ser toda la config.
                if (typeof data.result.periodicDataRecordTasks !== "undefined") {
                    // Guardo la lista en periodicDataRecordTasks para mantener actualizadas las consultas
                    periodicDataRecordTasks = data.result.periodicDataRecordTasks;
                }
            } else {
                $('#pRTAConfig').html("<p>Internal server error.</p>");
                $('#alertRTAConfig').show();
            }
        },
        error: function (response) {
            window.location = window.location.href;
        }
    });
}

function deletePeriodicDataRecordTask() {
    var urlPrefix = $("#urlAPIPrefix").val();
    var optionDelete = $('#selectPeriodicConfig').val() + "";
    var vecParams = {};
    vecParams['index'] = optionDelete;
    $.ajax({data: vecParams,
        type: "post",
        dataType: "json",
        url: urlPrefix + "/deletePeriodic",
        success: function (data) {
            if (data.errorCode === 0) {
                // Elimina la opcion seleccionada
                $("#selectPeriodicConfig option[value='" + optionDelete + "']").remove();
                // Oculta el panel de la derecha
                $("#FormPeriodicConfig").hide();
                // Saca los botones de save y cancel
                hideEdit(1, 'FormPeriodicConfig', '#PeriodicConfig-Actions', '#actionsRecordTypes', '', '', '', '');
                // El retorno de la llamada tiene que ser toda la config.
                if (typeof data.result.periodicDataRecordTasks !== "undefined") {
                    // Guardo la lista en periodicDataRecordTasks para mantener actualizadas las consultas
                    periodicDataRecordTasks = data.result.periodicDataRecordTasks;
                }
            } else {
                $('#pPeriodicConfig').html("<p>Internal server error.</p>");
                $('#alertPeriodicConfig').show();
            }
        },
        error: function (response) {
            window.location = window.location.href;
        }
    });
}

function savePeriodicDataRecordTasks() {
    // General
    var index = $('#selectPeriodicConfig').val() + "";
    var urlPrefix = $("#urlAPIPrefix").val();
    var typePeriodic = $("#typePeriodic").val() + "";
    var enabled = $("#EnablePeriodicConfig").is(':checked') + "";
    var cronPattern = $.trim($("#cronPattern").val());
    var days = $.trim($("#days").val());
    var selectRecordTypes = document.getElementById("selectRecordTypes");
    var allRecordTypes = [];
    for (var i = 0; i < selectRecordTypes.length; i++) {
        allRecordTypes.push(selectRecordTypes[i].value);
    }
    // Valida en caso de tratarce de PeriodicRemovalTask
    if (typePeriodic === "PeriodicRemovalTask" && !/^\d+$/.test(days)) {
        $('#pPeriodicConfig').html("<p>The value entered is incorrect.</p>");
        $('#alertPeriodicConfig').show();
        alert_red("days");
    } else {
        var vecParams = {};
        vecParams['index'] = index;
        vecParams['type'] = typePeriodic;
        vecParams['enabled'] = enabled;
        vecParams['recordTypes'] = allRecordTypes;
        vecParams['cronPattern'] = cronPattern;
        vecParams['days'] = days;

        $.ajax({data: vecParams,
            type: "post",
            dataType: "json",
            url: urlPrefix + "/savePeriodicDataRecordTasks",
            success: function (data) {
                $('#alertPeriodicConfig').hide();
                if (data.errorCode !== 1) {
                    hideEdit(1, 'FormPeriodicConfig', '#PeriodicConfig-Actions', '#actionsRecordTypes', '', '', '', '');
                    // El retorno de la llamada tiene que ser toda la config.
                    if (typeof data.result.periodicDataRecordTasks !== "undefined") {
                        // Guardo la lista en periodicDataRecordTasks para mantener actualizadas las consultas
                        periodicDataRecordTasks = data.result.periodicDataRecordTasks;
                    }
                } else {
                    $('#pPeriodicConfig').html("<p>Internal server error.</p>");
                    $('#alertPeriodicConfig').show();
                }
            },
            error: function (response) {
                window.location = window.location.href;
            }
        });
    }
}
function refreshConfig(idConfig, idIP, idPort) {
    var url = $("#urlAPIPrefix").val();
    var query1 = "#ajax-loader" + idConfig;
    var query2 = "#ajax-Refresh" + idConfig;
    $(query2).hide();
    $(query1).show();
//    var ip = $("#" + idIP).val();
//    var port = $("#" + idPort).val();
    switch (idConfig) {
        case 'SEP':
            url += "/reloadSepConfiguration";
            break;
        case 'Notification':
            url += "/reloadNotificationServerConfiguration";
            break;
        case 'CSM':
            url += "/reloadCsmConfiguration";
            break;
    }
    var vecParams = {};
    $.ajax({data: vecParams,
        type: "post",
        dataType: "json",
        url: url,
        success: function (data) {
            $(query2).show();
            $(query1).hide();
        },
        error: function (response) {
            window.location = window.location.href;
        }
    });
}