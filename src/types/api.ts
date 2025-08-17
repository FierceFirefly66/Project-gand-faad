export interface UploadResponse {
  id: string;
  fileName: string;
  content: string;
  uploadedAt: string;
  contentType: string;
}

export interface GenerateRequest {
  transcriptId: string;
  prompt: string;
}

export interface GenerateResponse {
  summaryId: string;
  content: string;
}

export interface ShareRequest {
  recipients: string[];
}