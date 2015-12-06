package assign2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

/**
 * Testing class for Library.
 * 
 * @author Erin Parker, Jordan Hensley, Romney Doria
 * CS 2420 Fall 2015
 * Assignment 2
 * 9/6/2015
 * 
 */
public class LibraryTester {
	
	public static void main(String[] args){
	    // Do 10000 lookups and use the average running time.
	    int timesToLoop = 10000;
	    
	    // For each problem size n . . .
	    for (int n = 1000; n <= 10000; n += 1000) {

	      // Generate a new "random" library of size n.
	      Library randLib = new Library();
	      randLib.addAll(generateLibrary(n));

	      long startTime, midpointTime, stopTime;

	      // First, spin computing stuff until one second has gone by.
	      // This allows this thread to stabilize.
	      startTime = System.nanoTime();
	      while (System.nanoTime() - startTime < 1000000000) { // empty block 
	      }

	      // Now, run the test.  
	      startTime = System.nanoTime();

	      for (int i = 0; i < timesToLoop; i++) {
	        randLib.lookup(generateIsbn());
	      }

	      midpointTime = System.nanoTime();

	      // Run a loop to capture the cost of running the "timesToLoop" loop and 
	      // generating a random ISBN.
	      for (long i = 0; i < timesToLoop; i++) { 
	        generateIsbn();
	      }

	      stopTime = System.nanoTime();

	      // Compute the time, subtract the cost of running the loop
	      // from the cost of running the loop and doing the lookups.
	      // Average it over the number of runs.
	      double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime))
	          / timesToLoop;

	      System.out.println(n + "\t" + averageTime);
	  }
	}

	// test an empty library
	Library lib = new Library();
	ArrayList<LibraryBook> booksCheckedOut = lib.lookup("Jane Doe");

	@Test
	public void testEmptyLibrary() {
		// lookup isbn
		assertNull(lib.lookup(978037429279L));

		// lookup holder
		assertNotNull(booksCheckedOut);
		assertEquals(0, booksCheckedOut.size());

		// empty library checkout
		assertFalse(lib.checkout(978037429279L, "Jane Doe", 1, 1, 2008));

		// empty library checkin(isbn)
		assertFalse(lib.checkin(978037429279L));

		// empty library checkin(holder)
		assertFalse(lib.checkin("Jane Doe"));
	}

	@Test
	public void testSmallLibrary() {
		// test a small library
		lib.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		lib.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		lib.add(9780446580342L, "David Baldacci", "Simple Genius");

		// small library lookup isbn
		assertNull(lib.lookup(9780330351690L));

		// small library checkout
		assertTrue(lib.checkout(9780330351690L, "Jane Doe", 1, 1, 2008));

		booksCheckedOut = lib.lookup("Jane Doe");
		// small library lookup holder
		assertNotNull(booksCheckedOut);
		assertTrue(booksCheckedOut.size() == 1);
		assertEquals(new Book(9780330351690L, "Jon Krakauer", "Into the Wild"), booksCheckedOut.get(0));
		assertEquals("Jane Doe", booksCheckedOut.get(0).getHolder());

		// small library checkin(isbn)
		assertTrue(lib.checkin(9780330351690L));

		// small library checkin(holder)
		assertFalse(lib.checkin("Jane Doe"));
	}

	@Test
	public void testMediumLibrary() {
		// test a medium library
		lib.addAll("Mushroom_Publishing.txt");

		//Books to test equals method
		LibraryBook testBook1 = new LibraryBook(9781843190004L, "Moyra Caldecott", "Weapons of the Wolfhound");
		LibraryBook testBook2 = new LibraryBook(9781843190004L, "Moyra Caldecott", "Weapons of the Wolfhound");
		LibraryBook testBook3 = new LibraryBook(9781843190004L, "Caldecott Moyra", "Weapons of the Wolfhound");
		LibraryBook testBook4 = new LibraryBook(2938759237592L, "Moyra Caldecott", "Weapons of the Wolfhound");
		LibraryBook testBook5 = new LibraryBook(9781843190004L, "moyra Caldecott", "Weapons of the Wolfhound");
		
		//testing equals method
		assertEquals(true, testBook1.equals(testBook2));
		assertEquals(false, testBook1.equals(testBook3));
		assertEquals(false, testBook1.equals(testBook4));
		assertEquals(false, testBook1.equals(testBook5));
		
		//test an empty checkout list
		assertNotNull(lib.lookup("test"));
		assertEquals(0, lib.lookup("test").size());
		assertEquals(null, lib.lookup(1209583252035L));
		
		//Test check out
		lib.checkout(9781843190004L, "holderName", 3, 3, 2016);
		
		assertEquals("holderName", lib.lookup("holderName").get(0).getHolder());
		assertEquals(testBook1, lib.lookup("holderName").get(0));
		assertEquals("holderName", lib.lookup(9781843190004L));
		
		//Test checkin by isbn and holderName
		assertEquals(true, lib.checkin(9781843190004L));
		lib.checkout(9781843190004L, "holderName", 3, 3, 2016);
		assertEquals(true, lib.checkin("holderName"));
		
		//Test multiple checkouts
		lib.checkout(9781843193319L, "Romney", 1, 1, 2017);
		lib.checkout(9781843190769L, "Romney", 1, 1, 2017);
		lib.checkout(9781843190394L, "Romney", 1, 1, 2017);
		
		//Test checking out the same book
		assertEquals(false,(lib.checkout(9781843193319L, "Romney", 1, 1, 2017)) ); 
		
		//Test the size of the lookup list
		assertEquals(3, lib.lookup("Romney").size());
		assertEquals(true, (lib.checkin(9781843193319L)));
		assertEquals(2, lib.lookup("Romney").size());
		
		//Test lookup by isbn
		assertEquals("Romney", lib.lookup(9781843190769L));
		assertEquals(null, lib.lookup(2038238402L));
		
		//Check false checkins and checkouts
		assertEquals(false, lib.checkout(23759328503L, "name", 3, 3, 3));
		assertEquals(false, lib.checkin(2374923L));
		assertEquals(false, lib.checkin("noName"));
		
		//Test checkin by name
		assertEquals(true, (lib.checkin("Romney")));
		assertEquals(0, lib.lookup("Romney").size());
	}

	/**
	 * Returns a library of "dummy" books (random ISBN and placeholders for
	 * author and title).
	 * 
	 * Useful for collecting running times for operations on libraries of
	 * varying size.
	 * 
	 * @param size
	 *            -- size of the library to be generated
	 */
	public static ArrayList<LibraryBook> generateLibrary(int size) {
		ArrayList<LibraryBook> result = new ArrayList<LibraryBook>();

		for(int i = 0; i < size; i++) {
			// generate random ISBN
			Random randomNumGen = new Random();
			String isbn = "";
			for(int j = 0; j < 13; j++)
				isbn += randomNumGen.nextInt(10);

			result.add(new LibraryBook(Long.parseLong(isbn), "An author", "A title"));
		}

		return result;
	}

	/**
	 * Returns a randomly-generated ISBN (a long with 13 digits).
	 * 
	 * Useful for collecting running times for operations on libraries of
	 * varying size.
	 */
	public static long generateIsbn() {
		Random randomNumGen = new Random();

		String isbn = "";
		for(int j = 0; j < 13; j++)
			isbn += randomNumGen.nextInt(10);

		return Long.parseLong(isbn);
	}
}
