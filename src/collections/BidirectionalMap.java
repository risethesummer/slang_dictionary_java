package collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * collections
 * Created by NhatLinh - 19127652
 * Date 12/9/2021 - 1:18 PM
 * Description: Bidirectional Map based on HashMap can retrieve keys from values
 */
public class BidirectionalMap {

    private HashMap<String, String> mainMap;
    private HashMap<String, List<String>> oppositeMap;
    private BooleanSupplier duplicatedCallback;

    /**
     *
     * @param initialCapacity
     */
    public BidirectionalMap(int initialCapacity, BooleanSupplier duplicatedCallback)
    {
        mainMap = new HashMap<>(initialCapacity);
        oppositeMap = new HashMap<>(initialCapacity);
        this.duplicatedCallback = duplicatedCallback;
    }

    public void add(String slang, String definition)
    {
        //If the main map contains the slang
        //-> callback to ask overwrite the slang
        if (mainMap.containsKey(slang))
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
        mainMap.put(slang, definition);
        List<String> slangList = oppositeMap.get(definition);
        //If the opposite map has stored the definition
        // -> update the slang list
        if (slangList != null)
            slangList.add(slang);
            //Else create a new slang list for the definition
        else
        {
            List<String> newSlangList = new ArrayList();
            newSlangList.add(slang);
            oppositeMap.put(definition, newSlangList);
        }
    }


    public void update(String slang, String newDefinition)
    {
        String oldDefinition = mainMap.get(slang);
        //Add to replace the old definition
        mainMap.put(slang, newDefinition);
        //Add new slang to the new definition
        oppositeMap.get(newDefinition).add(slang);
        //Remove the slang from the old definition
        oppositeMap.get(oldDefinition).remove(oldDefinition);
    }

    public void load()
    {
        int a =5 ;
    }

}
