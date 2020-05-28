package com.adamkorzeniak.masterdata.api.filter;

import com.adamkorzeniak.masterdata.exception.exceptions.InvalidFilterExpressionTypeException;
import lombok.Getter;

public class FilterExpressionToken {

    @Getter
    private String field;
    @Getter
    private String value;
    @Getter
    private FilterExpressionTokenType type;

    public FilterExpressionToken(String expression) {
        String[] expressionTokens = expression.split(" ");
        this.field = expressionTokens[0].trim();
        this.type = convertType(expressionTokens[1].trim());
        this.value = expressionTokens[2].trim();
    }

    private FilterExpressionTokenType convertType(String type) {
        switch (type) {
            case "eq":
                return FilterExpressionTokenType.EQUALS;
            case "neq":
                return FilterExpressionTokenType.NOT_EQUALS;
            case "lt":
                return FilterExpressionTokenType.LESS;
            case "le":
                return FilterExpressionTokenType.LESS_OR_EQUAL;
            case "gt":
                return FilterExpressionTokenType.GREATER;
            case "ge":
                return FilterExpressionTokenType.GREATER_OR_EQUAL;
            case "like":
                return FilterExpressionTokenType.LIKE;
            case "null":
                return FilterExpressionTokenType.IS_NULL;
            case "exist":
                return FilterExpressionTokenType.IS_NOT_NULL;
            default:
                throw new InvalidFilterExpressionTypeException(type);
        }
    }

}
