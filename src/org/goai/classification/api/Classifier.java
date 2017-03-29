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

package org.goai.classification.api;

import java.util.Map;

/**
 * This is the interface for all classifiers, which allows to specify
 * classification and item Java classes using generics. 
 * C is the classification Java class (or Enum) , and I is the Java class of item to classify
 * The intent of this interface is to provide application specific classification interface
 * (using Java classes to specify items and classification classes),
 * and decouple it from underlying framework specific implementation.
 * The idea is to provide generic, yet application specific classification API.
 * ..The benefit of using this interface is that rest of your application sees...
 * 
 * Inspired by the designs of
 * 
 *  Weka classifier
 *      http://weka.sourceforge.net/doc.dev/weka/classifiers/Classifier.html
 *  Java ML classifier
 *      http://java-ml.sourceforge.net/api/0.1.7/net/sf/javaml/classification/Classifier.html
 * 
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public interface Classifier<I, C> {
    
        /**
         * Initialise a classifier for the specified item-class mapping.
         * @param itemClassMap Map with items as keys and corresponding classification class as value
         */
        public void buildClassifier(Map<I, C> itemClassMap);
        
        /**
         * Classify the item and return corresponding classification class
         * @param item Item to classify
         * @return Item's class 
         */
        public C classify(I item);
        
        /**
         * Returns class membership distribution for specified item
         * @param item Item to classify
         * @return class membership distribution for specified item
         */
        public Map<C, Double> classDistribution(I item);
}