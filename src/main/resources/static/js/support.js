$('#exportConfig').click(function () {
    var filename = $.trim($("#filename").val());
    if (filename !== '') {
        var format = $("#format").val();
        var contextRoot = window.location.pathname + "";
        var arr = contextRoot.split("/");
        var newURL = window.location.protocol + "//" + window.location.host + "/" + arr[1] + "/exportConfig?filename=" + filename + "&format=" + format;
        window.open(newURL, '_blank');
        $("#modalExport").hide();
    } else {
        highlightError("filename");
    }
});

function highlightError(error) {
    document.getElementById(error).classList.add('error_submit');
    setTimeout(function () {
        document.getElementById(error).classList.remove('error_submit');
    }, 2000);
}