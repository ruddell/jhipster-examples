package com.jhipsterpress2.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Party.
 */
@Entity
@Table(name = "party")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Party implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "partyname", length = 100, nullable = false)
    private String partyname;

    @NotNull
    @Size(min = 2, max = 7500)
    @Column(name = "partydescription", length = 7500, nullable = false)
    private String partydescription;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "party")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Blog> blogs = new HashSet<>();

    @OneToMany(mappedBy = "party")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "party")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Message> messages = new HashSet<>();

    @OneToMany(mappedBy = "followed")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Follow> followeds = new HashSet<>();

    @OneToMany(mappedBy = "following")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Follow> followings = new HashSet<>();

    @OneToMany(mappedBy = "blockinguser")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Blockeduser> blockingusers = new HashSet<>();

    @OneToMany(mappedBy = "blockeduser")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Blockeduser> blockedusers = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private User user;

    @OneToOne(mappedBy = "party")
    @JsonIgnore
    private Album album;

    @ManyToMany(mappedBy = "parties")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Interest> interests = new HashSet<>();

    @ManyToMany(mappedBy = "parties")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Activity> activities = new HashSet<>();

    @ManyToMany(mappedBy = "parties")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Celeb> celebs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Party creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public String getPartyname() {
        return partyname;
    }

    public Party partyname(String partyname) {
        this.partyname = partyname;
        return this;
    }

    public void setPartyname(String partyname) {
        this.partyname = partyname;
    }

    public String getPartydescription() {
        return partydescription;
    }

    public Party partydescription(String partydescription) {
        this.partydescription = partydescription;
        return this;
    }

    public void setPartydescription(String partydescription) {
        this.partydescription = partydescription;
    }

    public byte[] getImage() {
        return image;
    }

    public Party image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Party imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public Party isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Set<Blog> getBlogs() {
        return blogs;
    }

    public Party blogs(Set<Blog> blogs) {
        this.blogs = blogs;
        return this;
    }

    public Party addBlog(Blog blog) {
        this.blogs.add(blog);
        blog.setParty(this);
        return this;
    }

    public Party removeBlog(Blog blog) {
        this.blogs.remove(blog);
        blog.setParty(null);
        return this;
    }

    public void setBlogs(Set<Blog> blogs) {
        this.blogs = blogs;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Party comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Party addComment(Comment comment) {
        this.comments.add(comment);
        comment.setParty(this);
        return this;
    }

    public Party removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setParty(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public Party messages(Set<Message> messages) {
        this.messages = messages;
        return this;
    }

    public Party addMessage(Message message) {
        this.messages.add(message);
        message.setParty(this);
        return this;
    }

    public Party removeMessage(Message message) {
        this.messages.remove(message);
        message.setParty(null);
        return this;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Set<Follow> getFolloweds() {
        return followeds;
    }

    public Party followeds(Set<Follow> follows) {
        this.followeds = follows;
        return this;
    }

    public Party addFollowed(Follow follow) {
        this.followeds.add(follow);
        follow.setFollowed(this);
        return this;
    }

    public Party removeFollowed(Follow follow) {
        this.followeds.remove(follow);
        follow.setFollowed(null);
        return this;
    }

    public void setFolloweds(Set<Follow> follows) {
        this.followeds = follows;
    }

    public Set<Follow> getFollowings() {
        return followings;
    }

    public Party followings(Set<Follow> follows) {
        this.followings = follows;
        return this;
    }

    public Party addFollowing(Follow follow) {
        this.followings.add(follow);
        follow.setFollowing(this);
        return this;
    }

    public Party removeFollowing(Follow follow) {
        this.followings.remove(follow);
        follow.setFollowing(null);
        return this;
    }

    public void setFollowings(Set<Follow> follows) {
        this.followings = follows;
    }

    public Set<Blockeduser> getBlockingusers() {
        return blockingusers;
    }

    public Party blockingusers(Set<Blockeduser> blockedusers) {
        this.blockingusers = blockedusers;
        return this;
    }

    public Party addBlockinguser(Blockeduser blockeduser) {
        this.blockingusers.add(blockeduser);
        blockeduser.setBlockinguser(this);
        return this;
    }

    public Party removeBlockinguser(Blockeduser blockeduser) {
        this.blockingusers.remove(blockeduser);
        blockeduser.setBlockinguser(null);
        return this;
    }

    public void setBlockingusers(Set<Blockeduser> blockedusers) {
        this.blockingusers = blockedusers;
    }

    public Set<Blockeduser> getBlockedusers() {
        return blockedusers;
    }

    public Party blockedusers(Set<Blockeduser> blockedusers) {
        this.blockedusers = blockedusers;
        return this;
    }

    public Party addBlockeduser(Blockeduser blockeduser) {
        this.blockedusers.add(blockeduser);
        blockeduser.setBlockeduser(this);
        return this;
    }

    public Party removeBlockeduser(Blockeduser blockeduser) {
        this.blockedusers.remove(blockeduser);
        blockeduser.setBlockeduser(null);
        return this;
    }

    public void setBlockedusers(Set<Blockeduser> blockedusers) {
        this.blockedusers = blockedusers;
    }

    public User getUser() {
        return user;
    }

    public Party user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Album getAlbum() {
        return album;
    }

    public Party album(Album album) {
        this.album = album;
        return this;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Set<Interest> getInterests() {
        return interests;
    }

    public Party interests(Set<Interest> interests) {
        this.interests = interests;
        return this;
    }

    public Party addInterest(Interest interest) {
        this.interests.add(interest);
        interest.getParties().add(this);
        return this;
    }

    public Party removeInterest(Interest interest) {
        this.interests.remove(interest);
        interest.getParties().remove(this);
        return this;
    }

    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public Party activities(Set<Activity> activities) {
        this.activities = activities;
        return this;
    }

    public Party addActivity(Activity activity) {
        this.activities.add(activity);
        activity.getParties().add(this);
        return this;
    }

    public Party removeActivity(Activity activity) {
        this.activities.remove(activity);
        activity.getParties().remove(this);
        return this;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    public Set<Celeb> getCelebs() {
        return celebs;
    }

    public Party celebs(Set<Celeb> celebs) {
        this.celebs = celebs;
        return this;
    }

    public Party addCeleb(Celeb celeb) {
        this.celebs.add(celeb);
        celeb.getParties().add(this);
        return this;
    }

    public Party removeCeleb(Celeb celeb) {
        this.celebs.remove(celeb);
        celeb.getParties().remove(this);
        return this;
    }

    public void setCelebs(Set<Celeb> celebs) {
        this.celebs = celebs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Party party = (Party) o;
        if (party.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), party.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Party{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", partyname='" + getPartyname() + "'" +
            ", partydescription='" + getPartydescription() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
}
