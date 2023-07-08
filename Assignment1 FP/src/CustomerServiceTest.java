
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CustomerServiceTest {
	private CustomerService service1, service2, service3;
	private Property property1, property2, property3;
	String date1 = "10/10/2022", date2 = "28/10/2022";

	@Before
	public void setUp() throws Exception {

		service1 = new CustomerService();
		service1.setSelectedProperty(0);
		property1 = service1.getSelectedProperty();
		service1.setdate(date1);
		service1.setdate2(date2);

		service2 = new CustomerService();
		service3 = new CustomerService();

	}

	@Test
	public void testCalPrice() {

		assertEquals(756.0, service1.Calprice(42, 18), 0.01);
		assertEquals(0.0, service1.Calprice(42, 0), 0.01);
		assertEquals(756.0, service1.Calprice(42, 18), 0.01);
		assertEquals(756.0, service1.Calprice(42, 18), 0.01);

	}

	@Test
	public void testgetDiscountedPrice() {

		assertEquals(39.90, service1.getDiscountedPrice(42.00, 18, 5), 0.01);
		assertEquals(42.00, service1.getDiscountedPrice(42.00, 6, 5), 0.01);
		assertEquals(48.36, service1.getDiscountedPrice(52.00, 31, 7), 0.01);

	}

	@Test
	public void testTotalPayment() {
		assertEquals(909.20, service1.TotalPayment(property1), 0.01);
//		assertEquals(42.00,service.TotalPayment(42.00,6,5),0.01);
//		assertEquals(48.36,service.TotalPayment(52.00,31,7),0.01);

	}

	@Test
	public void testSelectedProperty() {
		service1.setSelectedProperty(0);
		assertEquals(service1.getSelectedProperty(), service1.selectedProperty(service1.getPropertyList(), 0));
	}

	@Test
	public void testCalDuration() {
		service1.setSelectedProperty(0);
		assertEquals(10, service1.calDuration("10/10/2022", "20/10/2022"), 0.01);
		assertEquals(-1, service1.calDuration("10/10/2022", "09/10/2022"), 0.01);
		assertEquals(30, service1.calDuration("10/10/2022", "09/11/2022"), 0.01);

	}

	@Test
	public void testValidType() {
		service1.setSelectedProperty(0);
		assertFalse(service1.validType("5", 4));
		assertTrue(service1.validType("5", 5));
		assertFalse(service1.validType("6", 5));
		assertFalse(service1.validType("0", 5));
		assertFalse(service1.validType("-1", 5));

	}

	@Test
	public void testcheckRatingRange() {
		assertFalse(service1.checkRatingRange(6.0));
		assertFalse(service1.checkRatingRange(5.0));
		assertFalse(service1.checkRatingRange(5.1));
		assertTrue(service1.checkRatingRange(4.0));
	}

}
