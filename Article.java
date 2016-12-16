package assignment1;
import java.util.Arrays;
import java.util.Calendar;

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
 */
public class Article {

	/**
	 * Initialize this new article with given title, authors, journal, issue and year
	 * 
	 * @param title
	 * 		  The title of the article
	 * @param authors
	 * 		  The author(s), "Lastname, Firstname" format in an array
	 * @param journal
	 * 		  The name of the journal the article was published in
	 * @param issue
	 * 		  The issue number of the journal
	 * @param year 
	 * 		  The year of the publication
	 * @param cites
	 * 		  The articles cited by this article
	 * @param citedBy
	 * 		  The articles that cite this article
	 */
	String title;
	String[] authors;
	String journal;
	String ID;
	int issue;
	int year;
	Article[] cites;
	Article[] citedby;
	//overload constructor to allow for no citations? or maybe smart to initialize blank Article[]s
	public Article(String ID, String title, String[] authors, String journal, int issue, int year, Article[] cites, Article[] citedBy)
			throws IllegalArgumentException {
		//if (!isValidAuthor(authors)) //i think this segment should maybe be moved outside of the definition?
			//throw new IllegalArgumentException();
		/*TODO*/ //add authors to illegalArgumentException (between parenthesis) 
		setTitle(title);
		//this.getAuthors();
		setID(ID);
		this.authors= authors;

		assert(journal.length()>0);
		this.journal = journal;

		assert(issue>0);
		this.issue = issue;

		assert(year<2017);
		this.year = year;

		this.cites = cites;

		this.citedby = citedBy;
	}
	
	//should there also be a way to "generateID" with first 3 letters of author, title and year?
	
	private void setID(String ID) {
		this.ID= ID;
	}
	
	public String getID(){
		return ID;
	}

	public Article[] getCites() {
		return cites;
	}
	
	public void setCites(Article[] cites) {
		this.cites = cites;
	}
	
	public Article[] getCitedby() {
		return citedby;
	}

	public void setCitedby(Article[] citedby) {
		this.citedby = citedby;
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

	public static boolean isValidAuthor(Article article) {
		boolean checkAuthor= false;
		String[] authors= article.authors;
		for(int i=0; i<authors.length; i++){
			checkAuthor = (authors[i].contains(", ") && authors[i].length()>0);
		}
		return checkAuthor;
	}


	/**
	 * basic, immutable
	 * Inspector that returns the authors of the article
	 * @return authors of article
	 */
	public String[] getAuthors() {
		return this.authors;
	}

	public void isValidAuthor() {


	}

	/*assert(authors.length>0);
		for(String author: authors){
			assert(author.contains(", "));			
				};
		this.authors = authors;
	 */
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
		String[] authorsCopy= authors;
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
	 * this method returns all attributes assigned to a newly created article
	 * @param args
	 */
	public static void main(String[] args) {
		Article[] cites = null;
		Article[] citedBy = null;
		String[] authorz= new String[] {"Fazelli, Patrick", "Horowitz, Mark", "MacDougald, Ornella"};
		Article article1 = new Article("FAZMARR2013","Marrow fat and bone: new perspectives", authorz, "Journal of Clinical Endocrinology and Metabolism", 3, 2013, cites, citedBy);
		article1.setCapitalizedTitle();
		System.out.println(isValidAuthor(article1));
		System.out.println("The title of the article is: " + article1.getTitle());
		System.out.println("The authors of the aricle are: " + Arrays.toString(article1.convertAuthorNames()));//this won't work gotta update getauthors() with a for loop to print everything individually
		System.out.println("But the old author format is conserved. The authors are:" + article1.getAuthors());
		System.out.println("The number of authors is: " + article1.getNumberOfAuthors());
		System.out.println("The journal is: " + article1.getJournal());
		System.out.println("The journal issue is: " + article1.getIssue());
		System.out.println("The publication year is: " + article1.getYear());
		System.out.println("The publication is older than 10 years: "+ article1.isOlderThan());

	}



}

