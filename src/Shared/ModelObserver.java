package Shared;

/**
 * TODO: Model will use objects of this class.
 *      When there is any change in the state of any of the models, the corresponding model's modelChanged() method should be called.
 * This interface should be imported into Model and Controller, so View can pass an Observer from Controller into Model.
 */
public interface ModelObserver {
    /**
     * This is a utility method to test and see if the model is assigned to a given model name.
     * @param name The canonical name of a model.
     * @return true if the Observer is monitoring that model, false otherwise.
     */
    boolean hasName(String name);

    /**
     * This method should be called whenever there is a change to the model it is observing.
     */
    void modelChanged();
} // ModelObserver