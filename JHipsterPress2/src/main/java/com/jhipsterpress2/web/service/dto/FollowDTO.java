package com.jhipsterpress2.web.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Follow entity.
 */
public class FollowDTO implements Serializable {

    private Long id;

    private Instant creationDate;

    private Long followedId;

    private Long followingId;

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

    public Long getFollowedId() {
        return followedId;
    }

    public void setFollowedId(Long partyId) {
        this.followedId = partyId;
    }

    public Long getFollowingId() {
        return followingId;
    }

    public void setFollowingId(Long partyId) {
        this.followingId = partyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FollowDTO followDTO = (FollowDTO) o;
        if (followDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), followDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FollowDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", followed=" + getFollowedId() +
            ", following=" + getFollowingId() +
            "}";
    }
}
