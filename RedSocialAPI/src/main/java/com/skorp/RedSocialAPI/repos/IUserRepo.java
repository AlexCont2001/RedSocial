package com.skorp.RedSocialAPI.repos;

import com.skorp.RedSocialAPI.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends JpaRepository<User,Integer> {
    User findByUsername(String username);

}
