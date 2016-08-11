/////////////////////// DEFINICION DE VARIABLES GLOBALES ///////////////////////////////////
var urlAPIPrefix = null;
// TIMEZONE OFFSET (Offset utilizado para Highcharts)
var TIMEZONE_OFFSET = -3;

$(document).ready(function() { // INICIO FUNCIO READY
	// SETEO DE URL-PREFIX
	urlAPIPrefix = "/ajax";

	// SPLIT-PANE
	$('#layoutDashboard').layout({
		center__paneSelector : ".outer-center",
		west__paneSelector : ".outer-west",
		east__paneSelector : ".outer-east",
		west__size : 125,
		east__size : 125,
		spacing_open : 8 // ALL panes
		,
		spacing_closed : 12 // ALL panes
		,
		north__maxSize : 200,
		south__maxSize : 200

		// MIDDLE-LAYOUT (child of outer-center-pane)
		,
		center__childOptions : {
			center__paneSelector : ".middle-center",
			west__paneSelector : ".middle-west",
			east__paneSelector : ".middle-east",
			west__size : 200,
			east__size : 100,
			spacing_open : 8 // ALL panes
			,
			spacing_closed : 12
		// ALL panes
		}
	});

	// Carga los dashboards existentes
	loadDashboards();
	$("#dashboardsList").selectable();
});// FIN FUNCION READY

/**
 * Obtiene todos los dashboards existentes para el usuario
 * 
 * @returns {unresolved}
 */
function getDashboards() {
	var dashboards = [];
	$.ajax({
		type : "get",
		dataType : "json",
		async : false,
		url : urlAPIPrefix + "/dashboard/getDashboardsInfo",
		success : function(data) {
			dashboards = data;
		},
		error : function(response) {
			var str = response.responseText + "";
			var pos = str.lastIndexOf("Login");
			if (pos !== -1) {
				window.location = window.location.href;
			}
		}
	});
	return dashboards; // Listado de Dashboards (Id, Name)
}

/**
 * Obtiene el dashboard correspondiente al id especificado
 * 
 * @param {type}
 *            dashboardId
 * @returns {undefined}
 */
function getDashboard(dashboardId) {
	var dashboard = null;
	var vecParams = {};
	vecParams['dashboardId'] = dashboardId;
	$.ajax({
		type : "get",
		dataType : "json",
		data : vecParams,
		async : false,
		url : urlAPIPrefix + "/dashboard/getDashboard",
		success : function(data) {
			dashboard = data;
		},
		error : function(response) {
			var str = response.responseText + "";
			var pos = str.lastIndexOf("Login");
			if (pos !== -1) {
				window.location = window.location.href;
			}
		}
	});
	return dashboard; // Objeto dashboard con sus componentes
}

/**
 * Obtiene el id del dashboard seleccionado
 * 
 * @returns {undefined}
 */
function getSelectedDashboardId() {
	var selectedDashboard = $('#dashboardsList .ui-selected');
	if (selectedDashboard !== null && selectedDashboard !== undefined) {
		return selectedDashboard.attr('dashboardId');
	}
	return null;
}

/**
 * Obtiene el name del dashboard seleccionado
 * 
 * @returns {undefined}
 */
function getSelectedDashboardName() {
	var selectedDashboard = $('#dashboardsList .ui-selected');
	if (selectedDashboard !== null && selectedDashboard !== undefined) {
		return selectedDashboard.text();
	}
	return null;
}

/**
 * Crea un nuevo dashboard
 * 
 * @returns {undefined}
 */
function addDashboard() {
	var dashboardId = null;
	var dashboardName = $('#addDashboardName').val();
	$('#dialogAddDashboardButtonClose').click();
	// LLAMADA AJAX
	var vecParams = {};
	vecParams['dashboardName'] = dashboardName;
	$.ajax({
		type : "post",
		dataType : "json",
		data : vecParams,
		async : false,
		url : urlAPIPrefix + "/dashboard/createDashboard",
		success : function(data) {
			dashboardId = data;
			$('#dashboardsList').append("<li class='dashboardListItem' id='dashboardItem" + dashboardId + "' dashboardId='" + dashboardId + "'>" + dashboardName + "</li>");
		},
		error : function(response) {
			var str = response.responseText + "";
			var pos = str.lastIndexOf("Login");
			if (pos !== -1) {
				window.location = window.location.href;
			}
		}
	});
}

