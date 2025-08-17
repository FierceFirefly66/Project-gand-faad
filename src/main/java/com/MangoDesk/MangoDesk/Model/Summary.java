package com.MangoDesk.MangoDesk.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "summaries")
public class Summary {

    @Id
    private String id;
    private String transcriptId; // To link back to the original transcript
    private String prompt;       // The prompt used for generation
    private String content;      // The AI-generated summary

    public Summary(String transcriptId, String prompt, String content) {
        this.transcriptId = transcriptId;
        this.prompt = prompt;
        this.content = content;
    }

    public Summary(){};

    public String getId() {
        return id;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getTranscriptId() {
        return transcriptId;
    }

    public String getContent() {
        return content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTranscriptId(String transcriptId) {
        this.transcriptId = transcriptId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
