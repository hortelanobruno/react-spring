package com.callistech.analytics.frontend.util.bean;

import com.callistech.analytics.frontend.util.enums.Actions;
import com.callistech.analytics.frontend.util.enums.DataType;

/**
 *
 * @author callis
 */
public class Transaction {

	public String data;
	public Actions action;
	public DataType dataType;

	public Transaction() {
	}

	public Transaction(String data, Actions action, DataType dataType) {
		this.data = data;
		this.action = action;
		this.dataType = dataType;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Actions getAction() {
		return action;
	}

	public void setAction(Actions action) {
		this.action = action;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 59 * hash + (this.data != null ? this.data.hashCode() : 0);
		hash = 59 * hash + (this.action != null ? this.action.hashCode() : 0);
		hash = 59 * hash + (this.dataType != null ? this.dataType.hashCode() : 0);
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
		final Transaction other = (Transaction) obj;
		if ((this.data == null) ? (other.data != null) : !this.data.equals(other.data)) {
			return false;
		}
		if (this.action != other.action) {
			return false;
		}
		if (this.dataType != other.dataType) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Transaction{" + "data=" + data + ", action=" + action + ", dataType=" + dataType + '}';
	}

}
