/**
 * Copyright 2013 GOAI Framework http://goodoldai.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.goai.classification.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.goai.classification.api.Classifier;
import org.goai.core.GOAIComponent;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

/**
 * GOAI Classifier Wrapper for classifiers from Weka framework
 * @author Zoran Sevarac <sevarac@gmail.com>
 * TODO: use parametrized class Classifier<I, C>
 */
public class WekaClassifier extends GOAIComponent implements Classifier<double[], String> {

    /**
     * Weka interface Classifier
     */
    private weka.classifiers.Classifier wekaClassifier;
    
    /**
     * Weka Instances class as data set
     */
    private Instances wekaDataSet;
    
    /**
     * Set of possible class values that instance can be classified
     */
    private Set<String> classValues;
    
    /**
     * Map with class label as key and its mapped double value
     */
    private Map<String, Double> classVals;

    /**
     * Map class label with double value as key
     */
    private Map<Double, String> classValsDoubleAsKey;
    
    /**
     * Constructor WekaClassifier
     * @param wekaClassifier instance of classifier from Weka framework
     */
    public WekaClassifier(weka.classifiers.Classifier wekaClassifier) {
        this.wekaClassifier = wekaClassifier;
    }

    /**
     * Trains classifier with passed date set
     * @param itemClassMap Map<double[], String>
     */
    @Override
    public void buildClassifier(Map<double[], String> itemClassMap) {
        try {

            //Create instances out of Map
            setWekaDataSet(convertItemClassMapToInstances(itemClassMap));

            //buildClassifier method call for created instances
            wekaClassifier.buildClassifier(getWekaDataSet());

        } catch (Exception ex) {
            Logger.getLogger(WekaClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Classify item to one of classes
     * @param item double[]
     * @return String label of class
     */
    @Override
    public String classify(double[] item) {
        try {

            //Instance out of double array
            Instance testInstance = new DenseInstance(1, item);

            //DataSet set for instance
            testInstance.setDataset(wekaDataSet);

            //classifyInstance returns double
            Double classDoubleValue = wekaClassifier.classifyInstance(testInstance);
            
            return classValsDoubleAsKey.get(classDoubleValue);
        } catch (Exception ex) {
            Logger.getLogger(WekaClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Calculate values of probability for classes that item can be classified as
     * @param item double[]
     * @return Map<String, Double>
     */
    @Override
    public Map<String, Double> classDistribution(double[] item) {
        try {
            
            //Instance out of double array
            Instance testInstance = new DenseInstance(1, item);

            //DataSet set for instance
            testInstance.setDataset(wekaDataSet);

            //Map for class value and their predict value
            Map<String, Double> map = new HashMap<String, Double>();

            //Calculate predict values
            double[] predict = wekaClassifier.distributionForInstance(testInstance);

            //Number of class and predict values should be same
            if (classValues.size() != predict.length) {
                throw new RuntimeException("Class values Set should be same size as double array with predict values");
            }

            //fill map with class values and their predict values
            int i = 0;
            for (String val : classValues) {
                map.put(val, predict[i]);
                i++;
            }

            return map;
        } catch (Exception ex) {
            Logger.getLogger(WekaClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Converts map to weka data set
     * @param itemClassMap Map<double[], String>
     * @return Instances Weka data set
     */
    public Instances convertItemClassMapToInstances(Map<double[], String> itemClassMap) {

        if (itemClassMap.isEmpty()) {
            throw new RuntimeException("Map should have at least one element!");
        }

        //Get first row as example for mapping attributes from sample
        Map.Entry<double[], String> row = itemClassMap.entrySet().iterator().next();

        //Number of attributes without class attribute
        int numOfAttr = row.getKey().length;

        //possible class values
        fillClassValues(itemClassMap);

        //Sample size
        int capacity = itemClassMap.entrySet().size();

        //Create empty Instances data set
        Instances newDataSet = createEmptyInstancesDataSet(numOfAttr, capacity);

        //Set class attribute index
        newDataSet.setClassIndex(numOfAttr);

        //Iterating through sample rows
        for (Map.Entry<double[], String> entry : itemClassMap.entrySet()) {

            //double array of values for particular class as String
            double[] el = entry.getKey();
            String klasa = entry.getValue();

            //Instance of double array for values with class attribute value
            double[] rowValues = new double[numOfAttr + 1];

            //Values copy of common attributs
            for (int i = 0; i < numOfAttr; i++) {
                rowValues[i] = el[i];
            }

            //Double value copy of class attribute
            rowValues[numOfAttr] = classVals.get(klasa);

            //dataRow as instance of DenseInstance class, 1 as instance weight and values of all attributes
            Instance dataRow = new DenseInstance(1, rowValues);
            dataRow.setDataset(newDataSet);
            newDataSet.add(dataRow);
        }

        return newDataSet;
    }

    /**
     * Fills Set classValues with all possible values for instances
     * @param itemClassMap Map<double[], String>
     */
    private void fillClassValues(Map<double[], String> itemClassMap) {
        //Initilize Set for possible class values
        classValues = new HashSet<String>();

        //Fill Set with class values
        for (Map.Entry<double[], String> entry : itemClassMap.entrySet()) {
            classValues.add(entry.getValue());
        }
    }

    /**
     * Create empty weka data set
     * @param numOfAttr int Number of attributes without class attribute
     * @param capacity int Capacity of sample
     * @return Instances weka data set
     */
    private Instances createEmptyInstancesDataSet(int numOfAttr, int capacity) {
        //Vector for class attribute possible values
        FastVector fvClassVal = new FastVector();
        //Map double value for every possible class value
        classVals = new HashMap<String, Double>();
        //Map class label with double key value
        classValsDoubleAsKey = new HashMap<Double, String>();
        //ind represents double value for class attribute
        int ind = 0;
        //loop through possible class values
        for (String values : classValues) {

            //add value to vector
            fvClassVal.addElement(values);

            //map double value for class value
            classVals.put(values, new Double(ind));
            //map class label for double key value
            classValsDoubleAsKey.put(new Double(ind),values);

            ind++;
        }
        //Class attribute with possible values
        Attribute classAttribute = new Attribute("theClass", fvClassVal, classValues.size());
        //Creating attribute vector for Instances class instance
        FastVector fvWekaAttributes = new FastVector(numOfAttr + 1);
        //Fill vector with simple attributes
        for (int i = 0; i < numOfAttr; i++) {
            fvWekaAttributes.addElement(new Attribute(i + "", i));
        }
        //Add class attribute to vector
        fvWekaAttributes.addElement(classAttribute);

        //newDataSet as Instances class instance
        Instances newDataSet = new Instances("newDataSet", fvWekaAttributes, capacity);
        return newDataSet;
    }

    /**
     * @return the wekaDataSet
     */
    public Instances getWekaDataSet() {
        return wekaDataSet;
    }

    /**
     * @param wekaDataSet the wekaDataSet to set
     */
    public void setWekaDataSet(Instances wekaDataSet) {
        this.wekaDataSet = wekaDataSet;
    }
    
}
