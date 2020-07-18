package com.adamkorzeniak.masterdata.api.common.providers;


import com.adamkorzeniak.masterdata.entity.DatabaseEntity;

public interface ResourceClassProvider {

    Class<? extends DatabaseEntity> getClass(String feature, String resource);
}
