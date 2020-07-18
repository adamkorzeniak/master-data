package com.adamkorzeniak.masterdata.api.common.providers;

import com.adamkorzeniak.masterdata.entity.DatabaseEntity;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.entity.astronomy.model.*;
import com.adamkorzeniak.masterdata.entity.astronomy.model.Object;
import com.adamkorzeniak.masterdata.entity.book.model.Book;
import com.adamkorzeniak.masterdata.entity.book.model.BookTag;
import com.adamkorzeniak.masterdata.entity.diet.model.DietProduct;
import com.adamkorzeniak.masterdata.entity.diet.model.Recipe;
import com.adamkorzeniak.masterdata.entity.diet.model.RecipeTag;
import com.adamkorzeniak.masterdata.entity.error.model.Error;
import com.adamkorzeniak.masterdata.entity.location.model.Country;
import com.adamkorzeniak.masterdata.entity.location.model.Place;
import com.adamkorzeniak.masterdata.entity.management.model.RoutineGroup;
import com.adamkorzeniak.masterdata.entity.management.model.RoutineItem;
import com.adamkorzeniak.masterdata.entity.movie.model.Genre;
import com.adamkorzeniak.masterdata.entity.movie.model.Movie;
import com.adamkorzeniak.masterdata.entity.people.model.Person;
import com.adamkorzeniak.masterdata.entity.personal.model.DiaryItem;
import com.adamkorzeniak.masterdata.entity.product.model.Product;
import com.adamkorzeniak.masterdata.entity.product.model.ProductTag;
import com.adamkorzeniak.masterdata.entity.programming.model.ChecklistGroup;
import com.adamkorzeniak.masterdata.entity.programming.model.ChecklistItem;
import com.adamkorzeniak.masterdata.entity.programming.model.IntellijShortcut;
import com.adamkorzeniak.masterdata.entity.programming.model.IntellijTip;
import com.adamkorzeniak.masterdata.entity.travel.model.Luggage;
import org.springframework.stereotype.Service;

@Service
public class ResourceClassProviderImpl implements ResourceClassProvider {

    private static final String ASTRONOMY_FEATURE = "Astronomy";
    private static final String ASTRONOMY_LOCATION_RESOURCE = "locations";
    private static final String ASTRONOMY_OBJECT_RESOURCE = "objects";
    private static final String ASTRONOMY_OBSERVATION_RESOURCE = "observations";
    private static final String ASTRONOMY_OBSERVATION_PLAN_RESOURCE = "plans";
    private static final String ASTRONOMY_SOURCE_RESOURCE = "sources";

    private static final String BOOK_FEATURE = "Book";
    private static final String BOOK_RESOURCE = "books";
    private static final String BOOK_TAG_RESOURCE = "bookTags";

    private static final String DIET_FEATURE = "Diet";
    private static final String DIET_PRODUCT_RESOURCE = "products";
    private static final String DIET_RECIPE_RESOURCE = "recipes";
    private static final String DIET_RECIPE_TAG_RESOURCE = "recipeTags";

    private static final String ERROR_FEATURE = "Error";
    private static final String ERROR_RESOURCE = "errors";

    private static final String LOCATION_FEATURE = "Location";
    private static final String LOCATION_COUNTRY_RESOURCE = "countries";
    private static final String LOCATION_PLACE_RESOURCE = "places";
    private static final String MANAGEMENT_FEATURE = "Management";
    private static final String ROUTINE_GROUP_RESOURCE = "routineGroups";
    private static final String ROUTINE_ITEM_RESOURCE = "routineItems";

    private static final String MOVIE_FEATURE = "Movie";
    private static final String MOVIE_RESOURCE = "movies";
    private static final String MOVIE_GENRE_RESOURCE = "genres";

    private static final String PEOPLE_FEATURE = "People";
    private static final String PERSON_RESOURCE = "people";

    private static final String PERSONAL_FEATURE = "Personal";
    private static final String DIARY_ITEM = "diaryItems";

    private static final String PRODUCT_FEATURE = "Product";
    private static final String PRODUCT_RESOURCE = "products";
    private static final String PRODUCT_TAG_RESOURCE = "productTags";

