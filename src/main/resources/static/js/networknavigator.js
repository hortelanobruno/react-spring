/////////////////////// DEFINICION DE VARIABLES GLOBALES ///////////////////////////////////
	var urlAPIPrefix = null;
	var sites = null;
	var sces = null;
	var cmtss = null;
	var balancingGroups = null;

	$(document).ready(function() { // INICIO FUNCIO READY
		// SETEO DE URL-PREFIX
		urlAPIPrefix = "/ajax";

		// SPLIT-PANE
		$('#layoutNetwork').layout({
			center__paneSelector : ".outer-center",
			west__paneSelector : ".outer-west",
			east__paneSelector : ".outer-east",
			west__size : 125,
			east__size : 125,
			spacing_open : 8 // ALL panes
			,
			spacing_closed : 12 // ALL panes
			//,	north__spacing_open:	0
			//,	south__spacing_open:	0
			,
			north__maxSize : 200,
			south__maxSize : 200

			// MIDDLE-LAYOUT (child of outer-center-pane)
			,
			center__childOptions : {
				center__paneSelector : ".middle-center",
				west__paneSelector : ".middle-west",
				east__paneSelector : ".middle-east",
				west__size : 350,
				east__size : 100,
				spacing_open : 8 // ALL panes
				,
				spacing_closed : 12
			// ALL panes
			}
		});

		// Tree de network elements
		buildTreeNetworkNavigator();

	});// FIN FUNCION READY

	////////////////////////////////// FUNCIONES JAVASCRIPT ///////////////////////////////////////////////////

	function getAjax(url) {
		// LLAMADA AJAX GETs (sites, sces, cmtss)
		var resultado = null;
		$.ajax({
			type : "get",
			dataType : "json",
			async : false,
			url : urlAPIPrefix + url,
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

	function getSites() {
		return getAjax("/getSites");
	}

	function getSCEs() {
		return getAjax("/getSCEs");
	}

	function getCMTSs() {
		return getAjax("/getCMTSs");
	}

	function getNetworkElements() {
		sites = getSites();
		sces = getSCEs();
		cmtss = getCMTSs();
	}

	function getNetworkElementById(networkElementId, networkElements) {
		var networkElement = null;
		for ( var index in networkElements) {
			if (networkElements[index].id === networkElementId) {
				networkElement = networkElements[index];
				break;
			}
		}
		return networkElement;
	}

	function getSceById(sceId) {
		return getNetworkElementById(sceId, sces);
	}

	function getCmtsById(cmtsId) {
		return getNetworkElementById(cmtsId, cmtss);
	}

	function buildSTRDeviceNode(networkElement, strTree, uiIcon) {
		strTree += "<ul>";
		strTree += "<li class = 'isFolder isExpanded node-tree node-device' data-uiicon = '" + uiIcon + "'>";
		strTree += networkElement.name;
		if ((networkElement.ipAddress !== undefined) && (networkElement.ipAddress !== null)) {
			strTree += " [" + networkElement.ipAddress + "]";
		}
		strTree += "</li>";
		strTree += "</ul>";

		return strTree;

	}

	function buildSTRSCELinkNode(networkElement, strTree, uiIcon) {
		strTree += "<ul>";
		strTree += "<li class = 'isFolder isExpanded node-tree node-sce-link' data-uiicon = '" + uiIcon + "'>";
		strTree += networkElement.name;
		if ((networkElement.linkIndex !== undefined) && (networkElement.linkIndex !== null)) {
			strTree += " (Link ID " + networkElement.linkIndex + ")";
		}
		strTree += "</li>";
		strTree += "</ul>";

		return strTree;
	}

	function buildSTRCMTSInterfaceNode(networkElement, strTree, uiIcon) {
		strTree += "<ul>";
		strTree += "<li class = 'isFolder isExpanded node-tree node-cmts-interface' data-uiicon = '" + uiIcon + "'>";
		strTree += networkElement.name;
		strTree += "</li>";
		strTree += "</ul>";

		return strTree;
	}

	function loadSCEs(strTree, site) {
		var sce = null;
		for ( var sceIndex in site.sces) {
			sce = getSceById(site.sces[sceIndex]);
			if (sce !== null) {
				strTree = buildSTRDeviceNode(sce, strTree, "ui-icon-sce");
			}
		}
		return strTree;
	}

	function loadCMTs(strTree, site) {
		var cmts = null;
		for ( var cmtsIndex in site.cmtss) {
			cmts = getCmtsById(site.cmtss[cmtsIndex]);
			if (cmts !== null) {
				strTree = buildSTRDeviceNode(cmts, strTree, "ui-icon-cmts");
			}
		}
		return strTree;
	}

	function buildTreeNetworkNavigator() {
		getNetworkElements();
		balancingGroups = new Array();
		$("#demoTree ul").empty();
		var strTree = null;
		for ( var siteIndex in sites) {
			strTree += "<li data-uiicon='ui-icon-template-domain' class='isFolder isExpanded node-site'>" + sites[siteIndex].name;
			strTree = loadSCEs(strTree, sites[siteIndex]);
			strTree = loadCMTs(strTree, sites[siteIndex]);
			strTree += "</li>";
		}
		$("#demoTree ul").append(strTree);
		// EASYTREE
		easytree = $('#demoTree').easytree({
			stateChanged : easyTreeStateChange
		});
	}

	function getCmtsInterfaceFromBalancingGroup(cmtsInterfaceId) {
		var cmtsInterface = null;
		for ( var balancingGroupIndex in balancingGroups) {
			cmtsInterface = balancingGroups[balancingGroupIndex];
			if (cmtsInterface.id === cmtsInterfaceId) {
				return cmtsInterface;
			}
		}
		return null;
	}

	function isBalancingGroups(cmtsInterface, cmts) {
		for ( var key in cmts.cmtsBalancingGroups) {
			// Chequea con index de down
			for ( var downInterfaceIndex in cmts.cmtsBalancingGroups[key].downifIndexes) {
				if (cmts.cmtsBalancingGroups[key].downifIndexes[downInterfaceIndex] === cmtsInterface.id) {
					return true;
				}
			}
			// Chequea con index de up
			for ( var upInterfaceIndex in cmts.cmtsBalancingGroups[key].upifIndexes) {
				if (cmts.cmtsBalancingGroups[key].upifIndexes[upInterfaceIndex] === cmtsInterface.id) {
					return true;
				}
			}
		}
		return false;
	}

	function loadSitePanel() {
		$("#panelCentral").empty();
	}

	function loadPanelCmts(cmts) {
		$("#panelCentral").empty();
		var html = '';
		var cmtsInterface = null;
		var balancingGroup = null;

		html = '<form class="form-horizontal" role="form" style="margin-top: 10px;">'; // INCIO FORM
		html += '<div class="form-group">'; // INICIO DIV CMTS NAME
		html += '<label class="control-label col-sm-2 labelLoadPanel" for="cmtsName">Name:</label>';
		html += '<div class="col-sm-10 divLoadPanel">';
		html += '<input type="text" class="form-control textFieldLoadPanel" id="cmtsName" value="' + cmts.name + '" disabled></div></div>';// FIN DIV CMTS NAME

		html += '<div class="form-group">'; // INICIO DIV CMTS IP
		html += '<label class="control-label col-sm-2 labelLoadPanel" for="cmtsIp">IP Address:</label>';
		html += '<div class="col-sm-10 divLoadPanel">';
		html += '<input type="text" class="form-control textFieldLoadPanel" id="cmtsIp" value="' + cmts.ipAddress + '" disabled>';
		html += '</div></div>'; // FIN DIV CMTS IP

		html += '<div class="form-group">'; // INICIO DIV CMTS VENDOR
		html += '<label class="control-label col-sm-2 labelLoadPanel" for="cmtsVendor">Vendor:</label>';
		html += '<div class="col-sm-10 divLoadPanel">';
		html += '<input type="text" class="form-control textFieldLoadPanel" id="cmtsVendor" value="' + cmts.vendor + '" disabled>';
		html += '</div></div>'; // FIN DIV CMTS VENDOR

		html += '</form>'; // FIN FORM

		html += '<div class="form-group"> <fieldset> <legend></legend>'; // INICIO TREE
		html += '<div id="treeCmtsInterfaces"> <ul>';

		html += '</ul> </div> </fieldset> </div>';

		$("#panelCentral").append(html);
		// Carga el tree de interfaces
		$("#treeCmtsInterfaces ul").empty();
		var strTree = null;
		var strTreeOther = null;
		balancingGroups.length = 0;
		// Carga interfaces
		strTreeOther = "<ul>";
		strTreeOther += "<li class = 'isFolder node-tree node-balancing-group' data-uiicon = 'ui-icon-balancing-group'>Other Interfaces";
		// Carga other interfaces donwstream
		for ( var cmtsInterfaceIndex in cmts.cmtsInterfaces.Downstream) {
			cmtsInterface = cmts.cmtsInterfaces.Downstream[cmtsInterfaceIndex];
			if (!isBalancingGroups(cmtsInterface, cmts)) {
				strTreeOther = buildSTRCMTSInterfaceNode(cmtsInterface, strTreeOther, "ui-icon-cmts-interface");
			} else {
				balancingGroups.push(cmtsInterface);
			}
		}
		// Carga other interfaces upstream
		for ( var cmtsInterfaceIndex in cmts.cmtsInterfaces.Upstream) {
			cmtsInterface = cmts.cmtsInterfaces.Upstream[cmtsInterfaceIndex];
			if (!isBalancingGroups(cmtsInterface, cmts)) {
				strTreeOther = buildSTRCMTSInterfaceNode(cmtsInterface, strTreeOther, "ui-icon-cmts-interface");
			} else {
				balancingGroups.push(cmtsInterface);
			}
		}
		strTree += "</li>";
		strTree += "</ul>";

		// Carga balancing groups
		strTree = "<li data-uiicon='ui-icon-template-domain' class='isFolder isExpanded node-device-components-title'> CMTS Interfaces";
		for ( var balancingGroupKey in cmts.cmtsBalancingGroups) {
			balancingGroup = cmts.cmtsBalancingGroups[balancingGroupKey];
			strTree += "<ul>";
			strTree += "<li class = 'isFolder node-tree node-balancing-group' data-uiicon = 'ui-icon-balancing-group'>";
			strTree += balancingGroup.name;

			// Carga interfaces downstream
			for ( var ifDownstreamIndex in balancingGroup.downifIndexes) {
				cmtsInterface = getCmtsInterfaceFromBalancingGroup(balancingGroup.downifIndexes[ifDownstreamIndex]);
				strTree = buildSTRCMTSInterfaceNode(cmtsInterface, strTree, "ui-icon-cmts-interface");
			}
			// Carga interfaces upstream
			for ( var ifUpstreamIndex in balancingGroup.upifIndexes) {
				cmtsInterface = getCmtsInterfaceFromBalancingGroup(balancingGroup.upifIndexes[ifUpstreamIndex]);
				strTree = buildSTRCMTSInterfaceNode(cmtsInterface, strTree, "ui-icon-cmts-interface");
			}
			strTree += "</li>";
			strTree += "</ul>";
		}
		strTree += strTreeOther;
		strTree += "</li>";

		$("#treeCmtsInterfaces ul").append(strTree);
		// EASYTREE
		easytree = $('#treeCmtsInterfaces').easytree();
	}

	function loadPanelSce(sce) {
		$("#panelCentral").empty();
		html = '<form class="form-horizontal" role="form" style="margin-top: 10px;">'; // INCIO FORM
		html += '<div class="form-group">'; // INICIO DIV SCE NAME
		html += '<label class="control-label col-sm-2 labelLoadPanel" for="sceName">Name:</label>';
		html += '<div class="col-sm-10 divLoadPanel">';
		html += '<input type="text" class="form-control textFieldLoadPanel" id="sceName" value="' + sce.name + '" disabled></div></div>';// FIN DIV SCE NAME

		html += '<div class="form-group">'; // INICIO DIV SCE IP
		html += '<label class="control-label col-sm-2 labelLoadPanel" for="sceIp">IP Address:</label>';
		html += '<div class="col-sm-10 divLoadPanel">';
		html += '<input type="text" class="form-control textFieldLoadPanel" id="sceIp" value="' + sce.ipAddress + '" disabled>';
		html += '</div></div>'; // FIN DIV SCE IP

		html += '<div class="form-group">'; // INICIO DIV SCE VERSION
		html += '<label class="control-label col-sm-2 labelLoadPanel" for="sceVersion">Version:</label>';
		html += '<div class="col-sm-10 divLoadPanel">';
		if ((sce.sceVersion !== undefined) && (sce.sceVersion !== null)) {
			html += '<input type="text" class="form-control textFieldLoadPanel" id="sceVersion" value="' + sce.sceVersion + '" disabled>';
		} else {
			html += '<input type="text" class="form-control textFieldLoadPanel" id="sceVersion" value= N/A disabled>';
		}
		html += '</div></div>'; // FIN DIV SCE VERSION

		html += '</form>'; // FIN FORM

		html += '<div class="form-group"> <fieldset> <legend></legend>'; // INICIO TREE
		html += '<div id="treeSceLinks"> <ul>';

		html += '</ul> </div> </fieldset> </div>';

		$("#panelCentral").append(html);
		// Carga el tree de links
		$("#treeSceLinks ul").empty();
		var strTree = null;
		// Carga links
		strTree = "<li data-uiicon='ui-icon-template-domain' class='isFolder isExpanded node-device-components-title'>Links";
		// Carga links de sce
		for ( var sceLinkId in sce.links) {
			strTree = buildSTRSCELinkNode(sce.links[sceLinkId], strTree, "ui-icon-sce-link");

		}

		strTree += "</li>";
		$("#treeSceLinks ul").append(strTree);
		// EASYTREE
		easytree = $('#treeSceLinks').easytree();
	}

	function isSce(networkElementName, networkElementIp) {
		for ( var sceIndex in sces) {
			if ((networkElementName === sces[sceIndex].name) && (networkElementIp === sces[sceIndex].ipAddress)) {
				return true;
			}
		}
		return false;
	}

	function getNetworkElementName(stNetworkElement) {
		var networkElementName = null;
		var vec = stNetworkElement.split("[");
		if ((vec[0] !== undefined) && (vec[0] !== null)) {
			networkElementName = vec[0].trim();
		}
		return networkElementName;
	}

	function getNetworkElementIp(stNetworkElement) {
		var networkElementIp = null;
		var vec = stNetworkElement.split("[");
		if ((vec[1] !== undefined) && (vec[1] !== null)) {
			networkElementIp = vec[1].replace("]", "");
			networkElementIp = networkElementIp.trim();
		}
		return networkElementIp;
	}

	function getSceByNameAndIp(networkElementName, networkElementIp) {

		for ( var sceIndex in sces) {
			if ((networkElementName === sces[sceIndex].name) && (networkElementIp === sces[sceIndex].ipAddress)) {
				return sces[sceIndex];
			}
		}

		return null;
	}

	function getCmtsByNameAndIp(networkElementName, networkElementIp) {

		for ( var cmtsIndex in cmtss) {
			if ((networkElementName === cmtss[cmtsIndex].name) && (networkElementIp === cmtss[cmtsIndex].ipAddress)) {
				return cmtss[cmtsIndex];
			}
		}

		return null;
	}

	function easyTreeStateChange(siteNodes, nodesJson) {
		var networkElementName = null;
		var networkElementIp = null;

		for ( var siteNodeIndex in siteNodes) {
			if (siteNodes[siteNodeIndex].isActive) {
				loadSitePanel();
			} else {
				for ( var networkElementIndex in siteNodes[siteNodeIndex].children) {
					if (siteNodes[siteNodeIndex].children[networkElementIndex].isActive) {

						networkElementName = getNetworkElementName(siteNodes[siteNodeIndex].children[networkElementIndex].text);
						networkElementIp = getNetworkElementIp(siteNodes[siteNodeIndex].children[networkElementIndex].text);

						if (isSce(networkElementName, networkElementIp)) {
							loadPanelSce(getSceByNameAndIp(networkElementName, networkElementIp));
						} else {
							loadPanelCmts(getCmtsByNameAndIp(networkElementName, networkElementIp));
						}
					}
				}
			}
		}
	}

	////////////////////////////////// FIN FUNCIONES JAVASCRIPT ///////////////////////////////////////////////