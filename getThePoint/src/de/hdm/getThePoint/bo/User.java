package de.hdm.getThePoint.bo;

import de.hdm.getThePoint.db.dbmodel.Admin;

public class User extends BusinessObject {

	private static final long serialVersionUID = 691932732265636058L;

	private boolean isLoggedIn;

	private StudentBo student;

	private LehrenderBo lehrender;

	private Admin admin;

	public StudentBo getStudent() {
		return student;
	}

	public void setStudent(StudentBo student) {
		this.student = student;
	}

	public LehrenderBo getLehrender() {
		return lehrender;
	}

	public void setLehrender(LehrenderBo lehrender) {
		this.lehrender = lehrender;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

}
