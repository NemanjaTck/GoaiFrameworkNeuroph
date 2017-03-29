package org.goai.classification.samples;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.goai.classification.api.ClassificationProblem;
import org.goai.classification.api.Classifier;
import org.goai.classification.eval.ClassifierEvaluationBase;
import org.goai.classification.impl.WekaClassifier;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author vladimir
 */
public class UniversalEvaluator extends ClassifierEvaluationBase <double[], String, weka.classifiers.Classifier, Instances> {

    String[] classNames; // temprary solution before we support column names in dataset
    
    public UniversalEvaluator(weka.classifiers.Classifier classifierSrc, Instances problemSrc) {
        super(classifierSrc, problemSrc);
    }     
    
    /**
     * Creates and trains Weka classifier with an instance of MultilayerPerception classifier
     * and iris data set
     * @return Classifier
     */
    @Override
    public Classifier createClassifier(weka.classifiers.Classifier wekaClassifier) {
        try {
            // Inicialize GOAI Classifier with Weka classifier
            Classifier<double[], String> goaiClassifier = new WekaClassifier(wekaClassifier);
            
            //Process data from Map
            goaiClassifier.buildClassifier(makeMapOutOfInstances(super.getProblemSrc()));
            
            return goaiClassifier;
        } catch (Exception ex) {
            Logger.getLogger(UniversalEvaluator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Creates HashMap out of Weka data set
     * @return HashMap
     */
    @Override
    public ClassificationProblem createClassificationProblem(Instances wekaDataset) {
        try {
            // Convert Weka data set to HashMap
            HashMap<double[], String> classificationMap = makeMapOutOfInstances(wekaDataset);
            
            return new ClassificationProblem("Iris", classificationMap);
        } catch (Exception ex) {
            Logger.getLogger(UniversalEvaluator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Converts Weka data set to HashMap
     * @param jmlDataset Dataset to be converted
     * @return HashMap
     */
    private HashMap<double[], String> makeMapOutOfInstances(Instances wekaDataset) {
        
        //Get class index in given dataset
        int classIndex = wekaDataset.classIndex();
        
        //class index has to be set
        if(classIndex < 0){
            throw new RuntimeException("Class index has to be set!");
        }
        
        //map for instances values
        HashMap<double[], String> itemClassMap = new HashMap<double[],String>();
        
        //iterate through dataset
        for(Instance dataRow: wekaDataset){
            
            //initialize double array for values
            double[] mapKey = new double[wekaDataset.numAttributes()-1];
            
            //fill double array with values from instances
            for(int i = 0; i < wekaDataset.numAttributes()-1; i++){
                mapKey[i] = dataRow.value(i);
            }
            
            //Get class attribute as string value
            String clazz = dataRow.toString(dataRow.attribute(classIndex));
            
            //put entry in Map
            itemClassMap.put(mapKey, clazz);
        }
        
        return itemClassMap;
    }
    
    public void setClassNames(String[] classNames) {
        this.classNames = classNames;
    }
    
}
