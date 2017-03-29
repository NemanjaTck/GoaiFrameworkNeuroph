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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.goai.classification.api.Classifier;
import org.goai.core.GOAIComponent;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

/**
 * This class is an implementation of the Classifier interface that uses 
 * neural network from the Neuroph framework.
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public class NeurophClassifier extends GOAIComponent implements Classifier<double[], String> {
    
    /**
     * Neural network that actually does the classification
     */    
    private NeuralNetwork neuralNet; // wrap the compnent that does all the stuff as an attribute
    
    /**
     * Set of possible class values
     */
    private Set<String> classValues;
    
    /**
     * Map of possible class and their double values
     */
    private Map<String, Double> classVals;
    
    /**
     * Creates an instance of the NeurophClassifier with specified neural network from Neuroph framework.
     * @param neuralNet neural network from Neuroph framework that provides required classification
     */
    public NeurophClassifier(NeuralNetwork neuralNet) { 
        this.neuralNet = neuralNet;
    }

    /**
     * Creates an instance of the NeurophClassifier from the specified file which
     * is saved neural network from Neuroph framework.
     * @param nnFileName 
     */    
    public NeurophClassifier(String nnFileName) { 
        this.neuralNet = NeuralNetwork.load(nnFileName);
    }    
    
    /**
     * Set neural network for this classifier
     * @param neuralNet neural network to use
     */
    public void setNeuralNetwork(NeuralNetwork neuralNet) {
        this.neuralNet = neuralNet; 
    }

    /**
     * Returns neural network used by this classifier
     * @return neural network used by this classifier
     */
    public NeuralNetwork getNeuralNet() {
        return neuralNet;
    }
    
    /**
     * Trains neural network with data from itemClassMap
     * @param itemClassMap Map<double[], String>
     */
    @Override
    public void buildClassifier(Map<double[], String> itemClassMap) {
        DataSet dataSet = convertItemClassMapToDataset(itemClassMap);
        neuralNet.learn(dataSet);
    }

    /**
     * Returns the class name (as String) based on the specified input properties
     * @param item item to classify, a double array of input properties 
     * @return label of class that is most probable for item to be classified as
     */
    @Override
    public String classify(double[] item) {
        // set neural network input
        neuralNet.setInput(item);
        // calculate neural network output
        neuralNet.calculate();
        
        // find neuron with highest output
        Neuron maxNeuron=null;
        double maxOut = Double.NEGATIVE_INFINITY;
        for(Neuron neuron : neuralNet.getOutputNeurons()) {
            if (neuron.getOutput() > maxOut) {
                maxNeuron = neuron;
                maxOut = neuron.getOutput();
            }
        }
                           
        // and return its label
        return maxNeuron.getLabel();
    }

    /**
     * Calculate values that represent probability of item
     * being classified with particular class
     * @param item double[]
     * @return values of class probability
     */
    @Override
    public Map<String, Double> classDistribution(double[] item) {
        // set neural network input
        neuralNet.setInput(item);
        // calculate neural network output
        neuralNet.calculate();
        
        // find neuron with highest output
        Map<String, Double> possibilities = new HashMap<String, Double>();
        
        for(Neuron neuron : neuralNet.getOutputNeurons()) {
            possibilities.put(neuron.getLabel(), neuron.getOutput());
        }
        return possibilities;
    }

    /**
     * Convert map to neuroph data set
     * @param itemClassMap Map<double[], String>
     * @return DataSet
     */
    private DataSet convertItemClassMapToDataset(Map<double[], String> itemClassMap) {
        
        double[] entryRow = itemClassMap.entrySet().iterator().next().getKey();
        
        fillClassValues(itemClassMap);

        //Map double value for every possible class value
        classVals = new HashMap<String, Double>();

        //ind represents double value for class attribute
        int ind = 0;

        //loop through possible class values
        for (String values : classValues) {

            //map double value for class value
            classVals.put(values, new Double(ind));
            ind++;
        }
        
        DataSet neurophDataSet = new DataSet(entryRow.length, 1);
        
        for(Map.Entry<double[],String> entry : itemClassMap.entrySet()){
            double[] out = new double[1];
            out[0] = classVals.get(entry.getValue());
            DataSetRow dataSetRow = new DataSetRow(entry.getKey(), out);
            neurophDataSet.addRow(dataSetRow);
        }
        
        return neurophDataSet;
    }
    
    /**
     * Fill Set of possible class values
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

}
