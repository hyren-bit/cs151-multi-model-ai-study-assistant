package Shared;

/**
 * For use in Model and View, representing the current response state of an LLM.
 * Will be passed to the observers for displaying the model status.
 */
public enum ResponseState {
    /// The state corresponding to the LLM waiting for input.
    WAITING("Waiting"),
    /// The state corresponding to the LLM processing a query.
    RUNNING("Running..."),
    /// The state corresponding to the LLM successfully creating a response.
    SUCCESS("Success"),
    /// The state corresponding to the LLM failing to generate a response.
    FAILED("Failed"),
    /// The state corresponding to a failure due to excessive latency, or network error.
    TIMEOUT("Timeout"),
    /// The state corresponding to an invalid response from the LLM.
    INVALID_RESPONSE("Invalid Response");

    /// The name of the state.
    private String name;

    private ResponseState(String name) {
        this.name = name;
    } //ResponseState()

    @Override
    public String toString() {
        return name;
    } //toString()
}
