package collections;
import java.io.*;
import java.util.*;
import java.util.function.BooleanSupplier;

/**
 * collections
 * Created by NhatLinh - 19127652
 * Date 12/9/2021 - 1:18 PM
 * Description: Bidirectional Map based on HashMap can retrieve keys from values
 */
public class BidirectionalMap implements Serializable {

    /**
     * Key->value: Slang word -> definitions
     */
    private final TreeMap<String, List<String>> slangMap;
    /**
     * Value->key: Definition -> slang words
     */
    private final TreeMap<String, List<String>> definitionMap;

    /**
     * Create a new map
     */
    public BidirectionalMap()
    {
        //Slang -> definition
        slangMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        //Definition -> list of slang words
        definitionMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    /**
     * Add a new slang word to the map but checking duplicated
     * @param slangWord the added slang word
     * @param duplicatedCallback the callback used to confirm overwriting the definition if the slang word exists in the system
     * @return true if the word was added successfully.
     * false if failed to add the word (exception or the user do not want to overwrite the word
     */
    public boolean add(SlangWord slangWord, BooleanSupplier duplicatedCallback)
    {
        //If the main map contains the slang
        //-> callback to ask overwrite the slang
        if (slangMap.containsKey(slangWord.word))
        {
            //If the user approves to overwrite
            //->update the slang
            if (!duplicatedCallback.getAsBoolean())
                return false;
            update(slangWord);
        }
        else
            addNotCheck(slangWord.word, slangWord.definition);
        return true;
    }

    /**
     * Get a random slang word from the map
     * @return the random word
     */
    public SlangWord random()
    {
        try
        {
            List<Map.Entry<String, List<String>>> pairs = slangMap.entrySet().stream().toList();
            //Random an index of the generated array
            int randIndex = new Random().nextInt(pairs.size());
            Map.Entry<String, List<String>> choice = pairs.get(randIndex);
            return new SlangWord(choice.getKey(), choice.getValue());
        } catch (Exception e)
        {
            return null;
        }
    }

    /**
     * Get a random question containing a slang word and 4 definitions (one of them is the definition of the word)
     * @return the random question (1 slang word and 4 definitions)
     */
    public Question randomSlangQuestion()
    {
        List<Map.Entry<String, List<String>>> pairs = slangMap.entrySet().stream().toList();
        List<String> answers = new ArrayList<>(Question.NUMBER_OF_ANSWER);
        Random random = new Random();
        //Get indexes of the slang words used
        int[] randIndex = random.ints(Question.NUMBER_OF_ANSWER, 0, pairs.size()).toArray();
        //Choose the slang word for the user to guess
        int answerRandIndex = random.nextInt(randIndex.length);
        for (int i : randIndex)
        {
            List<String> curDefs = pairs.get(i).getValue();
            int randDefinitionEachSlang = random.nextInt(curDefs.size());
            answers.add(curDefs.get(randDefinitionEachSlang));
        }
        return new Question(pairs.get(randIndex[answerRandIndex]).getKey(), answers, answerRandIndex);
    }

    /**
     * Get a random question containing a definition and 4 slang words (one of them is the slang word of the definition)
     * @return the random question (4 slang words and 1 definition)
     */
    public Question randomDefinitionQuestion()
    {
        List<Map.Entry<String, List<String>>> pairs = definitionMap.entrySet().stream().toList();
        Random random = new Random();
        //Contains the slang words (answer of the question)
        List<String> answers = new ArrayList<>(Question.NUMBER_OF_ANSWER);
        //Get indexes of the definition used
        int[] randIndex = random.ints(Question.NUMBER_OF_ANSWER, 0, pairs.size()).toArray();
        for (int i : randIndex)
        {
            //For each definition
            //Get the slang word answered to the slang word
            List<String> curSlang = pairs.get(i).getValue();
            int randIndexForSlang = random.nextInt(curSlang.size());
            answers.add(curSlang.get(randIndexForSlang));
        }
        //Choose the slang word for the user to guess
        int answerRandIndex = random.nextInt(randIndex.length);
        return new Question(pairs.get(randIndex[answerRandIndex]).getKey(), answers, answerRandIndex);
    }

    /**
     * Add a new slang word to the map without checking duplicated words
     * @param slang the added slang word
     * @param definition the definitions of the word
     */
    public void addNotCheck(String slang, List<String> definition)
    {
        slangMap.put(slang, definition);
        addDefinitions(definition, slang);
    }


    /**
     * Update the definition of a slang word
     * @param slangWord contains the slang updated and the definition used to overwrite the old definition
     */
    public void update(SlangWord slangWord)
    {
        List<String> oldDefinition = slangMap.get(slangWord.word);
        //Add to replace the old definition
        slangMap.put(slangWord.word, slangWord.definition);
        //Remove from the slang list of the definitions
        deleteSlangWordFromDefinition(oldDefinition, slangWord.word);
        //Replace for each definition
        addDefinitions(slangWord.definition, slangWord.word);
    }

    private void addDefinitions(List<String> definitions, String slangWord)
    {
        for (String def : definitions)
        {
            //Add new slang to the new definition
            List<String> slangWords = definitionMap.computeIfAbsent(def, k -> new ArrayList<>());
            //If the slang words list does not exist
            slangWords.add(slangWord);
        }
    }

    private void deleteSlangWordFromDefinition(List<String> definitions, String slangWord)
    {
        for (String def : definitions)
        {
            List<String> slangList = definitionMap.get(def);
            if (slangList != null)
            {
                if (slangList.size() == 1)
                    //If there is no slang words of the definition -> remove the slang list
                    definitionMap.remove(def);
                else
                    //Remove the slang from the old definition
                    slangList.remove(slangWord);
            }
        }
    }

    /**
     * Delete a slang word from the map
     * @param slangWord the deleted slang word
     */
    public void delete(String slangWord)
    {
        List<String> definition = slangMap.get(slangWord);
        //Remove from the slang map
        slangMap.remove(slangWord);
        //Remove from the slang list of the definitions
        deleteSlangWordFromDefinition(definition, slangWord);
    }


    /**
     * Clear the content of the map
     */
    public void clear()
    {
        slangMap.clear();
        definitionMap.clear();
    }


    /**
     * Load the data of the map from the original file (not structured)
     * @param path the path of loaded file
     */
    public void load(String path)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            //Ignore the first line
            reader.readLine();
            while ((line = reader.readLine()) != null)
            {
                try
                {
                    String[] parts = line.split("`");
                    String[] defs = parts[1].split("\\|");
                    for (int i = 0; i < defs.length; i++)
                        defs[i] = defs[i].trim();
                    addNotCheck(parts[0], Arrays.stream(defs).toList());
                }
                catch (Exception e)
                {
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Get the slang map (slang word -> definition)
     * @return the slang map
     */
    public Map<String, List<String>> getSlangMap() {
        return slangMap;
    }

    /**
     * Get the definition map (definition -> slang word)
     * @return the definition map
     */
    public Map<String, List<String>> getDefinitionMap() {
        return definitionMap;
    }
}
