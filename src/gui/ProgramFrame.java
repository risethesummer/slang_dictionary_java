package gui;

import collections.Question;
import collections.SearchedSlangWord;
import collections.SlangWord;
import gui.modification.AddSlangWordPanel;
import gui.modification.UpdateDeleteSlangWordPanel;
import gui.puzzles.PuzzlePanel;
import gui.search.*;
import java.util.List;
import gui.settings.SettingPanel;

import javax.swing.*;
import java.awt.*;
import java.util.function.*;

/**
 * gui
 * Created by NhatLinh - 19127652
 * Date 12/23/2021 - 10:20 AM
 * Description: The main frame of the program containing function panels
 */
public class ProgramFrame extends JFrame {

    /**
     * Search definition function title
     */
    private final String SEARCH_DEFINITION = "Search definition by slang word";
    /**
     * Search slang words function title
     */
    private final String SEARCH_SLANG = "Search slang words by definition";
    /**
     * Get history function title
     */
    private final String GET_HISTORY = "Get search history";
    /**
     * Add new slang word function title
     */
    private final String ADD_SLANG = "Add new slang word";
    /**
     * Update slang word function title
     */
    private final String UPDATE_SLANG = "Update/Delete a slang word";
    /**
     * Setting function title
     */
    private final String SETTING = "Settings";
    /**
     * Get random slang word function title
     */
    private final String RANDOM = "Random a slang word";
    /**
     * Puzzles function title
     */
    private final String PUZZLES = "Puzzles";
    /**
     * Slang puzzle function title
     */
    private  final String PUZZLE_SLANG = "Guess definition by slang word";
    /**
     * Definition puzzle function title
     */
    private  final String PUZZLE_DEFINITION = "Guess slang word by definition";
    /**
     * The saving callback when disposing
     */
    private final Runnable onSave;

    /**
     * Construct a new frame with callbacks for the function panels
     * @param onSearchDefinitionSaving the search definition callback saving the search history
     * @param onSearchDefinition the search definition callback without saving history
     * @param onSearchSlangSaving the search slang words callback saving the search history
     * @param onSearchSlang the search slang words callback without saving history
     * @param onGetSearchDefinitionHistory the callback used to get search definitions history
     * @param onGetSearchSlangHistory the callback used to get search slang words history
     * @param definitionLoadedHistory the search definitions history stored in the system
     * @param slangLoadedHistory the search slang words history stored in the system
     * @param onAddSlangWord the callback for adding a new slang word
     * @param onUpdate the callback for updating a slang word
     * @param onDelete the callback for deleting a slang word
     * @param onReset the callback for resetting current data to the original state
     * @param onSave the callback for saving current data to disk
     * @param onRandom the callback for getting a random slang word
     * @param onSlangPuzzle the callback for getting a question for slang puzzle (1 slang word, 4 definitions)
     * @param onDefinitionPuzzle the callback for getting a question for definition puzzle (4 slang words, 1 definition)
     */
    public ProgramFrame(Function<String, String> onSearchDefinitionSaving,
                        Function<String, String> onSearchDefinition,
                        Function<String, String> onSearchSlangSaving,
                        Function<String, String> onSearchSlang,
                        Supplier<java.util.List<String>> onGetSearchDefinitionHistory,
                        Supplier<java.util.List<String>> onGetSearchSlangHistory,
                        List<SearchedSlangWord> definitionLoadedHistory,
                        List<SearchedSlangWord> slangLoadedHistory,
                        BiPredicate<SlangWord, BooleanSupplier> onAddSlangWord,
                        Consumer<SlangWord> onUpdate, Consumer<String> onDelete, Runnable onReset, Runnable onSave,
                        Supplier<SlangWord> onRandom, Supplier<Question> onSlangPuzzle, Supplier<Question> onDefinitionPuzzle)
    {
        JTabbedPane mainPanel = new JTabbedPane();
        mainPanel.setFont(new Font( "Arial", Font.BOLD, 15 ));
        mainPanel.setTabPlacement(JTabbedPane.LEFT);
        //Create function panels
        mainPanel.add(SEARCH_DEFINITION, new SearchDefinitionPanel(SEARCH_DEFINITION, "Slang", onSearchDefinition, onSearchDefinitionSaving));
        mainPanel.add(SEARCH_SLANG, new SearchSlangWordPanel(SEARCH_SLANG, "Definition", onSearchSlang, onSearchSlangSaving));
        mainPanel.add(GET_HISTORY, new GetHistoryPanel(GET_HISTORY, onGetSearchDefinitionHistory, onGetSearchSlangHistory, definitionLoadedHistory, slangLoadedHistory));
        mainPanel.add(ADD_SLANG, new AddSlangWordPanel(ADD_SLANG, onSearchDefinition, onAddSlangWord));
        mainPanel.add(UPDATE_SLANG, new UpdateDeleteSlangWordPanel(UPDATE_SLANG, onSearchDefinition, onUpdate, onDelete));
        mainPanel.add(RANDOM, new RandomSlangWordPanel(RANDOM, onRandom));

        JTabbedPane puzzleTab = new JTabbedPane();
        puzzleTab.add(PUZZLE_SLANG, new PuzzlePanel(PUZZLE_SLANG, onSlangPuzzle));
        puzzleTab.add(PUZZLE_DEFINITION, new PuzzlePanel(PUZZLE_DEFINITION, onDefinitionPuzzle));
        mainPanel.add(PUZZLES, puzzleTab);

        mainPanel.add(SETTING, new SettingPanel(SETTING, onReset, onSave));
        this.getContentPane().add(mainPanel);
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.onSave = onSave;
        pack();
        setVisible(true);
    }

    @Override
    public void dispose()
    {
        //Saving the data before closing the program
        onSave.run();
        super.dispose();
    }
}
