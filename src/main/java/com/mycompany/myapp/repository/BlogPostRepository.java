package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BlogPost;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the BlogPost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long>, JpaSpecificationExecutor<BlogPost> {

    @Query("select blog_post from BlogPost blog_post where blog_post.user.login = ?#{principal.username}")
    List<BlogPost> findByUserIsCurrentUser();

}
