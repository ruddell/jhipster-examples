package com.spingular.web.repository;
import com.spingular.web.domain.Foo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Foo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FooRepository extends JpaRepository<Foo, Long>, JpaSpecificationExecutor<Foo> {

}
