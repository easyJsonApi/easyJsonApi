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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Type;

import org.easyJsonApi.entities.Data;
import org.easyJsonApi.entities.JsonApi;
import org.easyJsonApi.entities.test.EntityTestAttr1;
import org.easyJsonApi.entities.test.EntityTestAttr2;
import org.easyJsonApi.exceptions.EasyJsonApiInvalidPackageException;
import org.easyJsonApi.tools.EasyJsonApiConfig;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;

public class EasyJsonApiSerializerTest {

    @Test
    public void setClassesUsedTest() throws EasyJsonApiInvalidPackageException {

        JsonSerializationContext serializerContext = mock(
                JsonSerializationContext.class);

        EasyJsonApiConfig config = new EasyJsonApiConfig(
                "org.easyJsonApi.entities.test");
        EasyJsonApiSerializer serializer = new EasyJsonApiSerializer();
        serializer.setConfig(config);
        serializer.setClassesUsed(EntityTestAttr1.class);
        serializer.setClassesUsed(EntityTestAttr2.class);

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElem = jsonParser.parse("{ 'attr1' : 'Attribute_1' }")
                .getAsJsonObject();

        // MOCK INFORMATION
        EntityTestAttr2 entityMock = new EntityTestAttr2();
        entityMock.setAttr1("Attribute_1");
        when(serializerContext.serialize(Mockito.any(JsonElement.class),
                Mockito.any(Type.class))).thenReturn(jsonElem);

        JsonApi jsonApi = new JsonApi();
        Data jsonApiData = new Data("1", "TEST", entityMock, Data.NULLABLE,
                Data.NULLABLE);

        jsonApi.addData(jsonApiData);

        String resultExpected = "{ 'data': [ { 'id': '1', 'type': 'TEST', 'attributes': { 'attr1' : 'Attribute_1' } } ] }";

        JsonElement responseJsonApi = serializer.serialize(jsonApi, null,
                serializerContext);

        Assert.assertNotNull(responseJsonApi);
        Assert.assertEquals(resultExpected.replace(" ", ""),
                responseJsonApi.toString().replace(" ", "").replace("\"", "'"));

    }

}
