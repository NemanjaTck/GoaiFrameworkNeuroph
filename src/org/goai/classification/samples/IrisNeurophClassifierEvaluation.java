package org.goai.classification.samples;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;

/**
 * ovaj je konacni!!!!
 * NOTE: Output neurons must have labels that correspond to classification classes
 * @author zoran
 */
public class IrisNeurophClassifierEvaluation {

    public static void main(String[] args) {        
         String nnFileName = IrisNeurophClassifierSample.class.getResource("IrisClassifier.nnet").getFile();
        // load neural network from file
        NeuralNetwork neuralNet = NeuralNetwork.load(nnFileName);
        // set labels manualy
        neuralNet.getOutputNeurons()[0].setLabel("Setosa");
        neuralNet.getOutputNeurons()[1].setLabel("Versicolor");
        neuralNet.getOutputNeurons()[2].setLabel("Virginica");
        neuralNet.save(nnFileName);        
        
        neuralNet = NeuralNetwork.load(IrisNeurophClassifierEvaluation.class.getResource("IrisClassifier.nnet").getFile());
        DataSet dataSet = DataSet.createFromFile(IrisNeurophClassifierEvaluation.class.getResource("iris_data_normalised.txt").getPath(), 4, 3, ",");
              
        NeurophClassifierEvaluation evaluation = 
                new NeurophClassifierEvaluation(neuralNet, dataSet); 
        
        String[] classNames = {"Setosa", "Versicolor", "Virginica"}; // these should be set either from output neurons or data set...        
        evaluation.setClassNames(classNames);
        
        evaluation.run();
        
        
    }      
    
}
