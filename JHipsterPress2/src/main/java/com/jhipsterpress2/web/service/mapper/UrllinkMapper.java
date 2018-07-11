package com.jhipsterpress2.web.service.mapper;

import com.jhipsterpress2.web.domain.*;
import com.jhipsterpress2.web.service.dto.UrllinkDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Urllink and its DTO UrllinkDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UrllinkMapper extends EntityMapper<UrllinkDTO, Urllink> {



    default Urllink fromId(Long id) {
        if (id == null) {
            return null;
        }
        Urllink urllink = new Urllink();
        urllink.setId(id);
        return urllink;
    }
}
