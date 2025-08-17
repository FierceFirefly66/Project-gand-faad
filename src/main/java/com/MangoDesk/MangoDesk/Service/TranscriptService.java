package com.MangoDesk.MangoDesk.Service;
import com.MangoDesk.MangoDesk.Model.Transcript;
import com.MangoDesk.MangoDesk.Repositories.TranscriptRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TranscriptService {

    private final TranscriptRepository transcriptRepository;

    public TranscriptService(TranscriptRepository transcriptRepository) {
        this.transcriptRepository = transcriptRepository;
    }

    public Transcript saveTranscript(MultipartFile file) {
        Transcript transcript = new Transcript();
        transcript.setFileName(file.getOriginalFilename());
        transcript.setContentType(file.getContentType());
        transcript.setUploadedAt(java.time.LocalDateTime.now()); // set timestamp

        try {
            // Extract file content (txt, docx, pdf)
            String content = extractText(file);
            transcript.setContent(content);
        } catch (Exception e) {
            e.printStackTrace();
            transcript.setContent("Error reading file: " + e.getMessage());
        }

        return transcriptRepository.save(transcript);
    }

    private String extractText(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename().toLowerCase();

        if (fileName.endsWith(".txt")) {
            return new String(file.getBytes());
        }
        else if (fileName.endsWith(".docx")) {
            try (InputStream is = file.getInputStream(); XWPFDocument doc = new XWPFDocument(is)) {
                return doc.getParagraphs().stream()
                        .map(XWPFParagraph::getText)
                        .collect(Collectors.joining("\n"));
            }
        }
        else if (fileName.endsWith(".pdf")) {
            try (PDDocument pdf = PDDocument.load(file.getInputStream())) {
                return new PDFTextStripper().getText(pdf);
            }
        }
        else {
            throw new IllegalArgumentException("Unsupported file format");
        }
    }

    public Transcript getTranscript(String id) {
        return transcriptRepository.findById(id).orElse(null);
    }

    public List<Transcript> getAllTranscripts() {
        return transcriptRepository.findAll();
    }
}