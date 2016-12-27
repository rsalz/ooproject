package assignment1;

import assignment1.Publication;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;

public class Database {
	List<Publication> database= new ArrayList<>();
	
	//public Database(List<Publication> database) {
	//	this.setDatabase(database);
	//}

	//private void setDatabase(List<Publication> database) {
	//	this.database= database;
	//}

	public void addPublication(Publication publication) {
		database.add(publication);
	}

	public Publication[] findBibliography(String author) {
		//create new Arraylist which is meant to contain objects of the type Publication
		List<Publication> authorList= new ArrayList<>();
		//Loop through the ArrayList database using the listiterator for as long as the iterator iter has a next article object
		for (Iterator<Publication> iter= database.listIterator(); iter.hasNext();) {
			//store the current article in iterarticle
			Publication iterpub= iter.next();
			//convert the author names in the current article to F. Lastname & store in an array of strings called authors
			String[] authors=iterpub.convertAuthorNames();
			//If the authors array contains the requested author, then add the iterarticle to the authorList
			if (Arrays.asList(authors).contains(author)) {
				authorList.add(iterpub);
			}
		}
		//Create an array of articles called containsKeywords of the size equal to the length of the authorlist
		Publication[] containsKeywords= new Publication[authorList.size()];
		//convert the authorlist to the containsKeywords array
		containsKeywords=authorList.toArray(containsKeywords);
		//return the array with the articles 
		return containsKeywords;

	}

	public double computeIndex(String author) {
		double index=0;
		//first find all his publications
		Publication[] authorPublications= findBibliography(author);
		//then retreive Publication[] citedBy for all of these
		for (Publication publication : authorPublications) {
			Publication[] cited= publication.getCitedBy();
			//for each publication, add to the score
			for (Publication cites : cited) {
				double citedIndex= cites.getIndexWeight();
				index=index+citedIndex;
			}
		}
		return index;
	}

	public Publication[] findKeyword(String word) {
		List<Publication> keywordList= new ArrayList<>();
		for (Iterator<Publication> iter= database.listIterator(); iter.hasNext();) {
			Publication iterpub= iter.next();
			//get the title of the current article
			String titleOfInterest= iterpub.getTitle();
			//convert the title of the current article to lower case & check if it contains the keyword in lower case
			//If it does, add the current article to the keywordList
			if (titleOfInterest.toLowerCase().contains(word.toLowerCase())) {
				keywordList.add(iterpub);
			}
		}
		Publication[] containsKeywords= new Publication[keywordList.size()];
		containsKeywords=keywordList.toArray(containsKeywords);
		return containsKeywords;
	}

	public void addReference(String citingID, String citedID) {
		//create two articles. One that cites, one that is cited by
		Publication citingPublication = null;
		Publication citedPublication = null;
		//retrieve the articles with these IDs by looping through the database. 
		for (Iterator<Publication> iter= database.listIterator(); iter.hasNext();) {
			//store the current article as cite
			Publication cite=iter.next();
			//if the ID of the current article matches the ID of the article that cites, then the current article is equal to citesA. (should only retrieve one result)
			if (cite.getID().equals(citingID)) {
				citingPublication= cite;
			}
			//if the ID of the current article matches the ID of the article that gets cited, then ... (should only retrieve one result)
			if (cite.getID().equals(citedID)) {
				citedPublication= cite;
			}
		}
		//throw an error if either of the given IDs are not within the database
		if (citingPublication==null || citedPublication==null) {
			throw new IllegalArgumentException();
		}
		//update the cites array of the article that cites another
		//first store the articles it already cites to a list that contains articles(! this needs to be arraylist, add is not supported in list)
		List<Publication> citationsOfCitingPublication = new ArrayList<Publication>(Arrays.asList(citingPublication.getCites()));
		//add the article that gets cited to that list
		citationsOfCitingPublication.add(citedPublication);
		//create a new array with the length of that list
		Publication[] ListCitationsOfCitingPublication= new Publication[citationsOfCitingPublication.size()];
		//store the list into that array
		ListCitationsOfCitingPublication=citationsOfCitingPublication.toArray(ListCitationsOfCitingPublication);
		//update the cited array of the article that gets cited
		//first store the articles that cited the article already into a list that contains articles
		List<Publication> CitedByOfCitedPublication = new ArrayList<Publication>(Arrays.asList(citedPublication.getCites()));
		//then add the article that cites the article to that list of articles
		CitedByOfCitedPublication.add(citingPublication);
		//create a new array of articles with the length of that list
		Publication[] ListCitedByOfCitedPublicatoin= new Publication[CitedByOfCitedPublication.size()];
		//then store the list into that array of articles
		ListCitedByOfCitedPublicatoin=CitedByOfCitedPublication.toArray(ListCitedByOfCitedPublicatoin);

		//put them back in the database
		//iterate through the database of articles
		for (Iterator<Publication> iter2= database.listIterator(); iter2.hasNext();) {
			//store the current article as cite2
			Publication cite2=iter2.next();
			//if the ID of the current article is equal to the ID of the article that is citing, then update its cites to the new citesAnew array
			if (cite2.getID().equals(citingID)) {
				cite2.setCites(ListCitationsOfCitingPublication);
			}
			//if the ID of the current article is equal to the ID of the article that gets cited, then update its citedBy to the new citedAnew array
			if (cite2.getID().equals(citedID)) {
				cite2.setCitedby(ListCitedByOfCitedPublicatoin);
			}
		}
	}


