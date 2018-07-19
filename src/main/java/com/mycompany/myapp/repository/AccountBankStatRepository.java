package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AccountBankStat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AccountBankStat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountBankStatRepository extends JpaRepository<AccountBankStat, Long> {

}
