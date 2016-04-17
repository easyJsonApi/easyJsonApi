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
import org.easyJsonApi.entities.Error;
import org.easyJsonApi.entities.JsonApi;
import org.easyJsonApi.entities.Source;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Class helps deserialize the request received for json api specification
 * 
 * @author nbento.neves@gmail.com
 */
public class EasyJsonApiDeserializer extends EasyJsonApiMachine implements JsonDeserializer<JsonApi> {

    @Override
    public JsonApi deserialize(JsonElement jsonElem, Type jsonType, JsonDeserializationContext jsonContext) throws JsonParseException {

        JsonApi request = new JsonApi();

        if (!jsonElem.isJsonNull()) {

            boolean existJsonFormat = jsonElem.getAsJsonObject().isJsonNull();

            if (!existJsonFormat) {
                if (Assert.notNull(jsonElem.getAsJsonObject().get("data"))) {
                    request = deserializerGeneric(jsonElem, jsonContext);
                } else if (Assert.notNull(jsonElem.getAsJsonObject().get("errors"))) {
                    request = deserializerError(jsonElem, jsonContext);
                }
            }
        }

        return request;
    }

    private JsonApi deserializerError(JsonElement jsonElem, JsonDeserializationContext jsonContext) {

        JsonApi request = new JsonApi();

        // Parse the attribute data
        JsonArray jsonArrayErrors = jsonElem.getAsJsonObject().get("errors").getAsJsonArray();

        // Iterate the data list
        for (int index = 0; index < jsonArrayErrors.size(); index++) {
            Error jsonApiError = new Error();

            JsonObject jsonError = jsonArrayErrors.get(index).getAsJsonObject();

            jsonApiError.setDetail(Assert.isNull(jsonError.get("detail")) ? null : jsonError.get("detail").getAsString());
            jsonApiError.setCode(Assert.isNull(jsonError.get("code")) ? null : jsonError.get("code").getAsString());
            jsonApiError.setTitle(Assert.isNull(jsonError.get("title")) ? null : jsonError.get("title").getAsString());
            jsonApiError.setId(Assert.isNull(jsonError.get("id")) ? null : jsonError.get("id").getAsString());

            // Get the source json
            if (Assert.notNull(jsonError.get("source"))) {
                JsonObject jsonErrorSource = jsonError.get("source").getAsJsonObject();
                Source objSource = jsonContext.deserialize(jsonErrorSource, Source.class);
                jsonApiError.setSource(objSource);
            }

            // Get the links json
            // if (Assert.notNull(jsonError.get("links"))) {
            // JsonObject jsonAttr = jsonError.get("links").getAsJsonObject();
            // Link objAttr = jsonContext.deserialize(jsonAttr, Link.class);
            // }

            request.getErrors().add(jsonApiError);
        }

        return request;
    }

    private JsonApi deserializerGeneric(JsonElement jsonElem, JsonDeserializationContext jsonContext) {

        JsonApi request = new JsonApi();

        // FIXME: Find solution for this validation
        if (this.tokenTypesToUse.containsKey(EasyJsonApiTypeToken.TOKEN_ATTR) || this.tokenTypesToUse.containsKey(EasyJsonApiTypeToken.TOKEN_RELS)
                || this.tokenTypesToUse.containsKey(EasyJsonApiTypeToken.TOKEN_META)) {

            // Parse the attribute data
            JsonArray jsonArrayData = jsonElem.getAsJsonObject().get("data").getAsJsonArray();

            // Iterate the data list
            for (int index = 0; index < jsonArrayData.size(); index++) {
                Data jsonApiData = new Data();

                JsonObject jsonData = jsonArrayData.get(index).getAsJsonObject();

                jsonApiData.setId(Assert.isNull(jsonData.get("id")) ? null : jsonData.get("id").getAsString());
                jsonApiData.setType(Assert.isNull(jsonData.get("type")) ? null : jsonData.get("type").getAsString());

                // Get the attributes json
                if (Assert.notNull(jsonData.get("attributes"))) {
                    JsonObject jsonAttr = jsonData.get("attributes").getAsJsonObject();
                    Object objAttr = jsonContext.deserialize(jsonAttr, this.tokenTypesToUse.get(EasyJsonApiTypeToken.TOKEN_ATTR));
                    jsonApiData.setAttr(objAttr);
                }

                // Get the relationships json
                if (Assert.notNull(jsonData.get("relationships"))) {
                    JsonObject jsonRels = jsonData.get("relationships").getAsJsonObject();
                    Object objRels = jsonContext.deserialize(jsonRels, this.tokenTypesToUse.get(EasyJsonApiTypeToken.TOKEN_RELS));
                    jsonApiData.setRels(objRels);
                }

                // Get the links json
                if (Assert.notNull(jsonData.get("links"))) {
                    JsonObject jsonLinks = jsonData.get("links").getAsJsonObject();
                    Object objLinks = jsonContext.deserialize(jsonLinks, this.tokenTypesToUse.get(EasyJsonApiTypeToken.TOKEN_LINKS));
                    jsonApiData.setLinks(objLinks);
                }

                request.getData().add(jsonApiData);
            }
        }

        return request;
    }

}
