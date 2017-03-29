/**
 * Copyright 2013 GOAI Framework http://goodoldai.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.goai.classification.impl;

import java.util.HashMap;
import java.util.Map;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import org.goai.classification.api.Classifier;
import org.goai.core.GOAIComponent;

/**
 *
 *  @author Zoran Sevarac <sevarac@gmail.com>
 */
public class JMLClassifier extends GOAIComponent implements Classifier<double[], String> {

    /**
     * Java-ML Classifier interface
     */
    private net.sf.javaml.classification.Classifier jmlClassifier;
    
    /**
     * Java-ML data set
     */
    private Dataset dataSet;
    
    /**
     * JMLClassifier constructor
     * @param jmlClassifier Classifier
     */
    public JMLClassifier(net.sf.javaml.classification.Classifier jmlClassifier) { 
        this.jmlClassifier = jmlClassifier;
    }       
    
    /**
     * Trains classifier with passed date set
     * @param itemClassMap Map<double[], String>
     */
    @Override
    public void buildClassifier(Map<double[], String> itemClassMap) {
        
        //convert Map to jml dataset
        dataSet = convertItemClassMapToJMLDataset(itemClassMap);
        
        //process data with classifier
        jmlClassifier.buildClassifier(dataSet);
    }

    /**
     * Classify item to one of classes
     * @param item double[]
     * @return String label of class
     */
    @Override
    public String classify(double[] item) {
        
        //initialize Instance from double array
        Instance testInstance = new DenseInstance(item);
        
        //classifiy testInstance
        Object classOfTestInstance = jmlClassifier.classify(testInstance);
        
        return (String) classOfTestInstance;
    }

    /**
     * Calculate values of probability for classes that item can be classified as
     * @param item double[]
     * @return Map<String, Double>
     */
    @Override
    public Map<String, Double> classDistribution(double[] item) {
        
        //initialize Instance from double array
        Instance testInstance = new DenseInstance(item);
        
        //calculate predict values for class values
        Map<Object,Double> map = jmlClassifier.classDistribution(testInstance);
        
        //convert map with Object key to map with String key
        Map<String, Double> mapWithStringAsKey = convertMapObjectKeyToMapStringKey(map);
        
        return mapWithStringAsKey;
    }

    /**
     * Convert map to Java-ML data set
     * @param itemClassMap Map<double[], String>
     * @return Dataset 
     */
    private Dataset convertItemClassMapToJMLDataset(Map<double[], String> itemClassMap) {
        
        if(itemClassMap.isEmpty()){
            throw new RuntimeException("Map should have at least one element!");
        }
        
        //initialize jml dataset
        Dataset jmlDataset = new DefaultDataset();
        
        //iterate through Map
        for(Map.Entry<double[],String> entry : itemClassMap.entrySet()){
            
            //initialize Instance from Map entry Key (double array) and Value (String) class value
            Instance dataRow = new DenseInstance(entry.getKey(), entry.getValue());
            
            //add Instance to jml dataset
            jmlDataset.add(dataRow);
        }
        
        return jmlDataset;
    }

    /**
     * Convert map with Objact as key to map with String as key
     * @param map Map<Object, Double>
     * @return Map<String, Double>
     */
    private Map<String, Double> convertMapObjectKeyToMapStringKey(Map<Object, Double> map) {
        Map<String,Double> predictValues = new HashMap<>();
        
        for(Map.Entry<Object,Double> entry : map.entrySet()){
            predictValues.put(entry.getKey().toString(), entry.getValue());
        }
        
        return predictValues;
    }
    
}