/**
 * Remueve el dashboard seleccionado
 * 
 * @returns {undefined}
 */
function deleteDashboard() {
	var dashboardId = getSelectedDashboardId();
	$('#dialogDeleteDashboardButtonClose').click();
	// LLAMADA AJAX
	var vecParams = {};
	vecParams['dashboardId'] = dashboardId;
	$.ajax({
		type : "post",
		dataType : "json",
		data : vecParams,
		async : false,
		url : urlAPIPrefix + "/dashboard/deleteDashboard",
		success : function(data) {
			$('#dashboardItem' + dashboardId).remove();
			$('#dashboardComponentsContainer').empty();
		},
		error : function(response) {
			var str = response.responseText + "";
			var pos = str.lastIndexOf("Login");
			if (pos !== -1) {
				window.location = window.location.href;
			}
		}
	});
}

/**
 * Actualiza el nombre del dashboard seleccionado
 * 
 * @returns {undefined}
 */
function editDashboard() {
	var dashboardId = getSelectedDashboardId();
	var newDashboardName = $('#editDashboardName').val();
	$('#dialogEditDashboardButtonClose').click();
	// LLAMADA AJAX
	var vecParams = {};
	vecParams['dashboardId'] = dashboardId;
	vecParams['dashboardName'] = newDashboardName;
	$.ajax({
		type : "post",
		dataType : "json",
		data : vecParams,
		async : false,
		url : urlAPIPrefix + "/dashboard/updateDashboard",
		success : function() {
			$('#dashboardItem' + dashboardId).text(newDashboardName);
		},
		error : function(response) {
			var str = response.responseText + "";
			var pos = str.lastIndexOf("Login");
			if (pos !== -1) {
				window.location = window.location.href;
			}
		}
	});
}

/**
 * Carga el listado de dashboards existentes para el usuario
 * 
 * @returns {undefined}
 */
function loadDashboards() {
	var dashboards = getDashboards();

	$('#dashboardsList').empty();
	var dashboardId = null;
	var dashboardName = null;
	for ( var dashboardIndex in dashboards) {
		dashboardId = dashboards[dashboardIndex].dashboardId;
		dashboardName = dashboards[dashboardIndex].dashboardName;
		$('#dashboardsList').append("<li class='dashboardListItem' id='dashboardItem" + dashboardId + "' dashboardId='" + dashboardId + "' onclick='selectDashboard()'>" + dashboardName + "</li>");
	}
}

/**
 * Acciones correspondientes al seleccionar un determinado dashboard
 * 
 * @returns {undefined}
 */
function selectDashboard() {
	var dashboardId = getSelectedDashboardId();
	if (dashboardId !== null) {
		// Habilita botones
		$("#buttonDeleteDashboard").prop("disabled", false);
		$("#buttonEditDashboard").prop("disabled", false);
		$("#buttonAddDashboardComponent").prop("disabled", false);
		// Limpia el panel de components
		resetDashboardComponentsContainer();
		// Carga los components
		loadDashboardComponents(dashboardId);
	}
}

/**
 * Inicializa el panel de dashboard components
 * 
 * @returns {undefined}
 */
function resetDashboardComponentsContainer() {
	$('#dashboardComponentsContainer').empty();
	$("#buttonAddDashboardComponent").prop("disabled", false);
}

/**
 * Carga los componentes de un dashboard determinado
 * 
 * @param dashboardId
 * @returns {undefined}
 */
