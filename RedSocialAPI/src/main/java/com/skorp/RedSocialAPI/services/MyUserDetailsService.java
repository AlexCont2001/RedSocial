package com.skorp.RedSocialAPI.services;

import com.skorp.RedSocialAPI.models.User;
import com.skorp.RedSocialAPI.models.UserPrincipal;
import com.skorp.RedSocialAPI.repos.IUserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final IUserRepo userRepo;

    public MyUserDetailsService(IUserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        return new UserPrincipal(user);
    }
}
