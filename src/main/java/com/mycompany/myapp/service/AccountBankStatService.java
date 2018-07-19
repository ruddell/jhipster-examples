package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.AccountBankStat;
import com.mycompany.myapp.repository.AccountBankStatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing AccountBankStat.
 */
@Service
@Transactional
public class AccountBankStatService {

    private final Logger log = LoggerFactory.getLogger(AccountBankStatService.class);

    private final AccountBankStatRepository accountBankStatRepository;

    public AccountBankStatService(AccountBankStatRepository accountBankStatRepository) {
        this.accountBankStatRepository = accountBankStatRepository;
    }

    /**
     * Save a accountBankStat.
     *
     * @param accountBankStat the entity to save
     * @return the persisted entity
     */
    public AccountBankStat save(AccountBankStat accountBankStat) {
        log.debug("Request to save AccountBankStat : {}", accountBankStat);        return accountBankStatRepository.save(accountBankStat);
    }

    /**
     * Get all the accountBankStats.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AccountBankStat> findAll() {
        log.debug("Request to get all AccountBankStats");
        return accountBankStatRepository.findAll();
    }


    /**
     * Get one accountBankStat by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AccountBankStat> findOne(Long id) {
        log.debug("Request to get AccountBankStat : {}", id);
        return accountBankStatRepository.findById(id);
    }

    /**
     * Delete the accountBankStat by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AccountBankStat : {}", id);
        accountBankStatRepository.deleteById(id);
    }
}
