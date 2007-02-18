package dk.frankbille.teachus.frontend.components;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

import wicket.Component;
import dk.frankbille.teachus.dao.BookingDAO;
import dk.frankbille.teachus.domain.Booking;
import dk.frankbille.teachus.domain.Bookings;
import dk.frankbille.teachus.domain.Period;
import dk.frankbille.teachus.domain.Pupil;
import dk.frankbille.teachus.frontend.TeachUsApplication;

public class PupilPeriodDateComponent extends PeriodDateComponent {
	private static final long serialVersionUID = 1L;
	
	private Pupil pupil;
	private Bookings bookings;

	public PupilPeriodDateComponent(String id, Pupil pupil, Period period, DateMidnight date) {
		super(id, period, date);
		
		BookingDAO bookingDAO = TeachUsApplication.get().getBookingDAO();
		
		this.pupil = pupil;
		this.bookings = bookingDAO.getBookingsForDate(period, date.toDate());
	}

	@Override
	protected Component getTimeContent(String wicketId, Period period, DateTime time) {
		Booking booking = bookings.getBooking(time.toDate());
		
		return new PupilPeriodDateComponentPanel(wicketId, booking, pupil, period, time);
	}

}
