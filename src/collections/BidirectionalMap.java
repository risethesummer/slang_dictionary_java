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

/**
 * collections
 * Created by NhatLinh - 19127652
 * Date 12/9/2021 - 1:18 PM
 * Description: Bidirectional Map based on HashMap can retrieve keys from values
 */
public class BidirectionalMap {

    private HashMap<String, String> slangMap;
    private HashMap<String, List<String>> definitionMap;
    private BooleanSupplier duplicatedCallback;

    /**
     *
     * @param initialCapacity
     */
    public BidirectionalMap(int initialCapacity, BooleanSupplier duplicatedCallback)
    {
        slangMap = new HashMap<>(initialCapacity);
        definitionMap = new HashMap<>(initialCapacity);
        this.duplicatedCallback = duplicatedCallback;
    }

    public void add(String slang, String definition)
    {
        //If the main map contains the slang
        //-> callback to ask overwrite the slang
        if (slangMap.containsKey(slang))
        {
            //If the user approves to overwrite
            //->update the slang
            if (duplicatedCallback.getAsBoolean())
                update(slang, definition);
            return;
        }

        addNotCheck(slang, definition);
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


    public void update(String slang, String newDefinition)
    {
        String oldDefinition = slangMap.get(slang);
        //Add to replace the old definition
        slangMap.put(slang, newDefinition);
        //Add new slang to the new definition
        definitionMap.get(newDefinition).add(slang);
        //Remove the slang from the old definition
        definitionMap.get(oldDefinition).remove(oldDefinition);
    }

    public void loadStructredFile(String slangPath, String defPath)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(slangPath));
            String line = reader.readLine();
            while (line != null)
            {
                line = reader.readLine();
                String[] parts = line.split("`");
                slangMap.put(parts[0], parts[1]);
            }
            reader.close();

            //Read slang words of a definition
            //slangword`def 1`def 2`....`def n
            reader = new BufferedReader(new FileReader(defPath));
            line = reader.readLine();
            while (line != null)
            {
                line = reader.readLine();
                String[] parts = line.split("`");

                List<String> words = new ArrayList<>(parts.length - 1);
                definitionMap.put(parts[0], words);
                for (int i = 1; i < parts.length; i++)
                    words.add(parts[i]);
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
                String[] parts = line.split("`");
                addNotCheck(parts[0], parts[1]);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
