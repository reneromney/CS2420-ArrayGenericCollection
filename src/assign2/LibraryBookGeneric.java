package assign2;

import java.util.GregorianCalendar;

/**
 * A class that creates a new book for the library, with the ability of setting
 * the holder to a string or phone number, etc
 * 
 * @author Jordan Hensley and Romney Doria CS 2420 fall 2015 Assignment 2
 *         9/6/2015
 *
 * @param <Type>
 */
public class LibraryBookGeneric<Type> extends Book {

	private Type bookHolder;
	private GregorianCalendar dueDate;

	/**
	 * Creates the book with the given isbn, title, and author
	 * 
	 * @param _isbn
	 * @param _author
	 * @param _title
	 */
	public LibraryBookGeneric(long _isbn, String _author, String _title) {
		super(_isbn, _author, _title);
		bookHolder = null;
		dueDate = null;
	}

	/**
	 * @return the holder of the book
	 */
	public Type getHolder() {
		return this.bookHolder;
	}

	/**
	 * @return the GregorianCalendar due date of this book
	 */
	public GregorianCalendar getDueDate() {
		return this.dueDate;
	}

	/**
	 * Checks in this book by setting its holder to null and due date to null
	 */
	public void checkIn() {
		bookHolder = null;
		dueDate = null;
	}

	/**
	 * Checks out this book by setting the holder to the specified holder and
	 * holder type and sets the due date to the given GregorianCalendar date.
	 * 
	 * @param holderName
	 * @param date
	 */
	public void checkOut(Type holderName, GregorianCalendar date) {
		this.bookHolder = holderName;
		this.dueDate = date;
	}

}
