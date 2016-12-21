package assignment1;

import assignment1.Article;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Database {
	List<Article> database= new ArrayList<>();

	public void addArticle(Article article) {

		database.add(article);
	}

	public Article[] findBibliography(String author) {
		//create new Arraylist which is meant to contain objects of the type Article
		List<Article> authorList= new ArrayList<>();
		//Loop through the ArrayList database using the listiterator for as long as the iterator iter has a next article object
		for (Iterator<Article> iter= database.listIterator(); iter.hasNext();) {
			//store the current article in iterarticle
			Article iterarticle= iter.next();
			//convert the author names in the current article to F. Lastname & store in an array of strings called authors
			String[] authors=iterarticle.convertAuthorNames();
			//If the authors array contains the requested author, then add the iterarticle to the authorList
			if (Arrays.asList(authors).contains(author)) {
				authorList.add(iterarticle);
			}
		}
		//Create an array of articles called containsKeywords of the size equal to the length of the authorlist
		Article[] containsKeywords= new Article[authorList.size()];
		//convert the authorlist to the containsKeywords array
		containsKeywords=authorList.toArray(containsKeywords);
		//return the array with the articles 
		return containsKeywords;

	}

	public Article[] findKeyword(String word) {
		List<Article> keywordList= new ArrayList<>();
		for (Iterator<Article> iter= database.listIterator(); iter.hasNext();) {
			Article iterarticle= iter.next();
			//get the title of the current article
			String titleOfInterest= iterarticle.getTitle();
			//convert the title of the current article to lower case & check if it contains the keyword in lower case
			//If it does, add the current article to the keywordList
			if (titleOfInterest.toLowerCase().contains(word.toLowerCase())) {
				keywordList.add(iterarticle);
			}
		}
		Article[] containsKeywords= new Article[keywordList.size()];
		containsKeywords=keywordList.toArray(containsKeywords);
		return containsKeywords;
	}

	public void addReference(String citingID, String citedID) {
		//*TODO*// //remember to throw an error if either of the given IDs are not within the database
		//create two articles. One that cites, one that is cited by
		Article citesA = null;
		Article citedA = null;
		//retrieve the articles with these IDs by looping through the database. 
		for (Iterator<Article> iter= database.listIterator(); iter.hasNext();) {
			//store the current article as cite
			Article cite=iter.next();
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
		List<Article> citesAA= new ArrayList<Article>(Arrays.asList(citesA.getCites()));
		//add the article that gets cited to that list
		citesAA.add(citedA);
		//create a new array with the length of that list
		Article[] citesAnew= new Article[citesAA.size()];
		//store the list into that array
		citesAnew=citesAA.toArray(citesAnew);
		//update the cited array of the article that gets cited
		//first store the articles that cited the article already into a list that contains articles
		List<Article> citedAA= new ArrayList<Article>(Arrays.asList(citedA.getCites()));
		//then add the article that cites the article to that list of articles
		citedAA.add(citesA);
		//create a new array of articles with the length of that list
		Article[] citedAnew= new Article[citedAA.size()];
		//then store the list into that array of articles
		citedAnew=citedAA.toArray(citedAnew);

		//put them back in the database
		//iterate through the database of articles
		for (Iterator<Article> iter2= database.listIterator(); iter2.hasNext();) {
			//store the current article as cite2
			Article cite2=iter2.next();
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


	public void deleteArticle(Article article) {
		//iterate through the database of articles
		for (Iterator<Article> iter= database.listIterator(); iter.hasNext();) {
			//store the current article as iterarticle
			Article iterarticle= iter.next();
			//if the current article matches the argument article, delete it from iter /*TODO*/ (Does this also delete it from the database? This should probably also renew the database arraylist)
			Article[] iterarticleCited = new Article[] {};
			if (iterarticle == article) {
				//store the articles by which the current article gets cited in an array iterarticleCited
				iterarticleCited = iterarticle.getCitedby();
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
				List<Article> iterarticleCitedList = new ArrayList<Article>(Arrays.asList(iterarticleCited));
				for (Article current : iterarticleCitedList) {
					//convert the array with citations to a list: currentCites
					List<Article> currentCites = new ArrayList<Article>(Arrays.asList(current.getCites()));
					//loop through the list with citations & remove the article of which its ID is equal to article.ID
					for (Iterator<Article> iterArt = currentCites.listIterator(); iterArt.hasNext();) {
						Article currentCitation = iterArt.next();
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

