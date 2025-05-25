package hr.fer.infsus.japan.controllers;

import hr.fer.infsus.japan.domain.dto.ProgressDto;
import hr.fer.infsus.japan.domain.entities.ProgressEntity;
import hr.fer.infsus.japan.dtos.UpdateProgressDto;
import hr.fer.infsus.japan.mappers.impl.ProgressMapper;
import hr.fer.infsus.japan.services.ProgressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/progresses")
public class ProgressController {

    private final ProgressMapper progressMapper;

    private final ProgressService progressService;

    @PreAuthorize("#email == authentication.name")
    @GetMapping("/{email}")
    public ResponseEntity<Set<ProgressDto>> findAllUserProgresses(@PathVariable("email") String email) {
        Set<ProgressEntity> progresses = progressService.findAllProgressesByUser(email);
        return ResponseEntity.ok(progresses.stream().map(progressMapper::toDto).collect(Collectors.toSet()));
    }

    @PreAuthorize("#email == authentication.name")
    @GetMapping("/{email}/{lessonId}")
    public ResponseEntity<ProgressDto> findProgress(
            @PathVariable("email") String email,
            @PathVariable("lessonId") Long lessonId
    ) {
        ProgressEntity progress = progressService.findByIdThrow(lessonId, email);
        return ResponseEntity.ok(progressMapper.toDto(progress));
    }

    @PreAuthorize("#email == authentication.name")
    @PostMapping("/{email}/{lessonId}")
    public ResponseEntity<ProgressDto> updateProgress(
            @PathVariable("email") String email,
            @PathVariable("lessonId") Long lessonId,
            @Valid @RequestBody UpdateProgressDto request
    ) {
        ProgressEntity progress = progressService.updateProgress(
                email,
                lessonId,
                request.getTermNum(),
                request.getQuestionNum()
        );
        return ResponseEntity.ok(progressMapper.toDto(progress));
    }

}
