// Variables globales
var urlAPIPrefix;
var granularity = "DAILY";
var siteId = null;
var siteName = null;
var siteIndex = null;

// READY
$(document).ready(function() {
	urlAPIPrefix = "/ajax";

	// Carga lista de servicios
	loadServices();
	// Selecciona el primero de la lista
	selectFirstService();
	// Carga los 3 paneles con el servicio seleccionado
	loadServicePanels();
});

/**
 * Carga el listado de servicios disponibles desde backend
 */
function loadServices() {
	$.ajax({
		type : "get",
		async : false,
		url : urlAPIPrefix + "/getDashboardQoEServices",
		success : function(data) {
			if (data !== undefined && data !== null && data.toString().trim().length > 0) {
				$.each(data, function(key, value) {
					$('#servicesSelect').append($("<option onclick='loadServicePanels()'> < /option>").attr("value", value.id).text(value.name));
				});
			} else {
				$("#servicesSelect").hide();
				$("#panelServices").append("<a class='list-group-item'> <i class='fa fa-exclamation-circle fa-fw'></i> Not Available <span class='pull-right text-muted small'><em></em> </span></a> ");
			}
		}
	});
}

/**
 * Selecciona el primer servicio de la lista
 */
function selectFirstService() {
	$("#servicesSelect").val($("#servicesSelect option:first").val());
}

function getSelectedServiceIds() {
	var selectedServiceId = $("#servicesSelect option:selected").val();
	var serviceIds = [];
	serviceIds.push(parseInt(selectedServiceId));
	return serviceIds;
}

/**
 * Setea el site seleccionado
 */
function setSiteSelected(selectedSiteId, selectedSiteName, selectedIndex) {
	siteId = selectedSiteId;
	siteName = selectedSiteName;
	siteIndex = selectedIndex;
	loadServicePanels();
}

/**
 * Carga los 3 paneles con el servicio seleccionado
 */
function loadServicePanels() {
	// Consulta la info correspondiente
	var params = {};
	params.granularity = granularity;
	params.servicesIds = getSelectedServiceIds();
	params.byPackage = false;
	params.siteId = siteId;
	var vecParams = {};
	vecParams['dashboardQoEParams'] = JSON.stringify(params);
	$.ajax({
		type : "post",
		async : false,
		data : vecParams,
		url : urlAPIPrefix + "/getDashboardQoE",
		success : function(data) {
			// Procesa la informacion recibida
			loadAverageBandwidthScore(data);
			loadServiceActiveSubscribers(data);
			loadServiceActiveSubscribersTrend(data);
		}
	});
}

function loadAverageBandwidthScore(data) {
	var infoAvailable = false;
	$("#avgBandwidthPanelGlobal").empty();
	$("#avgBandwidthPanel").empty();
	if (hasInfo(data)) {
		if (hasInfo(data.global.rate)) {
			var max = data.global.rate;
		} else {
			var max = 0;
		}

		var pct = 0;
		var trendColorStyle;
		var trendArrowStyle;
		// Carga el panel con la info Especifica
		if (hasInfo(data.elements)) {
			$.each(data.elements, function(i, item) {
				if (data.elements[i].rate > max) {
					max = data.elements[i].rate;
				}
				pct = (data.elements[i].rate / max) * 100;
				trendColorStyle = obtainTrendColorStyle(data.elements[i].trend);
				trendArrowStyle = obtainTrendArrowStyle(data.elements[i].trend);
				$("#avgBandwidthPanel").append(
						"<div class='row-fluid'> <div class='span12'> <table style='width: 100%;margin:0 auto;'> <tr> <td style='width: 100%;'> <span class='label label-default'>" + data.elements[i].name + "</span> <div class='progress' style='background-color:#d0d0d0;font-weight: bold;cursor:pointer'> <div class='progress-bar' onclick='setSiteSelected(" + data.elements[i].id + ",\"" + data.elements[i].name + "\"," + i + ")' style='width: " + pct + "% '>" + data.elements[i].rate
								+ "</div> </div> </td> <td style='width: 15%;padding-left:3px;'> <span class='badge " + trendArrowStyle + "' style='" + trendColorStyle + "'>" + data.elements[i].trend + "</span> </td> </tr> </table> </div> </div>");
			});
		}
		// Carga el panel con la info Global
		if (hasInfo(data.global) && hasInfo(data.global.name) && hasInfo(data.global.rate) && hasInfo(data.global.trend)) {
			pct = (data.global.rate / max) * 100;
			trendColorStyle = obtainTrendColorStyle(data.global.trend);
			trendArrowStyle = obtainTrendArrowStyle(data.global.trend);
			$("#avgBandwidthPanelGlobal").append(
					"<div class='row-fluid'> <div class='span12'> <table style='width: 100%;margin:0 auto;'> <tr> <td style='width: 100%;'> <span class='label label-default'>" + data.global.name + "</span> <div class='progress' style='background-color:#d0d0d0;font-weight: bold;cursor:pointer'> <div class='progress-bar'  onclick='setSiteSelected(" + null + ",\"" + data.global.name + "\")'style='background-color:darkcyan; width: " + pct + "% '>" + data.global.rate
							+ "</div> </div> </td> <td style='width: 15%;padding-left:3px;'> <span class='badge " + trendArrowStyle + "' style='" + trendColorStyle + "'>" + data.global.trend + "</span> </td> </tr> </table> </div> </div>");
			infoAvailable = true;
		}
	}
	if (!infoAvailable) {
		$("#avgBandwidthPanelGlobal").append("<a class='list-group-item'> <i class='fa fa-exclamation-circle fa-fw'></i> Not Available <span class='pull-right text-muted small'><em></em> </span></a> ");
	}
}

