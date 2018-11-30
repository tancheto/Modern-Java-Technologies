package bg.sofia.uni.fmi.mjt.movies;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import bg.sofia.uni.fmi.mjt.movies.model.Actor;
import bg.sofia.uni.fmi.mjt.movies.model.Movie;

public class MoviesExplorer {

	private List<Movie> movies;

	public MoviesExplorer(InputStream dataInput) {

		BufferedReader data = new BufferedReader(new InputStreamReader(dataInput));

		this.movies = data.lines().map(Movie::createMovie).filter(l -> l != null).collect(Collectors.toList());
	}

	public Collection<Movie> getMovies() {
		return movies;
	}

	public int countMoviesReleasedIn(int year) {

		return (int) movies.stream().filter(m -> m.getYear() == year).count();
	}

	public Movie findFirstMovieWithTitle(String title) {

		return movies.stream().filter(m -> m.getTitle().contains(title)).findFirst()
				.orElseThrow(() -> 
				new IllegalArgumentException(new String("No film with title " + title + " found.")));

	}

	public Collection<Actor> getAllActors() {

		return movies.stream().map(m -> m.getActors()).flatMap(a -> a.stream()).collect(Collectors.toSet());

	}

	public int getFirstYear() {

		return movies.stream().min((movie1, movie2) -> movie1.getYear() - movie2.getYear()).get().getYear();

	}

	public Collection<Movie> getAllMoviesBy(Actor actor) {

		return movies.stream().filter(m -> m.getActors().contains(actor)).collect(Collectors.toSet());

	}

	public Collection<Movie> getMoviesSortedByReleaseYear() {

		return movies.stream().sorted((movie1, movie2) -> movie1.getYear() - movie2.getYear())
				.collect(Collectors.toList());

	}

	public int findYearWithLeastNumberOfReleasedMovies() {

		return movies.stream().map(m -> m.getYear())
				.min((year1, year2) -> (countMoviesReleasedIn(year1) - countMoviesReleasedIn(year2))).get();

	}

	public Movie findMovieWithGreatestNumberOfActors() {

		return movies.stream().max((movie1, movie2) -> movie1.getActors().size() - movie2.getActors().size()).get();

	}

}