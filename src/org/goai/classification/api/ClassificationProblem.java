package org.goai.classification.api;

import java.util.HashMap;

/**
 * Use this class instead of classification hash map? (definitly yes!)
 *  @author Zoran Sevarac <sevarac@gmail.com>
 */
public class ClassificationProblem<I, C> {
    String name;
    HashMap<I, C> itemClassMap;
                  
    public ClassificationProblem(String name) {
        this.name = name;
        itemClassMap = new HashMap<>();
    }  
    
    public ClassificationProblem(String name, HashMap<I, C> itemClassMap) {
        this.name = name;
        this.itemClassMap = itemClassMap;
    }
    
    public void add(I item, C itemClass) {
        itemClassMap.put(item, itemClass);
    }
    
    public void remove(I item) {
        itemClassMap.remove(item);
    }
    
    public C getClassFor(I item) {
        return itemClassMap.get(item);
    }
        
    public HashMap<I, C> getItemClassMap() {
        return itemClassMap;
    }

    // TODO:
    // getClasses
    // getInputsForClass    
    
    
}
