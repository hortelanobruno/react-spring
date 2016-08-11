package com.callistech.analytics.frontend.util.bean;

import java.util.Arrays;

public class Cmts {

	private int id;
	private String name;
	private String[] cmtss;
	private int utilization;
	private int actuacion;
	private int vlinkId;
	private Long vlinkPir;
	private Long vlinkIdealPir;

	public Cmts() {

	}

	public Cmts(int id, String name, int utilization, int actuacion, int vlinkId, Long vlinkPir, Long vlinkIdealPir) {
		this.id = id;
		this.name = name;
		this.utilization = utilization;
		this.actuacion = actuacion;
		this.vlinkId = vlinkId;
		this.vlinkPir = vlinkPir;
		this.vlinkIdealPir = vlinkIdealPir;
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

	public int getUtilization() {
		return utilization;
	}

	public void setUtilization(int utilization) {
		this.utilization = utilization;
	}

	public int getActuacion() {
		return actuacion;
	}

	public void setActuacion(int actuacion) {
		this.actuacion = actuacion;
	}

	public int getVlinkId() {
		return vlinkId;
	}

	public void setVlinkId(int vlinkId) {
		this.vlinkId = vlinkId;
	}

	public Long getVlinkPir() {
		return vlinkPir;
	}

	public void setVlinkPir(Long vlinkPir) {
		this.vlinkPir = vlinkPir;
	}

	public Long getVlinkIdealPir() {
		return vlinkIdealPir;
	}

	public void setVlinkIdealPir(Long vlinkIdealPir) {
		this.vlinkIdealPir = vlinkIdealPir;
	}

	public String[] getCmtss() {
		return cmtss;
	}

	public void setCmtss(String[] cmtss) {
		this.cmtss = cmtss;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 19 * hash + this.id;
		hash = 19 * hash + (this.name != null ? this.name.hashCode() : 0);
		hash = 19 * hash + Arrays.deepHashCode(this.cmtss);
		hash = 19 * hash + this.utilization;
		hash = 19 * hash + this.actuacion;
		hash = 19 * hash + this.vlinkId;
		hash = 19 * hash + (this.vlinkPir != null ? this.vlinkPir.hashCode() : 0);
		hash = 19 * hash + (this.vlinkIdealPir != null ? this.vlinkIdealPir.hashCode() : 0);
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
		final Cmts other = (Cmts) obj;
		if (this.id != other.id) {
			return false;
		}
		if (this.utilization != other.utilization) {
			return false;
		}
		if (this.actuacion != other.actuacion) {
			return false;
		}
		if (this.vlinkId != other.vlinkId) {
			return false;
		}
		if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
			return false;
		}
		if (!Arrays.deepEquals(this.cmtss, other.cmtss)) {
			return false;
		}
		if (this.vlinkPir != other.vlinkPir && (this.vlinkPir == null || !this.vlinkPir.equals(other.vlinkPir))) {
			return false;
		}
		if (this.vlinkIdealPir != other.vlinkIdealPir && (this.vlinkIdealPir == null || !this.vlinkIdealPir.equals(other.vlinkIdealPir))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Cmts{" + "id=" + id + ", name=" + name + ", cmtss=" + cmtss + ", utilization=" + utilization + ", actuacion=" + actuacion + ", vlinkId=" + vlinkId + ", vlinkPir=" + vlinkPir + ", vlinkIdealPir=" + vlinkIdealPir + '}';
	}

}
