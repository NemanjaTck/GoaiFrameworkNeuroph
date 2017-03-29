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

package org.goai.core;

import java.io.Serializable;
import java.util.HashMap;

/**
 * This class provides configuration settings for GOAI components.
 * It holds various configuration settings in a hashmap, which can be accessed 
 * with getSetting and setSetting methods.
 * 
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public class GOAIConfiguration implements Serializable {
    /**
     * Configuration settings as (name, value) pairs
     */
    HashMap<String, Object> settings = new HashMap();
    
    /**
     * Returns specified configuration setting
     * @param key name of the configuration setting
     * @return specified configuration setting
     */
    public Object getSetting(String key) {
        return settings.get(key);
    }
    
    /**
     * Sets value for the specified configuration setting
     * @param key
     * @param value 
     */
    public void setSetting(String key, Object value) {
        settings.put(key, value);
    }
    
}
