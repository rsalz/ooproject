import java.util.Arrays;

/**
 * @author Renee Salz, David Carbonez
 * @version 1
 *	* This class represents an article in a scientfic journal using:
 *			- title
 *			- author(s)
 *			- name of the journal it was published in
 *			- issue number of the journal
 *			- year of publication
 */
public class Article {

	/**
	 * * @param title
	 *	The title of the article
	 *	Precondition:
	 *		* title should contain at least one character
	 * @param authors
	 * 	The author(s), "Lastname, Firstname" format in an array
	 * 	Preconditions:
	 * 		*There should be at least one author
	 * 		*Every author String in the array should contain a comma
	 * @param journal
	 * 	The name of the journal it was published in
	 * Precondition:
	 * 		* the name of the journal should contain at least one character
	 * @param issue
	 * 	The journal issue number
	 * Precondition:
	 * 		* issue number should be larger than 0 
	 * @param year
	 * 	The year it was published in
	 * Precondition:
	 * 		*publication year must be less than or equal to current year
	 * 	
	 */
	public Article(String title, String[] authors, String journal, int issue , int year){
		assert(title.length()>0);
		this.title = title;
		
		assert(authors.length>0);
		for(int i=0; i<authors.length; i++){
			assert(authors[i].contains(", "));			
				};
		this.authors = authors;
		
		assert(journal.length()>0);
		this.journal = journal;
		
		assert(issue>0);
		this.issue = issue;
		
		assert(year<2017);
		this.year = year;
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
	 * basic, immutable
	 * Inpector that returns the authors of the article
	 * @return authors of article
	 */
		public String[] getAuthors() {
		return this.authors;
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
	public String[] getAuthorNames() {
		for (int i=0; i<authors.length; i++) { //iterate through list of names (Last, First)
			String[] authorname= authors[i].split(", "); //split first and last names
			String firstname= authorname[1];
			String lastname= authorname[0];
			authors[i]= firstname.charAt(0) + ". " + lastname; //convert name to new format (F. Last)
		}
		return authors;
	}
	
	/**
	 * immutable
	 * this inspector returns the new string called upperCase in which the title string is stored with upper case first letters of each word
	 * @param title
	 * @return String with upper case title, words are followed by spaces, except for the last word
	 */
	public String getCapitalizedTitle() {
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
		return newtitle;
	
	}
	/**
	 * Return the age an article must be to be older than 10 years
	 * @return integer with corresponding value
	 */
	public static int getYearLimit() {
		return 10;
	}
	
	/**
	 * Return true if the year in which the article was published is more than 10 year ago 
	 * @return true/false if it is older/younger than 10 years
	 */
	public static boolean isOlderThan(int year) {
		return ((2016-year)>getYearLimit());
	}
	

	/**
	 * this method returns all attributes assigned to a newly created article
	 * @param args
	 */
	public static void main(String[] args) {
		
		Article article1 = new Article("Marrow fat and bone: new perspectives", new String[] {"Fazelli, Patrick", "Horowitz, Mark", "MacDougald, Ornella"}, "Journal of Clinical Endocrinology and Metabolism", 3, 2013);
		System.out.println("The title of the article is: " + article1.getTitle());
		System.out.println("The authors of the aricle are: " + Arrays.toString(article1.getAuthorNames()));
		System.out.println("The number of authors is: " + article1.getNumberOfAuthors());
		System.out.println("The upper case title is: " + article1.getCapitalizedTitle());
		System.out.println("The journal is: " + article1.getJournal());
		System.out.println("The journal issue is: " + article1.getIssue());
		System.out.println("The publication year is: " + article1.getYear());
		System.out.println("The publication is older than 10 years: "+ isOlderThan(article1.year));
	}
	

	private String title;
	private String[] authors;
	private String journal;
	private int issue;
	private int year;
}

