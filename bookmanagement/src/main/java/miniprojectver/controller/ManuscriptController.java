package miniprojectver.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;

import miniprojectver.domain.Manuscript;
import miniprojectver.domain.ManuscriptRepository;
import miniprojectver.dto.ManuscriptRequestDto;
import miniprojectver.dto.ManuscriptResponseDto;



@RestController
@RequestMapping("/manuscripts")
@RequiredArgsConstructor
public class ManuscriptController {
    private final ManuscriptRepository manuscriptRepository;

    @PostMapping
    public ResponseEntity<ManuscriptResponseDto> create(@RequestBody ManuscriptRequestDto requestDto) {
        Manuscript entity = requestDto.toEntity();
        Manuscript saved = manuscriptRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(ManuscriptResponseDto.fromEntity(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManuscriptResponseDto> get(@PathVariable Long id) {
        return manuscriptRepository.findById(id)
                .map(ManuscriptResponseDto::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
