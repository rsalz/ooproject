package assignment1;

public class Book extends Publication {

	private String publisher;
	
	public Book(String title, String[] authors, int year, Publication[] cites, Publication[] citedBy, String publisher) {
		super(title, authors, year, 1.2, cites, citedBy);
		this.publisher=publisher;
	}

	public String getPublisher() {
		return this.publisher;
	}
}
