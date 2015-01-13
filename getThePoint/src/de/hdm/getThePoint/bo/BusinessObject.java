package de.hdm.getThePoint.bo;

import java.io.Serializable;

public class BusinessObject implements Serializable {

	private static final long serialVersionUID = -3544159520355754839L;

	private Integer id;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
	}

	@Override
	public boolean equals(Object o) {

		if (o != null && o instanceof BusinessObject) {
			BusinessObject a = (BusinessObject) o;
			try {
				if (a.getId() == this.id)
					return true;
			} catch (IllegalArgumentException e) {

				return false;
			}
		}

		return false;
	}

	@Override
	public int hashCode() {
		return this.id;
	}

}
