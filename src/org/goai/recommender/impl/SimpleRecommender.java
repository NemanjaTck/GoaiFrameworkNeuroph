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

package org.goai.recommender.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.goai.recommender.api.Recommender;

/**
 *
 * @author zoran
 */
public class SimpleRecommender  implements Recommender<URL, String> {
    private HashMap<URL, String> rules= new HashMap();
    
    public SimpleRecommender(HashMap<URL, String> rules) {
        this.rules = rules;
    }
    
    @Override
    public List<String> getRecommendations(List<URL> data) {
        ArrayList list = new ArrayList(1);
        list.add(rules.get(data.get(0)));
        return list;
    }

 
    
}
