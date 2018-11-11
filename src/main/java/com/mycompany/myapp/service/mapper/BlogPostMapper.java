package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.BlogPostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BlogPost and its DTO BlogPostDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface BlogPostMapper extends EntityMapper<BlogPostDTO, BlogPost> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    BlogPostDTO toDto(BlogPost blogPost);

    @Mapping(source = "userId", target = "user")
    BlogPost toEntity(BlogPostDTO blogPostDTO);

    default BlogPost fromId(Long id) {
        if (id == null) {
            return null;
        }
        BlogPost blogPost = new BlogPost();
        blogPost.setId(id);
        return blogPost;
    }
}
