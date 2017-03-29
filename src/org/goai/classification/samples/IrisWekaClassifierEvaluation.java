/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.goai.classification.samples;

import static java.lang.Math.sqrt;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.goai.classification.eval.ClassifierEvaluationResult;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 *
 * @author vladimir
 */
public class IrisWekaClassifierEvaluation {

    
    
    
    public static void main(String[] args) {
        
        try {
            

            DataSource source = new DataSource("src/org/goai/classification/samples/iris.arff");
            Instances wekaDataset = source.getDataSet();
            wekaDataset.setClassIndex(4);
            
            WekaClassifierEvaluation evaluation =
                    new WekaClassifierEvaluation(new MultilayerPerceptron(), wekaDataset);

            String[] classNames = {"Setosa", "Versicolor", "Virginica"}; // these shoul dbe set either from output neurons or data set...        
            evaluation.setClassNames(classNames);
            //dodati da mogu da se promene 
            
            evaluation.run();

            
            } catch (Exception ex) {
            Logger.getLogger(IrisJMLClassifierEvaluation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
