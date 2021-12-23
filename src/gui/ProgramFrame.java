package gui;

import javax.swing.*;
import java.awt.*;

/**
 * gui
 * Created by NhatLinh - 19127652
 * Date 12/23/2021 - 10:20 AM
 * Description: ...
 */
public class ProgramFrame extends JFrame {

    public static void main(String[] args)
    {
        new ProgramFrame();
    }
    public ProgramFrame()
    {
        JPanel mainPanel = new JPanel(new BorderLayout());
        MultipleButtonsPanel selectFunctionsPanel = new MultipleButtonsPanel(new ButtonTextCallback[] {
                new ButtonTextCallback("Search definition by slang word", null),
                new ButtonTextCallback("Search slang words by definition", null),
                new ButtonTextCallback("Search history", null),
                new ButtonTextCallback("Add new slang word", null),
                new ButtonTextCallback("Edit/delete a slang word", null),
                new ButtonTextCallback("Reset the original slang word", null),
                new ButtonTextCallback("Random a slang word", null),
                new ButtonTextCallback("Puzzle", null),
                new ButtonTextCallback("Puzzle", null)
        });

        mainPanel.add(selectFunctionsPanel, BorderLayout.LINE_START);

        this.getContentPane().add(mainPanel);
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
