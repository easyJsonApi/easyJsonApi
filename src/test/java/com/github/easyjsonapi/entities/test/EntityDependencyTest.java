/*
 * #%L
 * EasyJsonApi
 * %%
 * Copyright (C) 2016 EasyJsonApi
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.github.easyjsonapi.entities.test;

import java.util.HashMap;
import java.util.Map;

public class EntityDependencyTest {

    private Map<String, String> attr;

    /**
     * @return the attr
     */
    public Map<String, String> getAttr() {
        if (attr == null) {
            attr = new HashMap<>();
        }
        return attr;
    }

    /**
     * @param attr the attr to set
     */
    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }

}