    private static final String PROGRAMMING_FEATURE = "Programming";
    private static final String PROGRAMMING_CHECKLIST_GROUP_RESOURCE = "checklistGroups";
    private static final String PROGRAMMING_CHECKLIST_ITEM_RESOURCE = "checklistItems";
    private static final String PROGRAMMING_INTELLIJ_SHORTCUT = "intellijShortcuts";
    private static final String PROGRAMMING_INTELLIJ_TIP_RESOURCE = "intellijTips";

    private static final String TRAVEL_FEATURE = "Travel";
    private static final String LUGGAGE_RESOURCE = "luggage";


    @Override
    public Class<? extends DatabaseEntity> getClass(String feature, String resource) {
        if (ASTRONOMY_FEATURE.equals(feature)) {
            if (ASTRONOMY_LOCATION_RESOURCE.equals(resource)) {
                return Location.class;
            }
            if (ASTRONOMY_OBJECT_RESOURCE.equals(resource)) {
                return Object.class;
            }
            if (ASTRONOMY_OBSERVATION_RESOURCE.equals(resource)) {
                return Observation.class;
            }
            if (ASTRONOMY_OBSERVATION_PLAN_RESOURCE.equals(resource)) {
                return ObservationPlan.class;
            }
            if (ASTRONOMY_SOURCE_RESOURCE.equals(resource)) {
                return Source.class;
            }
        }
        if (BOOK_FEATURE.equals(feature)) {
            if (BOOK_RESOURCE.equals(resource)) {
                return Book.class;
            }
            if (BOOK_TAG_RESOURCE.equals(resource)) {
                return BookTag.class;
            }
        }
        if (DIET_FEATURE.equals(feature)) {
            if (DIET_PRODUCT_RESOURCE.equals(resource)) {
                return DietProduct.class;
            }
            if (DIET_RECIPE_RESOURCE.equals(resource)) {
                return Recipe.class;
            }
            if (DIET_RECIPE_TAG_RESOURCE.equals(resource)) {
                return RecipeTag.class;
            }
        }
        if (ERROR_FEATURE.equals(feature)) {
            if (ERROR_RESOURCE.equals(resource)) {
                return Error.class;
            }
        }
        if (LOCATION_FEATURE.equals(feature)) {
            if (LOCATION_COUNTRY_RESOURCE.equals(resource)) {
                return Country.class;
            }
            if (LOCATION_PLACE_RESOURCE.equals(resource)) {
                return Place.class;
            }
        }
        if (MANAGEMENT_FEATURE.equals(feature)) {
            if (ROUTINE_GROUP_RESOURCE.equals(resource)) {
                return RoutineGroup.class;
            }
            if (ROUTINE_ITEM_RESOURCE.equals(resource)) {
                return RoutineItem.class;
            }
        }
        if (MOVIE_FEATURE.equals(feature)) {
            if (MOVIE_RESOURCE.equals(resource)) {
                return Movie.class;
            }
            if (MOVIE_GENRE_RESOURCE.equals(resource)) {
                return Genre.class;
            }
        }
        if (PEOPLE_FEATURE.equals(feature)) {
            if (PERSON_RESOURCE.equals(resource)) {
                return Person.class;
            }
        }
        if (PERSONAL_FEATURE.equals(feature)) {
            if (DIARY_ITEM.equals(resource)) {
                return DiaryItem.class;
            }
        }
        if (PRODUCT_FEATURE.equals(feature)) {
            if (PRODUCT_RESOURCE.equals(resource)) {
                return Product.class;
            }
            if (PRODUCT_TAG_RESOURCE.equals(resource)) {
                return ProductTag.class;
            }
        }
        if (PROGRAMMING_FEATURE.equals(feature)) {
            if (PROGRAMMING_CHECKLIST_GROUP_RESOURCE.equals(resource)) {
                return ChecklistGroup.class;
            }
            if (PROGRAMMING_CHECKLIST_ITEM_RESOURCE.equals(resource)) {
                return ChecklistItem.class;
            }
            if (PROGRAMMING_INTELLIJ_SHORTCUT.equals(resource)) {
                return IntellijShortcut.class;
            }
            if (PROGRAMMING_INTELLIJ_TIP_RESOURCE.equals(resource)) {
                return IntellijTip.class;
            }
        }
        if (TRAVEL_FEATURE.equals(feature)) {
            if (LUGGAGE_RESOURCE.equals(resource)) {
                return Luggage.class;
            }
        }
        throw new NotFoundException(feature, resource);
    }
}
