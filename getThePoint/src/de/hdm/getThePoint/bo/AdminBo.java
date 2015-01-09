package de.hdm.getThePoint.bo;


public class AdminBo extends BusinessObject {

	private static final long serialVersionUID = 3102384512729801773L;

	private String login;

	private String passwort;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

}
