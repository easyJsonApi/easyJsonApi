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
package org.easyJsonApi.adapters;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.easyJsonApi.tools.EasyJsonApiConfig;

import com.google.gson.reflect.TypeToken;

/**
 * Abstract class with logic to decide which classes
 * {@link EasyJsonApiDeserializer} and {@link EasyJsonApiSerializer} will use to
 * create the json api structure, independently if you convert one json string
 * to object or vice-versa
 * 
 * @author Nuno Bento (nbento.neves@gmail.com)
 */
public abstract class EasyJsonApiMachine {

    private LinkedList<Class<?>> classesUsedInJson = new LinkedList<>();

    private EasyJsonApiConfig config;

    protected Map<EasyJsonApiTypeToken, Type> tokenTypesToUse = new HashMap<>();

    /**
     * Set classes need to use when create the json api structure
     * 
     * @param clazz the class or classes need to use
     */
    public void setClassesUsed(Class<?>... clazz) {

        this.tokenTypesToUse.clear();

        if (this.classesUsedInJson.isEmpty()) {
            this.classesUsedInJson.addAll(Arrays.asList(clazz));
        } else {
            if (this.classesUsedInJson.isEmpty()) {
                this.classesUsedInJson.addAll(Arrays.asList(clazz));
            } else {
                this.classesUsedInJson.addAll(Arrays.asList(clazz));
                this.classesUsedInJson.retainAll(Arrays.asList(clazz));
            }
        }

        if (this.config.getClassesParsed().isEmpty()) {
            // Use Map structure to support the default configuration
            Type mapType = TypeToken.get(Map.class).getType();
            this.tokenTypesToUse.put(EasyJsonApiTypeToken.TOKEN_DEFAULT, mapType);
        } else {
            for (Map.Entry<EasyJsonApiTypeToken, List<Class<?>>> clazzMapping : this.config.getClassesParsed().entrySet()) {
                List<Class<?>> classes = clazzMapping.getValue();

                for (Class<?> cla : classes) {
                    if (this.classesUsedInJson.contains(cla)) {
                        Type classType = TypeToken.get(cla).getType();
                        this.tokenTypesToUse.put(clazzMapping.getKey(), classType);
                    }
                }
            }
        }
    }

    /**
     * Set the {@link EasyJsonApiConfig}
     * 
     * @param config the configuration
     */
    public void setConfig(EasyJsonApiConfig config) {
        this.config = config;
    }

}
