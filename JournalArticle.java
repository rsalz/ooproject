/**
 * 
 */
package assignment1;

/**
 * A class of information about journal articles.
 * @author Renee Salz
 *
 */
public class JournalArticle {
	/**
	 * 
	 * @param title
	 *	The title of the article
	 * @param authors
	 * 	The author(s), "Lastname, Firstname" format.
	 * 	there must be at least one author in the array
	 * @param nameofjournal
	 * 	The name of the journal it was published in
	 * @param issuenumber
	 * 	The journal issue number
	 * @param publicationyear
	 * 	The year it was published in
	 * @pre publication year must be less than or equal to current year
	 * 	publicationyear <= currentyear
	 */
	public JournalArticle(String title, String[] authors, String nameofjournal, int issuenumber, int publicationyear) {
		this.title= title; //this instead of separate setter
		this.nameofjournal= nameofjournal; //this instead of separate setter
		this.issuenumber= issuenumber; //this instead of separate setter, should issuenumber be double or int?
		setAuthorAt(authors);
	}
	
	//are there conditions on the title? need to be string >= 1 word or something like that?
	//public void setTitle(String title) {}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setAuthorAt(String[] authors) {
		assert (authors.length > 0);
		for (int i=0; i < authors.length; i++) {
			this.authors[i]= authors[i];
		}
	}
	public String getAuthorAt(int index) {
		assert (authors.length > 0);
		return authors[index-1];
	}
	
	//public void setJournalName(String nameofjournal) {}
	
	public String getJournalName() {
		return this.nameofjournal;
	}
	
	//public void setJournalIssue(int issuenumber) {}
	
	public int getJournalIssue() {
		return this.issuenumber;
	}
	
	public int getPublicationYear() {
		return publicationyear;
	}
	
	
	
	
	
	
	
	
	/**
	 * inspector that returns the number of authors.
	 * is immutable
	 */
	public int getNumberOfAuthors(){
		int numberofauthors= authors.length;
		return numberofauthors;
	}
	
	/**
	 * inspector that returns array of authors' names (first initial and last name)
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
	 * mutator that capitalizes all words in the title
	 */
	public static String capitalizeTitle(String title) {
		String newtitle= "";
		String[] capitaltitle= title.split(" ");
		for (int i=0; i<capitaltitle.length; i++) { 
			capitaltitle[i]= capitaltitle[i].substring(0,1).toUpperCase() + capitaltitle[i].substring(1);
			newtitle= newtitle + capitaltitle[i];
		}
		return newtitle;
	}
	
	/**
	 * inspector that tells if article is older than 10 years or not.
	 * set current year to 2016?
	 * @return true if and only if current year minus publication year is greater than 10
	 *
	 */
	public boolean isOlderThanTen(){
		int currentyear= 2016;
		if (currentyear-publicationyear > 10)
			return true;
		else
			return false;
	}
private String title;
private String nameofjournal;
private int issuenumber;
private String[] authors;
private int publicationyear;

public static void main(String[] args) {
	JournalArticle red= new JournalArticle("the sheep lays down to sleep", "Carbonez, David", "little kid journal", 47, 1997);
	String blue= "the cow jumps over the moon";
	String[] blues= blue.split(" ");
	blues[1]= blues[1].substring(0,1).toUpperCase() + blues[1].substring(1);
	System.out.println(blues[1]);
}

}
