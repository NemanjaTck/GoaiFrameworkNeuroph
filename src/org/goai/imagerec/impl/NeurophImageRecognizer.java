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

package org.goai.imagerec.impl;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import org.goai.imagerec.api.ImageRecognizer;
import org.goai.core.GOAIComponent;
import org.neuroph.core.NeuralNetwork;
//import org.neuroph.imgrec.ImageRecognitionPlugin;

/**
 *
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public class NeurophImageRecognizer extends GOAIComponent implements ImageRecognizer {
    NeuralNetwork neuralNet; // wrap the compnent that does all the stuff as an attribute
    
    public NeurophImageRecognizer(NeuralNetwork neuralNet) { // costructor with wrapped component as parameter
        this.neuralNet = neuralNet;
    }
    
    public void setNeuralNetwork(NeuralNetwork neuralNet) {
        this.neuralNet = neuralNet; 
    }

    public NeuralNetwork getNeuralNet() {
        return neuralNet;
    }
                
    @Override
    public HashMap<String, Double> recognizeImage(BufferedImage image) {
        // fali jar
//        ImageRecognitionPlugin irPlugin = (ImageRecognitionPlugin)neuralNet.getPlugin(ImageRecognitionPlugin.class);
//        return irPlugin.recognizeImage(image);
        return null;
    }
        
}
