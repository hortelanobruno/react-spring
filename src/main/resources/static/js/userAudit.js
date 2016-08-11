var table;
var advancedSearchFilter;
$(document).ready(function() {
	$('#datetimepicker1, #datetimepicker2').datetimepicker({
		format : 'Y-m-d H:i:s'
	});
	$('#open').click(function() {
		$('#datetimepicker1').datetimepicker('show');
	});
	$('#open2').click(function() {
		$('#datetimepicker2').datetimepicker('show');
	});
	$('#startTime').datetimepicker({
		datepicker : false,
		format : 'H:i',
		step : 5
	});
	$('#endTime').datetimepicker({
		datepicker : false,
		format : 'H:i',
		step : 5
	});

	table = $('#tableAudit').DataTable({
		"aLengthMenu" : [ [ 5, 10, 20, 50 ], [ 5, 10, 20, 50 ] ],
		"iDisplayLength" : 10,
		"aaSorting" : [ [ 1, 'desc' ] ],
		"serverSide" : true,
		"bFilter" : false,
		"ajax" : {
			"url" : "/ajax/retrieveAudits",
			"data" : function(d) {
				if (advancedSearchFilter !== undefined && advancedSearchFilter !== null) {
					d.advancedSearchFilter = JSON.stringify(advancedSearchFilter);
				}
			},
			"type" : "GET",
			"async" : false,
			error : function(response) {
				window.location = window.location.href;
			}
		},
		"columns" : [ {
			"data" : "username"
		}, {
			"data" : "ip"
		}, {
			"data" : "timestamp"
		}, {
			"data" : "action"
		}, {
			"data" : "parameters",
			"width" : "70%"
		} ]
	});

	$('#exportAuditButton').click(function() {
		var contextRoot = window.location.pathname + "";
		var arr = contextRoot.split("/");
		var newURL = window.location.protocol + "//" + window.location.host + "/" + arr[1] + "/exportAudits?";
		window.open(newURL, '_blank');
		window.focus();
	});

	$('#buttonRefreshTable').click(function() {
		advancedSearchFilter = null;
		table.ajax.reload();
	});

});

function clearAudits() {
	var permission = $('#clearAuditButton').val();

	if (permission !== undefined) {
		$.ajax({
			type : "post",
			url : "/ajax/clearAudits",
			success : function() {
				table.ajax.reload();
				$('#clearAuditsModal').modal('hide');
			},
			error : function(response) {
				window.location = window.location.href;
			}
		});
	} else {
		return false;
	}
}

function submitFormAdvancedSearch() {
	advancedSearchFilter = {};
	var userId = $("#userId").val();
	var userIP = $("#userIP").val();
	var datetimepicker1 = $("#datetimepicker1").val();
	var datetimepicker2 = $("#datetimepicker2").val();
	var actions = $("#actions").val();
	var parametersRegex = $("#parametersRegex").val();

	if (userId !== null && userId !== undefined && userId !== "") {
		advancedSearchFilter.username = userId;
	}
	if (userIP !== null && userIP !== undefined && userIP !== "") {
		advancedSearchFilter.ip = userIP;
	}
	if (datetimepicker1 !== null && datetimepicker1 !== undefined && datetimepicker1 !== "") {
		advancedSearchFilter.startTime = datetimepicker1;
	}
	if (datetimepicker2 !== null && datetimepicker2 !== undefined && datetimepicker2 !== "") {
		advancedSearchFilter.endTime = datetimepicker2;
	}
	if (actions !== null && actions !== undefined && actions !== "") {
		advancedSearchFilter.action = actions;
	}
	if (parametersRegex !== null && parametersRegex !== undefined && parametersRegex !== "") {
		advancedSearchFilter.parameters = parametersRegex;
	}

	$('#advancedSearchModal').modal('hide');
	table.clear().draw();
}