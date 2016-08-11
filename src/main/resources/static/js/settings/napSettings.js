var urlAPIPrefix;

$(document).ready(function() {
	console.log("WILDE");
	urlAPIPrefix = "/ajax";
	$("#urlAPIPrefix").val(urlAPIPrefix);
	$('.nav-tabs a').click(function() {
		$(this).tab('show');
		if (this !== "#RTAConfig" && this !== "#rtaGeneralConfig" && this !== "#rtaAdvancedConfig") {
			$("#RTAConfig-Actions").hide();
		}
	});

	getNapConfiguration();

	$('#cmtss tbody').on('click', 'tr', function() {
		$("#cmtss .selected").each(function() {
			$(this).removeClass('selected');
		});
		$(this).toggleClass('selected');
	});
	$('#CSVTable tbody').on('click', 'tr', function() {
		$("#CSVTable .selected").each(function() {
			$(this).removeClass('selected');
		});
		$(this).toggleClass('selected');
	});
	$('#scabbVersions tbody').on('click', 'tr', function() {
		$("#scabbVersions .selected").each(function() {
			$(this).removeClass('selected');
		});
		$(this).toggleClass('selected');
	});
	$('#tableInterestConversionList tbody').on('click', 'tr', function() {
		$("#tableInterestConversionList .selected").each(function() {
			$(this).removeClass('selected');
		});
		$(this).toggleClass('selected');
	});
	$('#CounterConsumptionConfiguration tbody').on('click', 'tr', function() {
		$("#CounterConsumptionConfiguration .selected").each(function() {
			$(this).removeClass('selected');
		});
		$(this).toggleClass('selected');
	});
	$('#tableCategories tbody').on('click', 'tr', function() {
		$("#tableCategories .selected").each(function() {
			$(this).removeClass('selected');
		});
		$(this).toggleClass('selected');
	});
	$('#tableConditionElement tbody').on('click', 'tr', function() {
		$("#tableConditionElement .selected").each(function() {
			$(this).removeClass('selected');
		});
		$(this).toggleClass('selected');
	});
	$('#tableURLHits tbody').on('click', 'tr', function() {
		$("#tableURLHits .selected").each(function() {
			$(this).removeClass('selected');
		});
		$(this).toggleClass('selected');
	});
	$('#tableCounters tbody').on('click', 'tr', function() {
		$("#tableCounters .selected").each(function() {
			$(this).removeClass('selected');
		});
		$(this).toggleClass('selected');
	});
	$('#tableCounterConsumptionConfiguration tbody').on('click', 'tr', function() {
		$("#tableCounterConsumptionConfiguration .selected").each(function() {
			$(this).removeClass('selected');
		});
		$(this).toggleClass('selected');
	});
});
function addRTAAdapter() {
	addFormRTA();
	$('#modalTitleForSelect').text("Add RTA Adapter");
	var buttonActions = "";
	buttonActions += "<button type='button' class='btn btn-success' data-dismiss='modal' onclick='newRTAAdapter();'>Ok</button>";
	buttonActions += "<button type='button' class='btn btn-default' data-dismiss='modal'>Cancel</button>";
	$('#buttonActions').html(buttonActions);
	$('#typeForSelect').val("selectRTAConfig");
	compareValue("selectRTAConfig", "RTAAdapterDetailRecordType");
	$('#modalForSelect').modal({
		backdrop : "static"
	});
	selectTypeOfRTA();
}
function addFormRTA() {
	var form = "";
	form += "<div class='form-group form-group-custom'>";
	form += "<label class='control-label col-sm-4'>RTA Adapter:</label>";
	form += "<div class='col-xs-8'>";
	form += "<select id='RTAAdapterDetailRecordType' onchange='selectTypeOfRTA();' class='form-control' name='AddAllowedSelectModal'>";
	form += "<option value='CDR_AD_INJECTION_AMOUNT'>CDR_AD_INJECTION_AMOUNT</option>";
	form += "<option value='CDR_DEVICE_COUNT_CMTS'>CDR_DEVICE_COUNT_CMTS</option>";
	form += "<option value='CDR_DEVICE_COUNT_SCE'>CDR_DEVICE_COUNT_SCE</option>";
	form += "<option value='CDR_HEAVY_CONSUMPTION'>CDR_HEAVY_CONSUMPTION</option>";
	form += "<option value='CDR_SUBSCRIBER_URL_HITS_BY_SCE'>CDR_SUBSCRIBER_URL_HITS_BY_SCE</option>";
	form += "<option value='CDR_TOP_CATEGORIES'>CDR_TOP_CATEGORIES</option>";
	form += "<option value='CDR_TOP_DEVICES'>CDR_TOP_DEVICES</option>";
	form += "<option value='CDR_TOP_HOSTS_BY_CATEGORY'>CDR_TOP_HOSTS_BY_CATEGORY</option>";
	form += "<option value='CDR_UNIQUE_SUBSCRIBERS'>CDR_UNIQUE_SUBSCRIBERS</option>";
	form += "<option value='CDR_CATEGORIZED_TRANSACTION'>CDR_CATEGORIZED_TRANSACTION</option>";
	form += "<option value='CDR_VIDEO_QOE_FROM_HTUR_BY_CMTS'>CDR_VIDEO_QOE_FROM_HTUR_BY_CMTS</option>";
	form += "<option value='CDR_VIDEO_QOE_FROM_HTUR_BY_SCE'>CDR_VIDEO_QOE_FROM_HTUR_BY_SCE</option>";
	form += "<option value='CDR_VIDEO_QOE_FROM_SUR_BY_CMTS'>CDR_VIDEO_QOE_FROM_SUR_BY_CMTS</option>";
	form += "<option value='CDR_VIDEO_QOE_FROM_SUR_BY_SCE'>CDR_VIDEO_QOE_FROM_SUR_BY_SCE</option>";
	form += "<option value='CDR_VIDEO_QOE_FROM_VTUR_BY_CMTS'>CDR_VIDEO_QOE_FROM_VTUR_BY_CMTS</option>";
	form += "<option value='CDR_VIDEO_QOE_FROM_VTUR_BY_SCE'>CDR_VIDEO_QOE_FROM_VTUR_BY_SCE</option>";
	form += "<option value='CDR_CATEGORY_HITS_BY_SUBSCRIBER'>CDR_CATEGORY_HITS_BY_SUBSCRIBER</option>";
	form += "<option value='CDR_USER_AGENT'>CDR_USER_AGENT</option>";
	form += "<option value='CDR_FLAVOR_MATCH_COUNT'>CDR_FLAVOR_MATCH_COUNT</option>";
	form += "</select></div></div>";
	form += "<div class='form-group form-group-custom'>";
	form += "<label class='control-label col-sm-4'>Type:</label>";
	form += "<div class='col-xs-8'>";
	form += "<select id='RTAAdapterConfigurationType'class='form-control cursorDefault' name='AddAllowedSelectModal' disabled>";
	form += "<option value='RTAAdapterCategoryConfiguration'>RTAAdapterCategoryConfiguration</option>";
	form += "<option value='RTAAdapterCategoryHitsConfiguration'>RTAAdapterCategoryHitsConfiguration</option>";
	form += "<option value='RTAAdapterDefaultConfiguration'>RTAAdapterDefaultConfiguration</option>";
	form += "<option value='RTAAdapterDeviceConfiguration'>RTAAdapterDeviceConfiguration</option>";
	form += "<option value='RTAAdapterHeavyConsumptionConfiguration'>RTAAdapterHeavyConsumptionConfiguration</option>";
	form += "<option value='RTAAdapterTopperConfiguration'>RTAAdapterTopperConfiguration</option>";
	form += "<option value='RTAAdapterURLCategoryConsumptionConfiguration'>RTAAdapterURLCategoryConsumptionConfiguration</option>";
	form += "<option value='RTAAdapterURLHitsConfiguration'>RTAAdapterURLHitsConfiguration</option>";
	form += "<option value='RTAAdapterUserAgentConfiguration'>RTAAdapterUserAgentConfiguration</option>";
	form += "<option value='RTAAdapterFlavorUpdaterConfiguration'>RTAAdapterFlavorUpdaterConfiguration</option>";
	form += "</select></div></div>";
	$('#divModalForSelect').html(form);
}
function selectTypeOfRTA() {
	var type = $('#RTAAdapterDetailRecordType').val() + "";
	if (type === "CDR_AD_INJECTION_AMOUNT") {
		$('#RTAAdapterConfigurationType').val("RTAAdapterDefaultConfiguration");
	} else if (type === "CDR_DEVICE_COUNT_CMTS") {
		$('#RTAAdapterConfigurationType').val("RTAAdapterDeviceConfiguration");
	} else if (type === "CDR_DEVICE_COUNT_SCE") {
		$('#RTAAdapterConfigurationType').val("RTAAdapterDeviceConfiguration");
	} else if (type === "CDR_HEAVY_CONSUMPTION") {
		$('#RTAAdapterConfigurationType').val("RTAAdapterHeavyConsumptionConfiguration");
	} else if (type === "CDR_SUBSCRIBER_URL_HITS_BY_SCE") {
		$('#RTAAdapterConfigurationType').val("RTAAdapterURLHitsConfiguration");
	} else if (type === "CDR_TOP_CATEGORIES") {
		$('#RTAAdapterConfigurationType').val("RTAAdapterTopperConfiguration");
	} else if (type === "CDR_TOP_DEVICES") {
		$('#RTAAdapterConfigurationType').val("RTAAdapterTopperConfiguration");
	} else if (type === "CDR_TOP_HOSTS_BY_CATEGORY") {
		$('#RTAAdapterConfigurationType').val("RTAAdapterTopperConfiguration");
	} else if (type === "CDR_UNIQUE_SUBSCRIBERS") {
		$('#RTAAdapterConfigurationType').val("RTAAdapterDefaultConfiguration");
	} else if (type === "CDR_CATEGORIZED_TRANSACTION") {
		$('#RTAAdapterConfigurationType').val("RTAAdapterURLCategoryConsumptionConfiguration");
	} else if (type === "CDR_VIDEO_QOE_FROM_HTUR_BY_CMTS") {
		$('#RTAAdapterConfigurationType').val("RTAAdapterCategoryConfiguration");
	} else if (type === "CDR_VIDEO_QOE_FROM_HTUR_BY_SCE") {
		$('#RTAAdapterConfigurationType').val("RTAAdapterCategoryConfiguration");
	} else if (type === "CDR_VIDEO_QOE_FROM_SUR_BY_CMTS") {
		$('#RTAAdapterConfigurationType').val("RTAAdapterCategoryConfiguration");
	} else if (type === "CDR_VIDEO_QOE_FROM_SUR_BY_SCE") {
		$('#RTAAdapterConfigurationType').val("RTAAdapterCategoryConfiguration");
	} else if (type === "CDR_VIDEO_QOE_FROM_VTUR_BY_CMTS") {
		$('#RTAAdapterConfigurationType').val("RTAAdapterCategoryConfiguration");
	} else if (type === "CDR_CATEGORY_HITS_BY_SUBSCRIBER") {
		$('#RTAAdapterConfigurationType').val("RTAAdapterCategoryHitsConfiguration");
	} else if (type === "CDR_USER_AGENT") {
		$('#RTAAdapterConfigurationType').val("RTAAdapterUserAgentConfiguration");
	} else if (type === "CDR_FLAVOR_MATCH_COUNT") {
		$('#RTAAdapterConfigurationType').val("RTAAdapterFlavorUpdaterConfiguration");
	}
}
// //////////////////// Demo de botones save cancel ////////////////////////
function editRDRSettings() {
	showEdit('FormRDRSettings', '#actions-RDR', '#AllowedTypeActions', '#SCABBVersion', '', '', 'buttonCancelEditRDR', '');
}
function editIPDRSettings() {
	showEdit('FormIPDRSettings', '#action-IPDRSettings', '#CMTSActions', '', '', '', 'cancelEdit-IPDR', '');
}
function editRTAS() {
	showEdit('FormCSVManager', '#action-CSV', '#PYCActions', '#CSVActions', '#DBActions', '#RTAactions', 'cancelEdit-CSV', '');
}
function editDBManager() {
	showEdit('FormDBManager', '#DBManager-Actions', '', '', '', '', 'CancelEditDBManager', '');
}
function editOthers() {
	showEdit('FormOthers', '#Others-Actions', '', '', '', '', 'CancelEditOthers', '');
}
function editRTAConfig(typePanel) {
	if (typePanel === "RTAAdapterCategoryHitsConfiguration") {
		showEdit('FormRTAConfig', '#RTAConfig-Actions', '#RTAactionsSources', '#RTAactionsCategories', '#divInterestConversionKey', '', 'CancelEditRTAConfig', '');
	} else if (typePanel === "RTAAdapterCategoryConfiguration") {
		showEdit('FormRTAConfig', '#RTAConfig-Actions', '#RTAactionsSources', '#divServices', '#divCategories', '#divConditionElements', 'CancelEditRTAConfig', '#divConditionElement');
	} else if (typePanel === "RTAAdapterURLHitsConfiguration") {
		showEdit('FormRTAConfig', '#RTAConfig-Actions', '#RTAactionsSources', '#divRTAactionsPackageIdsConsumptionConfiguration', '#RTAactionsURLHits', '#divURLHit', 'CancelEditRTAConfig', '');
	} else if (typePanel === "RTAAdapterHeavyConsumptionConfiguration") {
		showEdit('FormRTAConfig', '#RTAConfig-Actions', '#RTAactionsSources', '#divCounterConsumptionConfiguration', '#divAllowedPackageIdsConsumptionConfiguration', '#divConsumptionConfiguration', 'CancelEditRTAConfig', '');
	} else if (typePanel === "RTAAdapterURLCategoryConsumptionConfiguration") {
		showEdit('FormRTAConfig', '#RTAConfig-Actions', '#RTAactionsSources', '#divAllowedPackageIds', '#divCounterConsumptionConfiguration', '', 'CancelEditRTAConfig', '');
	} else if (typePanel === "RTAAdapterFlavorUpdaterConfiguration") {
		showEdit('FormRTAConfig', '#RTAConfig-Actions', '#RTAactionsSources', '', '', '', 'CancelEditRTAConfig', '');
	} else {
		showEdit('FormRTAConfig', '#RTAConfig-Actions', '#RTAactionsSources', '', '', '', 'CancelEditRTAConfig', '');
	}
}
function editPeriodicConfig() {
	showEdit('FormPeriodicConfig', '#PeriodicConfig-Actions', '#actionsRecordTypes', '', '', '', 'CancelEditPeriodicConfig', '');
}
// Llamadas a modales
function addPYC() {
	modalAddAllowed('AllowedTagsPYC', 'Add Allowed Tags PYC', 'Allowed&nbsp;Tags&nbsp;PYC:');
}
function addCSV() {
	modalAddAllowed('AllowedTagsCSV', 'Add Allowed Tags CSV Manager', 'Allowed&nbsp;Tags&nbsp;CSV&nbsp;Manager:');
}
function addDB() {
	modalAddAllowed('AllowedTagsDBManager', 'Add Allowed Tags DB Manager', 'Allowed&nbsp;Tags&nbsp;DB&nbsp;Manager:');
}
function addRTA() {
	modalAddAllowed('AllowedTagsRTA', 'Add Allowed Tags RTA Manager', 'Allowed&nbsp;Tags&nbsp;RTA&nbsp;Manager:');
}
function addSources() {
	modalAddAllowed('selectSources', 'Add Sources', 'Sources:');
}
function addRecordTypes() {
	modalAddAllowed('selectRecordTypes', 'Add Record Types', 'Record Types:');
}
// Muestra todos los botones ocultos. Y añade la clase demo.
function showEdit(idForm, idActions, idVariable1, idVariable2, idVariable3, idVariable4, idButtonCancel, idVariable5) {
	document.getElementById(idForm).classList.add('demo');
	var button = '<input type="button" class="btn btn-default" value="Cancel" onclick="hideEdit(' + 1 + ' , ' + "'" + idForm + "'" + ' , ' + "'" + idActions + "'" + ' , ' + "'" + idVariable1 + "'" + ' , ' + "'" + idVariable2 + "'" + ' , ' + "'" + idVariable3 + "'" + ' , ' + "'" + idVariable4 + "'" + ' , ' + "'" + idVariable5 + "'" + ');" />';
	document.getElementById(idButtonCancel).innerHTML = button;
	$(idActions).show();
	$(idVariable1).show();
	$(idVariable2).show();
	$(idVariable3).show();
	$(idVariable4).show();
	$(idVariable5).show();
}

