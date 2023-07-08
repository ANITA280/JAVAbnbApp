import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static ArrayList<Property> propertyList = new ArrayList<>();

	public static void main(String[] args) {
		// Creating an instance of CustomerService and running the whole program
		CustomerService service = new CustomerService();
		service.run();

	}
}
