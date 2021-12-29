package gui;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

/**
 * gui
 * Created by NhatLinh - 19127652
 * Date 12/23/2021 - 12:40 PM
 * Description: ...
 */
public class FunctionPanel extends JPanel {

    protected JPanel mainPanel;

    public FunctionPanel(String title)
    {
        setLayout(new BorderLayout());

        JPanel labelPanel = new JPanel();
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
        labelPanel.add(titleLabel);

        mainPanel = new JPanel();

        add(labelPanel, BorderLayout.PAGE_START);
        add(mainPanel);
    }
}