// Oculta todos los botones. Y la clase demo.
function hideEdit(id, idForm, idActions, idVariable1, idVariable2, idVariable3, idVariable4, idVariable5) {
	document.getElementById(idForm).classList.remove('demo');
	$(idActions).hide();
	$(idVariable1).hide();
	$(idVariable2).hide();
	$(idVariable3).hide();
	$(idVariable4).hide();
	$(idVariable5).hide();
	if (id !== '') {
		// loandForm(id);
	}
}

// Es para desabilitar el boton borrar en caso de que no haya nada seleccionado
function selects(select, idButtonDelete) {
	var cantSelect = 0;
	var l = select.options;
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
}

// Llamadas para add y edit de las tablas
function addCSVForTable() {
	modalForTable('CSVTable', 'New CSV Manager', 6, 'DRT,Separator,Include Headers Info,Source IP Format,TT Format,TT Pattern');
}
function editCSV() {
	modalForTableEdit('CSVTable', 'Edit CSV Manager', 6, 'DRT,Separator,Include Headers Info,Source IP Format,TT Format,TT Pattern');
}
function addSCABBVersion() {
	modalForTable('scabbVersions', 'New SCA BB Version', 2, 'IP&nbsp;Address,SCA&nbsp;BB&nbsp;Version');
}
function editSCABBVersion() {
	modalForTableEdit('scabbVersions', 'Edit SCA BB Version', 2, 'IP&nbsp;Address,SCA&nbsp;BB&nbsp;Version');
}
function addCMTS() {
	modalForTable('cmtss', 'New CMTS Manager', 4, 'IP, Port, Vendor, IP&nbsp;DR&nbsp;Enabled', 4);
}
function editCMTS() {
	modalForTableEdit('cmtss', 'Edit CMTS Manager', 4, 'IP, Port, Vendor, IP&nbsp;DR&nbsp;Enabled', 4);
}
function addInterestConversionKey() {
	modalForTable('tableInterestConversionList', 'New Interest Conversion Key', "RTAAdapterCategoryHitsConfiguration", 'Calification, Value', 0);
}
function addCounterConsumptionConfiguration() {
	modalForTable('tableCounterConsumptionConfiguration', 'New Counter Consumption Configuration', "RTAAdapterHeavyConsumptionConfiguration", 'Counter&nbsp;Id, Service&nbsp;Ids, All&nbsp;Services, Filter&nbsp;By&nbsp;Downstream, Min&nbsp;Downstream&nbsp;Allowed, Filter&nbsp;By&nbsp;Upstream, Min&nbsp;Upstream&nbsp;Allowed', 0);
}
function addURLHit() {
	modalForTable('tableURLHits', 'New URL Hit Configuration', "RTAAdapterURLHitsConfiguration", 'Source, Server&nbsp;IP, Server&nbsp;Port, Access&nbsp;String, Revolve&nbsp;Access&nbsp;String, Info&nbsp;String, Client&nbsp;IP, Client&nbsp;Port', 0);
}
function addConditionElement() {
	modalForTable('tableConditionElement', 'New Condition Element', "RTAAdapterCategoryConfiguration", 'Category&nbsp;Ids, Operator, Value', 0);
}
function addCategories() {
	modalForTable('tableCategories', 'New Category', "RTAAdapterCategoryConfiguration", 'Id, Name, Category&nbsp;Parent&nbsp;Id, Min&nbsp;Value, Max&nbsp;Value', 0);
}
function addCounterURLCategoryConsumptionConfiguration() {
	modalForTable('tableCounters', 'New Counter URL Category Consumption Configuration', "RTAAdapterURLCategoryConsumptionConfiguration", 'Counter&nbsp;Id, Category&nbsp;Ids, All&nbsp;Categories, Filter&nbsp;By&nbsp;Usage, Min&nbsp;Usage&nbsp;Allowed', 0);
}
function editInterestConversionKey() {
	modalForTableEdit2('tableInterestConversionList', 'Edit Interest Conversion Key', "RTAAdapterCategoryHitsConfiguration", 'Calification, Value');
}
function editCounterConsumptionConfiguration() {
	modalForTableEdit2('tableCounterConsumptionConfiguration', 'Edit Counter Consumption Configuration', "RTAAdapterHeavyConsumptionConfiguration", 'Counter&nbsp;Id, Service&nbsp;Ids, All&nbsp;Services, Filter&nbsp;By&nbsp;Downstream, Min&nbsp;Downstream&nbsp;Allowed, Filter&nbsp;By&nbsp;Upstream, Min&nbsp;Upstream&nbsp;Allowed');
}
function editURLHit() {
	modalForTableEdit2('tableURLHits', 'Edit URL Hit Configuration', "RTAAdapterURLHitsConfiguration", 'Source, Server&nbsp;IP, Server&nbsp;Port, Access&nbsp;String, Revolve&nbsp;Access&nbsp;String, Info&nbsp;String, Client&nbsp;IP, Client&nbsp;Port');
}
function editConditionElement() {
	modalForTableEdit2('tableConditionElement', 'Edit Condition Element', "RTAAdapterCategoryConfiguration", 'Category&nbsp;Ids, Operator, Value');
}
function editCategories() {
	modalForTableEdit2('tableCategories', 'Edit Category', "RTAAdapterCategoryConfiguration", 'Id, Name, Category&nbsp;Parent&nbsp;Id, Min&nbsp;Value, Max&nbsp;Value');
}
function editCounterURLCategoryConsumptionConfiguration() {
	modalForTableEdit2('tableCounters', 'Edit Counter URL Category Consumption Configuration', "RTAAdapterURLCategoryConsumptionConfiguration", 'Counter&nbsp;Id, Category&nbsp;Ids, All&nbsp;Categories, Filter&nbsp;By&nbsp;Usage, Min&nbsp;Usage&nbsp;Allowed');
}
// ////////////////////////// Manejo de modales ///////////////////////////////////////
// modal dinamico para Add de todos los selects. Variables necesarias: el id del select, el titulo del modal y el label del input.
function modalAddAllowed(idSelect, title, label) {
	$('#AddAllowedSelectModal').html("");
	$('#DetailRecordType option').clone().appendTo('#AddAllowedSelectModal');
	$('#modalTitle').text(title);
	$('#labelControl').text(label);
	$('#typeAddAllowed').val(idSelect);
	compareValue(idSelect, "AddAllowedSelectModal");
	$('#modalAddAllowed').modal({
		backdrop : "static"
	});
}

