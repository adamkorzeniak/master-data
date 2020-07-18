package com.adamkorzeniak.masterdata.api.basic.query.order;

import com.adamkorzeniak.masterdata.exception.exceptions.InvalidOrderExpressionTypeException;
import lombok.Getter;

public class OrderExpressionToken {

    @Getter
    private String field;
    @Getter
    private OrderExpressionTokenType type;

    public OrderExpressionToken(String expression) {
        String[] expressionTokens = expression.split(" ");
        this.field = expressionTokens[0].trim();
        String typeToken = expressionTokens.length > 1 ? expressionTokens[1].trim() : "asc";
        this.type = convertType(typeToken);
    }

    private OrderExpressionTokenType convertType(String type) {
        switch (type) {
            case "asc":
                return OrderExpressionTokenType.ASCENDING;
            case "desc":
                return OrderExpressionTokenType.DESCENDING;
            default:
                throw new InvalidOrderExpressionTypeException(type);
        }
    }
}
