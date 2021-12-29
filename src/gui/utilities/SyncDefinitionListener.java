package gui.utilities;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.util.function.Function;

/**
 * gui
 * Created by NhatLinh - 19127652
 * Date 12/29/2021 - 10:20 PM
 * Description: ...
 */
public record SyncDefinitionListener(JTextField slangField, JTextComponent oldDefinition, Function<String, String> onGetDefinition) implements DocumentListener {

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

    private void updateDefinition()
    {
        try
        {
            String text = slangField.getText();
            if (text != null && !text.isBlank())
            {
                String definition = onGetDefinition.apply(text);
                if (definition == null)
                    definition = "";
                final String finalDefinition = definition;
                SwingUtilities.invokeLater(() -> {
                    oldDefinition.setText(finalDefinition);
                });
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
