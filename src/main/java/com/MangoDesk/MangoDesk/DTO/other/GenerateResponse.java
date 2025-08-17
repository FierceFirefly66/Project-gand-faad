package com.MangoDesk.MangoDesk.DTO.other;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GenerateResponse {
    private String summaryId;  // ID of saved summary
    private String content;    // AI generated content
}
