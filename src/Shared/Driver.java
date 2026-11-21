package Shared;
import Client.OllamaClient;
import Model.*;
import Controller.*;
import Strategy.GemmaStrategy;
import Strategy.LLMStrategy;
import Strategy.LlamaStrategy;
import Strategy.QwenStrategy;
import View.*;

import java.util.ArrayList;

public class Driver {
    public void main(String[] args) {
        OllamaClient ollamaClient = new OllamaClient();
        ArrayList<LLMStrategy> strategies = new ArrayList<>();

        // create strategies for the three LLMs
        String modelName1 = "Llama";
        strategies.add(new LlamaStrategy(ollamaClient));

        String modelName2 = "Gemma";
        strategies.add(new GemmaStrategy(ollamaClient));

        String modelName3 = "Qwen";
        strategies.add(new QwenStrategy(ollamaClient));

        // create the view, model and controller
        View view = new View(modelName1, modelName2, modelName3);
        Model model = new Model();
        Controller controller = new Controller(model, view, strategies);
    } //main()
} //Driver
