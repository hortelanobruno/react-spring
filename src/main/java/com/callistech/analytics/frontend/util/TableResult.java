package com.callistech.analytics.frontend.util;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author brunoli
 */
public class TableResult implements Serializable {

	private String draw;
	private Integer recordsTotal;
	private Integer recordsFiltered;
	private String[][] data;
	private Object aaData;

	public TableResult() {
	}

	public TableResult(String draw, Integer recordsTotal, Integer recordsFiltered, String[][] data, Object aaData) {
		this.draw = draw;
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsFiltered;
		this.data = data;
		this.aaData = aaData;
	}

	public String getDraw() {
		return draw;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}

	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public String[][] getData() {
		return data;
	}

	public void setData(String[][] data) {
		this.data = data;
	}

	public Object getAaData() {
		return aaData;
	}

	public void setAaData(Object aaData) {
		this.aaData = aaData;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 17 * hash + (this.draw != null ? this.draw.hashCode() : 0);
		hash = 17 * hash + (this.recordsTotal != null ? this.recordsTotal.hashCode() : 0);
		hash = 17 * hash + (this.recordsFiltered != null ? this.recordsFiltered.hashCode() : 0);
		hash = 17 * hash + Arrays.deepHashCode(this.data);
		hash = 17 * hash + (this.aaData != null ? this.aaData.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TableResult other = (TableResult) obj;
		if ((this.draw == null) ? (other.draw != null) : !this.draw.equals(other.draw)) {
			return false;
		}
		if (this.recordsTotal != other.recordsTotal && (this.recordsTotal == null || !this.recordsTotal.equals(other.recordsTotal))) {
			return false;
		}
		if (this.recordsFiltered != other.recordsFiltered && (this.recordsFiltered == null || !this.recordsFiltered.equals(other.recordsFiltered))) {
			return false;
		}
		if (!Arrays.deepEquals(this.data, other.data)) {
			return false;
		}
		if (this.aaData != other.aaData && (this.aaData == null || !this.aaData.equals(other.aaData))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "TableResult{" + "draw=" + draw + ", recordsTotal=" + recordsTotal + ", recordsFiltered=" + recordsFiltered + ", data=" + data + ", aaData=" + aaData + '}';
	}

}
