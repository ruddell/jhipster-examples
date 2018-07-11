package com.jhipsterpress2.web.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Blockeduser entity.
 */
public class BlockeduserDTO implements Serializable {

    private Long id;

    private Instant creationDate;

    private Long blockinguserId;

    private Long blockeduserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Long getBlockinguserId() {
        return blockinguserId;
    }

    public void setBlockinguserId(Long partyId) {
        this.blockinguserId = partyId;
    }

    public Long getBlockeduserId() {
        return blockeduserId;
    }

    public void setBlockeduserId(Long partyId) {
        this.blockeduserId = partyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BlockeduserDTO blockeduserDTO = (BlockeduserDTO) o;
        if (blockeduserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), blockeduserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BlockeduserDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", blockinguser=" + getBlockinguserId() +
            ", blockeduser=" + getBlockeduserId() +
            "}";
    }
}
