package com.adamkorzeniak.masterdata.features.filmweb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.adamkorzeniak.masterdata.features.movie.model.dto.GenreDTO;
import com.adamkorzeniak.masterdata.features.movie.model.dto.MovieDTO;

@Service
public class FilmwebServiceImpl implements FilmwebService {

	private static final String FILMWEB_BASE_URL = "https://www.filmweb.pl";

	private Logger logger = LoggerFactory.getLogger(FilmwebServiceImpl.class);

	@Override
	public MovieDTO getMovieDetails(String movieUrl) {
		Document doc = retrieveDocument(movieUrl);
		if (doc == null) {
			return null;
		}

		MovieDTO movie = new MovieDTO();
		movie.setTitle(retrieveTitle(doc));

		movie.setOriginalTitle(retrieveOriginalTitle(doc));
		movie.setYear(retrieveYear(doc));
		movie.setDuration(retrieveDuration(doc));
		movie.setDescription(retrieveDescription(doc));
		movie.setFilmPlot(retrieveFilmPlot(doc));
		movie.setFilmwebRating(retrieveFilmwebRating(doc));
		movie.setFilmwebRatingCount(retrieveFilmwebRatingCount(doc));
		movie.setFilmwebTopRanking(retrieveFilmwebTopRanking(doc));
		movie.setDirectors(retrieveDirectors(doc));
		movie.setProductionCountries(retrieveProductionCountries(doc));
		movie.setGenres(retrieveGenres(doc));

		return movie;
	}

	private Document retrieveDocument(String movieUrl) {
		Document doc = null;
		try {
			doc = Jsoup.connect(movieUrl).get();
		} catch (IOException e) {
			logger.error("context", e);
		}
		return doc;
	}

	private String retrieveTitle(Document doc) {
		Element titleElement = doc.select(".filmTitle a").first();
		if (titleElement == null) {
			return null;
		}
		return titleElement.text();
	}

	private String retrieveOriginalTitle(Document doc) {
		Element originalTitleElement = doc.select(".filmMainHeader div h2").first();
		if (originalTitleElement == null) {
			return retrieveTitle(doc);
		}
		return originalTitleElement.text().trim();
	}

	private Integer retrieveYear(Document doc) {
		Element yearElement = doc.select(".filmTitle span").first();
		if (yearElement == null) {
			return null;
		}
		String yearString = yearElement.text();
		yearString = yearString.substring(yearString.indexOf('(') + 1);
		yearString = yearString.substring(0, yearString.indexOf(')'));
		return Integer.parseInt(yearString);
	}

	private Integer retrieveDuration(Document doc) {
		Element stringElement = doc.select(".filmMainHeader time").first();
		if (stringElement == null) {
			return null;
		}
		String durationString = stringElement.attr("datetime")
				.replace("PT", "")
				.replace("M", "")
				.trim();
		return Integer.parseInt(durationString);
	}

	private String retrieveDescription(Document doc) {
		Element descriptionElement = doc.select(".filmMainDescription p[itemprop$=description]").first();
		if (descriptionElement == null) {
			return null;
		}
		String description = descriptionElement.ownText();
		Element descriptionMoreElement = descriptionElement.select(".fullText").first();
		if (descriptionMoreElement != null) {
			description += " " + descriptionMoreElement.text();
		}
		return description;
	}

	private String retrieveFilmPlot(Document doc) {
		Element filmPlotElement = doc.select(".filmPlot p").first();
		if (filmPlotElement == null) {
			return null;
		}
		return filmPlotElement.text();
	}

	private String retrieveProductionCountries(Document doc) {
		Elements productionElements = doc.select(".filmInfo th:containsOwn(produkcja)").next().select("li a");
		if (productionElements == null || productionElements.isEmpty()) {
			return null;
		}
		return productionElements.stream()
			.map(element -> element.text().trim())
			.collect(Collectors.joining(", "));
	}

	private List<GenreDTO> retrieveGenres(Document doc) {
		Elements genreElements = doc.select(".filmInfo .genresList a");
		if (genreElements == null || genreElements.isEmpty()) {
			return new ArrayList<>();
		}
		return genreElements.stream()
				.map(element -> {
					GenreDTO dto = new GenreDTO();
					dto.setName(element.text());
					return dto;
				})
				.collect(Collectors.toList());
	}	
	
	private String retrieveDirectors(Document doc) {
		Elements directorElements = doc.select(".filmInfo li[itemprop$=director]");
		if (directorElements == null || directorElements.isEmpty()) {
			return null;
		}
		return directorElements.stream()
			.map(element -> element.text().trim())
			.collect(Collectors.joining(", "));
	}

	private Integer retrieveFilmwebTopRanking(Document doc) {
		Element filmwebTopRankingElement = doc.select("a.worldRanking").first();
		if (filmwebTopRankingElement == null) {
			return null;
		}
		String filmwebTopRankingString = filmwebTopRankingElement.attr("data-position").trim();
		return Integer.parseInt(filmwebTopRankingString);
	}

	private Integer retrieveFilmwebRatingCount(Document doc) {
		Element filmwebRatingCountElement = doc.select("span[itemprop$=ratingCount]").first();
		if (filmwebRatingCountElement == null) {
			return null;
		}
		String filmwebRatingCountString = filmwebRatingCountElement.text().trim();
		return Integer.parseInt(filmwebRatingCountString);
	}

	private Float retrieveFilmwebRating(Document doc) {
		Element filmwebRatingElement = doc.select("span[itemprop$=ratingValue]").first();
		if (filmwebRatingElement == null) {
			return null;
		}
		String filmwebRatingString = filmwebRatingElement.text().replace(",", ".").trim();
		return Float.parseFloat(filmwebRatingString);
	}

	@Override
	public List<String> getPopularMoviesUrls(int count) {

		List<String> movieUrls = new ArrayList<>();

		String url = "https://www.filmweb.pl/films/search?orderBy=popularity&descending=true&startCount=1000&page=";
		int page = 1;
		Document doc = null;

		do {

			try {
				doc = Jsoup.connect(url + page).get();
			} catch (IOException e) {
				logger.error("context", e);
			}

			if (doc == null) {
				return movieUrls;
			}

			Elements elements = doc.select("li.hits__item .filmPreview__link");

			if (elements.isEmpty()) {
				return movieUrls;
			}

			movieUrls.addAll(
				elements.stream()
					.map(link -> FILMWEB_BASE_URL + link.attr("href"))
					.collect(Collectors.toList()));
			page++;

		} while (movieUrls.size() < count);

		return movieUrls;
	}
}
