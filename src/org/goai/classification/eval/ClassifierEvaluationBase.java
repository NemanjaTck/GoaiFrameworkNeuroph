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

package org.goai.classification.eval;

import static java.lang.Math.sqrt;
import org.goai.classification.api.Classifier;
import org.goai.classification.api.ClassificationProblem;


/**
 * This class is abstract base class for all classifier evaluations.
 * To create specific classifier evaluation extend this class and implement following methods:
 *      createClassifier - which should create and return classifier instance to evaluate
 *      createClassificationProblem - which should create and retrun classification problem used for evaluation
 * 
 *  @author Zoran Sevarac <sevarac@gmail.com>
 */
public abstract class ClassifierEvaluationBase<ITEM_TYPE, CLASS_TYPE, CLASSIFIER_SRC, PROBLEM_SRC> {
    
    /**
     * Holds evaluation results
     */
    private ClassifierEvaluationResult<ITEM_TYPE, CLASS_TYPE> evaluationResults;
    
    
    private CLASSIFIER_SRC classifierSrc;
    private PROBLEM_SRC problemSrc;
    public int number = 100;
    static double sum = 0;
    static double max;
    static double min;
    public static double[] valuesArrayTotalCorrect= new double[100];
    
    public ClassifierEvaluationBase(CLASSIFIER_SRC classifierSrc, PROBLEM_SRC problemSrc) {
        this.classifierSrc = classifierSrc;
        this.problemSrc = problemSrc;        
    }    

    public PROBLEM_SRC getProblemSrc() {
        return problemSrc;
    }
    
    /**
     * This method should create and return instance of classifier to evaluate.
     * It wraps specific classifier implementation into GOAI Classifier interface
     * @return classifier to evaluate
     */
    protected abstract Classifier<ITEM_TYPE, CLASS_TYPE> createClassifier(CLASSIFIER_SRC src);
    
    /**
     * This method should return classification mapping with keys as items(inputs) and values as class
     * @return classification problem as map
     */
    protected abstract ClassificationProblem<ITEM_TYPE, CLASS_TYPE> createClassificationProblem(PROBLEM_SRC src);
    
    /**
     * Runs classifier evaluation. Implements generic classifier evaluation procedure:
     * 1. Create classifier
     * 2. Create classification map (classification problem)
     * 3. Run classifier evaluator
     */
    public void run() {
        
        for(int i = 0; i<number; i++){
        // create classifier instance
        Classifier classifier =  createClassifier(classifierSrc);
        // create classification problem
        ClassificationProblem<ITEM_TYPE, CLASS_TYPE> classificationProblem = createClassificationProblem(problemSrc);
        
        // create and run classifier evaluation for given problem
        ClassifierEvaluator<ITEM_TYPE, CLASS_TYPE> evaluator = new ClassifierEvaluator<>();
        evaluationResults = evaluator.evaluate(classifier, classificationProblem);
        System.out.println("--------------------------------------------------------------------------" );
        System.out.println("Iteration: " + i);
        System.out.println("--------------------------------------------------------------------------" );
        System.out.println(getEvaluationResults());
        
        }
        
            max = valuesArrayTotalCorrect[0];
            min = valuesArrayTotalCorrect[0];
            //Racunanje varijanse pomocu elemenata koje smo sacuvali u nizu
            for(int j = 0; j<valuesArrayTotalCorrect.length; j++){
                    sum = sum + (valuesArrayTotalCorrect[j]-(ClassifierEvaluationResult.allCorrect/100))*(valuesArrayTotalCorrect[j]-(ClassifierEvaluationResult.allCorrect/100));
                            
                  
            }
            //Racunanje ukupne sume
            for(int n = 0; n<valuesArrayTotalCorrect.length; n++){
                   ClassifierEvaluationResult.allCorrect = ClassifierEvaluationResult.allCorrect + valuesArrayTotalCorrect[n];     
                  
            }
            
            
            for(int k = 1; k<valuesArrayTotalCorrect.length; k++){
                    if(valuesArrayTotalCorrect[k]>max){
                        
                       max = valuesArrayTotalCorrect[k];
                    } 
                    if(valuesArrayTotalCorrect[k]<min){
                        
                        min = valuesArrayTotalCorrect[k];
                    }
                  
            }
        
        
        
        System.out.println("----------------------------------------------------");
            System.out.println("Classifier Statitics");
            System.out.println("----------------------------------------------------");
            System.out.println("Standard deviation:" + sqrt(sum/100));
            //Varijansa
            System.out.println("Variance:" + (sum/100));
            //Srednja vrednost
            System.out.println("Mean:" + ClassifierEvaluationResult.allCorrect/100);
            //Minimum i maksimum
            System.out.println("Maximum value: " + max);
            System.out.println("Minimum value: " +  min);
        
    }    
    
    /**
     * Returns classifier evaluation results
     * @return evaluation results
     */
    public ClassifierEvaluationResult<ITEM_TYPE, CLASS_TYPE> getEvaluationResults() {
        return evaluationResults;
    }
    
}
