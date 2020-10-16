package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.GeneratedByJHipster;
import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.FooDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Foo} and its DTO {@link FooDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
@GeneratedByJHipster
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
