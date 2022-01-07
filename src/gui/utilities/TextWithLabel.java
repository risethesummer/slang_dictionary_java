package gui.utilities;

import javax.swing.*;
import java.awt.*;

/**
 * gui
 * Created by NhatLinh - 19127652
 * Date 12/23/2021 - 4:42 PM
 * Description: A small panel contains a text field and a label
 */
public class TextWithLabel extends JPanel {

    /**
     * The text field of the panel
     */
    private final JTextField textField;

    /**
     * The panel containing text fields
     */
    protected final JPanel mainPanel;

    /**
     * Construct a panel with the content displayed on the label
     * @param displayedOnLabel the content displayed on the label
     */
    public TextWithLabel(String displayedOnLabel)
    {
        this.setLayout(new BorderLayout());

        JLabel label = new JLabel(displayedOnLabel);
        label.setAlignmentX(LEFT_ALIGNMENT);
        add(label, BorderLayout.PAGE_START);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
        textField = new JTextField();
        mainPanel.add(textField);
        add(mainPanel, BorderLayout.CENTER);

        setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
    }

    /**
     * Get the text field attribute
     * @return the text field attribute
     */
    public JTextField getTextField() {
        return textField;
    }
}
