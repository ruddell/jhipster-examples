package com.jhipsterpress2.web.service.mapper;

import com.jhipsterpress2.web.domain.Frontpageconfig;
import com.jhipsterpress2.web.service.dto.CustomFrontpageconfigDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Frontpageconfig and its DTO FrontpageconfigDTO.
 */
@Mapper(componentModel = "spring", uses = {FrontPagePostMapper.class})
public interface CustomFrontpageconfigMapper extends EntityMapper<CustomFrontpageconfigDTO, Frontpageconfig> {

    default Frontpageconfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        Frontpageconfig frontpageconfig = new Frontpageconfig();
        frontpageconfig.setId(id);
        return frontpageconfig;
    }
}
