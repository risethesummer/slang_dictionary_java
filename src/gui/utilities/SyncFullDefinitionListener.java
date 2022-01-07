package gui.utilities;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * gui.utilities
 * Created by NhatLinh - 19127652
 * Date 1/7/2022 - 2:19 PM
 * Description: Sync the current definition of a slang word calling back to set the list of definitions
 */
public record SyncFullDefinitionListener (JTextField slangField, Function<String, String> onGetDefinition, Consumer<String[]> callback) implements DocumentListener {
    @Override
    public void insertUpdate(DocumentEvent e) {
        updateDefinition();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updateDefinition();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        updateDefinition();
    }

    private void updateDefinition() {
        try {
            String text = slangField.getText();
            if (text != null && !text.isBlank()) {
                //Get hints
                String definitions = onGetDefinition.apply(text);
                if (definitions != null)
                    callback.accept(definitions.split("\\|"));
                else
                    callback.accept(null);
            } else {
                //Set null to the result field if the user inputs nothing
                callback.accept(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
