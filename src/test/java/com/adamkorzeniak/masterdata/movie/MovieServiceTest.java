package com.adamkorzeniak.masterdata.movie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Matchers;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adamkorzeniak.masterdata.movie.model.Movie;
import com.adamkorzeniak.masterdata.movie.repository.MovieRepository;
import com.adamkorzeniak.masterdata.movie.service.MovieService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest
public class MovieServiceTest {

	@MockBean
	private MovieRepository movieRepository;

	@Autowired
	private MovieService movieService;

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

		when(movieRepository.save(Matchers.<Movie>any())).thenAnswer(
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

		when(movieRepository.save(Matchers.<Movie>any())).thenAnswer(
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
