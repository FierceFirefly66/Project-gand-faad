package com.MangoDesk.MangoDesk.DTO.other;

public class UpdateSummaryRequest {
    private String prompt;
    private String content;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
