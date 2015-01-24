package de.hdm.getThePoint.db;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import de.hdm.getThePoint.bo.WissenstestBo;
import de.hdm.getThePoint.db.dbmodel.Ergebnis;
import de.hdm.getThePoint.db.dbmodel.Frage;
import de.hdm.getThePoint.db.dbmodel.Kategorie;
import de.hdm.getThePoint.db.dbmodel.Lehrender;
import de.hdm.getThePoint.db.dbmodel.Student;
import de.hdm.getThePoint.db.dbmodel.Wissenstest;

/**
 * Klasse f&uuml;r alle Datenbankzugriffe. Die Entitymanager Factory wird einmal
 * ge%oouml;ffnet, der EntityManager f&uuml;r jeden Transaktion. Er darf nur bei
 * "Singleresults" geschlossen werden, da sonst die Funktion des Lazy Loading
 * nicht funktioniert.
 * 
 * @author Fabian
 *
 */
public class DataAccess implements Serializable {

	private static final long serialVersionUID = -7993963883289903662L;

	private EntityManagerFactory entityManagerFactory = null;

	private EntityManager entityManager = null;

	private static final String PERSISTENCEUNIT = "getthepoint";

	public DataAccess() {
		getEntityManagerFactory();
	}

	/**
	 * Methode zum Abrufen aller {@link Wissenstest}.
	 * 
	 * @return
	 */
	public List<Wissenstest> getAllWissentests() throws PersistenceException {

		entityManager = entityManagerFactory.createEntityManager();

		List<Wissenstest> list = entityManager.createQuery(
				"Select wissenstest FROM Wissenstest wissenstest",
				Wissenstest.class).getResultList();
		return list;

	}

/**
	 * Methode zum Abrugen aller {@link Kategorie).
	 * @return
	 */
	public List<Kategorie> getAllKategorie() throws PersistenceException {

		entityManager = entityManagerFactory.createEntityManager();

		List<Kategorie> list = entityManager.createQuery(
				"Select kategorie FROM Kategorie kategorie", Kategorie.class)
				.getResultList();

		return list;
	}

	public List<Frage> getAllFrage() throws PersistenceException {

		entityManager = entityManagerFactory.createEntityManager();

		List<Frage> list = entityManager.createQuery(
				"SELECT frage FROM Frage frage", Frage.class).getResultList();
		return list;

	}

	public List<Lehrender> getAllLehrende() throws PersistenceException {

		entityManager = entityManagerFactory.createEntityManager();

		List<Lehrender> list = entityManager.createQuery(
				"SELECT lehrender FROM Lehrender lehrender", Lehrender.class)
				.getResultList();
		return list;

	}

	public List<Ergebnis> getAllErgebnisse() throws PersistenceException {

		entityManager = entityManagerFactory.createEntityManager();

		List<Ergebnis> list = entityManager.createQuery(
				"SELECT ergebnis FROM Ergebnis ergebnis", Ergebnis.class)
				.getResultList();
		return list;

	}

	public List<Ergebnis> getErgebnisseByWissenstest(
			WissenstestBo selektierterWissenstest) throws PersistenceException {

		entityManager = entityManagerFactory.createEntityManager();

		List<Ergebnis> list = entityManager.createQuery(
				"SELECT ergebnis FROM Ergebnis ergebnis where ergebnis.wissenstest = "
						+ selektierterWissenstest.getId()
						+ " ORDER BY ergebnis.student.id", Ergebnis.class)
				.getResultList();
		return list;

	}

	public List<Ergebnis> getErgebnisseByWissenstestOrderByFrageUndRichtig(
			WissenstestBo selektierterWissenstest) throws PersistenceException {

		entityManager = entityManagerFactory.createEntityManager();

		List<Ergebnis> list = entityManager.createQuery(
				"SELECT ergebnis FROM Ergebnis ergebnis where ergebnis.wissenstest = "
						+ selektierterWissenstest.getId()
						+ " ORDER BY ergebnis.frage.id, ergebnis.richtig",
				Ergebnis.class).getResultList();
		return list;

	}

	public List<Frage> getFragenByKategorie(int kategorie_id)
			throws PersistenceException {

		entityManager = entityManagerFactory.createEntityManager();

		List<Frage> list = entityManager.createQuery(
				"Select frage FROM Frage frage where frage.kategorie = "
						+ kategorie_id, Frage.class).getResultList();

		return list;
	}

	/**
	 * Ermittelt einen Studenten anhand seines Kuerzels.
	 * 
	 * @param kuerzel
	 * @return
	 * @throws PersistenceException
	 */
	public Student getStudentByKuerzel(int kuerzel) throws PersistenceException {

		entityManager = entityManagerFactory.createEntityManager();

		Student student = entityManager.createQuery(
				"Select student FROM Student student where student.kuerzel = '"
						+ kuerzel + "'", Student.class).getSingleResult();

		entityManager.close();

		return student;
	}

	/**
	 * Ermittelt einen Lehrenden anhand seines Kuerzels.s
	 * 
	 * @param kuerzel
	 * @return
	 * @throws PersistenceException
	 */
	public Lehrender getLehrenderByKuerzel(int kuerzel)
			throws PersistenceException {

		entityManager = entityManagerFactory.createEntityManager();

		Lehrender lehrender = entityManager.createQuery(
				"Select lehrender FROM Lehrender lehrender where lehrender.kuerzel = '"
						+ kuerzel + "'", Lehrender.class).getSingleResult();

		entityManager.close();

		return lehrender;

	}

	/**
	 * Erstellt eine neue {@link EntityManagerFactory} und einen neuen
	 * {@link EntityManager}.
	 * 
	 * @return
	 */
	private void getEntityManagerFactory() {
		entityManagerFactory = Persistence
				.createEntityManagerFactory(PERSISTENCEUNIT);

	}

	/**
	 * Schlie�t den {@link EntityManager} und die {@link EntityManagerFactory};
	 */
	public void closeEntityManagerFactory() {
		entityManager.close();
		entityManagerFactory.close();
	}

}
