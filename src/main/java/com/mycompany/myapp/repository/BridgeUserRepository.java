package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BridgeUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BridgeUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BridgeUserRepository extends JpaRepository<BridgeUser, Long> {

}
