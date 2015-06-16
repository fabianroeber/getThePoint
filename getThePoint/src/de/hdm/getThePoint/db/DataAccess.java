package de.hdm.getThePoint.db;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import de.hdm.getThePoint.bo.WissenstestBo;
import de.hdm.getThePoint.db.dbmodel.Admin;
import de.hdm.getThePoint.db.dbmodel.Antwort;
import de.hdm.getThePoint.db.dbmodel.Ergebnis;
import de.hdm.getThePoint.db.dbmodel.Frage;
import de.hdm.getThePoint.db.dbmodel.Kategorie;
import de.hdm.getThePoint.db.dbmodel.Lehrender;
import de.hdm.getThePoint.db.dbmodel.Student;
import de.hdm.getThePoint.db.dbmodel.Wissenstest;
import de.hdm.getThePoint.db.dbmodel.ZuordungWissenstestFrage;

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

	private EntityManagerFactory emf = null;

	private EntityManager em = null;

	public DataAccess() {
		emf = JpaUtil.getEmf();
	}

	/**
	 * Methode zum Abrufen aller {@link Wissenstest}.
	 * 
	 * @return
	 */
	public List<Wissenstest> getAllWissentests() throws PersistenceException {

		em = emf.createEntityManager();

		List<Wissenstest> list = em.createQuery(
				"Select wissenstest FROM Wissenstest wissenstest",
				Wissenstest.class).getResultList();
		return list;

	}

	/**
	 * Methode zum Abrufen aller {@link Wissenstest} eines Studenten, für
	 * welchen Ergebnisse vorhanden sind.
	 * 
	 * @return
	 */
	public List<Wissenstest> getWissentestsByStudentWithErgebnis(Student student)
			throws PersistenceException {

		em = emf.createEntityManager();

		List<Wissenstest> list = em
				.createQuery(
						"SELECT DISTINCT wissenstest FROM Wissenstest wissenstest, Ergebnis ergebnis WHERE wissenstest MEMBER OF ergebnis.wissenstest AND ergebnis.student.id = "
								+ student.getId(), Wissenstest.class)
				.getResultList();
		return list;
	}

	/**
	 * Methode zum Abrufen aller {@link Wissenstest} eines Lehrenden, für
	 * welchen keine Ergebnisse vorhanden sind.
	 * 
	 * @return
	 */
	public List<Wissenstest> getWissentestsByLehrenderWithoutErgebnis(
			Lehrender lehrender) throws PersistenceException {

		em = emf.createEntityManager();

		List<Wissenstest> list = em
				.createQuery(
						"SELECT DISTINCT wissenstest FROM Wissenstest wissenstest, Ergebnis ergebnis WHERE wissenstest NOT MEMBER OF ergebnis.wissenstest AND wissenstest.lehrender.id = "
								+ lehrender.getId(), Wissenstest.class)
				.getResultList();
		return list;
	}

	/**
	 * Methode zum Abrufen aller {@link Wissenstest} eines Lehrenden, für
	 * welchen Ergebnisse vorhanden sind.
	 * 
	 * @return
	 */
	public List<Wissenstest> getWissentestsByLehrenderWithErgebnis(
			Lehrender lehrender) throws PersistenceException {

		em = emf.createEntityManager();

		List<Wissenstest> list = em
				.createQuery(
						"SELECT DISTINCT wissenstest FROM Wissenstest wissenstest, Ergebnis ergebnis WHERE wissenstest MEMBER OF ergebnis.wissenstest AND wissenstest.lehrender.id = "
								+ lehrender.getId(), Wissenstest.class)
				.getResultList();
		return list;
	}

	/**
	 * Methode zum Abrufen aller {@link Wissenstest}, für welchen Ergebnisse
	 * vorhanden sind.
	 * 
	 * @return
	 */
	public List<Wissenstest> getWissentestsWithErgebnis()
			throws PersistenceException {

		em = emf.createEntityManager();

		List<Wissenstest> list = em
				.createQuery(
						"SELECT DISTINCT wissenstest FROM Wissenstest wissenstest, Ergebnis ergebnis WHERE wissenstest MEMBER OF ergebnis.wissenstest",
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

		em = emf.createEntityManager();

		List<Kategorie> list = em.createQuery(
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

		em = emf.createEntityManager();

		List<Frage> list = em.createQuery(
				"SELECT frage FROM Frage frage ORDER BY frage.id", Frage.class)
				.getResultList();
		return list;

	}

	/**
	 * Methode zum Abrufen aller Lehrenden
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	public List<Lehrender> getAllLehrende() throws PersistenceException {

		em = emf.createEntityManager();

		List<Lehrender> list = em.createQuery(
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

		em = emf.createEntityManager();

		List<Ergebnis> list = em.createQuery(
				"SELECT ergebnis FROM Ergebnis ergebnis", Ergebnis.class)
				.getResultList();
		return list;

	}

	/**
	 * 
	 * Methode liefert alle Ergebnisse zu einem Wissenstest
	 * 
	 * @param selektierterWissenstest
	 * @return
	 * @throws PersistenceException
	 */
	public List<Ergebnis> getErgebnisseByWissenstest(
			WissenstestBo selektierterWissenstest) throws PersistenceException {

		em = emf.createEntityManager();

		List<Ergebnis> list = em.createQuery(
				"SELECT ergebnis FROM Ergebnis ergebnis where ergebnis.wissenstest = "
						+ selektierterWissenstest.getId()
						+ " ORDER BY ergebnis.student.id", Ergebnis.class)
				.getResultList();
		return list;

	}

	public List<Ergebnis> getErgebnisseByWissenstestOrderByFrageUndRichtig(
			WissenstestBo selektierterWissenstest) throws PersistenceException {

		em = emf.createEntityManager();

		List<Ergebnis> list = em.createQuery(
				"SELECT ergebnis FROM Ergebnis ergebnis where ergebnis.wissenstest = "
						+ selektierterWissenstest.getId()
						+ " ORDER BY ergebnis.frage.id, ergebnis.richtig",
				Ergebnis.class).getResultList();
		return list;

	}

	public List<Frage> getFragenByKategorie(int kategorie_id)
			throws PersistenceException {

		em = emf.createEntityManager();

		List<Frage> list = em.createQuery(
				"Select frage FROM Frage frage where frage.kategorie = "
						+ kategorie_id + " ORDER BY frage.id", Frage.class)
				.getResultList();

		return list;
	}

	/**
	 * Ermittelt alle Ergebnisse f&uuml;r einen Studenten.
	 * 
	 * @param id
	 * @return
	 */
	public List<Ergebnis> getErgebnisseByStudent(int id) {

		em = emf.createEntityManager();

		List<Ergebnis> ergebnisse = em.createQuery(
				"Select ergebnis FROM Ergebnis ergebnis WHERE ergebnis.student.id = "
						+ id, Ergebnis.class).getResultList();

		return ergebnisse;
	}

	/**
	 * Ermittelt alle Ergebnisse f&uuml;r einen Studenten und einen Wissenstest.
	 * 
	 * @param wissenstest
	 *            , student
	 * @return
	 */
	public List<Ergebnis> getErgebnisseByWissenstestAndStudent(Student student,
			Wissenstest wissenstest) {

		em = emf.createEntityManager();

		List<Ergebnis> ergebnisse = em.createQuery(
				"Select ergebnis FROM Ergebnis ergebnis WHERE ergebnis.student.id = "
						+ student.getId() + " AND ergebnis.wissenstest.id = "
						+ wissenstest.getId(), Ergebnis.class).getResultList();

		return ergebnisse;
	}

	/**
	 * Diese Methode speichert ein Ergebnis in der Datenbank.
	 * 
	 * @param ergebnis
	 */
	public void saveErgebnis(Ergebnis ergebnis) {

		em = emf.createEntityManager();
		em.getTransaction().begin();

		Ergebnis ergebnisToSave;

		if (ergebnis.getId() != null) {
			ergebnisToSave = em.find(Ergebnis.class, ergebnis.getId());
		} else {
			ergebnisToSave = new Ergebnis();
		}
		ergebnisToSave.setAntwort(ergebnis.getAntwort());
		ergebnisToSave.setFrage(ergebnis.getFrage());
		ergebnisToSave.setStudent(ergebnis.getStudent());
		ergebnisToSave.setWissenstest(ergebnis.getWissenstest());
		ergebnisToSave.setRichtig(ergebnis.getRichtig());

		em.persist(ergebnisToSave);
		em.getTransaction().commit();
		em.close();

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

		em = emf.createEntityManager();

		Student student = em.createQuery(
				"Select student FROM Student student where student.kuerzel = '"
						+ kuerzel + "'", Student.class).getSingleResult();

		em.close();

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

		em = emf.createEntityManager();

		Lehrender lehrender = em.createQuery(
				"Select lehrender FROM Lehrender lehrender where lehrender.kuerzel = '"
						+ kuerzel + "'", Lehrender.class).getSingleResult();

		em.close();

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

		em = emf.createEntityManager();

		Admin admin = em.createQuery(
				"Select admin FROM Admin admin WHERE admin.login = '" + name
						+ "'", Admin.class).getSingleResult();

		em.close();

		return admin;

	}

	/**
	 * Methode, die eine Liste von Fragen in die Datenbank speichert.
	 * 
	 * @param fragen
	 */
	public void saveFragen(List<Frage> fragen) {

		for (Frage frage : fragen) {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			Frage frageToSave;
			if (frage.getId() != null) {
				frageToSave = em.find(Frage.class, frage.getId());
			} else {
				frageToSave = new Frage();
			}
			frageToSave.setBild(frage.getBild());
			frageToSave.setKategorie(frage.getKategorie());
			frageToSave.setSchwierigkeit(frage.getSchwierigkeit());
			frageToSave.setText(frage.getText());

			em.persist(frageToSave);

			for (Antwort antwort : frage.getAntworts()) {
				Antwort antwortToSave;
				if (antwort.getId() != null) {
					antwortToSave = em.find(Antwort.class, antwort.getId());
				} else {
					antwortToSave = new Antwort();
				}
				antwort.setText(antwort.getText());
				antwort.setFrage(frageToSave);
				em.persist(antwortToSave);
			}
			frageToSave.setAntwort(frage.getAntwort());
			em.persist(frageToSave);
			em.getTransaction().commit();
			em.close();
		}

	}

	/**
	 * Diese Methode speichert einen Wissenstest in die Datenbank.
	 * 
	 * @param wissenstest
	 */
	public void saveWissenstest(Wissenstest wissenstest) {

		em = emf.createEntityManager();
		em.getTransaction().begin();

		Wissenstest wissenstestToSave;
		if (wissenstest.getId() != null) {
			wissenstestToSave = em.find(Wissenstest.class, wissenstest.getId());
		} else {
			wissenstestToSave = new Wissenstest();
		}
		wissenstestToSave.setBezeichnung(wissenstest.getBezeichnung());
		wissenstestToSave.setEndzeit(wissenstest.getEndzeit());
		wissenstestToSave.setAktiv(wissenstest.getAktiv());
		wissenstestToSave.setStartzeit(wissenstest.getStartzeit());
		wissenstestToSave.setLehrender(wissenstest.getLehrender());
		wissenstestToSave.setRandom(wissenstest.getRandom());
		wissenstestToSave.setZeitFrage(wissenstest.getZeitFrage());
		em.persist(wissenstestToSave);

		for (ZuordungWissenstestFrage zuordungWissenstestFrage : wissenstest
				.getZuordungWissenstestFrages()) {
			ZuordungWissenstestFrage zuordungWissenstestFrageToSave;
			if (zuordungWissenstestFrage.getId() != null) {
				zuordungWissenstestFrageToSave = em.find(
						ZuordungWissenstestFrage.class,
						zuordungWissenstestFrage.getId());
			} else {
				zuordungWissenstestFrageToSave = new ZuordungWissenstestFrage();
			}
			zuordungWissenstestFrageToSave.setFrage(zuordungWissenstestFrage
					.getFrage());
			zuordungWissenstestFrageToSave.setWissenstest(wissenstestToSave);
			em.persist(zuordungWissenstestFrageToSave);
		}
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * Diese Methode l&oouml;scht einen Wissenstest aus der Datenbank.
	 * 
	 * @param wissenstest
	 */
	public void deleteWissenstest(Wissenstest wissenstest) {

	}

	/**
	 * L&ouml;scht eine Frage aus der Datenbank.
	 * 
	 * @param frage
	 */
	public void deleteFrage(Frage frage) {

		em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.contains(frage) ? frage : em.merge(frage));

		em.getTransaction().commit();
		em.close();

	}

	/**
	 * Methode zum Speichern eines Studenten.
	 * 
	 * @param dbModel
	 */
	public void saveStudent(Student student) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		Student studentToSave;

		if (student.getId() != null) {
			studentToSave = em.find(Student.class, student.getId());
		} else {
			studentToSave = new Student();
		}
		studentToSave.setKuerzel(student.getKuerzel());
		studentToSave.setMatrikelNr(student.getMatrikelNr());
		studentToSave.setName(student.getName());
		studentToSave.setVorname(student.getVorname());

		em.persist(studentToSave);
		em.getTransaction().commit();
		
		em.close();
		
	}

	/**
	 * Methode zum Speichern eines Lehrenden
	 * 
	 * @param lehrender
	 * @throws PersistenceException
	 */
	public void saveLehrender(Lehrender lehrender) throws PersistenceException {

		em = emf.createEntityManager();
		em.getTransaction().begin();

		Lehrender lehrenderToSave;

		if (lehrender.getId() != null) {
			lehrenderToSave = em.find(Lehrender.class, lehrender.getId());
		} else {
			lehrenderToSave = new Lehrender();
		}
		lehrenderToSave.setName(lehrender.getName());
		lehrenderToSave.setVorname(lehrender.getVorname());
		lehrenderToSave.setKuerzel(lehrender.getKuerzel());

		em.persist(lehrenderToSave);
		em.getTransaction().commit();
		em.close();

	}

	/**
	 * Methode zum L&ouml;schen eines Lehrenden.
	 * 
	 * @param lehrender
	 * @throws PersistenceException
	 */
	public void deleteLehrender(Lehrender lehrender)
			throws PersistenceException {
		em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.contains(lehrender) ? lehrender : em.merge(lehrender));
		em.getTransaction().commit();
		em.close();
	}

}
