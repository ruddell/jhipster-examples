package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BridgeBank;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BridgeBank entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BridgeBankRepository extends JpaRepository<BridgeBank, Long> {

}
