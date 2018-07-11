package com.jhipsterpress2.web.service.mapper;

import com.jhipsterpress2.web.domain.*;
import com.jhipsterpress2.web.service.dto.BlockeduserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Blockeduser and its DTO BlockeduserDTO.
 */
@Mapper(componentModel = "spring", uses = {PartyMapper.class})
public interface BlockeduserMapper extends EntityMapper<BlockeduserDTO, Blockeduser> {

    @Mapping(source = "blockinguser.id", target = "blockinguserId")
    @Mapping(source = "blockeduser.id", target = "blockeduserId")
    BlockeduserDTO toDto(Blockeduser blockeduser);

    @Mapping(source = "blockinguserId", target = "blockinguser")
    @Mapping(source = "blockeduserId", target = "blockeduser")
    Blockeduser toEntity(BlockeduserDTO blockeduserDTO);

    default Blockeduser fromId(Long id) {
        if (id == null) {
            return null;
        }
        Blockeduser blockeduser = new Blockeduser();
        blockeduser.setId(id);
        return blockeduser;
    }
}
