package com.adamkorzeniak.masterdata.api.features.crypto.service;

import com.adamkorzeniak.masterdata.api.features.crypto.model.CryptoDetails;
import com.adamkorzeniak.masterdata.api.features.crypto.model.CryptoShort;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface CryptoDetailsMapper {

    @Mappings({
//            @Mapping(target="id", expression = "java(null)"),
            @Mapping(target="id", ignore=true),
            @Mapping(target="cmcId", source="id"),
    })
    CryptoDetails sourceToDestination(CryptoShort source);
}