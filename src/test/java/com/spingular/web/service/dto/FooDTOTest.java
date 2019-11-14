package com.spingular.web.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.spingular.web.web.rest.TestUtil;

public class FooDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FooDTO.class);
        FooDTO fooDTO1 = new FooDTO();
        fooDTO1.setId(1L);
        FooDTO fooDTO2 = new FooDTO();
        assertThat(fooDTO1).isNotEqualTo(fooDTO2);
        fooDTO2.setId(fooDTO1.getId());
        assertThat(fooDTO1).isEqualTo(fooDTO2);
        fooDTO2.setId(2L);
        assertThat(fooDTO1).isNotEqualTo(fooDTO2);
        fooDTO1.setId(null);
        assertThat(fooDTO1).isNotEqualTo(fooDTO2);
    }
}
