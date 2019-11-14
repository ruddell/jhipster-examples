package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.FooDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Foo} and its DTO {@link FooDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FooMapper extends EntityMapper<FooDTO, Foo> {



    default Foo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Foo foo = new Foo();
        foo.setId(id);
        return foo;
    }
}
