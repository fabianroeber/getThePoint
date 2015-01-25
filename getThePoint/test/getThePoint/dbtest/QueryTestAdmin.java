package getThePoint.dbtest;

import org.junit.Test;

import de.hdm.getThePoint.db.DataAccess;
import de.hdm.getThePoint.db.dbmodel.Admin;

public class QueryTestAdmin {

	@Test
	public void test() {
	DataAccess dataAccess = new DataAccess();
	
	Admin admin = dataAccess.getAdminByUserName("admin");
	System.out.println(admin.getLogin());
	}

}
