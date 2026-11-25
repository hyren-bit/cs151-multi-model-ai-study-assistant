package View;

import Model.Model;
import Shared.ModelObserver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ViewTest {
    @Test
    void testViewWithInvalidModels() {
        View view = new View("", "", "");
        ModelObserver[] observers = view.getModelObservers();

        for (ModelObserver o : observers) {
            o.modelChanged();
            assertFalse(o.hasName(""));
            assertFalse(o.hasName(null));
        }
    }

    @Test
    void testViewWithValidModels() {
        View view = new View("m1", "m2", "m3");
        ModelObserver[] observers = view.getModelObservers();

        int m1Count = 0;
        int m2Count = 0;
        int m3Count = 0;
        for (ModelObserver o : observers) {
            if (o.hasName("m1")) {
                m1Count++;
            }
            if (o.hasName("m2")) {
                m2Count++;
            }
            if (o.hasName("m3")) {
                m3Count++;
            }
        }
        assertEquals(1, m1Count);
        assertEquals(1, m2Count);
        assertEquals(1, m3Count);
    }

    @Test
    void testAttachUserPromptListener() {
        View view = new View("model1", "model2", "model3");
        view.attachUserPromptListener(null);
    }

    @Test
    void testAttachModel() {
        View view = new View("model1", "model2", "model3");
        Model model = new Model();
        view.attachModel(model);

        for (ModelObserver o : view.getModelObservers()) {
            assertNotEquals(null, o);
            o.modelChanged();
            assertFalse(o.hasName("test"));
        }
    }

    @Test
    void testAttachNullModel() {
        View view = new View("model1", "model2", "model3");
        view.attachModel(null);
        for (ModelObserver o : view.getModelObservers()) {
            assertNotEquals(null, o);
            o.modelChanged();
            assertFalse(o.hasName("test"));
        }
    }

    @Test
    void getUserPrompt() {
        View view = new View("model1", "model2", "model3");
        assertNotNull(view.getUserPrompt());
    }
}