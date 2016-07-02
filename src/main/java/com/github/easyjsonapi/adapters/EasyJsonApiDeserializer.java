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
import com.github.easyjsonapi.entities.EJAData;
import com.github.easyjsonapi.entities.EJADataLinkage;
import com.github.easyjsonapi.entities.EJAError;
import com.github.easyjsonapi.entities.EJAHttpStatus;
import com.github.easyjsonapi.entities.JsonApi;
import com.github.easyjsonapi.entities.EJALink;
import com.github.easyjsonapi.entities.EJALinkRelated;
import com.github.easyjsonapi.entities.EJANullable;
import com.github.easyjsonapi.entities.EJARelationship;
import com.github.easyjsonapi.entities.EJARelationships;
import com.github.easyjsonapi.entities.EJASource;
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

                EJARelationships dataRels = EJANullable.RELATIONSHIPS;
                Object dataAttr = EJANullable.OBJECT;

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

                EJAData jsonApiData = new EJAData(dataId, dataType, dataAttr, dataRels);
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
     * @return one instance of {@link EJARelationships}
     */
    private EJARelationships deserializerDataRelationship(JsonObject json, JsonDeserializationContext jsonContext) {

        EJARelationships relationships = new EJARelationships();
        EJALink link = EJANullable.LINK;
        EJALinkRelated linkRelated = EJANullable.LINK_RELATED;
        Object metaRelated = EJANullable.OBJECT;

        JsonObject jsonRels = json.get("relationships").getAsJsonObject();

        for (Entry<String, JsonElement> jsonRelationship : jsonRels.entrySet()) {

            EJARelationship relationship = EJANullable.RELATIONSHIP;

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

                        linkRelated = new EJALinkRelated(href, metaLinkRelated);

                    }

                    link = new EJALink(linkRelated, self);
                }

                relationship = new EJARelationship(jsonRelationshipKey, link, metaRelated);

                if (Assert.notNull(relsData) && Assert.notNull(relationships)) {

                    for (JsonElement data : relsData) {
                        JsonObject dataRels = data.getAsJsonObject();

                        String id = JsonTools.getStringInsideJson("id", dataRels);
                        String type = JsonTools.getStringInsideJson("type", dataRels);

                        relationship.addDataLinkage(new EJADataLinkage(id, type));
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

            EJASource jsonApiErrorSource = null;
            EJAHttpStatus jsonApiErrorStatus = null;

            // Get the source json
            if (Assert.notNull(jsonError.get("source"))) {
                JsonObject jsonErrorSource = jsonError.get("source").getAsJsonObject();
                jsonApiErrorSource = jsonContext.deserialize(jsonErrorSource, EJASource.class);
            }

            // Get the http status json
            if (Assert.notNull(jsonError.get("status"))) {
                JsonObject jsonErrorStatus = jsonError.get("status").getAsJsonObject();
                jsonApiErrorStatus = EJAHttpStatus.getStatus(Integer.valueOf(jsonErrorStatus.getAsString()));
            }

            EJAError jsonApiError = new EJAError(jsonApiErrorId, jsonApiErrorTitle, jsonApiErrorStatus, jsonApiErrorCode, jsonApiErrorDetail,
                    EJANullable.OBJECT, jsonApiErrorSource);

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

        Object objDeserialized = EJANullable.OBJECT;

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
