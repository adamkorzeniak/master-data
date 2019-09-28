package com.adamkorzeniak.masterdata.movie;

import com.adamkorzeniak.masterdata.features.movie.model.Movie;
import com.adamkorzeniak.masterdata.features.movie.model.dto.MovieDTO;
import com.adamkorzeniak.masterdata.features.movie.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin")
public class MovieControllerTest {

    private String baseMoviesPath = "/v0/Movie/movies";

    @MockBean
    private MovieService movieService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void SearchMovies_TwoMoviesMatching_TwoMoviesReturned() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("min-year", "2000");
        Movie movie1 = new Movie();
        movie1.setId(15L);
        movie1.setTitle("Movie");
        Movie movie2 = new Movie();
        movie2.setId(25L);
        movie2.setTitle("Titanic");
        List<Movie> movies = Arrays.asList(movie1, movie2);

        when(movieService.searchMovies(params)).thenReturn(movies);

        mockMvc.perform(get(baseMoviesPath).param("min-year", "2000"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(15)))
            .andExpect(jsonPath("$[0].title", is("Movie")))
            .andExpect(jsonPath("$[1].id", is(25)))
            .andExpect(jsonPath("$[1].title", is("Titanic")));
    }

    @Test
    public void SearchMovies_NoMoviesMatching_NoMoviesReturned() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("min-year", "2000");
        List<Movie> movies = new ArrayList<>();

        when(movieService.searchMovies(params)).thenReturn(movies);

        mockMvc.perform(get(baseMoviesPath).param("min-year", "2000"))
            .andExpect(status().isNoContent());
    }

    @Test
    public void GetMovieById_CorrectIdProvided_ReturnsMovie() throws Exception {
        Long id = 15L;
        Movie mocked = new Movie();
        mocked.setTitle("Movie");
        mocked.setId(15L);
        Optional<Movie> optional = Optional.of(mocked);

        when(movieService.findMovieById(id)).thenReturn(optional);

        mockMvc.perform(get(baseMoviesPath + '/' + id)).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.title", is("Movie")))
            .andExpect(jsonPath("$.id", is(15)));
    }

    @Test
    public void GetMovieById_WrongIdProvided_ReturnsNotFoundError() throws Exception {
        Long id = 15L;

        when(movieService.findMovieById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get(baseMoviesPath + '/' + id)).andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is("REQ001")))
            .andExpect(jsonPath("$.title", is("Not Found")))
            .andExpect(jsonPath("$.message", is("Movie not found: id=" + id)));
    }

    @Test
    public void AddMovie_NoIssues_ReturnsAddedMovie() throws Exception {
        MovieDTO postMovie = new MovieDTO();
        postMovie.setTitle("Mockingbird");
        postMovie.setDuration(100);
        postMovie.setYear(2000);
        Movie mockMovie = new Movie();
        mockMovie.setTitle("Mockingbird");
        mockMovie.setDuration(100);
        mockMovie.setYear(2000);

        String requestJson = convertToJson(postMovie);

        when(movieService.addMovie(ArgumentMatchers.any())).thenReturn(mockMovie);

        mockMvc.perform(post(baseMoviesPath).contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.title", is("Mockingbird")))
            .andExpect(jsonPath("$.duration", is(100)))
            .andExpect(jsonPath("$.year", is(2000)));
    }

    @Test
    public void AddMovie_MissingRequiredField_ReturnsBadRequestError() throws Exception {
        MovieDTO postMovie = new MovieDTO();
        postMovie.setDuration(100);
        postMovie.setTitle("Titanic");

        String requestJson = convertToJson(postMovie);
        String errorMessage = "Invalid 'year' field value: null. Field 'year' must not be null.";

        mockMvc.perform(post(baseMoviesPath)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is("REQ000")))
            .andExpect(jsonPath("$.title", is("Bad Request")))
            .andExpect(jsonPath("$.message", is(errorMessage)));
    }

    @Test
    public void UpdateMovie_NoIssues_ReturnsUpdatedMovie() throws Exception {
        Long id = 11L;
        MovieDTO movie = new MovieDTO();
        movie.setId(id);
        movie.setTitle("Mockingbird");
        movie.setDuration(100);
        movie.setYear(2000);
        Movie mockMovie = new Movie();
        mockMovie.setId(id);
        mockMovie.setTitle("Mockingbird");
        mockMovie.setDuration(100);
        mockMovie.setYear(2000);

        String requestJson = convertToJson(movie);

        when(movieService.isMovieExist(id)).thenReturn(true);
        when(movieService.updateMovie(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(mockMovie);

        mockMvc.perform(put(baseMoviesPath + "/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.id", is(11)))
            .andExpect(jsonPath("$.title", is("Mockingbird")))
            .andExpect(jsonPath("$.duration", is(100)))
            .andExpect(jsonPath("$.year", is(2000)));
    }

    @Test
    public void UpdateMovie_WrongIdProvided_ReturnsNotFoundError() throws Exception {
        Long id = 11L;
        MovieDTO movie = new MovieDTO();
        movie.setId(id);
        movie.setTitle("Mockingbird");
        movie.setDuration(100);
        movie.setYear(2000);
        String requestJson = convertToJson(movie);

        when(movieService.isMovieExist(id)).thenReturn(false);

        mockMvc.perform(put(baseMoviesPath + "/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is("REQ001")))
            .andExpect(jsonPath("$.title", is("Not Found")))
            .andExpect(jsonPath("$.message", is("Movie not found: id=" + id)));
    }

    @Test
    public void DeleteMovie_CorrectIdProvided_DeletedMovie() throws Exception {
        Long id = 15L;

        when(movieService.isMovieExist(id)).thenReturn(true);

        mockMvc.perform(delete(baseMoviesPath + "/" + id)).andExpect(status().isNoContent());
    }

    @Test
    public void DeleteMovie_WrongIdProvided_ReturnsNotFoundError() throws Exception {
        Long id = 15L;

        when(movieService.isMovieExist(id)).thenReturn(false);

        mockMvc.perform(delete(baseMoviesPath + "/" + id)).andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is("REQ001")))
            .andExpect(jsonPath("$.title", is("Not Found")))
            .andExpect(jsonPath("$.message", is("Movie not found: id=" + id)));
    }

    private String convertToJson(MovieDTO postMovie) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(postMovie);
    }

}
