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
import java.util.Set;
import org.goai.classification.evalnn.ClassificationMeasure;
import org.goai.classification.samples.IrisWekaClassifierEvaluation;

/**
 * TODO:
 *  po klasama: koliko ih je prepoznato a nije trebalo
 *              koliko ih nije prepoznatoa trebalo je  - zbir ova dav je ukupni broj gresaka...
 *  @author Zoran Sevarac <sevarac@gmail.com>
 */
public class ClassifierEvaluationResult<I, C> {
    int totalClasses = 0;
    int totalItems = 0;
    
    int totalCorrect = 0; // sum of true positives and true negatives
    int totalWrong = 0;   // false positives and false negatives 
    
    public static double allCorrect;
    
    
    
    C targetCl;
    
    
    //TODO: add TP, FN, TN, FP - an calulate all related measuresas in Java ML PerformanceMeasure
    // http://java-ml.sourceforge.net/api/0.1.7/net/sf/javaml/classification/evaluation/PerformanceMeasure.html
    // dodaj polja i sve treba da se azurira u add metodi!
    
    
    List<C> classes;
    List<ClassificationResult<I, C>> classificationResults;      
    HashMap<C, ClassificationResultByClass> resultsByClass;
    
       
    public ClassifierEvaluationResult(List<C> classes) {
        this.classes = classes;
        totalClasses = classes.size();
        
        
        classificationResults = new ArrayList<>();        
        resultsByClass = new HashMap<>();
        
               
    }
    

    void addResultItem(ClassificationResult<I, C> resultItem) {
        totalItems++;

        ClassificationResultByClass resultByClass = resultsByClass.get(resultItem.targetClass);
                targetCl = resultItem.targetClass;
        if (resultByClass == null) {
            resultByClass = new ClassificationResultByClass(resultItem.targetClass);
            resultsByClass.put(resultItem.targetClass, resultByClass);
        }
        
        if (resultItem.isCorrect) {
            totalCorrect++;
            resultByClass.incCorrect();
        } else {
            totalWrong++;
            resultByClass.incWrong();
        }
        
        if (!classes.contains(resultItem.targetClass)) {
            classes.add(resultItem.targetClass);
        }
        
        classificationResults.add(resultItem);
            
    }
    

    public int getTotalClasses() {
        return totalClasses;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getTotalCorrect() {
        return totalCorrect;
    }

    public int getTotalWrong() {
        return totalWrong;
    }

    public double getCorrectPercent() {
        return ((double)totalCorrect / (double)totalItems) * 100;
    }
    
    public double getWrongPercent() {
        return ((double)totalWrong / (double)totalItems) * 100;
    }     

    public HashMap<C, ClassificationResultByClass> getResultsByClass() {
        return resultsByClass;
    }
    
    public Set<C> getClasses() {
        return resultsByClass.keySet();
    }
    
    
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ClassifierEvaluationBase.valuesArrayTotalCorrect[0]=totalCorrect;
        sb.append("Total Classes: ").append(totalClasses).append("\n");
        sb.append("Total Items to Classify: ").append(totalItems).append("\n");
        sb.append("Total Correct Classifications: ").append(totalCorrect).append(" (").append(getCorrectPercent()).append("%) \n");
        sb.append("Total Wrong Classifications: ").append(totalWrong).append(" (").append(getWrongPercent()).append("%) \n");
        
        sb.append("\nClassifications by class \n\n");
        
        for (ClassificationResultByClass byClass : resultsByClass.values()) {
            //Kreira objekat nase klase ClassificationMeasure sa parametrima byClass za odredjenu klasu i totalCorrect i Wrong koji su globalni
           
            ClassificationMeasure cm = new ClassificationMeasure(totalWrong - byClass.wrongClassifications, byClass.wrongClassifications, totalCorrect - byClass.correctClassifications, byClass.correctClassifications);
           // allCorrect = allCorrect + totalCorrect;
           // allWrong = allWrong + totalWrong;
            //da ispisuje broj iteracija
            for(int i = 0; i<ClassifierEvaluationBase.valuesArrayTotalCorrect.length; i++){
                if(ClassifierEvaluationBase.valuesArrayTotalCorrect[i]==0){
                    ClassifierEvaluationBase.valuesArrayTotalCorrect[i] = totalCorrect;
                    break;
                }
                
                      }
           
            sb.append(byClass.toString());
            sb.append(cm.toString()+"\n");
            
            
        }
        
         
        
        return sb.toString();
        
    }

}