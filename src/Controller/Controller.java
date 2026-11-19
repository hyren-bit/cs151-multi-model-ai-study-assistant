package Controller;

import Model.LLMResponse;
import Model.Model;
import Strategy.LLMStrategy;
import View.View;

import java.util.ArrayList;

public class Controller {
    private Model model;
    private View view;
    private ArrayList<LLMStrategy> strategies;

    public Controller(Model model, View view, ArrayList<LLMStrategy> strategies) {
        this.model = model;
        this.view = view;
        this.strategies = strategies;

        if (this.view != null) {
            this.view.attachModel(this.model);
            this.model.addObservers(this.view.getModelObservers());

            this.view.attachUserPromptListener(e -> {
                handleAskModels();
            });
        }
    }

    public void handleAskModels() {
        String question = "";
        if (view != null) {
            question = view.getUserPrompt();
        }
        
        if (question == null || question.trim().equals("")) {
            return;
        }

        ArrayList<String> modelNames = new ArrayList<String>();
        for (int i = 0; i < strategies.size(); i++) {
            modelNames.add(strategies.get(i).getDisplayName());
        }

        model.startNew(question, modelNames);

        for (int i = 0; i < strategies.size(); i++) {
            LLMStrategy strategy = strategies.get(i);
            String displayName = strategy.getDisplayName();

            strategy.generate(question).thenAccept(response -> {
                if (response != null) {
                    model.updateResponse(displayName, response);
                }
            });
        }
    }
}