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
import java.util.Map.Entry;

import com.github.easyjsonapi.asserts.Assert;
import com.github.easyjsonapi.entities.Data;
import com.github.easyjsonapi.entities.DataLinkage;
import com.github.easyjsonapi.entities.Error;
import com.github.easyjsonapi.entities.HttpStatus;
import com.github.easyjsonapi.entities.JsonApi;
import com.github.easyjsonapi.entities.Link;
import com.github.easyjsonapi.entities.LinkRelated;
import com.github.easyjsonapi.entities.Nullable;
import com.github.easyjsonapi.entities.Relationship;
import com.github.easyjsonapi.entities.Relationships;
import com.github.easyjsonapi.entities.Source;
import com.github.easyjsonapi.exceptions.EasyJsonApiCastException;
import com.github.easyjsonapi.tools.JsonTools;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Class helping deserializer the json api specification
 * 
 * @author Nuno Bento (nbento.neves@gmail.com)
 */
public class EasyJsonApiDeserializer extends EasyJsonApiMachine implements JsonDeserializer<JsonApi> {

    @Override
    public JsonApi deserialize(JsonElement jsonElem, Type jsonType, JsonDeserializationContext jsonContext) {

        JsonApi request = new JsonApi();

        if (!jsonElem.isJsonNull()) {

            boolean existJsonFormat = jsonElem.getAsJsonObject().isJsonNull();
            if (!existJsonFormat) {
                if (Assert.notNull(jsonElem.getAsJsonObject().get("data"))) {
                    request = deserializerData(jsonElem, jsonContext);
                } else if (Assert.notNull(jsonElem.getAsJsonObject().get("errors"))) {
                    request = deserializerError(jsonElem, jsonContext);
                }
            }
        }

        return request;
    }

    /**
     * Deserializer when occur an success
     * 
     * @param jsonElem
     *            the json element
     * @param jsonContext
     *            the json context
     * @return the json api object with values created
     */
    private JsonApi deserializerData(JsonElement jsonElem, JsonDeserializationContext jsonContext) {

        JsonApi request = new JsonApi();

        // FIXME: Find solution for this validation
        if (this.tokenTypesToUse.containsKey(EasyJsonApiTypeToken.TOKEN_ATTR) || this.tokenTypesToUse.containsKey(EasyJsonApiTypeToken.TOKEN_META)
                || this.tokenTypesToUse.containsKey(EasyJsonApiTypeToken.TOKEN_DEFAULT)
                || this.tokenTypesToUse.containsKey(EasyJsonApiTypeToken.TOKEN_META_RELATIONSHIP)) {

            // Parse the attribute data
            JsonArray jsonArrayData = jsonElem.getAsJsonObject().get("data").getAsJsonArray();

            // Iterate the data list
            for (int index = 0; index < jsonArrayData.size(); index++) {

                JsonObject jsonData = jsonArrayData.get(index).getAsJsonObject();

                Relationships dataRels = Nullable.RELATIONSHIPS;
                Object dataAttr = Nullable.OBJECT;

                String dataId = JsonTools.getStringInsideJson("id", jsonData);
                String dataType = JsonTools.getStringInsideJson("type", jsonData);

                // Get the attributes json
                if (Assert.notNull(jsonData.get("attributes"))) {
                    dataAttr = deserializerDataAttributes(jsonData, jsonContext);
                }

                // Get the relationship json
                if (Assert.notNull(jsonData.get("relationships"))) {
                    dataRels = deserializerDataRelationship(jsonData, jsonContext);
                }

                Data jsonApiData = new Data(dataId, dataType, dataAttr, dataRels);
                request.addData(jsonApiData);
            }
        }

        return request;
    }

    /**
     * Deserializer attributes json object
     * 
     * @param jsonAttributes
     *            the attributes json object
     * @param jsonContext
     *            the json context
     * @return one instance of {@link Object}
     */
    private Object deserializerDataAttributes(JsonObject json, JsonDeserializationContext jsonContext) {

        return deserializerObject("attributes", json, EasyJsonApiTypeToken.TOKEN_ATTR, jsonContext);

    }

