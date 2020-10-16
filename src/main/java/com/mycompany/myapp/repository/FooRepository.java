package com.mycompany.myapp.repository;

import com.mycompany.myapp.GeneratedByJHipster;
import com.mycompany.myapp.domain.Foo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Foo entity.
 */
@SuppressWarnings("unused")
@Repository
@GeneratedByJHipster
public interface FooRepository extends JpaRepository<Foo, Long>, JpaSpecificationExecutor<Foo> {}
