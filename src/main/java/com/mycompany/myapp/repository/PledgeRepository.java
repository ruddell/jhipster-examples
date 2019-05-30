package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Pledge;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pledge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PledgeRepository extends JpaRepository<Pledge, Long>, JpaSpecificationExecutor<Pledge> {

}
