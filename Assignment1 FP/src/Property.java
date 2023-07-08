//property class with all the attributes as the instance variables of the class 
public class Property {
	private String name;
	private String location;
	private String description;
	private String typeOfPlace;
	private String hostName;
	private int MaxNumOfGuests;
	private double rating;
	private double pricePerNight;
	private double serviceFee;
	private double cleaningFee;
	private double weeklyDis;

	// constructor
	public Property(String name, String location, String description, String typeOfPlace, String hostName,
			int MaxNumOfGuests, double rating, double pricePerNight, double serviceFee, double cleaningFee,
			double weeklyDis) {
		this.name = name;
		this.location = location;
		this.description = description;
		this.typeOfPlace = typeOfPlace;
		this.hostName = hostName;
		this.MaxNumOfGuests = MaxNumOfGuests;
		this.rating = rating;
		this.pricePerNight = pricePerNight;
		this.serviceFee = serviceFee;
		this.cleaningFee = cleaningFee;
		this.weeklyDis = weeklyDis;

	}

	// getters
	public String getName() {
		return this.name;
	}

	public String getLocation() {
		return this.location;
	}

	public String getDescription() {
		return this.description;
	}

	public String getTypeOfPlace() {
		return this.typeOfPlace;
	}

	public String getHostName() {
		return this.hostName;
	}

	public int getMaxNumOfGuests() {
		return this.MaxNumOfGuests;
	}

	public double getPricePerNight() {
		return this.pricePerNight;
	}

	public double getServiceFee() {
		return this.serviceFee;
	}

	public double getCleaningFee() {
		return this.cleaningFee;
	}

	public double getweeklyDis() {
		return this.weeklyDis;
	}

	public double getRating() {
		return this.rating;
	}

	// formatting the output
	public String toString() {

		return String.format("%-60s%-20s%-300s%-20s%-10s%-5d%-6.2f%-9.2f%-9.2f%-9.2f%-9.2f", name, location,
				description, typeOfPlace, hostName, MaxNumOfGuests, rating, pricePerNight, serviceFee, cleaningFee,
				weeklyDis);

	}

}
