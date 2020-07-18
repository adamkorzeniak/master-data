package com.adamkorzeniak.masterdata.persistence;

import com.adamkorzeniak.masterdata.api.basic.query.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.basic.query.filter.FilterExpressionToken;
import com.adamkorzeniak.masterdata.api.basic.query.filter.FilterExpressionTokenType;
import com.adamkorzeniak.masterdata.api.basic.query.order.OrderExpression;
import com.adamkorzeniak.masterdata.api.basic.query.order.OrderExpressionToken;
import com.adamkorzeniak.masterdata.api.basic.query.order.OrderExpressionTokenType;
import com.adamkorzeniak.masterdata.exception.exceptions.SQLExpressionUnexpectedTypeException;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.Map;

public class GenericSpecification<T> implements Specification<T> {

    private final transient FilterExpression filtersExpression;
    private final transient OrderExpression orderExpression;

    public GenericSpecification(FilterExpression filtersExpression, OrderExpression orderExpression) {
        if (filtersExpression == null) {
            filtersExpression = new FilterExpression();
        }
        this.filtersExpression = filtersExpression;
        this.orderExpression = orderExpression;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

        Predicate p = cb.conjunction();
        Map<String, Join<T, ?>> joinMap = new HashMap<>();

        for (FilterExpressionToken filterExpressionToken : filtersExpression.getFilters()) {

            FilterExpressionTokenType type = filterExpressionToken.getType();
            String field = filterExpressionToken.getField();
            String value = filterExpressionToken.getValue();

            From<T, ?> table = root;
            String column = field;

            if (field.contains(".")) {
                joinMap.putAll(buildJoins(root, field));
                table = joinMap.get(field.split("\\.")[0]);
                column = field.split("\\.")[1];
            }

            switch (type) {
                case EQUALS:
                    p.getExpressions().add(cb.equal(table.get(column), value));
                    break;
                case NOT_EQUALS:
                    p.getExpressions().add(cb.notEqual(table.get(column), value));
                    break;
                case LESS:
                    p.getExpressions().add(cb.lessThan(table.get(column), value));
                    break;
                case LESS_OR_EQUAL:
                    p.getExpressions().add(cb.lessThanOrEqualTo(table.get(column), value));
                    break;
                case GREATER:
                    p.getExpressions().add(cb.greaterThan(table.get(column), value));
                    break;
                case GREATER_OR_EQUAL:
                    p.getExpressions().add(cb.greaterThanOrEqualTo(table.get(column), value));
                    break;
                case LIKE:
                    p.getExpressions().add(cb.like(table.get(column), "%" + value + "%"));
                    break;
                case NOT_LIKE:
                    p.getExpressions().add(cb.notLike(table.get(column), "%" + value + "%"));
                    break;
                case IS_NULL:
                    p.getExpressions().add(cb.isNull(table.get(column)));
                    break;
                case IS_NOT_NULL:
                    p.getExpressions().add(cb.isNotNull(table.get(column)));
                    break;
                default:
                    throw new SQLExpressionUnexpectedTypeException(type);
            }
        }
        for (OrderExpressionToken orderExpressionToken : orderExpression.getOrderExpressionTokens()) {
            From<T, ?> table = root;
            String column = orderExpressionToken.getField();

            if (orderExpressionToken.getField().contains(".")) {
                joinMap.putAll(buildJoins(root, orderExpressionToken.getField()));
                table = joinMap.get(orderExpressionToken.getField().split("\\.")[0]);
                column = orderExpressionToken.getField().split("\\.")[1];
            }

            if (orderExpressionToken.getType() == OrderExpressionTokenType.ASCENDING) {
                cq.orderBy(cb.asc(table.get(column)));
            } else {
                cq.orderBy(cb.desc(table.get(column)));
            }
        }
        return p;
    }

    private Map<? extends String, ? extends Join<T, ?>> buildJoins(Root<T> root, String field) {
        String joinField = field.split("\\.")[0];
        Join<T, ?> join = root.join(joinField, JoinType.LEFT);
        return Map.of(joinField, join);
    }
}