    /**
     * Deserializer relationships json object
     * 
     * @param jsonRelationships
     *            the relationship json object
     * @param jsonContext
     *            the json context
     * @return one instance of {@link Relationships}
     */
    private Relationships deserializerDataRelationship(JsonObject json, JsonDeserializationContext jsonContext) {

        Relationships relationships = new Relationships();
        Link link = Nullable.LINK;
        LinkRelated linkRelated = Nullable.LINK_RELATED;
        Object metaRelated = Nullable.OBJECT;

        JsonObject jsonRels = json.get("relationships").getAsJsonObject();

        for (Entry<String, JsonElement> jsonRelationship : jsonRels.entrySet()) {

            Relationship relationship = Nullable.RELATIONSHIP;

            JsonObject jsonRelationshipValue = jsonRelationship.getValue().getAsJsonObject();

            String jsonRelationshipKey = Assert.isNull(jsonRelationship.getKey()) ? null : jsonRelationship.getKey();

            if (Assert.notNull(jsonRelationshipValue)) {

                if (Assert.notEmpty(jsonRelationshipKey) && jsonRelationshipKey.toLowerCase().equals("meta")) {

                    metaRelated = deserializerObject("meta", jsonRelationshipValue, EasyJsonApiTypeToken.TOKEN_META, jsonContext);

                }

                JsonObject relsLinks = Assert.isNull(jsonRelationshipValue.get("links")) ? null
                        : jsonRelationshipValue.get("links").getAsJsonObject();
                JsonArray relsData = Assert.isNull(jsonRelationshipValue.get("data")) ? null : jsonRelationshipValue.get("data").getAsJsonArray();

                if (Assert.notNull(relsLinks)) {

                    String self = JsonTools.getStringInsideJson("self", relsLinks);
                    JsonObject related = Assert.isNull(relsLinks.get("related")) ? null : relsLinks.get("related").getAsJsonObject();

                    if (Assert.notNull(related)) {

                        String href = JsonTools.getStringInsideJson("href", related);

                        Object metaLinkRelated = deserializerObject("meta", related, EasyJsonApiTypeToken.TOKEN_META_RELATIONSHIP, jsonContext);

                        linkRelated = new LinkRelated(href, metaLinkRelated);

                    }

                    link = new Link(linkRelated, self);
                }

                relationship = new Relationship(jsonRelationshipKey, link, metaRelated);

                if (Assert.notNull(relsData) && Assert.notNull(relationships)) {

                    for (JsonElement data : relsData) {
                        JsonObject dataRels = data.getAsJsonObject();

                        String id = JsonTools.getStringInsideJson("id", dataRels);
                        String type = JsonTools.getStringInsideJson("type", dataRels);

                        relationship.addDataLinkage(new DataLinkage(id, type));
                    }
                }

                relationships.getRelationships().add(relationship);
            }
        }

        return relationships;
    }

    /**
     * Deserializer when occur an error
     * 
     * @param jsonElem
     *            the json element
     * @param jsonContext
     *            the json context
     * @return the json api object with values created
     */
    private JsonApi deserializerError(JsonElement jsonElem, JsonDeserializationContext jsonContext) {

        JsonApi request = new JsonApi();

        JsonArray jsonArrayErrors = jsonElem.getAsJsonObject().get("errors").getAsJsonArray();

        // Iterate the errors list
        for (int index = 0; index < jsonArrayErrors.size(); index++) {

            JsonObject jsonError = jsonArrayErrors.get(index).getAsJsonObject();

            String jsonApiErrorDetail = JsonTools.getStringInsideJson("detail", jsonError);
            String jsonApiErrorCode = JsonTools.getStringInsideJson("code", jsonError);
            String jsonApiErrorTitle = JsonTools.getStringInsideJson("title", jsonError);
            String jsonApiErrorId = JsonTools.getStringInsideJson("id", jsonError);

            Source jsonApiErrorSource = null;
            HttpStatus jsonApiErrorStatus = null;

            // Get the source json
            if (Assert.notNull(jsonError.get("source"))) {
                JsonObject jsonErrorSource = jsonError.get("source").getAsJsonObject();
                jsonApiErrorSource = jsonContext.deserialize(jsonErrorSource, Source.class);
            }

            // Get the http status json
            if (Assert.notNull(jsonError.get("status"))) {
                JsonObject jsonErrorStatus = jsonError.get("status").getAsJsonObject();
                jsonApiErrorStatus = HttpStatus.getStatus(Integer.parseInt(jsonErrorStatus.getAsString()));
            }

            Error jsonApiError = new Error(jsonApiErrorId, jsonApiErrorTitle, jsonApiErrorStatus, jsonApiErrorCode, jsonApiErrorDetail,
                    Nullable.OBJECT, jsonApiErrorSource);

            request.addError(jsonApiError);
        }

        return request;
    }

    /**
     * Deserializer object json for {@link EasyJsonApiTypeToken} sent to method
     * 
     * @param name
     *            the name of json element need extract
     * @param json
     *            the json object
     * @param typeToken
     *            the type token to convert json
     * @param jsonContext
     *            the json context
     * @return one instance of typeToken sent
     */
    private Object deserializerObject(String name, JsonObject json, EasyJsonApiTypeToken typeToken, JsonDeserializationContext jsonContext) {

        Object objDeserialized = Nullable.OBJECT;

        if (Assert.notNull(json.get(name))) {
            JsonObject jsonObject = json.get(name).getAsJsonObject();

            Type type = Assert.notNull(this.tokenTypesToUse.get(EasyJsonApiTypeToken.TOKEN_DEFAULT))
                    ? this.tokenTypesToUse.get(EasyJsonApiTypeToken.TOKEN_DEFAULT) : this.tokenTypesToUse.get(typeToken);

            if (Assert.isNull(type)) {
                throw new EasyJsonApiCastException("Doesn't find token for " + name + " resource object inside json!");
            }

            objDeserialized = jsonContext.deserialize(jsonObject, type);
        }

        return objDeserialized;

    }

}
