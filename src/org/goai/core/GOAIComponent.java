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

import java.io.File;
import java.io.Serializable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * This is the base class for all GOAI components. It provides meaningfull 
 * name and configuration for every GOAI component.
 * 
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public /*abstract*/ class GOAIComponent implements Serializable {
    
    // what goues here? hooks to GOAI system....
    GOAIConfiguration configuration = new GOAIConfiguration();
    
    String name;
    // properties file goes to {name}.properties which s loaded into configuration

    public GOAIComponent() {
    }
          
    public GOAIConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(GOAIConfiguration configuration) {
        this.configuration = configuration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void save(File file) {
        throw new NotImplementedException();
    }
    
    public static GOAIComponent createFromFile(File file) {
        throw new NotImplementedException();
    }
    
    
    
    
    /**
     * Retruns meaningfu; descriptopn for specific GOAI class
     * @return 
     */
   // public String getDescription();
    
}