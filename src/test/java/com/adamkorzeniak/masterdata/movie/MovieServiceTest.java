package com.adamkorzeniak.masterdata.movie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adamkorzeniak.masterdata.features.movie.model.Genre;
import com.adamkorzeniak.masterdata.features.movie.model.Movie;
import com.adamkorzeniak.masterdata.features.movie.repository.MovieRepository;
import com.adamkorzeniak.masterdata.features.movie.service.MovieService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest
public class MovieServiceTest {

	@MockBean
	private MovieRepository movieRepository;

	@Autowired
	private MovieService movieService;

	@Test
	public void SearchMovies_NoGenreParam_ReturnsListOfMovies() throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("search-title", "to the");
		
		Movie movie1 = new Movie();
		movie1.setTitle("Back to the Future");
		Genre genre1 = new Genre();
		genre1.setName("Drama");
		movie1.setGenres(Arrays.asList(genre1));
		
		Movie movie2 = new Movie();
		movie2.setTitle("Back to the Future 2");
		Genre genre2 = new Genre();
		genre2.setName("Comedy");
		movie2.setGenres(Arrays.asList(genre2));
		
		List<Movie> movies = Arrays.asList(movie1, movie2);

		when(movieRepository.findAll(ArgumentMatchers.<Specification<Movie>>any())).thenReturn(movies);

		List<Movie> result = movieService.searchMovies(params);
		
		assertThat(params).hasSize(1);
		assertThat(result).isNotNull();
		assertThat(result).hasSize(2);
		assertThat(result.get(0).getTitle()).isEqualTo("Back to the Future");
		assertThat(result.get(1).getTitle()).isEqualTo("Back to the Future 2");
	}
	
	@Test
	public void SearchMovies_WithGenreParam_ReturnsListOfMoviesForGenre() throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("search-title", "to the");
		params.put("genres", "com");
		
		Movie movie1 = new Movie();
		movie1.setTitle("Back to the Future");
		Genre genre1 = new Genre();
		genre1.setName("Drama");
		movie1.setGenres(Arrays.asList(genre1));
		
		Movie movie2 = new Movie();
		movie2.setTitle("Back to the Future 2");
		Genre genre2 = new Genre();
		genre2.setName("Comedy");
		movie2.setGenres(Arrays.asList(genre2));
		
		List<Movie> movies = Arrays.asList(movie1, movie2);

		when(movieRepository.findAll(ArgumentMatchers.<Specification<Movie>>any())).thenReturn(movies);

		List<Movie> result = movieService.searchMovies(params);
		
		assertThat(params).hasSize(1);
		assertThat(result).isNotNull();
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getTitle()).isEqualTo("Back to the Future 2");
	}

	@Test
	public void FindMovieById_CorrectIdProvided_ReturnsOptionalOfMovie() throws Exception {
		Long id = 1L;
		Movie mocked = new Movie();
		mocked.setTitle("Titanic");
		mocked.setId(id);
		Optional<Movie> optional = Optional.of(mocked);

		when(movieRepository.findById(id)).thenReturn(optional);

		Optional<Movie> result = movieService.findMovieById(id);

		assertThat(result.isPresent()).isTrue();
		Movie movie = result.get();
		assertThat(movie.getTitle()).isEqualTo("Titanic");
		assertThat(movie.getId()).isEqualTo(id);

	}

	@Test
	public void FindMovieById_WrongIdProvided_ReturnsEmptyOptional() throws Exception {
		Long id = 1L;
		Optional<Movie> optional = Optional.empty();

		when(movieRepository.findById(id)).thenReturn(optional);

		Optional<Movie> result = movieService.findMovieById(id);

		assertThat(result.isPresent()).isFalse();
	}
	
	@Test
	public void AddMovie_MovieValid_ReturnsCreatedMovie() throws Exception {
		Long id = 1L;
		Movie movie = new Movie();
		movie.setTitle("Titanic");
		movie.setId(id);

		when(movieRepository.save(ArgumentMatchers.<Movie>any())).thenAnswer(
			mockRepositorySaveInvocation(movie)
		);

		Movie result = movieService.addMovie(movie);
		assertThat(result).isNotNull();
		assertThat(result.getTitle()).isEqualTo("Titanic");
		assertThat(result.getId()).isEqualTo(100L);
		
	}
	
	@Test
	public void UpdateMovie_MovieValid_ReturnsUpdatedMovie() throws Exception {
		Long id = 1L;
		Movie movie = new Movie();
		movie.setTitle("Titanic");
		movie.setId(id);

		when(movieRepository.save(ArgumentMatchers.<Movie>any())).thenAnswer(
			mockRepositorySaveInvocation(movie)
		);

		Movie result = movieService.updateMovie(id, movie);
		assertThat(result).isNotNull();
		assertThat(result.getTitle()).isEqualTo("Titanic");
		assertThat(result.getId()).isEqualTo(id);
	}
	
	@Test
	public void DeleteMovie_MovieIdValid_DeletesMovie() throws Exception {
		Long id = 1L;
		movieService.deleteMovie(id);
	}

	@Test
	public void IsMovieExist_MovieValid_ReturnsTrue() throws Exception {
		Long id = 1L;
		when(movieRepository.existsById(id)).thenReturn(true);

		assertThat(movieService.isMovieExist(id)).isTrue();
	}

	private Answer<?> mockRepositorySaveInvocation(Movie movie) {
		return invocation -> {
		    Object argument = invocation.getArguments()[0];
		    Movie receivedMovie = (Movie) argument;
		    if (receivedMovie.getId() >= 0 ) {
		        return movie;
		    } else {
		    	Movie newMovie = new Movie();
		    	newMovie.setId(100L);
		    	newMovie.setTitle(movie.getTitle());
		        return newMovie;
		    }
		};
	}
	
}
