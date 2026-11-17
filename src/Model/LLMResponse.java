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
    
    public String getErrorMessage() { 
        return errorMessage; 
    }
}