package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.GeneratedByJHipster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@GeneratedByJHipster
public class FooMapperTest {
    private FooMapper fooMapper;

    @BeforeEach
    public void setUp() {
        fooMapper = new FooMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(fooMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(fooMapper.fromId(null)).isNull();
    }
}
