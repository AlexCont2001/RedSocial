package com.skorp.RedSocialAPI.like;

import com.skorp.RedSocialAPI.publication.Publication;
import com.skorp.RedSocialAPI.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILikeRepository extends JpaRepository<Like,Integer> {
    boolean existsByUserAndPublication(User user, Publication publication);
}