// Funcion para igualar los selects y no repetir variables.
function compareValue(idSelect, idSelectModal) {
	var selectVariable = document.getElementById(idSelect);
	var y = selectVariable.length;
	for (var i = 0; i < y; i++) {
		var variableSelect = selectVariable.options[i];
		var idjQuery = "#" + idSelectModal + " option[value='" + variableSelect.value + "']";
		$(idjQuery).each(function() {
			$(this).remove();
		});
	}
}

// La variable: "idSelect" tiene que llegar con #
function modalAddSelect(idSelect, title, label) {
	$('#typeAddSelect').val(idSelect);
	$('#modalTitleSelect').text(title);
	$('#labelControlSelect').text(label);
	$('#modalAddSelect').modal({
		backdrop : "static"
	});
}
function modalAddSelect2(idSelect, title, label) {
	$('#typeAddSelect2').val(idSelect);
	$('#modalTitleSelect2').text(title);
	$('#labelControlSelect2').text(label);
	$('#modalAddSelect2').modal({
		backdrop : false
	});
}

// elimina la variable.
function deleteVariableSelecTableModal(variableSelect) {
	$(".selectTable option[value='" + variableSelect + "']").each(function() {
		$(this).remove();
	});
}

// modal dinamico para Delete de todos los select. Variables: id del select y el id del boton remover.
function modalDeleteAllowed(idAllowed, idButtonRemove) {
	$('#typeDeleteAllowed').val(idAllowed);
	$('#idButtonRemove').val(idButtonRemove);
	$('#modalDeleteAllowed').modal({
		backdrop : "static"
	});
}
function modalDelete2(idSelect, idButtonRemove) {
	$('#typeDelete2').val(idSelect);
	$('#idButtonRemove2').val(idButtonRemove);
	$('#modalDelete2').modal({
		backdrop : false
	});
}

function modalDeleteItem(idAllowed, idButtonRemove) {
	$('#typeDeleteAllowed').val(idAllowed);
	$('#idButtonRemove').val(idButtonRemove);
	$('#modalDeleteAllowed').modal({
		backdrop : false
	});
}
// Utiliza el id del select que esta en el modal. Y añade las opciones seleccionadas al select correspondiente.
function newAddAllowed() {
	var idSelect = $('#typeAddAllowed').val();
	$('#AddAllowedSelectModal option:selected').remove().appendTo("#" + idSelect + "");
	$("#" + idSelect + "").find('option:selected').removeAttr("selected");
	$('#modalAddAllowed').modal('hide');
}

function newAddValueSelect() {
	var boolean = true;
	var idSelect = $('#typeAddSelect').val();
	var input = $('#input-AddSelect').val();
	var option = "<option value='" + input + "'>" + input + "</option>";
	if (idSelect === "#selectServices") {
		if (!/^\d+$/.test(input)) {
			boolean = false;
			$('#labelAlertSelect').show();
			alert_red("input-AddSelect");
		}
	}
	if (boolean) {
		$(idSelect).append(option);
		$('#labelAlertSelect').hide();
		$('#modalAddSelect').modal('hide');
		$('#input-AddSelect').val('');
	}
}

function newAddValueSelect2() {
	var idSelect = $('#typeAddSelect2').val();
	var input = $.trim($('#input-AddSelect2').val());
	if (input === '') {
		$('#errorInAddValue').show();
	} else if (!/^\d+$/.test(input)) {
		$('#errorInAddValue').show();
	} else {
		var option = "<option value='" + input + "'>" + input + "</option>";
		$('#errorInAddValue').hide();
		$(idSelect).append(option);
		$('#modalAddSelect2').modal('hide');
		$('#input-AddSelect2').val('');
	}
}

