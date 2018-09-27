package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.FooDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Foo and its DTO FooDTO.
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
