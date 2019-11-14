package com.spingular.web.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class FooMapperTest {

    private FooMapper fooMapper;

    @BeforeEach
    public void setUp() {
        fooMapper = new FooMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(fooMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(fooMapper.fromId(null)).isNull();
    }
}
