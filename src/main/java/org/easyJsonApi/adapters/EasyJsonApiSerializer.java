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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Class helping serializer the json api specification
 * 
 * @author Nuno Bento (nbento.neves@gmail.com)
 */
public class EasyJsonApiSerializer extends EasyJsonApiMachine implements JsonSerializer<JsonApi> {

    @Override
    public JsonElement serialize(JsonApi jsonApi, Type typeOfSrc, JsonSerializationContext jsonContext) {

        JsonObject jsonElem = new JsonObject();

        if (Assert.notNull(jsonApi)) {
            if (!jsonApi.getData().isEmpty() && !jsonApi.getErrors().isEmpty()) {
                // TODO: throw an exception when lists have values in the same
                // time
            } else if (!jsonApi.getData().isEmpty()) {
                return serializerGeneric(jsonApi, jsonContext);
            } else if (!jsonApi.getErrors().isEmpty()) {
                return serializerError(jsonApi, jsonContext);
            }
        }

        // TODO: Maybe it's best to throw an exception ??
        return jsonElem;

    }

    /**
     * Serializer when occur an error
     * 
     * @param jsonApi the json object
     * @param jsonContext the json context
     * @return the json api object with values created
     */
    private JsonElement serializerError(JsonApi jsonApi, JsonSerializationContext jsonContext) {

        JsonObject jsonElem = new JsonObject();

        JsonArray jsonArrayErrors = new JsonArray();

        for (Error jsonApiError : jsonApi.getErrors()) {

            JsonObject jsonError = new JsonObject();

            if (Assert.notEmpty(jsonApiError.getId())) {
                jsonError.addProperty("id", jsonApiError.getId());
            }

            if (Assert.notEmpty(jsonApiError.getTitle())) {
                jsonError.addProperty("title", jsonApiError.getTitle());
            }

            if (Assert.notEmpty(jsonApiError.getCode())) {
                jsonError.addProperty("code", jsonApiError.getCode());
            }

            if (Assert.notEmpty(jsonApiError.getDetail())) {
                jsonError.addProperty("detail", jsonApiError.getDetail());
            }

            if (Assert.notNull(jsonApiError.getStatus())) {
                jsonError.addProperty("status", String.valueOf(jsonApiError.getStatus().getCode()));
            }

            if (Assert.notNull(jsonApiError.getSource())) {
                // TODO: Need to do
                // jsonError.addProperty("source", requestError.getDetail());
            }

            if (Assert.notNull(jsonApiError.getMeta())) {
                // TODO: Need to do
                // jsonError.addProperty("meta", requestError.getDetail());
            }

            jsonArrayErrors.add(jsonError);
        }

        jsonElem.add("errors", jsonArrayErrors);

        return jsonElem;

    }

    /**
     * Serializer when occur an success
     * 
     * @param jsonapi the json object
     * @param jsonContext the json context
     * @return the json api object with values created
     */
    private JsonElement serializerGeneric(JsonApi jsonapi, JsonSerializationContext jsonContext) {

        JsonObject jsonElem = new JsonObject();

        JsonArray jsonArrayData = new JsonArray();

        for (Data jsonApiData : jsonapi.getData()) {

            JsonObject jsonData = new JsonObject();

            if (Assert.notEmpty(jsonApiData.getId())) {
                jsonData.addProperty("id", jsonApiData.getId());
            }

            if (Assert.notEmpty(jsonApiData.getType())) {
                jsonData.addProperty("type", jsonApiData.getType());
            }

            if (Assert.notNull(jsonApiData.getAttr())) {

                Type type = Assert.notNull(this.tokenTypesToUse.get(EasyJsonApiTypeToken.TOKEN_DEFAULT))
                        ? this.tokenTypesToUse.get(EasyJsonApiTypeToken.TOKEN_DEFAULT) : this.tokenTypesToUse.get(EasyJsonApiTypeToken.TOKEN_ATTR);

                JsonElement jsonAttr = jsonContext.serialize(jsonApiData.getAttr(), type);
                jsonData.add("attributes", jsonAttr);
            }

            if (Assert.notNull(jsonApiData.getRels())) {

                Type type = Assert.notNull(this.tokenTypesToUse.get(EasyJsonApiTypeToken.TOKEN_DEFAULT))
                        ? this.tokenTypesToUse.get(EasyJsonApiTypeToken.TOKEN_DEFAULT) : this.tokenTypesToUse.get(EasyJsonApiTypeToken.TOKEN_RELS);

                JsonElement jsonRels = jsonContext.serialize(jsonApiData.getRels(), type);
                jsonData.add("relationships", jsonRels);
            }

            if (Assert.notNull(jsonApiData.getLinks())) {

                Type type = Assert.notNull(this.tokenTypesToUse.get(EasyJsonApiTypeToken.TOKEN_DEFAULT))
                        ? this.tokenTypesToUse.get(EasyJsonApiTypeToken.TOKEN_DEFAULT) : this.tokenTypesToUse.get(EasyJsonApiTypeToken.TOKEN_LINKS);

                JsonElement jsonLinks = jsonContext.serialize(jsonApiData.getLinks(), type);
                jsonData.add("links", jsonLinks);
            }

            jsonArrayData.add(jsonData);

        }

        jsonElem.add("data", jsonArrayData);

        return jsonElem;

    }

}
