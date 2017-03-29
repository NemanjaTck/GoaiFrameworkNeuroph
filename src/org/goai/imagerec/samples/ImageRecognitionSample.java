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

package org.goai.imagerec.samples;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import org.goai.imagerec.api.ImageRecognizer;
import org.goai.imagerec.impl.NeurophImageRecognizer;
import org.neuroph.core.NeuralNetwork;

/**
 * This sample shows how to do image recognition with neural network from
 * Neuroph framework using GOAI ImageRecognizer
 * .
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public class ImageRecognitionSample {
    
    public static void main(String args[]) throws IOException {
        // get path to neural network file for iris classification
        String nnFileName = ImageRecognitionSample.class.getResource("resources/ImageRecNet.nnet").getFile();
        
        // load neural network from file
        NeuralNetwork neuralNet = NeuralNetwork.load(nnFileName);
        
        // create GOAI classifier with loaded Neuroph neural network
        ImageRecognizer imageRecognizer = new NeurophImageRecognizer(neuralNet);
        
        // test image recognition
        String imgFileName = ImageRecognitionSample.class.getResource("resources/dog.jpg").getFile();
        BufferedImage image = ImageIO.read(new File(imgFileName));
        
        HashMap<String, Double> recognitionResults = imageRecognizer.recognizeImage(image);
        
        String recImage="";
        double max = 0;
        for(String key : recognitionResults.keySet()) {
            if (recognitionResults.get(key) > max) {
                max = recognitionResults.get(key);
                recImage = key;
            }
        }
        
        System.out.println("Recognized image: "+recImage);

        
    }    
    
}
