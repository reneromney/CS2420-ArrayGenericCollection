package assign2;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;

/**
 * Testing class for LibraryGeneric.
 * 
 * @author Erin Parker, Jordan Hensley, and Romney Doria CS 2420 Fall 2015
 *         jHensley doria Assignment 2 9/6/2015
 */
public class LibraryGenericTester {

	@Test
	public void testINTID() {

		// Test basic functionality of an aditional type
		LibraryGeneric<Integer> intLib = new LibraryGeneric<Integer>();
		intLib.addAll("Mushroom_Publishing.txt");
		Integer myInt = 1234567;
		assertEquals(true, intLib.checkout(9781843191230L, myInt, 1, 1, 2008));
		assertEquals("1234567", intLib.lookup(9781843191230L).toString());
		LibraryBook testBook1 = new LibraryBook(9781843191230L, "Mary Lancaster", "An Endless Exile");
		assertEquals(testBook1, intLib.lookup(myInt).get(0));

	}

	@Test
	public void testNameID() {

		// test a library that uses names (String) to id patrons
		LibraryGeneric<String> lib1 = new LibraryGeneric<String>();

		// test empty library
		assertNull(lib1.lookup(234802348L));
		assertFalse(lib1.checkout(20384L, "holder", 3, 3, 3));
		assertFalse(lib1.checkin(20384023L));
		assertFalse(lib1.checkin("name"));
		assertEquals(new ArrayList<String>(), lib1.lookup("holder"));

		// test library that uses names
		lib1.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		lib1.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		lib1.add(9780446580342L, "David Baldacci", "Simple Genius");
		lib1.addAll("Mushroom_Publishing.txt");

		String patron1 = "Jane Doe";
		String patrontest = "Romney";

		// test no such book
		assertNull(lib1.lookup(2374972394L));
		assertEquals(false, lib1.checkout(203580238L, "test", 3, 3, 3));

		// first checkout
		assertTrue(lib1.checkout(9780330351690L, patron1, 1, 1, 2008));
		assertFalse(lib1.checkout(9780330351690L, patrontest, 1, 1, 2008));

		// second checkout
		assertTrue(lib1.checkout(9780374292799L, patron1, 1, 1, 2008));

		// lookup isbn
		assertEquals(patron1, lib1.lookup(9780374292799L));

		// lookup holder
		ArrayList<LibraryBookGeneric<String>> booksCheckedOut1 = lib1.lookup(patron1);
		assertNotNull(booksCheckedOut1);
		assertEquals(2, booksCheckedOut1.size());
		assertTrue(booksCheckedOut1.contains(new Book(9780330351690L, "Jon Krakauer", "Into the Wild")));
		assertTrue(booksCheckedOut1.contains(new Book(9780374292799L, "Thomas L. Friedman", "The World is Flat")));
		assertEquals(patron1, booksCheckedOut1.get(0).getHolder());
		assertEquals(new GregorianCalendar(2008, 1, 1), booksCheckedOut1.get(0).getDueDate());
		assertEquals(patron1, booksCheckedOut1.get(1).getHolder());
		assertEquals(new GregorianCalendar(2008, 1, 1), booksCheckedOut1.get(1).getDueDate());

		// checkin holder
		assertFalse(lib1.checkin(9780446580342L));
		assertFalse(lib1.checkin(203840238402L));
		assertTrue(lib1.checkin(patron1));

	}

	@Test
	public void testPhoneNumberID() {

		// test a library that uses phone numbers (PhoneNumber) to id patrons
		LibraryGeneric<PhoneNumber> lib2 = new LibraryGeneric<PhoneNumber>();
		lib2.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		lib2.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		lib2.add(9780446580342L, "David Baldacci", "Simple Genius");

		PhoneNumber patron2 = new PhoneNumber("801.555.1234");
		PhoneNumber patron3 = new PhoneNumber("555.555.5555");

		// first checkout
		assertTrue(lib2.checkout(9780330351690L, patron2, 1, 1, 2008));
		assertFalse(lib2.checkout(9780330351690L, patron3, 3, 3, 3));
		assertFalse(lib2.checkout(230840328L, patron3, 3, 3, 3));

		// second checkout
		assertTrue(lib2.checkout(9780374292799L, patron2, 1, 1, 2008));

		ArrayList<LibraryBookGeneric<PhoneNumber>> booksCheckedOut2 = lib2.lookup(patron2);

		// lookup holder
		assertNull(lib2.lookup(20384023L));
		assertNotNull(booksCheckedOut2 == null);
		assertEquals(2, booksCheckedOut2.size());
		assertTrue(booksCheckedOut2.contains(new Book(9780330351690L, "Jon Krakauer", "Into the Wild")));
		assertTrue(booksCheckedOut2.contains(new Book(9780374292799L, "Thomas L. Friedman", "The World is Flat")));
		assertEquals(patron2, booksCheckedOut2.get(0).getHolder());
		assertEquals(new GregorianCalendar(2008, 1, 1), booksCheckedOut2.get(0).getDueDate());
		assertEquals(patron2, booksCheckedOut2.get(1).getHolder());
		assertEquals(new GregorianCalendar(2008, 1, 1), booksCheckedOut2.get(1).getDueDate());

		// checkin holder
		assertTrue(lib2.checkin(patron2));

	}

