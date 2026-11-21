package Model;

import Shared.ResponseState;

public class LLMResponse {
    private String modelName;
    private String text;
    private ResponseState status;
    private long responseTimeMs;
    private String errorMessage;

    public LLMResponse(String modelName, String text, ResponseState status, long responseTimeMs, String errorMessage) {
        this.modelName = modelName;
        
        if (text != null) {
            this.text = text;
        } else {
            this.text = "";
        }
        
        this.status = status;
        this.responseTimeMs = responseTimeMs;
        
        if (errorMessage != null) {
            this.errorMessage = errorMessage;
        } else {
            this.errorMessage = "";
        }
    }

    public static LLMResponse success(String modelName, String text, long responseTimeMs) {
        if (text == null || text.trim().equals("")) {
            return new LLMResponse(modelName, "Invalid response.", ResponseState.INVALID_RESPONSE, responseTimeMs, "Empty response");
        }
        return new LLMResponse(modelName, text, ResponseState.SUCCESS, responseTimeMs, "");
    }

    public static LLMResponse failure(String modelName, String errorMessage, long responseTimeMs) {
        if (errorMessage != null && errorMessage.contains("Timeout")) {
            return new LLMResponse(modelName, "Request timed out.", ResponseState.TIMEOUT, responseTimeMs, errorMessage);
        }
        return new LLMResponse(modelName, "Error: " + errorMessage, ResponseState.FAILED, responseTimeMs, errorMessage);
    }

    public String getModelName() { 
        return modelName; 
    }
    
    public String getText() { 
        return text; 
    }
    
    public ResponseState getStatus() { 
        return status; 
    }
    
    public long getResponseTimeMs() { 
        return responseTimeMs; 
    }

    public long getElapsedMillis() {            // getter allias for Strategy
    return getResponseTimeMs();
    }

    public String getErrorMessage() { 
        return errorMessage; 
    }
}
