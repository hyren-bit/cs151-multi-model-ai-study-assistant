package Model;

import Shared.ModelObserver;
import Shared.ResponseState;
import java.util.ArrayList;

public class Model {
    private String question;
    private LLMResponse llamaResponse;
    private LLMResponse gemmaResponse;
    private LLMResponse qwenResponse;
    
    private ArrayList<ModelObserver> observers;

    public Model() {
        question = "";
        observers = new ArrayList<ModelObserver>();
        // TODO: we should be careful about hard-coding the model names outside of the driver.
        //  This program should function correctly if we use three different strategies/LLMs
        llamaResponse = new LLMResponse("Llama", "Waiting", ResponseState.WAITING, 0, "");
        gemmaResponse = new LLMResponse("Gemma", "Waiting", ResponseState.WAITING, 0, "");
        qwenResponse = new LLMResponse("Qwen", "Waiting", ResponseState.WAITING, 0, "");
    }

    public void addObserver(ModelObserver observer) {
        if (observer != null) {
            boolean found = false;
            for (int i = 0; i < observers.size(); i++) {
                if (observers.get(i) == observer) {
                    found = true;
                }
            }
            if (!found) {
                observers.add(observer);
            }
        }
    }

    public void addObservers(ModelObserver[] observerArray) {
        if (observerArray != null) {
            for (int i = 0; i < observerArray.length; i++) {
                addObserver(observerArray[i]);
            }
        }
    }

    public void removeObserver(ModelObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String specificModelChanged) {
        for (int i = 0; i < observers.size(); i++) {
            ModelObserver observer = observers.get(i);
            
            if (specificModelChanged != null) {
                if (observer.hasName(specificModelChanged)) {
                    observer.modelChanged();
                }
            } else {
                observer.modelChanged();
            }
        }
    }

    public void startNew(String question, ArrayList<String> modelNames) {
        this.question = question;
        
        for (int i = 0; i < modelNames.size(); i++) {
            String name = modelNames.get(i);
            LLMResponse runningRes = new LLMResponse(name, "Running...", ResponseState.RUNNING, 0, "");
            
            if (name.equals("Llama")) {
                llamaResponse = runningRes;
            } else if (name.equals("Gemma")) {
                gemmaResponse = runningRes;
            } else if (name.equals("Qwen")) {
                qwenResponse = runningRes;
            }
        }
        
        notifyObservers(null);
    }

    public void updateResponse(String modelName, LLMResponse response) {
        if (modelName.equals("Llama")) {
            llamaResponse = response;
        } else if (modelName.equals("Gemma")) {
            gemmaResponse = response;
        } else if (modelName.equals("Qwen")) {
            qwenResponse = response;
        }
        notifyObservers(modelName);
    }

    public String getQuestion() {
        return question;
    }

    public LLMResponse getLLMResponse(String modelName) {
        if (modelName.equals("Llama")) {
            return llamaResponse;
        } else if (modelName.equals("Gemma")) {
            return gemmaResponse;
        } else if (modelName.equals("Qwen")) {
            return qwenResponse;
        }
        return new LLMResponse(modelName, "Waiting", ResponseState.WAITING, 0, "");
    }

    public ResponseState getState(String modelName) {
        return getLLMResponse(modelName).getStatus();
    }

    public String getResponse(String modelName) {
        return getLLMResponse(modelName).getText();
    }

    public String getStatusHeader(String modelName) {
        LLMResponse res = getLLMResponse(modelName);
        if (res.getStatus() == ResponseState.SUCCESS) {
            return res.getStatus().toString() + " - " + res.getResponseTimeMs() + " ms";
        }
        return res.getStatus().toString();
    }
}