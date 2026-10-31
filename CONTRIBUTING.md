# Contributing

This document describes the team's initial GitHub collaboration workflow.

## Branches

- `main`: stable branch. Changes should be reviewed before they are merged here.
- `dev`: integration branch for completed and reviewed team work.
- `feature/<short-description>`: a branch for one focused task.

Examples:

- `feature/project-proposal`
- `feature/research-model-options`
- `docs/week-01-report`

## Workflow

1. Check the relevant GitHub Issue and confirm the task with the team.
2. Start from the latest `dev` branch.
3. Create a focused branch for your task.
4. Make changes related to that task only.
5. Commit with a short, descriptive message.
6. Push the branch and open a Pull Request targeting `dev`.
7. Ask at least one teammate to review the PR.
8. Address feedback and merge after approval.
9. Keep `main` stable; merge from `dev` to `main` at agreed project milestones.

## Commit message examples

- `docs: add week 1 progress report`
- `docs: describe team Git workflow`
- `chore: add project configuration`

## Issue and Pull Request expectations

- Use an Issue to explain the task, expected outcome, and relevant context.
- Keep each PR focused and reasonably small.
- In the PR description, summarize what changed and link the related Issue.
- Do not commit passwords, API keys, access tokens, or other secrets.
- Coordinate with the team before changing shared project structure or dependencies.

## Current project phase

The initial Week 1 work is repository and workflow setup. Do not add application implementation code until the team has agreed on the requirements and project plan.