	@Test
	public void testInventory() {

		// test inventory lookup
		LibraryBookGeneric<Integer> testbook1 = new LibraryBookGeneric<Integer>(9781843190004L, "test1", "title1");
		LibraryBookGeneric<Integer> testbook2 = new LibraryBookGeneric<Integer>(29374028304L, "test2", "title2");
		LibraryBookGeneric<Integer> testbook3 = new LibraryBookGeneric<Integer>(234123L, "test3", "title3");

		// create the unsorted list
		LibraryGeneric<Integer> unsorted = new LibraryGeneric<Integer>();
		unsorted.add(9781843190004L, "test1", "title1");
		unsorted.add(29374028304L, "test2", "title2");
		unsorted.add(234123L, "test3", "title3");

		// create the sorted list
		ArrayList<LibraryBookGeneric<Integer>> sorted = new ArrayList<LibraryBookGeneric<Integer>>();
		sorted.add(testbook3);
		sorted.add(testbook2);
		sorted.add(testbook1);

		assertEquals(sorted, unsorted.getInventoryList());
	}

	@Test
	public void getOverdueTest() {

		// Create a list of all books
		LibraryGeneric<String> unsorted = new LibraryGeneric<String>();
		unsorted.addAll("Mushroom_Publishing.txt");

		// Check out some books
		unsorted.checkout(9781843190028L, "test1", 3, 3, 2034);
		unsorted.checkout(9781843190042L, "test2", 4, 8, 2015);
		unsorted.checkout(9781843190073L, "test3", 5, 19, 2000);

		// create the used books
		LibraryBookGeneric<String> testbook1 = new LibraryBookGeneric<String>(9781843190028L, "Moyra Caldecott",
				"Crystal Legends");
		LibraryBookGeneric<String> testbook2 = new LibraryBookGeneric<String>(9781843190042L, "Martyn Folkes",
				"Bath City Centre Street Map and Guide");
		LibraryBookGeneric<String> testbook3 = new LibraryBookGeneric<String>(9781843190073L, "Jen Alexander",
				"The Coming of the Third");

		// add books in expected order
		ArrayList<LibraryBookGeneric<String>> sorted = new ArrayList<LibraryBookGeneric<String>>(
				Arrays.asList(testbook3, testbook2, testbook1));

		// run test
		assertEquals(sorted, unsorted.getOverdueList(1, 1, 2050));
	}

	@Test
	public void testCompAuthor() {

		// create books
		LibraryBookGeneric<String> book5 = new LibraryBookGeneric<String>(9780374292799L, "Thomas L. Friedman",
				"The World is Flat");
		LibraryBookGeneric<String> book4 = new LibraryBookGeneric<String>(9780330351690L, "Jon Krakauer",
				"Into the Wild");
		LibraryBookGeneric<String> book2 = new LibraryBookGeneric<String>(9780446580342L, "David Baldacci",
				"Simple Genius");
		LibraryBookGeneric<String> book1 = new LibraryBookGeneric<String>(9781843190677L, "Cheryl Jones",
				"Herbs for Healthy Skin");
		LibraryBookGeneric<String> book3 = new LibraryBookGeneric<String>(9781843190073L, "Jen Alexander",
				"The Coming of the Third");
		LibraryBookGeneric<String> book6 = new LibraryBookGeneric<String>(9781843190677L, "cat Jones",
				"Cats for Healthy Skin");

		// add the books in an unsorted order
		ArrayList<LibraryBookGeneric<String>> unsortedBooks = new ArrayList<LibraryBookGeneric<String>>(
				Arrays.asList(book5, book4, book2, book1, book3, book6));
		LibraryGeneric<String> unsortedBooksLib = new LibraryGeneric<String>();
		unsortedBooksLib.addAll(unsortedBooks);

		// create the sorted list
		ArrayList<LibraryBookGeneric<String>> sortedBooks = new ArrayList<LibraryBookGeneric<String>>(
				Arrays.asList(book6, book1, book2, book3, book4, book5));

		// run test
		assertEquals(sortedBooks, unsortedBooksLib.getOrderedByAuthor());

	}
}
