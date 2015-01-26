package de.hdm.getThePoint.db;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import de.hdm.getThePoint.bo.WissenstestBo;
import de.hdm.getThePoint.db.dbmodel.Admin;
import de.hdm.getThePoint.db.dbmodel.Ergebnis;
import de.hdm.getThePoint.db.dbmodel.Frage;
import de.hdm.getThePoint.db.dbmodel.Kategorie;
import de.hdm.getThePoint.db.dbmodel.Lehrender;
import de.hdm.getThePoint.db.dbmodel.Student;
import de.hdm.getThePoint.db.dbmodel.Wissenstest;

/**
 * Klasse f&uuml;r alle Datenbankzugriffe. Die Entitymanager Factory wird einmal
 * ge&ouml;ffnet, der EntityManager f&uuml;r jeden Transaktion. Er darf nur bei
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
	 * Methode zum Abrufen aller {@link Kategorie}
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	public List<Kategorie> getAllKategorie() throws PersistenceException {

		entityManager = entityManagerFactory.createEntityManager();

		List<Kategorie> list = entityManager.createQuery(
				"Select kategorie FROM Kategorie kategorie", Kategorie.class)
				.getResultList();

		return list;
	}

	/**
	 * Methode zum Abrufen aller Fragen
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	public List<Frage> getAllFrage() throws PersistenceException {

		entityManager = entityManagerFactory.createEntityManager();

		List<Frage> list = entityManager.createQuery(
				"SELECT frage FROM Frage frage", Frage.class).getResultList();
		return list;

	}

	/**
	 * Methode zum Abrufen aller Lehrenden
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	public List<Lehrender> getAllLehrende() throws PersistenceException {

		entityManager = entityManagerFactory.createEntityManager();

		List<Lehrender> list = entityManager.createQuery(
				"SELECT lehrender FROM Lehrender lehrender", Lehrender.class)
				.getResultList();
		return list;

	}

	/**
	 * Methode zum Abrufen aller Ergebnisse
	 * 
	 * @return
	 * @throws PersistenceException
	 */
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
	public Student getStudentByKuerzel(String kuerzel)
			throws PersistenceException {

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
	public Lehrender getLehrenderByKuerzel(String kuerzel)
			throws PersistenceException {

		entityManager = entityManagerFactory.createEntityManager();

		Lehrender lehrender = entityManager.createQuery(
				"Select lehrender FROM Lehrender lehrender where lehrender.kuerzel = '"
						+ kuerzel + "'", Lehrender.class).getSingleResult();

		entityManager.close();

		return lehrender;

	}

	/**
	 * Ermittelt einen Adminuser.
	 * 
	 * @param name
	 * @return
	 * @throws PersistenceException
	 */
	public Admin getAdminByUserName(String name) throws PersistenceException {

		entityManager = entityManagerFactory.createEntityManager();

		Admin admin = entityManager.createQuery(
				"Select admin FROM Admin admin WHERE admin.login = '" + name
						+ "'", Admin.class).getSingleResult();

		entityManager.close();

		return admin;

	}

	/**
	 * Methode zum Speichern eines Studenten.
	 * 
	 * @param dbModel
	 */
	public void saveStudent(Student student) {
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		Student studentToSave;

		if (student.getId() != null) {
			studentToSave = entityManager.find(Student.class, student.getId());
		} else {
			studentToSave = new Student();
		}
		studentToSave.setKuerzel(student.getKuerzel());
		studentToSave.setMatrikelNr(student.getMatrikelNr());
		studentToSave.setName(student.getName());
		studentToSave.setVorname(student.getVorname());

		entityManager.persist(studentToSave);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	/**
	 * Methode zum Speichern eines Lehrenden
	 * 
	 * @param lehrender
	 * @throws PersistenceException
	 */
	public void saveLehrender(Lehrender lehrender) throws PersistenceException {

		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		Lehrender lehrenderToSave;

		if (lehrender.getId() != null) {
			lehrenderToSave = entityManager.find(Lehrender.class,
					lehrender.getId());
		} else {
			lehrenderToSave = new Lehrender();
		}
		lehrenderToSave.setName(lehrender.getName());
		lehrenderToSave.setVorname(lehrender.getVorname());
		lehrenderToSave.setKuerzel(lehrender.getKuerzel());

		entityManager.persist(lehrenderToSave);
		entityManager.getTransaction().commit();
		entityManager.close();

	}

	/**
	 * Methode zum L&ouml;schen eines Lehrenden.
	 * 
	 * @param lehrender
	 * @throws PersistenceException
	 */
	public void deleteLehrender(Lehrender lehrender)
			throws PersistenceException {
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.contains(lehrender) ? lehrender
				: entityManager.merge(lehrender));
		entityManager.getTransaction().commit();
		entityManager.close();
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
