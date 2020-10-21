package com.adamkorzeniak.masterdata.api.features.crypto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "symbol")
public class CryptoShort implements Serializable {

    private Long id;

    private String name;

    private String symbol;

    private String slug;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("is_active")
    private Boolean isActive;
}
