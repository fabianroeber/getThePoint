package getThePoint.dbtest;

import java.util.List;

import org.junit.Test;

import de.hdm.getThePoint.bo.FrageZuordung;
import de.hdm.getThePoint.bo.StudentBo;
import de.hdm.getThePoint.bo.WissenstestBo;
import de.hdm.getThePoint.db.DataAccess;
import de.hdm.getThePoint.db.dbmodel.Student;
import de.hdm.getThePoint.db.dbmodel.Wissenstest;
import de.hdm.getThePoint.db.mapper.WissenstestMapper;

public class TestingWissenstestByStudentWithErgebnisse {

	@Test
	public void test() {

		DataAccess dataAcces = new DataAccess();
		WissenstestMapper wissenstestMapper = new WissenstestMapper();

		Student student = new Student();
		
		student = dataAcces.getStudentByKuerzel("teststudent");
		
		List<Wissenstest> liste = dataAcces.getWissentestsWithErgebnis();

		for (Wissenstest wissenstest : liste) {
			System.out.println(wissenstest.getBezeichnung());
			System.out.println(wissenstest.getLehrender().getName());
		}

	}
}
