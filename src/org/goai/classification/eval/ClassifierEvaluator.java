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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.goai.classification.api.ClassificationProblem;
import org.goai.classification.api.Classifier;

/**
 * This class contains general procedure for running classifier evaluation.
 * It provides a methods for running classifier evaluation on a single classifier, 
 * and on collection (List) of classifiers.
 * These methods expect classifier instance (implementation of Classifier interface)
 * and classification problem given as (item, class) mapping, as input arguments.
 * 
 * TODO: add method for running classifier evaluation for multiple problems
 *       add method for running multiple classifier evaluation for multiple problems  
 * 
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public class ClassifierEvaluator<I, C> {

    /**
     * Evaluates specified classifier for specified classification problem
     * @param classifier classifier to evaluate
     * @param itemClassMap classification problem given as (item,class) mapping
     * @return results of classifier evaluation as ClassifierEvaluationResults object
     */
    public ClassifierEvaluationResult evaluate(Classifier<I, C> classifier, ClassificationProblem<I, C> classificationProblem) {

        // get all different classification classes from classification map
        List<C> classes = new ArrayList();
        for (C c : classificationProblem.getItemClassMap().values()) {
            // iterate all items from map
            if (!classes.contains(c)) {
                // if we dont have it, add it to list of distinct classes
                classes.add(c);
            }
        }

        // create evaluation results object
        ClassifierEvaluationResult evaluationResult = new ClassifierEvaluationResult(classes);

        // run classification using given classifier on each map item
        // and generate classiifcation result
        for (I item : classificationProblem.getItemClassMap().keySet()) { 
            // iterate all items from map
            C targetClass = classificationProblem.getClassFor(item);
            // classify current item
            C classifedClass = classifier.classify(item); 
            // create classification result for current item
            ClassificationResult result = new ClassificationResult(item, targetClass, classifedClass);
            // add that classification result to overall results
            evaluationResult.addResultItem(result);
        }

        return evaluationResult;
    }
    
    /**
     * Evaluates a collection of classifiers for specified classification problem
     * @param classifiers list of classifiers to evaluate
     * @param itemClassMap classification problem given as item->class mapping
     * @return results of classifier evaluation
     */
    public HashMap<Classifier<I, C>, ClassifierEvaluationResult<I, C>> evaluate(List<Classifier<I, C>> classifiers, ClassificationProblem<I, C> classificationProblem) {
        // create map to hold evaluation results, classifier as key and result as value
        HashMap evaluationResultsMap = new HashMap();
        // iterate all classifiers and run evaluation on each of them
        for (Classifier classifier : classifiers) {
            // evaluate current classifier for give classification problem
            ClassifierEvaluationResult evaluationResult = evaluate(classifier, classificationProblem);
            // put classification results in results hash map
            evaluationResultsMap.put(classifier, evaluationResult);
        }

        return evaluationResultsMap;
    }    


}