package org.easyJsonApi.entities;

import java.math.BigDecimal;

import org.easyJsonApi.entities.test.EntityDependencyTest;
import org.easyJsonApi.entities.test.EntityTestAttr1;
import org.easyJsonApi.exceptions.EasyJsonApiException;
import org.easyJsonApi.tools.EasyJsonApi;
import org.easyJsonApi.tools.EasyJsonApiConfig;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.GsonBuilder;

public class JsonApiTest {

    @Test
    public void createJsonApiRequestTest() throws JsonProcessingException, EasyJsonApiException {

        JsonApi request = new JsonApi();

        EntityDependencyTest testDependency = new EntityDependencyTest();
        testDependency.getAttr().put("TestMap", "1");

        EntityTestAttr1 testEntity = new EntityTestAttr1();
        testEntity.setAttr1("Test Unit");
        testEntity.setAttr2(new BigDecimal(100));
        testEntity.setAttr3(testDependency);

        Data dataJsonApi = new Data();
        dataJsonApi.setId("1");
        dataJsonApi.setType("TEST");
        dataJsonApi.setAttr(testEntity);

        request.getData().add(dataJsonApi);

        String resultRequest = new GsonBuilder().setPrettyPrinting().create().toJson(request);

        EasyJsonApiConfig config = new EasyJsonApiConfig("org.easyJsonApi.entities.test");
        EasyJsonApi jsonMaker = EasyJsonApi.getInstance();
        jsonMaker.setConfig(config);
        JsonApi requestJsonRevert = jsonMaker.convertStringToJsonApi(resultRequest, EntityTestAttr1.class);

        Assert.assertNotNull(requestJsonRevert);
        Assert.assertNotNull(requestJsonRevert.getData().get(0));
        Assert.assertEquals("1", requestJsonRevert.getData().get(0).getId());
        Assert.assertEquals("TEST", requestJsonRevert.getData().get(0).getType());
        Assert.assertEquals("Test Unit", ((EntityTestAttr1) requestJsonRevert.getData().get(0).getAttr()).getAttr1());
        Assert.assertEquals("100", ((EntityTestAttr1) requestJsonRevert.getData().get(0).getAttr()).getAttr2().toPlainString());
        Assert.assertEquals("1", ((EntityTestAttr1) requestJsonRevert.getData().get(0).getAttr()).getAttr3().getAttr().get("TestMap"));

    }

}
