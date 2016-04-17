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

import java.math.BigDecimal;

import org.easyJsonApi.adapters.EasyJsonApiDeserializer;
import org.easyJsonApi.adapters.EasyJsonApiSerializer;
import org.easyJsonApi.asserts.Assert;
import org.easyJsonApi.entities.Data;
import org.easyJsonApi.entities.Error;
import org.easyJsonApi.entities.JsonApi;
import org.easyJsonApi.exceptions.EasyJsonApiException;
import org.easyJsonApi.exceptions.EasyJsonApiMalformedJsonException;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * This is a main class of EasyJsonApi api. Is a singleton what you need to use
 * for convert the json strings to json objects, and vice-versa.
 * This class contain mechanisms to serializer and deserializer the json api
 * information, you only need to set the configuration, or use the default
 * configuration, and enter the json api with classes utilized inside the json
 * api.
 * 
 * @see EasyJsonApiConfig the configuration of EasyJsonApi
 * @see EasyJsonApiSerializer the serializer of EasyJsonApi
 * @see EasyJsonApiDeserializer the deserializer of EasyJsonApi
 * @author Nuno Bento
 * @version %I%, %G%
 */
public class EasyJsonApi {

    private static EasyJsonApi easyJsonApi = null;

    /**
     * Get the EasyJsonApi instance
     * 
     * @return the EasyJsonApi
     */
    public static EasyJsonApi getInstance() {
        if (easyJsonApi == null) {
            easyJsonApi = new EasyJsonApi();
        }

        return easyJsonApi;
    }

    private EasyJsonApiDeserializer deserializerJsonApi;

    private EasyJsonApiConfig easyJsonApiConfig = null;

    private EasyJsonApiSerializer serializerJsonApi;

    /**
     * The private construction for singleton
     */
    private EasyJsonApi() {
        deserializerJsonApi = new EasyJsonApiDeserializer();
        serializerJsonApi = new EasyJsonApiSerializer();
    }

    /**
     * Convert one {@link JsonApi} object into json api string
     * 
     * @param jsonApi the json api object
     * @return the string with json api format
     * @throws EasyJsonApiMalformedJsonException
     */
    public String convertJsonApiToString(JsonApi json) throws EasyJsonApiMalformedJsonException {
        return convertJsonApiToString(json, new Class[0]);
    }

    /**
     * Convert one {@link JsonApi} object into json api string with utilized
     * classes inside the object
     * 
     * @param json the json api object
     * @param classes the classes utilized inside the object
     * @return the string with json api format
     * @throws EasyJsonApiMalformedJsonException
     */
    public String convertJsonApiToString(JsonApi json, Class<?>... classes) throws EasyJsonApiMalformedJsonException {

        if (Assert.isNull(json)) {
            return null;
        }

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        if (Assert.isNull(this.easyJsonApiConfig)) {
            setConfigDefault();
        }

        this.serializerJsonApi.setConfig(this.easyJsonApiConfig);
        this.serializerJsonApi.setClassesUsed(classes);

        builder.registerTypeAdapter(JsonApi.class, this.serializerJsonApi);

        String jsonApi = null;

        try {
            jsonApi = builder.create().toJson(json);
        } catch (JsonSyntaxException ex) {
            throw new EasyJsonApiMalformedJsonException("Problem with json sended!", ex);
        }

        return jsonApi;
    }

    /**
     * Convert one string into {@link JsonApi} object
     * 
     * @param json the json api string
     * @return the {@link JsonApi} object
     * @throws EasyJsonApiException if the json api string was any problem
     */
    public JsonApi convertStringToJsonApi(String json) throws EasyJsonApiException {
        return convertStringToJsonApi(json, new Class[0]);
    }

    /**
     * Convert one string into {@link JsonApi} object with classes utilized
     * inside the object
     * 
     * @param json the json api string
     * @param classes the classes utilized inside the json api
     * @return the {@link JsonApi} object
     * @throws EasyJsonApiException if the json api string was any
     *             problem
     */
    public JsonApi convertStringToJsonApi(String json, Class<?>... classes) throws EasyJsonApiException {

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        if (Assert.isNull(this.easyJsonApiConfig)) {
            setConfigDefault();
        }

        this.deserializerJsonApi.setConfig(this.easyJsonApiConfig);
        this.deserializerJsonApi.setClassesUsed(classes);

        builder.registerTypeAdapter(JsonApi.class, this.deserializerJsonApi);

        JsonApi jsonApi = null;

        try {
            jsonApi = builder.create().fromJson(json, JsonApi.class);
        } catch (JsonSyntaxException ex) {
            throw new EasyJsonApiMalformedJsonException("Problem with json sended!", ex);
        }

        if (Assert.notNull(jsonApi)) {

            // Return null when doesn't exist errors and data
            if (jsonApi.getErrors().isEmpty() && jsonApi.getData().isEmpty()) {
                return null;
            } else if (!jsonApi.getData().isEmpty()) {
                // Get the first object inside the data and check if has any
                // attribute instanced
                Data firstData = jsonApi.getData().get(BigDecimal.ZERO.intValue());
                if (Assert.isNull(firstData.getId(), firstData.getType(), firstData.getAttr(), firstData.getRels(), firstData.getLinks())) {
                    return null;
                }
            } else if (!jsonApi.getErrors().isEmpty()) {
                // Get the first object inside the errors and check if has any
                // error instanced
                Error firstError = jsonApi.getErrors().get(BigDecimal.ZERO.intValue());
                if (Assert.isNull(firstError.getId(), firstError.getTitle(), firstError.getDetail(), firstError.getCode(), firstError.getMeta(),
                        firstError.getSource(), firstError.getStatus())) {
                    return null;
                }
            }

        }

        return jsonApi;
    }

    /**
     * Set the configuration of EasyJsonApi {@link EasyJsonApiConfig}
     * 
     * @param config the configuration of EasyJsonApi
     */
    public void setConfig(EasyJsonApiConfig config) {
        this.easyJsonApiConfig = config;
    }

    /**
     * Set the configuration of EasyJsonApi using default configuration
     * {@link EasyJsonApiConfig}
     */
    private void setConfigDefault() {
        // TODO: Create the default configuration
        throw new UnsupportedOperationException("Need create the default configuration!");
    }

}
