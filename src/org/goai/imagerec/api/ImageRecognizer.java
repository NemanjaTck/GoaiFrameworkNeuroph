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

package org.goai.imagerec.api;

import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public interface ImageRecognizer {
    
    // build recognizer? 
    public HashMap<String, Double>  recognizeImage(BufferedImage image);
}
