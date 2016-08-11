
var CONTEXT_PATH = "/nap-frontend";
var ADMIN_PATH = CONTEXT_PATH + "/admin";

/**
 * Arma la tabla de Apps a partir del parametro recibido
 * @param {type} client
 * @returns {undefined}
 */
function buildTable(client) {
    loadTable(client);
}
/**
 * Obtiene la cantidad de filas que se quieren crear para la tabla
 * @param {type} client
 * @returns {undefined}
 */
function getRowCount(client) {
    if (client !== null) {
        switch (client.toString().toUpperCase()) {
            case "NADIP":
                return 1;
            case "SEP":
                return 3;                
            case "CABLEVISION":
                return 3;
            case "MTNL":
                return 1;                
            case "TELERED":
                return 3;
            default:
                return 3;
        }
    } else {
        return 3;
    }
}
/**
 * Carga el contenido de la tabla con las apps correspondientes
 * @param {type} client
 * @returns {undefined}
 */
function loadTable(client) {
    if (client !== null) {
        switch (client.toString().toUpperCase()) {
            case "NADIP":
                loadTableNADIP();
                break;
            case "SEP":
                loadTableSEP();
                break;                
            case "CABLEVISION":
                loadTableCablevision();
                break;
            case "MTNL":
                loadTableMTNL();
                break;                
            case "TELERED":
                loadTableTelered();
                break;                
            default:
                loadTableAll();
                break;
        }
    } else {
        loadTableAll();
    }
}

// APP Buttons  ////////////////////////////////////////////////////////////////
function startAppButtonsRow(rowName) {
    return "<div id='rowButtons'>";
}

function endAppButtonsRow() {
    return "</div>";
}

function buildAppButton(appName, url, image) {
    return "<li><a href=" + url + ">" + appName + "</a></li>";
}
// APPs ////////////////////////////////////////////////////////////////////////
function addAppNetworkNavigator() {
    $('#tableApplications').append(buildAppButton("Network Navigator", ADMIN_PATH + "/networkNavigator"));
}

function addAppSystemStatistics() {
    $('#tableApplications').append(buildAppButton("System Statistics", ADMIN_PATH + "/systemStatistics"));
}

function addAppQoEHTURAnalysis() {
    $('#tableApplications').append(buildAppButton("HTTP QoE Transaction Analysis", ADMIN_PATH + "/qoe/htur"));
}

function addAppQoESURAnalysis() {
    $('#tableApplications').append(buildAppButton("Subscriber QoE Usage Analysis", ADMIN_PATH + "/qoe/sur"));
}

function addAppQoEVTURAnalysis() {
    $('#tableApplications').append(buildAppButton("Video QoE Transaction Analysis", ADMIN_PATH + "/qoe/vtur"));
}

function addAppDynamicServices() {
    $('#tableApplications').append(buildAppButton("Dynamic Services", ADMIN_PATH + "/reporter?domain=Dynamic_Services"));
}

function addAppInBrowserNotification() {
    $('#tableApplications').append(buildAppButton("In-Browser Notification", ADMIN_PATH + "/reporter?domain=InBrowser_Notification"));
}

function addAppIPDRMonitoring() {
    $('#tableApplications').append(buildAppButton("IPDR Monitoring", ADMIN_PATH + "/reporter?domain=IPDR_Monitoring"));
}

function addAppSubscriberMonitoring() {
    $('#tableApplications').append(buildAppButton("Subscriber Monitoring", ADMIN_PATH + "/reporter?domain=Subscriber_Monitoring"));
}

function addAppTrafficCongestion() {
    $('#tableApplications').append(buildAppButton("Traffic Congestion", ADMIN_PATH + "/reporter?domain=Traffic_Congestion"));
}

function addAppTrafficMonitoring() {
    $('#tableApplications').append(buildAppButton("Traffic Monitoring", ADMIN_PATH + "/reporter?domain=Traffic_Monitoring"));
}

function addAppURLAnalysis() {
    $('#tableApplications').append(buildAppButton("URL Analysis", ADMIN_PATH + "/reporter?domain=URL_Analysis"));
}

function addAppVideoMonitoring() {
    $('#tableApplications').append(buildAppButton("Video Monitoring", ADMIN_PATH + "/reporter?domain=Video_Monitoring"));
}

function addAppBubbleMap() {
    $('#tableApplications').append(buildAppButton("Bubble Map", ADMIN_PATH + "/bubbleMap"));
}
function NAPsettings() {
    $('#tableApplications').append(buildAppButton("NAP Settings", ADMIN_PATH + "/NAPsettings"));
}



// Tables //////////////////////////////////////////////////////////////////////
function loadTableAll() {
    var row = 0;
    // ROW 1
    row = 1;
    addAppNetworkNavigator(row);
    addAppSystemStatistics(row);
    addAppQoEHTURAnalysis(row);
    addAppQoESURAnalysis(row);
    addAppQoEVTURAnalysis(row);
    // ROW 2
    row = 2;
    addAppDynamicServices(row);
    addAppInBrowserNotification(row);
    addAppIPDRMonitoring(row);
    addAppSubscriberMonitoring(row);
    addAppTrafficCongestion(row);
    // ROW 3
    row = 3;
    addAppTrafficMonitoring(row);
    addAppURLAnalysis(row);
    addAppVideoMonitoring(row);
    addAppBubbleMap(row);
    NAPsettings(row);
}

function loadTableNADIP() {
    var row = 0;
    // ROW 1
    row = 1;
    addAppNetworkNavigator(row);
    addAppInBrowserNotification(row);
}

function loadTableSEP() {
    var row = 0;
    // ROW 1
    row = 1;
    addAppNetworkNavigator(row);
    addAppDynamicServices(row);
    addAppInBrowserNotification(row);
    // ROW 2
    row = 2;
    addAppSubscriberMonitoring(row);
    addAppTrafficCongestion(row);
    addAppTrafficMonitoring(row);
    // ROW 3
    row = 3;
    addAppURLAnalysis(row);
    addAppVideoMonitoring(row);    
}

function loadTableCablevision() {
    var row = 0;
    // ROW 1
    row = 1;
    addAppDynamicServices(row);
    addAppIPDRMonitoring(row);
    addAppSubscriberMonitoring(row);
    // ROW 2
    row = 2;
    addAppTrafficCongestion(row);
    addAppTrafficMonitoring(row);
    addAppVideoMonitoring(row);
    // ROW 3
    row = 3;
    addAppNetworkNavigator(row);
}

function loadTableMTNL() {
    var row = 0;
    // ROW 1
    row = 1;
    addAppNetworkNavigator(row);
    addAppInBrowserNotification(row);
}

function loadTableTelered() {
    var row = 0;
    // ROW 1
    row = 1;
    addAppNetworkNavigator(row);
    addAppSystemStatistics(row);
    addAppQoEHTURAnalysis(row);
    addAppQoESURAnalysis(row);
    addAppQoEVTURAnalysis(row);
    // ROW 2
    row = 2;
    addAppDynamicServices(row);
    addAppInBrowserNotification(row);
    addAppIPDRMonitoring(row);
    addAppSubscriberMonitoring(row);
    addAppTrafficCongestion(row);
    // ROW 3
    row = 3;
    addAppTrafficMonitoring(row);
    addAppURLAnalysis(row);
    addAppVideoMonitoring(row);
}
