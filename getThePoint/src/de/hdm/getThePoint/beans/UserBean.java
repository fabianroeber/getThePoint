package de.hdm.getThePoint.beans;

import java.security.GeneralSecurityException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.unboundid.ldap.sdk.LDAPException;

import de.hdm.getThePoint.bo.LehrenderBo;
import de.hdm.getThePoint.bo.StudentBo;
import de.hdm.getThePoint.db.dbmodel.Admin;
import de.hdm.getThePoint.ldap.LdapAuthentificator;

/**
 * Diese Klassen verwaltet das An- und Abmelden von Benutzern und stellt
 * Informationen &uuml;ber den aktuell angemeldeten Benutzer beresit.
 * 
 * @author Fabian
 *
 */
@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean {

	@ManagedProperty(value = "#{mitarbeiterBo}")
	private StudentBo mitarbeiterBo;

	private LehrenderBo lehrenderBo;

	private Admin admin;

	LdapAuthentificator ldapAuthentificator;

	public UserBean() {
		ldapAuthentificator = new LdapAuthentificator();
	}

	private String userName;

	private String password;

	public String login() {

		if (userName != null && password != null) {

			if (userName.equalsIgnoreCase("devmode")) {

				// dummy user erstellen

				return "/content_mobile.xhtml";

			} else {

				try {
					String username = ldapAuthentificator.authenticate(
							userName, password);

					// Datenbankzugriff auf Tabelle lehrender oder student TODO

				} catch (LDAPException | GeneralSecurityException e) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Error",
									"Nutzer konnte nicht autorisiert werden"));
					return "";

				}

			}

		}
		return "/content_mobile.xhtml";

	}

	public String logout() {
		// Logoug Sachen machen
		return "/logout.xhtml"; // TODO erstellen
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public StudentBo getMitarbeiterBo() {
		return mitarbeiterBo;
	}

	public void setMitarbeiterBo(StudentBo mitarbeiterBo) {
		this.mitarbeiterBo = mitarbeiterBo;
	}

	public LehrenderBo getLehrenderBo() {
		return lehrenderBo;
	}

	public void setLehrenderBo(LehrenderBo lehrenderBo) {
		this.lehrenderBo = lehrenderBo;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

}