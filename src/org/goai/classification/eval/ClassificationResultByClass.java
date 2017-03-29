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


import org.goai.classification.evalnn.ClassificationMeasure;

/**
 *
 *  @author Zoran Sevarac <sevarac@gmail.com>
 */
public class ClassificationResultByClass<C> {
     C classificationClass;
     C classificationClass2;
     
     int correctClassifications, wrongClassifications;
     int correctClassifications2, wrongClassifications2;
     double percentCorrect, percentWrong;
     double percentCorrect2, percentWrong2;
     
     ClassificationMeasure cm = new ClassificationMeasure();
    
    
//    int truePositives = 0;
//    int falseNegatives = 0;
//    int trueNegatives = 0;
//    int falsePositives = 0;    

    public ClassificationResultByClass(C classificationClass) {
        this.classificationClass = classificationClass;
    }
    
 

    public int getCorrectClassifications() {
        return correctClassifications;
    }
    public int getCorrectClassifications2() {
        return correctClassifications2;
    }

    public void setCorrectClassifications(int correctClassifications) {
        this.correctClassifications = correctClassifications;
    }
    
  
    public int getWrongClassifications() {
        return wrongClassifications;
    }

    public void setWrongClassifications(int wrongClassifications) {
        this.wrongClassifications = wrongClassifications;
    }

    public double getPercentCorrect() {
        return (double)correctClassifications / (correctClassifications + wrongClassifications) * 100;
    }

    public double getPercentWrong() {
        return (double)wrongClassifications / (correctClassifications + wrongClassifications) * 100;
    }    
        
    public void incCorrect() {
        correctClassifications++;
    }
    
    public void incWrong() {
        wrongClassifications++;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Class:" + classificationClass+ "\n");
        sb.append("Correct classifications:" + correctClassifications + "("+getPercentCorrect() + "%) \n");
        sb.append("Wrong classifications:" + wrongClassifications + "("+ getPercentWrong() + "%) \n");
        
        return sb.toString();
    }
    
    
    
    
    
}
