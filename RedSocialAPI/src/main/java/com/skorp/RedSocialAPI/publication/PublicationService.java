package com.skorp.RedSocialAPI.publication;

import com.skorp.RedSocialAPI.publication.dto.PublicationCreateDTO;
import com.skorp.RedSocialAPI.publication.dto.PublicationResponseDTO;
import com.skorp.RedSocialAPI.user.IUserRepository;
import com.skorp.RedSocialAPI.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublicationService {

    private final IPublicationRepository publicationRepository;
    private final IUserRepository userRepository;
    private final ModelMapper mapper;
    public PublicationService(IPublicationRepository publicationRepository, ModelMapper mapper, IUserRepository userRepository) {
        this.publicationRepository = publicationRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    public ResponseEntity<List<PublicationResponseDTO>> getPublications() {
        List<Publication> publications = publicationRepository.findAll();
        if (publications.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<PublicationResponseDTO> dtoPublications = publications.stream()
                .map(publication -> mapper.map(publication,PublicationResponseDTO.class))
                .toList();
        return ResponseEntity.ok(dtoPublications);
    }

    public ResponseEntity<PublicationResponseDTO> getPublicationById(Integer id) {
        Optional<Publication> publication = publicationRepository.findById(id);
        return publication.map(value ->
                ResponseEntity.ok(mapper.map(value, PublicationResponseDTO.class))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<PublicationResponseDTO> createPublication(PublicationCreateDTO publicationCreateDTO) {
        Optional<User> user = userRepository.findById(publicationCreateDTO.getUserId());
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Publication publication = mapper.map(publicationCreateDTO, Publication.class);
        publication.setUser(user.get());
        publicationRepository.save(publication);
        return ResponseEntity.ok(mapper.map(publication,PublicationResponseDTO.class));
    }

    public ResponseEntity<Void> deletePublication(Integer id) {
        Optional<Publication> publication = publicationRepository.findById(id);
        if (publication.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        publicationRepository.delete(publication.get());
        return ResponseEntity.ok().build();
    }
}
