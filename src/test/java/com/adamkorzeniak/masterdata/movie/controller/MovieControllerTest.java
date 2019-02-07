package com.adamkorzeniak.masterdata.movie.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.adamkorzeniak.masterdata.movie.model.Movie;
import com.adamkorzeniak.masterdata.movie.service.GenreService;
import com.adamkorzeniak.masterdata.movie.service.MovieService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin")
public class MovieControllerTest {

	private String baseMoviesPath = "/Movie/api/v0/movies";

	@MockBean
	private MovieService movieService;
	@MockBean
	private GenreService genreService;

	@Autowired
	private MockMvc mockMvc;

//	@Test
//	public void GetAllMovies_NoIssue_ReturnsAllMovies() throws Exception {
//		
//		doReturn(Arrays.asList(new Movie(), new Movie())).when(movieService).findAllMovies();
//		
//		mockMvc.perform(get(baseMoviesPath))
//			.andExpect(status().isOk())
//			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//			.andExpect(jsonPath("$", hasSize(2)));
//	}

	@Test
	public void GetMovieById_CorrectIdProvided_ReturnsMovie() throws Exception {
		Movie mockMovie = new Movie();
		mockMovie.setTitle("Movie");
		Long id = 15L;

		doReturn(mockMovie).when(movieService).findMovieById(id);

		mockMvc.perform(get(baseMoviesPath + '/' + id)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.title", is("Movie")));
	}

	@Test
	public void GetMovieById_WrongIdProvided_ThrowsNotFoundException() throws Exception {
		Long id = 15L;

		doReturn(null).when(movieService).findMovieById(id);

		mockMvc.perform(get(baseMoviesPath + '/' + id)).andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.code", is("REQ404"))).andExpect(jsonPath("$.title", is("Not Found")))
				.andExpect(jsonPath("$.message", is("Movie not found: id=" + id)));
	}

//	@Test
	public void AddMovie_NoIssues_ReturnsAddedMovie() throws Exception {
		Movie postMovie = new Movie();
		postMovie.setTitle("Mockingbird");
		postMovie.setDuration(100);
		Movie mockMovie = new Movie();
		mockMovie.setTitle("Mockingbird");
		mockMovie.setDuration(100);

		doReturn(mockMovie).when(movieService).addMovie(Matchers.any());

		mockMvc.perform(post(baseMoviesPath).contentType(MediaType.APPLICATION_JSON).content(postMovie.toString()))
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.title", is("Mockingbird"))).andExpect(jsonPath("$.duration", is(100)));
	}

	@Test
	public void DeleteMovie_CorrectIdProvided_DeletedMovie() throws Exception {
		Long id = 15L;

		doReturn(true).when(movieService).isMovieExist(id);

		mockMvc.perform(delete(baseMoviesPath + "/" + id)).andExpect(status().isNoContent());
	}

	@Test
	public void DeleteMovie_WrongIdProvided_ThrowsNotFoundException() throws Exception {
		Long id = 15L;

		doReturn(false).when(movieService).isMovieExist(id);

		mockMvc.perform(delete(baseMoviesPath + "/" + id)).andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.code", is("REQ404"))).andExpect(jsonPath("$.title", is("Not Found")))
				.andExpect(jsonPath("$.message", is("Movie not found: id=" + id)));
	}

}
