# Week 2 Task Allocation and Work Plan

## Project

CS151 Multi-Model AI Study Assistant

## Reporting Period

November 2–6, 2026

## Weekly Goal

Begin the first implementation phase by building the application's foundational components. Each member should complete a clearly defined part of the architecture, keep interfaces consistent, and prepare the components for integration.

**Week 2 is focused on the initial implementation.** Advanced integration, full concurrent execution, and final validation will continue in later weeks.

## Team Roles

The assignments below use Member 1, Member 2, and Member 3 as placeholders. Replace them with the actual team members' names after the team confirms the role allocation.

### Member 1 — Model and Core Application State

**Main responsibility:** Implement the Model layer and its observable state.

Tasks:
- [ ] Create the `LLMResponse` class to represent one model's result.
- [ ] Include the required response information, such as model name, response text, execution status, elapsed time, and error information.
- [ ] Define and implement the response statuses: `WAITING`, `RUNNING`, `SUCCESS`, `FAILED`, `TIMEOUT`, and `INVALID_RESPONSE`.
- [ ] Create the `ModelObserver` interface with the required notification method.
- [ ] Create the shared `QuerySession` Model to store the current question and the response state for each LLM.
- [ ] Implement observer registration, removal, and notification.
- [ ] Make sure Model state changes notify registered observers.
- [ ] Add basic tests for response-state updates and observer notifications, where practical.

**Expected deliverables:**
- Initial Model classes and interfaces.
- A working observer-notification mechanism.
- A short explanation of how the Model stores and updates the three model responses.

### Member 2 — GUI and View Layer

**Main responsibility:** Build the initial user interface and reusable response views.

Tasks:
- [ ] Create the main application window using the agreed Java GUI framework.
- [ ] Add a question input area.
- [ ] Add the **Ask Models** button.
- [ ] Create one reusable response panel/view for an LLM.
- [ ] Display three response views, one for each required model.
- [ ] Show an appropriate initial or waiting state before a response is available.
- [ ] Show each model's response status and response text.
- [ ] Implement the View/Observer connection so a view can react to Model notifications.
- [ ] Keep GUI code separate from Model and request-execution logic.
- [ ] Coordinate the View interface with Member 1 before finalizing method names.

**Expected deliverables:**
- A runnable GUI skeleton.
- Three response panels using the same reusable view design.
- Views that can display updated Model state.

### Member 3 — Strategy Layer and Ollama Preparation

**Main responsibility:** Define the interchangeable LLM strategies and prepare local Ollama access.

Tasks:
- [ ] Define the `LLMStrategy` interface.
- [ ] Include the required methods: `getDisplayName()` and `generate(String question)`.
- [ ] Create the concrete strategy classes: `LlamaStrategy`, `GemmaStrategy`, and `QwenStrategy`.
- [ ] Keep model-specific configuration inside the corresponding strategy.
- [ ] Review the provided `OllamaClient` from the course materials and determine how each strategy will use it.
- [ ] Do not create a replacement HTTP client unless the team confirms that the provided client cannot meet the assignment requirements.
- [ ] Install or verify Ollama and check that the local service is available.
- [ ] Pull and verify the required models:
  - `llama3.2:1b`
  - `gemma3:1b`
  - `qwen3:1.7b`
- [ ] Test each model locally and record any setup problems.
- [ ] Coordinate the Strategy interface with Member 1 and the Controller integration point with the team.

**Expected deliverables:**
- The Strategy interface and three concrete strategy classes.
- A documented approach for using the provided `OllamaClient`.
- Confirmation of which required Ollama models are installed and runnable, or a clear record of blockers.

## Shared Team Tasks

All members are responsible for the following:

- [ ] Confirm the package structure and class naming conventions before creating overlapping files.
- [ ] Agree on the method signatures and data passed between Model, View, Controller, and Strategy.
- [ ] Review the assignment requirements together and keep implementation aligned with the required MVC, Observer, and Strategy patterns.
- [ ] Create individual feature branches from `dev`.
- [ ] Commit and push work regularly so each member's contributions are visible in Git history.
- [ ] Open Pull Requests into `dev` and ask another member to review each PR.
- [ ] Resolve interface conflicts before merging.
- [ ] Update the Week 2 progress report with actual completed work, individual contributions, and blockers.

## Suggested Schedule

| Day | Focus | Expected result |
|---|---|---|
| Monday, Nov 2 | Confirm package structure, interfaces, and task ownership | Team agrees on class responsibilities and method signatures |
| Tuesday, Nov 3 | Implement individual components | Initial Model, GUI, and Strategy components are in progress |
| Wednesday, Nov 4 | Continue implementation and run component-level checks | Main classes compile; Ollama setup is verified or blockers are recorded |
| Thursday, Nov 5 | Review interfaces and integrate initial components | Components can be connected without conflicting definitions |
| Friday, Nov 6 | Test, review PRs, and document progress | Week 2 work is pushed to GitHub and the progress report is ready |

## Integration Agreements

To reduce merge conflicts and avoid duplicated responsibilities:

1. Member 1 owns the Model classes and observer notification mechanism.
2. Member 2 owns the GUI and reusable response views.
3. Member 3 owns the Strategy interface, concrete strategies, and Ollama setup.
4. The team must agree on shared interfaces before independently implementing code that depends on them.
5. Strategies should perform model requests and return results; they should not directly modify the Model or GUI.
6. The Controller should coordinate the Model, Views, and Strategies. It should not be implemented independently by multiple members.
7. Use the course-provided `OllamaClient` as required by the assignment.

## End-of-Week Acceptance Checklist

By the end of November 6, the team should aim to have:

- [ ] A defined and compiling initial Model layer.
- [ ] A defined Observer interface and notification flow.
- [ ] A runnable GUI skeleton with three response views.
- [ ] A defined Strategy interface and three concrete strategy classes.
- [ ] Ollama installed and the required models tested, or setup blockers documented.
- [ ] Shared interfaces reviewed by all three members.
- [ ] Individual work committed and pushed to GitHub.
- [ ] Pull Requests reviewed and merged into `dev`, where ready.
- [ ] The Week 2 progress report updated with factual contributions and remaining work.

## Important Notes

- The three member assignments are proposed role divisions, not a substitute for confirming the actual names and availability of team members.
- Do not mark a task complete until the work has actually been implemented and checked.
- If a task is blocked by an unavailable course file, environment issue, or unresolved interface, record the blocker instead of silently replacing the required component.
- Full asynchronous coordination, independent response updates, timeout handling, and end-to-end validation should be completed and verified as the relevant later phases are reached.
