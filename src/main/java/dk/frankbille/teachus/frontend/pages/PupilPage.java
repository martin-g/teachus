package dk.frankbille.teachus.frontend.pages;

import wicket.ResourceReference;
import dk.frankbille.teachus.domain.Admin;
import dk.frankbille.teachus.domain.Person;
import dk.frankbille.teachus.domain.Pupil;
import dk.frankbille.teachus.domain.Teacher;
import dk.frankbille.teachus.frontend.UserLevel;
import dk.frankbille.teachus.frontend.utils.Icons;

public class PupilPage extends PersonPage {
	private static final long serialVersionUID = 1L;

	public PupilPage(Pupil pupil) {
		super(UserLevel.PUPIL, pupil);
	}

	@Override
	protected ResourceReference getPageIcon() {
		return Icons.PUPIL;
	}

	@Override
	protected Class<? extends PersonsPage> getPersonsPageClass() {
		return PupilsPage.class;
	}
	
	@Override
	protected boolean allowUserEditing(Person loggedInPerson, Person editPerson) {
		boolean allow = false;
		
		Pupil pupil = (Pupil) editPerson;
		
		if (loggedInPerson instanceof Admin) {
			allow = true;
		} else if (loggedInPerson instanceof Teacher) {
			allow = pupil.getTeacher().getId().equals(loggedInPerson.getId());
		} else if (loggedInPerson instanceof Pupil) {
			allow = pupil.getId().equals(loggedInPerson.getId());
		}
		
		return allow;
	}

}