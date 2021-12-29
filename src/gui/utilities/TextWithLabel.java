package gui.utilities;

import javax.swing.*;
import java.awt.*;

/**
 * gui
 * Created by NhatLinh - 19127652
 * Date 12/23/2021 - 4:42 PM
 * Description: ...
 */
public class TextWithLabel extends JPanel {

    private JTextField textField;

    public TextWithLabel(String displayedOnLabel)
    {
        this.setLayout(new BorderLayout());

        JLabel label = new JLabel(displayedOnLabel);
        label.setAlignmentX(LEFT_ALIGNMENT);
        add(label, BorderLayout.PAGE_START);

        textField = new JTextField();
        add(textField, BorderLayout.CENTER);

        setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
    }

    public JTextField getTextField() {
        return textField;
    }
}
