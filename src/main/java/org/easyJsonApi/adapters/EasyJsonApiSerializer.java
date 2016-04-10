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

import org.easyJsonApi.asserts.Assert;
import org.easyJsonApi.entities.Data;
import org.easyJsonApi.entities.JsonApi;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class EasyJsonApiSerializer extends EasyJsonApiMachine implements JsonSerializer<JsonApi> {

    @Override
    public JsonElement serialize(JsonApi request, Type typeOfSrc, JsonSerializationContext context) {

        JsonObject jsonElem = new JsonObject();

        JsonArray jsonArrayData = new JsonArray();

        for (Data requestData : request.getData()) {

            JsonObject jsonData = new JsonObject();

            if (Assert.notEmpty(requestData.getId())) {
                jsonData.addProperty("id", requestData.getId());
            }

            if (Assert.notEmpty(requestData.getType())) {
                jsonData.addProperty("type", requestData.getType());
            }

            if (Assert.notNull(requestData.getAttr())) {
                JsonElement jsonAttr = context.serialize(requestData.getAttr(), this.tokenTypesToUse.get(EasyJsonApiTypeToken.TOKEN_ATTR));
                jsonData.add("attributes", jsonAttr);
            }

            if (Assert.notNull(requestData.getRels())) {
                JsonElement jsonRels = context.serialize(requestData.getRels(), this.tokenTypesToUse.get(EasyJsonApiTypeToken.TOKEN_RELS));
                jsonData.add("relationships", jsonRels);
            }

            if (Assert.notNull(requestData.getLinks())) {
                JsonElement jsonLinks = context.serialize(requestData.getLinks(), this.tokenTypesToUse.get(EasyJsonApiTypeToken.TOKEN_LINKS));
                jsonData.add("links", jsonLinks);
            }

            jsonArrayData.add(jsonData);

        }

        jsonElem.add("data", jsonArrayData);

        return jsonElem;
    }

}
