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
		List<Article> authorList= new ArrayList<>();
		for (Iterator<Article> iter= database.listIterator(); iter.hasNext();) {
			Article iterarticle= iter.next();
			String[] authors=iterarticle.convertAuthorNames();
			if (Arrays.asList(authors).contains(author)) {
				authorList.add(iterarticle);
			}
		}
		Article[] containsKeywords= new Article[authorList.size()];
		containsKeywords=authorList.toArray(containsKeywords);
		return containsKeywords;
		
	}
	
	public Article[] findKeyword(String word) {
		List<Article> keywordList= new ArrayList<>();
		for (Iterator<Article> iter= database.listIterator(); iter.hasNext();) {
			Article iterarticle= iter.next();
			String titleOfInterest= iterarticle.getTitle();
			if (titleOfInterest.toLowerCase().contains(word.toLowerCase())) {
				keywordList.add(iterarticle);
			}
		}
		Article[] containsKeywords= new Article[keywordList.size()];
		containsKeywords=keywordList.toArray(containsKeywords);
		return containsKeywords;
	}
	
	public void addReference(String citingID, String citedID) {
		//TODO remember to throw an error if either of the given IDs are not within the database
		Article citesA = null;
		Article citedA = null;
		//retrieve the articles with these IDs
		for (Iterator<Article> iter= database.listIterator(); iter.hasNext();) {
			Article cite=iter.next();
			if (cite.ID.equals(citingID)) {
				citesA= cite;
			}
			if (cite.ID.equals(citedID)) {
				citedA= cite;
			}
		}
		//update one
		List<Article> citesAA= Arrays.asList(citesA.getCites());
		citesAA.add(citedA);
		Article[] citesAnew= new Article[citesAA.size()];
		citesAnew=citesAA.toArray(citesAnew);
		//update the other
		List<Article> citedAA= Arrays.asList(citedA.getCites());
		citedAA.add(citedA);
		Article[] citedAnew= new Article[citedAA.size()];
		citedAnew=citedAA.toArray(citedAnew);
		//put them back in the database
		for (Iterator<Article> iter2= database.listIterator(); iter2.hasNext();) {
			Article cite2=iter2.next();
			if (cite2.ID.equals(citingID)) {
				cite2.setCites(citesAnew);
			}
			if (cite2.ID.equals(citedID)) {
				cite2.setCitedby(citedAnew);
			}
		}
	}
	
	public void deleteArticle(Article article) {
		for (Iterator<Article> iter= database.listIterator(); iter.hasNext();) {
			Article iterarticle= iter.next();
			Article[] iterarticleCited= iterarticle.getCitedby();
			if (iterarticle == article) {
				iter.remove();
			}
			//remove citation from all articles with this article in citedBy
			if (Arrays.asList(iterarticleCited).contains(article)) {
				List<Article> iterarticleList= Arrays.asList(iterarticleCited);
				iterarticleList.remove(article);
				Article[] iterarticleNew= new Article[iterarticleList.size()];
				iterarticleNew= iterarticleList.toArray(iterarticleNew);
				iterarticle.setCitedby(iterarticleNew);
			}
		}
	}

}
