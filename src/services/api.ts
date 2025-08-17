const API_BASE_URL = 'http://localhost:8080';

export const api = {
  // Upload transcript file
  uploadTranscript: async (file: File): Promise<any> => {
    const formData = new FormData();
    formData.append('file', file);

    const response = await fetch(`${API_BASE_URL}/api/transcripts/upload`, {
      method: 'POST',
      body: formData,
    });

    if (!response.ok) {
      throw new Error('Failed to upload transcript');
    }

    return response.json();
  },

  // Generate summary
  generateSummary: async (transcriptId: string, prompt: string): Promise<any> => {
    const response = await fetch(`${API_BASE_URL}/summary/generate`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        transcriptId,
        prompt,
      }),
    });

    if (!response.ok) {
      throw new Error('Failed to generate summary');
    }

    return response.json();
  },

  // Update summary content
  updateSummary: async (summaryId: string, content: string): Promise<any> => {
    const response = await fetch(`${API_BASE_URL}/summary/${summaryId}/content`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(content),
    });

    if (!response.ok) {
      throw new Error('Failed to update summary');
    }

    return response.json();
  },

  // Share summary via email
  shareSummary: async (summaryId: string, recipients: string[]): Promise<string> => {
    const response = await fetch(`${API_BASE_URL}/summary/${summaryId}/share`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        recipients,
      }),
    });

    if (!response.ok) {
      throw new Error('Failed to share summary');
    }

    return response.text();
  },
};