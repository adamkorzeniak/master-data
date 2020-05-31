package com.adamkorzeniak.masterdata.features.search.service;

import com.adamkorzeniak.masterdata.features.book.model.Book;
import com.adamkorzeniak.masterdata.features.book.model.BookTag;
import com.adamkorzeniak.masterdata.features.book.repository.BookRepository;
import com.adamkorzeniak.masterdata.features.book.repository.BookTagRepository;
import com.adamkorzeniak.masterdata.features.error.repository.ErrorRepository;
import com.adamkorzeniak.masterdata.features.location.model.Country;
import com.adamkorzeniak.masterdata.features.location.model.Place;
import com.adamkorzeniak.masterdata.features.location.repository.CountryRepository;
import com.adamkorzeniak.masterdata.features.location.repository.PlaceRepository;
import com.adamkorzeniak.masterdata.features.movie.model.Genre;
import com.adamkorzeniak.masterdata.features.movie.model.Movie;
import com.adamkorzeniak.masterdata.features.movie.repository.GenreRepository;
import com.adamkorzeniak.masterdata.features.movie.repository.MovieRepository;
import com.adamkorzeniak.masterdata.features.people.model.Person;
import com.adamkorzeniak.masterdata.features.people.repository.PersonRepository;
import com.adamkorzeniak.masterdata.features.product.model.Product;
import com.adamkorzeniak.masterdata.features.product.model.ProductTag;
import com.adamkorzeniak.masterdata.features.product.repository.ProductRepository;
import com.adamkorzeniak.masterdata.features.product.repository.ProductTagRepository;
import com.adamkorzeniak.masterdata.features.programming.model.ChecklistGroup;
import com.adamkorzeniak.masterdata.features.programming.repository.ChecklistRepository;
import com.adamkorzeniak.masterdata.persistence.SearchSpecification;
import com.adamkorzeniak.masterdata.features.search.model.SearchResult;
import com.adamkorzeniak.masterdata.features.travel.model.Luggage;
import com.adamkorzeniak.masterdata.features.travel.repository.LuggageRepository;
import com.adamkorzeniak.masterdata.features.error.model.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    private final Map<Class, JpaSpecificationExecutor> repositories;

    @Autowired
    public SearchServiceImpl(BookRepository bookRepository,
                             BookTagRepository bookTagRepository,
                             ErrorRepository errorRepository,
                             CountryRepository countryRepository,
                             PlaceRepository placeRepository,
                             MovieRepository movieRepository,
                             GenreRepository genreRepository,
                             PersonRepository personRepository,
                             ProductRepository productRepository,
                             ProductTagRepository productTagRepository,
                             ChecklistRepository checklistRepository,
                             LuggageRepository luggageRepository) {
        this.repositories = new HashMap<>();
        this.repositories.put(Book.class, bookRepository);
        this.repositories.put(BookTag.class, bookTagRepository);
        this.repositories.put(Error.class, errorRepository);
        this.repositories.put(Country.class, countryRepository);
        this.repositories.put(Place.class, placeRepository);
        this.repositories.put(Movie.class, movieRepository);
        this.repositories.put(Genre.class, genreRepository);
        this.repositories.put(Person.class, personRepository);
        this.repositories.put(Product.class, productRepository);
        this.repositories.put(ProductTag.class, productTagRepository);
        this.repositories.put(ChecklistGroup.class, checklistRepository);
        this.repositories.put(Luggage.class, luggageRepository);
    }

    @Override
    public List<SearchResult> searchAll(String searchQuery) {
        List<SearchResult> searchResults = new ArrayList<>();
        for (Map.Entry<Class, JpaSpecificationExecutor> entry : repositories.entrySet()) {
            List results = entry.getValue().findAll(new SearchSpecification<>(searchQuery, entry.getKey()));
            searchResults.addAll(buildResults(entry.getKey().getName(), results));
        }
        return searchResults;
    }

    private List<SearchResult> buildResults(String entityType, List<?> entityResults) {
        List<SearchResult> searchResults = new ArrayList<>();
        for (Object entity : entityResults) {
            searchResults.add(new SearchResult(entityType, entity));
        }
        return searchResults;
    }
}
