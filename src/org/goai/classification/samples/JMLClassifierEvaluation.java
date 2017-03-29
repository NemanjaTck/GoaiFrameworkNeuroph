package org.goai.classification.samples;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.data.FileHandler;
import org.goai.classification.api.ClassificationProblem;
import org.goai.classification.api.Classifier;
import org.goai.classification.eval.ClassifierEvaluationBase;
import org.goai.classification.impl.JMLClassifier;


/**
 *
 * @author vladimir
 */
public class JMLClassifierEvaluation extends ClassifierEvaluationBase<double[], String, net.sf.javaml.classification.Classifier, Dataset> {

    
    String[] classNames; // temprary solution before we support column names in dataset
    
    public JMLClassifierEvaluation(net.sf.javaml.classification.Classifier classifierSrc, Dataset problemSrc) {
        super(classifierSrc, problemSrc);
    }
    
    
    
    /**
     * Creates and trains Java-ML classifier with an instance of NaiveBayes classifier
     * and iris data set
     * @return Classifier
     */
    @Override
    public Classifier createClassifier(net.sf.javaml.classification.Classifier jmlClassifier) {
        try {
            // Initialize Java-ML classifier with NaiveBayes classifier
            Classifier<double[], String> goaiClassifier = new JMLClassifier(jmlClassifier);
            goaiClassifier.buildClassifier(convertJMLDatasetToMap(super.getProblemSrc()));
            return goaiClassifier;
        } catch (Exception ex) {
            Logger.getLogger(JMLClassifierEvaluation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Creates HashMap out of Java-ML data set
     * @return HashMap
     */
    @Override
    public ClassificationProblem createClassificationProblem(Dataset src) {
        try {
            // Convert Java-ML data set to HashMap
            HashMap<double[],String> itemClassMap = convertJMLDatasetToMap(src);
            
            return new ClassificationProblem("Iris", itemClassMap);
        } catch (Exception ex) {
            Logger.getLogger(JMLClassifierEvaluation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Converts Java-ML data set to HashMap
     * @param jmlDataset Dataset to be converted
     * @return HashMap
     */
    private HashMap<double[], String> convertJMLDatasetToMap(Dataset jmlDataset) {
        
        //number of attributes without class attribute
        int numOfAttributes = jmlDataset.noAttributes();
        
        //initialize map
        HashMap<double[],String> itemClassMap = new HashMap<double[], String>();
        
        //iterate through jml dataset
        for(Instance dataRow : jmlDataset){
            
            //initialize double array for values from dataset
            double[] values = new double[numOfAttributes];
            int ind = 0;
            
            //iterate through values in dataset instance an adding them in double array
            for(Double val : dataRow){
                values[ind] = val;
                ind++;
            }
            
            //put attribute values and class value in map
            itemClassMap.put(values, dataRow.classValue().toString());
        }
        return itemClassMap;
    }
    
    public void setClassNames(String[] classNames) {
        this.classNames = classNames;
    }
    
}
