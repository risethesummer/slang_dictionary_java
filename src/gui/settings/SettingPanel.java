package gui.settings;

import gui.FunctionPanel;

import javax.swing.*;

/**
 * gui.settings
 * Created by NhatLinh - 19127652
 * Date 12/29/2021 - 11:38 PM
 * Description: ...
 */
public class SettingPanel extends FunctionPanel {

    public SettingPanel(String title, Runnable onReset, Runnable onSave)
    {
        super(title);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        JButton resetButton = new JButton("Reset to the original slang word list");
        resetButton.addActionListener(e -> {
            int choice = JOptionPane.showOptionDialog(this,
                    "Do you really want to reset the slang word list?",
                    "Confirm reset the slang word list",
                    JOptionPane.QUESTION_MESSAGE,
                    JOptionPane.YES_NO_OPTION,
                    null,
                    new String[]{"Yes", "No"},
                    0);
            if (choice != 0)
                return;
            onReset.run();
        });
        resetButton.setAlignmentX(CENTER_ALIGNMENT);

        JButton saveButton = new JButton("Save the current list");
        saveButton.addActionListener(e -> {
            onSave.run();
        });
        saveButton.setAlignmentX(CENTER_ALIGNMENT);

        mainPanel.add(resetButton);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(saveButton);
        mainPanel.add(Box.createVerticalStrut(20));
    }
}
