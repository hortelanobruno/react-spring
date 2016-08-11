var urlAPIPrefix = "/akax"; // URL para las llamadas ajax
// VTUR-HTUR-SUR
var liQoESceId = 'liQoESce'; // Id para li que pone dinamicamente
var liQoECmtsId = 'liQoECmts';

// Se utiliza para guardar el tipo de tabla (VTUR-HTUR-SUR)
var myType = null;
// Indica si se esta trabajando la tabla con cmst o con sce
var isCmts = false;
// Se utiliza para guardar la ip de un sce cuando se click en el link del mismo
var currentSceIp = null;

$(document).ready(function() {
	switch (viewTypeQoE) {
	case 'vtur':
		$('#buttonVTUR').trigger('click');
		break;
	case 'htur':
		$('#buttonHTUR').trigger('click');
		break;
	case 'sur':
		$('#buttonSUR').trigger('click');
		break;
	}
});// Fin ready function

/**
 * Muestra la pantalla correspondiente
 * 
 * @param {type}
 *            type Tipo de pantalla
 */
function viewType(type) {
	myType = type;
	isCmts = false;
	showGlobal(myType, false, false);
}

/**
 * Vuelve al home de la pantalla
 * 
 * 
 */
function showHome() {
	// Oculto botones de la barra >Home>Char Type>SCE
	$('#charType').hide();
	$('#device').hide();
	$('#divTabla').hide();

	removeListItem();
	showButtonsPrincipales();
	myType = null;
	isCmts = false;
	currentSceIp = null;
}

/**
 * Muestra los botones de la barra principal
 * 
 */
function showButtonsPrincipales() {
	$('#buttonVTUR').show();
	$('#buttonHTUR').show();
	$('#buttonSUR').show();
	$('#buttonDevice').show();
}

/**
 * Oculta los botones de la barra principal
 * 
 */
function hideButtonsPrincipales() {
	$('#buttonVTUR').hide();
	$('#buttonHTUR').hide();
	$('#buttonSUR').hide();
	$('#buttonDevice').hide();
}

/**
 * Muestra la tabla para un tipo de CDR (VTUR-HTUR-SUR)
 * 
 * @param {type}
 *            type Indica el typo de CDR (VTUR-HTUR-SUR)
 * @param {type}
 *            isByPackage Indica si hay filtro por package
 * @param {type}
 *            isByService Indica si hay filtro por service
 */
function showGlobal(type, isByPackage, isByService) {
	var cdrData = executeGlobalQuery(type, isByPackage, isByService);
	if ((cdrData !== 'undefined') && (cdrData !== null)) {
		hideButtonsPrincipales();
		addListItemOnOrderedList(cdrData.templateName, isByPackage, isByService);
		loadTableTagline(cdrData.templateName, new Date(cdrData.timestamp * 1000));
		buildTheadTable(isByPackage, isByService, showGlobalWithFilters, showGlobalWithFilters);
		buildTBodyTable(cdrData);
	} else {
		showNoResult();
	}
}

/**
 * Muestra un mensaje de que indica que no hubo resultado para la consulta
 * 
 */
function showNoResult() {
	hideButtonsPrincipales();
	$('#divTabla').show();
	$('#titulo').text("");
	$('#tabla').empty();
	$('#timestamp').text("No results found");
}

/**
 * Construye el Thead de la tabla
 * 
 * @param {type}
 *            isByPackage Indica si el query tiene filtro por package
 * @param {type}
 *            isByService Indica si el query tiene filtro por service
 * @param {type}
 *            functionCheckboxService Funcion que se ejecuta en el click del checkbox de service
 * @param {type}
 *            functionCheckboxPackage Funcion que se ejecuta en el click del checkbox de package
 */
function buildTheadTable(isByPackage, isByService, functionCheckboxService, functionCheckboxPackage) {
	var thead = "<thead>";
	thead += "<tr><th>Device</th>";
	thead += "<th><input class='checkboxTable' id = 'checkboxService' type='checkbox'>Service</th>";
	thead += "<th><input class='checkboxTable' id = 'checkboxPackage' type='checkbox'>Package</th>";
	thead += "<th>Quality</th>";
	thead += "<th>Low</th>";
	thead += "<th>Mid</th>";
	thead += "<th>High</th>";
	thead += "<th>Max</th>";
	thead += "<th>Streams</th></tr>";
	$('#tabla').append(thead);
	$('#checkboxService').click(functionCheckboxService);
	$('#checkboxPackage').click(functionCheckboxPackage);

	$('#checkboxService').prop('checked', false);
	$('#checkboxPackage').prop('checked', false);
	if (isByPackage) {
		$('#checkboxPackage').prop('checked', true);
	}
	if (isByService) {
		$('#checkboxService').prop('checked', true);
	}
}

