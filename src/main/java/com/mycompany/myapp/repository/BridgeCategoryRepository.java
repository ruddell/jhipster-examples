package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BridgeCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BridgeCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BridgeCategoryRepository extends JpaRepository<BridgeCategory, Long> {

}
