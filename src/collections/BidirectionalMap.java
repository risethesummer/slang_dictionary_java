package collections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * collections
 * Created by NhatLinh - 19127652
 * Date 12/9/2021 - 1:18 PM
 * Description: Bidirectional Map based on HashMap can retrieve keys from values
 */
public class BidirectionalMap {

    private HashMap<String, String> slangMap;
    private HashMap<String, List<String>> definitionMap;

    /**
     *
     * @param initialCapacity
     */
    public BidirectionalMap(int initialCapacity)
    {
        slangMap = new HashMap<>(initialCapacity);
        definitionMap = new HashMap<>(initialCapacity);
    }

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


    public void update(SlangWord slangWord)
    {
        String oldDefinition = slangMap.get(slangWord.word);
        //Add to replace the old definition
        slangMap.put(slangWord.word, slangWord.definition);
        //Add new slang to the new definition
        definitionMap.get(slangWord.definition).add(slangWord.word);
        //Remove the slang from the old definition
        definitionMap.get(oldDefinition).remove(oldDefinition);
    }

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

    public void loadStructuredFile(String slangPath, String defPath)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(slangPath));
            String line = reader.readLine();
            while (line != null)
            {
                line = reader.readLine();
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
            line = reader.readLine();
            while (line != null)
            {
                line = reader.readLine();
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
            //slangword`def 1`def 2`....`def n
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

    public void load(String path)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while (line != null)
            {
                line = reader.readLine();
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

    public HashMap<String, String> getSlangMap() {
        return slangMap;
    }

    public HashMap<String, List<String>> getDefinitionMap() {
        return definitionMap;
    }

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
