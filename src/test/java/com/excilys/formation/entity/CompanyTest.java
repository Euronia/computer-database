package com.excilys.formation.entity;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CompanyTest {
	
	private Company testCompany;

	@Before
	public void setUp() throws Exception {
		testCompany = new Company("name");
		testCompany.id = 1;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testCompany() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetId() {
		assertEquals(testCompany.getId(),testCompany.id);
	}

	@Test
	public void testSetId() {
		testCompany.setId(12);
		assertEquals(testCompany.id, 12);
	}

	@Test
	public void testGetName() {
		assertEquals(testCompany.getName(),testCompany.name);
	}

	@Test
	public void testSetName() {
		testCompany.setName("foo");
		assertEquals(testCompany.name, "foo");
	}

	@Test
	public void testGetComputers() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetComputers() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddComputer() {
		fail("Not yet implemented");
	}

	@Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

}
