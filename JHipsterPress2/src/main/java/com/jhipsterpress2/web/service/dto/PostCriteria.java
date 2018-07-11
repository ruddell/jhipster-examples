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
 * Criteria class for the Post entity. This class is used in PostResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /posts?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PostCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private InstantFilter creationDate;

    private InstantFilter publicationDate;

    private StringFilter headline;

    private StringFilter leadtext;

    private StringFilter bodytext;

    private StringFilter quote;

    private StringFilter conclusion;

    private LongFilter commentId;

    private LongFilter blogId;

    private LongFilter tagId;

    private LongFilter topicId;

    public PostCriteria() {
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

    public InstantFilter getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(InstantFilter publicationDate) {
        this.publicationDate = publicationDate;
    }

    public StringFilter getHeadline() {
        return headline;
    }

    public void setHeadline(StringFilter headline) {
        this.headline = headline;
    }

    public StringFilter getLeadtext() {
        return leadtext;
    }

    public void setLeadtext(StringFilter leadtext) {
        this.leadtext = leadtext;
    }

    public StringFilter getBodytext() {
        return bodytext;
    }

    public void setBodytext(StringFilter bodytext) {
        this.bodytext = bodytext;
    }

    public StringFilter getQuote() {
        return quote;
    }

    public void setQuote(StringFilter quote) {
        this.quote = quote;
    }

    public StringFilter getConclusion() {
        return conclusion;
    }

    public void setConclusion(StringFilter conclusion) {
        this.conclusion = conclusion;
    }

    public LongFilter getCommentId() {
        return commentId;
    }

    public void setCommentId(LongFilter commentId) {
        this.commentId = commentId;
    }

    public LongFilter getBlogId() {
        return blogId;
    }

    public void setBlogId(LongFilter blogId) {
        this.blogId = blogId;
    }

    public LongFilter getTagId() {
        return tagId;
    }

    public void setTagId(LongFilter tagId) {
        this.tagId = tagId;
    }

    public LongFilter getTopicId() {
        return topicId;
    }

    public void setTopicId(LongFilter topicId) {
        this.topicId = topicId;
    }

    @Override
    public String toString() {
        return "PostCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (publicationDate != null ? "publicationDate=" + publicationDate + ", " : "") +
                (headline != null ? "headline=" + headline + ", " : "") +
                (leadtext != null ? "leadtext=" + leadtext + ", " : "") +
                (bodytext != null ? "bodytext=" + bodytext + ", " : "") +
                (quote != null ? "quote=" + quote + ", " : "") +
                (conclusion != null ? "conclusion=" + conclusion + ", " : "") +
                (commentId != null ? "commentId=" + commentId + ", " : "") +
                (blogId != null ? "blogId=" + blogId + ", " : "") +
                (tagId != null ? "tagId=" + tagId + ", " : "") +
                (topicId != null ? "topicId=" + topicId + ", " : "") +
            "}";
    }

}
