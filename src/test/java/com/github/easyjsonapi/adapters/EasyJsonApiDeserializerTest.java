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
import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.easyjsonapi.TestHelper;
import com.github.easyjsonapi.adapters.EasyJsonApiDeserializer;
import com.github.easyjsonapi.core.EasyJsonApiConfig;
import com.github.easyjsonapi.entities.EJAData;
import com.github.easyjsonapi.entities.JsonApi;
import com.github.easyjsonapi.entities.test.EntityDependencyTest;
import com.github.easyjsonapi.entities.test.EntityTestAttr1;
import com.github.easyjsonapi.entities.test.EntityTestAttr2;
import com.github.easyjsonapi.entities.test.EntityTestMeta;
import com.github.easyjsonapi.entities.test.EntityTestMetaRelationship;
import com.github.easyjsonapi.exceptions.EasyJsonApiCastException;
import com.github.easyjsonapi.exceptions.EasyJsonApiInvalidPackageException;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class EasyJsonApiDeserializerTest {

    private final String JSON_TEST_FOLDER = "jsonTest/EasyJsonApiDeserializerTest/";

    @Test
    public void genericDeserializerAttrTest() throws EasyJsonApiInvalidPackageException {

        JsonDeserializationContext deserializerContext = mock(JsonDeserializationContext.class);

        EasyJsonApiConfig config = new EasyJsonApiConfig("com.github.easyjsonapi.entities.test");
        EasyJsonApiDeserializer deserializer = new EasyJsonApiDeserializer();
        deserializer.setConfig(config);
        deserializer.setClassesUsed(EntityTestAttr1.class);

        EntityTestAttr1 attr = new EntityTestAttr1();
        attr.setAttr1("Miguel Teles");
        attr.setAttr2(new BigDecimal("2000"));
        EntityDependencyTest attr3 = new EntityDependencyTest();
        attr3.getAttr().put("title", "The best food");
        attr3.getAttr().put("pages", "4000");
        attr.setAttr3(attr3);

        when(deserializerContext.deserialize(Mockito.any(JsonElement.class), Mockito.any(Type.class))).thenReturn(attr);

        String jsonToTest = TestHelper.retriveJsonFile(JSON_TEST_FOLDER + "genericDeserializerAttrTest.json");

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElem = jsonParser.parse(jsonToTest).getAsJsonObject();

        JsonApi responseJsonApi = deserializer.deserialize(jsonElem, null, deserializerContext);

        List<EJAData> cloneData = responseJsonApi.getData();

        EJAData firstData = cloneData.get(0);

        Assert.assertNotNull(responseJsonApi);
        Assert.assertEquals(1, cloneData.size());
        Assert.assertEquals("books", firstData.getType());
        Assert.assertEquals("1", firstData.getId());
        Assert.assertEquals("Miguel Teles", ((EntityTestAttr1) firstData.getAttr()).getAttr1());
        Assert.assertEquals(BigDecimal.valueOf(2000), ((EntityTestAttr1) firstData.getAttr()).getAttr2());
        Assert.assertEquals("The best food", ((EntityTestAttr1) firstData.getAttr()).getAttr3().getAttr().get("title"));
        Assert.assertEquals("4000", ((EntityTestAttr1) firstData.getAttr()).getAttr3().getAttr().get("pages"));
    }

    @Test(expected = EasyJsonApiCastException.class)
    public void genericDeserializerCastExceptionAttrTest() throws EasyJsonApiInvalidPackageException {

        JsonDeserializationContext deserializerContext = mock(JsonDeserializationContext.class);

        EasyJsonApiConfig config = new EasyJsonApiConfig("com.github.easyjsonapi.entities.test");
        EasyJsonApiDeserializer deserializer = new EasyJsonApiDeserializer();
        deserializer.setConfig(config);
        deserializer.setClassesUsed(EntityTestMeta.class);

        String jsonToTest = TestHelper.retriveJsonFile(JSON_TEST_FOLDER + "genericDeserializerCastExceptionAttrTest.json");

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElem = jsonParser.parse(jsonToTest).getAsJsonObject();

        deserializer.deserialize(jsonElem, null, deserializerContext);
    }

    @Test(expected = EasyJsonApiCastException.class)
    public void genericDeserializerCastExceptionRelationshipTest() throws EasyJsonApiInvalidPackageException {

        JsonDeserializationContext deserializerContext = mock(JsonDeserializationContext.class);

        EasyJsonApiConfig config = new EasyJsonApiConfig("com.github.easyjsonapi.entities.test");
        EasyJsonApiDeserializer deserializer = new EasyJsonApiDeserializer();
        deserializer.setConfig(config);
        deserializer.setClassesUsed(EntityTestAttr1.class);

        String jsonToTest = TestHelper.retriveJsonFile(JSON_TEST_FOLDER + "genericDeserializerCastExceptionRelsTest.json");

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElem = jsonParser.parse(jsonToTest).getAsJsonObject();

        deserializer.deserialize(jsonElem, null, deserializerContext);
    }

    @Test
    public void genericDeserializerRelationshipsTest() throws EasyJsonApiInvalidPackageException {

        JsonDeserializationContext deserializerContext = mock(JsonDeserializationContext.class);

        EasyJsonApiConfig config = new EasyJsonApiConfig("com.github.easyjsonapi.entities.test");
        EasyJsonApiDeserializer deserializer = new EasyJsonApiDeserializer();
        deserializer.setConfig(config);
        deserializer.setClassesUsed(EntityTestMetaRelationship.class);

        EntityTestMetaRelationship metaRelationship = new EntityTestMetaRelationship();
        metaRelationship.setCount("10");

        when(deserializerContext.deserialize(Mockito.any(JsonElement.class), Mockito.any(Type.class))).thenReturn(metaRelationship);

        String jsonToTest = TestHelper.retriveJsonFile(JSON_TEST_FOLDER + "genericDeserializerRelationshipsTest.json");

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElem = jsonParser.parse(jsonToTest).getAsJsonObject();

        JsonApi responseJsonApi = deserializer.deserialize(jsonElem, null, deserializerContext);

        List<EJAData> cloneData = responseJsonApi.getData();

        EJAData firstData = cloneData.get(0);

        Assert.assertNotNull(responseJsonApi);
        Assert.assertEquals(1, cloneData.size());
        Assert.assertEquals("books", firstData.getType());
        Assert.assertEquals("1", firstData.getId());
        Assert.assertEquals(2, firstData.getRels().getRelationships().size());

    }

    @Test
    public void setClassesUsedTest() throws EasyJsonApiInvalidPackageException {

        JsonDeserializationContext deserializerContext = mock(JsonDeserializationContext.class);

        EasyJsonApiConfig config = new EasyJsonApiConfig("com.github.easyjsonapi.entities.test");
        EasyJsonApiDeserializer deserializer = new EasyJsonApiDeserializer();
        deserializer.setConfig(config);
        deserializer.setClassesUsed(EntityTestAttr1.class, EntityTestAttr2.class);

        String jsonToTest = TestHelper.retriveJsonFile(JSON_TEST_FOLDER + "deserializerSetClassesUsedTest.json");

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElem = jsonParser.parse(jsonToTest).getAsJsonObject();

        // MOCK INFORMATION
        EntityTestAttr2 entityMock = new EntityTestAttr2();
        entityMock.setAttr1("Java book");
        when(deserializerContext.deserialize(Mockito.any(JsonElement.class), Mockito.any(Type.class))).thenReturn(entityMock);

        JsonApi responseJsonApi = deserializer.deserialize(jsonElem, null, deserializerContext);

        List<EJAData> cloneData = responseJsonApi.getData();

        Assert.assertNotNull(responseJsonApi);
        Assert.assertEquals(1, cloneData.size());
        Assert.assertEquals("books", cloneData.get(0).getType());
        Assert.assertEquals("1", cloneData.get(0).getId());
        Assert.assertEquals("Java book", ((EntityTestAttr2) cloneData.get(0).getAttr()).getAttr1());

    }

}
