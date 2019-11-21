package com.adamkorzeniak.masterdata.api;

import com.adamkorzeniak.masterdata.exception.exceptions.SearchFilterParamNotSupportedException;
import com.adamkorzeniak.masterdata.exception.exceptions.SearchFilterParamsNotInitializedException;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Specification implementation supporting following filters
 * Search, Match, Min, Max, Exist, Order
 */
public class GenericSpecification<T> implements Specification<T> {

    private static final long serialVersionUID = 2286342570890056988L;

    private final List<SearchFilterParam> filters;

    /**
     * Creates specification with Search Params list.
     * If list is null throws Runtime Exception.
     */
    public GenericSpecification(List<SearchFilterParam> filters) {
        if (filters == null) {
            throw new SearchFilterParamsNotInitializedException();
        }
        this.filters = filters;
    }

    /**
     * Creates a predicate for Specification.
     */
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

        Predicate p = cb.conjunction();

        for (SearchFilterParam filter : filters) {
            switch (filter.getFunction()) {
                case SEARCH:
                    p.getExpressions().add(cb.like(root.get(filter.getField()), "%" + filter.getValue() + "%"));
                    break;
                case MATCH:
                    p.getExpressions().add(cb.equal(root.get(filter.getField()), filter.getValue()));
                    break;
                case MIN:
                    p.getExpressions().add(cb.ge(root.get(filter.getField()), Double.parseDouble(filter.getValue())));
                    break;
                case MAX:
                    p.getExpressions().add(cb.le(root.get(filter.getField()), Double.parseDouble(filter.getValue())));
                    break;
                case EXIST:
                    boolean exist = Boolean.parseBoolean(filter.getValue());
                    if (exist) {
                        p.getExpressions().add(cb.isNotNull(root.get(filter.getField())));
                    } else {
                        p.getExpressions().add(cb.isNull(root.get(filter.getField())));
                    }
                    break;
                case ORDER_ASC:
                    cq.orderBy(cb.asc(root.get(filter.getField())));
                    break;
                case ORDER_DESC:
                    cq.orderBy(cb.desc(root.get(filter.getField())));
                    break;
                default:
                    throw new SearchFilterParamNotSupportedException(filter.getFunction());
            }
        }
        return p;
    }
}