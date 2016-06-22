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
package com.github.easyjsonapi.adapters;

import java.lang.reflect.Type;
import java.util.List;

import com.github.easyjsonapi.asserts.Assert;
import com.github.easyjsonapi.entities.Data;
import com.github.easyjsonapi.entities.DataLinkage;
import com.github.easyjsonapi.entities.Error;
import com.github.easyjsonapi.entities.JsonApi;
import com.github.easyjsonapi.entities.Relationship;
import com.github.easyjsonapi.entities.Relationships;
import com.github.easyjsonapi.exceptions.EasyJsonApiCastException;
import com.github.easyjsonapi.tools.JsonTools;
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

        List<Data> cloneData = null;
        List<Error> cloneError = null;

        if (Assert.notNull(jsonApi)) {

            cloneData = jsonApi.getData();
            cloneError = jsonApi.getErrors();

            if (!cloneData.isEmpty() && !cloneError.isEmpty()) {
                // TODO: throw an exception when lists have values in the
                // same time
            } else if (!cloneData.isEmpty()) {
                return serializerData(cloneData, jsonApi, jsonContext);
            } else if (!cloneError.isEmpty()) {
                return serializerError(cloneError, jsonApi, jsonContext);
            }

        }

        // TODO: Maybe it's best to throw an exception ??
        return jsonElem;

    }

    /**
     * Serializer when occur an success
     * 
     * @param cloneData
     *            the list with data cloned
     * @param jsonapi
     *            the json object
     * @param jsonContext
     *            the json context
     * @return the json api object with values created
     */
    private JsonElement serializerData(List<Data> cloneData, JsonApi jsonapi, JsonSerializationContext jsonContext) {

        JsonObject jsonElem = new JsonObject();

        JsonArray jsonArrayData = new JsonArray();

        for (Data jsonApiData : cloneData) {

            JsonObject jsonData = new JsonObject();

            if (Assert.notEmpty(jsonApiData.getId())) {
                jsonData.addProperty("id", jsonApiData.getId());
            }

            if (Assert.notEmpty(jsonApiData.getType())) {
                jsonData.addProperty("type", jsonApiData.getType());
            }

            if (Assert.notNull(jsonApiData.getAttr())) {
                JsonElement jsonAttr = serializerDataAttr(jsonApiData.getAttr(), jsonContext);
                jsonData.add("attributes", jsonAttr);
            }

            if (Assert.notNull(jsonApiData.getRels())) {
                JsonElement jsonRels = serializerDataRels(jsonApiData.getRels(), jsonContext);

                if (Assert.notNull(jsonRels)) {
                    jsonData.add("relationships", jsonRels);
                }

            }

            jsonArrayData.add(jsonData);

        }

        jsonElem.add("data", jsonArrayData);

        return jsonElem;

    }

    /**
     * Serializer attributes
     * 
     * @param attr
     *            the attributes object
     * @param jsonContext
     *            the json context
     * @return one instance of json
     */
    private JsonElement serializerDataAttr(Object attr, JsonSerializationContext jsonContext) {

        return serializerObject(attr, EasyJsonApiTypeToken.TOKEN_ATTR, jsonContext);

    }

    /**
     * Serializer relationship
     * 
     * @param rels
     *            the relationships object
     * @param jsonContext
     *            the json context
     * @return one instance of json
     */
    private JsonElement serializerDataRels(Relationships rels, JsonSerializationContext jsonContext) {

        JsonObject jsonRels = new JsonObject();

        if (Assert.isNull(rels) || rels.getRelationships().isEmpty()) {
            return null;
        }

        for (Relationship jsonApiRels : rels.getRelationships()) {

            if (Assert.notEmpty(jsonApiRels.getName())) {

                JsonObject jsonRel = new JsonObject();
                JsonObject jsonRelLink = null;
                JsonArray jsonRelData = null;
                JsonObject jsonRelMeta = null;

                // Build links json object
                if (Assert.notNull(jsonApiRels.getLinks())) {

                    jsonRelLink = new JsonObject();
                    JsonObject jsonRelLinkRelated = null;

                    if (Assert.notNull(jsonApiRels.getLinks().getLinkRelated())) {

                        jsonRelLinkRelated = new JsonObject();

                        JsonTools.insertObject(jsonRelLinkRelated, "href", jsonApiRels.getLinks().getLinkRelated().getHref());

                        if (Assert.notNull(jsonApiRels.getLinks().getLinkRelated().getMeta())) {
                            JsonElement jsonRelLinkMeta = serializerObject(jsonApiRels.getLinks().getLinkRelated().getMeta(),
                                    EasyJsonApiTypeToken.TOKEN_META, jsonContext);

                            JsonTools.insertObject(jsonRelLinkRelated, "meta", jsonRelLinkMeta);
                        }
                    }

                    JsonTools.insertObject(jsonRelLink, "self", jsonApiRels.getLinks().getSelf());
                    JsonTools.insertObject(jsonRelLink, "related", jsonRelLinkRelated);

                }

                // Build data json object
                if (Assert.notNull(jsonApiRels.getDataLinkage()) && !jsonApiRels.getDataLinkage().isEmpty()) {

                    jsonRelData = new JsonArray();

                    for (DataLinkage dataLinkage : jsonApiRels.getDataLinkage()) {
                        JsonObject jsonData = new JsonObject();
                        jsonData.addProperty("id", dataLinkage.getId());
                        jsonData.addProperty("type", dataLinkage.getType());

                        jsonRelData.add(jsonData);
                    }
                }

                // Build meta json object
                if (Assert.notNull(jsonApiRels.getMeta())) {

                    jsonRelMeta = serializerObject(jsonApiRels.getMeta(), EasyJsonApiTypeToken.TOKEN_META_RELATIONSHIP, jsonContext)
                            .getAsJsonObject();
                }

                JsonTools.insertObject(jsonRel, "links", jsonRelLink);
                JsonTools.insertObject(jsonRel, "data", jsonRelData);
                JsonTools.insertObject(jsonRel, "meta", jsonRelMeta);

                JsonTools.insertObject(jsonRels, jsonApiRels.getName(), jsonRel);

            }

        }

        return jsonRels;

    }

    /**
     * Serializer when occur an error
     * 
     * @param cloneError
     *            the list with errors cloned
     * @param jsonApi
     *            the json object
     * @param jsonContext
     *            the json context
     * @return the json api object with values created
     */
    private JsonElement serializerError(List<Error> cloneError, JsonApi jsonApi, JsonSerializationContext jsonContext) {

        JsonObject jsonElem = new JsonObject();

        JsonArray jsonArrayErrors = new JsonArray();

        for (Error jsonApiError : cloneError) {

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
     * Serializer object json for {@link EasyJsonApiTypeToken} sent to method
     * 
     * @param obj
     *            the object needs serializer
     * @param typeToken
     *            the type token to convert object
     * @param jsonContext
     *            the json context
     * @return one instance of json
     */
    private JsonElement serializerObject(Object obj, EasyJsonApiTypeToken typeToken, JsonSerializationContext jsonContext) {

        Type type = Assert.notNull(this.tokenTypesToUse.get(EasyJsonApiTypeToken.TOKEN_DEFAULT))
                ? this.tokenTypesToUse.get(EasyJsonApiTypeToken.TOKEN_DEFAULT) : this.tokenTypesToUse.get(typeToken);

        if (Assert.isNull(type)) {
            throw new EasyJsonApiCastException("Doesn't find token for " + obj.getClass().getName() + " resource object!");
        }

        return jsonContext.serialize(obj, type);
    }

}
