package bg.sofia.uni.fmi.mjt.movies;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import bg.sofia.uni.fmi.mjt.movies.MoviesExplorer;
import bg.sofia.uni.fmi.mjt.movies.model.Actor;
import bg.sofia.uni.fmi.mjt.movies.model.Movie;

public class MoviesExplorerTest {

	static final int YEAR_ONE = 2017;
	static final int YEAR_TWO = 2018;
	static final int YEAR_THREE = 1998;
	static final int YEAR_FOUR = 2010;

	@Test
	public void getMoviesTest() {

		String dummyData = "Room 506 (2017)/ Hristova, Tanya/ Cherneva, Yoana/ Lungolova, Magdalena"
				+ System.lineSeparator() + "Room 115 (2018)/ Desi, First/ Desislava, Second/ Drangova, Radost";

		MoviesExplorer explorer = new MoviesExplorer(new ByteArrayInputStream(dummyData.getBytes()));

		final int number = 2;

		assertEquals(number, explorer.getMovies().size());

	}

	@Test
	public void countMoviesReleasedInTest() {

		String dummyData = "Room 506 (2017)/ Hristova, Tanya/ Cherneva, Yoana/ Lungolova, Magdalena"
				+ System.lineSeparator() + "Room 115 (2018)/ Desi, First/ Desislava, Second/ Drangova, Radost";

		MoviesExplorer explorer = new MoviesExplorer(new ByteArrayInputStream(dummyData.getBytes()));

		assertEquals(1, explorer.countMoviesReleasedIn(YEAR_ONE));
		assertEquals(1, explorer.countMoviesReleasedIn(YEAR_TWO));
		assertEquals(0, explorer.countMoviesReleasedIn(YEAR_THREE));

	}

	@Test
	public void findFirstMovieWithTitleTest() {

		String dummyData = "Room 506 (2017)/ Hristova, Tanya/ Cherneva, Yoana/ Lungolova, Magdalena"
				+ System.lineSeparator() + "Room 115 (2018)/ Desi, First/ Desislava, Second/ Drangova, Radost";

		MoviesExplorer explorer = new MoviesExplorer(new ByteArrayInputStream(dummyData.getBytes()));

		assertEquals("Room 506", explorer.findFirstMovieWithTitle("Room 506").getTitle());
		assertEquals(YEAR_ONE, explorer.findFirstMovieWithTitle("Room 506").getYear());
	}

	@Test
	public void findFirstMovieWithTitleTestTwo() {

		String dummyData = "Room 506 (2017)/ Hristova, Tanya/ Cherneva, Yoana/ Lungolova, Magdalena"
				+ System.lineSeparator() + "Room 115 (2018)/ Desi, First/ Desislava, Second/ Drangova, Radost";

		MoviesExplorer explorer = new MoviesExplorer(new ByteArrayInputStream(dummyData.getBytes()));

		assertEquals("Room 506", explorer.findFirstMovieWithTitle("Room").getTitle());
		assertEquals(YEAR_ONE, explorer.findFirstMovieWithTitle("Room ").getYear());
	}

	@Test(expected = IllegalArgumentException.class)
	public void findFirstMovieWithTitleTestThree() {

		String dummyData = "Room 506 (2017)/ Hristova, Tanya/ Cherneva, Yoana/ Lungolova, Magdalena"
				+ System.lineSeparator() + "Room 115 (2018)/ Desi, First/ Desislava, Second/ Drangova, Radost";

		MoviesExplorer explorer = new MoviesExplorer(new ByteArrayInputStream(dummyData.getBytes()));

		explorer.findFirstMovieWithTitle("foo bar");

	}

	@Test
	public void getAllActorsTest() {

		String dummyData = "Room 506 (2017)/ Hristova, Tanya/ Cherneva, Yoana/ Lungolova, Magdalena"
				+ System.lineSeparator() + "Room 115 (2018)/ Desi, First/ Desislava, Second/ Drangova, Radost";

		MoviesExplorer explorer = new MoviesExplorer(new ByteArrayInputStream(dummyData.getBytes()));

		final int numActors = 6;

		assertEquals(numActors, explorer.getAllActors().size());

	}

	@Test
	public void getAllActorsTestTwo() {

		String dummyData = "Room 506 (2017)/ Hristova, Tanya/ Cherneva, Yoana/ Lungolova, Magdalena"
				+ System.lineSeparator() + "Room 115 (2018)/ Desi, First/ Hristova, Tanya/ Drangova, Radost";

		MoviesExplorer explorer = new MoviesExplorer(new ByteArrayInputStream(dummyData.getBytes()));

		final int numActors = 5;

		assertEquals(numActors, explorer.getAllActors().size());

	}

	@Test
	public void getFirstYearTest() {

		String dummyData = "Room 506 (2010)/ Hristova, Tanya/ Cherneva, Yoana/ Lungolova, Magdalena"
				+ System.lineSeparator() + "Room 115 (2018)/ Desi, First/ Hristova, Tanya/ Drangova, Radost";

		MoviesExplorer explorer = new MoviesExplorer(new ByteArrayInputStream(dummyData.getBytes()));

		assertEquals(YEAR_FOUR, explorer.getFirstYear());

	}

