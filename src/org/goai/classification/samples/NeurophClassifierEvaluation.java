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
import org.goai.classification.eval.ClassifierEvaluationBase;
import org.goai.classification.impl.NeurophClassifier;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

/**
 * This class implements classifier evaluation using Neuroph based classifier
 * Create GOAI Classifier from given neural network and run evaluation for given data set
 *  // provide neural net and data set files or instances which should be wrapped in GOAI 
 *  @author Zoran Sevarac <sevarac@gmail.com>
 */
public class NeurophClassifierEvaluation extends ClassifierEvaluationBase<double[], String, NeuralNetwork, DataSet> {
 
    String[] classNames; // temprary solution before we support column names in dataset
    
    public NeurophClassifierEvaluation(NeuralNetwork neuralNet, DataSet dataSet) {
        super(neuralNet, dataSet);        
    }
    
    /**
     * Wrap given neural network into GOAI NeurophClassifier wrapper
     * @param neuralNet
     * @return 
     */
    @Override
    protected Classifier<double[], String> createClassifier(NeuralNetwork neuralNet) {
        // create GOAI classifier with neural network
        Classifier<double[], String> goaiClassifier = new NeurophClassifier(neuralNet);        
        return goaiClassifier;
    }

    /**
     * Creates classification problem from given data set
     * @param dataSet
     * @return 
     */
    @Override    
    protected ClassificationProblem<double[], String> createClassificationProblem(DataSet dataSet) {

        ClassificationProblem<double[], String> classificationProblem = new ClassificationProblem<>("IrisClassification");

        for(DataSetRow row : dataSet.getRows()) {
            double[] out = row.getDesiredOutput();
            // get column names for outputs
            
            // TODO: propravi ovo!
//            String className="";
//            if (out[0] == 1) {
//                className = "Setosa";
//            } else if (out[1] == 1) {
//                className = "Versicolor";
//            } else if (out[2] == 1) {
//                className = "Virginica";
//            }

            String className="";
//            if (out[0] == 1) {
//                className = "LeftHand";
//            } else if (out[1] == 1) {
//                className = "RightHand";
//            } else if (out[2] == 1) {
//                className = "Foot";
//            } else {
//                className = "Rest";
//            }       
            
//            // get labels of out{put neurons!!!
            for(int i=0; i<out.length; i++) {
                if (out[i]==1) {
                    className = classNames[i];
                    break;
                }   
            }
            
            classificationProblem.add(row.getInput(), className);
        }        
        
        return classificationProblem;
    }

    public void setClassNames(String[] classNames) {
        this.classNames = classNames;
    }
    
    
    
          
}