// Utiliza el id del select que este en el modal. Elimina las opciones seleccionadas. Y desabilita el boton eliminar hasta que seleccione otra opcion.
function deleteAllowed() {
	var idSelect = $('#typeDeleteAllowed').val();
	if (idSelect === "#selectQualityConditions") {
		var variableDelete = $("#selectQualityConditions").val();
		deleteQualityConditionInterno(variableDelete);
		$("#selectQualityConditions option[value='" + variableDelete + "']").remove();
	} else if (idSelect === "#selectGroupURLHitsConfiguration") {
		var variableDelete = $("#selectGroupURLHitsConfiguration").val();
		deleteGroupURLHitsConfigurationInterno(variableDelete);
		$("#selectGroupURLHitsConfiguration option[value='" + variableDelete + "']").remove();
	} else if (idSelect === "#selectPeriodicConfig") {
		deletePeriodicDataRecordTask();
	} else {
		idSelect += ' option:selected';
		$(idSelect).remove();
	}
	var idButtonRemove = $('#idButtonRemove').val();

	$(idButtonRemove).prop('disabled', true);
	$('#modalDeleteAllowed').modal('hide');
}
function delete2() {
	var idSelect = $('#typeDelete2').val();
	idSelect += ' option:selected';
	$(idSelect).remove();
	$('#modalDelete2').modal('hide');
}

// Modal dinamico para Tablas. Variables: el titulo del modal, el id de la tabla el numero de input y los label que llevan.
function modalForTable(idTable, title, cant, labels, enable) {
	$('#modalTitleForTable').text(title);
	var vecLabels = labels.split(",");
	var htmlForm = "";
	// Para los RTA
	if (cant === "RTAAdapterCategoryHitsConfiguration") {
		htmlForm = newFormCusttomForRTA(2, vecLabels, "RTAAdapterCategoryHitsConfiguration");
		$('#bodyForTable').html(htmlForm);
	} else if (cant === "RTAAdapterHeavyConsumptionConfiguration") {
		htmlForm = newFormCusttomForRTA(7, vecLabels, "RTAAdapterHeavyConsumptionConfiguration");
		$('#bodyForTable').html(htmlForm);
		document.getElementById("addServiceIdInModal").addEventListener("click", function() {
			modalAddSelect2('#input-table1', 'New service', 'Service Id');
		});
		document.getElementById("removeServiceIds").addEventListener("click", function() {
			modalDelete2('#input-table1', '#removeServiceIds');
		});
	} else if (cant === "RTAAdapterURLHitsConfiguration") {
		htmlForm = newFormCusttomForRTA(8, vecLabels, "RTAAdapterURLHitsConfiguration");
		$('#bodyForTable').html(htmlForm);
		$('#DetailRecordType option').clone().appendTo('#input-table0');
		selectForTableURLHits();
	} else if (cant === "RTAAdapterCategoryConfiguration" && idTable === "tableConditionElement") {
		htmlForm = newFormCusttomForRTA(3, vecLabels, "RTAAdapterCategoryConfiguration");
		$('#bodyForTable').html(htmlForm);
		document.getElementById("addCategoryIdInModal").addEventListener("click", function() {
			modalAddSelect2('#input-table0', 'New Category', 'Category Id');
		});
		document.getElementById("removeCategoryIds").addEventListener("click", function() {
			modalDelete2('#input-table0', '#removeCategoryIds');
		});
	} else if (cant === "RTAAdapterCategoryConfiguration" && idTable === "tableCategories") {
		htmlForm = newFormCusttomForRTA(5, vecLabels, "RTAAdapterCategoryConfiguration");
		$('#bodyForTable').html(htmlForm);
	} else if (cant === "RTAAdapterURLCategoryConsumptionConfiguration") {
		htmlForm = newFormCusttomForRTA(5, vecLabels, "RTAAdapterURLCategoryConsumptionConfiguration");
		$('#bodyForTable').html(htmlForm);
		document.getElementById("addCategoryIdInModal").addEventListener("click", function() {
			modalAddSelect2('#input-table1', 'New Category', 'Category Id');
		});
		document.getElementById("removeCategoryIds").addEventListener("click", function() {
			modalDelete2('#input-table1', '#removeCategoryIds');
		});
	} else {
		htmlForm = newFormCusttom(cant, vecLabels, enable - 1);
		$('#bodyForTable').html(htmlForm);
	}

	$('#isEdit').val("false");
	$('#typeModalForTable').val(idTable);
	if (cant === 6) {
		selectForTableCSV();
	}
	$('#alertForTable').hide();

	$('#modalForTable').modal({
		backdrop : "static"
	});
}
// Crea el formulario. Variables: la cantidad de input y los label de cada uno. enable en el puesto numerico del check
function newFormCusttom(cant, vecLabels, enable) {
	var form = "";
	for (var i = 0; i < cant; i++) {
		form += "<div class='form-group form-group-custom'>";
		form += "<label class='control-label col-sm-4'>" + vecLabels[i] + ":</label>";
		form += "<div class='col-xs-6'>";
		if (enable === i) {
			form += "<input id='input-table" + i + "' type='checkbox' size='17' class='checkbox' />";
		} else if (cant === 2 && i === 1) {
			form += "<select id='input-table" + i + "' class='form-control cursorDefault'><option value='v36x'>v36x</option><option value='v41x'>v41x</option></select>";
		} else if (cant === 4 && i === 2) {
			form += "<select id='input-table" + i + "' class='form-control cursorDefault'><option value='motorola'>motorola</option><option value='arris'>arris</option><option value='cisco'>cisco</option><option value='casa'>casa</option></select>";
		} else if (cant === 6 && i === 0) {
			form += "<select id='input-table" + i + "' class='form-control selectTable'></select>";
		} else if (cant === 6 && i === 2) {
			form += "<input id='input-table" + i + "' type='checkbox' size='17' class='checkbox' />";
		} else if (cant === 6 && i === 3) {
			form += "<select id='input-table" + i + "' class='form-control cursorDefault'><option value='string'>string</option><option value='number'>number</option></select>";
		} else if (cant === 6 && i === 4) {
			form += "<select id='input-table" + i + "' class='form-control cursorDefault'><option value='milliseconds'>milliseconds</option><option value='date'>date</option></select>";
		} else {
			form += "<input id='input-table" + i + "' type='text' size='17' value='' class='form-control cursorDefault' />";
		}
		form += "</div>";
		form += "</div>";
	}
	// Esta variable es para el switch segun la cantidad de rows es la tabla
	form += "<label id='input-table-cant' class='hidden'>" + cant + "</label>";
	return form;
}
// Crea el formulario. Variables: la cantidad de input y los label de cada uno.
function newFormCusttomForRTA(cant, vecLabels, type) {
	var form = "";
	for (var i = 0; i < cant; i++) {
		form += "<div class='form-group form-group-custom'>";
		form += "<label class='control-label col-sm-4'>" + vecLabels[i] + ":</label>";
		form += "<div class='col-xs-6'>";
		if (cant === 2) {
			form += "<input id='input-table" + i + "' type='text' size='17' class='form-control'/>";
		} else if (type === "RTAAdapterHeavyConsumptionConfiguration") {
			switch (i) {
			case 1:
				form += "<div class='controls2'>";
				form += "<button id='addServiceIdInModal' title='Add Service' type='button' class='btn btn-default' data-whatever='@mdo' ><img src='../img/report/add.png'></button>";
				form += "<button id='removeServiceIds' title='Delete Service' type='button' class='btn btn-default' data-whatever='@mdo' ><img src='../img/report/cross.png' ></button>";
				form += "</div>";
				form += "<select id='input-table" + i + "' multiple class='form-control'></select>";
				break;
			case 2:
				form += "<input id='input-table" + i + "' type='checkbox' size='17' class='checkbox'/>";
				break;
			case 3:
				form += "<input id='input-table" + i + "' type='checkbox' size='17' class='checkbox'/>";
				break;
			case 5:
				form += "<input id='input-table" + i + "' type='checkbox' size='17' class='checkbox'/>";
				break;
			default:
				form += "<input id='input-table" + i + "' type='text' size='17' class='form-control'/>";
				break;
			}
		} else if (type === "RTAAdapterURLHitsConfiguration") {
			switch (i) {
			case 0:
				form += "<select id='input-table" + i + "' class='form-control selectTable'></select>";
				break;
			case 4:
				form += "<input id='input-table" + i + "' type='checkbox' size='17' class='checkbox'/>";
				break;
			default:
				form += "<input id='input-table" + i + "' type='text' size='17' class='form-control'/>";
				break;
			}
		} else if (type === "RTAAdapterCategoryConfiguration" && cant === 3) {
			switch (i) {
			case 0:
				form += "<div class='controls2'>";
				form += "<button id='addCategoryIdInModal' title='Add Category' type='button' class='btn btn-default' data-whatever='@mdo' ><img src='../img/report/add.png'></button>";
				form += "<button id='removeCategoryIds' title='Delete Category' type='button' class='btn btn-default' data-whatever='@mdo' ><img src='../img/report/cross.png' ></button>";
				form += "</div>";
				form += "<select id='input-table" + i + "' multiple class='form-control'></select>";
				break;
			default:
				form += "<input id='input-table" + i + "' type='text' size='17' class='form-control'/>";
				break;
			}
		} else if (type === "RTAAdapterCategoryConfiguration" && cant === 5) {
			form += "<input id='input-table" + i + "' type='text' size='17' class='form-control'/>";
		} else if (type === "RTAAdapterURLCategoryConsumptionConfiguration") {
			switch (i) {
			case 1:
				form += "<div class='controls2'>";
				form += "<button id='addCategoryIdInModal' title='Add Category' type='button' class='btn btn-default' data-whatever='@mdo' ><img src='../img/report/add.png'></button>";
				form += "<button id='removeCategoryIds' title='Delete Category' type='button' class='btn btn-default' data-whatever='@mdo' ><img src='../img/report/cross.png' ></button>";
				form += "</div>";
				form += "<select id='input-table" + i + "' multiple class='form-control'></select>";
				break;
			case 2:
				form += "<input id='input-table" + i + "' type='checkbox' size='17' class='checkbox'/>";
				break;
			case 3:
				form += "<input id='input-table" + i + "' type='checkbox' size='17' class='checkbox'/>";
				break;
			default:
				form += "<input id='input-table" + i + "' type='text' size='17' class='form-control'/>";
				break;
			}
		}
		form += "</div>";
		form += "</div>";
	}
	// Esta variable es para el switch segun la cantidad de rows es la tabla
	form += "<label id='input-table-cant' class='hidden'>" + cant + "</label>";
	return form;
}
// Modal para las tablas. Solo para borrar filas
function modalForTableDelete(title, typeOftable) {
	// en caso de que sea la tabla de CSV la primer row no se puede eliminar (es la default)
	if (typeOftable === 6) {
		var CSVManager = $('.padding-CSV .selected td').map(function() {
			return $(this).text();
		}).get();
		if (CSVManager[0] != "*") {
			$('#modalTitleForTableDelete').text(title);
			$("#typeModalForTableDelete").val(typeOftable);
			$('#modalForTableDelete').modal({
				backdrop : "static"
			});
		}
	} else {
		$('#modalTitleForTableDelete').text(title);
		$("#typeModalForTableDelete").val(typeOftable);
		$('#modalForTableDelete').modal({
			backdrop : "static"
		});
	}
}
// Modal dinamico para editar Tablas. Variables: el titulo del modal, el id de la tabla el numero de input y los label que llevan.
function modalForTableEdit(idTable, title, cant, labels, enable) {
	$('#modalTitleForTable').text(title);
	var vecLabels = labels.split(",");
	var htmlForm = newFormCusttom(cant, vecLabels, enable - 1);
	$('#bodyForTable').html(htmlForm);
	$('#typeModalForTable').val(idTable);
	$('#isEdit').val("true");
	if (cant == 6) {
		selectForTableCSV();
		var CSVManager = $('.padding-CSV .selected td').map(function() {
			return $(this).text();
		}).get();

		var option = "<option value='" + CSVManager[0] + "'>" + CSVManager[0] + "</option>";
		if (CSVManager[0] == "*") {
			$("#input-table0").html(option);
		} else {
			$("#input-table0").append(option);
			$("#input-table0").val(CSVManager[0]);
		}
		$("#input-table1").val(CSVManager[1]);
		if (CSVManager[2] == "true") {
			$('#input-table2').prop("checked", true);
		} else {
			$('#input-table2').prop("checked", false);
		}
		$("#input-table3").val(CSVManager[3]);
		$("#input-table4").val(CSVManager[4]);
		$("#input-table5").val(CSVManager[5]);
	} else if (cant == 2) {
		var scabbVersions = $('.padding-scabbVersions .selected td').map(function() {
			return $(this).text();
		}).get();
		$('#input-table0').val(scabbVersions[0]);
		$('#input-table1').val(scabbVersions[1]);
	} else if (cant == 4) {
		var cmtss = $('.padding-cmtss .selected td').map(function() {
			return $(this).text();
		}).get();
		$('#input-table0').val(cmtss[0]);
		$('#input-table1').val(cmtss[1]);
		$('#input-table2').val(cmtss[2]);
		if (cmtss[3] == "true") {
			$('#input-table3').prop("checked", true);
		} else {
			$('#input-table3').prop("checked", false);
		}
	}
	$('#alertForTable').hide();
	$('#modalForTable').modal({
		backdrop : "static"
	});
}

