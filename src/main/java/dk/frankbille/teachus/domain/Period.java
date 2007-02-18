package dk.frankbille.teachus.domain;

import java.util.Date;
import java.util.List;

import dk.frankbille.teachus.domain.impl.PeriodImpl.WeekDay;

public interface Period {

	Long getId();
	
	String getName();
	
	Date getEndDate();

	Date getEndTime();

	Date getBeginDate();

	Date getStartTime();

	List<WeekDay> getWeekDays();
	
	Teacher getTeacher();
	
	double getPrice();
	
	void setName(String name);

	void setEndDate(Date endDate);

	void setEndTime(Date endTime);

	void setBeginDate(Date startDate);

	void setStartTime(Date startTime);
	
	void setTeacher(Teacher teacher);
	
	void setPrice(double price);

	void addWeekDay(WeekDay weekDay);

	boolean hasWeekDay(Date date);

	boolean dateIntervalContains(Date date);

	boolean hasDate(Date date);

	Date generateDate(Date startDate);

}