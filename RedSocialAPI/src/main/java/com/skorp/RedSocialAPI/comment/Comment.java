package com.skorp.RedSocialAPI.comment;

import com.skorp.RedSocialAPI.publication.Publication;
import com.skorp.RedSocialAPI.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String comment;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "publication_id")
    private Publication publication;

    private Integer parentId;

    private long creationDate;

    public Comment() {
    }

    public Comment(Integer id, String comment, User user, Publication publication, Integer parentId, long creationDate) {
        this.id = id;
        this.comment = comment;
        this.user = user;
        this.publication = publication;
        this.parentId = parentId;
        this.creationDate = creationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }
}
