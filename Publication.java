package assignment1;

import java.util.Arrays;
import java.util.Calendar;

public abstract class Publication {
	
	protected String ID;
	protected String title;
	protected String[] authors;
	protected int year;
	protected double indexWeight;
	protected Publication[] cites;
	protected Publication[] citedBy;
	
	public Publication(String title, String[] authors, int year, double indexWeight, Publication[] cites, Publication[] citedBy) throws IllegalArgumentException {
		if (!hasAuthor(authors))
			throw new IllegalArgumentException();
		else
		for (String auth : authors) {
			if (!isValidAuthor(auth))
				throw new IllegalArgumentException();
		}
		this.title=title;
		this.authors = authors;
		this.year = year;
		this.cites = cites;
		this.citedBy = citedBy;
		this.ID= this.authors[0].substring(0, 3).toUpperCase() + this.title.substring(0, 3).toUpperCase() + this.year;
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
		Publication[] cites = new Publication[] {};
		Publication[] citedBy = new Publication[] {};
		String[] authorz= new String[] {"Fazelli, Patrick", "Horowitz, Mark", "MacDougald, Ornella"};
		String[] authorz2 = new String[] {"FAuthor1, LAuthor1", "FAuthor2, LAuthor2", "FAuthor3, LAuthor3", "Fauthor4, LAuthor4"};
		Article article1 = new Article("Marrow fat and bone: new perspectives", authorz, 2013, cites, citedBy, "Journal of Clinical Endocrinology and Metabolism", 1);
		Article article2 = new Article("Artilce2title", authorz2, 2016, cites, citedBy, "Article2Journal", 12);
		Book book1 = new Book("booktitle", authorz, 2016, cites, citedBy, "publisher");
		article1.setCapitalizedTitle();
		System.out.println("The ID of the article is " + article1.getID());
		System.out.println("The title of the article is: " + article1.getTitle());

		System.out.println("The authors of the aricle are: " + Arrays.toString(article1.convertAuthorNames()));//this won't work gotta update getauthors() with a for loop to print everything individually
		System.out.println("But the old author format is conserved. The authors are:" + Arrays.toString(article1.getAuthors()));


		System.out.println("The number of authors is: " + article1.getNumberOfAuthors());
		System.out.println("The journal is: " + article1.getJournal());
		System.out.println("The journal issue is: " + article1.getIssue());
		System.out.println("The publication year is: " + article1.getYear());
		System.out.println("The publication is older than 10 years: "+ article1.isOlderThan());
		Database database1 = new Database();
		System.out.println("print ID's");
		System.out.println(article1.getID());
		System.out.println(article2.getID());
		System.out.println(book1.getID());
		
		database1.addPublication(article1);
		database1.addPublication(article2);
		database1.addPublication(book1);
		database1.addReference(book1.ID, article1.ID);
		
		System.out.println("printed database: ");
		database1.printDatabase();
		
				
	}


}


