package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BridgeAccountBank;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BridgeAccountBank entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BridgeAccountBankRepository extends JpaRepository<BridgeAccountBank, Long> {

}
