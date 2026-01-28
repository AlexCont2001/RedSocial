package com.skorp.RedSocialAPI.user;

import com.skorp.RedSocialAPI.config.JwtService;
import com.skorp.RedSocialAPI.role.IRoleRepository;
import com.skorp.RedSocialAPI.role.Role;
import com.skorp.RedSocialAPI.user.dto.UserCreateDTO;
import com.skorp.RedSocialAPI.user.dto.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
    private final ModelMapper mapper;

    public UserService(IUserRepository userRepository, AuthenticationManager authenticationManager,
                       JwtService jwtService, ModelMapper mapper, IRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.mapper = mapper;
        this.roleRepository = roleRepository;
    }

    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<UserResponseDTO> dtoUsers = users.stream()
                .map(user -> mapper.map(user, UserResponseDTO.class))
                .toList();
        return ResponseEntity.ok(dtoUsers);

    }
    public ResponseEntity<UserResponseDTO> register(UserCreateDTO userDTO) {
        Optional<Role> role = roleRepository.findById(userDTO.getRoleId());
        if(role.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = mapper.map(userDTO, User.class);
        user.setRole(role.get());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(mapper.map(user, UserResponseDTO.class));
    }

    public ResponseEntity<String> login(User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    )
            );
            User dbUser = userRepository.findByUsername(user.getUsername());
            return jwtService.generateToken(dbUser);

        } catch (AuthenticationException e) {
            throw new RuntimeException("Credenciales inválidas");
        }
    }

    public ResponseEntity<Void> deleteUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user.get());
        return ResponseEntity.ok().build();
    }
}
