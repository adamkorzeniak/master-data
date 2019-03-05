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

import com.adamkorzeniak.masterdata.movie.model.Genre;
import com.adamkorzeniak.masterdata.movie.repository.GenreRepository;
import com.adamkorzeniak.masterdata.movie.service.GenreService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest
public class GenreServiceTest {

	@MockBean
	private GenreRepository genreRepository;

	@Autowired
	private GenreService genreService;

//	@Test
//	public void FindAllGenres_NoIssue_ReturnsAllGenres() throws Exception {
//		when(genreRepository.findAll()).thenReturn(Arrays.asList(new Genre(), new Genre()));
//		
//		List<Genre> genres = genreService.findAllGenres();
//		assertEquals(2, genres.size());
//	}

	@Test
	public void FindGenreById_CorrectIdProvided_ReturnsOptionalOfGenre() throws Exception {
		Long id = 1L;
		Genre mocked = new Genre();
		mocked.setName("Comedy");
		mocked.setId(id);
		Optional<Genre> optional = Optional.of(mocked);

		when(genreRepository.findById(id)).thenReturn(optional);

		Optional<Genre> result = genreService.findGenreById(id);

		assertThat(result.isPresent()).isTrue();
		Genre genre = result.get();
		assertThat(genre.getName()).isEqualTo("Comedy");
		assertThat(genre.getId()).isEqualTo(id);

	}

	@Test
	public void FindGenreById_WrongIdProvided_ReturnsEmptyOptional() throws Exception {
		Long id = 1L;
		Optional<Genre> optional = Optional.empty();

		when(genreRepository.findById(id)).thenReturn(optional);

		Optional<Genre> result = genreService.findGenreById(id);

		assertThat(result.isPresent()).isFalse();
	}

	@Test
	public void AddGenre_GenreValid_ReturnsCreatedGenre() throws Exception {
		Long id = 1L;
		Genre genre = new Genre();
		genre.setName("Comedy");
		genre.setId(id);

		when(genreRepository.save(Matchers.<Genre>any())).thenAnswer(
			mockRepositorySaveInvocation(genre)
		);

		Genre result = genreService.addGenre(genre);
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo("Comedy");
		assertThat(result.getId()).isEqualTo(100L);
		
	}
	
	@Test
	public void UpdateGenre_GenreValid_ReturnsUpdatedGenre() throws Exception {
		Long id = 1L;
		Genre genre = new Genre();
		genre.setName("Comedy");
		genre.setId(id);

		when(genreRepository.save(Matchers.<Genre>any())).thenAnswer(
			mockRepositorySaveInvocation(genre)
		);

		Genre result = genreService.updateGenre(id, genre);
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo("Comedy");
		assertThat(result.getId()).isEqualTo(id);
	}
	
	@Test
	public void DeleteGenre_GenreIdValid_DeletesGenre() throws Exception {
		Long id = 1L;
		genreService.deleteGenre(id);
	}

	@Test
	public void IsGenreExist_GenreValid_ReturnsTrue() throws Exception {
		Long id = 1L;
		when(genreRepository.existsById(id)).thenReturn(true);

		assertThat(genreService.isGenreExist(id)).isTrue();
	}

	private Answer<?> mockRepositorySaveInvocation(Genre genre) {
		return invocation -> {
		    Object argument = invocation.getArguments()[0];
		    Genre receivedGenre = (Genre) argument;
		    if (receivedGenre.getId() >= 0 ) {
		        return genre;
		    } else {
		    	Genre newGenre = new Genre();
		    	newGenre.setId(100L);
		    	newGenre.setName(genre.getName());
		        return newGenre;
		    }
		};
	}
}
