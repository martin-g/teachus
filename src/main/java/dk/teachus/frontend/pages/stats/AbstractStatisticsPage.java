package dk.teachus.frontend.pages.stats;

import wicket.RestartResponseAtInterceptPageException;
import wicket.protocol.http.WebApplication;
import dk.teachus.domain.Teacher;
import dk.teachus.frontend.TeachUsSession;
import dk.teachus.frontend.UserLevel;
import dk.teachus.frontend.pages.AuthenticatedBasePage;

public abstract class AbstractStatisticsPage extends AuthenticatedBasePage {
	
	public AbstractStatisticsPage() {
		super(UserLevel.TEACHER);
		
		if (TeachUsSession.get().getUserLevel() != UserLevel.TEACHER) {
			throw new RestartResponseAtInterceptPageException(WebApplication.get().getHomePage());
		}
	}
	
	protected Teacher getTeacher() {
		return (Teacher) TeachUsSession.get().getPerson();
	}

}
