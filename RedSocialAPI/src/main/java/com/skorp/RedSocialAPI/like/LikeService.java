package com.skorp.RedSocialAPI.like;

import com.skorp.RedSocialAPI.like.dto.LikeCreateDTO;
import com.skorp.RedSocialAPI.like.dto.LikeResponseDTO;
import com.skorp.RedSocialAPI.publication.IPublicationRepository;
import com.skorp.RedSocialAPI.publication.Publication;
import com.skorp.RedSocialAPI.user.IUserRepository;
import com.skorp.RedSocialAPI.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    private final ILikeRepository likeRepository;
    private final IUserRepository userRepository;
    private final IPublicationRepository publicationRepository;
    private final ModelMapper mapper;

    public LikeService(ILikeRepository likeRepository, IUserRepository userRepository,
                       IPublicationRepository publicationRepository, ModelMapper mapper) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.publicationRepository = publicationRepository;
        this.mapper = mapper;
    }

    public ResponseEntity<List<LikeResponseDTO>> getLikes() {
        List<Like> likes = likeRepository.findAll();
        if(likes.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        List<LikeResponseDTO> likesDTO = likes.stream()
                .map(like -> mapper.map(like, LikeResponseDTO.class))
                .toList();
        return ResponseEntity.ok(likesDTO);
    }

    public ResponseEntity<LikeResponseDTO> getLikeById(Integer id) {
        Optional<Like> like = likeRepository.findById(id);
        if(like.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        LikeResponseDTO likeResponseDTO = mapper.map(like.get(), LikeResponseDTO.class);
        return ResponseEntity.ok(likeResponseDTO);
    }

    public ResponseEntity<LikeResponseDTO> createLike(LikeCreateDTO likeCreateDTO) {
        Optional <User> user = userRepository.findById(likeCreateDTO.getUserId());
        Optional <Publication> publication = publicationRepository.findById(likeCreateDTO.getPublicationId());

        if(user.isPresent() && publication.isPresent()){
            boolean existLike = likeRepository.existsByUserAndPublication(user.get(), publication.get());
            if(existLike){
                return ResponseEntity.badRequest().build();
            }
            Like like  = new Like();
            like.setUser(user.get());
            like.setPublication(publication.get());

            likeRepository.save(like);
            LikeResponseDTO likeResponseDTO = mapper.map(like, LikeResponseDTO.class);
            return ResponseEntity.ok(likeResponseDTO);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Void> deleteLike(Integer id) {
        Optional<Like> like = likeRepository.findById(id);
        if(like.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        likeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
