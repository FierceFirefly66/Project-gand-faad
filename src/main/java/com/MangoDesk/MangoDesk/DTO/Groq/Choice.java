package com.MangoDesk.MangoDesk.DTO.Groq;

import com.MangoDesk.MangoDesk.DTO.Groq.Message;
import lombok.Data;

@Data
public class Choice {
    private Message message;
    private int index;
    private String finish_reason;
}
