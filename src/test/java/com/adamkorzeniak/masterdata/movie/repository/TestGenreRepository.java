package com.adamkorzeniak.masterdata.movie.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adamkorzeniak.masterdata.exception.NotFoundException;
import com.adamkorzeniak.masterdata.movie.model.Genre;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TestGenreRepository {
	
	private Genre comedy;
	private Genre melodrama;
	private Genre thriller;
	private Genre drama;
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	public TestGenreRepository() {
		comedy = new Genre();
		comedy.setName("Comedy");
		thriller = new Genre();
		thriller.setName("Thriller");
		drama = new Genre();
		drama.setName("Drama");
		melodrama = new Genre();
		melodrama.setName("Melodrama");
	}
	
	private void populateRepository(Genre... genres) {
		for (Genre g: genres) {
			this.entityManager.persist(g);
		}
	}
	
	@Test
	public void FindAllGenres_NoIssue_ReturnsAllGenres() throws Exception {
		populateRepository(comedy, drama);
		List<Genre> genres = genreRepository.findAll();

		assertEquals(2, genres.size());
		for (Genre genre: genres) {
			if (genre.getId() == comedy.getId()) {
				assertEquals("Comedy", genre.getName());
			} else if (genre.getId() == drama.getId()) {
				assertEquals("Drama", genre.getName());
			} else {
				fail("Genre with incorrect id retrieved from database");
			}
		}
	}
	
	@Test
	public void FindGenreById_CorrectIdProvided_ReturnsOptionalWithGenre() throws Exception {
		populateRepository(comedy, thriller, melodrama, drama);
		Optional<Genre> result = genreRepository.findById(thriller.getId());
		if (!result.isPresent()) {
			throw new NotFoundException("Genre not found");
		}
		Genre genre = result.get();
		assertEquals(thriller.getId(), genre.getId());
		assertEquals("Thriller", genre.getName());
	}
	
	@Test
	public void FindGenreById_WrongIdProvided_ReturnsEmptyOptional() throws Exception {
		populateRepository(comedy, thriller, melodrama, drama);
		Optional<Genre> result = genreRepository.findById(999L);
		assertFalse(result.isPresent());
	}
	
	@Test
	public void FindGenreContainingString_GenresContainString_ReturnsGenresList() throws Exception {
		populateRepository(comedy, thriller, melodrama, drama);
		String name = "rama";
		List<Genre> genres = genreRepository.findByNameIgnoreCaseContaining(name);
		assertEquals(2, genres.size());
		for (Genre genre: genres) {
			if (genre.getId() == melodrama.getId()) {
				assertEquals("Melodrama", genre.getName());
			} else if (genre.getId() == drama.getId()) {
				assertEquals("Drama", genre.getName());
			} else {
				fail("Genre with incorrect id retrieved from database");
			}
		}
	}
	
	@Test
	public void FindGenreContainingString_NoGenreContainString_ReturnsEmptyList() throws Exception {
		populateRepository(comedy, thriller, melodrama, drama);
		String name = "dramat";
		List<Genre> genres = genreRepository.findByNameIgnoreCaseContaining(name);
		assertEquals(0, genres.size());
	}

}
