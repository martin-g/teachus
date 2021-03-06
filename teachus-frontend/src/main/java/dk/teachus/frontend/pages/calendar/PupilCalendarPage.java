/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dk.teachus.frontend.pages.calendar;

import org.apache.wicket.model.Model;
import org.joda.time.DateTime;

import dk.teachus.backend.domain.Pupil;
import dk.teachus.backend.domain.TeachUsDate;
import dk.teachus.frontend.TeachUsSession;
import dk.teachus.frontend.UserLevel;
import dk.teachus.frontend.components.calendar.PupilPeriodsCalendarPanel;
import dk.teachus.frontend.pages.AuthenticatedBasePage;


public class PupilCalendarPage extends AuthenticatedBasePage {
	private static final long serialVersionUID = 1L;
	
	private Pupil pupil;
	
	public PupilCalendarPage() {
		this(null);
	}
	
	public PupilCalendarPage(Pupil pupil) {
		this(TeachUsSession.get().createNewDate(new DateTime()), pupil);
	}
	
	public PupilCalendarPage(TeachUsDate pageDate, Pupil pupil) {
		super(UserLevel.PUPIL);
		
		if (pupil == null) {
			pupil = (Pupil) TeachUsSession.get().getPerson();
		}
		
		this.pupil = pupil;
				
		add(new PupilPeriodsCalendarPanel("calendar", new Model<TeachUsDate>(pageDate), pupil));
	}
	
	@Override
	protected String getPageLabel() {
		return TeachUsSession.get().getString("General.calendarFor")+" "+pupil.getName(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	protected AuthenticatedPageCategory getPageCategory() {
		return AuthenticatedPageCategory.CALENDAR;
	}
	
}
