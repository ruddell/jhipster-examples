package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Bar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Bar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BarRepository extends JpaRepository<Bar, Long> {

}
