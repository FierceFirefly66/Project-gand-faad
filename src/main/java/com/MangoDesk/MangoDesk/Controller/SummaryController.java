package com.MangoDesk.MangoDesk.Controller;

import com.MangoDesk.MangoDesk.DTO.other.GenerateRequest;
import com.MangoDesk.MangoDesk.DTO.other.GenerateResponse;
import com.MangoDesk.MangoDesk.DTO.other.ShareRequest;
import com.MangoDesk.MangoDesk.DTO.other.UpdateSummaryRequest;
import com.MangoDesk.MangoDesk.Model.Summary;
import com.MangoDesk.MangoDesk.Service.EmailService;
import com.MangoDesk.MangoDesk.Service.SummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/summary")
@RequiredArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;
    private final EmailService emailService;

    @PostMapping("/generate")
    public ResponseEntity<GenerateResponse> generate(@RequestBody GenerateRequest request) {
        return ResponseEntity.ok(summaryService.generateSummary(request));
    }

    @PutMapping("/{id}/content")
    public ResponseEntity<Summary> updateSummaryContent(
            @PathVariable String id,
            @RequestBody String newContent
    ) {
        return ResponseEntity.ok(summaryService.updateSummary(id, newContent));
    }

    @PostMapping("/{id}/regenerate")
    public ResponseEntity<Summary> regenerateSummary(
            @PathVariable String id,
            @RequestBody Map<String, String> body) {

        String prompt = body.get("prompt");
        return ResponseEntity.ok(summaryService.regenerateSummary(id, prompt));
    }

    @PostMapping("/{id}/share")
    public ResponseEntity<String> shareSummary(
            @PathVariable String id,
            @RequestBody ShareRequest request
    ) {
        Summary summary = summaryService.getSummaryById(id);

        request.getRecipients().forEach(email ->
                emailService.sendEmail(
                        email,
                        "Shared Meeting Summary: " + summary.getPrompt(),
                        summary.getContent()
                )
        );

        return ResponseEntity.ok("✅ Summary shared successfully!");
    }




}