	public void deleteArticle(Publication article) {
		//iterate through the database of articles
		List<Publication> updatedDB= new ArrayList<Publication>();
		for (ListIterator<Publication> iter= database.listIterator(); iter.hasNext();) {
			//store the current article as iterarticle
			Publication iterarticle= iter.next();
			List<Publication> citesDelete= new ArrayList<>(Arrays.asList(iterarticle.getCites()));
			List<Publication> citedByDelete= new ArrayList<>(Arrays.asList(iterarticle.getCitedBy()));
			if (iterarticle != article) {
				//iterate through cites and citedBy to remove the article
				for (ListIterator<Publication> iterCites= citesDelete.listIterator(); iterCites.hasNext();) {
					Publication citesCheck= iterCites.next();
					if (citesCheck == article) {
						iterCites.remove();
					}
				}
				for (ListIterator<Publication> iterCited= citedByDelete.listIterator(); iterCited.hasNext();) {
					Publication citedCheck= iterCited.next();
					if (citedCheck == article) {
						iterCited.remove();
					}
				}
				//update changes, put back into iterarticle
				Publication[] citesDeleted= new Publication[citesDelete.size()];
				citesDeleted=citesDelete.toArray(citesDeleted);
				Publication[] citedByDeleted= new Publication[citedByDelete.size()];
				citedByDeleted=citedByDelete.toArray(citedByDeleted);
				iterarticle.setCites(citesDeleted);
				iterarticle.setCitedby(citedByDeleted);
				updatedDB.add(iterarticle);
			}	
		}
		//update changes in database
		database=updatedDB;
	}
	
	public Publication[] getCitations(Publication publication) {
		HashSet<Publication> citations= new HashSet<Publication>();
		//directly and indirectly cited:
		Publication[] directlyCited= publication.getCitedBy();
		for (Publication pubby: directlyCited) {
			citations.add(pubby);
			Publication[] indirectlyCited= pubby.getCitedBy();
			for (Publication pubby2: indirectlyCited) {
				citations.add(pubby2);
				}
			}
		//more distant:
		//create set in order so things added will go to the end and the iterator will eventually reach them, thus going infinitely until there is nothing left to add
		LinkedHashSet<Publication> orderedCitations=new LinkedHashSet<Publication>(citations);
		Iterator<Publication> iter= orderedCitations.iterator();
		while (iter.hasNext()) {
			Publication nearpub= iter.next();
			Publication[] nearpubCite= nearpub.getCitedBy();
			for (Publication farCite: nearpubCite) {
				orderedCitations.add(farCite);
			}
		}
		//convert to array
		Publication[] allCited= orderedCitations.toArray(new Publication[orderedCitations.size()]);
		return allCited;
		
	}

	public void printDatabase() {

		for(Publication a: database) {
			a.printPublication();
		}		
	}
}

