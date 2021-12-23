package gui;

import javax.swing.*;
import java.awt.*;

/**
 * PACKAGE_NAME
 * Created by NhatLinh - 19127652
 * Date 11/30/2021 - 11:54 AM
 * Description: A panel (box layout) consisting of multiple buttons
 */
public class MultipleButtonsPanel extends Panel {

    /**
     * Create the panel with a list of text and callback
     * @param buttonInformation the information of the buttons (text and callback)
     * @param direction the direction of the buttons
     * @param height the height of the structs between the buttons
     */
    public MultipleButtonsPanel(ButtonTextCallback[] buttonInformation)
    {
        setLayout(new GridLayout(buttonInformation.length, 1));
        for (ButtonTextCallback information : buttonInformation)
        {
            JButton button = new JButton(information.text());
            button.addActionListener(information.callback());
            add(button);
            //add(Box.createVerticalStrut(height));
        }
    }
}
