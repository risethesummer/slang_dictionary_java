package gui.utilities;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * gui.utilities
 * Created by NhatLinh - 19127652
 * Date 1/7/2022 - 12:06 PM
 * Description: A panel containing text, label, checkbox and a button destroying the panel
 */
public class TextWithCheckBox extends JPanel {

    /**
     * Checkbox for deleting definition
     */
    private final JCheckBox checkBox = new JCheckBox();

    /**
     * First text field (old definition)
     */
    private final JTextField firstTextField = new JTextField();

    /**
     * Second text field (update definition)
     */
    private final JTextField secondTextField = new JTextField();

    /**
     * Get the first text field (old definition)
     * @return the first text field
     */
    public JTextField getFirstTextField() {
        return firstTextField;
    }

    /**
     * Construct a panel with destroying callback
     * @param onDestroy destroying callback
     */
    public TextWithCheckBox(Consumer<Component> onDestroy) {
        super(new GridLayout(1, 4));
        firstTextField.setEditable(false);
        add(firstTextField);
        add(secondTextField);
        add(checkBox);
        JButton destroyButton = new JButton("Delete");
        if (onDestroy == null)
            destroyButton.setEnabled(false);
        else
        {
            checkBox.setEnabled(false);
            destroyButton.addActionListener(e -> onDestroy.accept(this));
        }
        add(destroyButton);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
    }

    /**
     * Get the update content of the text field
     * @return the updated content (null if the input is not validate)
     */
    public String getUpdateText()
    {
        if (checkBox.isSelected())
            return null;
        //Get the old definition if did not input the new one
        if (secondTextField.getText() == null || secondTextField.getText().isBlank())
        {
            if (firstTextField.getText() == null || firstTextField.getText().isBlank())
                return null;
            return firstTextField.getText();
        }
        return secondTextField.getText();
    }
}
