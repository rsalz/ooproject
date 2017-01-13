package assignment1;

import assignment1.Publication;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;

/**
 * 
 * @author Renee Salz and David Carbonez
 * 
 * This class contains the database, which is a list of publications.
 * 
 * @invar	- an ID of a publication in the database must be unique
 * 
 *
 */

public class Database {
	static List<Publication> database= new ArrayList<>();

	/**
	 * Mutator that adds input publication to database
	 * @param publication
	 * 
	 */
	public void addPublication(Publication publication) {
		if (existsInDatabase(publication))
			throw new IllegalArgumentException("The given publication already exists in the database");
		database.add(publication);
	}

	/**
	 * Inspector
	 * @param author (must be in the format Last, First)
	 * @return all publications by author
	 */
	public Publication[] findBibliography(String author) {
		if (isValidAuthorSearch(author)==false) {
			throw new IllegalArgumentException("The author given is not a valid author");
		}
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
		Publication[] bibliography= new Publication[authorList.size()];
		//convert the authorlist to the containsKeywords array
		bibliography=authorList.toArray(bibliography);
		//return the array with the articles 
		return bibliography;

	}
	
	/**
	 * Inspector
	 * @param author
	 * @return weights of all publications in database by author
	 * @post if author is valid and has publications that have been cited, index > 0
	 */
	public double computeIndex(String author) {
		if (isValidAuthorSearch(author)==false) {
			throw new IllegalArgumentException("The author given is not a valid author");
		}
		double index=0;
		//first find all his publications
		Publication[] authorPublications= findBibliography(author);
		//then retrieve Publication[] citedBy for all of these
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

	/**
	 * Return a boolean reflecting whether an author is correctly formatted (at least one character and containing a comma)
	 * @param author
	 *        The author to be checked
	 * @return True if and only if the author contains 1 or more characters and contains a comma
	 */
	public static boolean isValidAuthorSearch(String author) {
		boolean checkAuthor= false;
		checkAuthor = (author.contains(". ") && author.length()>0);
		return checkAuthor;
	}

	/**
	 * inspector
	 * @param word
	 * 		  The word that will be sought in the title
	 * @return all publications with a title containing that word
	 */
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

	/**
	 * Mutator that adds references to publications in the database
	 * @param citingID of paper that is to cite
	 * @param citedID of paper that is to be cited
	 * @post cannot have the same publication in citing or citedBy more than once
	 */
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
		if (citingPublication==null || citedPublication==null || citingPublication == citedPublication) {
			throw new IllegalArgumentException("Invalid article selection for reference");
		}

		//first store the articles it already cites to a list that contains articles(! this needs to be arraylist, add is not supported in list)
		List<Publication> citationsOfCitingPublication = new ArrayList<Publication>(Arrays.asList(citingPublication.getCites()));
		//invar: make sure cited isn't already in citing (can't cite same thing twice)
		for (Iterator<Publication> itercite= citationsOfCitingPublication.listIterator(); itercite.hasNext();) {
			Publication cite=itercite.next();
			if (cite==citedPublication) {
				throw new IllegalArgumentException();
			}
		}
		//add the article that gets cited to that list
		citationsOfCitingPublication.add(citedPublication);
		//create a new array with the length of that list
		Publication[] ListCitationsOfCitingPublication= new Publication[citationsOfCitingPublication.size()];
		//store the list into that array
		ListCitationsOfCitingPublication=citationsOfCitingPublication.toArray(ListCitationsOfCitingPublication);

		//first store the articles that cited the article already into a list that contains articles
		List<Publication> CitedByOfCitedPublication = new ArrayList<Publication>(Arrays.asList(citedPublication.getCitedBy()));
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

	/**
	 * Destructor that explicitly deletes an article from database and cites list of articles that cite it
	 * @param article to be destroyed
	 * @pre article must be in database
	 * @post article no longer in database or any other article citations
	 */
	public void deletePublication(Publication publication) {
		if (!existsInDatabase(publication))
			throw new IllegalArgumentException("The given publication already exists in the database");
		//iterate through the database of articles
		List<Publication> updatedDB= new ArrayList<Publication>();
		for (ListIterator<Publication> iter= database.listIterator(); iter.hasNext();) {
			//store the current article as iterarticle
			Publication iterarticle= iter.next();
			//create 2 arraylists with the current cites & citedBy
			List<Publication> citesDelete= new ArrayList<>(Arrays.asList(iterarticle.getCites()));
			List<Publication> citedByDelete= new ArrayList<>(Arrays.asList(iterarticle.getCitedBy()));
			if (iterarticle != publication) {
				//iterate through cites of current article
				for (ListIterator<Publication> iterCites= citesDelete.listIterator(); iterCites.hasNext();) {
					Publication citesCheck= iterCites.next();
					//if the cites is equal to the article you want to delete, remove it from the cites
					if (citesCheck == publication) {
						iterCites.remove();
					}
				}
				//iterate through citedBy of current article
				for (ListIterator<Publication> iterCited= citedByDelete.listIterator(); iterCited.hasNext();) {
					Publication citedCheck= iterCited.next();
					//if the citedBy is equal to the article you want to delete, remove it from the cites
					if (citedCheck == publication) {
						iterCited.remove();
					}
				}
				//make an array of publications with the size of the arraylist in which you deleted the cites
				Publication[] citesDeleted= new Publication[citesDelete.size()];
				//store the content of the arraylist in the array
				citesDeleted=citesDelete.toArray(citesDeleted);

				//make an array of publications with the size of the arraylist in which you deleted the citedBy
				Publication[] citedByDeleted= new Publication[citedByDelete.size()];
				//store the content of the arraylist in the array
				citedByDeleted=citedByDelete.toArray(citedByDeleted);

				//assign the new arrays to the current article
				iterarticle.setCites(citesDeleted);
				iterarticle.setCitedby(citedByDeleted);

				//add the new article to the database
				updatedDB.add(iterarticle);
			}
			//for the article you're deleting, remove all its cites & citedBy's
			else if (iterarticle == publication) {
				citesDelete.clear();
				citedByDelete.clear();

				//make an array of publications with the size of the arraylist in which you deleted the cites
				Publication[] citesDeleted = new Publication[citesDelete.size()];
				//store the content of the arraylist (nothing) in the array
				citesDeleted = citesDelete.toArray(citesDeleted);

				//make an array of publications with the size of the arraylist in which you deleted the citedBy
				Publication[] citedByDeleted= new Publication[citedByDelete.size()];
				//store the content of the arraylist in the array
				citedByDeleted=citedByDelete.toArray(citedByDeleted);

				//assign the new arrays to the current article
				iterarticle.setCites(citesDeleted);
				iterarticle.setCitedby(citedByDeleted);
			}

		}
		//update changes in database
		database=updatedDB;
	}
	/**
	 * Return a boolean reflecting whether the given publication exists in the database
	 * @param publication
	 * 		  The publication to be checked
	 * @return True if and only if the publication is already present in the database
	 */
	public static boolean existsInDatabase(Publication publication){
		boolean isInDatabase = false;
		List<String> IDsInDatabase = new ArrayList<>();
		for(Publication p: database) {
			String ID = p.getID();
			IDsInDatabase.add(ID);
		}
		if (IDsInDatabase.contains(publication.getID())) {
			isInDatabase = true;
		}
		return isInDatabase;
	}


	/**
	 * Inspector
	 * basic
	 * @param publication in database
	 * @return non-empty list of publications directly or indirectly cited by given publication
	 * 
	 */
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

	/**
	 * Method that allows a list of publications to be printed with all their corresponding attributes 
	 * @param publications
	 */
	public void printListOfPublications(Publication[] publications) {
		for(Publication p: publications){
			p.printPublication();
		}

	}

	/**
	 * Method that prints all the publications in the database
	 */
	public void printDatabase() {
		for(Publication a: database) {
			a.printPublication();
		}		
	}
}

