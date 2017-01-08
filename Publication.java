package assignment1;

import java.util.Arrays;
import java.util.Calendar;

/**
 * @author Renee Salz, David Carbonez
 *	* This class represents a scientific publication using:
 *			- ID
 *			- title
 *			- author(s)
 *			- //name of the journal it was published in
 *			- //issue number of the journal
 *			- year of publication
 *			- indexweight of the publication
 *			- the articles it cites
 *			- the articles it is cited by
 *
 *@invar	- A publication must have at least one author
 *			- Every author must have a first and a last name, separated by a comma
 *			- //The name of the journal should contain at least one character
 *			- //The issue number should be larger than 0
 *			- A publication must cite at least 1 other publication
 *			- An ID must be unique
 */

public abstract class Publication {
	
	/**
	 * Initialize this new publication with given ID, title, authors, year, indexWeight, cites & citedby
	 * 
	 * @param ID
	 *  	   A unique identifier for an article 
	 * @param title
	 * 		  The title of the publication
	 * @param authors
	 * 		  The author(s), "Lastname, Firstname" format in an array
	 * @param year 
	 * 		  The year of the publication
	 * @param indexWeight
	 * 		  The weight of a publication in the indexcalculation
	 * @param cites
	 * 		  The articles cited by this publication
	 * @param citedBy
	 * 		  The articles that cite this publication
	 */

	protected String ID;
	protected String title;
	protected String[] authors;
	protected int year;
	protected double indexWeight;
	protected Publication[] cites;
	protected Publication[] citedBy;
	
	
	public Publication(String title, String[] authors, int year, double indexWeight, Publication[] cites, Publication[] citedBy) throws IllegalArgumentException {
		this.title=title;
		if (!hasAuthor(authors))
			throw new IllegalArgumentException("No authors found");
		else
		for (String auth : authors) {
			if (!isValidAuthor(auth))
				throw new IllegalArgumentException("Invalid author format");
		}
		this.authors = authors;
		this.year = year;
		this.cites = cites;
		this.citedBy = citedBy;
		this.ID= this.authors[0].substring(0, 3).toUpperCase() +  this.title.substring(0, 3).toUpperCase() + this.year;

		
	}
	
	public String getID(){
		return this.ID;
	}

	public Publication[] getCites() {
		return cites;
	}
	
	public void setCites(Publication[] cites) {
		this.cites = cites;
	}
	
	public Publication[] getCitedBy() {
		return citedBy;
	}

	public void setCitedby(Publication[] citedBy) {
		this.citedBy = citedBy;
	}
	
	public void setIndexWeight(double indexWeight) { //this is public so it can be set by database (see assignment description)
		this.indexWeight=indexWeight;
	}
	public double getIndexWeight() {
		return this.indexWeight;
	}


	/**
	 * basic, immutable
	 * Inspector that returns the title that has been appointed to the article 
	 * @return title of article
	 */

	public String getTitle(){
		return this.title;
	}

	/**
	 * The title of the article
	 * Precondition:
	 *		* title should contain at least one character
	 * @param title
	 */
	public void setTitle(String title) {
		assert(title.length()>0);
		this.title= title;
	}

	public static boolean isValidAuthor(String author) {
		boolean checkAuthor= false;
		//checkAuthor is set to true, only when the length of authors != 0 and when author contains a comma
		checkAuthor = (author.contains(", ") && author.length()>0);
		return checkAuthor;
		}
	
	public static boolean hasAuthor(String[] author) {
		boolean hasAuthor = false;
		hasAuthor = author.length>0;
		return hasAuthor;
	}


	/**
	 * basic, immutable
	 * Inspector that returns the authors of the article
	 * @return authors of article
	 */
	public String[] getAuthors() {
		return authors;
	}


	/**
	 * basic, immutable
	 * Inspector that returns the year in which the article was published
	 * @return year of article
	 */
	public int getYear() {
		return this.year;
	}

	/**
	 * basic, immutable
	 * this inspector returns the number of authors
	 * @return length of the authors array
	 */
	public int getNumberOfAuthors() {
		return this.authors.length;
	}
	
	/**
	 * basic, immutable
	 * this inspector returns the names of the authors as "F. Last"
	 * @return array authors names in new format "F. Last"
	 */
	public String[] convertAuthorNames() {
		String[] authorsCopy= getAuthors().clone();
		for (int i = 0; i<authorsCopy.length; i++) { //iterate through list of names (Last, First)
			String[] authorname= authorsCopy[i].split(", "); //split first and last names
			String firstname= authorname[1];
			String lastname= authorname[0];
			authorsCopy[i]= firstname.charAt(0) + ". " + lastname; //convert name to new format (F. Last)

		}
		return authorsCopy;
	}

	/** 
	 * Mutator
	 * @param title
	 * @post title contains same words but with the first letters capitalized
	 */
	public void setCapitalizedTitle() {
		String newtitle= "";
		String[] capitaltitle= getTitle().split(" ");
		for (int i=0; i<capitaltitle.length; i++) { 
			capitaltitle[i]= capitaltitle[i].substring(0,1).toUpperCase() + capitaltitle[i].substring(1);
			if (i<capitaltitle.length-1) {
				newtitle= newtitle + capitaltitle[i] + " ";
			} else {
				newtitle = newtitle + capitaltitle[i];
			}
		}
		setTitle(newtitle);

	}

	/**
	 * Inspector
	 * Return true if the year in which the article was published is more than 10 year ago 
	 * @return true/false if it is older/younger than 10 years
	 */
	public boolean isOlderThan() {
		int currentYear= Calendar.getInstance().get(Calendar.YEAR);
		return ((currentYear-getYear())>10);
	}
	
