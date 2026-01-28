package com.skorp.RedSocialAPI.like;

import com.skorp.RedSocialAPI.like.dto.LikeCreateDTO;
import com.skorp.RedSocialAPI.like.dto.LikeResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/likes")
    public ResponseEntity<List<LikeResponseDTO>> getLikes(){
        return likeService.getLikes();
    }
    @GetMapping("/like/{id}")
    public ResponseEntity<LikeResponseDTO> getLikeById(@PathVariable Integer id){
        return likeService.getLikeById(id);
    }
    @PostMapping("/like")
    public ResponseEntity<LikeResponseDTO> createLike(@RequestBody LikeCreateDTO likeCreateDTO){
        return likeService.createLike(likeCreateDTO);
    }
    @DeleteMapping("/like/{id}")
    public ResponseEntity<Void> deleteLike(@PathVariable Integer id){
        return likeService.deleteLike(id);
    }
}
