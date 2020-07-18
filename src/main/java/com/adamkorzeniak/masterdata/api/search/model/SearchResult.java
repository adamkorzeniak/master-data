package com.adamkorzeniak.masterdata.api.search.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class SearchResult {

    private String type;
    private Object entry;
}
