var INITIAL_DEVICE_TYPE = "SCE";
var deviceType = null;
var deviceStats = null;

$(document).ready(function() {
	showTable(INITIAL_DEVICE_TYPE);
	$('#divTabla').show();
}); // Fin ready function

function showTable(type) {
	deviceType = type;
	activeLink();
	obtainStats();
	loadTableStatics();
}

function activeLink() {
	if (deviceType === "SCE") {
		$("#buttonCMTS").removeClass("linkDisabled");
		$("#buttonCMTS a").removeClass("textDisabled");
		$("#buttonSCE").addClass("linkDisabled");
		$("#buttonSCE a").addClass("textDisabled");
	} else if (deviceType === "CMTS") {
		$("#buttonCMTS").addClass("linkDisabled");
		$("#buttonCMTS a").addClass("textDisabled");
		$("#buttonSCE").removeClass("linkDisabled");
		$("#buttonSCE a").removeClass("textDisabled");
	}
}

function obtainStats() {
	if (deviceType !== null && deviceType !== undefined) {
		deviceStats = getDeviceStatistics(deviceType);
	}
}

function loadTableStatics() {
	// Limpia
	$('#titulo').empty();
	$('#tabla').empty();
	// Carga
	if (deviceType !== null && deviceType !== undefined) {
		buildTableTitle();
		buildTableHeader();
		buildTableBody();
	}
}

function buildTableTitle() {
	$('#titulo').append(deviceType.toString() + " Statistics");
}

function buildTableHeader() {
	var thead = "";
	thead += "<thead class='tableHeader'>";
	// Carga la data de correspondiente del header
	if (deviceType === "SCE") {
		thead += loadSCETableHeader();
	} else if (deviceType === "CMTS") {
		thead += loadCMTSTableHeader();
	}
	thead += "</thead>";
	$('#tabla').append(thead);
}

function buildTableBody() {
	var tBody = "";
	tBody += "<tbody>";
	if (deviceStats !== null && deviceStats !== undefined && deviceStats.length > 0) {
		// Carga los las filas correspondientes
		if (deviceType === "SCE") {
			tBody += loadSCETableRows();
		} else if (deviceType === "CMTS") {
			tBody += loadCMTSTableRows();
		}
	}
	tBody += "</tbody>";
	$('#tabla').append(tBody);
}

// SCE /////////////////////////////////////////////////////////////
function loadSCETableHeader() {
	var thead = "";
	thead += "<tr>";
	thead += "<th>SCE Name</th>";
	thead += "<th class='alignCenter'>IP Address</th>";
	thead += "<th class='alignCenter'>RDR SUR Formatter Connected</th>";
	thead += "<th class='alignRight'>Last RDR SUR</th>";
	thead += "</tr>";
	return thead;
}

function loadSCETableRows() {
	var tRows = "";

	sortSCEStats();
	for ( var sceIndex in deviceStats) {
		tRows += buildSCETableRow(deviceStats[sceIndex]);
	}
	return tRows;
}

function buildSCETableRow(sce) {
	var tRow = "";
	tRow += "<tr>";
	// SCE Name
	tRow += "<td>" + getFormattedValue(sce.sceName, sce.sceName, "N/A") + "</td>";
	// IP Address
	tRow += "<td class='alignCenter'>" + getFormattedValue(sce.sceIP, sce.sceIP, "-") + "</td>";
	// SUR Connected
	tRow += "<td class='alignCenter'>" + getStatusLight(getFormattedValue(sce.connectionRDRSurUp, sce.connectionRDRSurUp, false)) + "</td>";//
	// Last SUR
	tRow += "<td class='alignRight'>" + getFormattedValue(sce.lastRDR, formatDate(sce.lastRDR), "-") + "</td>";
	tRow += "</tr>";

	return tRow;
}

// CMTS ////////////////////////////////////////////////////////////
function loadCMTSTableHeader() {
	var thead = "";
	thead += "<tr>";
	thead += "<th>CMTS Name</th>";
	thead += "<th class='alignCenter'>IP Address</th>";
	thead += "<th class='alignCenter'>Enabled</th>";
	thead += "<th class='alignCenter''>Connected</th>";
	thead += "<th class='alignRight'>Last IPDR</th>";
	thead += "</tr>";
	return thead;
}

function loadCMTSTableRows() {
	var tRows = "";

	sortCMTSStats();
	for ( var cmtsIndex in deviceStats) {
		tRows += buildCMTSTableRow(deviceStats[cmtsIndex]);
	}
	return tRows;
}

function buildCMTSTableRow(cmts) {
	var tRow = "";
	tRow += "<tr>";
	// CMTS Name
	tRow += "<td>" + getFormattedValue(cmts.cmtsName, cmts.cmtsName, "N/A") + "</td>";
	// IP Address
	tRow += "<td class='alignCenter'>" + getFormattedValue(cmts.cmtsIP, cmts.cmtsIP, "-") + "</td>";
	// Enabled
	tRow += "<td class='alignCenter'>" + getStatusLight(getFormattedValue(cmts.ipdrEnable, cmts.ipdrEnable, false)) + "</td>";
	// Connected
	tRow += "<td class='alignCenter'>" + getStatusLight(getFormattedValue(cmts.ipdrConnected, cmts.ipdrConnected, false)) + "</td>";
	// Last IPDR
	tRow += "<td class='alignRight'>" + getFormattedValue(cmts.lastIPDR, formatDate(cmts.lastIPDR), "-") + "</td>";
	tRow += "</tr>";

	return tRow;
}

// METODOS AUXILIARES //////////////////////////////////////////////
function formatDate(longDate) {
	var date = new Date(longDate);
	return date.toString();
}

function getFormattedValue(variable, value, defaultValue) {
	if (variable !== null && variable !== undefined) {
		return value;
	}
	return defaultValue;
}

function getStatusLight(status) {
	var lightFileUrl = null;

	if (status) {
		lightFileUrl = "/img/stats/green-light.png";
	} else {
		lightFileUrl = "/img/stats/red-light.png";
	}
	return "<img class='statusLight' src='" + lightFileUrl + "'>";
}

function sortSCEStats() {
	deviceStats.sort(function(sce1, sce2) {
		return (sce1.sceName >= sce2.sceName ? 1 : -1);
	});
}

function sortCMTSStats() {
	deviceStats.sort(function(cmts1, cmts2) {
		return (cmts1.cmtsName >= cmts2.cmtsName ? 1 : -1);
	});
}

// LLAMADAS AJAX ///////////////////////////////////////////////////
/**
 * Obtiene las esta
 * 
 * @param {type}
 *            deviceType
 * @returns {data}
 */
function getDeviceStatistics(deviceType) {
	var deviceStatistics = null;
	var vecParams = {};
	vecParams['deviceType'] = deviceType;
	$.ajax({
		type : "post",
		dataType : "json",
		data : vecParams,
		async : false,
		url : "/ajax/getDeviceStatistics",
		success : function(data) {
			deviceStatistics = data;
		},
		error : function(response) {
			var str = response.responseText + "";
			var pos = str.lastIndexOf("Login");
			if (pos !== -1) {
				window.location = window.location.href;
			}
		}
	});
	return deviceStatistics;
}