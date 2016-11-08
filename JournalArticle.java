import java.util.Arrays;


/**
 * @author David
 *	* This class represents an article in a scientfic journal using:
 *			- title
 *			- author(s)
 *			- name of the journal
 *			- issue number of the journal
 *			- year of publication
 */
public class Article {

	private String title;
	private String[] authors;
	private String journal;
	private int issue;
	private int year;
	

	/**
	 * New article will have attributes title, authors, journal, issue and year
	 */
	public Article(String title, String[] authors, String journal, int issue , int year){
		setTitle(title);
		setAuthors(authors);
		setJournal(journal);
		setIssue(issue);
		setYear(year);		
		}
	
	/**
	 * return the title that has been appointed to the article 
	 * @return title of article
	 */
	public String getTitle(){
		return this.title;
	}
	
	/**
	 * set the article title to a specified String t
	 * @param t
	 */
	public void setTitle(String t){
		this.title = t;
	}
	
	/**
	 * return the authors of the article
	 * @return authors of article
	 */
		public String[] getAuthors() {
		return this.authors;
	}
	
	/**
	 * set the article authors to a specified String a
	 * @param a
	 */
	public void setAuthors(String[] a){
		this.authors = a;
	}
	
	/**
	 * return the journal in which the article is published
	 * @return journal of article
	 */
	public String getJournal() {
		return this.journal;
		
	}
	/**
	 * set the journal in which thee article is published to a specified String j
	 * @param j
	 */
	public void setJournal (String j){
		this.journal = j;
	}

	/**
	 * return the issue of the journal in which the article is published
	 * @return issue of article
	 */
	public int getIssue() {
		return this.issue;
	}
	
	/**
	 * set the issue of the journal to a specified String i
	 * @param i
	 */
	public void setIssue (int i){
		this.issue = i;
	}
	
	/**
	 * return the year in which the article was published
	 * @return year of article
	 */
	public int getYear() {
		return this.year;
	}
	/**
	 * set the year in which the article was published to a specified value y
	 * @param y
	 */
	public void setYear (int y){
		this.year = y;
	}
	
	/**
	 * this inspector returns the number of authors
	 * @return length of the authors array
	 */
	public int getNumberOfAuthors() {
		return this.authors.length;
	}
	
	/**
	 * this mutator makes a new string called upperCase in which the title string is stored with upper case first letters of each word
	 * @param title
	 * @return String with upper case title
	 */
/*	public static String toUpperCase(String title) {
		String upperCase = "";
		String[] words = title.split (" ");
		for (int i = 0; i < words.length; i++) {
		upperCase += words[i].replaceFirst(words[i].charAt(0)+"", Character.toUpperCase(words[i].charAt(0))+"")+ " ";
			}
		return upperCase;
		}
*/
	
	public static String capitalizeTitle(String title) {
		String newtitle= "";
		String[] capitaltitle= title.split(" ");
		for (int i=0; i<capitaltitle.length; i++) { 
			capitaltitle[i]= capitaltitle[i].substring(0,1).toUpperCase() + capitaltitle[i].substring(1);
			newtitle= newtitle + capitaltitle[i] + " ";
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
	
	public String[] getAuthorNames() {
		for (int i=0; i<authors.length; i++) { //iterate through list of names (Last, First)
			String[] authorname= authors[i].split(", "); //split first and last names
			String firstname= authorname[1];
			String lastname= authorname[0];
			authors[i]= firstname.charAt(0) + ". " + lastname; //convert name to new format (F. Last)
		}
		return authors;
	}
	/*
	public static String names(String authors) {
		String initialName="";
		String[] partsOfName = authors.split(" ");
		initialName = partsOfName[1].charAt(1)+"";
		initialName += ". ";
		initialName += partsOfName[0];
		return initialName;
			}
	*/
	/**
	 * this method returns all attributes assigned to a newly created article
	 * @param args
	 */
	public static void main(String[] args) {
		
		Article article1 = new Article("Marrow fat and bone: new perspectives", new String[] {"Fazelli, Patrick", "Horowitz, Mark", "MacDougald, Ornella"}, "Journal of Clinical Endocrinology and Metabolism", 3, 2013);
		System.out.println("The title of the article is: " + article1.getTitle());
		System.out.println("The authors of the aricle are: " + Arrays.toString(article1.getAuthorNames()));
		//System.out.println("The correct names of the authors are: " + names(article1.authors));
		System.out.println("The number of authors is: " + article1.getNumberOfAuthors());
		//System.out.println("The upper case title is: " + toUpperCase(article1.title));
		System.out.println("The upper case title is: " + capitalizeTitle(article1.title));
		System.out.println("The journal is: " + article1.getJournal());
		System.out.println("The journal issue is: " + article1.getIssue());
		System.out.println("The publication year is: " + article1.getYear());
		System.out.println("The publication is older than 10 years: "+ isOlderThan(article1.year));
	}
	
}

