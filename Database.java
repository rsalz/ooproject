package assignment1;

import assignment1.Publication;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Database {
	List<Publication> database= new ArrayList<>();

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
		//*TODO*// //remember to throw an error if either of the given IDs are not within the database
		//create two articles. One that cites, one that is cited by
		Publication citesA = null;
		Publication citedA = null;
		//retrieve the articles with these IDs by looping through the database. 
		for (Iterator<Publication> iter= database.listIterator(); iter.hasNext();) {
			//store the current article as cite
			Publication cite=iter.next();
			//if the ID of the current article matches the ID of the article that cites, then the current article is equal to citesA. (should only retrieve one result)
			if (cite.getID().equals(citingID)) {
				citesA= cite;
			}
			//if the ID of the current article matches the ID of the article that gets cited, then ... (should only retrieve one result)
			if (cite.getID().equals(citedID)) {
				citedA= cite;
			}
		}
		//update the cites array of the article that cites another
		//first store the articles it already cites to a list that contains articles(! this needs to be arraylist, add is not supported in list)
		List<Publication> citesAA= new ArrayList<Publication>(Arrays.asList(citesA.getCites()));
		//add the article that gets cited to that list
		citesAA.add(citedA);
		//create a new array with the length of that list
		Publication[] citesAnew= new Publication[citesAA.size()];
		//store the list into that array
		citesAnew=citesAA.toArray(citesAnew);
		//update the cited array of the article that gets cited
		//first store the articles that cited the article already into a list that contains articles
		List<Publication> citedAA= new ArrayList<Publication>(Arrays.asList(citedA.getCites()));
		//then add the article that cites the article to that list of articles
		citedAA.add(citesA);
		//create a new array of articles with the length of that list
		Publication[] citedAnew= new Publication[citedAA.size()];
		//then store the list into that array of articles
		citedAnew=citedAA.toArray(citedAnew);

		//put them back in the database
		//iterate through the database of articles
		for (Iterator<Publication> iter2= database.listIterator(); iter2.hasNext();) {
			//store the current article as cite2
			Publication cite2=iter2.next();
			//if the ID of the current article is equal to the ID of the article that is citing, then update its cites to the new citesAnew array
			if (cite2.getID().equals(citingID)) {
				cite2.setCites(citesAnew);
			}
			//if the ID of the current article is equal to the ID of the article that gets cited, then update its citedBy to the new citedAnew array
			if (cite2.getID().equals(citedID)) {
				cite2.setCitedby(citedAnew);
			}
		}
	}


	public void deleteArticle(Publication article) {
		//iterate through the database of articles
		for (Iterator<Publication> iter= database.listIterator(); iter.hasNext();) {
			//store the current article as iterarticle
			Publication iterarticle= iter.next();
			//if the current article matches the argument article, delete it from iter /*TODO*/ (Does this also delete it from the database? This should probably also renew the database arraylist)
			Publication[] iterarticleCited = new Publication[] {};
			if (iterarticle == article) {
				//store the articles by which the current article gets cited in an array iterarticleCited
				iterarticleCited = iterarticle.getCitedBy();
				iter.remove();

				/*TODO*/ //will delete this block later!
				//check the number of objects in database & iter to check if article gets removed from database too
				System.out.println(database.size());
				int iterations = 0;
				while (iter.hasNext()) {
					iter.next();
					iterations = iterations + 1;
					System.out.println("length iter" + iterations);
				}
				/*TODO*/ //will delete this block later!
				//loop through all the articles in iterarticleCited ( = all the articles that cite the article we'll delete)
				List<Publication> iterarticleCitedList = new ArrayList<Publication>(Arrays.asList(iterarticleCited));
				for (Publication current : iterarticleCitedList) {
					//convert the array with citations to a list: currentCites
					List<Publication> currentCites = new ArrayList<Publication>(Arrays.asList(current.getCites()));
					//loop through the list with citations & remove the article of which its ID is equal to article.ID
					for (Iterator<Publication> iterArt = currentCites.listIterator(); iterArt.hasNext();) {
						Publication currentCitation = iterArt.next();
						//if the ID of the currentArticle equals the ID of the article we want to remove => remove that citation.
						if (currentCitation.getID() == article.getID()){
							iterArt.remove();/*TODO*/ //check if this really deletes the citation
						}
					}

				}

			}

		}
	}
}

