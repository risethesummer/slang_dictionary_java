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
 * Description: Get hints for user when they input
 */
public record SyncDefinitionListener(JTextField slangField, JTextComponent oldDefinition, Function<String, String> onGetDefinition) implements DocumentListener {

    /**
     * Called when inserting to the document
     * @param e document event
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        updateDefinition();
    }

    /**
     * Called when deleting something from the document
     * @param e document event
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        updateDefinition();
    }

    /**
     * Called when modifying the document
     * @param e document event
     */
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
                //Get hints
                String definition = onGetDefinition.apply(text);
                if (definition == null)
                    definition = "";
                final String finalDefinition = definition;
                //Set hints to the result field
                SwingUtilities.invokeLater(() -> {
                    oldDefinition.setText(finalDefinition);
                });
            }
            else
            {
                //Set null to the result field if the user inputs nothing
                SwingUtilities.invokeLater(() -> {
                    oldDefinition.setText(null);
                });
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
