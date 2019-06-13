package com.adamkorzeniak.masterdata.movie;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adamkorzeniak.masterdata.features.movie.model.Genre;
import com.adamkorzeniak.masterdata.features.movie.model.Movie;
import com.adamkorzeniak.masterdata.features.movie.model.dto.GenreDTO;
import com.adamkorzeniak.masterdata.features.movie.model.dto.MovieDTO;
import com.adamkorzeniak.masterdata.features.movie.service.MovieServiceHelper;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
public class MovieServiceHelperTest {
	
	private static final Long ID = 17L;
	private static final  String TITLE = "Title";
	private static final  Integer YEAR = 1990;
	private static final  Integer DURATION = 222;
	private static final  Integer RATING = 6;
	private static final  Integer WATCH_PRIORITY = 3;
	private static final  String DESCRIPTION = "description";
	private static final  String REVIEW = "review";
	private static final  String PLOT_SUMMARY = "plot summary";
	private static final  LocalDate REVIEW_DATE = LocalDate.of(2019, Month.JANUARY, 1);

	@Test
	public void ConvertMovieDtoToEntity_NoIssuesExpected_ReturnsConvertedEntity() throws Exception {
		
		MovieDTO dto = new MovieDTO();
		dto.setId(ID);
		dto.setTitle(TITLE);
		dto.setYear(YEAR);
		dto.setDuration(DURATION);
		dto.setRating(RATING);
		dto.setWatchPriority(WATCH_PRIORITY);
		dto.setDescription(DESCRIPTION);
		dto.setReview(REVIEW);
		dto.setPlotSummary(PLOT_SUMMARY);
		dto.setReviewDate(REVIEW_DATE);
		dto.setGenres(Arrays.asList(
			createGenreDTO("Comedy"),
			createGenreDTO("Drama")));
		
		Movie movie = MovieServiceHelper.convertToEntity(dto);
		
		assertThat(movie).isNotNull();
		assertThat(movie.getId()).isEqualTo(ID);
		assertThat(movie.getTitle()).isEqualTo(TITLE);
		assertThat(movie.getYear()).isEqualTo(YEAR);
		assertThat(movie.getDuration()).isEqualTo(DURATION);
		assertThat(movie.getRating()).isEqualTo(RATING);
		assertThat(movie.getWatchPriority()).isEqualTo(WATCH_PRIORITY);
		assertThat(movie.getDescription()).isEqualTo(DESCRIPTION);
		assertThat(movie.getReview()).isEqualTo(REVIEW);
		assertThat(movie.getPlotSummary()).isEqualTo(PLOT_SUMMARY);
		assertThat(movie.getReviewDate()).isEqualTo(REVIEW_DATE);
		assertThat(movie.getGenres()).isNotNull();
		assertThat(movie.getGenres().size()).isEqualTo(2);
		assertThat(movie.getGenres().get(0).getName()).isEqualTo("Comedy");
		assertThat(movie.getGenres().get(1).getName()).isEqualTo("Drama");
	}

	@Test
	public void ConvertMovieEntityToDto_NoIssuesExpected_ReturnsConvertedDto() throws Exception {
		Movie movie = new Movie();
		movie.setId(ID);
		movie.setTitle(TITLE);
		movie.setYear(YEAR);
		movie.setDuration(DURATION);
		movie.setRating(RATING);
		movie.setWatchPriority(WATCH_PRIORITY);
		movie.setDescription(DESCRIPTION);
		movie.setReview(REVIEW);
		movie.setPlotSummary(PLOT_SUMMARY);
		movie.setReviewDate(REVIEW_DATE);
		movie.setGenres(Arrays.asList(
				createGenre("Comedy"),
				createGenre("Drama")));
		
		MovieDTO dto = MovieServiceHelper.convertToDTO(movie);
		
		assertThat(dto).isNotNull();
		assertThat(dto.getId()).isEqualTo(ID);
		assertThat(dto.getTitle()).isEqualTo(TITLE);
		assertThat(dto.getYear()).isEqualTo(YEAR);
		assertThat(dto.getDuration()).isEqualTo(DURATION);
		assertThat(dto.getRating()).isEqualTo(RATING);
		assertThat(dto.getWatchPriority()).isEqualTo(WATCH_PRIORITY);
		assertThat(dto.getDescription()).isEqualTo(DESCRIPTION);
		assertThat(dto.getReview()).isEqualTo(REVIEW);
		assertThat(dto.getPlotSummary()).isEqualTo(PLOT_SUMMARY);
		assertThat(dto.getReviewDate()).isEqualTo(REVIEW_DATE);
		assertThat(dto.getGenres()).isNotNull();
		assertThat(dto.getGenres().size()).isEqualTo(2);
		assertThat(dto.getGenres().get(0).getName()).isEqualTo("Comedy");
		assertThat(dto.getGenres().get(1).getName()).isEqualTo("Drama");
	}
	
	@Test
	public void ConvertMovieDtoToEntity_GenreListNull_ReturnsConvertedEntity() throws Exception {
		
		MovieDTO dto = new MovieDTO();
		dto.setTitle(TITLE);
		dto.setGenres(null);
		
		Movie movie = MovieServiceHelper.convertToEntity(dto);
		
		assertThat(movie).isNotNull();
		assertThat(movie.getTitle()).isEqualTo(TITLE);
		assertThat(movie.getGenres()).isNotNull();
		assertThat(movie.getGenres().size()).isEqualTo(0);
	}
	
	@Test
	public void ConvertMovieDtoToEntity_GenreListEmpty_ReturnsConvertedEntity() throws Exception {
		
		MovieDTO dto = new MovieDTO();
		dto.setTitle(TITLE);
		dto.setGenres(new ArrayList<>());
		
		Movie movie = MovieServiceHelper.convertToEntity(dto);
		
		assertThat(movie).isNotNull();
		assertThat(movie.getTitle()).isEqualTo(TITLE);
		assertThat(movie.getGenres()).isNotNull();
		assertThat(movie.getGenres().size()).isEqualTo(0);
	}
	
	@Test
	public void ConvertMovieEntityToDto_GenreListNull_ReturnsConvertedDto() throws Exception {
		Movie movie = new Movie();
		movie.setTitle(TITLE);
		movie.setGenres(null);
		
		MovieDTO dto = MovieServiceHelper.convertToDTO(movie);
		
		assertThat(dto).isNotNull();
		assertThat(dto.getTitle()).isEqualTo(TITLE);
		assertThat(dto.getGenres()).isNotNull();
		assertThat(dto.getGenres().size()).isEqualTo(0);
	}
	
	@Test
	public void ConvertMovieEntityToDto_GenreListEmpty_ReturnsConvertedDto() throws Exception {
		Movie movie = new Movie();
		movie.setTitle(TITLE);
		movie.setGenres(new ArrayList<>());
		
		MovieDTO dto = MovieServiceHelper.convertToDTO(movie);
		
		assertThat(dto).isNotNull();
		assertThat(dto.getTitle()).isEqualTo(TITLE);
		assertThat(dto.getGenres()).isNotNull();
		assertThat(dto.getGenres().size()).isEqualTo(0);
	}
	
	private Genre createGenre(String name) {
		Genre genre = new Genre();
		genre.setName(name);
		return genre;
	}
	
	private GenreDTO createGenreDTO(String name) {
		GenreDTO dto = new GenreDTO();
		dto.setName(name);
		return dto;
	}
}
