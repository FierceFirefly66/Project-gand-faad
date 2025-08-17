package com.MangoDesk.MangoDesk.DTO.Groq;

import java.util.List;
import lombok.Data;
import com.MangoDesk.MangoDesk.DTO.Groq.Message;

@Data
public class GroqRequest {
    private List<Message> messages;
    private String model;

    public GroqRequest(List<Message> messages, String model) {
        this.messages = messages;
        this.model = model;
    }

    public GroqRequest() {

    }
}
