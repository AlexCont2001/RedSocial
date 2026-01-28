package com.skorp.RedSocialAPI.comment;

import com.skorp.RedSocialAPI.comment.dto.CommentCreateDTO;
import com.skorp.RedSocialAPI.comment.dto.CommentResponseDTO;
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
public class CommentService {

    private final ICommentRepository commentRepository;
    private final IUserRepository userRepository;
    private final IPublicationRepository publicationRepository;
    private final ModelMapper mapper;

    public CommentService(ICommentRepository commentRepository,  ModelMapper mapper, IUserRepository userRepository, IPublicationRepository publicationRepository) {
        this.commentRepository = commentRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.publicationRepository = publicationRepository;
    }

    public ResponseEntity<List<CommentResponseDTO>> getComments() {
        List<Comment> comments = commentRepository.findAll();
        if(comments.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        List<CommentResponseDTO> commentResponseDTOS = comments.stream()
                .map(comment -> mapper.map(comment, CommentResponseDTO.class))
                .toList();
        return ResponseEntity.ok(commentResponseDTOS);
    }

    public ResponseEntity<CommentResponseDTO> getCommentById(Integer id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        CommentResponseDTO commentResponseDTO = mapper.map(comment.get(), CommentResponseDTO.class);
        return ResponseEntity.ok(commentResponseDTO);
    }

    public ResponseEntity<CommentResponseDTO> createComment(CommentCreateDTO commentCreateDTO) {
        Optional<User> user = userRepository.findById(commentCreateDTO.getUserId());
        Optional<Publication> publication = publicationRepository.findById(commentCreateDTO.getPublicationId());
        if(user.isEmpty() || publication.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Comment comment = mapper.map(commentCreateDTO, Comment.class);
        comment.setUser(user.get());
        comment.setPublication(publication.get());
        commentRepository.save(comment);
        return ResponseEntity.ok(mapper.map(comment, CommentResponseDTO.class));
    }

    public ResponseEntity<Void> deleteComment(Integer id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        commentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
