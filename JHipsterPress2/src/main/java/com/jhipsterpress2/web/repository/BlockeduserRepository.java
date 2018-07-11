package com.jhipsterpress2.web.repository;

import com.jhipsterpress2.web.domain.Blockeduser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Blockeduser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlockeduserRepository extends JpaRepository<Blockeduser, Long>, JpaSpecificationExecutor<Blockeduser> {

}
