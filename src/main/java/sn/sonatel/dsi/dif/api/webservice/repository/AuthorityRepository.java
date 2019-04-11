package sn.sonatel.dsi.dif.api.webservice.repository;

import sn.sonatel.dsi.dif.api.webservice.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
