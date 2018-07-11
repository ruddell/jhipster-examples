package com.jhipsterpress2.web.service.mapper;

import com.jhipsterpress2.web.domain.*;
import com.jhipsterpress2.web.service.dto.PartyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Party and its DTO PartyDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PartyMapper extends EntityMapper<PartyDTO, Party> {

    @Mapping(source = "user.id", target = "userId")
    PartyDTO toDto(Party party);

    @Mapping(target = "blogs", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "messages", ignore = true)
    @Mapping(target = "followeds", ignore = true)
    @Mapping(target = "followings", ignore = true)
    @Mapping(target = "blockingusers", ignore = true)
    @Mapping(target = "blockedusers", ignore = true)
    @Mapping(source = "userId", target = "user")
    @Mapping(target = "album", ignore = true)
    @Mapping(target = "interests", ignore = true)
    @Mapping(target = "activities", ignore = true)
    @Mapping(target = "celebs", ignore = true)
    Party toEntity(PartyDTO partyDTO);

    default Party fromId(Long id) {
        if (id == null) {
            return null;
        }
        Party party = new Party();
        party.setId(id);
        return party;
    }
}