// Para los RTA
function modalForTableEdit2(idTable, title, panel, labels) {
	$('#modalTitleForTable').text(title);
	var vecLabels = labels.split(",");
	var htmlForm = "";

	if (panel === "RTAAdapterCategoryHitsConfiguration") {
		htmlForm = newFormCusttomForRTA(2, vecLabels, "RTAAdapterCategoryHitsConfiguration");
		$('#bodyForTable').html(htmlForm);
		var tableInterestConversionList = $('#tableInterestConversionList .selected td').map(function() {
			return $(this).text();
		}).get();
		$("#input-table0").val(tableInterestConversionList[0]);
		$("#input-table1").val(tableInterestConversionList[1]);

	} else if (panel === "RTAAdapterHeavyConsumptionConfiguration") {
		htmlForm = newFormCusttomForRTA(7, vecLabels, "RTAAdapterHeavyConsumptionConfiguration");
		$('#bodyForTable').html(htmlForm);
		var tableCounterConsumptionConfiguration = $('#tableCounterConsumptionConfiguration .selected td').map(function() {
			return $(this).text();
		}).get();
		$("#input-table0").val(tableCounterConsumptionConfiguration[1]);
		if (tableCounterConsumptionConfiguration[2] != "") {
			var arr = tableCounterConsumptionConfiguration[2].split(",");
			for (var i = 0; i < arr.length; i++) {
				var option = "<option value='" + arr[i] + "'>" + arr[i] + "</option>";
				$("#input-table1").append(option);
			}
		}
		if (tableCounterConsumptionConfiguration[3] == "true") {
			$('#input-table2').prop("checked", true);
		} else {
			$('#input-table2').prop("checked", false);
		}
		if (tableCounterConsumptionConfiguration[4] == "true") {
			$('#input-table3').prop("checked", true);
		} else {
			$('#input-table3').prop("checked", false);
		}
		$("#input-table4").val(tableCounterConsumptionConfiguration[5]);
		if (tableCounterConsumptionConfiguration[6] == "true") {
			$('#input-table5').prop("checked", true);
		} else {
			$('#input-table5').prop("checked", false);
		}
		$("#input-table6").val(tableCounterConsumptionConfiguration[7]);
		document.getElementById("addServiceIdInModal").addEventListener("click", function() {
			modalAddSelect2('#input-table1', 'New Service', 'Service Id');
		});
		document.getElementById("removeServiceIds").addEventListener("click", function() {
			modalDelete2('#input-table1', '#removeServiceIds');
		});
	} else if (panel === "RTAAdapterURLHitsConfiguration") {
		htmlForm = newFormCusttomForRTA(8, vecLabels, "RTAAdapterURLHitsConfiguration");
		$('#bodyForTable').html(htmlForm);
		$('#DetailRecordType option').clone().appendTo('#input-table0');
		var tableURLHits = $('#tableURLHits .selected td').map(function() {
			return $(this).text();
		}).get();
		$("#input-table0").val(tableURLHits[0]);
		$("#input-table1").val(tableURLHits[1]);
		$("#input-table2").val(tableURLHits[2]);
		$("#input-table3").val(tableURLHits[3]);
		if (tableURLHits[4] == "true") {
			$('#input-table4').prop("checked", true);
		} else {
			$('#input-table4').prop("checked", false);
		}
		$("#input-table5").val(tableURLHits[5]);
		$("#input-table6").val(tableURLHits[6]);
		$("#input-table7").val(tableURLHits[7]);

	} else if (panel === "RTAAdapterCategoryConfiguration" && idTable === "tableConditionElement") {
		htmlForm = newFormCusttomForRTA(3, vecLabels, "RTAAdapterCategoryConfiguration");
		$('#bodyForTable').html(htmlForm);
		var tableConditionElement = $('#tableConditionElement .selected td').map(function() {
			return $(this).text();
		}).get();
		if (tableConditionElement[0] != "") {
			var arr = tableConditionElement[0].split(",");
			for (var i = 0; i < arr.length; i++) {
				var option = "<option value='" + arr[i] + "'>" + arr[i] + "</option>";
				$("#input-table0").append(option);
			}
		}
		$("#input-table1").val(tableConditionElement[1]);
		$("#input-table2").val(tableConditionElement[2]);
		document.getElementById("addCategoryIdInModal").addEventListener("click", function() {
			modalAddSelect2('#input-table0', 'New Category', 'Category Id');
		});
		document.getElementById("removeCategoryIds").addEventListener("click", function() {
			modalDelete2('#input-table0', '#removeCategoryIds');
		});
	} else if (panel === "RTAAdapterCategoryConfiguration" && idTable === "tableCategories") {
		htmlForm = newFormCusttomForRTA(5, vecLabels, "RTAAdapterCategoryConfiguration");
		$('#bodyForTable').html(htmlForm);
		var tableCategories = $('#tableCategories .selected td').map(function() {
			return $(this).text();
		}).get();
		$("#input-table0").val(tableCategories[1]);
		$("#input-table1").val(tableCategories[2]);
		$("#input-table2").val(tableCategories[3]);
		$("#input-table3").val(tableCategories[4]);

	} else if (panel === "RTAAdapterURLCategoryConsumptionConfiguration") {
		htmlForm = newFormCusttomForRTA(5, vecLabels, "RTAAdapterURLCategoryConsumptionConfiguration");
		$('#bodyForTable').html(htmlForm);
		var tableCounters = $('#tableCounters .selected td').map(function() {
			return $(this).text();
		}).get();
		$("#input-table0").val(tableCounters[1]);
		if (tableCounters[2] != "") {
			var arr = tableCounters[2].split(",");
			for (var i = 0; i < arr.length; i++) {
				var option = "<option value='" + arr[i] + "'>" + arr[i] + "</option>";
				$("#input-table1").append(option);
			}
		}

		if (tableCounters[3] == "true") {
			$('#input-table2').prop("checked", true);
		} else {
			$('#input-table2').prop("checked", false);
		}

		if (tableCounters[4] == "true") {
			$('#input-table3').prop("checked", true);
		} else {
			$('#input-table3').prop("checked", false);
		}
		$("#input-table4").val(tableCounters[5]);
		document.getElementById("addCategoryIdInModal").addEventListener("click", function() {
			modalAddSelect2('#input-table1', 'New Category', 'Category Id');
		});
		document.getElementById("removeCategoryIds").addEventListener("click", function() {
			modalDelete2('#input-table1', '#removeCategoryIds');
		});
	}

	$('#typeModalForTable').val(idTable);
	$('#isEdit').val("true");
	$('#alertForTable').hide();
	$('#modalForTable').modal({
		backdrop : "static"
	});
}
// Limpia el modal para crear nuevos Group URL Hits
function clearModalGroupURL() {
	$("#newGroupURLsId").val("");
	$("#newGroupURLsName").val("");
	$("#newGroupURLsCategoryId").val("");
}

