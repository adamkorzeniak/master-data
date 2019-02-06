package com.adamkorzeniak.masterdata.movie.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.adamkorzeniak.masterdata.movie.exception.GenreAlreadyInMovieException;
import com.adamkorzeniak.masterdata.movie.exception.NotFoundGenreInMovieException;
import com.adamkorzeniak.masterdata.shared.FilterParameter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode(exclude="genres")
@Entity
@Table(name="movie__movies")
public class Movie {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@NotBlank
	@Column(name="title")
	private String title;

	@Min(1800)	@Max(2999)
	@Column(name="year")
	private Integer year;

	@Min(0)	@Max(2999)
	@Column(name="duration")
	private Integer duration;

	@Min(0) @Max(10)
	@Column(name="rating")
	private Integer rating;

	@Min(0)	@Max(10)
	@Column(name="watch_priority")
	private Integer watchPriority;

	@Column(name="description")
	private String description;

	@Column(name="review")
	private String review;

	@Column(name="plot_summary")
	private String plotSummary;

	@Column(name="review_date")
	private LocalDate reviewDate;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="movie__movie_genres",
			joinColumns=@JoinColumn(name="movie_id"),
			inverseJoinColumns=@JoinColumn(name="genre_id"))
	private List<Genre> genres;
	
//	@ManyToMany(fetch=FetchType.EAGER)
//	@JoinTable(
//			name="MOVIES_roles",
//			joinColumns=@JoinColumn(name="movie_id"),
//			inverseJoinColumns=@JoinColumn(name="person_id"))
//	private List<Role> roles;

//	public void addGenre(Genre genre) {
//
//		if (genres == null) {
//			genres = new ArrayList<>();
//		}
//		if (genres.contains(genre)) {
//			throw new GenreAlreadyInMovieException(
//					"Movie with id=" + id + " already contains genre with id=" + genre.getId()
//					+ ". Genre cannot be added to Movie");
//		}
//		genres.add(genre);
//	}
	
//	public void removeGenre(Genre genre) {
//
//		if (genres == null || !genres.contains(genre)) {
//			throw new NotFoundGenreInMovieException(
//					"Movie with id=" + id + " doesn't contain genre with id=" + genre.getId()
//					+ ". Genre cannot be removed from Movie");
//		}
//		genres.remove(genre);
//	}
	
}
