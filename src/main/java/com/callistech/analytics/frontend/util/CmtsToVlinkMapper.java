package com.callistech.analytics.frontend.util;

import java.util.HashMap;
import java.util.Map;

import com.callistech.analytics.frontend.util.bean.Cmts;
import com.callistech.policyserver.psm.integration.rest.vo.VLinkTinyVO;

public class CmtsToVlinkMapper {

	private static Map<Integer, Integer> vlinkToCmts = null;
	private static Map<Integer, Integer> cmtsToVlink = null;

	private static void initMaps() {
		vlinkToCmts = new HashMap();
		cmtsToVlink = new HashMap();

	}

	public static void put(Integer vlinkId, Integer cmtsId) {
		if (cmtsToVlink == null) {
			initMaps();
		}
		vlinkToCmts.put(vlinkId, cmtsId);
		cmtsToVlink.put(cmtsId, vlinkId);
	}

	public static Integer get(Cmts cmts) {
		if (cmtsToVlink == null) {
			initMaps();
		}
		Integer vlinkId = cmtsToVlink.get(cmts.getVlinkId());
		return vlinkId;
	}

	public static Integer get(VLinkTinyVO vlink) {
		Integer cmtsId = vlinkToCmts.get(vlink.getVlinkId());
		return cmtsId;
	}
}
