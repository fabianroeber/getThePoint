package de.hdm.getThePoint.ldap;

import java.security.GeneralSecurityException;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchScope;
import com.unboundid.util.ssl.SSLUtil;
import com.unboundid.util.ssl.TrustAllTrustManager;

/**
 * Diese Klasse regelt den Zugriff auf den Hochschul LDAP-Server.
 * 
 * @author Fabian
 *
 */
public class LdapAuthentificator {
	/**
	 * 
	 * @param ldapUserId
	 * @param password
	 * @return
	 * @throws LDAPException
	 * @throws GeneralSecurityException
	 */
	public String authenticate(String ldapUserId, String password)
			throws LDAPException, GeneralSecurityException {

		String userName = null;
		boolean ldapUserIdAuthenticated = false;

		LDAPConnection ldaps = null;

		ldaps = this.customLDAPConnection(ldapUserId, password);
		if (ldaps != null) {
			ldapUserIdAuthenticated = true;
		}

		SearchResult sr = null;
		try {
			if (ldapUserIdAuthenticated) {

				// Der Suchstring muss wie ben�tigt angepasst werden
				sr = ldaps.search("ou=People,o=group", SearchScope.SUB, "(uid="
						+ ldapUserId + ")");
				// cn = full name
				userName = sr.getSearchEntries().get(0).getAttribute("cn")
						.getValue();
				System.out.println("Logged in as " + userName);
			}
		} catch (Exception e) {
			System.out.println("Start LDAP Search failed: " + ldapUserId);
			e.printStackTrace();
			System.out.println("End LDAP Search failed: " + ldapUserId);

			ldapUserIdAuthenticated = false;
		}

		return userName;
	}

	private LDAPConnection customLDAPConnection(String ldapUserId,
			String password) {
		// trusts any certificate
		// https://www.unboundid.com/products/ldap-sdk/docs/javadoc/com/unboundid/util/ssl/SSLUtil.html
		SSLUtil sslUtil = new SSLUtil(new TrustAllTrustManager());
		LDAPConnection ldaps = null;
		try {
			// Der Servername und uid Suchstring muss wie ben�tigt angepasst
			// werden
			ldaps = new LDAPConnection(sslUtil.createSSLSocketFactory(),
					"ldap.hdm-stuttgart.de", 389, "uid=" + ldapUserId
							+ ",ou=userlist,dc=hdm-stuttgart,dc=de", password);
		} catch (Exception e) {
			System.out.println("Start LDAP Authentication failed: "
					+ ldapUserId);
			e.printStackTrace();
			System.out.println("End LDAP Authentication failed: " + ldapUserId);
		}
		return ldaps;
	}
}
