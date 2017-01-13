package assignment1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *@author Renee Salz, David Carbonez	
 ** This class represents a scientific publication using:
 *			- ID
 *			- title
 *			- author(s)
 *			- conference name
 *			- year of publication
 *			- indexweight of the publication
 *			- the articles it cites
 *			- the articles it is cited by
 *
 *@invar	- A publication must have at least one author
 *			- Every author must have a first and a last name, separated by a comma
 *			- A conference name must have at least on character
 */

public class ConferencePaper extends Publication{

	/**
	 * Initialize this new conference paper with parameters of superclass and given conference
	 * 
	 * @param conference
	 * 		  The name of the conference
	 */

	private String conference;

	public ConferencePaper(String title, String[] authors, int year, Publication[] cites, Publication[] citedBy, String conference) {
		super(title, authors, year, 0.7, cites, citedBy);
		if(!isValidConference(conference))
			throw new IllegalArgumentException("Invalid conference name");
		this.conference=conference;
	}

	/**
	 * basic, immutable
	 * Inspector that returns the name of the conference at which the article was published
	 * @return the name of the conference
	 */
	public String getConference() {
		return this.conference;
	}

	/**
	 * Return a boolean reflecting whether the given conferencename is a valid conferencename
	 * @param conference
	 * 		  The conferencename to check
	 * @return True if and only if the name of the conference contains 1 or more characters
	 */
	public static boolean isValidConference(String conference) {
		boolean checkConference = false;
		checkConference = conference.length()>0;
		return checkConference;
	}

	/**
	 * basic, immutable
	 * returns the indexweight
	 * @return indexweight
	 */
	@Override
	public double getIndexWeight() {
		return 0.7;
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
		System.out.println("ID: " + this.getID() + ", Title: " + this.getTitle() + ", Authors: " + Arrays.toString(this.convertAuthorNames()) + ", Conference: " + this.getConference() + ", Year: " + this.getYear() + ", Cites: " + Arrays.toString(citesIDs.toArray()) + ", Cited By: " + Arrays.toString(citedByIDs.toArray()));

	}
}
