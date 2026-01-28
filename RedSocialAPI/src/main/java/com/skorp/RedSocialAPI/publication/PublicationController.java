package com.skorp.RedSocialAPI.publication;

import com.skorp.RedSocialAPI.publication.dto.PublicationCreateDTO;
import com.skorp.RedSocialAPI.publication.dto.PublicationResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publications")
public class PublicationController {
    private final PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping("/publications")
    public ResponseEntity<List<PublicationResponseDTO>> getPublications() {
        return publicationService.getPublications();
    }
    @GetMapping("/publication/{id}")
    public ResponseEntity<PublicationResponseDTO> getPublicationById(@PathVariable Integer id) {
        return publicationService.getPublicationById(id);
    }
    @PostMapping("/publication")
    public ResponseEntity<PublicationResponseDTO>  createPublication(@RequestBody PublicationCreateDTO publicationCreateDTO) {
        return publicationService.createPublication(publicationCreateDTO);
    }
    @DeleteMapping("/publication/{id}")
    public ResponseEntity<Void> deletePublication(@PathVariable Integer id) {
        return publicationService.deletePublication(id);
    }
}
