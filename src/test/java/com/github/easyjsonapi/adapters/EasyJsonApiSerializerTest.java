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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Type;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.easyjsonapi.TestHelper;
import com.github.easyjsonapi.adapters.EasyJsonApiSerializer;
import com.github.easyjsonapi.core.EasyJsonApiConfig;
import com.github.easyjsonapi.entities.EJAData;
import com.github.easyjsonapi.entities.EJADataLinkage;
import com.github.easyjsonapi.entities.JsonApi;
import com.github.easyjsonapi.entities.EJALink;
import com.github.easyjsonapi.entities.EJALinkRelated;
import com.github.easyjsonapi.entities.EJANullable;
import com.github.easyjsonapi.entities.EJARelationship;
import com.github.easyjsonapi.entities.test.EntityTestAttr1;
import com.github.easyjsonapi.entities.test.EntityTestAttr2;
import com.github.easyjsonapi.entities.test.EntityTestMeta;
import com.github.easyjsonapi.exceptions.EasyJsonApiCastException;
import com.github.easyjsonapi.exceptions.EasyJsonApiInvalidPackageException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;

public class EasyJsonApiSerializerTest {

    private final String JSON_TEST_FOLDER = "jsonTest/EasyJsonApiSerializerTest/";

    @Test(expected = EasyJsonApiCastException.class)
    public void genericSerializerCastExceptionAttrTest() throws EasyJsonApiInvalidPackageException {

        JsonSerializationContext serializerContext = mock(JsonSerializationContext.class);

        EasyJsonApiConfig config = new EasyJsonApiConfig("com.github.easyjsonapi.entities.test");
        EasyJsonApiSerializer serializer = new EasyJsonApiSerializer();
        serializer.setConfig(config);
        serializer.setClassesUsed(EntityTestMeta.class);

        JsonApi jsonApi = new JsonApi();
        EJAData data = new EJAData("100", "books", new EntityTestAttr1());
        jsonApi.addData(data);

        serializer.serialize(jsonApi, null, serializerContext);
    }

    @Test(expected = EasyJsonApiCastException.class)
    public void genericSerializerCastExceptionRelationshipTest() throws EasyJsonApiInvalidPackageException {

        JsonSerializationContext serializerContext = mock(JsonSerializationContext.class);

        EasyJsonApiConfig config = new EasyJsonApiConfig("com.github.easyjsonapi.entities.test");
        EasyJsonApiSerializer serializer = new EasyJsonApiSerializer();
        serializer.setConfig(config);
        serializer.setClassesUsed(EntityTestMeta.class);

        EntityTestMeta meta = new EntityTestMeta();
        meta.setMetadata("Meta test");
        EJARelationship rel = new EJARelationship("author", EJANullable.LINK, meta);

        JsonApi jsonApi = new JsonApi();
        EJAData data = new EJAData("100", "books", EJANullable.OBJECT);
        data.getRels().getRelationships().add(rel);
        jsonApi.addData(data);

        serializer.serialize(jsonApi, null, serializerContext);
    }

    // Elements are not ordered because implementation using set collection
    @Ignore
    @Test
    public void genericSerializerRelationshipTest() throws EasyJsonApiInvalidPackageException {

        JsonSerializationContext serializerContext = mock(JsonSerializationContext.class);

        EasyJsonApiConfig config = new EasyJsonApiConfig("com.github.easyjsonapi.entities.test");
        EasyJsonApiSerializer serializer = new EasyJsonApiSerializer();
        serializer.setConfig(config);

        EJALinkRelated linkRelatedAuthor = new EJALinkRelated("http://test.com", EJANullable.OBJECT);
        EJALink linkAuthor = new EJALink(linkRelatedAuthor, "http://test.com/test");
        EJARelationship relAuthor = new EJARelationship("author", linkAuthor, EJANullable.OBJECT);
        EJADataLinkage dataLinkNews = new EJADataLinkage("1", "news");
        EJADataLinkage dataLinkBook = new EJADataLinkage("9", "books");
        relAuthor.addDataLinkage(dataLinkNews);
        relAuthor.addDataLinkage(dataLinkBook);

        EJALink linkComments = new EJALink(EJANullable.LINK_RELATED, "http://test.com/test2");
        EJARelationship relComments = new EJARelationship("comments", linkComments, EJANullable.OBJECT);

        JsonApi jsonApi = new JsonApi();
        EJAData data = new EJAData("1", "books", EJANullable.OBJECT);
        data.getRels().getRelationships().add(relAuthor);
        data.getRels().getRelationships().add(relComments);
        jsonApi.addData(data);

        String resultExpected = TestHelper.retriveJsonFile(JSON_TEST_FOLDER + "genericSerializerRelationshipTest.json");

        JsonElement responseJsonApi = serializer.serialize(jsonApi, null, serializerContext);

        Assert.assertNotNull(responseJsonApi);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String resultActual = gson.toJson(responseJsonApi);

        Assert.assertEquals(resultExpected, resultActual);

    }

    @Test
    public void setClassesUsedTest() throws EasyJsonApiInvalidPackageException {

        JsonSerializationContext serializerContext = mock(JsonSerializationContext.class);

        EasyJsonApiConfig config = new EasyJsonApiConfig("com.github.easyjsonapi.entities.test");
        EasyJsonApiSerializer serializer = new EasyJsonApiSerializer();
        serializer.setConfig(config);
        serializer.setClassesUsed(EntityTestAttr2.class);

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElem = jsonParser.parse("{ 'attr1' : 'My love story' }").getAsJsonObject();

        // MOCK INFORMATION
        EntityTestAttr2 entityMock = new EntityTestAttr2();
        entityMock.setAttr1("My love story");
        when(serializerContext.serialize(Mockito.any(JsonElement.class), Mockito.any(Type.class))).thenReturn(jsonElem);

        JsonApi jsonApi = new JsonApi();
        EJAData jsonApiData = new EJAData("1", "books", entityMock);

        jsonApi.addData(jsonApiData);

        String resultExpected = TestHelper.retriveJsonFile(JSON_TEST_FOLDER + "serializerSetClassesUsedTest.json").replace(" ", "").replace("\n", "");

        JsonElement responseJsonApi = serializer.serialize(jsonApi, null, serializerContext);

        Assert.assertNotNull(responseJsonApi);
        Assert.assertEquals(resultExpected, responseJsonApi.toString().replace(" ", "").replace("\"", "'"));

    }

}
