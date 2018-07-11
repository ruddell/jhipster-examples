package com.jhipsterpress2.web.service.mapper;

import com.jhipsterpress2.web.domain.*;
import com.jhipsterpress2.web.service.dto.FollowDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Follow and its DTO FollowDTO.
 */
@Mapper(componentModel = "spring", uses = {PartyMapper.class})
public interface FollowMapper extends EntityMapper<FollowDTO, Follow> {

    @Mapping(source = "followed.id", target = "followedId")
    @Mapping(source = "following.id", target = "followingId")
    FollowDTO toDto(Follow follow);

    @Mapping(source = "followedId", target = "followed")
    @Mapping(source = "followingId", target = "following")
    Follow toEntity(FollowDTO followDTO);

    default Follow fromId(Long id) {
        if (id == null) {
            return null;
        }
        Follow follow = new Follow();
        follow.setId(id);
        return follow;
    }
}