	@Test
	public void getFirstYearTestTwo() {

		String dummyData = "Room 506 (2018)/ Hristova, Tanya/ Cherneva, Yoana/ Lungolova, Magdalena"
				+ System.lineSeparator() + "Room 115 (2018)/ Desi, First/ Hristova, Tanya/ Drangova, Radost";

		MoviesExplorer explorer = new MoviesExplorer(new ByteArrayInputStream(dummyData.getBytes()));

		assertEquals(YEAR_TWO, explorer.getFirstYear());

	}

	@Test
	public void getAllMoviesByTest() {

		String dummyData = "Room 506 (2010)/ Hristova, Tanya/ Cherneva, Yoana/ Lungolova, Magdalena"
				+ System.lineSeparator() + "Room 115 (2018)/ Desi, First/ Hristova, Tanya/ Drangova, Radost";

		MoviesExplorer explorer = new MoviesExplorer(new ByteArrayInputStream(dummyData.getBytes()));
		Actor me = new Actor("Tanya", "Hristova");

		final int number = 2;

		assertEquals(number, explorer.getAllMoviesBy(me).size());

	}

	@Test
	public void getAllMoviesByTestTwo() {

		String dummyData = "Room 506 (2010)/ Hristova, Tanya/ Cherneva, Yoana/ Lungolova, Magdalena"
				+ System.lineSeparator() + "Room 115 (2018)/ Desi, First/ Hristova, Tanya/ Drangova, Radost";

		MoviesExplorer explorer = new MoviesExplorer(new ByteArrayInputStream(dummyData.getBytes()));
		Actor you = new Actor("Viktoriya", "Dobreva");

		final int number = 0;

		assertEquals(number, explorer.getAllMoviesBy(you).size());

	}

	@Test
	public void findYearWithLeastNumberOfReleasedMoviesTest() {

		String dummyData = "Room 506 (2010)/ Hristova, Tanya/ Cherneva, Yoana/ Lungolova, Magdalena"
				+ System.lineSeparator() + "Room 115 (2018)/ Desi, First/ Hristova, Tanya/ Drangova, Radost"
				+ System.lineSeparator() + "Room 120 (2010)/ Desi, First/ Hristova, Tanya/ Drangova, Radost";

		MoviesExplorer explorer = new MoviesExplorer(new ByteArrayInputStream(dummyData.getBytes()));

		assertEquals(YEAR_TWO, explorer.findYearWithLeastNumberOfReleasedMovies());

	}

	@Test
	public void findMovieWithGreatestNumberOfActorsTest() {

		String dummyData = "Room 506 (2010)/ Simeonov, Simeon/ Hristova, Tanya/ Cherneva, Yoana/ Lungolova, Magdalena"
				+ System.lineSeparator() + "Room 115 (2018)/ Desi, First/ Hristova, Tanya/ Drangova, Radost"
				+ System.lineSeparator() + "Room 120 (2010)/ Desi, First/ Hristova, Tanya/ Drangova, Radost";

		MoviesExplorer explorer = new MoviesExplorer(new ByteArrayInputStream(dummyData.getBytes()));

		assertEquals("Room 506", explorer.findMovieWithGreatestNumberOfActors().getTitle());

	}

	@Test
	public void getMoviesSortedByReleaseYearTest() {

		String dummyData = "Room 506 (2010)/ Simeonov, Simeon/ Hristova, Tanya/ Cherneva, Yoana/ Lungolova, Magdalena"
				+ System.lineSeparator() + "Room 115 (2018)/ Desi, First/ Hristova, Tanya/ Drangova, Radost"
				+ System.lineSeparator() + "Room 120 (2015)/ Desi, First/ Hristova, Tanya/ Drangova, Radost";

		MoviesExplorer explorer = new MoviesExplorer(new ByteArrayInputStream(dummyData.getBytes()));

		assertEquals("Room 506", ((Movie) explorer.getMoviesSortedByReleaseYear().toArray()[0]).getTitle());

	}

	@Test
	public void getFirstAndLastNameTest() {

		Actor random = new Actor("Penka", "Panayotova");
		
		assertEquals("Penka", random.getFirstName());
		assertEquals("Panayotova", random.getLastName());
		
	}
	
	@Test
	public void actorToStringTest() {

		Actor random = new Actor("Penka", "Panayotova");
		
		assertEquals("Actor [firstName=Penka, lastName=Panayotova]", random.toString());
		
	}
	
	@Test
	public void movieToStringTest() {

		String dummyData = "Room 506 (2010)/ Simeonov, Simeon/ Hristova, Tanya/ Cherneva, Yoana/ Lungolova, Magdalena"
				+ System.lineSeparator() + "Room 115 (2018)/ Desi, First/ Hristova, Tanya/ Drangova, Radost"
				+ System.lineSeparator() + "Room 120 (2010)/ Desi, First/ Hristova, Tanya/ Drangova, Radost";

		MoviesExplorer explorer = new MoviesExplorer(new ByteArrayInputStream(dummyData.getBytes()));

		assertEquals("Movie [title=Room 506, year=2010]", explorer.findMovieWithGreatestNumberOfActors().toString());
		
	}


}