// ///////////////////////////// Funciones de validacion /////////////////////////////////
function validatorVariablesRDR(RDRPort, queueDepth, timeout) {
	var bolleanRDRPort = true;
	var bolleanQueueDepth = true;
	var bolleanTimeout = true;
	var bollean = true;
	var errorInfo = "<p>";
	if (RDRPort === '') {
		errorInfo += "Enter the port.</br>";
		alert_red("RDRPort");
		bolleanRDRPort = false;
	} else if (RDRPort <= 65535 && /^\d+$/.test(RDRPort)) {
		bolleanRDRPort = true;
	} else {
		errorInfo += "The port value entered is incorrect.</br>";
		alert_red("RDRPort");
		bolleanRDRPort = false;
	}
	if (queueDepth === '') {
		errorInfo += "Enter the queue depth.</br>";
		alert_red("queueDepth");
		bolleanQueueDepth = false;
	} else if (/^\d+$/.test(queueDepth)) {
		bolleanQueueDepth = true;
	} else {
		errorInfo += "The queue depth value entered is incorrect.</br>";
		alert_red("queueDepth");
		bolleanQueueDepth = false;
	}
	if (timeout === '') {
		errorInfo += "Enter timeout.</br>";
		alert_red("timeout");
		bolleanTimeout = false;
	} else if (/^\d+$/.test(timeout)) {
		bolleanTimeout = true;
	} else {
		errorInfo += "The timeout value entered is incorrect.</br>";
		alert_red("timeout");
		bolleanTimeout = false;
	}
	errorInfo += "</p>";
	$('#pRDR').html(errorInfo);
	// si alguno falla no se realiza la llamada post
	if (!bolleanRDRPort || !bolleanQueueDepth || !bolleanTimeout) {
		bollean = false;
	}
	return bollean;
}
function validatorVariablesIPDR(ipIPDR, portIPDR) {
	var bolleanIpIPDR = true;
	var bolleanPortIPDR = true;
	var bollean = true;
	var errorInfo = "<p>";
	if (ipIPDR === '') {
		errorInfo += "Enter the ip.</br>";
		alert_red("ipIPDR");
		bolleanIpIPDR = false;
	}
	if (portIPDR === '') {
		portIPDR += "Enter the port.</br>";
		alert_red("portIPDR");
		bolleanPortIPDR = false;
	} else if (portIPDR <= 65535 && /^\d+$/.test(portIPDR)) {
		bolleanPortIPDR = true;
	} else {
		errorInfo += "The port value entered is incorrect.</br>";
		alert_red("portIPDR");
		bolleanPortIPDR = false;
	}
	errorInfo += "</p>";
	$('#pIPDR').html(errorInfo);
	// si alguno falla no se realiza la llamada post
	if (!bolleanIpIPDR || !bolleanPortIPDR) {
		bollean = false;
	}
	return bollean;
}
function validatorVariablesDBManager(url, user, password, DBName, tableName, SuscriberIdCol, InterestIdCol) {
	var bolleanUrl = true;
	var bolleanUser = true;
	var bolleanPassword = true;
	var bolleanDBName = true;
	var bolleanTableName = true;
	var bolleanSuscriberIdCol = true;
	var bolleanInterestIdCol = true;
	var bollean = true;
	var errorInfo = "<p>";
	if (url === '') {
		errorInfo += "Enter the url.</br>";
		alert_red("url");
		bolleanUrl = false;
	}
	if (user === '') {
		errorInfo += "Enter the user.</br>";
		alert_red("user");
		bolleanUser = false;
	}
	if (password === '') {
		errorInfo += "Enter the password.</br>";
		alert_red("password");
		bolleanPassword = false;
	}
	if (DBName === '') {
		errorInfo += "Enter the DB Name.</br>";
		alert_red("DBName");
		bolleanDBName = false;
	}
	if (tableName === '') {
		errorInfo += "Enter the table name.</br>";
		alert_red("tableName");
		bolleanTableName = false;
	}
	if (SuscriberIdCol === '') {
		errorInfo += "Enter the id col Suscriber.</br>";
		alert_red("SuscriberIdCol");
		bolleanSuscriberIdCol = false;
	}
	if (InterestIdCol === '') {
		errorInfo += "Enter the interest id col.</br>";
		alert_red("InterestIdCol");
		bolleanInterestIdCol = false;
	}
	errorInfo += "</p>";
	$('#pDBManager').html(errorInfo);
	// si alguno falla no se realiza la llamada post
	if (!bolleanUrl || !bolleanUser || !bolleanPassword || !bolleanDBName || !bolleanTableName || !bolleanSuscriberIdCol || !bolleanInterestIdCol) {
		bollean = false;
	}
	return bollean;
}
function validatorVariablesOthers(RMIIP, RMIPort, RMIService, RestIP, RestPort, SepIP, SepPort, SERIP, SERPort, CSMIP, CSMPort) {
	var bolleanRMIIP = true;
	var bolleanRMIPort = true;
	var bolleanRMIService = true;
	var bolleanRestIP = true;
	var bolleanRestPort = true;
	var bolleanSepIP = true;
	var bolleanSepPort = true;
	var bolleanSERIP = true;
	var bolleanSERPort = true;
	var bolleanCSMIP = true;
	var bolleanCSMPort = true;
	var bollean = true;
	var errorInfo = "<p>";
	if (RMIIP === '') {
		errorInfo += "Enter the ip in RMI Config.</br>";
		alert_red("RMIIP");
		bolleanRMIIP = false;
	}
	if (RMIService === '') {
		errorInfo += "Enter the service name in RMI Config.</br>";
		alert_red("RMIIP");
		bolleanRMIService = false;
	}
	if (RMIPort === '') {
		errorInfo += "Enter the port in RMI Config.</br>";
		alert_red("RMIPort");
		bolleanRMIPort = false;
	} else if (RMIPort <= 65535 && /^\d+$/.test(RMIPort)) {
		bolleanRMIPort = true;
	} else {
		errorInfo += "The port value entered in RMI Config is incorrect.</br>";
		alert_red("RMIPort");
		bolleanRMIPort = false;
	}
	if (RestIP === '') {
		errorInfo += "Enter the ip in Rest Config.</br>";
		alert_red("RestIP");
		bolleanRestIP = false;
	}
	if (RestPort === '') {
		errorInfo += "Enter the port in Rest Config.</br>";
		alert_red("RestPort");
		bolleanRestPort = false;
	} else if (RestPort <= 65535 && /^\d+$/.test(RestPort)) {
		bolleanRestPort = true;
	} else {
		errorInfo += "The port value entered in Rest Config is incorrect.</br>";
		alert_red("RestPort");
		bolleanRestPort = false;
	}
	if (SepIP === '') {
		errorInfo += "Enter the ip in Sep Config.</br>";
		alert_red("SepIP");
		bolleanSepIP = false;
	}
	if (SepPort === '') {
		errorInfo += "Enter the port in Sep Config.</br>";
		alert_red("SepPort");
		bolleanSepPort = false;
	} else if (SepPort <= 65535 && /^\d+$/.test(SepPort)) {
		bolleanSepPort = true;
	} else {
		errorInfo += "The port value entered in Sep Config is incorrect.</br>";
		alert_red("SepPort");
		bolleanSepPort = false;
	}
	if (SERIP === '') {
		errorInfo += "Enter the ip in Notification Server Config.</br>";
		alert_red("SERIP");
		bolleanSERIP = false;
	}
	if (SERPort === '') {
		errorInfo += "Enter the port in Notification Server Config.</br>";
		alert_red("SERPort");
		bolleanSERPort = false;
	} else if (SERPort <= 65535 && /^\d+$/.test(SERPort)) {
		bolleanSERPort = true;
	} else {
		errorInfo += "The port value entered in Notification Server Config is incorrect.</br>";
		alert_red("SERPort");
		bolleanSERPort = false;
	}
	if (CSMIP === '') {
		errorInfo += "Enter the ip in Notification Server Config.</br>";
		alert_red("CSMIP");
		bolleanCSMIP = false;
	}
	if (CSMPort === '') {
		errorInfo += "Enter the port in Notification Server Config.</br>";
		alert_red("CSMPort");
		bolleanCSMPort = false;
	} else if (SERPort <= 65535 && /^\d+$/.test(CSMPort)) {
		bolleanCSMPort = true;
	} else {
		errorInfo += "The port value entered in Notification Server Config is incorrect.</br>";
		alert_red("SERPort");
		bolleanSERPort = false;
	}
	errorInfo += "</p>";

	$('#pOthers').html(errorInfo);

	// si alguno falla no se realiza la llamada post
	if (!bolleanRMIIP || !bolleanRMIPort || !bolleanRMIService || !bolleanRestIP || !bolleanRestPort || !bolleanSepIP || !bolleanSepPort || !bolleanSERIP || !bolleanSERPort || !bolleanCSMIP || !bolleanCSMPort) {
		bollean = false;
	}
	return bollean;
}

