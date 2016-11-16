package com.excilys.formation.entity;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;

import junit.framework.TestCase;

public class ComputerTest extends TestCase {
	
	private static Computer testComputer;
	Company testCompany = new Company("testCompany");

	@Before
	public void setUp() throws Exception {
		
		testComputer = new Computer.ComputerBuilder("nom",testCompany).build();
	}
	
	@After
	public void tearDown() throws Exception {
		testComputer.id =1;
		testComputer.name ="nom";
		testComputer.introduced = null;
		testComputer.discontinued = null;
		testComputer.manufacturer = testCompany;
	}

	public void testHashCode() {
		fail("Not yet implemented");
	}

	public void testComputer() {
	}

	public void testGetId() {
		assertEquals(testComputer.getId(), testComputer.id);
	}

	public void testSetId() {
		testComputer.setId(12);
		assertEquals(testComputer.id, 12);
	}

	public void testGetName() {
		assertEquals(testComputer.getName(), testComputer.name);
	}

	public void testSetName() {
		testComputer.setName("foo");
		assertEquals(testComputer.name, "foo");
		testComputer.setName(null);
		assertNull(testComputer.name);
	}

	public void testGetIntroduced() {
		assertEquals(testComputer.getIntroduced(), testComputer.introduced);
	}

	public void testSetIntroduced() {
		testComputer.setIntroduced(LocalDate.parse("2007-12-12"));
		assertEquals(testComputer.introduced, LocalDate.parse("2007-12-12"));
	}

	public void testGetDiscontinued() {
		assertEquals(testComputer.getDiscontinued(), testComputer.discontinued);
	}

	public void testSetDiscontinued() {
		testComputer.setDiscontinued(LocalDate.parse("2007-12-12"));
		assertEquals(testComputer.discontinued, LocalDate.parse("2007-12-12"));
	}

	public void testGetManufacturer() {
		assertEquals(testComputer.getManufacturer(), testComputer.manufacturer);
	}

	public void testSetManufacturer() {
		fail("Not yet implemented");
	}

	public void testToString() {
		fail("Not yet implemented");
	}

	public void testEqualsObject() {
		assertTrue(testComputer.equals(testComputer));
		assertFalse(testComputer.equals(null));
	}

}