/**
 * Construye el TBody de la tabla
 * 
 * @param {type}
 *            cdrData Datos recibidos de la llamada ajax
 */
function buildTBodyTable(cdrData) {
	var tbody = "<tbody>";
	var index = 1;
	for ( var cdr in cdrData.cdrs) {

		tbody += "<tr>";
		if (isCmts) {
			tbody += "<td>" + cdrData.cdrs[cdr].networkDevice.name + "</td>";
		} else {
			tbody += "<td><a class='linkStyle' id='" + index + "'>" + cdrData.cdrs[cdr].networkDevice.name + "</a></td>";
			tbody += "<td style='display:none' id='deviceIp" + index + "'>" + cdrData.cdrs[cdr].networkDevice.ipAddress + "</td>";
		}

		if ((typeof cdrData.cdrs[cdr].service !== 'undefined') && (cdrData.cdrs[cdr].service !== null)) {
			tbody += "<td>" + cdrData.cdrs[cdr].service.name + "</td>";
		} else {
			tbody += "<td></td>";
		}

		if ((typeof cdrData.cdrs[cdr].pkg !== 'undefined') && (cdrData.cdrs[cdr].pkg !== null)) {
			tbody += "<td>" + cdrData.cdrs[cdr].pkg.name + "</td>";
		} else {
			tbody += "<td></td>";
		}

		tbody += "<td><strong>" + cdrData.categories[cdrData.cdrs[cdr].quality] + "</strong></td>";

		for ( var cat in cdrData.categories) {
			tbody += "<td><div data-toggle='tooltip' title='Value: " + cdrData.cdrs[cdr].categories[cat].value + " - Unique subs: " + cdrData.cdrs[cdr].categories[cat].uniqSubs + "'>" + cdrData.cdrs[cdr].categories[cat].relativeValue.toFixed(2) + "%</div></td>";
		}

		tbody += "<td><div data-toggle='tooltip' title='Value: " + cdrData.cdrs[cdr].streams.value + ".'>" + cdrData.cdrs[cdr].streams.relativeValue.toFixed(2) + "%</div></td>";
		tbody += "</tr>";

		index = index + 1;
	}
	tbody += "</tbody>";
	$('#tabla').append(tbody);

	// Setea la funcion en los links
	for (i = 1; i <= index; i++) {
		$('#' + i).click(function() {
			var index = $(this)[0].id;
			var deviceIp = $('#deviceIp' + index).text();
			showDevice(deviceIp);
		});
	}
}

/**
 * Muestra la tabla para un sce
 * 
 * @param {type}
 *            deviceIp Es la ip del SCE
 */
function showDevice(deviceIp) {
	isCmts = true;
	showDeviceSCE(deviceIp);
}

/**
 * Muestra la tabla para un SCE
 * 
 * @param {type}
 *            sceIp Es la ip del SCE
 */
function showDeviceSCE(sceIp) {
	// Gurda la ip del sce del cual se van a mostrar los cmts
	currentSceIp = sceIp;
	var isByPackage = false;
	var isByService = false;
	if ($('#checkboxService').prop('checked')) {
		isByService = true;
	}
	if ($('#checkboxPackage').prop('checked')) {
		isByPackage = true;
	}
	var cdrData = executeQueryBySce(sceIp, isByPackage, isByService);
	if ((cdrData !== 'undefined') && (cdrData !== null) && (cdrData.cdrs.length > 0)) {
		hideButtonsPrincipales();
		addListItemOnOrderedList(cdrData.templateName, isByPackage, isByService);
		loadTableTagline(cdrData.templateName, new Date(cdrData.timestamp * 1000));
		buildTheadTable(isByPackage, isByService, showGlobalWithFilters, showGlobalWithFilters);
		buildTBodyTable(cdrData);
	} else {
		showNoResult();
	}

}

/**
 * Ejecuta un global query para VTUR, HTUR y SUR
 * 
 * @param {type}
 *            queryType Indica el tipo de query a ejecutar (VTUR, HTUR, SUR)
 * @param {type}
 *            isByPackage Indica si tiene filtro por package
 * @param {type}
 *            isByService Indica si tiene filtro por service
 */
function executeGlobalQuery(queryType, isByPackage, isByService) {
	var cdrData = null;
	var vecParams = {};
	vecParams['type'] = queryType;
	vecParams['byPackage'] = isByPackage;
	vecParams['byService'] = isByService;
	$.ajax({
		type : "post",
		dataType : "json",
		data : vecParams,
		async : false,
		url : urlAPIPrefix + "/executeQueryByType",
		success : function(data) {
			cdrData = data;
		},
		error : function(response) {
			var str = response.responseText + "";
			var pos = str.lastIndexOf("Login");
			if (pos !== -1) {
				window.location = window.location.href;
			}
		}
	});
	return cdrData;
}

