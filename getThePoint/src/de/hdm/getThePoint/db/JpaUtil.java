package de.hdm.getThePoint.db;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Diese Klasse verwaltet die Enititymanagerfactory als Singleton.
 * 
 * @author Fabian
 *
 */
public class JpaUtil {

	private static final String PERSISTENCEUNIT = "getthepoint";

	private static EntityManagerFactory emf = null;

	/**
	 * Getter für die Entitymanagerfactory
	 * 
	 * @return
	 */
	public static EntityManagerFactory getEmf() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory(PERSISTENCEUNIT);
		}
		return emf;
	}
}
