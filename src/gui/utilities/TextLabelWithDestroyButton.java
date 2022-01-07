package gui.utilities;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * gui.utilities
 * Created by NhatLinh - 19127652
 * Date 1/7/2022 - 11:20 AM
 * Description: A panel containing text, label and a button destroying the panel
 */
public class TextLabelWithDestroyButton extends TextWithLabel {

    /**
     * Construct a panel with full information
     * @param displayedOnLabel the text displayed on the label
     * @param displayedOnButton the text displayed on the button
     * @param onDestroy the callback when clicking the destroy button
     */
    public TextLabelWithDestroyButton(String displayedOnLabel, String displayedOnButton, Consumer<Component> onDestroy)
    {
        super(displayedOnLabel);
        JButton button = new JButton(displayedOnButton);
        button.addActionListener(e -> onDestroy.accept(this));
        mainPanel.add(button);
    }
}
