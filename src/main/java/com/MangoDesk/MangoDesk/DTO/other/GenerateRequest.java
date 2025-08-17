package com.MangoDesk.MangoDesk.DTO.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateRequest {
    private String transcriptId;   // which transcript to use
    private String prompt;         // user’s custom instruction

}
