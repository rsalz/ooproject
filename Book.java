package assignment1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Book extends Publication {

	private String publisher;

	public Book(String title, String[] authors, int year, Publication[] cites, Publication[] citedBy, String publisher) {
		super(title, authors, year, 1.2, cites, citedBy);
		this.publisher=publisher;
	}

	public String getPublisher() {
		return this.publisher;
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
		System.out.println("Title: " + this.getTitle() + ", Authors: " + Arrays.toString(this.getAuthors()) + ", Publisher: " + this.getPublisher() + ", Year: " + this.getYear() + ", Cites: " + Arrays.toString(citesIDs.toArray()) + ", Cited By: " + Arrays.toString(citedByIDs.toArray()));

	}
}