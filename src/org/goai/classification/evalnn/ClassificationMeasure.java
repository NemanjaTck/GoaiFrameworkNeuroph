/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.goai.classification.evalnn;
import static java.lang.Math.sqrt;
import java.lang.Object;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;

/**
 *
 * @author Nemanja
 */
public class ClassificationMeasure {
    double falsenegative;
    double falsepositive;
    double truenegative;
    double truepositive;
    
    //Default Constructor, all values will be zero
    public ClassificationMeasure() {
    }
    //Constructs a new measure using arguments
    public ClassificationMeasure(double falsenegative, double falsepositive, double truenegative, double truepositive) {
        this.falsenegative=falsenegative;
        this.falsepositive=falsepositive;
        this.truenegative=truenegative;
        this.truepositive=truepositive;
    }
    public double getAccuracy() {
        return (truepositive+truenegative)/(falsenegative+falsepositive+truenegative+truepositive);
    }
    //True positive rate, recall, sensitivity
     public double getTruePositiveRate() {
         return truepositive/(falsenegative+truepositive);
     }
     //Specifity, true negative rate
     public double getTrueNegativeRate() {
         return truenegative/(truenegative+falsepositive);
     }
     //Precision, predicted positive value
     public double getPrecision() {
         return truepositive/(truepositive+falsepositive);
     }
     //False positive rate,
     public double getFalsePositiveRate() {
         return falsepositive/(truenegative+falsepositive);
     }
     //False negative rate,
     public double getFalseNegativeRate() {
         return falsenegative/(falsenegative+truepositive);
     }
     
     //Total
     public double getTotal() {
         return falsenegative+falsepositive+truenegative+truepositive;
     }
     
     public double getFDR(){
         return falsepositive/(truepositive + falsepositive);       
     }
     
     public double getMatthewsCorrelationCoefficient(){
     return (truepositive*truenegative-falsepositive*falsenegative)/(sqrt((truepositive+falsepositive)*(truepositive+falsenegative)*(truenegative+falsepositive)*(truenegative+falsenegative)));
     } 
     
     public String toString() {
        StringBuilder sb = new StringBuilder();
        
        
        sb.append("Sensitivity or true positive rate (TPR): ").append(getTruePositiveRate()).append("\n");
        sb.append("Specificity (SPC) or true negative rate (TNR): ").append(getTrueNegativeRate()).append("\n");
        sb.append("Fall-out or false positive rate (FPR): ").append(getFalsePositiveRate()).append("\n");
        sb.append("False negative rate (FNR): ").append(getFalseNegativeRate()).append("\n");
        sb.append("Total items: ").append(getTotal()).append("\n");
        sb.append("Accuracy (ACC): ").append(getAccuracy()).append("\n");
        sb.append("Precision or positive predictive value (PPV): ").append(getPrecision()).append("\n");
        sb.append("False discovery rate (FDR): ").append(getFDR()).append("\n");
        sb.append("Matthews correlation Coefficient (MCC): ").append(getMatthewsCorrelationCoefficient()).append("\n");
        return sb.toString();
        
    }
     
     
}
