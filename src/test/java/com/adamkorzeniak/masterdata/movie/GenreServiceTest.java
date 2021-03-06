package com.adamkorzeniak.masterdata.movie;

import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.movie.model.Genre;
import com.adamkorzeniak.masterdata.features.movie.model.Movie;
import com.adamkorzeniak.masterdata.features.movie.repository.GenreRepository;
import com.adamkorzeniak.masterdata.features.movie.repository.MovieRepository;
import com.adamkorzeniak.masterdata.features.movie.service.GenreService;
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

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest
public class GenreServiceTest {

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private MovieRepository movieRepository;

    @Autowired
    private GenreService genreService;

    @Test
    public void SearchGenres_NoIssues_ReturnsListOfGenres() {
        Map<String, String> params = new HashMap<>();
        params.put("search-name", "comedy");
        Genre comedy = new Genre();
        comedy.setName("Comedy");
        Genre darkComedy = new Genre();
        darkComedy.setName("Dark Comedy");
        List<Genre> genres = Arrays.asList(comedy, darkComedy);

        when(genreRepository.findAll(ArgumentMatchers.<Specification<Genre>>any())).thenReturn(genres);

        List<Genre> result = genreService.searchGenres(params);
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Comedy");
        assertThat(result.get(1).getName()).isEqualTo("Dark Comedy");

    }

