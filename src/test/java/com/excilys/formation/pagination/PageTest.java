package com.excilys.formation.pagination;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PageTest {
    Page<Object> testPage;

    @Before
    public void setUp() throws Exception {
        testPage = new Page<Object>();
        testPage.currentPage = 2;
        testPage.elements = null;
        testPage.elementsPerPage = 12;
        testPage.nbPages = 5 ;
        testPage.totalElements = 53 ;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testPage() {
        testPage = new Page<>(10);
        assertEquals(testPage.elementsPerPage,10);
        assertNotNull(testPage);
    }

    @Test
    public void testPageInt() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetElementsByPage() {
        assertEquals(testPage.getElementsByPage(),12);
    }

    @Test
    public void testSetElementsByPage() {
        testPage.setElementsByPage(10);
        assertEquals(testPage.elementsPerPage,10);
    }

    @Test
    public void testGetTotalElements() {
        assertEquals(testPage.getTotalElements(),53);
    }

    @Test
    public void testSetTotalElements() {
        testPage.setTotalElements(50);
        assertEquals(testPage.totalElements,50);
    }

    @Test
    public void testGetCurrentPage() {
        assertEquals(testPage.getCurrentPage(),2);
    }

    @Test
    public void testSetCurrentPage() {
        testPage.setCurrentPage(4);
        assertEquals(testPage.currentPage,4);
    }

    @Test
    public void testGetNbPages() {
        assertEquals(testPage.getNbPages(),5);
    }

    @Test
    public void testSetNbPages() {
        testPage.setNbPages(7);
        assertEquals(testPage.nbPages,7);
    }

    @Test
    public void testSetElements() {
        Object obj = new Object();
        List<Object> objects = new ArrayList<Object>();
        objects.add(obj);
        testPage.setElements(objects);
        assertEquals(testPage.elements,objects);
    }

    @Test
    public void testNextPage() {
        assertTrue(testPage.nextPage());
        testPage.currentPage = 5;
        assertFalse(testPage.nextPage());
    }

    @Test
    public void testPrevPage() {
        assertTrue(testPage.prevPage());
        testPage.currentPage = 1;
        assertFalse(testPage.prevPage());
    }

    @Test
    public void testSetPage() {
        assertTrue(testPage.setPage(3));
        assertFalse(testPage.setPage(0));
        assertFalse(testPage.setPage(Integer.MAX_VALUE));
    }

    @Test
    public void testCalculateNbPages() {
        fail("Not yet implemented");
    }

}
