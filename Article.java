package assignment1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Renee Salz, David Carbonez
 *	* This class represents a scientific article using:
 *			- ID
 *			- title
 *			- author(s)
 *			- name of the journal it was published in
 *			- issue number of the journal
 *			- year of publication
 *			- indexweight of the publication
 *			- the articles it cites
 *			- the articles it is cited by
 *
 *@invar	- A publication must have at least one author
 *			- Every author must have a first and a last name, separated by a comma
 *			- The name of the journal should contain at least one character
 *			- The issue number should be larger than 0
 *			- A publication must cite at least 1 other publication
 *			- An ID must be unique
 */

public class Article extends Publication{
	
	/**
	 * Initialize this new article with parameters of superclass and given journal and issue number
	 * 
	 * @param journal
	 * 		  The journal in which the article was published
	 * @param issue
	 * 		  The issue number of the journal in which the article was published

	 */
	
	private String journal;
	private int issue;
	


	//overload constructor to allow for no citations? or maybe smart to initialize blank Article[]s---> those are initialized in the superclass already
	public Article(String title, String[] authors, int year, Publication[] cites, Publication[] citedBy, String journal, int issue) {
		super(title, authors, year, 1.0, cites, citedBy);
		if(!isValidJournal(journal))
			throw new IllegalArgumentException("Invalid journal name");
		this.journal = journal;		
		if (!isValidIssueNumber(issue))
			throw new IllegalArgumentException("Invalid issue number");
		this.issue = issue;		
	}


	/**
	 * basic, immutable
	 * Inspector that returns the journal in which the article is published
	 * @return journal of article
	 */
	public String getJournal() {
		return this.journal;

	}

	/**
	 * basic, immutable
	 * Inspector that returns the issue of the journal in which the article is published
	 * @return issue of article
	 */
	public int getIssue() {
		return this.issue;
	}
	
	public static boolean isValidJournal(String journal) {
		boolean checkJournal = false;
		//checkJournal will only be set to true if journalname contains at least one letter
		checkJournal = journal.length()>0;
		return checkJournal;
	}
	
	public static boolean isValidIssueNumber(int issue) {
		boolean checkIssue = false;
		//checkIssue will only be set to true if issue is > 0
		checkIssue = issue>0;
		return checkIssue;
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
		System.out.println("Title: " + this.getTitle() + ", Authors: " + Arrays.toString(this.getAuthors()) + ", Journal: " + this.getJournal() + ", Issue: " + this.getIssue() + ", Year: " + this.getYear() + ", Cites: " + Arrays.toString(citesIDs.toArray())+ ", Cited By: " + Arrays.toString(citedByIDs.toArray()));



	}
}
