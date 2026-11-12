package Shared;

// TODO: I'm not convinced this is the best final location for this class. - John

/**
 * This interface should be imported into Model and Controller, so View can pass an Observer from Controller into Model.
 */
public interface ModelObserver {
    void modelChanged();
}