/**
 * 
 * @param {type}
 *            deviceIp Es la ip del SCE
 * @param {type}
 *            isByPackage Indica si tiene filtro por package
 * @param {type}
 *            isByService Indica si tiene filtro por service
 * @returns {data} cdrData Contiene la info devuelta por la consulta
 */
function executeQueryBySce(deviceIp, isByPackage, isByService) {
	var cdrData = null;
	var vecParams = {};
	vecParams['type'] = myType;
	vecParams['sce'] = deviceIp;
	vecParams['byPackage'] = false;
	vecParams['byService'] = false;
	if (isByService) {
		vecParams['byService'] = true;
	}
	if (isByPackage) {
		vecParams['byPackage'] = true;
	}

	$.ajax({
		type : "post",
		dataType : "json",
		data : vecParams,
		async : false,
		url : urlAPIPrefix + "/executeQueryBySCE",
		success : function(data) {
			cdrData = data;
		},
		error : function(response) {
			var str = response.responseText + "";
			var pos = str.lastIndexOf("Login");
			if (pos !== -1) {
				window.location = window.location.href;
			}
		}
	});
	return cdrData;
}

/**
 * Agrega un list item en la ordered list orderedListBar
 * 
 * @param {type}
 *            linkName Nombre del link que contiene el list item
 * @param {type}
 *            isByPackage Indica si esta aplicado el filtro por package
 * @param {type}
 *            isByService Indica si esta aplicado el filtro por package
 * 
 */
function addListItemOnOrderedList(linkName, isByPackage, isByService) {
	var id = null;
	var functionName = null;

	if (isCmts) {
		id = liQoECmtsId;
		functionName = callShowDevice;
	} else {
		id = liQoESceId;
		functionName = showGlobalSceWithFilters;
	}

	if (!existKey(id)) {
		var li = "<li id = ";
		li += id;
		li += " class='active'>";
		li += "<a class='linkStyle'>";
		li += linkName;
		li += "</a></li>";
		$('#orderedListBar').append(li);
		$('#' + id).click(functionName);
	}

	// Habilita todos los links
	$("#" + liQoESceId).removeClass("linkDisabled");
	$("#" + liQoESceId + " a").removeClass("textDisabled");
	if (existKey(liQoECmtsId)) {
		$("#" + liQoECmtsId).removeClass("linkDisabled");
		$("#" + liQoECmtsId + " a").removeClass("textDisabled");
	}
	// Deshabilita el activo
	$("#" + id).addClass("linkDisabled");
	$("#" + id + " a").addClass("textDisabled");
}

/**
 * Llama a la funcion callShowDevice
 * 
 */
function callShowDevice() {
	isCmts = true;
	showDevice(currentSceIp);
}

/**
 * Indica si esxite una clave
 * 
 * @param {type}
 *            key Clave a chequear
 * @returns {Boolean} true o false
 */
function existKey(key) {
	if (($('#' + key)[0] !== undefined) && ($('#' + key)[0] !== null)) {
		return true;
	}
	return false;
}

/**
 * Remueve los listItem de la orderedList, se utiliza cuando reinicia la pantalla
 * 
 */
function removeListItem() {
	// VTUR-HTUR-SUR
	if ($('#' + liQoESceId) !== 'undefined' && $('#' + liQoESceId) !== null) {
		$('#' + liQoESceId).remove();
	}

	if ($('#' + liQoECmtsId) !== 'undefined' && $('#' + liQoECmtsId) !== null) {
		$('#' + liQoECmtsId).remove();
	}
}

/**
 * Agrega el titulo a la tabla, vacia la tabla y setea el timestamp
 * 
 * @param {type}
 *            tableTitle Titulo de la tabla
 * @param {type}
 *            timestamp Fecha
 * 
 */
function loadTableTagline(tableTitle, timestamp) {
	$('#divTabla').show();
	$('#titulo').text(tableTitle);
	$('#timestamp').text(timestamp);
	$('#tabla').empty();
}

/**
 * Muestra la tabla teniendo cuenta el/los filtros
 * 
 */
function showGlobalWithFilters() {
	if (isCmts) {
		showDevice(currentSceIp);
	} else {
		showGlobalSceWithFilters();
	}

}

/**
 * Muestra la tabla teniendo en cuenta los posibles filtros aplicados (isByService, isByPackage)
 * 
 */
function showGlobalSceWithFilters() {
	isCmts = false;
	if ($('#checkboxService').prop('checked') && $('#checkboxPackage').prop('checked')) {
		// Tiene tildado ambos checkbox
		showGlobal(myType, true, true);

	} else if ($('#checkboxService').prop('checked')) {
		// Tiene tildado solo service
		showGlobal(myType, false, true);

	} else {

		if ($('#checkboxPackage').prop('checked')) {
			// Destildo service
			showGlobal(myType, true, false);

		} else {
			// Destildado ambos checkbox
			showGlobal(myType, false, false);

		}

	}
}