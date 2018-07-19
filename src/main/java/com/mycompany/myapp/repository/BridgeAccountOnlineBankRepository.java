package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BridgeAccountOnlineBank;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BridgeAccountOnlineBank entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BridgeAccountOnlineBankRepository extends JpaRepository<BridgeAccountOnlineBank, Long> {

}
