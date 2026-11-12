package Shared;

/**
 * For use in Model and View, representing the current response state of an LLM.
 * Will be passed to the observers for displaying the model status.
 */
public enum ResponseState {
    WAITING("Waiting"),
    RUNNING("Running"),
    SUCCESS("Success"),
    FAILED("Failed"),
    TIMEOUT("Timeout"),
    INVALID_RESPONSE("Invalid Response");

    private String name;

    private ResponseState(String name) {
        this.name = name;
    } //ResponseState()

    @Override
    public String toString() {
        return name;
    } //toString()
}
