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
package org.easyJsonApi.tools;

import org.easyJsonApi.asserts.Assert;
import org.easyJsonApi.exceptions.EasyJsonApiRuntimeException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Tool helps with json, it has all method need to validate json structure and wrap objects
 * 
 * @author Nuno Bento (nbento.neves@gmail.com)
 */
public class JsonTools {

    /**
     * Get string inside json
     * 
     * @param name
     *            the name of attribute
     * @param json
     *            the json object
     * @return string with json object value
     */
    public static String getStringInsideJson(String name, JsonObject json) {

        if (Assert.isNull(json.get(name))) {
            return null;
        }

        return json.get(name).getAsString();

    }

    /**
     * Put json object inside the json structure submitted
     * 
     * @param jsonObj
     *            the concat json structure
     * @param name
     *            the name of json attribute
     * @param objectInsert
     *            the json object needs to insert inside json structure, you must choose two types:
     *            String or JsonElement otherwise you will get {@link EasyJsonApiRuntimeException} exception.
     */
    public static void insertObject(JsonObject jsonObj, String name, Object objectInsert) {

        if (Assert.notNull(jsonObj)) {
            if (Assert.notNull(objectInsert)) {

                if (objectInsert instanceof String) {
                    if (Assert.notEmpty((String) objectInsert)) {
                        jsonObj.addProperty(name, (String) objectInsert);
                    }
                } else if (objectInsert instanceof JsonElement) {
                    jsonObj.add(name, (JsonElement) objectInsert);
                } else {
                    throw new EasyJsonApiRuntimeException("Invalid object inserted!");
                }
            }
        }

    }

}