function loadDashboardComponents(dashboardId) {
	var dashboard = getDashboard(dashboardId);

	if (dashboard !== null && dashboard !== undefined) {
		if (dashboard.components !== null && dashboard.components !== undefined && dashboard.components.length > 0) {
			var templateId;
			var templateInstanceId;
			var templateInstanceName;
			var index;
			for ( var componentIndex in dashboard.components) {
				// Obtiene los valores de la template
				templateId = dashboard.components[componentIndex].templateId;
				templateInstanceId = dashboard.components[componentIndex].templateInstanceId;
				templateInstanceName = dashboard.components[componentIndex].templateInstanceName;
				// Agrega el componente al dashboard
				index = $('#dashboardComponentsContainer').children().length + 1;
				$('#dashboardComponentsContainer')
						.append(
								"<div id='dashboardComponent" + index + "' class='dashboardComponent col-xs-6 col-md-5' templateId='" + templateId + "' templateInstanceId='" + templateInstanceId + "'><p class='dashboardComponentButton' data-toggle='modal' onclick='showDeleteDashboardComponent(" + index + ")' title='Delete selected Dashboard Component' index='" + index + "'></p><p class='dashboardComponentTitle'>" + templateInstanceName + "</p><div id='dashboardComponentChart" + index
										+ "'></div></div>");
				// Ejecuta el template
				executeTemplate(templateId, templateInstanceId, index);
			}
		}
	}
}

/**
 * Crea un nuevo dashboard component
 * 
 * @returns {undefined}
 */
function showAddDashboardComponent() {
	// Obtiene todos los templates disponibles a agregar
	var availableComponents = getUserAvailableDashboardComponents();
	var templateId;
	var templateName;
	var templateInstanceId;
	var templateInstanceName;
	$('#comboAddDashboardComponent').empty();
	for ( var reportIndex in availableComponents.reports) {
		// Obtiene el id & name de template
		templateId = availableComponents.reports[reportIndex].templateId;
		templateName = availableComponents.reports[reportIndex].templateName;
		if (templateId !== null && templateId !== undefined && templateName !== null && templateName !== undefined) {
			for ( var reportInstancesIndex in availableComponents.reports[reportIndex].reportInstances) {
				// Obtiene el id & name de la instancia de template
				templateInstanceId = availableComponents.reports[reportIndex].reportInstances[reportInstancesIndex].templateInstanceId;
				templateInstanceName = availableComponents.reports[reportIndex].reportInstances[reportInstancesIndex].templateInstanceName;
				$('#comboAddDashboardComponent').append("<option templateId='" + templateId + "' templateInstanceId='" + templateInstanceId + "' templateInstanceName='" + templateInstanceName + "'>" + templateName + " - " + templateInstanceName + "</option>");
			}
		}
	}
	$('#addDashboardComponent').modal();
}

function addDashboardComponent() {
	var dashboardId = getSelectedDashboardId();
	var selectedComponent = $('#comboAddDashboardComponent :selected');
	var templateId = selectedComponent.attr('templateId');
	var templateInstanceId = selectedComponent.attr('templateInstanceId');
	var templateInstanceName = selectedComponent.attr('templateInstanceName');
	var index;
	// LLAMADA AJAX
	var vecParams = {};
	vecParams['dashboardId'] = dashboardId;
	vecParams['templateId'] = templateId;
	vecParams['templateInstanceId'] = templateInstanceId;
	vecParams['templateInstanceName'] = templateInstanceName;
	$.ajax({
		type : "post",
		dataType : "json",
		data : vecParams,
		async : false,
		url : urlAPIPrefix + "/dashboard/createDashboardComponent",
		success : function(data) {
			// Agrega el componente al dashboard
			index = $('#dashboardComponentsContainer').children().length + 1;
			$('#dashboardComponentsContainer').append(
					"<div id='dashboardComponent" + index + "' class='dashboardComponent col-xs-6 col-md-5' templateId='" + templateId + "' templateInstanceId='" + templateInstanceId + "'><p class='dashboardComponentButton' data-toggle='modal' onclick='showDeleteDashboardComponent(" + index + ")' title='Delete selected Dashboard' index='" + index + "'></p><p class='dashboardComponentTitle'>" + templateInstanceName + "</p><div id='dashboardComponentChart" + index + "'></div></div>");
			// Ejecuta template
			executeTemplate(templateId, templateInstanceId, index);
			// Cierra el dialog
			$('#addDashboardComponent').modal('hide');
		},
		error : function(response) {
			var str = response.responseText + "";
			var pos = str.lastIndexOf("Login");
			if (pos !== -1) {
				window.location = window.location.href;
			}
		}
	});
}