function validatorVariablesTable(variable1, variable2, numtable) {
	var bolleanVariable1 = true;
	var bolleanvariable2 = true;
	var bollean = true;
	var errorInfo = "<p>";
	if (numtable === 2) {
		if (variable1 === '') {
			errorInfo += "Enter the ip.</br>";
			alert_red("input-table0");
			bolleanVariable1 = false;
		} else if (/^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/.test(variable1)) {
			bolleanVariable1 = true;
		} else {
			errorInfo += "The ip value entered is incorrect.</br>";
			alert_red("input-table0");
			bolleanVariable1 = false;
		}
	}
	if (numtable === 4) {
		if (variable1 === '') {
			errorInfo += "Enter the ip.</br>";
			alert_red("input-table0");
			bolleanVariable1 = false;
		}
		if (variable2 === '') {
			errorInfo += "Enter the port.</br>";
			alert_red("input-table1");
			bolleanvariable2 = false;
		} else if (variable2 <= 65535 && /^\d+$/.test(variable2)) {
			bolleanvariable2 = true;
		} else {
			errorInfo += "The port value entered is incorrect.</br>";
			alert_red("input-table1");
			bolleanvariable2 = false;
		}
	}
	if (numtable === 6) {
		if (variable1 === '') {
			errorInfo += "Enter the separator.</br>";
			alert_red("input-table1");
			bolleanVariable1 = false;
		}
		if (variable2 === '') {
			errorInfo += "Enter the timestamp format pattern.</br>";
			alert_red("input-table5");
			bolleanvariable2 = false;
		}
	}
	errorInfo += "</p>";
	$('#pForTable').html(errorInfo);
	// si alguno falla no se realizan cambios
	if (!bolleanVariable1 || !bolleanvariable2) {
		bollean = false;
	}
	return bollean;
}

// En la variable1 tiene que ingresar la calification y en la otra el value
function validatorVariablesTableInterestConversionList(variable1, variable2) {
	var bolleanVariable1 = true;
	var bolleanvariable2 = true;
	var bollean = true;
	var errorInfo = "<p>";

	if (variable1 === '') {
		errorInfo += "Enter the calification.</br>";
		alert_red("input-table0");
		bolleanVariable1 = false;
	} else if (/^[^\0]*$/.test(variable1)) {
		bolleanVariable1 = true;
	} else {
		errorInfo += "The Calification value entered is incorrect.</br>";
		alert_red("input-table0");
		bolleanVariable1 = false;
	}
	if (variable2 === '') {
		errorInfo += "Enter the value.</br>";
		alert_red("input-table1");
		bolleanvariable2 = false;
	} else if (/^\-?(\d)*(\.(\d)*)?$/.test(variable2)) {
		bolleanvariable2 = true;
	} else {
		errorInfo += "The value entered is incorrect.</br>";
		alert_red("input-table1");
		bolleanvariable2 = false;
	}
	errorInfo += "</p>";
	$('#pForTable').html(errorInfo);
	// si alguno falla no se realizan cambios
	if (!bolleanVariable1 || !bolleanvariable2) {
		bollean = false;
	}
	return bollean;
}

// variable1 = counterId. variable2 = minUsageAllowed.
function validatorVariablesTableCounters(variable1, variable2) {
	var bolleanVariable1 = true;
	var bolleanvariable2 = true;
	var bollean = true;
	var errorInfo = "<p>";

	if (variable1 === '') {
		errorInfo += "Enter the counter id.</br>";
		alert_red("input-table0");
		bolleanVariable1 = false;
	} else if (/^\d+$/.test(variable1)) {
		bolleanVariable1 = true;
	} else {
		errorInfo += "The counter id value entered is incorrect.</br>";
		alert_red("input-table0");
		bolleanVariable1 = false;
	}
	if (variable2 === '') {
		errorInfo += "Enter the min usage allowed.</br>";
		alert_red("input-table4");
		bolleanvariable2 = false;
	} else if (/^\d+$/.test(variable2)) {
		bolleanvariable2 = true;
	} else {
		errorInfo += "The min usage allowed entered is incorrect.</br>";
		alert_red("input-table4");
		bolleanvariable2 = false;
	}
	errorInfo += "</p>";
	$('#pForTable').html(errorInfo);
	// si alguno falla no se realizan cambios
	if (!bolleanVariable1 || !bolleanvariable2) {
		bollean = false;
	}
	return bollean;
}

function validatorVariablesTableCategories(variable1, variable2, variable3, variable4, variable5) {
	var bolleanVariable1 = true;
	var bolleanvariable2 = true;
	var bolleanvariable3 = true;
	var bolleanvariable4 = true;
	var bolleanvariable5 = true;
	var bollean = true;
	var errorInfo = "<p>";

	if (variable1 === '') {
		errorInfo += "Enter the id.</br>";
		alert_red("input-table0");
		bolleanVariable1 = false;
	} else if (/^\d+$/.test(variable1)) {
		bolleanVariable1 = true;
	} else {
		errorInfo += "The id value entered is incorrect.</br>";
		alert_red("input-table0");
		bolleanVariable1 = false;
	}

	if (variable2 === '') {
		errorInfo += "Enter the name.</br>";
		alert_red("input-table1");
		bolleanvariable2 = false;
	}

	if (variable3 === '') {
		errorInfo += "Enter the category parent id.</br>";
		alert_red("input-table2");
		bolleanvariable3 = false;
	} else if (/^\d+$/.test(variable3)) {
		bolleanvariable3 = true;
	} else {
		errorInfo += "The category parent id entered is incorrect.</br>";
		alert_red("input-table2");
		bolleanvariable3 = false;
	}

	if (variable4 === '') {
		errorInfo += "Enter the min value.</br>";
		alert_red("input-table3");
		bolleanvariable4 = false;
	} else if (/^\d+$/.test(variable4)) {
		bolleanvariable4 = true;
	} else {
		errorInfo += "The min value entered is incorrect.</br>";
		alert_red("input-table3");
		bolleanvariable4 = false;
	}

	if (variable5 === '') {
		errorInfo += "Enter the max value.</br>";
		alert_red("input-table4");
		bolleanvariable5 = false;
	} else if (/^\d+$/.test(variable5)) {
		bolleanvariable5 = true;
	} else {
		errorInfo += "The max value entered is incorrect.</br>";
		alert_red("input-table4");
		bolleanvariable5 = false;
	}

	errorInfo += "</p>";
	$('#pForTable').html(errorInfo);
	// si alguno falla no se realizan cambios
	if (!bolleanVariable1 || !bolleanvariable2 || !bolleanvariable3 || !bolleanvariable4 || !bolleanvariable5) {
		bollean = false;
	}
	return bollean;
}

