package com.skorp.RedSocialAPI.user;

import com.skorp.RedSocialAPI.config.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final IUserRepository userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

    public UserService(IUserRepository userRepo, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    public List<User> getUsers() {
        return userRepo.findAll();
    }
    public User register(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public String login(User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    )
            );
            User dbUser = userRepo.findByUsername(user.getUsername());
            return jwtService.generateToken(dbUser);

        } catch (AuthenticationException e) {
            throw new RuntimeException("Credenciales inválidas");
        }
    }

    public String deleteUser(int id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            userRepo.delete(user.get());
            return "Usuario Eliminado";
        }
        return "Usuario Inexistente";
    }
}
