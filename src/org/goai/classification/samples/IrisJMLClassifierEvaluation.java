/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.goai.classification.samples;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.javaml.classification.bayes.NaiveBayesClassifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;

/**
 *
 * @author vladimir
 */
public class IrisJMLClassifierEvaluation {
    
    public static void main(String[] args) {
        try {
            Dataset jmlDataset = FileHandler.loadDataset(new File("src/org/goai/classification/samples/iris.data"), 4, ",");
            
            JMLClassifierEvaluation evaluation = 
                    new JMLClassifierEvaluation(new NaiveBayesClassifier(true, true, true), jmlDataset); 
            
            String[] classNames = {"Setosa", "Versicolor", "Virginica"}; // these shoul dbe set either from output neurons or data set...        
            evaluation.setClassNames(classNames);
            
            evaluation.run();
            
            System.out.println(evaluation.getEvaluationResults());
        } catch (IOException ex) {
            Logger.getLogger(IrisJMLClassifierEvaluation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
