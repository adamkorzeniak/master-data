package com.adamkorzeniak.masterdata.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adamkorzeniak.masterdata.api.SearchFilterParam;
import com.adamkorzeniak.masterdata.api.SearchFilterService;
import com.adamkorzeniak.masterdata.api.SearchFunctionType;
import com.adamkorzeniak.masterdata.exception.exceptions.FieldFilterNotSupportedException;
import com.adamkorzeniak.masterdata.exception.exceptions.SearchFilterParamNotSupportedException;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest
public class SearchFilterServiceTest {

    @Autowired
    public Environment env;

    @Autowired
    private SearchFilterService searchFilterService;

    @Test
    public void BuildSearchFilters_NoIssue_ReturnsSearchFilters() {
        Map<String, String> params = new HashMap<>();
        params.put("match-title", "Titanic");
        params.put("search-description", "love");
        params.put("min-duration", "10");
        params.put("max-year", "2000");
        params.put("order-desc", "rating");
        params.put("order-asc", "watchPriority");
        params.put("exist-review", "false");

        List<SearchFilterParam> filters = searchFilterService.buildFilters(params, "movie.movies");

        assertThat(filters).isNotNull();
        assertThat(filters).hasSize(7);

    }

    @Test
    public void BuildMatchSearchFilter_NoIssue_ReturnsSearchFilter() {
        Map<String, String> params = new HashMap<>();
        params.put("match-title", "Titanic");

        List<SearchFilterParam> filters = searchFilterService.buildFilters(params, "movie.movies");

        assertThat(filters).isNotNull();
        assertThat(filters).hasSize(1);
        assertThat(filters.get(0).getFunction()).isEqualTo(SearchFunctionType.MATCH);
        assertThat(filters.get(0).getField()).isEqualTo("title");
        assertThat(filters.get(0).getValue()).isEqualTo("Titanic");

    }

    @Test
    public void BuildSearchSearchFilter_NoIssue_ReturnsSearchFilter() {
        Map<String, String> params = new HashMap<>();
        params.put("search-description", "love");

        List<SearchFilterParam> filters = searchFilterService.buildFilters(params, "movie.movies");

        assertThat(filters).isNotNull();
        assertThat(filters).hasSize(1);
        assertThat(filters.get(0).getFunction()).isEqualTo(SearchFunctionType.SEARCH);
        assertThat(filters.get(0).getField()).isEqualTo("description");
        assertThat(filters.get(0).getValue()).isEqualTo("love");

    }

    @Test
    public void BuildMinSearchFilter_NoIssue_ReturnsSearchFilter() {
        Map<String, String> params = new HashMap<>();
        params.put("min-duration", "10");

        List<SearchFilterParam> filters = searchFilterService.buildFilters(params, "movie.movies");

        assertThat(filters).isNotNull();
        assertThat(filters).hasSize(1);
        assertThat(filters.get(0).getFunction()).isEqualTo(SearchFunctionType.MIN);
        assertThat(filters.get(0).getField()).isEqualTo("duration");
        assertThat(filters.get(0).getValue()).isEqualTo("10");

    }

    @Test
    public void BuildMaxSearchFilter_NoIssue_ReturnsSearchFilter() {
        Map<String, String> params = new HashMap<>();
        params.put("max-year", "2000");

        List<SearchFilterParam> filters = searchFilterService.buildFilters(params, "movie.movies");

        assertThat(filters).isNotNull();
        assertThat(filters).hasSize(1);
        assertThat(filters.get(0).getFunction()).isEqualTo(SearchFunctionType.MAX);
        assertThat(filters.get(0).getField()).isEqualTo("year");
        assertThat(filters.get(0).getValue()).isEqualTo("2000");

    }

    @Test
    public void BuildExistSearchFilter_NoIssue_ReturnsSearchFilter() {
        Map<String, String> params = new HashMap<>();
        params.put("exist-review", "false");

        List<SearchFilterParam> filters = searchFilterService.buildFilters(params, "movie.movies");

        assertThat(filters).isNotNull();
        assertThat(filters).hasSize(1);
        assertThat(filters.get(0).getFunction()).isEqualTo(SearchFunctionType.EXIST);
        assertThat(filters.get(0).getField()).isEqualTo("review");
        assertThat(filters.get(0).getValue()).isEqualTo("false");

    }

    @Test
    public void BuildOrderAscSearchFilter_NoIssue_ReturnsSearchFilter() {
        Map<String, String> params = new HashMap<>();
        params.put("order-asc", "watchPriority");

        List<SearchFilterParam> filters = searchFilterService.buildFilters(params, "movie.movies");

        assertThat(filters).isNotNull();
        assertThat(filters).hasSize(1);
        assertThat(filters.get(0).getFunction()).isEqualTo(SearchFunctionType.ORDER_ASC);
        assertThat(filters.get(0).getField()).isEqualTo("watchPriority");
        assertThat(filters.get(0).getValue()).isNull();

    }

    @Test
    public void BuildOrderDescSearchFilter_NoIssue_ReturnsSearchFilter() {
        Map<String, String> params = new HashMap<>();
        params.put("order-desc", "rating");

        List<SearchFilterParam> filters = searchFilterService.buildFilters(params, "movie.movies");

        assertThat(filters).isNotNull();
        assertThat(filters).hasSize(1);
        assertThat(filters.get(0).getFunction()).isEqualTo(SearchFunctionType.ORDER_DESC);
        assertThat(filters.get(0).getField()).isEqualTo("rating");
        assertThat(filters.get(0).getValue()).isNull();

    }

    @Test
    public void BuildSearchFilters_FilterNotSupported_ThrowsException() {
        Map<String, String> params = new HashMap<>();
        params.put("max-id", "2000");

        assertThatExceptionOfType(SearchFilterParamNotSupportedException.class).isThrownBy(() -> searchFilterService.buildFilters(params, "movie.genres"))
            .withMessage("This resource doesn't support Max");

    }

    @Test
    public void BuildSearchFilters_FilterFieldNotSupported_ThrowsException() {
        Map<String, String> params = new HashMap<>();
        params.put("match-id", "2000");

        assertThatExceptionOfType(FieldFilterNotSupportedException.class).isThrownBy(() -> searchFilterService.buildFilters(params, "movie.genres"))
            .withMessage("This resource doesn't support Match for field: id");

    }

}
