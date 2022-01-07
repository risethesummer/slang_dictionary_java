package gui.puzzles;

import collections.Question;
import gui.FunctionPanel;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

/**
 * gui.puzzles
 * Created by NhatLinh - 19127652
 * Date 12/30/2021 - 3:01 PM
 * Description: A panel for puzzle activities
 */
public class PuzzlePanel extends FunctionPanel {
    /**
     * The right solution for current question
     */
    private int currentRight = -1;
    /**
     * Store the label showing the question
     */
    private final JLabel questionLabel = new JLabel("Question",JLabel.CENTER);
    /**
     * Store the answer buttons
     */
    private final JButton[] answerButtons = new JButton[Question.NUMBER_OF_ANSWER];

    /**
     * Construct a new panel with full information
     * @param title the title for the function
     * @param onGetQuestion the callback used to get the function when click the load button
     */
    public PuzzlePanel(String title, Supplier<Question> onGetQuestion)
    {
        super(title);
        mainPanel.setLayout(new BorderLayout());

        JButton loadButton = new JButton("Load new question");
        loadButton.addActionListener(e -> {
            Question question = onGetQuestion.get();
            questionLabel.setText(question.question());
            for (int i = 0; i < question.answer().size(); i++)
                answerButtons[i].setText(question.answer().get(i));
            currentRight = question.right();
            startAnswering();
        });

        JPanel questionLabelOuterPanel = new JPanel();
        questionLabelOuterPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        questionLabel.setFont(new Font( "Arial", Font.BOLD, 15 ));
        JPanel questionPanel = new JPanel();
        questionPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.PAGE_AXIS));
        questionLabelOuterPanel.add(questionLabel);

        JPanel answerPanel = new JPanel(new GridLayout(2, 2));
        answerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        for (int i = 0; i < Question.NUMBER_OF_ANSWER; i++)
        {
            answerButtons[i] = new JButton();
            answerPanel.add(answerButtons[i]);
        }
        //Add end answering to each button
        answerButtons[0].addActionListener(e -> endAnswering(0));
        answerButtons[1].addActionListener(e -> endAnswering(1));
        answerButtons[2].addActionListener(e -> endAnswering(2));
        answerButtons[3].addActionListener(e -> endAnswering(3));

        mainPanel.add(loadButton, BorderLayout.PAGE_START);
        questionPanel.add(Box.createVerticalStrut(20));
        questionPanel.add(questionLabelOuterPanel);
        questionPanel.add(answerPanel);
        questionPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(questionPanel, BorderLayout.CENTER);
    }

    private void activeButtons(boolean active)
    {
        for (JButton button : answerButtons)
            button.setEnabled(active);
    }

    private void startAnswering()
    {
        for (JButton button : answerButtons)
            button.setBackground(null);
        activeButtons(true);
    }

    private void endAnswering(int choice)
    {
        if (currentRight != -1)
        {
            activeButtons(false);
            if (currentRight == choice)
                JOptionPane.showMessageDialog(this, "You've selected the right solution!", "RIGHT", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "The answer is wrong!", "WRONG", JOptionPane.ERROR_MESSAGE);
            answerButtons[choice].setBackground(Color.RED);
            answerButtons[currentRight].setBackground(Color.GREEN);
        }
    }
}
