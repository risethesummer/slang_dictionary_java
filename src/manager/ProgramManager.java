package manager;

import collections.BidirectionalMap;
import collections.SearchedSlangWord;
import gui.ProgramFrame;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * manager
 * Created by NhatLinh - 19127652
 * Date 12/23/2021 - 11:49 AM
 * Description: The manager of the program using singleton pattern (mid-man between gui and data)
 */
public class ProgramManager {

    //Initial amount for the data map
    private final int MAX = 8000;

    private final String SLANG_PATH = "slangDefault.txt";
    private final String DEFINITION_PATH = "definitionDefault.txt";
    private final String ORIGINAL_PATH = "slang.txt";
    private final String HISTORY_DEFINITION_PATH = "historyDef.txt";
    private final String HISTORY_SlANG_PATH = "historySlang.txt";

    private static ProgramManager instance;

    private List<SearchedSlangWord> searchedDefinitions;
    private List<SearchedSlangWord> searchedSlangWords;

    private BidirectionalMap data = new BidirectionalMap(MAX);


    /**
     * Get the instance object of the manager class
     * @return the instance object
     */
    public static ProgramManager getInstance()
    {
        if (instance == null)
            instance = new ProgramManager();
        return instance;
    }

    private ProgramManager()
    {
        //If the first time loading the program -> use the original list
        //Else, use the structured files
        if (new File(SLANG_PATH).isFile() && new File(DEFINITION_PATH).isFile())
            data.loadStructuredFile(SLANG_PATH, DEFINITION_PATH);
        else
            data.load(ORIGINAL_PATH);
        loadHistory();

        //Callbacks for gui
        //Search definition and save history callback
        Function<String, String> onSearchDefinition = (s)->{
            String result = data.getSlangMap().get(s);
            if (result != null)
                searchedDefinitions.add(new SearchedSlangWord(s, result, LocalDateTime.now()));
            return result;
        };

        //Search slang word and save history callback
        Function<String, String> onSeachSlangWords = (s) ->
        {
            List<String> result = data.getDefinitionMap().get(s);
            if (result != null)
            {
                String strResult = "";
                for (String slang : result)
                {
                    searchedSlangWords.add(new SearchedSlangWord(s, slang, LocalDateTime.now()));
                    strResult += slang + '\n';
                }
                return strResult;
            }
            return null;
        };

        //Get search definition history callback
        Supplier<List<String>> onGetDefinitionHistory = () ->
        {
            try
            {
                List<String> words = new ArrayList<>(searchedDefinitions.size());
                for (SearchedSlangWord w : searchedDefinitions)
                    words.add(w.toString());
                return words;
            }
            catch (Exception e)
            {
                return null;
            }
        };

        //Get search slang word history callback
        Supplier<List<String>> onGetSlangHistory = () ->
        {
            try
            {
                List<String> words = new ArrayList<>(searchedSlangWords.size());
                for (SearchedSlangWord w : searchedSlangWords)
                    words.add(w.toString());
                return words;
            }
            catch (Exception e)
            {
                return null;
            }
        };

        //Create the main frame
        new ProgramFrame(onSearchDefinition, data.getSlangMap()::get, onSeachSlangWords, data::getSlangWords, onGetDefinitionHistory, onGetSlangHistory, searchedDefinitions, searchedSlangWords, data::add, data::update, data::delete, this::resetData, this::saveData, data::random, data::randomSlangQuestion, data::randomDefinitionQuestion);
    }

    private void resetData()
    {
        data.clear();
        data.load(ORIGINAL_PATH);
    }

    private void saveData()
    {
        try
        {
            //Save current data
            data.saveFile(SLANG_PATH, DEFINITION_PATH);

            //Save search history
            //Save search definition history
            ObjectOutputStream defStream = new ObjectOutputStream(new FileOutputStream(HISTORY_DEFINITION_PATH));
            defStream.writeObject(searchedDefinitions);
            defStream.flush();
            defStream.close();
            //Save search slang word history
            ObjectOutputStream slangStream = new ObjectOutputStream(new FileOutputStream(HISTORY_SlANG_PATH));
            slangStream.writeObject(searchedSlangWords);
            slangStream.flush();
            slangStream.close();
        }
        catch (Exception e)
        {

        }
    }

    private void loadHistory()
    {
        try
        {
            ObjectInputStream defStream = new ObjectInputStream(new FileInputStream(HISTORY_DEFINITION_PATH));
            searchedDefinitions = (ArrayList<SearchedSlangWord>)defStream.readObject();
            ObjectInputStream slangStream = new ObjectInputStream(new FileInputStream(HISTORY_SlANG_PATH));
            searchedSlangWords = (ArrayList<SearchedSlangWord>)slangStream.readObject();
        }
        catch (Exception e)
        {
            searchedDefinitions = new ArrayList<>();
            searchedSlangWords = new ArrayList<>();
        }
    }

    /**
     * Run the program (get the instance object of the manager class)
     * @param args command line arguments (not used)
     */
    public static void main(String[] args)
    {
        ProgramManager.getInstance();
    }
}
