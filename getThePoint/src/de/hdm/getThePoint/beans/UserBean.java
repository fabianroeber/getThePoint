package de.hdm.getThePoint.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import de.hdm.getThePoint.bo.LehrenderBo;
import de.hdm.getThePoint.bo.StudentBo;
import de.hdm.getThePoint.db.dbmodel.Admin;
import de.hdm.getThePoint.db.mapper.LehrenderMapper;
import de.hdm.getThePoint.db.mapper.StudentMapper;
import de.hdm.getThePoint.ldap.LdapAuthentificator;

/**
 * Diese Klassen verwaltet das An- und Abmelden von Benutzern und stellt
 * Informationen &uuml;ber den aktuell angemeldeten Benutzer bereit.
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

	private Admin admin;

	private LehrenderBo lehrender;

	private StudentBo student;

	private LehrenderMapper lehrenderMapper;

	private StudentMapper studentMapper;

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
	 * Konstrukor l&auml;dt Mapper und LDAP Schnittstelle.
	 */
	public UserBean() {
		lehrenderMapper = new LehrenderMapper();
		studentMapper = new StudentMapper();
		ldapAuthentificator = new LdapAuthentificator();
	}

	/**
	 * Diese Methode regelt die Authentifizierung des Benutzers. Dabei wird
	 * zun&auml;chst versucht, den Benutzer als Admin zu identifizieren. Ist der
	 * User kein Admin, wird der LDAP Login angestoßen. Ist dieser erfolgreich,
	 * wird abgefragt, ob der Benutzer die Rolle eines Lehrenden hat. Wenn ja,
	 * wird er als Lehrender eingeloggt. Wenn nicht, wird er als Student
	 * eingeloggt.
	 * 
	 * Für die Entwicklung der Applikation sind hier zwei Testuser (1 Lehrender
	 * und 1 Student) implementiert, die sich unabhängig vom LDAP anmelden
	 * können.
	 */
	public String login(boolean mobile) {

		if (userName != null && password != null) {

			// Dieser Teil dient nur zum Testen
			if (userName.equalsIgnoreCase("testlehrender")) {

				loggedIn = true;
				checkLehrender();
				if (mobile) {
					return navigationBean.redirectToMobileWelcome();
				}

				return navigationBean.redirectToWelcomeVeraltung();

			}
			// Dieser Teil dient nur zum Testen
			else if (userName.equalsIgnoreCase("teststudent")) {
				loggedIn = true;
				organizeStudentUserData();
				if (mobile) {
					return navigationBean.redirectToMobileWelcome();
				}
				return navigationBean.redirectToWelcome();

			} else if (userName.equalsIgnoreCase("admin")) {
				try {
					admin = dataAccessBean.getDataAccess().getAdminByUserName(
							userName);
					if (password.equals(admin.getPasswort())) {
						loggedIn = true;
						return navigationBean.redirectToAdmin();
					}
				} catch (PersistenceException e) {
					e.printStackTrace();
					loginFailed();
				}
			} else {
				// LDAP CHECK
				// try {
				// String ldapuser = ldapAuthentificator.authenticate(
				// userName, password);

				if (userName != null) {
					loggedIn = true;

					// checkLehrender();
					if (lehrender == null) {
						organizeStudentUserData();
						if (mobile) {
							return navigationBean.redirectToMobileWelcome();
						}
						return navigationBean.redirectToWelcome();
					}
					if (mobile) {
						return navigationBean.redirectToMobileWelcome();
					}
					return navigationBean.redirectToWelcome();
				}

				// } catch (LDAPException | GeneralSecurityException e) {
				// e.printStackTrace();
				// loginFailed();
				// }

			}

		}
		return "";

	}

	/**
	 * Fehlermeldung, wenn der LDAP Login gescheitert ist.
	 */
	private void loginFailed() {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
						"Nutzer konnte nicht autorisiert werden"));
		loggedIn = false;
	}

	/**
	 * Versucht, einen Lehrenden aus der Datenbank zu ermitteln.
	 */
	private void checkLehrender() {
		lehrender = lehrenderMapper.getModel(dataAccessBean.getDataAccess()
				.getLehrenderByKuerzel(userName));
	}

	/**
	 * Diese Methode organisiert das laden der Nutzerdaten. Sie sucht nach der
	 * erfolgreichen Authentifizierung in der Datenbank, ob der Student bereits
	 * vorhanden ist. Ist dies nicht der Fall, wird ein neuer Student in der
	 * Datenbank persistiert.
	 */
	private void organizeStudentUserData() {

		try {
			student = studentMapper.getModel(dataAccessBean.getDataAccess()
					.getStudentByKuerzel(userName));
		} catch (NoResultException e) {
			registerNewUser();
		}
	}

	/**
	 * Meldet den Benutzer von der Anwendung ab.
	 * 
	 * @return
	 */
	public String logout(boolean mobile) {
		loggedIn = false;
		lehrender = null;
		admin = null;
		student = null;
		if (mobile) {
			return navigationBean.redirectToMobileLogout();
		}

		return navigationBean.redirectToLogout();
	}

	/**
	 * Diese Methode schreibt einen neuen Studenten in die Datenbank.
	 */
	private void registerNewUser() {
		student = new StudentBo();
		student.setKuerzel(userName);
		dataAccessBean.getDataAccess().saveStudent(
				studentMapper.getDbModel(student));
		student = studentMapper.getModel(dataAccessBean.getDataAccess()
				.getStudentByKuerzel(userName));
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

	public boolean isLoggedIn() {

		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
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

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public LehrenderBo getLehrender() {
		return lehrender;
	}

	public void setLehrender(LehrenderBo lehrender) {
		this.lehrender = lehrender;
	}

	public StudentBo getStudent() {
		return student;
	}

	public void setStudent(StudentBo student) {
		this.student = student;
	}

}
