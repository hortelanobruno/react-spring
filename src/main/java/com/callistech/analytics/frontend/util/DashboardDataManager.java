package com.callistech.analytics.frontend.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.callistech.analytics.frontend.util.bean.Cmts;
import com.callistech.analytics.frontend.util.bean.Site;
import com.callistech.analytics.frontend.util.bean.VLink;
import com.callistech.policyserver.psm.integration.rest.CSMAPI;
import com.callistech.policyserver.psm.integration.rest.vo.NetworkSceTinyVO;
import com.callistech.policyserver.psm.integration.rest.vo.VLinkTinyVO;

/**
 *
 * @author callis
 */
public class DashboardDataManager {

	private static Map<Integer, NetworkSceTinyVO> sces = null;
	private static Map<Integer, Cmts> cmtss = null;
	private static Map<Integer, VLinkTinyVO> vlinks = null;
	private static Map<String, Site> siteNameMapper = null;
	private static CSMAPI csmApi = null;
	private static ArrayList<Site> sites = new ArrayList<Site>();
	private static String[] cmmss = { "[CMT1.ALM1-BSR64K, CMT2.ALM1-BSR64K, ...]" };
	private static String[] cmmss2 = { "[CMT3.CON1-BSR64K, CMT2.CON1-BSR64K, ...]" };

	private static void init() {
		// getSites();
		// getSces();
		// getCmtss();
		// getVlinks();
		sites = new ArrayList<Site>();
		Site site;
		Cmts cmts;
		site = new Site();
		site.setActuacion(14L);
		site.setId(1);
		site.setName("ALM");
		site.setIdealProvisioning("44");
		site.setActualProvisioning("33");
		site.setCmtsss(cmmss);
		cmts = new Cmts();
		cmts.setId(45);
		cmts.setName("CMT1.ALM1-BSR64K");
		cmts.setActuacion(2);
		cmts.setUtilization(50);
		cmts.setVlinkId(45);
		cmts.setVlinkIdealPir(1000L);
		cmts.setVlinkPir(2000L);
		site.setCmtss(cmts);
		cmts = new Cmts();
		cmts.setId(46);
		cmts.setName("CMT2.ALM1-BSR64K");
		cmts.setActuacion(5);
		cmts.setUtilization(30);
		cmts.setVlinkId(46);
		cmts.setVlinkIdealPir(1200L);
		cmts.setVlinkPir(2400L);
		site.setCmtss(cmts);
		cmts = new Cmts();
		cmts.setId(47);
		cmts.setName("CMT3.ALM1-BSR64K");
		cmts.setActuacion(0);
		cmts.setUtilization(10);
		cmts.setVlinkId(47);
		cmts.setVlinkIdealPir(500L);
		cmts.setVlinkPir(400L);
		site.setCmtss(cmts);
		sites.add(site);
		site = new Site();
		site.setActuacion(5L);
		site.setIdealProvisioning("120");
		site.setActualProvisioning("55");
		site.setId(2);
		site.setName("CON");
		site.setCmtsss(cmmss2);
		cmts = new Cmts();
		cmts.setId(6);
		cmts.setName("CMT1.CON1-BSR64K");
		cmts.setActuacion(2);
		cmts.setUtilization(50);
		cmts.setVlinkId(6);
		cmts.setVlinkIdealPir(1000L);
		cmts.setVlinkPir(2000L);
		site.setCmtss(cmts);
		cmts = new Cmts();
		cmts.setId(7);
		cmts.setName("CMT2.CON1-BSR64K");
		cmts.setActuacion(5);
		cmts.setUtilization(50);
		cmts.setVlinkId(7);
		cmts.setVlinkIdealPir(1200L);
		cmts.setVlinkPir(2400L);
		site.setCmtss(cmts);
		cmts = new Cmts();
		cmts.setId(4);
		cmts.setName("CMT3.CON1-BSR64K");
		cmts.setActuacion(0);
		cmts.setUtilization(50);
		cmts.setVlinkId(47);
		cmts.setVlinkIdealPir(500L);
		cmts.setVlinkPir(400L);
		site.setCmtss(cmts);
		sites.add(site);
	}

