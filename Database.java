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
		try {
		if (existsInDatabase(publication))
			throw new IllegalArgumentException();
		database.add(publication);
		} catch (IllegalArgumentException iae) {
			System.err.println("The given publication already exists in the database");
		}
	}

	/**
	 * Inspector
	 * @param author (must be in the format Last, First)
	 * @return all publications by author
	 */
	public Publication[] findBibliography(String author) {
		try {
		if (isValidAuthorSearch(author)==false) {
			throw new IllegalArgumentException();
		}
		List<Publication> authorList= new ArrayList<>();
		for (Iterator<Publication> iter= database.listIterator(); iter.hasNext();) {
			Publication iterpub= iter.next();
			String[] authors=iterpub.convertAuthorNames();
			if (Arrays.asList(authors).contains(author)) {
				authorList.add(iterpub);
			}
		}
		Publication[] bibliography= new Publication[authorList.size()];
		bibliography=authorList.toArray(bibliography);
		return bibliography;
		} catch (IllegalArgumentException iae) {
			System.err.println("Invalid author");
			return null;
		}

	}
	
	/**
	 * Inspector
	 * @param author
	 * @return weights of all publications in database by author
	 * @post if author is valid and has publications that have been cited, index > 0
	 */
	public double computeIndex(String author) {
		try {
		if (isValidAuthorSearch(author)==false) {
			throw new IllegalArgumentException();
		}
		double index=0;
		Publication[] authorPublications= findBibliography(author);
		for (Publication publication : authorPublications) {
			Publication[] cited= publication.getCitedBy();
			for (Publication cites : cited) {
				double citedIndex= cites.getIndexWeight();
				index=index+citedIndex;
			}
		}
		return index; 
		} catch (IllegalArgumentException iae) {
			System.err.println("The author given is not a valid author");
			return 0.0;
		}
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
			String titleOfInterest= iterpub.getTitle();
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
		try {
		Publication citingPublication = null;
		Publication citedPublication = null;
		for (Iterator<Publication> iter= database.listIterator(); iter.hasNext();) {
			Publication cite=iter.next();
			if (cite.getID().equals(citingID)) {
				citingPublication= cite;
			}
			if (cite.getID().equals(citedID)) {
				citedPublication= cite;
			}
		}
		if (citingPublication==null || citedPublication==null || citingPublication == citedPublication) {
			throw new IllegalArgumentException();
		}

		List<Publication> citationsOfCitingPublication = new ArrayList<Publication>(Arrays.asList(citingPublication.getCites()));
		for (Iterator<Publication> itercite= citationsOfCitingPublication.listIterator(); itercite.hasNext();) {
			Publication cite=itercite.next();
			if (cite==citedPublication) {
				throw new IllegalArgumentException();
			}
		}
		citationsOfCitingPublication.add(citedPublication);
		Publication[] ListCitationsOfCitingPublication= new Publication[citationsOfCitingPublication.size()];
		ListCitationsOfCitingPublication=citationsOfCitingPublication.toArray(ListCitationsOfCitingPublication);

		List<Publication> CitedByOfCitedPublication = new ArrayList<Publication>(Arrays.asList(citedPublication.getCitedBy()));
		CitedByOfCitedPublication.add(citingPublication);
		Publication[] ListCitedByOfCitedPublicatoin= new Publication[CitedByOfCitedPublication.size()];
		ListCitedByOfCitedPublicatoin=CitedByOfCitedPublication.toArray(ListCitedByOfCitedPublicatoin);
		for (Iterator<Publication> iter2= database.listIterator(); iter2.hasNext();) {
			Publication cite2=iter2.next();
			if (cite2.getID().equals(citingID)) {
				cite2.setCites(ListCitationsOfCitingPublication);
			}
			if (cite2.getID().equals(citedID)) {
				cite2.setCitedby(ListCitedByOfCitedPublicatoin);
			}
		}
		} catch (IllegalArgumentException iae) {
			System.err.println("Invalid article selection for reference");
		}
	}

	/**
	 * Destructor that explicitly deletes an article from database and cites list of articles that cite it
	 * @param article to be destroyed
	 * @pre article must be in database
	 * @post article no longer in database or any other article citations
	 */
	public void deletePublication(Publication publication) {
		try{
		if (!existsInDatabase(publication))
			throw new IllegalArgumentException();
		List<Publication> updatedDB= new ArrayList<Publication>();
		for (ListIterator<Publication> iter= database.listIterator(); iter.hasNext();) {
			Publication iterarticle= iter.next();
			List<Publication> citesDelete= new ArrayList<>(Arrays.asList(iterarticle.getCites()));
			List<Publication> citedByDelete= new ArrayList<>(Arrays.asList(iterarticle.getCitedBy()));
			if (iterarticle != publication) {
				for (ListIterator<Publication> iterCites= citesDelete.listIterator(); iterCites.hasNext();) {
					Publication citesCheck= iterCites.next();
					if (citesCheck == publication) {
						iterCites.remove();
					}
				}
				for (ListIterator<Publication> iterCited= citedByDelete.listIterator(); iterCited.hasNext();) {
					Publication citedCheck= iterCited.next();
					if (citedCheck == publication) {
						iterCited.remove();
					}
				}
				Publication[] citesDeleted= new Publication[citesDelete.size()];
				citesDeleted=citesDelete.toArray(citesDeleted);
				Publication[] citedByDeleted= new Publication[citedByDelete.size()];
				citedByDeleted=citedByDelete.toArray(citedByDeleted);
				iterarticle.setCites(citesDeleted);
				iterarticle.setCitedby(citedByDeleted);
				updatedDB.add(iterarticle);
			}
			//for the article you're deleting, remove all its cites & citedBy's
			else if (iterarticle == publication) {
				citesDelete.clear();
				citedByDelete.clear();

				Publication[] citesDeleted = new Publication[citesDelete.size()];
				citesDeleted = citesDelete.toArray(citesDeleted);

				Publication[] citedByDeleted= new Publication[citedByDelete.size()];
				citedByDeleted=citedByDelete.toArray(citedByDeleted);

				iterarticle.setCites(citesDeleted);
				iterarticle.setCitedby(citedByDeleted);
			}

		}
		database=updatedDB;
		} catch (IllegalArgumentException iae) {
			System.err.println("The given publication already exists in the database");
		}
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
		try {
		if (!existsInDatabase(publication))
			throw new IllegalArgumentException();
		HashSet<Publication> citations= new HashSet<Publication>();
		//directly and indirectly cited:
		Publication[] directlyCited= publication.getCitedBy();
		for (Publication pubby: directlyCited) {
			citations.add(pubby);
			Publication[] indirectlyCited= pubby.getCitedBy();
			for (Publication pubby2: indirectlyCited) {
				if (pubby2!= publication) {
					citations.add(pubby2);
				}
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
				if (farCite!= publication) {
					orderedCitations.add(farCite);
				}
			}
		}
		Publication[] allCited= orderedCitations.toArray(new Publication[orderedCitations.size()]);
		return allCited;
		} catch (IllegalArgumentException iae) {
			System.err.println("Publication doesn't exist in database");
			return null;
		}
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

