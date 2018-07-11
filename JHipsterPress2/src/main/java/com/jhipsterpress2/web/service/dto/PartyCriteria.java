package com.jhipsterpress2.web.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the Party entity. This class is used in PartyResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /parties?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PartyCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private InstantFilter creationDate;

    private StringFilter partyname;

    private StringFilter partydescription;

    private BooleanFilter isActive;

    private LongFilter blogId;

    private LongFilter commentId;

    private LongFilter messageId;

    private LongFilter followedId;

    private LongFilter followingId;

    private LongFilter blockinguserId;

    private LongFilter blockeduserId;

    private LongFilter userId;

    private LongFilter albumId;

    private LongFilter interestId;

    private LongFilter activityId;

    private LongFilter celebId;

    public PartyCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(InstantFilter creationDate) {
        this.creationDate = creationDate;
    }

    public StringFilter getPartyname() {
        return partyname;
    }

    public void setPartyname(StringFilter partyname) {
        this.partyname = partyname;
    }

    public StringFilter getPartydescription() {
        return partydescription;
    }

    public void setPartydescription(StringFilter partydescription) {
        this.partydescription = partydescription;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
    }

    public LongFilter getBlogId() {
        return blogId;
    }

    public void setBlogId(LongFilter blogId) {
        this.blogId = blogId;
    }

    public LongFilter getCommentId() {
        return commentId;
    }

    public void setCommentId(LongFilter commentId) {
        this.commentId = commentId;
    }

    public LongFilter getMessageId() {
        return messageId;
    }

    public void setMessageId(LongFilter messageId) {
        this.messageId = messageId;
    }

    public LongFilter getFollowedId() {
        return followedId;
    }

    public void setFollowedId(LongFilter followedId) {
        this.followedId = followedId;
    }

    public LongFilter getFollowingId() {
        return followingId;
    }

    public void setFollowingId(LongFilter followingId) {
        this.followingId = followingId;
    }

    public LongFilter getBlockinguserId() {
        return blockinguserId;
    }

    public void setBlockinguserId(LongFilter blockinguserId) {
        this.blockinguserId = blockinguserId;
    }

    public LongFilter getBlockeduserId() {
        return blockeduserId;
    }

    public void setBlockeduserId(LongFilter blockeduserId) {
        this.blockeduserId = blockeduserId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getAlbumId() {
        return albumId;
    }

    public void setAlbumId(LongFilter albumId) {
        this.albumId = albumId;
    }

    public LongFilter getInterestId() {
        return interestId;
    }

    public void setInterestId(LongFilter interestId) {
        this.interestId = interestId;
    }

    public LongFilter getActivityId() {
        return activityId;
    }

    public void setActivityId(LongFilter activityId) {
        this.activityId = activityId;
    }

    public LongFilter getCelebId() {
        return celebId;
    }

    public void setCelebId(LongFilter celebId) {
        this.celebId = celebId;
    }

    @Override
    public String toString() {
        return "PartyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (partyname != null ? "partyname=" + partyname + ", " : "") +
                (partydescription != null ? "partydescription=" + partydescription + ", " : "") +
                (isActive != null ? "isActive=" + isActive + ", " : "") +
                (blogId != null ? "blogId=" + blogId + ", " : "") +
                (commentId != null ? "commentId=" + commentId + ", " : "") +
                (messageId != null ? "messageId=" + messageId + ", " : "") +
                (followedId != null ? "followedId=" + followedId + ", " : "") +
                (followingId != null ? "followingId=" + followingId + ", " : "") +
                (blockinguserId != null ? "blockinguserId=" + blockinguserId + ", " : "") +
                (blockeduserId != null ? "blockeduserId=" + blockeduserId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (albumId != null ? "albumId=" + albumId + ", " : "") +
                (interestId != null ? "interestId=" + interestId + ", " : "") +
                (activityId != null ? "activityId=" + activityId + ", " : "") +
                (celebId != null ? "celebId=" + celebId + ", " : "") +
            "}";
    }

}
