package com.adamkorzeniak.masterdata.movie;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.adamkorzeniak.masterdata.features.movie.model.Genre;
import com.adamkorzeniak.masterdata.features.movie.model.dto.GenreDTO;
import com.adamkorzeniak.masterdata.features.movie.model.dto.GenrePatchDTO;
import com.adamkorzeniak.masterdata.features.movie.service.GenreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin")
public class GenreControllerTest {

    private static final String BASE_GENRES_PATH = "/v0/Movie/genres";

    @MockBean
    private GenreService genreService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void SearchGenres_TwoGenresMatching_TwoGenresReturned() throws Exception {
        Map<String, String> params = new HashMap<>();
        Genre genre1 = new Genre();
        genre1.setId(15L);
        genre1.setName("Genre");
        Genre genre2 = new Genre();
        genre2.setId(25L);
        genre2.setName("Comedy");
        List<Genre> genres = Arrays.asList(genre1, genre2);

        doReturn(genres).when(genreService).searchGenres(params);

        mockMvc.perform(get(BASE_GENRES_PATH))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(15)))
            .andExpect(jsonPath("$[0].name", is("Genre")))
            .andExpect(jsonPath("$[1].id", is(25)))
            .andExpect(jsonPath("$[1].name", is("Comedy")));
    }

    @Test
    public void SearchGenres_NoGenresMatching_NoGenresReturned() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("min-year", "2000");
        List<Genre> genres = new ArrayList<>();

        doReturn(genres).when(genreService).searchGenres(params);

        mockMvc.perform(get(BASE_GENRES_PATH).param("min-year", "2000"))
            .andExpect(status().isNoContent());
    }

    @Test
    public void GetGenreById_CorrectIdProvided_ReturnsGenre() throws Exception {
        Long id = 15L;
        Genre mocked = new Genre();
        mocked.setName("Genre");
        mocked.setId(15L);
        Optional<Genre> optional = Optional.of(mocked);

        doReturn(optional).when(genreService).findGenreById(id);

        mockMvc.perform(get(BASE_GENRES_PATH + '/' + id)).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.name", is("Genre")))
            .andExpect(jsonPath("$.id", is(15)));
    }

    @Test
    public void GetGenreById_WrongIdProvided_ReturnsNotFoundError() throws Exception {
        Long id = 15L;
        doReturn(Optional.empty()).when(genreService).findGenreById(id);

        mockMvc.perform(get(BASE_GENRES_PATH + '/' + id)).andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is("REQ001")))
            .andExpect(jsonPath("$.title", is("Not Found")))
            .andExpect(jsonPath("$.message", is("Genre not found: id=" + id)));
    }

    @Test
    public void AddGenre_NoIssues_ReturnsAddedGenre() throws Exception {
        GenreDTO postGenre = new GenreDTO();
        postGenre.setName("Comedy");
        Genre mockGenre = new Genre();
        mockGenre.setName("Comedy");

        String requestJson = convertToJson(postGenre);

        doReturn(mockGenre).when(genreService).addGenre(ArgumentMatchers.any());

        mockMvc.perform(post(BASE_GENRES_PATH).contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.name", is("Comedy")));
    }

    @Test
    public void AddGenre_MissingRequiredField_ReturnsBadRequestError() throws Exception {
        GenreDTO postGenre = new GenreDTO();
        postGenre.setId(100L);

        String requestJson = convertToJson(postGenre);
        String errorMessage = "Invalid 'name' field value: null. Field 'name' must not be blank.";

        mockMvc.perform(post(BASE_GENRES_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is("REQ000")))
            .andExpect(jsonPath("$.title", is("Bad Request")))
            .andExpect(jsonPath("$.message", is(errorMessage)));
    }

    @Test
    public void UpdateGenre_NoIssues_ReturnsUpdatedGenre() throws Exception {
        Long id = 11L;
        GenreDTO genre = new GenreDTO();
        genre.setId(id);
        genre.setName("Comedy");
        Genre mockGenre = new Genre();
        mockGenre.setId(id);
        mockGenre.setName("Comedy");

        String requestJson = convertToJson(genre);

        doReturn(true).when(genreService).isGenreExist(id);
        doReturn(mockGenre).when(genreService).updateGenre(ArgumentMatchers.anyLong(), ArgumentMatchers.any());

        mockMvc.perform(put(BASE_GENRES_PATH + "/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.id", is(11)))
            .andExpect(jsonPath("$.name", is("Comedy")));
    }

    @Test
    public void UpdateGenre_WrongIdProvided_ReturnsNotFoundError() throws Exception {
        Long id = 11L;
        GenreDTO genre = new GenreDTO();
        genre.setId(id);
        genre.setName("Comedy");
        String requestJson = convertToJson(genre);

        doReturn(false).when(genreService).isGenreExist(id);

        mockMvc.perform(put(BASE_GENRES_PATH + "/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is("REQ001")))
            .andExpect(jsonPath("$.title", is("Not Found")))
            .andExpect(jsonPath("$.message", is("Genre not found: id=" + id)));
    }

    @Test
    public void DeleteGenre_CorrectIdProvided_DeletedGenre() throws Exception {
        Long id = 15L;

        doReturn(true).when(genreService).isGenreExist(id);

        mockMvc.perform(delete(BASE_GENRES_PATH + "/" + id)).andExpect(status().isNoContent());
    }

    @Test
    public void DeleteGenre_WrongIdProvided_ReturnsNotFoundError() throws Exception {
        Long id = 15L;

        doReturn(false).when(genreService).isGenreExist(id);

        mockMvc.perform(delete(BASE_GENRES_PATH + "/" + id)).andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is("REQ001")))
            .andExpect(jsonPath("$.title", is("Not Found")))
            .andExpect(jsonPath("$.message", is("Genre not found: id=" + id)));
    }

    @Test
    public void MergeGenres_NoIssues_ReturnsResultGenre() throws Exception {
        Long oldId = 11L;
        Long newId = 22L;

        GenrePatchDTO patchDTO = new GenrePatchDTO();
        patchDTO.setOp("merge");
        patchDTO.setTargetGenreId(newId);

        Genre targetGenre = new Genre();
        targetGenre.setId(newId);
        targetGenre.setName("Comedy");

        String requestJson = convertToJson(patchDTO);

        doReturn(true).when(genreService).isGenreExist(oldId);
        doReturn(true).when(genreService).isGenreExist(newId);
        doReturn(targetGenre).when(genreService).mergeGenres(ArgumentMatchers.anyLong(), ArgumentMatchers.any());

        mockMvc.perform(patch(BASE_GENRES_PATH + "/" + oldId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.id", is(22)))
            .andExpect(jsonPath("$.name", is("Comedy")));
    }

    @Test
    public void MergeGenres_SourceGenreNotExists_ReturnsErrorResult() throws Exception {
        Long oldId = 11L;
        Long newId = 22L;

        GenrePatchDTO patchDTO = new GenrePatchDTO();
        patchDTO.setOp("merge");
        patchDTO.setTargetGenreId(newId);

        String requestJson = convertToJson(patchDTO);

        doReturn(false).when(genreService).isGenreExist(oldId);
        doReturn(true).when(genreService).isGenreExist(newId);

        mockMvc.perform(patch(BASE_GENRES_PATH + "/" + oldId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is("REQ001")))
            .andExpect(jsonPath("$.title", is("Not Found")))
            .andExpect(jsonPath("$.message", is("Genre not found: id=11")));
    }

    @Test
    public void MergeGenres_TargetGenreNotExists_ReturnsErrorResult() throws Exception {
        Long oldId = 11L;
        Long newId = 22L;

        GenrePatchDTO patchDTO = new GenrePatchDTO();
        patchDTO.setOp("merge");
        patchDTO.setTargetGenreId(newId);

        String requestJson = convertToJson(patchDTO);

        doReturn(true).when(genreService).isGenreExist(oldId);
        doReturn(false).when(genreService).isGenreExist(newId);

        mockMvc.perform(patch(BASE_GENRES_PATH + "/" + oldId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is("REQ001")))
            .andExpect(jsonPath("$.title", is("Not Found")))
            .andExpect(jsonPath("$.message", is("Genre not found: id=22")));
    }

    @Test
    public void PatchGenre_OperationNotSupported_ReturnsErrorResult() throws Exception {
        Long newId = 22L;

        GenrePatchDTO patchDTO = new GenrePatchDTO();
        patchDTO.setOp("rename");
        patchDTO.setTargetGenreId(newId);

        String requestJson = convertToJson(patchDTO);

        mockMvc.perform(patch(BASE_GENRES_PATH + "/" + 11)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is("REQ104")))
            .andExpect(jsonPath("$.title", is("Patch Operation Not Supported")))
            .andExpect(jsonPath("$.message", is("Operation 'rename' is not supported for Patch method on Genre resource.")));
    }

    private String convertToJson(GenreDTO postGenre) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(postGenre);
    }

    private String convertToJson(GenrePatchDTO genrePatchDTO) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(genrePatchDTO);
    }

}
