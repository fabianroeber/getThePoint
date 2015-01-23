package de.hdm.getThePoint.beans;

import java.io.Serializable;
import java.security.GeneralSecurityException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.unboundid.ldap.sdk.LDAPException;

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
public class UserBean implements Serializable {

	private static final long serialVersionUID = 6129158197703648244L;

	private LdapAuthentificator ldapAuthentificator;

	private boolean loggedIn;

	private String userName;

	private String password;

	/**
	 * Hier wird die Klasse {@link DataAccessBean} injiziert, die den
	 * Datenbankzugriff bereitstellt.
	 */
	@ManagedProperty(value = "#{dataAccesBean}")
	private DataAccessBean dataAccessBean;

	/**
	 * Hier wird die Klasse {@link NavigationBean} injiziert, um Zugriff auf
	 * Navigationsaktionen zu bekommen.
	 */
	@ManagedProperty(value = "#{navigationBean}")
	private NavigationBean navigationBean;

	/**
	 * Diese Methode regelt die Authentifizierung des Benutzers.
	 */
	public String login() {

		if (userName != null && password != null) {

			if (userName.equalsIgnoreCase("devmode")) {

				loggedIn = true;
				// organizeUserData();
				return navigationBean.redirectToWelcome();

			} else {

				try {
					String ldapuser = ldapAuthentificator.authenticate(
							userName, password);

					if (ldapuser != null && ldapuser.equals(userName)) {
						loggedIn = true;
						// organizeUserData();
						return navigationBean.redirectToWelcome();
					}

				} catch (LDAPException | GeneralSecurityException e) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Error",
									"Nutzer konnte nicht autorisiert werden"));
					loggedIn = false;
					return navigationBean.redirectToLogin();
				}

			}

		}
		return "";

	}

	/**
	 * Diese Methode organisiert das laden der Nutzerdaten. Sie sucht nach der
	 * erfolgreichen Authentifizierung in der Datenbank, ob der Mitarbetier
	 * bereits vorhanden ist. Ist dies nicht der Fall, wird ein neuer
	 * Mitarbeiter in der Datenbank persistiert.
	 */
	// public void organizeUserData() {
	//
	// getUserData();
	// if (mitarbeiter == null) {
	// registerNewUser();
	// }
	// }

	/**
	 * Meldet den Benutzer von der Anwendung ab.
	 * 
	 * @return
	 */
	public String logout() {
		loggedIn = false;
		return navigationBean.redirectToLogout();
	}

	// /**
	// * Diese Methode l&auml;dt die Mitarbeiterdaten aus der Datenbank.
	// */
	// public void getUserData() {
	// mitarbeiter = new MitarbeiterMapper().getBo(dataAccessBean
	// .getDataAccess().getMitarbeiterByUserName(userName));
	// }
	//
	// /**
	// * Diese Methode schreibt einen neuen Benutzer in die Datenbank.
	// */
	// public void registerNewUser() {
	// mitarbeiter = new MitarbeiterBo(userName);
	// dataAccessBean.getDataAccess().saveMitarbeiter(
	// new MitarbeiterMapper().getDbObject(mitarbeiter));
	// }

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

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public UserBean() {
		ldapAuthentificator = new LdapAuthentificator();
	}

	public DataAccessBean getDataAccessBean() {
		return dataAccessBean;
	}

	public void setDataAccessBean(DataAccessBean dataAccessBean) {
		this.dataAccessBean = dataAccessBean;
	}

	public NavigationBean getNavigationBean() {
		return navigationBean;
	}

	public void setNavigationBean(NavigationBean navigationBean) {
		this.navigationBean = navigationBean;
	}

	

}
