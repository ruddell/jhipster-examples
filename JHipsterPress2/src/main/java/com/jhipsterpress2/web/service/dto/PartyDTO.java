package com.jhipsterpress2.web.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Party entity.
 */
public class PartyDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant creationDate;

    @NotNull
    @Size(min = 2, max = 100)
    private String partyname;

    @NotNull
    @Size(min = 2, max = 7500)
    private String partydescription;

    @Lob
    private byte[] image;
    private String imageContentType;

    private Boolean isActive;

    private Long userId;

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

    public String getPartyname() {
        return partyname;
    }

    public void setPartyname(String partyname) {
        this.partyname = partyname;
    }

    public String getPartydescription() {
        return partydescription;
    }

    public void setPartydescription(String partydescription) {
        this.partydescription = partydescription;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PartyDTO partyDTO = (PartyDTO) o;
        if (partyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartyDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", partyname='" + getPartyname() + "'" +
            ", partydescription='" + getPartydescription() + "'" +
            ", image='" + getImage() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", user=" + getUserId() +
            "}";
    }
}
