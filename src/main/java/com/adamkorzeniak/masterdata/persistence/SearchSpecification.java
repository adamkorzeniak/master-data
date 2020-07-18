package com.adamkorzeniak.masterdata.persistence;

import com.adamkorzeniak.masterdata.exception.exceptions.NotSupportedSearchFieldClassException;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class SearchSpecification<T> implements Specification<T> {

    private static final long serialVersionUID = 2286342570890056988L;

    private static final List<Class> basicFields = Arrays.asList(Long.class, Integer.class, String.class, Double.class, LocalDateTime.class, LocalDate.class);
    private static final List<Class> collectionFields = Arrays.asList(List.class, Set.class);
    private static final String PACKAGE_NAME_PREFIX = "com.adamkorzeniak.masterdata";

    private final String searchValue;
    private final Class classType;

    public SearchSpecification(String searchValue, Class classType) {
        this.searchValue = searchValue;
        this.classType = classType;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        Field[] fields = classType.getDeclaredFields();
        for (Field field : fields) {
            predicates.addAll(buildPredicates(cb, root, field));
        }

        Predicate[] predicatesArray = new Predicate[predicates.size()];
        predicates.toArray(predicatesArray);
        return cb.or(predicatesArray);
    }

    private List<Predicate> buildPredicates(CriteriaBuilder cb, From<T, ?> table, Field field) {
        Class<?> fieldClass = field.getType();
        if (collectionFields.contains(fieldClass)) {
            ParameterizedType listType = (ParameterizedType) field.getGenericType();
            fieldClass = (Class<?>) listType.getActualTypeArguments()[0];
        }

        List<Predicate> predicates = new ArrayList<>();
        if (basicFields.contains(fieldClass)) {
            if (fieldClass.equals(String.class)) {
                predicates.add(buildBasicPredicate(cb, table, field));
            }
        } else if (fieldClass.getPackageName().startsWith(PACKAGE_NAME_PREFIX)) {
            for (Field objectField : fieldClass.getDeclaredFields()) {
                Join<T, ?> join = table.join(field.getName(), JoinType.LEFT);
                predicates.addAll(buildPredicates(cb, join, objectField));
            }
        } else {
            throw new NotSupportedSearchFieldClassException(fieldClass);
        }
        return predicates;
    }

    private Predicate buildBasicPredicate(CriteriaBuilder cb, From<T, ?> table, Field field) {
        return cb.like(table.get(field.getName()), "%" + searchValue + "%");
    }
}