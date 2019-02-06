package com.adamkorzeniak.masterdata.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.adamkorzeniak.masterdata.movie.model.Genre;
import com.adamkorzeniak.masterdata.movie.model.Movie;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>, JpaSpecificationExecutor<Genre> {

	/**
	 * Returns list of genres which name contains provided string. 
	 * 
	 * @param	name - string to be found in genre name
	 * @return  List of genres
	 */
	List<Genre> findByNameIgnoreCaseContaining(String name);
}

