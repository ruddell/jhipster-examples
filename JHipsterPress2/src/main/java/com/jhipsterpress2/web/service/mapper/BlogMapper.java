package com.jhipsterpress2.web.service.mapper;

import com.jhipsterpress2.web.domain.*;
import com.jhipsterpress2.web.service.dto.BlogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Blog and its DTO BlogDTO.
 */
@Mapper(componentModel = "spring", uses = {PartyMapper.class})
public interface BlogMapper extends EntityMapper<BlogDTO, Blog> {

    @Mapping(source = "party.id", target = "partyId")
    BlogDTO toDto(Blog blog);

    @Mapping(target = "posts", ignore = true)
    @Mapping(source = "partyId", target = "party")
    Blog toEntity(BlogDTO blogDTO);

    default Blog fromId(Long id) {
        if (id == null) {
            return null;
        }
        Blog blog = new Blog();
        blog.setId(id);
        return blog;
    }
}
