package com.adamkorzeniak.masterdata.api.common.providers;

import com.adamkorzeniak.masterdata.api.basic.DatabaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Map;

public interface RepositoryProviderService {

    JpaSpecificationExecutor<? extends DatabaseEntity> getExecutor(Class<? extends DatabaseEntity> modelClass);

    JpaRepository<? extends DatabaseEntity, Long> getRepository(Class<? extends DatabaseEntity> responseClass);

    Map<Class<? extends DatabaseEntity>, JpaSpecificationExecutor<? extends DatabaseEntity>> getAllRepositories();
}
