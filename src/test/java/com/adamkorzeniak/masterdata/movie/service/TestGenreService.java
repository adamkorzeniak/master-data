package com.adamkorzeniak.masterdata.movie.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adamkorzeniak.masterdata.movie.model.Genre;
import com.adamkorzeniak.masterdata.movie.repository.GenreRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestGenreService {

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
	public void FindGenreById_CorrectIdProvided_ReturnsGenre() throws Exception {
		Long id = 1L;
		Genre mocked = new Genre();
		mocked.setName("Comedy");
		mocked.setId(id);
		Optional<Genre> optional = Optional.of(mocked);

		when(genreRepository.findById(id)).thenReturn(optional);

		Optional<Genre> result = genreService.findGenreById(id);

		assertTrue(result.isPresent());
		Genre genre = result.get();
		assertEquals("Comedy", genre.getName());
		assertEquals(id, genre.getId());

	}

	@Test
	public void FindGenreById_WrongIdProvided_ReturnsEmptyOptional() throws Exception {
		Long id = 1L;
		Optional<Genre> optional = Optional.empty();

		when(genreRepository.findById(id)).thenReturn(optional);

		Optional<Genre> result = genreService.findGenreById(id);

		assertFalse(result.isPresent());
	}

//	@Test
//	public void FindGenresByName_NameStringFound_ReturnsGenresList() throws Exception {
//		String name = "drama";
//		
//		when(genreRepository.findByNameIgnoreCaseContaining(name)).thenReturn(Arrays.asList(new Genre(), new Genre(), new Genre()));
//
//		List<Genre> genres = genreService.findGenresByName(name);
//		assertEquals(3, genres.size());
//	}

	@Test
	public void AddGenre_GenreValid_ReturnsCreatedGenre() throws Exception {
		Genre genre = new Genre();
		genre.setName("Comedy");

		when(genreRepository.save(Matchers.any())).thenReturn(genre);

		Genre result = genreService.addGenre(genre);
		assertEquals("Comedy", result.getName());
	}

	@Test
	public void UpdateGenre_GenreValid_ReturnsUpdatedGenre() throws Exception {
		Genre genre = new Genre();
		genre.setName("Comedy");

		when(genreRepository.save(Matchers.any())).thenReturn(genre);

		Genre result = genreService.updateGenre(1L, genre);
		assertEquals("Comedy", result.getName());
	}

	@Test
	public void DeleteGenre_GenreIdValid_DeletesGenre() throws Exception {
		genreService.deleteGenre(1L);
	}

	@Test
	public void IsGenreExist_GenreValid_ReturnsTrue() throws Exception {
		Long id = 1L;
		when(genreRepository.existsById(id)).thenReturn(true);

		assertTrue(genreService.isGenreExist(id));
	}
}
