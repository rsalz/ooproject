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
	 *			- A publication must cite at least 1 other publication
	 *			- An ID must be unique
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

	public String getConference() {
		return this.conference;
	}
	
	public static boolean isValidConference(String conference) {
		boolean checkConference = false;
		//checkJournal will only be set to true if publisher contains at least one letter
		checkConference = conference.length()>0;
		return checkConference;
	}

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
		System.out.println("Title: " + this.getTitle() + ", Authors: " + Arrays.toString(this.getAuthors()) + ", Conference: " + this.getConference() + ", Year: " + this.getYear() + ", Cites: " + Arrays.toString(citesIDs.toArray()) + ", Cited By: " + Arrays.toString(citedByIDs.toArray()));

	}
}
