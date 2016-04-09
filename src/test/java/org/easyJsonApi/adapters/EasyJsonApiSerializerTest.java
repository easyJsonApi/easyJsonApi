package org.easyJsonApi.adapters;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Type;

import org.easyJsonApi.adapters.EasyJsonApiSerializer;
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

        JsonSerializationContext serializerContext = mock(JsonSerializationContext.class);

        EasyJsonApiConfig config = new EasyJsonApiConfig("org.easyJsonApi.entities.test");
        EasyJsonApiSerializer serializer = new EasyJsonApiSerializer();
        serializer.setConfig(config);
        serializer.setClassesUsed(EntityTestAttr1.class);
        serializer.setClassesUsed(EntityTestAttr2.class);

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElem = jsonParser.parse("{ 'attr1' : 'Attribute_1' }").getAsJsonObject();

        // MOCK INFORMATION
        EntityTestAttr2 entityMock = new EntityTestAttr2();
        entityMock.setAttr1("Attribute_1");
        when(serializerContext.serialize(Mockito.any(JsonElement.class), Mockito.any(Type.class))).thenReturn(jsonElem);

        JsonApi requestJsonApi = new JsonApi();
        Data requestDataJsonApi = new Data();
        requestDataJsonApi.setId("1");
        requestDataJsonApi.setType("TEST");
        requestDataJsonApi.setAttr(entityMock);

        requestJsonApi.getData().add(requestDataJsonApi);

        String resultExpected = "{ 'data': [ { 'id': '1', 'type': 'TEST', 'attributes': { 'attr1' : 'Attribute_1' } } ] }";

        JsonElement responseJsonApi = serializer.serialize(requestJsonApi, null, serializerContext);

        Assert.assertNotNull(responseJsonApi);
        Assert.assertEquals(resultExpected.replace(" ", ""), responseJsonApi.toString().replace(" ", "").replace("\"", "'"));

    }

}
