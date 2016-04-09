package org.easyJsonApi.tools;

import java.math.BigDecimal;

import org.easyJsonApi.entities.Data;
import org.easyJsonApi.entities.JsonApi;
import org.easyJsonApi.entities.test.EntityTestAttr1;
import org.easyJsonApi.entities.test.EntityTestAttr2;
import org.easyJsonApi.exceptions.EasyJsonApiException;
import org.easyJsonApi.exceptions.EasyJsonApiInvalidPackageException;
import org.easyJsonApi.exceptions.EasyJsonApiMalformedJsonException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class EasyJsonApiTest {

    private final Logger logger = LoggerFactory.getLogger(EasyJsonApiTest.class);

    private JsonApi jsonApiResult;

    private EasyJsonApi jsonMaker;

    private EasyJsonApiConfig jsonMakerConfig;

    private String jsonToMapping;

    @Before
    public void setUp() throws EasyJsonApiInvalidPackageException {
        jsonMakerConfig = new EasyJsonApiConfig("org.easyJsonApi.entities.test");
        jsonMaker = EasyJsonApi.getInstance();
        jsonMaker.setConfig(jsonMakerConfig);

        jsonToMapping = "";
    }

    @Test(expected = EasyJsonApiMalformedJsonException.class)
    public void convertStringToJsonApiTestWithMalformatedJson() throws EasyJsonApiException {

        // Check result for invalid json
        jsonToMapping = "{ 'data': [ { ] }";
        jsonApiResult = jsonMaker.convertStringToJsonApi(jsonToMapping, EntityTestAttr1.class);

        Assert.assertNull(jsonApiResult);

        // Check result for invalid json
        jsonToMapping = "{ 'dat: [ { ] }";
        jsonApiResult = jsonMaker.convertStringToJsonApi(jsonToMapping, EntityTestAttr1.class);

        Assert.assertNull(jsonApiResult);

    }

    @Test
    public void convertStringToJsonApiTestWithInvalidJson() throws EasyJsonApiException {

        // Check result for empty json
        jsonToMapping = "";
        jsonApiResult = jsonMaker.convertStringToJsonApi(jsonToMapping, EntityTestAttr1.class);

        Assert.assertNull(jsonApiResult);

        // Check result for null json
        jsonToMapping = null;
        jsonApiResult = jsonMaker.convertStringToJsonApi(jsonToMapping, EntityTestAttr1.class);

        Assert.assertNull(jsonApiResult);

        // Check result for json with invalid parameters
        jsonToMapping = "{ }";
        jsonApiResult = jsonMaker.convertStringToJsonApi(jsonToMapping, EntityTestAttr1.class);

        Assert.assertNull(jsonApiResult);

    }

    @Test
    public void convertStringToJsonApiTestWithData() throws EasyJsonApiException {

        // Check result for json with data empty
        jsonToMapping = "{ 'data': [ ] }";
        jsonApiResult = jsonMaker.convertStringToJsonApi(jsonToMapping, EntityTestAttr1.class);

        Assert.assertNull(jsonApiResult);

        // Check result for json with data empty
        jsonToMapping = "{ 'data': [ { } ] }";
        jsonApiResult = jsonMaker.convertStringToJsonApi(jsonToMapping, EntityTestAttr1.class);

        Assert.assertNull(jsonApiResult);

        // Check result for json with attributes empty
        jsonToMapping = "{ 'data': [ { 'type': 'TEST', 'id': '1', 'attributes': { } } ] }";
        jsonApiResult = jsonMaker.convertStringToJsonApi(jsonToMapping, EntityTestAttr1.class);

        Assert.assertNotNull(jsonApiResult);
        Assert.assertNotNull(jsonApiResult.getData().get(0));
        Assert.assertEquals("1", jsonApiResult.getData().get(0).getId());
        Assert.assertEquals("TEST", jsonApiResult.getData().get(0).getType());

        // Check result for json with all attributes
        jsonToMapping = "{ 'data': [ { 'type': 'TEST', 'id': '1', 'attributes': { 'attr1': 'Test Unit', 'attr2': 100, 'attr3': { 'attr': { 'TestMap': '1' } } } } ] }";
        jsonApiResult = jsonMaker.convertStringToJsonApi(jsonToMapping, EntityTestAttr1.class);

        Assert.assertNotNull(jsonApiResult);
        Assert.assertNotNull(jsonApiResult.getData().get(0));
        Assert.assertEquals("1", jsonApiResult.getData().get(0).getId());
        Assert.assertEquals("TEST", jsonApiResult.getData().get(0).getType());
        Assert.assertEquals("Test Unit", ((EntityTestAttr1) jsonApiResult.getData().get(0).getAttr()).getAttr1());
        Assert.assertEquals("100", ((EntityTestAttr1) jsonApiResult.getData().get(0).getAttr()).getAttr2().toPlainString());
        Assert.assertEquals("1", ((EntityTestAttr1) jsonApiResult.getData().get(0).getAttr()).getAttr3().getAttr().get("TestMap"));

        // Check result for json with all attributes
        jsonToMapping = "{ 'data': [ { 'type': 'TEST', 'id': '1', 'attributes': { 'attr1': 'Test Unit 2' } } ] }";
        jsonApiResult = jsonMaker.convertStringToJsonApi(jsonToMapping, EntityTestAttr2.class);

        Assert.assertNotNull(jsonApiResult);
        Assert.assertNotNull(jsonApiResult.getData().get(0));
        Assert.assertEquals("1", jsonApiResult.getData().get(0).getId());
        Assert.assertEquals("TEST", jsonApiResult.getData().get(0).getType());
        Assert.assertEquals("Test Unit 2", ((EntityTestAttr2) jsonApiResult.getData().get(0).getAttr()).getAttr1());

    }

    @Test
    public void convertStringToJsonApiTestWithErrors() throws EasyJsonApiException {

        // Check result for json with errors empty
        jsonToMapping = "{ 'errors': [ { } ] }";
        jsonApiResult = jsonMaker.convertStringToJsonApi(jsonToMapping, EntityTestAttr1.class);

        Assert.assertNull(jsonApiResult);

        // Check result for json with error
        jsonToMapping = "{ 'errors': [ { 'id': '200', 'code': '2000',  'detail' : 'Error detail', 'title' : 'Error title', 'source' : { 'pointer' : '/test/attributes/tests' } } ] }";
        jsonApiResult = jsonMaker.convertStringToJsonApi(jsonToMapping, EntityTestAttr1.class);

        Assert.assertNotNull(jsonApiResult);
        Assert.assertNotNull(jsonApiResult.getErrors().get(0));
        Assert.assertEquals("200", jsonApiResult.getErrors().get(0).getId());
        Assert.assertEquals("2000", jsonApiResult.getErrors().get(0).getCode());
        Assert.assertEquals("Error detail", jsonApiResult.getErrors().get(0).getDetail());
        Assert.assertEquals("Error title", jsonApiResult.getErrors().get(0).getTitle());
        Assert.assertEquals("/test/attributes/tests", jsonApiResult.getErrors().get(0).getSource().getPointer());

        // Check result for json with multiple errors
        jsonToMapping = "{ 'errors': [ { 'id': '200' }, { 'id' : '300'} ] }";
        jsonApiResult = jsonMaker.convertStringToJsonApi(jsonToMapping, EntityTestAttr1.class);

        Assert.assertNotNull(jsonApiResult);
        Assert.assertEquals(2, jsonApiResult.getErrors().size());

    }

    @Test(expected = UnsupportedOperationException.class)
    public void convertStringToJsonApiTestWithDefaultConfiguration() throws EasyJsonApiException {

        jsonToMapping = "{ 'data': [ { 'type': 'TEST', 'id': '1', 'attributes': { 'attr1': 'Test Unit 2' } } ] }";

        jsonMaker.setConfig(null);
        jsonMaker.convertStringToJsonApi(jsonToMapping);

    }

    @Test
    public void convertJsonApiToStringTestWithInvalidObject() throws EasyJsonApiException {

        // Check result for null object
        JsonApi request = null;
        String result = jsonMaker.convertJsonApiToString(request, EntityTestAttr1.class);

        Assert.assertNull(result);
    }

    @Test
    public void convertJsonApiToStringTest() throws EasyJsonApiException {

        JsonApi request = new JsonApi();

        Data dataRequest = new Data();
        dataRequest.setId("Test");
        dataRequest.setType("REQUEST_JSON_API");

        EntityTestAttr1 entityTest = new EntityTestAttr1();
        entityTest.setAttr1("Test entity dependency");
        entityTest.setAttr2(new BigDecimal(10));

        dataRequest.setAttr(entityTest);

        request.getData().add(dataRequest);

        String jsonToRevert = jsonMaker.convertJsonApiToString(request, EntityTestAttr1.class);

        logger.info(jsonToRevert);

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElemExpected = jsonParser
                .parse("{ 'data': [ { 'id': 'Test', 'type': 'REQUEST_JSON_API', 'attributes': { 'attr1': 'Test entity dependency', 'attr2': 10 } } ] }")
                .getAsJsonObject();
        JsonElement jsonElemResult = jsonParser.parse(jsonToRevert).getAsJsonObject();

        Assert.assertEquals(jsonElemExpected, jsonElemResult);

        // Check result for empty object
        request = new JsonApi();
        jsonToRevert = jsonMaker.convertJsonApiToString(request, EntityTestAttr1.class);

        logger.info(jsonToRevert);

        jsonElemExpected = jsonParser.parse("{ 'data': [ ] }").getAsJsonObject();
        jsonElemResult = jsonParser.parse(jsonToRevert).getAsJsonObject();

        Assert.assertEquals(jsonElemExpected, jsonElemResult);

    }

    @Test(expected = UnsupportedOperationException.class)
    public void convertJsonApiToStringTestWithDefaultConfiguration() {

        JsonApi request = new JsonApi();

        Data dataRequest = new Data();
        dataRequest.setId("Test");
        dataRequest.setType("REQUEST_JSON_API");

        EntityTestAttr1 entityTest = new EntityTestAttr1();
        entityTest.setAttr1("Test entity dependency");
        entityTest.setAttr2(new BigDecimal(10));

        dataRequest.setAttr(entityTest);

        request.getData().add(dataRequest);

        jsonMaker.setConfig(null);
        jsonMaker.convertJsonApiToString(request);

    }
}
