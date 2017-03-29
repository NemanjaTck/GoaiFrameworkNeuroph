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
 * 
 * todo: manage stratup, configuration of all components
 *       provide services to GOAI Compoennts
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public class GOAIRuntimeContainer implements Serializable {
    HashMap<String, GOAIComponent> components;
    GOAIConfiguration configuration; // configuration of container, which compponents it includes...
    
    public GOAIRuntimeContainer() {
        this.components = new HashMap<>();
    }
        
    public void addComponent(GOAIComponent component, String name) {
        // todo: check if name is null and give auto name
        component.setName(name);
        components.put(name, component);
    }
    
    public void removeComponnet(String name) {
        components.remove(name);
    }
    
    public GOAIComponent getComponent(String name) {
        return components.get(name);
    }
       
}
