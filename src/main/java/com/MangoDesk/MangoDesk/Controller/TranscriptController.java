package com.MangoDesk.MangoDesk.Controller;

import com.MangoDesk.MangoDesk.Model.Transcript;
import com.MangoDesk.MangoDesk.Service.TranscriptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/transcripts")
public class TranscriptController {

    private final TranscriptService transcriptService;

    public TranscriptController(TranscriptService transcriptService) {
        this.transcriptService = transcriptService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Transcript> uploadTranscript(@RequestParam("file") MultipartFile file) {
        System.out.println("Received file: " + file.getOriginalFilename());
        System.out.println("Content type: " + file.getContentType());
        System.out.println("Size: " + file.getSize());
        try {
            Transcript transcript = transcriptService.saveTranscript(file);
            return ResponseEntity.ok(transcript);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transcript> getTranscript(@PathVariable String id) {
        Transcript transcript = transcriptService.getTranscript(id);
        return transcript != null ? ResponseEntity.ok(transcript) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Transcript> getAllTranscripts() {
        return transcriptService.getAllTranscripts();
    }
}
