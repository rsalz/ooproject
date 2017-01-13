package assignment1;

import java.util.Arrays;
import java.util.Calendar;

/**
 * @author Renee Salz, David Carbonez
 *	* This class represents a scientific publication using:
 *			- ID
 *			- title
 *			- author(s)
 *			- year of publication
 *			- indexweight of the publication
 *			- the articles it cites
 *			- the articles it is cited by
 *
 *@invar	- A publication must have at least one author
 *			- Every author must have a first and a last name, separated by a comma
 *			- A title must contain at least one character
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
	 * @throws IllegalArgumentException
	 * 			- There is no valid title given
	 * 			- there are no authors given
	 * 			- the author format has been violated
	 */

	protected String ID;
	protected String title;
	protected String[] authors;
	protected int year;
	protected double indexWeight;
	protected Publication[] cites;
	protected Publication[] citedBy;
	
	public Publication(String title, String[] authors, int year, double indexWeight, Publication[] cites, Publication[] citedBy) throws IllegalArgumentException {
		if (!isValidTitle(title))
			throw new IllegalArgumentException("No valid title found");
		this.title=title;
		if (!hasAuthor(authors))
			throw new IllegalArgumentException("No authors found");
		else
		for (String auth : authors) {
			if (!isValidAuthor(auth))
				throw new IllegalArgumentException("Invalid author format");
		}
		this.indexWeight = getIndexWeight();
		this.authors = authors;
		this.year = year;
		this.cites = cites;
		this.citedBy = citedBy;
		this.ID= this.authors[0].substring(0, 3).toUpperCase() +  this.title.substring(0, 3).toUpperCase() + this.year;

		
	}
	
	/**
	 * basic, immutable
	 * Inspector that returns the ID the publication is set to
	 * @return the ID
	 */
	public String getID(){
		return this.ID;
	}

	
	/**
	 * basic, immutable
	 * Inspector that returns the articles cited by the publication
	 * @return citations of publication
	 */
	public Publication[] getCites() {
		return cites;
	}
	
	/**
	 * set the citations of the publication to specified value
	 * @param cites
	 */
	public void setCites(Publication[] cites) {
		this.cites = cites;
	}
	
	/**
	 * basic
	 * Returns the publications that cite this publication
	 * @return publications that cite the publication 
	 */
	public Publication[] getCitedBy() {
		return citedBy;
	}

	/**
	 * set the publications that cite the publication to specified value
	 * @param citedBy
	 */
	public void setCitedby(Publication[] citedBy) {
		this.citedBy = citedBy;
	}

	/**
	 * basic
	 * Inspector that returns the title that has been appointed to the article 
	 * @return title of article
	 */
	public String getTitle(){
		return this.title;
	}

	/**
	 * set the title of the article to a specified value
	 * @param title
	 */
	public void setTitle(String title) {
		this.title= title;
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
	 * returns the indexweight
	 * @return indexweight
	 */
	public double getIndexWeight() {
		return indexWeight;
	}
	
	
	/**
	 * Return a boolean reflecting whether the given title is a valid title
	 * @param title 
	 * 		  The title to check
	 * @return True if and only if the title contains 1 or more characters
	 */
	public static boolean isValidTitle(String title) {
		boolean checkTitle = false;
		checkTitle = title.length()>0;
		return checkTitle;
	}

	/**
	 * Return a boolean reflecting whether an author is correctly formatted (at least one character and containing a comma)
	 * @param author
	 *        The author to be checked
	 * @return True if and only if the author contains 1 or more characters and contains a comma
	 */
	public static boolean isValidAuthor(String author) {
		boolean checkAuthor= false;
		checkAuthor = (author.contains(", ") && author.length()>0);
		return checkAuthor;
		}
	
	/**
	 * Return a boolean reflecting whether the array of authors is valid (has 1 or more inputs)
	 * @param author
	 * 	      The array of authors to be checked
	 * @return True if and only if the array of authors contains 1 or more arguments
	 */
	public static boolean hasAuthor(String[] author) {
		boolean hasAuthor = false;
		hasAuthor = author.length>0;
		return hasAuthor;
	}

	/**
	 * immutable
	 * this inspector returns the number of authors
	 * @return length of the authors array
	 */
	public int getNumberOfAuthors() {
		return this.authors.length;
	}
	
	/**
	 * immutable
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
	
	/**
	 * Method that will allow the publication to be printed as a sequence of their attributes (Overridden in every subclass)
	 */
	public void printPublication() {
	}


	/**
	 * this method demonstrates the functionality of the various functions defined in this program
	 * @param args
	 */
	public static void main(String[] args) {
		//define cites & citedBy arrrays (empty for now)
		Publication[] cites = new Publication[] {};
		Publication[] citedBy = new Publication[] {};
		
		//define authors 
		String[] authors= new String[] {"Fazelli, Patrick", "Horowitz, Mark", "MacDougald, Ornella"};
		String[] authors2 = new String[] {"Suchacki, Carla", "Rosen, Clifford", "Cawthorn, Will" };
		String[] authors3 = new String[] {"DiGirolamo, Douglas", "Clemens, Thomas","Fazelli, Patrick" };
		String[] authors4 = new String[] {"McPherson, Ruth", "Hegele, Robert", "Fazelli, Patrick"};
		
		//define objects (2 articles, 1 book, 1 conferencepaper)
		Article article1 = new Article("Marrow fat and bone: new perspectives", authors, 2013, cites, citedBy, "Journal of Clinical Endocrinology and Metabolism", 1);
		Article article2 = new Article("Bone marrow adipose tissue: formation, function and regulation", authors2, 2016, cites, citedBy, "Current Opinion in Pharmacology", 6);
		Book book1 = new Book("The skeleton", authors3, 2000, cites, citedBy, "Macmillan Publishers");
		ConferencePaper conferencepaper1 = new ConferencePaper("Commentary on Cutting Edge Science", authors4, 2012, cites, citedBy, "Conference on Bone");
		
		//capitalize all titles
		article1.setCapitalizedTitle();
		article2.setCapitalizedTitle();
		book1.setCapitalizedTitle();
		conferencepaper1.setCapitalizedTitle();
		
		//print out all the characteristics of article 1
		System.out.println("The ID of the article is " + article1.getID());
		System.out.println("The title of the article is: " + article1.getTitle());
		System.out.println("The authors of the aricle are: " + Arrays.toString(article1.convertAuthorNames()));
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
		
		//add article 2 to database
		database1.addPublication(article2);
		
		//add book1 to database
		database1.addPublication(book1);
		
		//add conferencepaper1 to database
		database1.addPublication(conferencepaper1);
		
		//print database content
		System.out.println("Database content: ");
		database1.printDatabase();
		System.out.println();
		
		//add reference in database1(article 1 cites book 1)
		database1.addReference(article1.getID(), book1.getID() );
		
		//add reference in database (article 1 cites article 2)
		database1.addReference(article1.getID(), article2.getID());
		
		//print database content
		System.out.println("Database content: ");
		database1.printDatabase();
		System.out.println();
			
		//delete article2 from database
		database1.deletePublication(article2);
		
		//print database content
		System.out.println("Database content: ");
		database1.printDatabase();
		System.out.println();
		
		//delete article1 from database
		database1.deletePublication(article1);
		
		//print database content
		System.out.println("Database content");
		database1.printDatabase();
		System.out.println();
		
		//add article 1 to database
		database1.addPublication(article1);
		
		//add article 2 to database
		database1.addPublication(article2);
		
		//print database content
		System.out.println("Database content");
		database1.printDatabase();
		System.out.println();
		
		//article 2 cites article 1
		database1.addReference(conferencepaper1.getID(), article1.getID());
		
		//add reference in database (article 1 cites conferencepaper1)
		database1.addReference(article2.getID(), conferencepaper1.getID());
		
		//add reference in database (book 1 cites article 1)
		database1.addReference(book1.getID(),  article1.getID());
				
		//add reference indatabase (book 1 cites article 2)
		database1.addReference(book1.getID(), article2.getID());

		//print database content
		System.out.println("Database content: ");
		database1.printDatabase();
		System.out.println();
		
		//retrieve citations of conferencepaper1
		System.out.println("Get citations of publication with ID: " + conferencepaper1.getID());
		database1.printListOfPublications(database1.getCitations(conferencepaper1));
		System.out.println();
		
		//find bibliography of Fazelli
		System.out.println("all publications of Fazelli, Patrick");
		String authorx = "P. Fazelli";
		database1.printListOfPublications(database1.findBibliography(authorx));
		System.out.println();
		
		//find keyword marrow
		System.out.println("all publications with keyword 'marrow'");
		String keyword = "marrow";
		database1.printListOfPublications(database1.findKeyword(keyword));
		System.out.println();
		
		//compute index of Fazelli
		System.out.println("Compute index of Fazelli");
		String author = "P. Fazelli";
		System.out.println(database1.computeIndex(author));
		System.out.println();
		
		
	}


}


