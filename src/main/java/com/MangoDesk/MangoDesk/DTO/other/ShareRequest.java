package com.MangoDesk.MangoDesk.DTO.other;

import lombok.Data;

import java.util.List;

@Data
public class ShareRequest {
    private List<String> recipients;
}
