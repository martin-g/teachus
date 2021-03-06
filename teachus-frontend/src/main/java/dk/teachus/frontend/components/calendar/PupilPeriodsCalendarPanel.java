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
package dk.teachus.frontend.components.calendar;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

import dk.teachus.backend.domain.Booking;
import dk.teachus.backend.domain.Pupil;
import dk.teachus.backend.domain.PupilBooking;
import dk.teachus.backend.domain.TeachUsDate;
import dk.teachus.backend.domain.Teacher;
import dk.teachus.frontend.TeachUsApplication;
import dk.teachus.frontend.TeachUsSession;

public class PupilPeriodsCalendarPanel extends PeriodsCalendarPanel {
	private static final long serialVersionUID = 1L;

	private final Pupil pupil;

	public PupilPeriodsCalendarPanel(String id, IModel<TeachUsDate> weekDateModel, Pupil pupil) {
		super(id, weekDateModel);
		this.pupil = pupil;
	}
	
	@Override
	protected Teacher getTeacher() {
		return pupil.getTeacher();
	}

	@Override
	protected boolean isTimeSlotBookable(TimeSlot<PeriodBookingTimeSlotPayload> timeSlot) {
		Booking booking = timeSlot.getPayload().getBooking();
		if (booking != null) {
			if (booking instanceof PupilBooking) {
				PupilBooking pupilBooking = (PupilBooking) booking;
				
				if (pupilBooking.getPupil().getId().equals(this.pupil.getId())) {
					return true;
				}
			}
		} else {
			return true;
		}
		
		return false;
	}

	@Override
	protected void appendToTimeSlotContent(List<String> contentLines, TimeSlot<PeriodBookingTimeSlotPayload> timeSlot) {
		Booking booking = timeSlot.getPayload().getBooking();
		if (booking != null) {
			if (booking instanceof PupilBooking) {
				PupilBooking pupilBooking = (PupilBooking) booking;
				if (pupilBooking.getPupil().getId().equals(this.pupil.getId())) {
					contentLines.add(pupilBooking.getPupil().getName());
				} else {
					contentLines.add(TeachUsSession.get().getString("PeriodsCalendarPanel.booked")); //$NON-NLS-1$
				}
			} else {
				contentLines.add(TeachUsSession.get().getString("PeriodsCalendarPanel.booked")); //$NON-NLS-1$
			}
		}
	}
	
	@Override
	protected String getTimeSlotClass(TimeSlot<PeriodBookingTimeSlotPayload> timeSlot) {
		Booking booking = timeSlot.getPayload().getBooking();
		if (booking != null) {
			if (booking instanceof PupilBooking) {
				PupilBooking pupilBooking = (PupilBooking) booking;
				if (pupilBooking.getPupil().getId().equals(this.pupil.getId())) {
					return "pupilBooked bookingLink"; //$NON-NLS-1$
				} else {
					return "booked"; //$NON-NLS-1$
				}
			}
			
			return "booked"; //$NON-NLS-1$
		}
		
		return "nobooking bookingLink"; //$NON-NLS-1$
	}
	
	@Override
	protected void onTimeSlotClicked(TimeSlot<PeriodBookingTimeSlotPayload> timeSlot, TeachUsDate date, AjaxRequestTarget target) {
		PeriodBookingTimeSlotPayload payload = timeSlot.getPayload();
		Booking booking = payload.getBooking();
		if (booking != null) {
			TeachUsApplication.get().getBookingDAO().deleteBooking(booking);
			payload.setBooking(null);
		} else {
			PupilBooking pupilBooking = TeachUsApplication.get().getBookingDAO().createPupilBookingObject();
			pupilBooking.setActive(true);
			pupilBooking.setDate(date);
			pupilBooking.setPeriod(payload.getPeriod());
			pupilBooking.setTeacher(pupil.getTeacher());
			pupilBooking.setPupil(pupil);
			pupilBooking.setCreateDate(TeachUsSession.get().createNewDate());
			TeachUsApplication.get().getBookingDAO().book(pupilBooking);
			payload.setBooking(pupilBooking);
		}
	}
	
}
