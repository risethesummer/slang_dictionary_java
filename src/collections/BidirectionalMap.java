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
public class BidirectionalMap {

    private TreeMap<String, String> slangMap;
    private TreeMap<String, List<String>> definitionMap;

    /**
     * Create a new map with initial capacity to increase performance when the size increases
     * @param initialCapacity the initial capacity
     */
    public BidirectionalMap(int initialCapacity)
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
            List<Map.Entry<String, String>> pairs = slangMap.entrySet().stream().toList();
            //Random an index of the generated array
            int randIndex = new Random().nextInt(pairs.size());
            Map.Entry<String, String> choice = pairs.get(randIndex);
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
        List<Map.Entry<String, String>> pairs = slangMap.entrySet().stream().toList();
        List<String> answers = new ArrayList<>(Question.NUMBER_OF_ANSWER);
        Random random = new Random();
        //Get indexes of the slang words used
        int[] randIndex = random.ints(Question.NUMBER_OF_ANSWER, 0, pairs.size()).toArray();
        //Choose the slang word for the user to guess
        int answerRandIndex = random.nextInt(randIndex.length);
        for (int i : randIndex)
            answers.add(pairs.get(i).getValue());
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
     * @param definition the definition of the word
     */
    public void addNotCheck(String slang, String definition)
    {
        slangMap.put(slang, definition);
        List<String> slangList = definitionMap.get(definition);
        //If the opposite map has stored the definition
        // -> update the slang list
        if (slangList != null)
            slangList.add(slang);
            //Else create a new slang list for the definition
        else
        {
            List<String> newSlangList = new ArrayList();
            newSlangList.add(slang);
            definitionMap.put(definition, newSlangList);
        }
    }


    /**
     * Update the definition of a slang word
     * @param slangWord contains the slang updated and the definition used to overwrite the old definition
     */
    public void update(SlangWord slangWord)
    {
        String oldDefinition = slangMap.get(slangWord.word);
        //Add to replace the old definition
        slangMap.put(slangWord.word, slangWord.definition);
        //Add new slang to the new definition
        List<String> slangWords = definitionMap.get(slangWord.definition);
        //If the slang words list does not exist
        if (slangWords == null)
        {
            slangWords = new ArrayList<>();
            definitionMap.put(slangWord.definition, slangWords);
        }
        slangWords.add(slangWord.word);
        Object m = definitionMap.get(oldDefinition);
        //Remove the slang from the old definition
        definitionMap.get(oldDefinition).remove(oldDefinition);
    }

    /**
     * Delete a slang word from the map
     * @param slangWord the deleted slang word
     */
    public void delete(String slangWord)
    {
        String definition = slangMap.get(slangWord);
        //Remove from the slang list
        slangMap.remove(slangWord);
        List<String> definitionList = definitionMap.get(definition);
        //Just remains 1 word -> delete the definition list from the definition map
        if (definitionList.size() == 1)
            definitionMap.remove(definition);
        else
            //Remove the slang from the definition list
            definitionList.remove(slangWord);
    }

    /**
     * Load the data of the map by a structured file constructed by the system
     * @param slangPath the path of the slang words file
     * @param defPath the path of the definitions file
     */
    public void loadStructuredFile(String slangPath, String defPath)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(slangPath));
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                try {
                    String[] parts = line.split("`");
                    slangMap.put(parts[0], parts[1]);
                }
                catch (Exception e)
                {
                    continue;
                }
            }
            reader.close();

            //Read slang words of a definition
            //slangword`def 1`def 2`....`def n
            reader = new BufferedReader(new FileReader(defPath));
            while ((line = reader.readLine()) != null)
            {
                try
                {
                    String[] parts = line.split("`");
                    List<String> words = new ArrayList<>(parts.length - 1);
                    definitionMap.put(parts[0], words);
                    for (int i = 1; i < parts.length; i++)
                        words.add(parts[i]);
                }
                catch (Exception e)
                {
                    continue;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Save the map to disk formatting the data to quickly load it later
     * @param slangPath the path saving the slang words
     * @param defPath the path saving the definitions
     */
    public void saveFile(String slangPath, String defPath)
    {
        try
        {
            BufferedWriter slangWriter = new BufferedWriter(new FileWriter(slangPath));
            slangMap.forEach((slang, definition)->{
                try
                {
                    slangWriter.write(slang + "`" + definition);
                    slangWriter.newLine();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            });
            slangWriter.flush();
            slangWriter.close();

            //Save slang words of a definition
            //definition'slang 1`....`slang n
            var defWriter = new BufferedWriter(new FileWriter(defPath));
            definitionMap.forEach((def, slang) -> {
                try
                {
                    defWriter.write(def + "`");
                    for (var s : slang)
                        defWriter.write(s + "`");
                    defWriter.newLine();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            });
            defWriter.flush();
            defWriter.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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
                    addNotCheck(parts[0], parts[1]);
                }
                catch (Exception e)
                {
                    continue;
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
    public Map<String, String> getSlangMap() {
        return slangMap;
    }

    /**
     * Get the definition map (definition -> slang word)
     * @return the definition map
     */
    public Map<String, List<String>> getDefinitionMap() {
        return definitionMap;
    }

    /**
     * Get the formatted list of slang words of a definition slang1 /n slang2 /n slang3
     * @param definition the definition used to get the slang words
     * @return the list of slang words (string format)
     */
    public String getSlangWords(String definition)
    {
        try
        {
            String result = "";
            for (String slang : definitionMap.get(definition))
                result += slang + '\n';
            return result;
        }
        catch (Exception e)
        {
            return "";
        }
    }
}
