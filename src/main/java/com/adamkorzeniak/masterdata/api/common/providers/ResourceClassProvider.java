package com.adamkorzeniak.masterdata.api.common.providers;


import com.adamkorzeniak.masterdata.api.basic.DatabaseEntity;

public interface ResourceClassProvider {

    Class<? extends DatabaseEntity> getClass(String feature, String resource);
}
