package com.adamkorzeniak.masterdata.error;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adamkorzeniak.masterdata.features.error.model.Error;
import com.adamkorzeniak.masterdata.features.error.model.ErrorDTO;
import com.adamkorzeniak.masterdata.features.error.service.ErrorServiceHelper;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest
public class ErrorServiceHelperTest {

    private static final Long ID = 17L;
    private static final String ERROR_ID = "master-data-web-11111";
    private static final String APP_ID = "master-data-web";
    private static final String NAME = "Error name";
    private static final String STATUS = "Client failure";
    private static final String DETAILS = "Client failed because user don't know how to use software ;)";
    private static final String URL = "\\location";
    private static final String STACK = "Exception occurred: blablabla, noone understands";
    private static final Long TIME = 11111111L;

    @Test
    public void ConvertErrorDtoToEntity_NoIssuesExpected_ReturnsConvertedEntity() {
        ErrorDTO dto = new ErrorDTO();
        dto.setId(ID);
        dto.setErrorId(ERROR_ID);
        dto.setAppId(APP_ID);
        dto.setName(NAME);
        dto.setStatus(STATUS);
        dto.setDetails(DETAILS);
        dto.setUrl(URL);
        dto.setStack(STACK);
        dto.setTime(TIME);

        Error error = ErrorServiceHelper.convertToEntity(dto);

        assertThat(error).isNotNull();
        assertThat(error.getId()).isEqualTo(ID);
        assertThat(error.getErrorId()).isEqualTo(ERROR_ID);
        assertThat(error.getAppId()).isEqualTo(APP_ID);
        assertThat(error.getName()).isEqualTo(NAME);
        assertThat(error.getStatus()).isEqualTo(STATUS);
        assertThat(error.getDetails()).isEqualTo(DETAILS);
        assertThat(error.getUrl()).isEqualTo(URL);
        assertThat(error.getStack()).isEqualTo(STACK);
        assertThat(error.getTime()).isEqualTo(TIME);
    }

    @Test
    public void ConvertErrorEntityToDto_NoIssuesExpected_ReturnsConvertedDto() {
        Error entity = new Error();
        entity.setId(ID);
        entity.setErrorId(ERROR_ID);
        entity.setAppId(APP_ID);
        entity.setName(NAME);
        entity.setStatus(STATUS);
        entity.setDetails(DETAILS);
        entity.setUrl(URL);
        entity.setStack(STACK);
        entity.setTime(TIME);

        ErrorDTO dto = ErrorServiceHelper.convertToDTO(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(ID);
        assertThat(dto.getErrorId()).isEqualTo(ERROR_ID);
        assertThat(dto.getAppId()).isEqualTo(APP_ID);
        assertThat(dto.getName()).isEqualTo(NAME);
        assertThat(dto.getStatus()).isEqualTo(STATUS);
        assertThat(dto.getDetails()).isEqualTo(DETAILS);
        assertThat(dto.getUrl()).isEqualTo(URL);
        assertThat(dto.getStack()).isEqualTo(STACK);
        assertThat(dto.getTime()).isEqualTo(TIME);
    }
}
