import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class CustomerService {
	private ArrayList<Property> propertyList = new ArrayList<>();
	String FILE_NAME = "Melbnb.csv";
	private String date;
	private String date2;
	private Property selectedProperty;

	// setters
	public void setdate(String date) {
		this.date = date;
	}

	public void setdate2(String date2) {
		this.date2 = date2;
	}

	public void setSelectedProperty(int index) {
		getDetails();
		this.selectedProperty = propertyList.get(index);

	}

	// getters
	public Property getSelectedProperty() {
		getDetails();
		return this.selectedProperty;
	}

	public ArrayList<Property> getPropertyList() {
		return this.propertyList;
	}

	public CustomerService() {
		getDetails();
	}

	// the function to read from the csv file and accomodating the values into the
	// variables of the program
	public void getDetails() {
		Scanner scanner = null;
		int count = 0;
		Property property;
		try {
			scanner = new Scanner(new File(FILE_NAME));
			while (scanner.hasNextLine()) {

				String line = scanner.nextLine();

				String[] tokens = line.split(",+");

				if (count > 0) {// ignore the header of csv file
					// creating property objects with the values from the csv file
					int maxNumofGuests = Integer.parseInt(tokens[5]);
					double rating = Double.parseDouble(tokens[6]);
					double price = Double.parseDouble(tokens[7]);
					double serviceFee = Double.parseDouble(tokens[8]);
					double cleaningFee = Double.parseDouble(tokens[9]);
					double weeklyDis = Double.parseDouble(tokens[10]);
					property = new Property(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], maxNumofGuests,
							rating, price, serviceFee, cleaningFee, weeklyDis);
					// adding each property object created to an array of properties
					propertyList.add(property);

				}
				count = count + 1;

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// catching the error in case of wrong file name
			System.err.print(e.getMessage());
			System.out.println("Please restart the program with an properFileName:");

		}
		if (scanner != null) {
			scanner.close();
		}
	}

	// function to print the main menu on the console
	public void printMenu() {
		System.out.println("Welcome to Melbnb!");
		System.out.println("> Select from main menu  ");
		System.out.println("1) Search by location");
		System.out.println("2) Browse by type of place");
		System.out.println("3) Filter by rating");
		System.out.println("4) Exit");
	}

	// Function to start taking the user inputs and interacting with the users based
	// on their option selected
	public void run() {
		printMenu();
		Scanner input = new Scanner(System.in);
		String option = input.next();
		boolean valid = false;
		// Validating user input and throwing exception in case of invalid one
		while (!valid) {
			try {
				if (!option.equals("1") && !option.equals("2") && !option.equals("3") & !option.equals("4")) {
					throw new InvalidOptionException("Invalid Option, ");
				} else {
					valid = true;
					break;
				}
			} catch (InvalidOptionException e) {
				System.err.print(e.getMessage());
				System.out.println("Please choose an option:");
				option = input.next();
			}
		}
		// calling other functions based on the user input
		if (option.equals("1")) {
			byLocation();
		}
		if (option.equals("2")) {
			byType();
		}
		if (option.equals("3")) {
			byRating();
		}
		if (option.equals("4")) {
			exit();
		}

	}

	// calling this function when user wants to book based on location
	public void byLocation() {
		Scanner input = new Scanner(System.in);
		System.out.println("Please provide a location: ");
		String location = input.next();
		location = location.toLowerCase();
		ArrayList<Property> locationwise = new ArrayList<>();
		int num = 1;
		System.out.println("> Select from matching list ");
		for (int i = 0; i < propertyList.size(); i = i + 1) {
			String locations = propertyList.get(i).getLocation().toLowerCase();

			if (locations.contains(location)) {
				System.out.println(num + ") " + propertyList.get(i).getName().toString());
				locationwise.add(propertyList.get(i));
				num = num + 1;
			}

		}
		System.out.println(num + ")  Go to main menu ");

		selectedOption(locationwise, num);
	}

	// calling this function when user wants to book based on type
	public void byType() {
		int num = 1;
		ArrayList<String> TypeMenu = new ArrayList<>();
		System.out.println("> Browse by type of place ");
		for (int i = 0; i < propertyList.size(); i = i + 1) {

			if (!TypeMenu.contains(propertyList.get(i).getTypeOfPlace())) {
				TypeMenu.add(propertyList.get(i).getTypeOfPlace());
			}

		}
		int count = 1;
		for (String types : TypeMenu) {

			System.out.println(count + ")" + types);
			count = count + 1;
		}
		System.out.println(count + ") Go to main menu ");

		ArrayList<Property> typewise = new ArrayList<>();
		System.out.println("Please select:");
		Scanner input = new Scanner(System.in);
		String type = input.next();
		System.out.println(type.toString());
		while (!validType(type, TypeMenu.size() + 1)) {
			System.out.println("Please select a valid option:");
			type = input.next();
		}
		int Type = Integer.parseInt(type);
		for (int i = 0; i < TypeMenu.size(); i = i + 1) {
			if (Type == i + 1) {
				type = TypeMenu.get(i);
			}
		}
		if (Type == TypeMenu.size() + 1) {
			type = " Go to main menu ";
			run();
		}
		type = type.toLowerCase();
		for (int i = 0; i < propertyList.size(); i = i + 1) {
			String locations = propertyList.get(i).getTypeOfPlace().toLowerCase();

			if (locations.contains(type)) {
				System.out.println(num + ") " + propertyList.get(i).getName().toString());
				typewise.add(propertyList.get(i));
				num = num + 1;
			}

		}

		System.out.println(num + " Go to main menu ");
		selectedOption(typewise, num);
	}

	// calling this function when user wants to book based on rating

	public void byRating() {
		System.out.println("Please provide the minimum rating:");
		Scanner input = new Scanner(System.in);

		Double location = input.nextDouble();
		while (!checkRatingRange(location)) {
			System.out.println("Please provide a minimum rating less than 5");
			location = input.nextDouble();
		}
		ArrayList<Property> ratingwise = new ArrayList<>();
		int num = 1;
		System.out.println("> Select from matching list ");
		for (int i = 0; i < propertyList.size(); i = i + 1) {
			Double locations = propertyList.get(i).getRating();

			if (locations >= (location)) {
				System.out.println(num + ") " + propertyList.get(i).toString());
				ratingwise.add(propertyList.get(i));
				num = num + 1;
			}

		}
		System.out.println(num + ")Go to main menu ");
		selectedOption(ratingwise, num);
	}

	// function to finalise the selected option by the users
	public void selectedOption(ArrayList<Property> locationwise, int lastOption) {
		System.out.println("Please selet:");
		Scanner option = new Scanner(System.in);
		int optionSelected = option.nextInt();

		boolean valid = false;
		while (!valid) {
			try {
				for (int i = 0; i < locationwise.size(); i = i + 1) {
					if (optionSelected == i + 1 || optionSelected == lastOption) {
						valid = true;

					}

				}
				if (valid == true) {
					break;
				} else {
					System.out.println(optionSelected);
					throw new InvalidOptionException("Invalid Option, ");
				}
			}

			catch (InvalidOptionException e) {
				System.err.print(e.getMessage());
				System.out.println("Please choose an option:");
				optionSelected = option.nextInt();
			}
		}

		if (optionSelected == lastOption) {
			run();
		} else {
			Property selected = selectedProperty(locationwise, optionSelected);
			provideDates();
			provideDetails(selected);

		}
	}

	// function to return the selectedProperty by the users
	public Property selectedProperty(ArrayList<Property> locationwise, int optionSelected) {
		for (int i = 0; i < locationwise.size(); i = i + 1) {
			if (optionSelected == i + 1) {
				selectedProperty = locationwise.get(i);

			}
		}

		return selectedProperty;
	}

	// function to check whether user input type is valid or not
	public boolean validType(String type, int max) {
		if (Integer.parseInt(type) > max || Integer.parseInt(type) <= 0) {
			return false;
		}
		return true;
	}

	// function to check the valid user input for minimum rating
	public boolean checkRatingRange(Double location) {
		if (location >= 5) {
			return false;
		}
		return true;
	}

	// function called whenever the user wants to exit the program
	public void exit() {
		System.out.println("You exited our program");
	}

	// function to take input of user checkin and checkout dates
	public void provideDates() {

		System.out.println("> Provide dates ");
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please provide check-in date (dd/mm/yyyy):");
		date = scanner.next();
		Date currentDate = new java.util.Date();
		String c = new SimpleDateFormat("dd/MM/yyyy").format(currentDate);

		long validCheckin = calDuration(c, date);
		while (validCheckin < 0) {
			System.out.println("Enter a future date:");
			date = scanner.next();
			validCheckin = calDuration(c, date);
		}
		Date checkIndate;
		Date checOutdate;
		try {
			checkIndate = new SimpleDateFormat("dd/MM/yyyy").parse(date);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Please provide checkout date (dd/mm/yyyy):");

		date2 = scanner.next();
		long validDif = calDuration(c, date2);
		long validDif2 = calDuration(date, date2);
		while (validDif < 0 || validDif2 < 0) {
			System.out.println(validDif + " " + validDif2);
			System.out.println("Enter a valid date which is not old than checkin date:");
			date2 = scanner.next();
			validDif = calDuration(c, date2);
			validDif2 = calDuration(date, date2);

		}

		try {
			checOutdate = new SimpleDateFormat("dd/MM/yyyy").parse(date2);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

	}

	// function to calculate the duration between two dates
	public long calDuration(String date, String date2) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate d1 = LocalDate.parse(date, formatter);
		LocalDate d2 = LocalDate.parse(date2, formatter);
		long noOfDaysDifference = ChronoUnit.DAYS.between(d1, d2);
		return noOfDaysDifference;

	}

	// function to print the property details along with price
	public void provideDetails(Property selected) {
		double price = Calprice(selected.getPricePerNight(), calDuration(date, date2));
		double disPrice = getDiscountedPrice(selected.getPricePerNight(), calDuration(date, date2),
				selected.getweeklyDis());
		System.out.println("> Show property details");
		System.out.println(selected.toString());
		System.out.printf("Total Price:$%.2f ( %.2f" + " * " + calDuration(date, date2) + "nights)\n", price,
				selected.getPricePerNight());
		System.out.printf("Discounted Price:$ %.2f ( %.2f" + "*" + calDuration(date, date2) + "nights)\n",
				disPrice * calDuration(date, date2), disPrice);
		System.out.printf("Service fee:%.2f\n", selected.getServiceFee() * calDuration(date, date2));
		System.out.printf("Total: %.2f\n", TotalPayment(selected));
		reservation();
	}

	// function to calculate the price based on rate per night
	public double Calprice(double price, long duration) {
		double totalPrice = price * duration;
		return totalPrice;
	}

	// function to calculate the discounted price for per night
	public double getDiscountedPrice(double price, long duration, double weeklyDis) {
		double discountedPrice = 0;
		if (duration >= 7) {
			discountedPrice = (price * ((100 - weeklyDis) / 100));
		} else {
			discountedPrice = price;
		}
		return discountedPrice;
	}

	// function to calculate the total payment for the total duration of stay
	public double TotalPayment(Property selected) {
		double totalPrice = Calprice(selected.getPricePerNight(), calDuration(date, date2));
		double discountedPrice = getDiscountedPrice(selected.getPricePerNight(), calDuration(date, date2),
				selected.getweeklyDis()) * calDuration(date, date2);
		double totalServiceFee = selected.getServiceFee() * calDuration(date, date2);
		double price = discountedPrice + totalServiceFee + selected.getCleaningFee();
		return price;
	}

	// function to confirm reservation of a property
	public void reservation() {
		Scanner input = new Scanner(System.in);
		System.out.println("Would you like to reserve the property (Y/N)?");
		String chosen = input.next();
		if (chosen.compareTo("Y") == 0) {
			CustomerDetails();

		} else {
			exit();
		}

	}

	// functiom to print all the details after booking has been finalised
	public void CustomerDetails() {
		System.out.println("Please provide your given name:");
		Scanner input = new Scanner(System.in);
		String name = input.next();
		System.out.println("Please provide your surname:");
		Scanner input2 = new Scanner(System.in);
		String surname = input2.next();
		System.out.println("Please provide your email address:");
		Scanner input3 = new Scanner(System.in);
		String email = input3.next();
		System.out.println("Please provide number of guests:");
		Scanner input4 = new Scanner(System.in);
		String guestsNo = input4.next();
		System.out.println("Confirm and pay (Y/N):");
		Scanner input5 = new Scanner(System.in);
		String pay = input5.next();
		if (pay.compareTo("Y") == 0) {
			System.out.println("> Congratulations! Your trip is booked. A receipt has been sent to your email. \n"
					+ "  Details of your trip are shown below.  \n"
					+ "  Your host will contact you before your trip. Enjoy your stay! ");
			details(name, surname, email, guestsNo);
		} else {
			exit();
		}
	}

	// function to format the details
	public void details(String name, String surname, String email, String guestsNo) {
		System.out.printf("%-15s %s %s \n%-15s %s \n%-15s %s guests \n%-15s %s \n%-15s %s\n%-15s %s %s %s\n%-15s %.2f",
				"Name:", name, surname, "Email:", email, "Who's coming:", guestsNo, "Check-in date:", date,
				"Checkout date:", date2, "Your stay:", selectedProperty.getName(), "hosted by",
				selectedProperty.getHostName(), "Total payment: $", TotalPayment(selectedProperty));
	}

}
