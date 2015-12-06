package assign2;

import java.util.GregorianCalendar;

/**
 * Library book class that constructs a new a book for the library and contains
 * methods to check that book in and out.
 * 
 * @author Jordan Hensley and Romney Doria CS fall 2015 9/6/2015
 *
 */
public class LibraryBook extends Book {

	private String bookHolder;
	private GregorianCalendar dueDate;

	/**
	 * Creates a new book with the given isbn, author, and title
	 * 
	 * @param _isbn
	 * @param _author
	 * @param _title
	 */
	public LibraryBook(long _isbn, String _author, String _title) {
		super(_isbn, _author, _title);
		bookHolder = null;
		dueDate = null;
	}

	/**
	 * Returns the holder of the book the method is called on
	 * 
	 * @return holder of book
	 */
	public String getHolder() {
		return this.bookHolder;
	}

	/**
	 * Returns the GregorianCalendar dueDate of the current book
	 * 
	 * @return GregorianCalendar due date of book
	 */
	public GregorianCalendar getDueDate() {
		return this.dueDate;
	}

	/**
	 * Checks in the book by setting due date and holder to null.
	 */
	public void checkIn() {
		bookHolder = null;
		dueDate = null;
	}

	/**
	 * Checks out the current book by setting the holder name to name and the
	 * due date to the given GregorianCalendar
	 * 
	 * @param name
	 * @param date
	 */
	public void checkOut(String name, GregorianCalendar date) {
		this.bookHolder = name;
		dueDate = date;
	}

}
