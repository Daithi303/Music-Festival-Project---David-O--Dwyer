package Main_Package;

import java.io.Serializable;

public class TimeSlot implements Comparable<TimeSlot>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static enum Day  {Friday, Saturday};
	public static enum Time  {Two, Four, Six, Eight, Ten, Midnight};
	private Integer timeSlotId;
	private final Day day;
	private final Time time;
	private String bandName;
	
	/**
	 * @return
	 */
	public Integer getTimeSlotId() {
		return timeSlotId;
	}
	/**
	 * @param timeSlotId
	 */
	public void setTimeSlotId(Integer timeSlotId) {
		this.timeSlotId = timeSlotId;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return day+" at "+time;
	}
	
	/**
	 * @return
	 */
	public Day getDay() {
		return day;
	}
	/**
	 * @return
	 */
	public String getBandName() {
		return bandName;
	}
	/**
	 * @param bandName
	 */
	public void setBandName(String bandName) {
		this.bandName = bandName;
	}
	/**
	 * @return
	 */
	public Time getTime() {
		return time;
	}
	/**
	 * @param day
	 * @param time
	 * @param timeSlotId
	 */
	public TimeSlot(Day day, Time time, Integer timeSlotId)
	{
		this.day = day;
		this.time = time;
		this.timeSlotId = timeSlotId;
		this.bandName = "";
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(TimeSlot o) {

		return this.getTimeSlotId().intValue() - o.getTimeSlotId().intValue();
	}


}
