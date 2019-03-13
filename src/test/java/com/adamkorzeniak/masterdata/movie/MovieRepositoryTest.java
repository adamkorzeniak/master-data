package com.adamkorzeniak.masterdata.movie;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adamkorzeniak.masterdata.movie.model.Genre;
import com.adamkorzeniak.masterdata.movie.model.Movie;
import com.adamkorzeniak.masterdata.movie.repository.MovieRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@DataJpaTest
public class MovieRepositoryTest {
	
	private Movie titanic;
	private Movie inception;
	private Movie seven;
	private List<Genre> persistedGenres;

	@Autowired
	private MovieRepository movieRepository;


	@Autowired
	private TestEntityManager entityManager;
	
	@BeforeEach
	public void createSampleMovies() {
		titanic = createMovie("Titanic", "Drama", "Romance");
		inception = createMovie("Inception", "Thriller", "Sci-fi");
		seven = createMovie("Seven", "Thriller", "Crime");
		
		populateRepository();
	}

	@Test
	public void FindMovieContainingGenre_NoMoviesContainingGenre_ReturnsEmptyList() throws Exception {
		Genre genre = findGenreByName("Horror");
		List<Movie> movies = movieRepository.findByGenresContaining(genre);
		assertThat(movies.size()).isEqualTo(0);
	}

	@Test
	public void FindMovieContainingGenre_OneMovieContainingGenre_ReturnsListWithOneElement() throws Exception {
		Genre genre = findGenreByName("Romance");
		List<Movie> movies = movieRepository.findByGenresContaining(genre);
		assertThat(movies.size()).isEqualTo(1);
		assertThat(movies.get(0).getTitle()).isEqualTo(titanic.getTitle());
	}

	@Test
	public void FindMovieContainingGenre_TwoMoviesContainingGenre_ReturnsListWithTwoElements() throws Exception {
		Genre genre = findGenreByName("Thriller");
		List<Movie> movies = movieRepository.findByGenresContaining(genre);
		assertThat(movies.size()).isEqualTo(2);
		Movie movie1 = movies.get(0);
		Movie movie2 = movies.get(1);
		assertThat(movie1.getTitle())
			.isIn(Arrays.asList(inception.getTitle(), seven.getTitle()));
		assertThat(movie2.getTitle())
			.isIn(Arrays.asList(inception.getTitle(), seven.getTitle()));
		assertThat(movie1.getTitle()).isNotEqualTo(movie2.getTitle());
	
	}

	private void populateRepository() {
		persistAndFlushGenres();
		persistMovie("Titanic", "Drama", "Romance");
		persistMovie("Inception", "Thriller", "Sci-fi");
		persistMovie("Seven", "Thriller", "Crime");
		persistMovie("No Genre");
		this.entityManager.flush();
	}
	
	private void persistAndFlushGenres() {
		List<String> genreList = Arrays.asList("Drama", "Romance", "Thriller", "Sci-fi", "Crime", "Horror");
		List<Genre> genres = genreList.stream()
			.map(this::createGenre)
			.collect(Collectors.toList());
		genres.forEach(entityManager::persist);
		this.entityManager.flush();
		persistedGenres =  genres;		
	}
	
	private Movie persistMovie(String title, String... genreNames) {
		Movie movie = new Movie();
		movie.setTitle(title);
		movie.setYear(2000);
		movie.setDuration(120);
		List<Genre> genres = Arrays.asList(genreNames).stream()
				.map(genre -> findGenreByName(genre))
				.collect(Collectors.toList());
		movie.setGenres(genres);
		this.entityManager.persist(movie);
		return movie;
	}
	
	private Genre findGenreByName(String name) {
		return persistedGenres.stream()
			.filter(genre -> genre.getName().equals(name))
			.findFirst()
			.orElse(null);
	}
	
	private Movie createMovie(String title, String... genreNames) {
		Movie movie = new Movie();
		movie.setTitle(title);
		List<Genre> genres = Arrays.asList(genreNames).stream()
				.map(name -> createGenre(name))
				.collect(Collectors.toList());
		movie.setGenres(genres);
		return movie;
	}

	private Genre createGenre(String name) {
		Genre genre = new Genre();
		genre.setName(name);
		return genre;
	}

}
