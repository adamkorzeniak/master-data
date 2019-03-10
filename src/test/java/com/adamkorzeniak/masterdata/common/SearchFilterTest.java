package com.adamkorzeniak.masterdata.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adamkorzeniak.masterdata.common.api.SearchFilter;
import com.adamkorzeniak.masterdata.common.api.SearchFunctionType;
import com.adamkorzeniak.masterdata.exception.InvalidQueryParamException;
import com.adamkorzeniak.masterdata.exception.InvalidQueryParamValueException;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest
public class SearchFilterTest {

	@Test
	public void CreateSearchTypeFilter_NoIssues_FilterCreated() throws Exception {
		SearchFilter filter = new SearchFilter("search-title", "Titanic");
		
		assertThat(filter).isNotNull();
		assertThat(filter.getFunction()).isEqualTo(SearchFunctionType.SEARCH);
		assertThat(filter.getField()).isEqualTo("title");
		assertThat(filter.getValue()).isEqualTo("Titanic");
	}	
	
	@Test
	public void CreateMatchTypeFilter_NoIssues_FilterCreated() throws Exception {
		SearchFilter filter = new SearchFilter("match-title", "Inception");
		
		assertThat(filter).isNotNull();
		assertThat(filter.getFunction()).isEqualTo(SearchFunctionType.MATCH);
		assertThat(filter.getField()).isEqualTo("title");
		assertThat(filter.getValue()).isEqualTo("Inception");
	}	
	
	@Test
	public void CreateMinTypeFilter_NoIssues_FilterCreated() throws Exception {
		SearchFilter filter = new SearchFilter("min-year", "2000");
		
		assertThat(filter).isNotNull();
		assertThat(filter.getFunction()).isEqualTo(SearchFunctionType.MIN);
		assertThat(filter.getField()).isEqualTo("year");
		assertThat(filter.getValue()).isEqualTo("2000");
	}	
	
	@Test
	public void CreateMaxTypeFilter_NoIssues_FilterCreated() throws Exception {
		SearchFilter filter = new SearchFilter("max-duration", "100");
		
		assertThat(filter).isNotNull();
		assertThat(filter.getFunction()).isEqualTo(SearchFunctionType.MAX);
		assertThat(filter.getField()).isEqualTo("duration");
		assertThat(filter.getValue()).isEqualTo("100");
	}	
	
	@Test
	public void CreateExistTypeFilter_NoIssues_FilterCreated() throws Exception {
		SearchFilter filter = new SearchFilter("exist-description", "true");
		
		assertThat(filter).isNotNull();
		assertThat(filter.getFunction()).isEqualTo(SearchFunctionType.EXIST);
		assertThat(filter.getField()).isEqualTo("description");
		assertThat(filter.getValue()).isEqualTo("true");
	}	
	
	@Test
	public void CreateOrderDescTypeFilter_NoIssues_FilterCreated() throws Exception {
		SearchFilter filter = new SearchFilter("order-desc", "year");
		
		assertThat(filter).isNotNull();
		assertThat(filter.getFunction()).isEqualTo(SearchFunctionType.ORDER_DESC);
		assertThat(filter.getField()).isEqualTo("year");
		assertThat(filter.getValue()).isNull();
	}	
	
	@Test
	public void CreateOrderAscTypeFilter_NoIssues_FilterCreated() throws Exception {
		SearchFilter filter = new SearchFilter("order-asc", "duration");
		
		assertThat(filter).isNotNull();
		assertThat(filter.getFunction()).isEqualTo(SearchFunctionType.ORDER_ASC);
		assertThat(filter.getField()).isEqualTo("duration");
		assertThat(filter.getValue()).isNull();
	}	
	
	@Test
	public void CreateOrderTypeFilter_NoIssues_FilterCreated() throws Exception {
		SearchFilter filter = new SearchFilter("order", "title");
		
		assertThat(filter).isNotNull();
		assertThat(filter.getFunction()).isEqualTo(SearchFunctionType.ORDER_ASC);
		assertThat(filter.getField()).isEqualTo("title");
		assertThat(filter.getValue()).isNull();
	}	
	
	@Test
	public void CreateAnyOfTypeFilter_InvalidQueryParam_ExceptionThrown() throws Exception {
		
		assertThatExceptionOfType(InvalidQueryParamException.class).isThrownBy(() -> { 
			new SearchFilter("anyof", "duration"); })
        .withMessage("Invalid query param: anyof");
	}	
	
	@Test
	public void CreateExistTypeFilter_InvalidQueryParam_ExceptionThrown() throws Exception {
		
		assertThatExceptionOfType(InvalidQueryParamException.class).isThrownBy(() -> { 
			new SearchFilter("exist", "duration"); })
        .withMessage("Invalid query param: exist");
	}		
	
	@Test
	public void CreateMinTypeFilter_InvalidQueryParam_ExceptionThrown() throws Exception {
		
		assertThatExceptionOfType(InvalidQueryParamException.class).isThrownBy(() -> { 
			new SearchFilter("min", "duration"); })
        .withMessage("Invalid query param: min");
	}		
	
	@Test
	public void CreateSearchTypeFilter_InvalidQueryParam_ExceptionThrown() throws Exception {
		
		assertThatExceptionOfType(InvalidQueryParamException.class).isThrownBy(() -> { 
			new SearchFilter("search", "duration"); })
        .withMessage("Invalid query param: search");
	}		
	
	@Test
	public void CreateOrderTypeFilter_InvalidQueryParam_ExceptionThrown() throws Exception {
		
		assertThatExceptionOfType(InvalidQueryParamException.class).isThrownBy(() -> { 
			new SearchFilter("order-asc-desc", "duration"); })
        .withMessage("Invalid query param: order-asc-desc");
	}
	
	@Test
	public void CreateExistTypeFilter_InvalidBooleanValue_ExceptionThrown() throws Exception {
		
		assertThatExceptionOfType(InvalidQueryParamValueException.class).isThrownBy(() -> { 
			new SearchFilter("exist-rating", "duration"); })
        .withMessage("Invalid query param value for 'exist-rating'. Exist supports only boolean values.");
	}
	
	@Test
	public void CreateMinTypeFilter_InvalidNumericValue_ExceptionThrown() throws Exception {
		
		assertThatExceptionOfType(InvalidQueryParamValueException.class).isThrownBy(() -> { 
			new SearchFilter("min-rating", "ten"); })
        .withMessage("Invalid query param value for 'min-rating'. Min supports only numeric values.");
	}
	
	@Test
	public void CreateOrderAlphabeticalTypeFilter_InvalidOrderTypeValue_ExceptionThrown() throws Exception {
		
		assertThatExceptionOfType(InvalidQueryParamException.class).isThrownBy(() -> { 
			new SearchFilter("order-alphabetically", "duration"); })
        .withMessage("Invalid query param: order-alphabetically");
	}

}
