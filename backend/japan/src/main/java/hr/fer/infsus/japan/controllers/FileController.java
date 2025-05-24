package hr.fer.infsus.japan.controllers;

import hr.fer.infsus.japan.domain.entities.FileEntity;
import hr.fer.infsus.japan.services.FileService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @PermitAll
    @GetMapping("/{uuid}")
    public ResponseEntity<byte[]> getFile(@PathVariable("uuid") UUID uuid) {
        FileEntity file = fileService.findByUuidThrow(uuid);
        String utf8FileName = UriUtils.encode(file.getFileName(), "UTF-8");
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(file.getMediaType()))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + file.getFileName() + "\"; filename*=UTF-8''" + utf8FileName
                )
                .body(file.getData());
    }

}
