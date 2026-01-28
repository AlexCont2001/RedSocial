package com.skorp.RedSocialAPI.comment;

import com.skorp.RedSocialAPI.comment.dto.CommentCreateDTO;
import com.skorp.RedSocialAPI.comment.dto.CommentResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    public  CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponseDTO>> getComments(){
        return commentService.getComments();
    }
    @GetMapping("/comment/{id}")
    public ResponseEntity<CommentResponseDTO> getComment(@PathVariable Integer id){
        return commentService.getCommentById(id);
    }
    @PostMapping("/comment")
    public ResponseEntity<CommentResponseDTO> createComment(@RequestBody CommentCreateDTO commentCreateDTO){
        return commentService.createComment(commentCreateDTO);
    }
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id){
        return commentService.deleteComment(id);
    }
}
