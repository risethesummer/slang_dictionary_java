package manager;

import collections.BidirectionalMap;
import collections.SearchedSlangWord;
import collections.SlangWord;
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

    private final String ORIGINAL_PATH = "slang.txt";
    private final String SAVE_PATH = "save.dat";
    private final String HISTORY_DEFINITION_PATH = "historyDef.dat";
    private final String HISTORY_SlANG_PATH = "historySlang.dat";

    private static ProgramManager instance;

    private ArrayList<SearchedSlangWord> searchedDefinitions;
    private ArrayList<SearchedSlangWord> searchedSlangWords;
    private BidirectionalMap data;


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
        firstLoad();

        //Callbacks for gui
        //Search definition and save history callback
        Function<String, String> onSearchDefinition = (s)->{
            List<String> result = data.getSlangMap().get(s);
            if (result != null)
            {
                searchedDefinitions.add(new SearchedSlangWord(s, result, LocalDateTime.now()));
                return SlangWord.asString(result, "|");
            }
            return null;
        };

        //Search slang word and save history callback
        Function<String, String> onSeachSlangWords = (s) ->
        {
            List<String> result = data.getDefinitionMap().get(s);
            if (result != null)
            {
                searchedSlangWords.add(new SearchedSlangWord(s, result, LocalDateTime.now()));
                return SlangWord.asString(result, "\n");
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
        new ProgramFrame(onSearchDefinition, s -> SlangWord.asString(data.getSlangMap().get(s), "|"), onSeachSlangWords, s -> SlangWord.asString(data.getDefinitionMap().get(s), "\n"), onGetDefinitionHistory, onGetSlangHistory, searchedDefinitions, searchedSlangWords, data::add, data::update, data::delete, this::resetData, this::saveData, data::random, data::randomSlangQuestion, data::randomDefinitionQuestion);
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
            saveObject(data, SAVE_PATH);
            saveObject(searchedDefinitions, HISTORY_DEFINITION_PATH);
            saveObject(searchedSlangWords, HISTORY_SlANG_PATH);
        }
        catch (Exception e)
        {

        }
    }

    private void firstLoad()
    {
        try
        {
            Object map = loadObject(SAVE_PATH);
            //If the save file does not exist
            if (map == null)
            {
                data = new BidirectionalMap();
                data.load(ORIGINAL_PATH);
            }
            else
                data = (BidirectionalMap)map;

            Object defHistory = loadObject(HISTORY_DEFINITION_PATH);
            searchedDefinitions = defHistory == null ? new ArrayList<>() : (ArrayList)defHistory;

            Object slangHistory = loadObject(HISTORY_SlANG_PATH);
            searchedSlangWords = slangHistory == null ? new ArrayList<>() : (ArrayList)slangHistory;
        }
        catch (Exception e)
        {
        }
    }


    private Object loadObject(String path)
    {
        try
        {
            ObjectInputStream stream = new ObjectInputStream(new FileInputStream(path));
            return stream.readObject();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private void saveObject(Serializable serializable, String path)
    {
        try
        {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(path));
            stream.writeObject(serializable);
            stream.flush();
            stream.close();
        }
        catch (Exception e)
        {
            new File(path).delete();
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
