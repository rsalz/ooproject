import java.util.Arrays;

/**
 * 
 */


/**
 * A class of information about journal articles.
 * @author Renee Salz, David Carbonez
 *
 */
public class JournalArticle {
	/**
	 * 
	 * @param title
	 *	The title of the article
	 * @param authors
	 * 	The author(s), "Lastname, Firstname" format.
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
		this.nameOfJournal= nameofjournal; //this instead of separate setter
		this.issueNumber= issuenumber; //this instead of separate setter, should issuenumber be double or int?
		this.publicationYear = publicationyear;
	}
	
	//are there conditions on the title? need to be string >= 1 word or something like that?
	//public void setTitle(String title) {}
	
	/**
	 * Basic inspector, immutable
	 * @return title of the article
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Basic inspector, immutable
	 * @return journal in which article is published
	 */
	public String getNameOfJournal() {  //#dc: added a separate setter for name of journal
		return this.nameOfJournal;
	}
	
	/**
	 * Preconditions:
	 * 	*String is not empty
	 * @param nameOfJournal
	 */
	public void setNameOfJournal(String nameOfJournal) {
		this.nameOfJournal = nameOfJournal;
	}
	
	/**
	 * Basic inspector
	 * @param index
	 * @return authors of the article
	 */
	public String getAuthorAt(int index) { //#dc: shouldn't this return all authors at once (for loop, + append to list (maybe ArrayList))
		assert (authors.length > 0);
		return authors[index-1];
	}
	
	/**
	 * Preconditions
	 * length of String authors is not 0
	 * @param authors
	 */
	public void setAuthorAt(String[] authors) {
		assert (authors.length > 0);
		for (int i=0; i < authors.length; i++) {
			this.authors[i]= authors[i];
		}
	}

	/**
	 * Basic inspector, immutable
	 * @return issue number of the journal
	 */
	public int getJournalIssue() {
		return this.issueNumber;
	}

	
	/**
	 * Basic inspector
	 * @return year of publication
	 */
	public int getPublicationYear() {
		return this.publicationYear;
	}
	
	/**
	 * preconditions:
	 * 	* publication year is larger than or equal to 0
	 * 	* publication year is smaller than or equal to 'current year'
	 * @param publicationYear
	 */
	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
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
	public void capitalizeTitle(String title) {
		String[] capitaltitle= title.split(" ");
		for (int i=0; i<capitaltitle.length; i++) { 
			capitaltitle[i]= capitaltitle[i].substring(0,1).toUpperCase() + capitaltitle[i].substring(1);
		}
	}
	
	/**
	 * inspector that tells if article is older than 10 years or not.
	 * set current year to 2016?
	 * @return true if and only if current year minus publication year is greater than 10
	 *
	 */
	public boolean isOlderThanTen(){
		int currentyear= 2016;
		if (currentyear-publicationYear > 10)
			return true;
		else
			return false;
				
	}
	
	
private String title;
private String nameOfJournal;
private int issueNumber;
private String[] authors;
private int publicationYear;

public static void main(String[] args) {
	
	JournalArticle article1 = new JournalArticle("Marrow fat and bone: new perspectives", new String[] {"Fazelli, Patrick", "Horowitz, Mark", "MacDougald, Ornella"}, "Journal of Clinical Endocrinology and Metabolism", 3, 2013);
	System.out.println("The authors of the aricle are: " + Arrays.toString(article1.getAuthorNames()));
}
}


	