    @Test
    public void FindGenreById_CorrectIdProvided_ReturnsOptionalOfGenre() {
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
    public void FindGenreById_WrongIdProvided_ReturnsEmptyOptional() {
        Long id = 1L;
        Optional<Genre> optional = Optional.empty();

        when(genreRepository.findById(id)).thenReturn(optional);

        Optional<Genre> result = genreService.findGenreById(id);

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void AddGenre_GenreValid_ReturnsCreatedGenre() {
        Long id = 1L;
        Genre genre = new Genre();
        genre.setName("Comedy");
        genre.setId(id);

        when(genreRepository.save(ArgumentMatchers.any())).thenAnswer(
            mockRepositorySaveInvocation(genre)
        );

        Genre result = genreService.addGenre(genre);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Comedy");
        assertThat(result.getId()).isEqualTo(100L);

    }

    @Test
    public void UpdateGenre_GenreValid_ReturnsUpdatedGenre() {
        Long id = 1L;
        Genre genre = new Genre();
        genre.setName("Comedy");
        genre.setId(id);

        when(genreRepository.save(ArgumentMatchers.any())).thenAnswer(
            mockRepositorySaveInvocation(genre)
        );

        Genre result = genreService.updateGenre(id, genre);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Comedy");
        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    public void DeleteGenre_GenreIdValid_DeletesGenre() {
        Long id = 1L;
        genreService.deleteGenre(id);
    }

    @Test
    public void IsGenreExist_GenreValid_ReturnsTrue() {
        Long id = 1L;
        when(genreRepository.existsById(id)).thenReturn(true);

        assertThat(genreService.isGenreExist(id)).isTrue();
    }

    @Test
    public void MergeGenres_MovieContainsBothGenres_OldGenreRemovedFromMovieAndReturnsTargerGenre() {
        Long sourceId = 11L;
        Long targetId = 22L;

        Genre sourceGenre = new Genre();
        sourceGenre.setId(sourceId);
        sourceGenre.setName("Comedy");

        Genre targetGenre = new Genre();
        targetGenre.setId(targetId);
        targetGenre.setName("Drama");

        Movie movie = new Movie();
        movie.setTitle("Titanic");
        movie.setDuration(100);
        movie.setYear(2000);
        movie.setGenres(new ArrayList<>(Arrays.asList(sourceGenre, targetGenre)));

        when(genreRepository.findById(sourceId)).thenReturn(Optional.of(sourceGenre));
        when(genreRepository.findById(targetId)).thenReturn(Optional.of(targetGenre));
        when(movieRepository.findByGenresContaining(sourceGenre)).thenReturn(Collections.singletonList(movie));

        Genre result = genreService.mergeGenres(sourceId, targetId);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Drama");
        assertThat(result.getId()).isEqualTo(targetId);

        assertThat(movie.getGenres()).hasSize(1);
        assertThat(movie.getGenres().get(0)).isNotNull();
        assertThat(movie.getGenres().get(0).getId()).isEqualTo(targetId);
        assertThat(movie.getGenres().get(0).getName()).isEqualTo("Drama");
    }

    @Test
    public void MergeGenres_MovieContainsSourceGenre_GenresReplacedAndReturnsTargerGenre() {
        Long sourceId = 11L;
        Long targetId = 22L;

        Genre sourceGenre = new Genre();
        sourceGenre.setId(sourceId);
        sourceGenre.setName("Comedy");

        Genre targetGenre = new Genre();
        targetGenre.setId(targetId);
        targetGenre.setName("Drama");

        Movie movie = new Movie();
        movie.setTitle("Titanic");
        movie.setDuration(100);
        movie.setYear(2000);
        movie.setGenres(Collections.singletonList(sourceGenre));

        when(genreRepository.findById(sourceId)).thenReturn(Optional.of(sourceGenre));
        when(genreRepository.findById(targetId)).thenReturn(Optional.of(targetGenre));
        when(movieRepository.findByGenresContaining(sourceGenre)).thenReturn(Collections.singletonList(movie));

        Genre result = genreService.mergeGenres(sourceId, targetId);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Drama");
        assertThat(result.getId()).isEqualTo(targetId);

        assertThat(movie.getGenres()).hasSize(1);
        assertThat(movie.getGenres().get(0)).isNotNull();
        assertThat(movie.getGenres().get(0).getId()).isEqualTo(targetId);
        assertThat(movie.getGenres().get(0).getName()).isEqualTo("Drama");
    }

    @Test
    public void MergeGenres_MovieContainsSourceGenre_OldGenreRemovedFromMovieAndReturnsTargerGenre() {
        Long sourceId = 11L;
        Long targetId = 22L;

        Genre sourceGenre = new Genre();
        sourceGenre.setId(sourceId);
        sourceGenre.setName("Comedy");

        Genre targetGenre = new Genre();
        targetGenre.setId(targetId);
        targetGenre.setName("Drama");

        Movie movie = new Movie();
        movie.setTitle("Titanic");
        movie.setDuration(100);
        movie.setYear(2000);
        movie.setGenres(Collections.singletonList(sourceGenre));

        when(genreRepository.findById(sourceId)).thenReturn(Optional.of(sourceGenre));
        when(genreRepository.findById(targetId)).thenReturn(Optional.of(targetGenre));
        when(movieRepository.findByGenresContaining(sourceGenre)).thenReturn(Collections.singletonList(movie));

        Genre result = genreService.mergeGenres(sourceId, targetId);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Drama");
        assertThat(result.getId()).isEqualTo(targetId);

        assertThat(movie.getGenres()).hasSize(1);
        assertThat(movie.getGenres().get(0)).isNotNull();
        assertThat(movie.getGenres().get(0).getId()).isEqualTo(targetId);
        assertThat(movie.getGenres().get(0).getName()).isEqualTo("Drama");
    }

    @Test
    public void MergeGenres_SourceGenreNotExists_ThrowsException() {
        Long sourceId = 11L;
        Long targetId = 22L;


        Genre genre = new Genre();
        genre.setId(targetId);
        genre.setName("Comedy");

        when(genreRepository.findById(sourceId)).thenReturn(Optional.empty());
        when(genreRepository.findById(targetId)).thenReturn(Optional.of(genre));


        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> genreService.mergeGenres(sourceId, targetId))
            .withMessage("Genre not found: id=11");
    }

    @Test
    public void MergeGenres_TargetGenreNotExists_ThrowsException() {
        Long sourceId = 11L;
        Long targetId = 22L;

        Genre genre = new Genre();
        genre.setId(sourceId);
        genre.setName("Comedy");

        when(genreRepository.findById(sourceId)).thenReturn(Optional.of(genre));
        when(genreRepository.findById(targetId)).thenReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> genreService.mergeGenres(sourceId, targetId))
            .withMessage("Genre not found: id=22");
    }

    private Answer<?> mockRepositorySaveInvocation(Genre genre) {
        return invocation -> {
            Object argument = invocation.getArguments()[0];
            Genre receivedGenre = (Genre) argument;
            if (receivedGenre.getId() >= 0) {
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
