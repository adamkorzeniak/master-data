package com.adamkorzeniak.masterdata.features.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.adamkorzeniak.masterdata.features.movie.model.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>, JpaSpecificationExecutor<Genre> {
}
