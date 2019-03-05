package com.adamkorzeniak.masterdata.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.adamkorzeniak.masterdata.movie.model.Genre;
import com.adamkorzeniak.masterdata.movie.model.Movie;
import java.util.List;

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
