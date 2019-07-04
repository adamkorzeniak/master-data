package com.adamkorzeniak.masterdata.features.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.adamkorzeniak.masterdata.features.movie.model.Genre;
import com.adamkorzeniak.masterdata.features.movie.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

	/**
	 * Returns list of movies that contains given genre.
	 * If no movies found returns empty list.
	 * 
	 * @param genre Must be in managed (persisted) state.
	 * 
	 * @return List of movies for given genre
	 */
	List<Movie> findByGenresContaining(Genre genre);
}
