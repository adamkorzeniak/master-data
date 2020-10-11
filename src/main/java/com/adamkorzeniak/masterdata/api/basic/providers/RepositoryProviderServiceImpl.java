package com.adamkorzeniak.masterdata.api.basic.providers;

import com.adamkorzeniak.masterdata.entity.DatabaseEntity;
import com.adamkorzeniak.masterdata.entity.astronomy.model.*;
import com.adamkorzeniak.masterdata.entity.astronomy.model.SkyObject;
import com.adamkorzeniak.masterdata.entity.astronomy.repository.*;
import com.adamkorzeniak.masterdata.entity.book.model.Book;
import com.adamkorzeniak.masterdata.entity.book.model.BookTag;
import com.adamkorzeniak.masterdata.entity.book.repository.BookRepository;
import com.adamkorzeniak.masterdata.entity.book.repository.BookTagRepository;
import com.adamkorzeniak.masterdata.entity.diet.model.DietProduct;
import com.adamkorzeniak.masterdata.entity.diet.model.Recipe;
import com.adamkorzeniak.masterdata.entity.diet.model.RecipeTag;
import com.adamkorzeniak.masterdata.entity.diet.repository.DietProductRepository;
import com.adamkorzeniak.masterdata.entity.diet.repository.RecipeRepository;
import com.adamkorzeniak.masterdata.entity.diet.repository.RecipeTagRepository;
import com.adamkorzeniak.masterdata.entity.error.model.Error;
import com.adamkorzeniak.masterdata.entity.error.repository.ErrorRepository;
import com.adamkorzeniak.masterdata.entity.location.model.Country;
import com.adamkorzeniak.masterdata.entity.location.model.Place;
import com.adamkorzeniak.masterdata.entity.location.repository.CountryRepository;
import com.adamkorzeniak.masterdata.entity.location.repository.PlaceRepository;
import com.adamkorzeniak.masterdata.entity.management.model.RoutineGroup;
import com.adamkorzeniak.masterdata.entity.management.model.RoutineItem;
import com.adamkorzeniak.masterdata.entity.management.repository.RoutineGroupRepository;
import com.adamkorzeniak.masterdata.entity.management.repository.RoutineItemRepository;
import com.adamkorzeniak.masterdata.entity.movie.model.Genre;
import com.adamkorzeniak.masterdata.entity.movie.model.Movie;
import com.adamkorzeniak.masterdata.entity.movie.repository.GenreRepository;
import com.adamkorzeniak.masterdata.entity.movie.repository.MovieRepository;
import com.adamkorzeniak.masterdata.entity.people.model.Person;
import com.adamkorzeniak.masterdata.entity.people.repository.PersonRepository;
import com.adamkorzeniak.masterdata.entity.personal.model.DiaryItem;
import com.adamkorzeniak.masterdata.entity.personal.repository.DiaryItemRepository;
import com.adamkorzeniak.masterdata.entity.product.model.Product;
import com.adamkorzeniak.masterdata.entity.product.model.ProductTag;
import com.adamkorzeniak.masterdata.entity.product.repository.ProductRepository;
import com.adamkorzeniak.masterdata.entity.product.repository.ProductTagRepository;
import com.adamkorzeniak.masterdata.entity.programming.model.ChecklistGroup;
import com.adamkorzeniak.masterdata.entity.programming.model.ChecklistItem;
import com.adamkorzeniak.masterdata.entity.programming.model.IntellijShortcut;
import com.adamkorzeniak.masterdata.entity.programming.model.IntellijTip;
import com.adamkorzeniak.masterdata.entity.programming.repository.ChecklistGroupRepository;
import com.adamkorzeniak.masterdata.entity.programming.repository.ChecklistItemRepository;
import com.adamkorzeniak.masterdata.entity.programming.repository.IntellijShortcutRepository;
import com.adamkorzeniak.masterdata.entity.programming.repository.IntellijTipRepository;
import com.adamkorzeniak.masterdata.entity.travel.model.Luggage;
import com.adamkorzeniak.masterdata.entity.travel.repository.LuggageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RepositoryProviderServiceImpl implements RepositoryProviderService {

    private final Map<Class<? extends DatabaseEntity>, java.lang.Object> repositories;

    @Autowired
    public RepositoryProviderServiceImpl(
            //ASTRONOMY
            LocationRepository locationRepository,
            ObjectRepository objectRepository,
            ObservationRepository observationRepository,
            ObservationPlanRepository observationPlanRepository,
            SourceRepository sourceRepository,
            //BOOK
            BookRepository bookRepository,
            BookTagRepository bookTagRepository,
            //DIET
            DietProductRepository dietProductRepository,
            RecipeRepository recipeRepository,
            RecipeTagRepository recipeTagRepository,
            //ERROR
            ErrorRepository errorRepository,
            //LOCATION
            CountryRepository countryRepository,
            PlaceRepository placeRepository,
            //MANAGEMENT
            RoutineGroupRepository routineGroupRepository,
            RoutineItemRepository routineItemRepository,
            //MOVIE
            GenreRepository genreRepository,
            MovieRepository movieRepository,
            //PEOPLE
            PersonRepository personRepository,
            //PERSONAL
            DiaryItemRepository diaryItemRepository,
            //PRODUCT
            ProductRepository productRepository,
            ProductTagRepository productTagRepository,
            //PROGRAMMING
            ChecklistGroupRepository checklistGroupRepository,
            ChecklistItemRepository checklistItemRepository,
            IntellijShortcutRepository intellijShortcutRepository,
            IntellijTipRepository intellijTipRepository,
            //TRAVEL
            LuggageRepository luggageRepository
    ) {
        this.repositories = new HashMap<>();
        //ASTRONOMY
        this.repositories.put(Location.class, locationRepository);
        this.repositories.put(SkyObject.class, objectRepository);
        this.repositories.put(Observation.class, observationRepository);
        this.repositories.put(ObservationPlan.class, observationPlanRepository);
        this.repositories.put(Source.class, sourceRepository);
        //BOOK
        this.repositories.put(Book.class, bookRepository);
        this.repositories.put(BookTag.class, bookTagRepository);
        //DIET
        this.repositories.put(DietProduct.class, dietProductRepository);
        this.repositories.put(Recipe.class, recipeRepository);
        this.repositories.put(RecipeTag.class, recipeTagRepository);
        //ERROR
        this.repositories.put(Error.class, errorRepository);
        //LOCATION
        this.repositories.put(Country.class, countryRepository);
        this.repositories.put(Place.class, placeRepository);
        //MANAGEMENT
        this.repositories.put(RoutineGroup.class, routineGroupRepository);
        this.repositories.put(RoutineItem.class, routineItemRepository);
        //MOVIE
        this.repositories.put(Genre.class, genreRepository);
        this.repositories.put(Movie.class, movieRepository);
        //PEOPLE
        this.repositories.put(Person.class, personRepository);
        //PERSONAL
        this.repositories.put(DiaryItem.class, diaryItemRepository);
        //PRODUCT
        this.repositories.put(Product.class, productRepository);
        this.repositories.put(ProductTag.class, productTagRepository);
        //PROGRAMMING
        this.repositories.put(ChecklistGroup.class, checklistGroupRepository);
        this.repositories.put(ChecklistItem.class, checklistItemRepository);
        this.repositories.put(IntellijShortcut.class, intellijShortcutRepository);
        this.repositories.put(IntellijTip.class, intellijTipRepository);
        //TRAVEL
        this.repositories.put(Luggage.class, luggageRepository);
    }

    @Override
    public JpaSpecificationExecutor<? extends DatabaseEntity> getExecutor(Class<? extends DatabaseEntity> modelClass) {
        return (JpaSpecificationExecutor<? extends DatabaseEntity>) this.repositories.get(modelClass);
    }

    @Override
    public JpaRepository<? extends DatabaseEntity, Long> getRepository(Class<? extends DatabaseEntity> modelClass) {
        return (JpaRepository<? extends DatabaseEntity, Long>) this.repositories.get(modelClass);
    }

    @Override
    public Map<Class<? extends DatabaseEntity>, JpaSpecificationExecutor<? extends DatabaseEntity>> getAllRepositories() {
        return this.repositories.entrySet().stream()
                .collect(
                        Collectors.toMap(
                                entry -> entry.getKey(),
                                entry -> (JpaSpecificationExecutor<? extends DatabaseEntity>) entry.getValue()
                        )
                );
    }
}
