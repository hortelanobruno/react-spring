var urlAPIPrefix;

$(document).ready(function() {
	urlAPIPrefix = "/ajax";
	check_settings();
	$(".js-example-basic-single").select2();
});

function check_settings() {
	if (Time_Zone !== "") {
		$('#Time_Zone').val(Time_Zone);
	}
}

function SubmitSettings() {
	var port = $('#portNap').val();
	var ip = $('#ipNap').val();
	var tz = $("#Time_Zone").val();
	var timeout = $("#timeout").val();
	$.ajax({
		data : {
			"IpNap" : ip,
			"PortNap" : port,
			"Time_Zone" : tz,
			"timeout" : timeout
		},
		type : "post",
		url : urlAPIPrefix + "/SetSettings",
		success : function(data) {
			// alert("success");
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