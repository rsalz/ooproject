package assignment1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Renee Salz, David Carbonez
 * @version 2.0
 *	* This class represents an article in a scientfic journal using:
 *			- title
 *			- author(s)
 *			- name of the journal it was published in
 *			- issue number of the journal
 *			- year of publication
 *			- the articles it cites
 *			- the articles it is cited by
 *
 *@invar	- An article must have at least one author
 *			- Every author must have a first and a last name, separated by a comma
 *			- The name of the journal should contain at least one character
 *			- The issue number should be larger than 0
 *			- A journal article must cite at least 1 other journal article
 *			- An ID must be unique
 */
public class Article extends Publication{

	private String journal;
	private int issue;


	/**
	 * Initialize this new article with given title, authors, journal, issue and year
	 * 
	 * @param title
	 * 		  The title of the article
	 * @param authors
	 * 		  The author(s), "Lastname, Firstname" format in an array
	 * @param journal
	 * 		  The name of the journal the article was published in
	 * @param ID
	 * 		  A unique identifier for an article 
	 * @param issue
	 * 		  The issue number of the journal
	 * @param year 
	 * 		  The year of the publication
	 * @param cites
	 * 		  The articles cited by this article
	 * @param citedBy
	 * 		  The articles that cite this article
	 */

	//overload constructor to allow for no citations? or maybe smart to initialize blank Article[]s
	public Article(String title, String[] authors, int year, Publication[] cites, Publication[] citedBy, String journal, int issue) {
		super(title, authors, year, 1.0, cites, citedBy);
		assert(journal.length()>0);
		this.journal = journal;		
		assert(issue>0);
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

	@Override
	public void printPublication() {
		List<String> citesIDs = new ArrayList<String>();
		List<String> citedByIDs = new ArrayList<String>();
		for(Publication p : this.getCites()) {
			List<Publication> citesPublications = new ArrayList<Publication>(Arrays.asList(this.getCites()));
			for (Publication b: citesPublications) {
				citesIDs.add(b.getID());	
			}
		}
		for (Publication q: this.getCitedBy()) {
			List<Publication> citedByPublications = new ArrayList<Publication>(Arrays.asList(this.getCitedBy()));
			for (Publication c: citedByPublications) {
				citedByIDs.add(c.getID());
			}

		}
		System.out.println("Title: " + this.getTitle() + ", Authors: " + Arrays.toString(this.getAuthors()) + ", Journal: " + this.getJournal() + ", Issue: " + this.getIssue() + ", Year: " + this.getYear() + ", Cites: " + Arrays.toString(citesIDs.toArray())+ ", Cited By: " + Arrays.toString(citedByIDs.toArray()));



	}
}
