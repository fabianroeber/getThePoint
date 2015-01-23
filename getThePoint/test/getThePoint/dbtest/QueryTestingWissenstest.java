package getThePoint.dbtest;

import java.util.List;

import org.junit.Test;

import de.hdm.getThePoint.bo.FrageZuordung;
import de.hdm.getThePoint.bo.WissenstestBo;
import de.hdm.getThePoint.db.DataAccess;
import de.hdm.getThePoint.db.dbmodel.Wissenstest;
import de.hdm.getThePoint.db.mapper.WissenstestMapper;

public class QueryTestingWissenstest {

	@Test
	public void test() {

		DataAccess dataAcces = new DataAccess();
		WissenstestMapper wissenstestMapper = new WissenstestMapper();

		List<Wissenstest> liste = dataAcces.getAllWissentests();

		for (Wissenstest wissenstest : liste) {
			System.out.println(wissenstest.getBezeichnung());
			System.out.println(wissenstest.getLehrender().getName());
		}

	}
}
