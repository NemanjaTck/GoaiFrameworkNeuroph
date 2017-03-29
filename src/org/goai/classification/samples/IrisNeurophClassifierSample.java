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

package org.goai.classification.samples;

import org.goai.classification.api.ClassificationProblem;
import org.goai.classification.api.Classifier;
import org.goai.classification.eval.ClassifierEvaluationResult;
import org.goai.classification.eval.ClassifierEvaluator;
import org.goai.classification.impl.NeurophClassifier;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

/**
 * This sample shows how to do Iris classification with neural network from
 * Neuroph framework using GOAI NeurophClassifier.
 * 
 * One way to run classification from main method
 * 
 * @see Classifier
 * @author Zoran Sevarac <sevarac@gmail.com>
 * 
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * @deprecated Take a look at NeurophClassifierEvaluation and IrisNeurophClassifierEvaluation
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class IrisNeurophClassifierSample {
       
    
    /*
     * nnFileName
     * data set file 
     * override createClassificaationMap (make it abstract)
     */
    
    public static void main(String args[]) {
        (new  IrisNeurophClassifierSample()).run();                              
    }   
    
    public void run() {
        // get path to neural network file for iris classification
        String nnFileName = IrisNeurophClassifierSample.class.getResource("IrisClassifier.nnet").getFile();
        
        // load neural network from file
        NeuralNetwork neuralNet = NeuralNetwork.load(nnFileName);
        // set labels manualy
//        neuralNet.getOutputNeurons()[0].setLabel("Setosa");
//        neuralNet.getOutputNeurons()[1].setLabel("Versicolor");
//        neuralNet.getOutputNeurons()[2].setLabel("Virginica");
//        neuralNet.save(nnFileName);
        
        // create GOAI classifier with loaded Neuroph neural network
        Classifier<double[], String> goaiClassifier = new NeurophClassifier(neuralNet);
        
                  
        // load dataset and create map here
        DataSet evaluationDataSet =  DataSet.createFromFile(IrisNeurophClassifierSample.class.getResource("iris_data_normalised.txt").getPath(), 4, 3, ",");
        
        ClassificationProblem<double[], String> classificationProblem = createClassificationProblem(evaluationDataSet);
        
        ClassifierEvaluator<double[], String> evaluator = new ClassifierEvaluator<>();
        ClassifierEvaluationResult evaluationResults =  evaluator.evaluate(goaiClassifier, classificationProblem);        
        
        System.out.println(evaluationResults);
        
    }
    
    public ClassificationProblem createClassificationProblem(DataSet dataSet) {
        ClassificationProblem<double[], String> classificationProblem = new ClassificationProblem<>("IrisClassification");

        for(DataSetRow row : dataSet.getRows()) {
            double[] out = row.getDesiredOutput();
            String classif="";
            if (out[0] == 1) {
                classif = "Setosa";
            } else if (out[1] == 1) {
                classif = "Versicolor";
            } else if (out[2] == 1) {
                classif = "Virginica";
            }
                        
            classificationProblem.add(row.getInput(), classif);
        }        
        
        return classificationProblem;
    }
    

    
}