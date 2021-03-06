package dk.teachus.backend.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import junit.framework.TestCase;
import dk.teachus.backend.domain.Period.Status;
import dk.teachus.backend.domain.impl.BookingsImpl;
import dk.teachus.backend.domain.impl.PeriodImpl;
import dk.teachus.backend.domain.impl.TeacherBookingImpl;
import dk.teachus.backend.domain.impl.TeacherImpl;

public class TestBookings extends TestCase {
	
	/**
	 * Test that getting a booking only gets the booking for that period
	 */
	public void testIsolatedGetBooking() {
		Teacher teacher = createTeacher();
		
		Period period1 = createPeriod(1, teacher);
		Period period2 = createPeriod(2, teacher);
		
		List<Booking> bookingList = new ArrayList<Booking>();
		TeachUsDate time1 = new TeachUsDate(2009, 6, 11, 16, 0, 0, TimeZone.getTimeZone("Europe/Copenhagen"));
		bookingList.add(createTeacherBooking(period1, teacher, time1));
		bookingList.add(createTeacherBooking(period1, teacher, new TeachUsDate(2009, 6, 11, 17, 0, 0, TimeZone.getTimeZone("Europe/Copenhagen"))));
		bookingList.add(createTeacherBooking(period1, teacher, new TeachUsDate(2009, 6, 18, 16, 0, 0, TimeZone.getTimeZone("Europe/Copenhagen"))));
		bookingList.add(createTeacherBooking(period1, teacher, new TeachUsDate(2009, 6, 18, 17, 0, 0, TimeZone.getTimeZone("Europe/Copenhagen"))));
		
		Bookings bookings = new BookingsImpl(bookingList);
		
		assertNull(bookings.getBooking(period2, time1));
		assertNotNull(bookings.getBooking(period1, time1));
	}
	
	private Teacher createTeacher() {
		Teacher teacher = new TeacherImpl();
		teacher.setActive(true);
		teacher.setCurrency("kr");
		teacher.setEmail("test@teachus.dk");
		teacher.setLocale(Locale.ENGLISH);
		teacher.setName("Teacher 1");
		teacher.setPhoneNumber("12345678");
		teacher.setUsername("test1");
		return teacher;
	}
	
	private Period createPeriod(long id, Teacher teacher) {
		PeriodImpl period = new PeriodImpl();
		period.setId(id);
		period.setName("Period "+id);
		period.setBeginDate(new TeachUsDate(2009, 6, 11, TimeZone.getTimeZone("Europe/Copenhagen")));
		period.setStartTime(new TeachUsDate(2009, 6, 11, 16, 0, 0, TimeZone.getTimeZone("Europe/Copenhagen")));
		period.setEndTime(new TeachUsDate(2009, 6, 11, 16, 0, 0, TimeZone.getTimeZone("Europe/Copenhagen")));
		period.setIntervalBetweenLessonStart(60);
		period.setLessonDuration(60);
		period.setLocation("Location 1");
		period.setPrice(350.0);
		period.setRepeatEveryWeek(1);
		period.setStatus(Status.FINAL);
		period.setTeacher(teacher);
		return period;
	}
	
	private TeacherBooking createTeacherBooking(Period period, Teacher teacher, TeachUsDate date) {
		TeacherBooking tb = new TeacherBookingImpl();
		tb.setActive(true);
		tb.setCreateDate(new TeachUsDate());
		tb.setDate(date);
		tb.setPeriod(period);
		tb.setUpdateDate(new TeachUsDate());
		tb.setTeacher(teacher);
		return tb;
	}
	
}
