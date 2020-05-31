package com.adamkorzeniak.masterdata.features.search.model;

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