var deleteComponentIndex;

function showDeleteDashboardComponent(dashboardComponentChartIndex) {
	deleteComponentIndex = dashboardComponentChartIndex;
	$('#deleteDashboardComponent').modal();
}

/**
 * Elimina el dashboard component correspondiente
 * 
 * @returns {undefined}
 */
function deleteDashboardComponent() {
	var dashboardId = getSelectedDashboardId();
	var templateId = $('#dashboardComponent' + deleteComponentIndex).attr('templateId');
	var templateInstanceId = $('#dashboardComponent' + deleteComponentIndex).attr('templateInstanceId');
	// LLAMADA AJAX
	var vecParams = {};
	vecParams['dashboardId'] = dashboardId;
	vecParams['templateId'] = templateId;
	vecParams['templateInstanceId'] = templateInstanceId;
	$.ajax({
		type : "post",
		dataType : "json",
		data : vecParams,
		async : false,
		url : urlAPIPrefix + "/dashboard/deleteDashboardComponent",
		success : function(data) {
			$('#deleteDashboardComponent').modal('hide');
			$('#dashboardComponent' + deleteComponentIndex).remove();
		},
		error : function(response) {
			var str = response.responseText + "";
			var pos = str.lastIndexOf("Login");
			if (pos !== -1) {
				window.location = window.location.href;
			}
		}
	});
}

/**
 * 
 * @param {type}
 *            templateId
 * @param {type}
 *            templateInstanceId
 * @param {type}
 *            index
 * @returns {undefined}
 */
function executeTemplate(templateId, templateInstanceId, index) {
	// LLAMADA AJAX
	var vecParams = {};
	vecParams['templateId'] = templateId;
	vecParams['templateInstanceId'] = templateInstanceId;
	$.ajax({
		type : "post",
		dataType : "json",
		data : vecParams,
		async : false,
		url : urlAPIPrefix + "/dashboard/executeDashboardComponent",
		success : function(data) {
			var parsedResult = parseResult(data);
			// Borra el titulo del chart porque esta en el component
			parsedResult.title = '';
			// Llama a ejecutar highcharts
			$(function() {
				Highcharts.setOptions({
					global : {
						timezoneOffset : TIMEZONE_OFFSET,
						useUTC : false
					}
				});
				$('#dashboardComponentChart' + index).highcharts(parsedResult);
			});
		},
		error : function(response) {
			var str = response.responseText + "";
			var pos = str.lastIndexOf("Login");
			if (pos !== -1) {
				window.location = window.location.href;
			}
		}
	});
}

function getUserAvailableDashboardComponents() {
	var dashboardId = getSelectedDashboardId();
	// LLAMADA AJAX PARA OBTENER LOS REPORTS GUARDADOS DEL USUARIO
	var vecParams = {};
	vecParams['dashboardId'] = dashboardId;
	var resultado = [];
	$.ajax({
		type : "get",
		dataType : "json",
		data : vecParams,
		async : false,
		url : urlAPIPrefix + "/dashboard/getUserAvailableDashboardComponents",
		success : function(data) {
			resultado = data;
		},
		error : function(response) {
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
 * Acciones que realiza al cerrar el dialogo de Add Dashboard
 */
function closeAddDashboard() {
	$('#addDashboardName').val('');
}

/**
 * Acciones que realiza al cerrar el dialogo de Edit Dashboard
 */
function closeEditDashboard() {
	$('#editDashboardName').val('');
}

/**
 * Acciones que realiza al cerrar el dialogo de Add Dashboard Component
 */
function closeAddDashboardComponent() {

}

/**
 * Acciones que realiza al cerrar el dialogo de Delete Dashboard Component
 */
function closeDeleteDashboardComponent() {
	deleteComponentIndex = null;
}