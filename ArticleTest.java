package assignment1;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ArticleTest {

	private Article article1;
	private Article[] cites= null;
	private Article[] citedby=null;

	@Before
	public void setUp() throws Exception {
		article1 = new Article("FAZMARR2013","Marrow fat and bone: new perspectives", new String[] {"Fazelli, Patrick", "Horowitz, Mark", "MacDougald, Ornella"}, "Journal of Clinical Endocrinology and Metabolism", 3, 2013, cites, citedby);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		assertEquals("Marrow fat and bone: new perspectives", article1.getTitle());
		}
	

	@Test
	public void test3() {
		String[] authorNamesTest= {"P. Fazelli", "M. Horowitz", "O. MacDougald"};
		assertArrayEquals(authorNamesTest, article1.getAuthors());
	}
	
	@Test
	public void test4() {
		assertEquals(3, article1.getNumberOfAuthors());
	}
	
	@Test
	public void test2() {
		article1.setCapitalizedTitle();
		assertEquals("Marrow Fat And Bone: New Perspectives", article1.getTitle());
	}
	
	@Test
	public void test5() {
		assertEquals("Journal of Clinical Endocrinology and Metabolism", article1.getJournal());
	}
	
	@Test
	public void test6() {
		assertEquals(3, article1.getIssue());
	}
	
	@Test
	public void test7() {
		assertEquals(2013, article1.getYear());
	}
	
	@Test
	public void test8() {
		boolean isOlderTest= false;
		assertEquals(isOlderTest, article1.isOlderThan());
	}

	
	
}

