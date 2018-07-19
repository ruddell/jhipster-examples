package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BridgeTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BridgeTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BridgeTransactionRepository extends JpaRepository<BridgeTransaction, Long> {

}
