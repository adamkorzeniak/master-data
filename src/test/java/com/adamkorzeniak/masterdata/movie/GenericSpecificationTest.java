package com.adamkorzeniak.masterdata.movie;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.adamkorzeniak.masterdata.common.GenericSpecification;
import com.adamkorzeniak.masterdata.common.api.SearchFilter;
import com.adamkorzeniak.masterdata.common.api.SearchFilterService;
import com.adamkorzeniak.masterdata.movie.model.Movie;
import com.adamkorzeniak.masterdata.movie.repository.MovieRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@AutoConfigureTestEntityManager
@Transactional
@SpringBootTest
public class GenericSpecificationTest {
	
	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private SearchFilterService searchFilterService;
	
	@Test
	public void GenericSpecificationInMovieRepository_FilteringOneMovieWithEachCondition_ReturnsListWithOneMovie() throws Exception {

		persistMoviesForFiltering();
		
		Map<String, String> params = new HashMap<>();
		params.put("match-title", "Title matched");
		params.put("search-description", "story");
		params.put("min-year", "2000");
		params.put("max-duration", "200");
		params.put("exist-review", "true");
		params.put("exist-plotSummary", "false");

		List<SearchFilter> filters = searchFilterService.buildFilters(params, "movie.movies");
		Specification<Movie> spec = new GenericSpecification<>(filters);
		List<Movie> movies = movieRepository.findAll(spec);
		
		assertThat(movies.size()).isEqualTo(1);
		Movie movie = movies.get(0);
		assertThat(movie.getTitle()).isEqualTo("Title matched");
		assertThat(movie.getYear()).isEqualTo(2005);
		assertThat(movie.getDuration()).isEqualTo(195);
		assertThat(movie.getDescription()).isEqualTo("Love story on ship");
		assertThat(movie.getReview()).isEqualTo("This is nice movie");
		assertThat(movie.getPlotSummary()).isNull();
	
	}
	
	@Test
	public void GenericSpecificationInMovieRepository_OrderMoviesAscending_ReturnsSortedList() throws Exception {

		persistMoviesForOrdering();
		
		Map<String, String> params = new HashMap<>();
		params.put("min-duration", "187");
		params.put("order-asc", "duration");

		List<SearchFilter> filters = searchFilterService.buildFilters(params, "movie.movies");
		Specification<Movie> spec = new GenericSpecification<>(filters);
		List<Movie> movies = movieRepository.findAll(spec);
		
		assertThat(movies.size()).isEqualTo(5);
		assertThat(movies.get(0).getDuration()).isEqualTo(187);
		assertThat(movies.get(1).getDuration()).isEqualTo(189);
		assertThat(movies.get(2).getDuration()).isEqualTo(191);
		assertThat(movies.get(3).getDuration()).isEqualTo(193);
		assertThat(movies.get(4).getDuration()).isEqualTo(195);
	}
	
	@Test
	public void GenericSpecificationInMovieRepository_OrderMoviesDescending_ReturnsSortedList() throws Exception {

		persistMoviesForOrdering();
		
		Map<String, String> params = new HashMap<>();
		params.put("order-desc", "year");
		params.put("max-year", "2013");

		List<SearchFilter> filters = searchFilterService.buildFilters(params, "movie.movies");
		Specification<Movie> spec = new GenericSpecification<>(filters);
		List<Movie> movies = movieRepository.findAll(spec);
		
		assertThat(movies.size()).isEqualTo(5);
		assertThat(movies.get(0).getYear()).isEqualTo(2013);
		assertThat(movies.get(1).getYear()).isEqualTo(2011);
		assertThat(movies.get(2).getYear()).isEqualTo(2009);
		assertThat(movies.get(3).getYear()).isEqualTo(2007);
		assertThat(movies.get(4).getYear()).isEqualTo(2005);
	}
	
	private void persistMoviesForFiltering() {
		List<Movie> movies = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			Movie movie = new Movie();
			movie.setTitle("Title matched");
			movie.setYear(2005 + i);
			movie.setDuration(195 - i);
			movie.setDescription("Love story on ship");
			movie.setReview("This is nice movie");
			movies.add(movie);
		}
		movies.get(1).setTitle("Another title");
		movies.get(2).setYear(1995);
		movies.get(3).setDuration(205);
		movies.get(4).setDescription("Another description");
		movies.get(5).setReview(null);
		movies.get(6).setPlotSummary("Plot summary exists");
		
		movies.forEach(entityManager::persist);
	}
	

	
	private void persistMoviesForOrdering() {
		List<Movie> movies = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			Movie movie = new Movie();
			movie.setTitle("Title");
			movie.setYear(2005 + i*2);
			movie.setDuration(195 - i*2);
			movies.add(movie);
		}
		Collections.shuffle(movies);
		movies.forEach(entityManager::persist);
	}
}
