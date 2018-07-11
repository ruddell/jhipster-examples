package com.jhipsterpress2.web.service.mapper;

import com.jhipsterpress2.web.domain.*;
import com.jhipsterpress2.web.service.dto.CelebDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Celeb and its DTO CelebDTO.
 */
@Mapper(componentModel = "spring", uses = {PartyMapper.class, ProfileMapper.class})
public interface CelebMapper extends EntityMapper<CelebDTO, Celeb> {



    default Celeb fromId(Long id) {
        if (id == null) {
            return null;
        }
        Celeb celeb = new Celeb();
        celeb.setId(id);
        return celeb;
    }
}