	public void printPublication() {
	}


	/**
	 * this method returns all attributes assigned to a newly created article
	 * @param args
	 */
	public static void main(String[] args) {
		//define cites & citedBy arrrays (empty for now) /*TODO*/
		Publication[] cites = new Publication[] {};
		Publication[] citedBy = new Publication[] {};
		
		//define authors /*TODO*/ //make proper authors
		String[] authorz= new String[] {"Fazelli, Patrick", "Horowitz, Mark", "MacDougald, Ornella"};
		String[] authorz2 = new String[] {"FAuthor1, LAuthor1", "FAuthor2, LAuthor2", "FAuthor3, LAuthor3", "Fauthor4, LAuthor4"};
		
		//define objects (2 articles, 1 book, 1 conferencepaper)
		Article article1 = new Article("Marrow fat and bone: new perspectives", authorz, 2013, cites, citedBy, "Journal of Clinical Endocrinology and Metabolism", 1);
		Article article2 = new Article("Artilce2title", authorz2, 2016, cites, citedBy, "Article2Journal", 12);
		Book book1 = new Book("booktitle", authorz, 2016, cites, citedBy, "publisher");
		ConferencePaper conferencepaper1 = new ConferencePaper("confpapTitle", authorz2, 2999, cites, citedBy, "conference C");
		
		//capitalize all titles
		article1.setCapitalizedTitle();
		article2.setCapitalizedTitle();
		book1.setCapitalizedTitle();
		conferencepaper1.setCapitalizedTitle();
		
		//print out all the characteristics of article 1
		System.out.println("The ID of the article is " + article1.getID());
		System.out.println("The title of the article is: " + article1.getTitle());
		System.out.println("The authors of the aricle are: " + Arrays.toString(article1.convertAuthorNames()));//this won't work gotta update getauthors() with a for loop to print everything individually
		System.out.println("But the old author format is conserved. The authors are:" + Arrays.toString(article1.getAuthors()));
		System.out.println("The number of authors is: " + article1.getNumberOfAuthors());
		System.out.println("The journal is: " + article1.getJournal());
		System.out.println("The journal issue is: " + article1.getIssue());
		System.out.println("The publication year is: " + article1.getYear());
		System.out.println("The publication is older than 10 years: "+ article1.isOlderThan());
		
		//create a database object database1
		Database database1 = new Database();
		
		//print the ID's of all the objects
		System.out.println("print ID's");
		System.out.println(article1.getID());
		System.out.println(article2.getID());
		System.out.println(book1.getID());
		System.out.println(conferencepaper1.getID());
		
		//add artilce 1 to database
		database1.addPublication(article1);
		
		//print database content
		System.out.println("article 1");
		database1.printDatabase();
		System.out.println();
		
		//add article 2 to database
		database1.addPublication(article2);
		
		//print database content
		System.out.println("article 1 & article 2");
		database1.printDatabase();
		System.out.println();

		//add book1 to database
		database1.addPublication(book1);
		
		//print database content
		System.out.println("article 1 & article 2 & book 1");
		database1.printDatabase();
		System.out.println();
		
		//add conferencepaper1 to database
		database1.addPublication(conferencepaper1);
		
		//print database content
		System.out.println("article 1, article 2, book1 & conferencepaper1");
		database1.printDatabase();
		System.out.println();
		
		//add reference in database1(article 1 cites book 1)
		database1.addReference(article1.getID(), book1.getID() );
		
		//print database content
		System.out.println("reference article1 cites book1");
		database1.printDatabase();
		System.out.println();
		
		//add reference in database (article 1 cites article 2)
		database1.addReference(article1.getID(), article2.getID());
		
		//print database content
		System.out.println("article1 cites article2");
		database1.printDatabase();
		System.out.println();
		
		//delete article2 from database
		database1.deleteArticle(article2);
		
		//print database content
		System.out.println("deleted article 2");
		database1.printDatabase();
		System.out.println();
		
		//delete article1 from database
		database1.deleteArticle(article1);
		
		//print database content
		System.out.println("deleted article 1");
		database1.printDatabase();
		System.out.println();
		
		//add reference in database (conferencepaper1 cites book1)
		database1.addReference(book1.getID(), conferencepaper1.getID());
		
		//print database content
		System.out.println("book1 cites conferencepaper1");
		database1.printDatabase();
		System.out.println();
		
		//add article 1 to database
		database1.addPublication(article1);
		
		//add article 2 to database
		database1.addPublication(article2);
		
		//article 2 cites article 1
		database1.addReference(article2.getID(), article1.getID());
		
		//print database content
		System.out.println("article 1 added");
		database1.printDatabase();
		System.out.println();
		
		//add reference in database (article 1 cites conferencepaper1)
		database1.addReference(article1.getID(), conferencepaper1.getID());

		//print database content
		System.out.println("article1 cites conferencepaper1");
		database1.printDatabase();
		System.out.println();
		
		//article that tries to cite itself, exception is thrown
		//database1.addReference(article1.getID(),  article1.getID());

		
		//retrieve citations of book1
		System.out.println("Get citations confpap");
		database1.printListOfPublications(database1.getCitations(conferencepaper1));
		System.out.println();
		
		//find biblipgraphy of Fazelli
		System.out.println("all publications of Fazelli, Patrick");
		String authorx = "Fazelli, Patrick";
		database1.findBibliography(authorx);
	}


}


