package com.callistech.analytics.frontend.util.bean;

public class VLink {

	public int id;
	public long pir;
	public int siteId;
	public int cmtsId;

	public VLink() {
	}

	public VLink(int id, long pir, int siteId, int cmtsId) {
		this.id = id;
		this.pir = pir;
		this.siteId = siteId;
		this.cmtsId = cmtsId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getPir() {
		return pir;
	}

	public void setPir(long pir) {
		this.pir = pir;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getCmtsId() {
		return cmtsId;
	}

	public void setCmtsId(int cmtsId) {
		this.cmtsId = cmtsId;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 83 * hash + this.id;
		hash = 83 * hash + (int) (this.pir ^ (this.pir >>> 32));
		hash = 83 * hash + this.siteId;
		hash = 83 * hash + this.cmtsId;
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
		final VLink other = (VLink) obj;
		if (this.id != other.id) {
			return false;
		}
		if (this.pir != other.pir) {
			return false;
		}
		if (this.siteId != other.siteId) {
			return false;
		}
		if (this.cmtsId != other.cmtsId) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "VLink{" + "id=" + id + ", pir=" + pir + ", siteId=" + siteId + ", cmtsId=" + cmtsId + '}';
	}

}
