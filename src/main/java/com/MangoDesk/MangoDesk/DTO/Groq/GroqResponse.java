package com.MangoDesk.MangoDesk.DTO.Groq;

import java.awt.*;
import java.util.List;
import lombok.Data;

@Data
public class GroqResponse {
    private List<Choice> choices;
}