function obtainTrendColorStyle(trendValue) {
	var trendColorStyle = "";
	var trendArrow = "";
	if (trendValue > 0) {
		trendColorStyle += "background-color: green;";
		trendArrow += ""
	} else if (trendValue < 0) {
		trendColorStyle += "background-color: red;";
	}
	return trendColorStyle;
}

function obtainTrendArrowStyle(trendValue) {
	var trendArrowStyle = "";
	if (trendValue > 0) {
		trendArrowStyle += "glyphicon glyphicon-arrow-up";
	} else if (trendValue < 0) {
		trendArrowStyle += "glyphicon glyphicon-arrow-down";
	}
	return trendArrowStyle;
}

function loadServiceActiveSubscribers(data) {
	if (hasInfo(data) && hasInfo(data.global)) {
		$('#alertNotAvailableCircles').hide();

		if (hasInfo(data)) {
			var dataGlobal;
			if (siteId === null) {
				// Carga Donut Global
				dataGlobal = data.global;
				loadDonutGlobal(dataGlobal);
				// Esconde Donut de Site
				$('#labelHUB').hide();
				$('#donut-right').empty();
			} else {
				// Carga Donut Global
				dataGlobal = data.elements[siteIndex];
				loadDonutGlobal(dataGlobal);
				// Carga Donut de Site
				dataGlobal = data.elements[siteIndex];
				loadDonutSite(dataGlobal);
			}
		} else {
			showServiceActiveSubscribersNotAvailableAlert();
		}
	}
}

function loadDonutGlobal(dataGlobal) {
	if (hasInfo(dataGlobal)) {
		$('#labelGlobal').hide();
		$('#donut-left').empty();
		if (hasInfo(dataGlobal) && hasInfo(dataGlobal.global)) {
			var donutGlobal = [ {
				label : dataGlobal.global.items[0].name,
				value : dataGlobal.global.items[0].value
			}, {
				label : dataGlobal.global.items[1].name,
				value : dataGlobal.global.items[1].value
			} ];
			Morris.Donut({
				element : 'donut-left',
				resize : true,
				data : donutGlobal,
				colors : [ 'orange', 'purple', 'red', 'yellow' ]
			});
			$('#labelGlobal').show();
			$('#donut-left').show();
		} else {
			showServiceActiveSubscribersNotAvailableAlert();
		}
	} else {
		showServiceActiveSubscribersNotAvailableAlert();
	}
}

function loadDonutSite(dataGlobal) {
	if (hasInfo(dataGlobal)) {
		var siteName = dataGlobal.name;
		$('#donut-right').empty();
		$('#labelHUB').show();
		$('#labelHUB').text(siteName);
		if (hasInfo(dataGlobal.specific)) {
			var donutSpecific = [ {
				label : dataGlobal.specific.items[0].name,
				value : dataGlobal.specific.items[0].value
			}, {
				label : dataGlobal.specific.items[1].name,
				value : dataGlobal.specific.items[1].value
			} ];
			Morris.Donut({
				element : 'donut-right',
				resize : true,
				data : donutSpecific,
				colors : [ 'green', 'navy', 'olive' ]
			});
			$('#donut-right').show();
		} else {
			showServiceActiveSubscribersNotAvailableAlert();
		}
	} else {
		showServiceActiveSubscribersNotAvailableAlert();
	}
}

function showServiceActiveSubscribersNotAvailableAlert() {
	$('#donut-left').hide();
	$('#donut-right').hide();
	$('#labelGlobal').hide();
	$('#labelHUB').hide();
	$('#alertNotAvailableCircles').show();
}

function loadServiceActiveSubscribersTrend(data) {
	$('#alertNotAvailableChart').hide();
	$('#line-chart').empty();

	if (hasInfo(data)) {
		if (siteId === null) {
			if (hasInfo(data.global)) {
				areaChart(data.global.chart, granularity);
			} else {
				$('#alertNotAvailableChart').show();
			}
		} else {
			if (hasInfo(data.elements) && hasInfo(data.elements[siteIndex])) {
				areaChart(data.elements[siteIndex].chart, granularity);
			} else {
				$('#alertNotAvailableChart').show();
			}
		}
	} else {
		$('#alertNotAvailableChart').show();
	}
}

function areaChart(data) {
	var dataChart = [];

	if (hasInfo(data)) {
		$.each(data.items, function(i, item) {
			var d = new Date(data.items[i].timestamp * 1000);
			dataChart.push({
				"day" : d.getTime(),
				"subscribers" : data.items[i].value
			});
		});

		Morris.Line({
			element : 'line-chart',
			data : dataChart,
			xkey : 'day',
			ykeys : [ 'subscribers' ],
			parseTime : true,
			labels : [ 'Active Subscribers' ],
			trendLine : true,
			trendLineWidth : 2,
			trendLineColors : [ 'red', 'yellow', 'purple' ],
			lineColors : [ 'blue', 'green', 'navy', 'olive', 'orange', 'purple', 'red', 'yellow' ],
			hideHover : 'auto',
			resize : true

		});
	} else {
		$('#alertNotAvailableChart').show();
	}
}

/**
 * Indica si tiene info
 */
function hasInfo(value) {
	return (value !== undefined && value !== null && value.toString().trim().length > 0);
}