function validatorVariablesTableConditionElement(variable1, variable2) {
	var bolleanvariable2 = true;
	var bolleanvariable3 = true;
	var bollean = true;
	var errorInfo = "<p>";

	if (variable1 === '') {
		errorInfo += "Enter the operator.</br>";
		alert_red("input-table1");
		bolleanvariable2 = false;
	}
	if (variable2 === '') {
		errorInfo += "Enter the value.</br>";
		alert_red("input-table2");
		bolleanvariable3 = false;
	} else if (/^[0-9]*[.][0-9]+$/.test(variable2)) {
		bolleanvariable3 = true;
	} else if (/^\d+$/.test(variable2)) {
		bolleanvariable3 = true;
	} else {
		errorInfo += "The value entered is incorrect.</br>";
		alert_red("input-table2");
		bolleanvariable3 = false;
	}

	errorInfo += "</p>";
	$('#pForTable').html(errorInfo);
	// si alguno falla no se realizan cambios
	if (!bolleanvariable2 || !bolleanvariable3) {
		bollean = false;
	}
	return bollean;
}

function validatorVariablesTableCounterConsumptionConfiguration(variable1, variable2, variable3) {
	var bolleanVariable1 = true;
	var bolleanvariable2 = true;
	var bolleanvariable3 = true;
	var bollean = true;
	var errorInfo = "<p>";

	if (variable1 === '') {
		errorInfo += "Enter the counter id.</br>";
		alert_red("input-table0");
		bolleanVariable1 = false;
	} else if (/^\d+$/.test(variable1)) {
		bolleanVariable1 = true;
	} else {
		errorInfo += "The counter id entered is incorrect.</br>";
		alert_red("input-table0");
		bolleanVariable1 = false;
	}

	if (variable2 === '') {
		errorInfo += "Enter the min downstream allowed.</br>";
		alert_red("input-table4");
		bolleanvariable2 = false;
	} else if (/^\d+$/.test(variable2)) {
		bolleanvariable2 = true;
	} else {
		errorInfo += "The min downstream allowed entered is incorrect.</br>";
		alert_red("input-table4");
		bolleanvariable2 = false;
	}

	if (variable3 === '') {
		errorInfo += "Enter the min upstream allowed.</br>";
		alert_red("input-table6");
		bolleanvariable3 = false;
	} else if (/^\d+$/.test(variable3)) {
		bolleanvariable3 = true;
	} else {
		errorInfo += "The min upstream allowed entered is incorrect.</br>";
		alert_red("input-table6");
		bolleanvariable3 = false;
	}

	errorInfo += "</p>";
	$('#pForTable').html(errorInfo);
	// si alguno falla no se realizan cambios
	if (!bolleanVariable1 || !bolleanvariable2 || !bolleanvariable3) {
		bollean = false;
	}
	return bollean;
}

function validatorVariablesTableURLHits(serverIP, serverPort, accessString, clientIP, clientPort) {
	var bolleanVariable1 = true;
	var bolleanvariable2 = true;
	var bolleanvariable3 = true;
	var bolleanvariable4 = true;
	var bolleanvariable5 = true;
	var bollean = true;
	var errorInfo = "<p>";

	if (serverIP === '') {
		errorInfo += "Enter the server ip.</br>";
		alert_red("input-table1");
		bolleanVariable1 = false;
	} else if (/^\d+$/.test(serverIP)) {
		bolleanVariable1 = true;
	} else {
		errorInfo += "The server ip entered is incorrect.</br>";
		alert_red("input-table1");
		bolleanVariable1 = false;
	}

	if (serverPort === '') {
		errorInfo += "Enter the server port.</br>";
		alert_red("input-table2");
		bolleanvariable2 = false;
	} else if (serverPort <= 65535 && /^\d+$/.test(serverPort)) {
		bolleanvariable2 = true;
	} else {
		errorInfo += "The server port entered is incorrect.</br>";
		alert_red("input-table2");
		bolleanvariable2 = false;
	}

	if (accessString === '') {
		errorInfo += "Enter the access string.</br>";
		alert_red("input-table3");
		bolleanvariable3 = false;
	}

	if (clientIP === '') {
		errorInfo += "Enter the client ip.</br>";
		alert_red("input-table6");
		bolleanvariable4 = false;
	} else if (/^\d+$/.test(clientIP)) {
		bolleanvariable4 = true;
	} else {
		errorInfo += "The client ip entered is incorrect.</br>";
		alert_red("input-table6");
		bolleanvariable4 = false;
	}
	if (clientPort === '') {
		errorInfo += "Enter the client port.</br>";
		alert_red("input-table7");
		bolleanvariable5 = false;
	} else if (clientPort <= 65535 && /^\d+$/.test(clientPort)) {
		bolleanvariable5 = true;
	} else {
		errorInfo += "The client port entered is incorrect.</br>";
		alert_red("input-table7");
		bolleanvariable5 = false;
	}

	errorInfo += "</p>";
	$('#pForTable').html(errorInfo);
	// si alguno falla no se realizan cambios
	if (!bolleanVariable1 || !bolleanvariable2 || !bolleanvariable3 || !bolleanvariable4 || !bolleanvariable5) {
		bollean = false;
	}
	return bollean;
}

function validatorVariablesRTAAdapterTopperConfiguration(variable1) {
	var bollean = true;
	var errorInfo = "<p>";

	if (variable1 === '') {
		errorInfo += "Enter the min allowed hits.</br>";
		alert_red("minAllowedHits");
		bollean = false;
	} else if (/^\d+$/.test(variable1)) {
		bollean = true;
	} else {
		errorInfo += "The min allowed hits entered is incorrect.</br>";
		alert_red("minAllowedHits");
		bollean = false;
	}

	errorInfo += "</p>";
	$('#pForRTAConfig').html(errorInfo);

	return bollean;
}
function validatorVariablesRTAAdapterFlavorUpdaterConfiguration(sepIpAddress, sepPort, defaultFlavorId, newFlavorsCountToApplyInSep) {
	var bolleanVariable1 = true;
	var bolleanvariable2 = true;
	var bolleanvariable4 = true;
	var bolleanvariable5 = true;
	var bollean = true;
	var errorInfo = "<p>";

	if (sepIpAddress === '') {
		errorInfo += "Enter the server ip.</br>";
		alert_red("sepIpAddress");
		bolleanVariable1 = false;
	} else if (/^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/.test(sepIpAddress)) {
		bolleanVariable1 = true;
	} else {
		errorInfo += "The server ip entered is incorrect.</br>";
		alert_red("input-table1");
		bolleanVariable1 = false;
	}

	if (sepPort === '') {
		errorInfo += "Enter the port.</br>";
		alert_red("sepPort");
		bolleanvariable2 = false;
	} else if (sepPort <= 65535 && /^\d+$/.test(sepPort)) {
		bolleanvariable2 = true;
	} else {
		errorInfo += "The port entered is incorrect.</br>";
		alert_red("sepPort");
		bolleanvariable2 = false;
	}

	if (defaultFlavorId === '') {
		errorInfo += "Enter the default flavor id.</br>";
		alert_red("defaultFlavorId");
		bolleanvariable4 = false;
	} else if (/^\d+$/.test(defaultFlavorId)) {
		bolleanvariable4 = true;
	} else {
		errorInfo += "The default flavor id entered is incorrect.</br>";
		alert_red("defaultFlavorId");
		bolleanvariable4 = false;
	}
	if (newFlavorsCountToApplyInSep === '') {
		errorInfo += "Enter the new flavors count to apply in SEP.</br>";
		alert_red("newFlavorsCountToApplyInSep");
		bolleanvariable5 = false;
	} else if (/^\d+$/.test(newFlavorsCountToApplyInSep)) {
		bolleanvariable5 = true;
	} else {
		errorInfo += "The new flavors count to apply in SEP entered is incorrect.</br>";
		alert_red("newFlavorsCountToApplyInSep");
		bolleanvariable5 = false;
	}

	errorInfo += "</p>";
	$('#pForRTAConfig').html(errorInfo);
	// si alguno falla no se realizan cambios
	if (!bolleanVariable1 || !bolleanvariable2 || !bolleanvariable4 || !bolleanvariable5) {
		bollean = false;
	}
	return bollean;
}
function alert_red(id) {
	document.getElementById(id).classList.add('alert-red');
	setTimeout(function() {
		document.getElementById(id).classList.remove('alert-red');
	}, 2000);
}