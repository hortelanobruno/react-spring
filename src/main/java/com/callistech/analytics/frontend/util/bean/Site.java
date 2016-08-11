package com.callistech.analytics.frontend.util.bean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author callis
 */
public class Site {

	private int id;
	private String name;
	private Long actuacion;
	private String idealProvisioning;
	private String actualProvisioning;
	private String[] cmtsss;
	private Map<Integer, Cmts> cmtss = new HashMap<Integer, Cmts>();

	public Site() {
	}

	public Site(int id, String name, Long actuacion, String idealProvisioning, String actualProvisioning) {
		this.id = id;
		this.name = name;
		this.actuacion = actuacion;
		this.idealProvisioning = idealProvisioning;
		this.actualProvisioning = actualProvisioning;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getActuacion() {
		return actuacion;
	}

	public void setActuacion(Long actuacion) {
		this.actuacion = actuacion;
	}

	public String getIdealProvisioning() {
		return idealProvisioning;
	}

	public void setIdealProvisioning(String idealProvisioning) {
		this.idealProvisioning = idealProvisioning;
	}

	public String getActualProvisioning() {
		return actualProvisioning;
	}

	public void setActualProvisioning(String actualProvisioning) {
		this.actualProvisioning = actualProvisioning;
	}

	public Map<Integer, Cmts> getCmtss() {
		return cmtss;
	}

	public void setCmtss(Cmts cmts) {
		cmtss.put(cmts.getId(), cmts);
	}

	public String[] getCmtsss() {
		return cmtsss;
	}

	public void setCmtsss(String[] cmtsss) {
		this.cmtsss = cmtsss;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 97 * hash + this.id;
		hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
		hash = 97 * hash + (this.actuacion != null ? this.actuacion.hashCode() : 0);
		hash = 97 * hash + (this.idealProvisioning != null ? this.idealProvisioning.hashCode() : 0);
		hash = 97 * hash + (this.actualProvisioning != null ? this.actualProvisioning.hashCode() : 0);
		hash = 97 * hash + Arrays.deepHashCode(this.cmtsss);
		hash = 97 * hash + (this.cmtss != null ? this.cmtss.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Site other = (Site) obj;
		if (this.id != other.id) {
			return false;
		}
		if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
			return false;
		}
		if ((this.idealProvisioning == null) ? (other.idealProvisioning != null) : !this.idealProvisioning.equals(other.idealProvisioning)) {
			return false;
		}
		if ((this.actualProvisioning == null) ? (other.actualProvisioning != null) : !this.actualProvisioning.equals(other.actualProvisioning)) {
			return false;
		}
		if (this.actuacion != other.actuacion && (this.actuacion == null || !this.actuacion.equals(other.actuacion))) {
			return false;
		}
		if (!Arrays.deepEquals(this.cmtsss, other.cmtsss)) {
			return false;
		}
		if (this.cmtss != other.cmtss && (this.cmtss == null || !this.cmtss.equals(other.cmtss))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Site{" + "id=" + id + ", name=" + name + ", actuacion=" + actuacion + ", idealProvisioning=" + idealProvisioning + ", actualProvisioning=" + actualProvisioning + ", cmtsss=" + cmtsss + ", cmtss=" + cmtss + '}';
	}

}
