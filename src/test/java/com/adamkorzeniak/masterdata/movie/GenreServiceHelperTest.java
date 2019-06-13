package com.adamkorzeniak.masterdata.movie;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adamkorzeniak.masterdata.features.movie.model.Genre;
import com.adamkorzeniak.masterdata.features.movie.model.dto.GenreDTO;
import com.adamkorzeniak.masterdata.features.movie.service.GenreServiceHelper;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest
public class GenreServiceHelperTest {

	@Test
	public void ConvertGenreDtoToEntity_NoIssuesExpected_ReturnsConvertedEntity() throws Exception {
		Long id = 17L;
		String name = "Comedy";
		
		GenreDTO dto = new GenreDTO();
		dto.setId(id);
		dto.setName(name);
		
		Genre genre = GenreServiceHelper.convertToEntity(dto);
		
		assertThat(genre).isNotNull();
		assertThat(genre.getId()).isEqualTo(id);
		assertThat(genre.getName()).isEqualTo(name);
	}

	@Test
	public void ConvertGenreEntityToDto_NoIssuesExpected_ReturnsConvertedDto() throws Exception {
		Long id = 22L;
		String name = "Horor";
		
		Genre genre = new Genre();
		genre.setId(id);
		genre.setName(name);
		
		GenreDTO dto = GenreServiceHelper.convertToDTO(genre);
		
		assertThat(dto).isNotNull();
		assertThat(dto.getId()).isEqualTo(id);
		assertThat(dto.getName()).isEqualTo(name);
	}
}
