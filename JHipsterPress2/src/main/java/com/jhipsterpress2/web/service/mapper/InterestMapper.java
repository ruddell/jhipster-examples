package com.jhipsterpress2.web.service.mapper;

import com.jhipsterpress2.web.domain.*;
import com.jhipsterpress2.web.service.dto.InterestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Interest and its DTO InterestDTO.
 */
@Mapper(componentModel = "spring", uses = {PartyMapper.class, ProfileMapper.class})
public interface InterestMapper extends EntityMapper<InterestDTO, Interest> {



    default Interest fromId(Long id) {
        if (id == null) {
            return null;
        }
        Interest interest = new Interest();
        interest.setId(id);
        return interest;
    }
}
