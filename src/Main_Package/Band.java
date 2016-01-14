package Main_Package;

import java.io.Serializable;

public class Band implements Serializable{

	private static final long serialVersionUID = 1L;

	private String name;
	
	private Double price;
	
	private String genre;
	
	private String timeSlot;

	/**
	 * @param name
	 * @param price
	 * @param genre
	 */
	public Band(String name, Double price, String genre) {
		super();
		this.name = name;
		this.price = price;
		this.genre = genre;
		this.timeSlot = "Unassigned";
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * @param genre
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}



	/**
	 * @return
	 */
	public String getTimeSlot() {
		return timeSlot;
	}

	/**
	 * @param timeSlotId
	 */
	public void setTimeSlot(String timeSlotId) {
		this.timeSlot = timeSlotId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Name: "+name+",    "+"Price: €"+price+"0,    Genre: "+genre+".\n";
	}
	

}
