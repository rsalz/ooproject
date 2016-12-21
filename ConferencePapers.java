package assignment1;

public class ConferencePapers extends Publication{

	private String conference;
	
	public ConferencePapers(String title, String[] authors, int year, Publication[] cites, Publication[] citedBy, String conference) {
		super(title, authors, year, 0.7, cites, citedBy);
		this.conference=conference;
	}

	public String getConference() {
		return this.conference;
	}

}
