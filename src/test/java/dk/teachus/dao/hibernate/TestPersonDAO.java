package dk.teachus.dao.hibernate;

import dk.teachus.domain.Person;
import dk.teachus.frontend.WicketSpringTestCase;

public class TestPersonDAO extends WicketSpringTestCase {
	private static final long serialVersionUID = 1L;

	public void testPasswordUserType() {
		Person person = getPersonDAO().authenticatePerson("admin", "admin");
		endTransaction();
		assertNotNull(person);
	}
	
}