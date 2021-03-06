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
package dk.teachus.backend.domain;

import java.util.List;

import dk.teachus.backend.domain.impl.PeriodImpl.WeekDay;

public interface Period {

	public static enum Status {
		DRAFT,
		FINAL,
		DELETED
	}
	
	Long getId();
	
	String getName();
	
	TeachUsDate getEndDate();

	TeachUsDate getEndTime();

	TeachUsDate getBeginDate();

	TeachUsDate getStartTime();

	List<WeekDay> getWeekDays();
	
	Teacher getTeacher();
	
	String getLocation();
	
	double getPrice();
	
	Status getStatus();
	
	/**
	 * @return The duration of a lesson in minutes
	 */
	int getLessonDuration();
	
	/**
	 * @return How often a lesson can start. F.ex. 15 on this property
	 * gives: 10.10, 10.15, 10.30 and 10.45
	 */
	int getIntervalBetweenLessonStart();
	
	int getRepeatEveryWeek();
	
	void setName(String name);

	void setEndDate(TeachUsDate endDate);

	void setEndTime(TeachUsDate endTime);

	void setBeginDate(TeachUsDate startDate);

	void setStartTime(TeachUsDate startTime);
	
	void setTeacher(Teacher teacher);
	
	void setLocation(String location);
	
	void setPrice(double price);
	
	void setLessonDuration(int minutes);
	
	void setIntervalBetweenLessonStart(int minutes);
	
	void setRepeatEveryWeek(int repeatEveryWeek);
	
	void setStatus(Status status);

	/*
	 * Utility methods
	 */
	void addWeekDay(WeekDay weekDay);

	boolean hasWeekDay(TeachUsDate date);

	boolean dateIntervalContains(TeachUsDate date);

	boolean hasDate(TeachUsDate date);

	TeachUsDate generateDate(TeachUsDate startDate);
	
	boolean isTimeValid(TeachUsDate time);

	boolean mayBook(TeachUsDate time);

	boolean conflicts(TeachUsDate bookedTime, TeachUsDate time);

	boolean inLesson(TeachUsDate bookedTime, TeachUsDate time);
	
	boolean isValid();
	
}