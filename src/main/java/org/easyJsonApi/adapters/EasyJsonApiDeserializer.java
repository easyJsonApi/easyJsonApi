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
import org.easyJsonApi.entities.HttpStatus;
import org.easyJsonApi.entities.JsonApi;
import org.easyJsonApi.entities.Source;
import org.easyJsonApi.exceptions.EasyJsonApiCastException;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Class helping deserializer the json api specification
 * 
 * @author Nuno Bento (nbento.neves@gmail.com)
 */
public class EasyJsonApiDeserializer extends EasyJsonApiMachine
        implements JsonDeserializer<JsonApi> {

    @Override
    public JsonApi deserialize(JsonElement jsonElem, Type jsonType,
            JsonDeserializationContext jsonContext) throws JsonParseException {

        JsonApi request = new JsonApi();

        if (!jsonElem.isJsonNull()) {

            boolean existJsonFormat = jsonElem.getAsJsonObject().isJsonNull();
            try {
                if (!existJsonFormat) {
                    if (Assert
                            .notNull(jsonElem.getAsJsonObject().get("data"))) {
                        request = deserializerGeneric(jsonElem, jsonContext);
                    } else if (Assert.notNull(
                            jsonElem.getAsJsonObject().get("errors"))) {
                        request = deserializerError(jsonElem, jsonContext);
                    }
                }
            } catch (EasyJsonApiCastException exCast) {
                // TODO
                exCast.printStackTrace();
            }
        }

        return request;
    }

    /**
     * Deserializer when occur an error
     * 
     * @param jsonElem the json element
     * @param jsonContext the json context
     * @return the json api object with values created
     */
    private JsonApi deserializerError(JsonElement jsonElem,
            JsonDeserializationContext jsonContext) {

        JsonApi request = new JsonApi();

        // Parse the attribute data
        JsonArray jsonArrayErrors = jsonElem.getAsJsonObject().get("errors")
                .getAsJsonArray();

        // Iterate the data list
        for (int index = 0; index < jsonArrayErrors.size(); index++) {

            JsonObject jsonError = jsonArrayErrors.get(index).getAsJsonObject();

            String jsonApiErrorDetail = Assert.isNull(jsonError.get("detail"))
                    ? null : jsonError.get("detail").getAsString();
            String jsonApiErrorCode = Assert.isNull(jsonError.get("code"))
                    ? null : jsonError.get("code").getAsString();
            String jsonApiErrorTitle = Assert.isNull(jsonError.get("title"))
                    ? null : jsonError.get("title").getAsString();
            String jsonApiErrorId = Assert.isNull(jsonError.get("id")) ? null
                    : jsonError.get("id").getAsString();

            Source jsonApiErrorSource = null;
            HttpStatus jsonApiErrorStatus = null;

            // Get the source json
            if (Assert.notNull(jsonError.get("source"))) {
                JsonObject jsonErrorSource = jsonError.get("source")
                        .getAsJsonObject();
                jsonApiErrorSource = jsonContext.deserialize(jsonErrorSource,
                        Source.class);
            }

            // Get the http status json
            if (Assert.notNull(jsonError.get("status"))) {
                JsonObject jsonErrorStatus = jsonError.get("status")
                        .getAsJsonObject();
                jsonApiErrorStatus = HttpStatus.getStatus(
                        Integer.valueOf(jsonErrorStatus.getAsString()));
            }

            // Get the links json
            // if (Assert.notNull(jsonError.get("links"))) {
            // JsonObject jsonAttr = jsonError.get("links").getAsJsonObject();
            // Link objAttr = jsonContext.deserialize(jsonAttr, Link.class);
            // }

            Error jsonApiError = new Error(jsonApiErrorId, jsonApiErrorTitle,
                    jsonApiErrorStatus, jsonApiErrorCode, jsonApiErrorDetail,
                    Error.NULLABLE, jsonApiErrorSource);

            request.addError(jsonApiError);
        }

        return request;
    }

    /**
     * Deserializer when occur an success
     * 
     * @param jsonElem the json element
     * @param jsonContext the json context
     * @return the json api object with values created
     * @throws EasyJsonApiCastException
     */
    private JsonApi deserializerGeneric(JsonElement jsonElem,
            JsonDeserializationContext jsonContext)
            throws EasyJsonApiCastException {

        JsonApi request = new JsonApi();

        // FIXME: Find solution for this validation
        if (this.tokenTypesToUse.containsKey(EasyJsonApiTypeToken.TOKEN_ATTR)
                || this.tokenTypesToUse
                        .containsKey(EasyJsonApiTypeToken.TOKEN_RELS)
                || this.tokenTypesToUse
                        .containsKey(EasyJsonApiTypeToken.TOKEN_META)
                || this.tokenTypesToUse
                        .containsKey(EasyJsonApiTypeToken.TOKEN_DEFAULT)) {

            // Parse the attribute data
            JsonArray jsonArrayData = jsonElem.getAsJsonObject().get("data")
                    .getAsJsonArray();

            // Iterate the data list
            for (int index = 0; index < jsonArrayData.size(); index++) {

                JsonObject jsonData = jsonArrayData.get(index)
                        .getAsJsonObject();

                Object dataAttr = null;
                Object dataRels = null;
                Object dataLinks = null;

                String dataId = Assert.isNull(jsonData.get("id")) ? null
                        : jsonData.get("id").getAsString();
                String dataType = Assert.isNull(jsonData.get("type")) ? null
                        : jsonData.get("type").getAsString();

                // Get the attributes json
                if (Assert.notNull(jsonData.get("attributes"))) {
                    JsonObject jsonAttr = jsonData.get("attributes")
                            .getAsJsonObject();

                    Type type = Assert.notNull(this.tokenTypesToUse
                            .get(EasyJsonApiTypeToken.TOKEN_DEFAULT))
                                    ? this.tokenTypesToUse
                                            .get(EasyJsonApiTypeToken.TOKEN_DEFAULT)
                                    : this.tokenTypesToUse.get(
                                            EasyJsonApiTypeToken.TOKEN_ATTR);

                    if (Assert.isNull(type)) {
                        throw new EasyJsonApiCastException(
                                "Doesn't find token for attributes resource object!");
                    }

                    dataAttr = jsonContext.deserialize(jsonAttr, type);
                }

                // Get the relationships json
                if (Assert.notNull(jsonData.get("relationships"))) {
                    JsonObject jsonRels = jsonData.get("relationships")
                            .getAsJsonObject();

                    Type type = Assert.notNull(this.tokenTypesToUse
                            .get(EasyJsonApiTypeToken.TOKEN_DEFAULT))
                                    ? this.tokenTypesToUse
                                            .get(EasyJsonApiTypeToken.TOKEN_DEFAULT)
                                    : this.tokenTypesToUse.get(
                                            EasyJsonApiTypeToken.TOKEN_RELS);

                    if (Assert.isNull(type)) {
                        throw new EasyJsonApiCastException(
                                "Doesn't find token for relationships resource object!");
                    }

                    dataRels = jsonContext.deserialize(jsonRels, type);
                }

                // Get the links json
                if (Assert.notNull(jsonData.get("links"))) {
                    JsonObject jsonLinks = jsonData.get("links")
                            .getAsJsonObject();

                    Type type = Assert.notNull(this.tokenTypesToUse
                            .get(EasyJsonApiTypeToken.TOKEN_DEFAULT))
                                    ? this.tokenTypesToUse
                                            .get(EasyJsonApiTypeToken.TOKEN_DEFAULT)
                                    : this.tokenTypesToUse.get(
                                            EasyJsonApiTypeToken.TOKEN_LINKS);

                    if (Assert.isNull(type)) {
                        throw new EasyJsonApiCastException(
                                "Doesn't find token for links resource object!");
                    }

                    dataLinks = jsonContext.deserialize(jsonLinks, type);
                }

                Data jsonApiData = new Data(dataId, dataType, dataAttr,
                        dataRels, dataLinks);
                request.addData(jsonApiData);
            }
        }

        return request;
    }

}
