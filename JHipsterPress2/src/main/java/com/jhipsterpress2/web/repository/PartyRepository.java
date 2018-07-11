package com.jhipsterpress2.web.repository;

import com.jhipsterpress2.web.domain.Party;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Party entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyRepository extends JpaRepository<Party, Long>, JpaSpecificationExecutor<Party> {

    @Query("select party from Party party where party.user.login = ?#{principal.username}")
    List<Party> findByUserIsCurrentUser();

}