	private static void getSites() {
		sites = new ArrayList<Site>();
		siteNameMapper = new HashMap<String, Site>();
		Site site;
		try {
			FileReader inReader = new FileReader("C:/Users/callis/Documents/NetBeansProjects/com.callistech_dashboard/dashboard-web/src/main/resources/sites.cfg");
			BufferedReader in = new BufferedReader(inReader);

			String line;
			String[] values;
			while ((line = in.readLine()) != null) {
				values = line.split(",");
				site = new Site();
				site.setId(Integer.valueOf(values[0]));
				site.setName(values[1]);
				sites.add(site);
				siteNameMapper.put(site.getName(), site);
			}
		} catch (FileNotFoundException ex) {
			Logger.getLogger(DashboardDataManager.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(DashboardDataManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private static void getSces() {
		if (csmApi == null) {
			csmApi = new CSMAPI();
			try {
				csmApi.connect("10.0.0.68", 9876);
			} catch (Exception ex) {
				Logger.getLogger(DashboardDataManager.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		sces = null;
		List<NetworkSceTinyVO> sceList = null;
		try {
			sceList = csmApi.getSces();
			sces = new HashMap();
			for (NetworkSceTinyVO sce : sceList) {
				sces.put(sce.getId(), sce);
			}
		} catch (Exception ex) {
			Logger.getLogger(DashboardDataManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private static void getCmtss() {
		cmtss = new HashMap();
		Cmts cmts;
		try {
			BufferedReader in = new BufferedReader(new FileReader("C:/Users/callis/Documents/NetBeansProjects/com.callistech_dashboard/dashboard-web/src/main/resources/cmtss.cfg"));
			String line;
			String[] values;
			while ((line = in.readLine()) != null) {
				values = line.split(",");
				cmts = new Cmts();
				cmts.setId(Integer.valueOf(values[0]));
				cmts.setName(values[1]);
				cmtss.put(cmts.getId(), cmts);
				String siteName = cmts.getName().split("\\.")[1];
				siteName = siteName.substring(0, 3);
				Site site = siteNameMapper.get(siteName);
				site.setCmtss(cmts);
			}
		} catch (FileNotFoundException ex) {
			Logger.getLogger(DashboardDataManager.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(DashboardDataManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private static void getCsmVlinks() {
		vlinks = new HashMap();
		if (csmApi == null) {
			csmApi = new CSMAPI();
		}
		try {
			List<VLinkTinyVO> vlinkList = csmApi.getAllVLinks();
			// !!!!!!!!!!!!!!! Codigo provisorio para emular vlink por cmts!!!!!!!!!!!!!!!!!!!!!!!!
			Map<Integer, Integer> mapeoVlink = new HashMap();
			mapeoVlink.put(0, 45);
			mapeoVlink.put(1, 46);
			mapeoVlink.put(2, 47);
			mapeoVlink.put(3, 6);
			mapeoVlink.put(4, 7);
			mapeoVlink.put(5, 4);
			mapeoVlink.put(6, 130);
			mapeoVlink.put(7, 131);
			mapeoVlink.put(8, 132);
			int i = 0;
			// --------------------------------------------------------------------------------------
			for (VLinkTinyVO vlink : vlinkList) {
				// !!!!!!!!!!!!!!! Codigo provisorio para emular vlink por cmts!!!!!!!!!!!!!!!!!!!!!!!!
				vlink.setVlinkId(mapeoVlink.get(i));
				i++;
				// --------------------------------------------------------------------------------------
				vlinks.put(vlink.getVlinkId(), vlink);
				// Integer cmtsId = CmtsToVlinkMapper.get(vlink);
				Cmts cmts = cmtss.get(mapeoVlink.get(i));
				if (cmts != null) {
					cmts.setVlinkId(vlink.getVlinkId());
					cmts.setVlinkPir(vlink.getPir());
				}
			}
		} catch (Exception ex) {
			Logger.getLogger(DashboardDataManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private static void getIdealVlinks() {
		for (VLinkTinyVO vlink : vlinks.values()) {
			Integer cmtsId = CmtsToVlinkMapper.get(vlink);
			Cmts cmts = cmtss.get(cmtsId);
			if (cmts != null) {
				cmts.setVlinkIdealPir(vlink.getPir());
			}
		}
	}

	private static int getUtilizacion(int vlinkId) {
		return 10;
	}

	private static void getUtilizacion() {
		for (VLinkTinyVO vlink : vlinks.values()) {
			int utilizacion = getUtilizacion(vlink.getVlinkId());
			Integer cmtsId = CmtsToVlinkMapper.get(vlink);
			Cmts cmts = cmtss.get(cmtsId);
			cmts.setUtilization(utilizacion);
		}
	}

	private static int getActuacion(int vlinkId) {
		return 4;
	}

	private static void getActuacion() {
		for (VLinkTinyVO vlink : vlinks.values()) {
			int actuacion = getActuacion(vlink.getVlinkId());
			Integer cmtsId = CmtsToVlinkMapper.get(vlink);
			Cmts cmts = cmtss.get(cmtsId);
			cmts.setUtilization(actuacion);
		}
	}

	private static void getVlinks() {
		getCsmVlinks();
		getIdealVlinks();
		getUtilizacion();
		getActuacion();
	}

	public static ArrayList<Site> getAll() {
		// if (sites == null) {
		init();
		// }
		return sites;
	}

	public static void set(VLink vlink) {
		// Cmts cmts = sites.get(vlink.siteId).getCmtss(vlink.cmtsId);
		// cmts.setVlinkPir(vlink.pir);
	}

	public static void set(ArrayList<Site> items) {
		// Site mySite;
		// Cmts myCmts;
		// for (Site site : items.) {
		// mySite = sites.get(site.getId());
		// mySite.setId(site.getId());
		// mySite.setName(site.getName());
		// mySite.setActuacion(site.getActuacion());
		// for (Cmts cmts : site.getCmtss().values()) {
		//// myCmts = mySite.getCmtss(cmts.getId());
		//// if (myCmts == null) {
		//// mySite.addCmts(cmts);
		//// } else {
		//// myCmts.setId(cmts.getId());
		//// myCmts.setName(cmts.getName());
		//// myCmts.setVlinkId(cmts.getVlinkId());
		//// myCmts.setVlinkPir(cmts.getVlinkPir());
		//// myCmts.setUtilizacion(cmts.getUtilizacion());
		//// myCmts.setActuacion(cmts.getActuacion());
		//// }
		// }
		// }
	}
}
