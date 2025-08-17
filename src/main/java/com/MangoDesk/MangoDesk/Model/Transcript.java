package com.MangoDesk.MangoDesk.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;


@Document(collection = "transcripts")
public class Transcript {

    @Id
    private String id;
    private String fileName;
    private String content;
    private LocalDateTime uploadedAt;
    private String contentType;   // <-- add this

    public Transcript() {}

    public Transcript(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
        this.uploadedAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getFileName() { return fileName; }
    public String getContent() { return content; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public String getContentType() { return contentType; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public void setContent(String content) { this.content = content; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
    public void setContentType(String contentType) { this.contentType = contentType; }

}
