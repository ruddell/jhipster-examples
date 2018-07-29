package com.mini4.web.service.mapper;

import com.mini4.web.domain.*;
import com.mini4.web.service.dto.PostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Post and its DTO PostDTO.
 */
@Mapper(componentModel = "spring", uses = {BlogMapper.class})
public interface PostMapper extends EntityMapper<PostDTO, Post> {

    @Mapping(source = "blog.id", target = "blogId")
    PostDTO toDto(Post post);

    @Mapping(target = "comments", ignore = true)
    @Mapping(source = "blogId", target = "blog")
    Post toEntity(PostDTO postDTO);

    default Post fromId(Long id) {
        if (id == null) {
            return null;
        }
        Post post = new Post();
        post.setId(id);
        return post;
    }
}
