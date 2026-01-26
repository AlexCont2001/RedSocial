package com.skorp.RedSocialAPI.like;

import com.skorp.RedSocialAPI.publication.Publication;
import com.skorp.RedSocialAPI.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "publication_id")
    private Publication publication;

    public Like() {
    }
    public Like(User user, Publication publication) {
        this.user = user;
        this.publication = publication;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
