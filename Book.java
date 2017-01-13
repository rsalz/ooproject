package assignment1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *@author Renee Salz, David Carbonez	
 ** This class represents a book using:
 *			- ID
 *			- title
 *			- author(s)
 *			- publisher
 *			- year of publication
 *			- indexweight of the publication
 *			- the articles it cites
 *			- the articles it is cited by
 *
 *@invar	- A publication must have at least one author
 *			- The name of the publisher should contain at least one character
 *			- Every author must have a first and a last name, separated by a comma
 */

public class Book extends Publication {
	
	/**
	 * Initialize this new article with parameters of superclass and given publisher
	 * 
	 * @param publisher
	 * 		  The publisher of the book
	 */

	private String publisher;

	public Book(String title, String[] authors, int year, Publication[] cites, Publication[] citedBy, String publisher) {
		super(title, authors, year, 1.2, cites, citedBy);
		if(!isValidPublisher(publisher))
			throw new IllegalArgumentException("Invalid publisher name");
		this.publisher=publisher;
	}
	/**
	 * basic, immutable
	 * Inspector that returns the publisher of book
	 * @return publisher of book
	 */
	public String getPublisher() {
		return this.publisher;
	}
	
	/**
	 * Return a boolean reflecting whether the given pupblishername is a valid publishername
	 * @param publisher
	 * 		  The publishername to check
	 * @return True if and only if the name of the publisher contains 1 or more characters
	 */
	public static boolean isValidPublisher(String publisher) {
		boolean checkPublisher = false;
		checkPublisher = publisher.length()>0;
		return checkPublisher;
	}
	
	/**
	 * basic, immutable
	 * returns the indexweight
	 * @return indexweight
	 */
	@Override
	public double getIndexWeight() {
		return 1.2;
	}

	/**
	 * Method that allows to print a publication as a sequence of its attributes
	 */
	@Override
	public void printPublication() {
		List<String> citesIDs = new ArrayList<String>();
		List<String> citedByIDs = new ArrayList<String>();
		List<Publication> citesPublications = new ArrayList<Publication>(Arrays.asList(this.getCites()));
		for (Publication b: citesPublications) {
			citesIDs.add(b.getID());	
		}
		List<Publication> citedByPublications = new ArrayList<Publication>(Arrays.asList(this.getCitedBy()));
		for (Publication c: citedByPublications) {
			citedByIDs.add(c.getID());
		}
		System.out.println("ID: " + this.getID() + ", Title: " + this.getTitle() + ", Authors: " + Arrays.toString(this.convertAuthorNames()) + ", Publisher: " + this.getPublisher() + ", Year: " + this.getYear() + ", Cites: " + Arrays.toString(citesIDs.toArray()) + ", Cited By: " + Arrays.toString(citedByIDs.toArray()));

	}
}

