package com.MangoDesk.MangoDesk.Service;

import com.MangoDesk.MangoDesk.DTO.Groq.GroqRequest;
import com.MangoDesk.MangoDesk.DTO.Groq.GroqResponse;
import com.MangoDesk.MangoDesk.DTO.Groq.Message;
import com.MangoDesk.MangoDesk.DTO.other.GenerateRequest;
import com.MangoDesk.MangoDesk.DTO.other.GenerateResponse;
import com.MangoDesk.MangoDesk.Model.Summary;
import com.MangoDesk.MangoDesk.Model.Transcript;
import com.MangoDesk.MangoDesk.Repositories.SummaryRepository;
import com.MangoDesk.MangoDesk.Repositories.TranscriptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SummaryService {

    private final SummaryRepository summaryRepository;
    private final TranscriptRepository transcriptRepository;
    private final EmailService emailService;
    private final RestTemplate restTemplate;

    @Value("${groq.api.url}")
    private String groqApiUrl;

    @Value("${groq.api.key}")
    private String groqApiKey;

    // 1. Generate summary
    public GenerateResponse generateSummary(GenerateRequest request) {
        Transcript transcript = transcriptRepository.findById(request.getTranscriptId())
                .orElseThrow(() -> new RuntimeException("Transcript not found with id: " + request.getTranscriptId()));

        String aiText = callGroq(request.getPrompt(), transcript.getContent());

        Summary summary = new Summary();
        summary.setTranscriptId(request.getTranscriptId());
        summary.setPrompt(request.getPrompt());
        summary.setContent(aiText);
        summaryRepository.save(summary);

        return new GenerateResponse(summary.getId(), aiText);
    }

    // 2. Update summary manually
    public Summary updateSummary(String id, String newContent) {
        Summary summary = getSummaryByIdOrThrow(id);
        summary.setContent(newContent);
        return summaryRepository.save(summary);
    }

    // 3. Regenerate summary using new prompt
    public Summary regenerateSummary(String id, String prompt) {
        Summary summary = getSummaryByIdOrThrow(id);

        Transcript transcript = transcriptRepository.findById(summary.getTranscriptId())
                .orElseThrow(() -> new RuntimeException("Transcript not found with id: " + summary.getTranscriptId()));

        String aiText = callGroq(prompt, transcript.getContent());

        summary.setPrompt(prompt);
        summary.setContent(aiText);
        return summaryRepository.save(summary);
    }

    // 4. Share summary via email
    public void shareSummary(String id, List<String> recipients) {
        Summary summary = getSummaryByIdOrThrow(id);
        for (String recipient : recipients) {
            emailService.sendEmail(
                    recipient,
                    "Shared Summary",
                    summary.getContent()
            );
        }
    }

    public Summary getSummaryById(String id) {
        return getSummaryByIdOrThrow(id);
    }

    // ---------------- Helper methods ----------------
    private Summary getSummaryByIdOrThrow(String id) {
        return summaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Summary not found with id: " + id));
    }

    private String callGroq(String prompt, String transcriptContent) {
        GroqRequest groqRequest = new GroqRequest();
        groqRequest.setModel("llama3-70b-8192");

        List<Message> messages = new ArrayList<>();
        messages.add(new Message("system", "You are a helpful assistant. Generate a summary based on the transcript and instructions."));
        messages.add(new Message("user", prompt));
        messages.add(new Message("user", "Transcript:\n" + transcriptContent));
        groqRequest.setMessages(messages);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(groqApiKey);

        HttpEntity<GroqRequest> entity = new HttpEntity<>(groqRequest, headers);

        ResponseEntity<GroqResponse> response = restTemplate.postForEntity(
                groqApiUrl,
                entity,
                GroqResponse.class
        );

        if (response.getBody() == null ||
                response.getBody().getChoices() == null ||
                response.getBody().getChoices().isEmpty()) {
            throw new RuntimeException("Groq API returned no result");
        }

        return response.getBody().getChoices().get(0).getMessage().getContent();
    }
}
