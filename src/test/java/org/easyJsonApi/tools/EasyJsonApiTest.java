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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easyJsonApi.entities.Data;
import org.easyJsonApi.entities.Error;
import org.easyJsonApi.entities.HttpStatus;
import org.easyJsonApi.entities.JsonApi;
import org.easyJsonApi.entities.Source;
import org.easyJsonApi.entities.test.EntityTestAttr1;
import org.easyJsonApi.entities.test.EntityTestAttr2;
import org.easyJsonApi.entities.test.EntityTestRels;
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

    private JsonApi jsonApiObject;

    private JsonApi jsonApiObjectResult;

    private String jsonApiString;

    private String jsonApiStringResult;

    private EasyJsonApi jsonMaker;

    private EasyJsonApiConfig jsonMakerConfig;

    private final Logger logger = LoggerFactory
            .getLogger(EasyJsonApiTest.class);

    @Test
    public void convertAllResourcesStringToJsonApiTest()
            throws EasyJsonApiException {

        // TODO: Need add the link object
        jsonApiString = "{ 'data': [ { 'type': 'TEST', 'id': '1', 'attributes': { 'attr1': 'Attribute', 'attr2': 200, 'attr3': { 'attr': { 'Model': 'XPTO', 'License': '85599' } } }, 'relationships': { 'name': 'Miguel', 'age': '30' } } ] }";

        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString,
                EntityTestAttr1.class, EntityTestRels.class);

        List<Data> cloneData = jsonApiObjectResult.getData();

        Assert.assertNotNull(jsonApiObjectResult);
        Assert.assertNotNull(cloneData.get(0));
        Assert.assertEquals("1", cloneData.get(0).getId());
        Assert.assertEquals("TEST", cloneData.get(0).getType());
        Assert.assertEquals("Attribute",
                ((EntityTestAttr1) cloneData.get(0).getAttr()).getAttr1());
        Assert.assertEquals(new BigDecimal(200),
                ((EntityTestAttr1) cloneData.get(0).getAttr()).getAttr2());
        Assert.assertEquals("XPTO",
                ((EntityTestAttr1) cloneData.get(0).getAttr()).getAttr3()
                        .getAttr().get("Model"));
        Assert.assertEquals("85599",
                ((EntityTestAttr1) cloneData.get(0).getAttr()).getAttr3()
                        .getAttr().get("License"));
        Assert.assertEquals("Miguel",
                ((EntityTestRels) cloneData.get(0).getRels()).getName());
        Assert.assertEquals("30",
                ((EntityTestRels) cloneData.get(0).getRels()).getAge());
    }

    @Test
    public void convertDataAttributesJsonApiToStringTest()
            throws EasyJsonApiException {

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElemExpected = null;
        JsonElement jsonElemResult = null;

        // Check result for empty object
        jsonApiObject = new JsonApi();
        jsonApiStringResult = jsonMaker.convertJsonApiToString(jsonApiObject,
                EntityTestAttr1.class);

        logger.info(jsonApiStringResult);

        jsonElemExpected = jsonParser.parse("{ }").getAsJsonObject();
        jsonElemResult = jsonParser.parse(jsonApiStringResult)
                .getAsJsonObject();

        Assert.assertEquals(jsonElemExpected, jsonElemResult);

        // Check result for object with all attributes using EntityTestAttr1
        jsonApiObject = new JsonApi();

        EntityTestAttr1 entityTest = new EntityTestAttr1();
        entityTest.setAttr1("Test entity dependency");
        entityTest.setAttr2(new BigDecimal(10));

        Data dataRequest = new Data("Test", "REQUEST_JSON_API", entityTest,
                Data.NULLABLE, Data.NULLABLE);
        jsonApiObject.addData(dataRequest);

        jsonApiStringResult = jsonMaker.convertJsonApiToString(jsonApiObject,
                EntityTestAttr1.class);

        logger.info(jsonApiStringResult);

        jsonElemExpected = jsonParser
                .parse("{ 'data': [ { 'id': 'Test', 'type': 'REQUEST_JSON_API', 'attributes': { 'attr1': 'Test entity dependency', 'attr2': 10 } } ] }")
                .getAsJsonObject();
        jsonElemResult = jsonParser.parse(jsonApiStringResult)
                .getAsJsonObject();

        Assert.assertEquals(jsonElemExpected, jsonElemResult);

    }

    @Test
    public void convertDataAttributesStringToJsonApiTest()
            throws EasyJsonApiException {

        // Check result for json with data empty
        jsonApiString = "{ 'data': [ ] }";
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString,
                EntityTestAttr1.class);

        Assert.assertNull(jsonApiObjectResult);

        // Check result for json with data empty but array has one instance
        jsonApiString = "{ 'data': [ { } ] }";
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString,
                EntityTestAttr1.class);

        Assert.assertNull(jsonApiObjectResult);

        // Check result for json with attributes empty
        jsonApiString = "{ 'data': [ { 'type': 'TEST', 'id': '1', 'attributes': { } } ] }";
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString,
                EntityTestAttr1.class);

        List<Data> cloneData = jsonApiObjectResult.getData();

        Assert.assertNotNull(jsonApiObjectResult);
        Assert.assertNotNull(cloneData.get(0));
        Assert.assertEquals("1", cloneData.get(0).getId());
        Assert.assertEquals("TEST", cloneData.get(0).getType());

        // Check result for json with all attributes using EntityTestAttr1
        jsonApiString = "{ 'data': [ { 'type': 'TEST', 'id': '1', 'attributes': { 'attr1': 'Test Unit', 'attr2': 100, 'attr3': { 'attr': { 'TestMap': '1' } } } } ] }";
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString,
                EntityTestAttr1.class);

        cloneData = jsonApiObjectResult.getData();

        Assert.assertNotNull(jsonApiObjectResult);
        Assert.assertNotNull(cloneData.get(0));
        Assert.assertEquals("1", cloneData.get(0).getId());
        Assert.assertEquals("TEST", cloneData.get(0).getType());
        Assert.assertEquals("Test Unit",
                ((EntityTestAttr1) cloneData.get(0).getAttr()).getAttr1());
        Assert.assertEquals("100",
                ((EntityTestAttr1) cloneData.get(0).getAttr()).getAttr2()
                        .toPlainString());
        Assert.assertEquals("1", ((EntityTestAttr1) cloneData.get(0).getAttr())
                .getAttr3().getAttr().get("TestMap"));

        // Check result for json with all attributes using EntityTestAttr2
        jsonApiString = "{ 'data': [ { 'type': 'TEST', 'id': '1', 'attributes': { 'attr1': 'Test Unit 2' } } ] }";
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString,
                EntityTestAttr2.class);

        cloneData = jsonApiObjectResult.getData();

        Assert.assertNotNull(jsonApiObjectResult);
        Assert.assertNotNull(cloneData.get(0));
        Assert.assertEquals("1", cloneData.get(0).getId());
        Assert.assertEquals("TEST", cloneData.get(0).getType());
        Assert.assertEquals("Test Unit 2",
                ((EntityTestAttr2) cloneData.get(0).getAttr()).getAttr1());

    }

    @Test
    public void convertDataRelsJsonApiToStringTest()
            throws EasyJsonApiException {

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElemExpected = null;
        JsonElement jsonElemResult = null;

        // Check result for object with all attributes using EntityTestAttr1
        jsonApiObject = new JsonApi();

        EntityTestRels entityTest = new EntityTestRels();
        entityTest.setName("Miguel");
        entityTest.setAge("10");

        Data dataRequest = new Data("Test", "REQUEST_JSON_API", Data.NULLABLE,
                entityTest, Data.NULLABLE);
        jsonApiObject.addData(dataRequest);

        jsonApiStringResult = jsonMaker.convertJsonApiToString(jsonApiObject,
                EntityTestRels.class);

        logger.info(jsonApiStringResult);

        jsonElemExpected = jsonParser
                .parse("{ 'data': [ { 'id': 'Test', 'type': 'REQUEST_JSON_API', 'relationships': { 'name': 'Miguel', 'age': '10' } } ] }")
                .getAsJsonObject();
        jsonElemResult = jsonParser.parse(jsonApiStringResult)
                .getAsJsonObject();

        Assert.assertEquals(jsonElemExpected, jsonElemResult);

    }

    @Test
    public void convertDataRelsStringToJsonApiTest()
            throws EasyJsonApiException {

        // Check result for json with relationships empty
        jsonApiString = "{ 'data': [ { 'type': 'TEST', 'id': '1', 'relationships': { } } ] }";
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString,
                EntityTestRels.class);

        List<Data> cloneData = jsonApiObjectResult.getData();

        Assert.assertNotNull(jsonApiObjectResult);
        Assert.assertNotNull(cloneData.get(0));
        Assert.assertEquals("1", cloneData.get(0).getId());
        Assert.assertEquals("TEST", cloneData.get(0).getType());

        // Check result for json with all relationship using EntityTestRels
        jsonApiString = "{ 'data': [ { 'type': 'TEST', 'id': '1', 'relationships': { 'name': 'Mickey', 'age': '20' } } ] }";
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString,
                EntityTestRels.class);

        cloneData = jsonApiObjectResult.getData();

        Assert.assertNotNull(jsonApiObjectResult);
        Assert.assertNotNull(cloneData.get(0));
        Assert.assertEquals("1", cloneData.get(0).getId());
        Assert.assertEquals("TEST", cloneData.get(0).getType());
        Assert.assertEquals("Mickey",
                ((EntityTestRels) cloneData.get(0).getRels()).getName());
        Assert.assertEquals("20",
                ((EntityTestRels) cloneData.get(0).getRels()).getAge());
    }

    @Test
    public void convertErrorsJsonApiToStringTest() throws EasyJsonApiException {

        // Check result for json with errors empty
        jsonApiString = "{ 'errors': [ { } ] }";
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString,
                EntityTestAttr1.class);

        Assert.assertNull(jsonApiObjectResult);

        // Check result for json with error
        jsonApiString = "{ 'errors': [ { 'id': '200', 'code': '2000',  'detail' : 'Error detail', 'title' : 'Error title', 'source' : { 'pointer' : '/test/attributes/tests' } } ] }";
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString,
                EntityTestAttr1.class);

        List<Error> cloneError = jsonApiObjectResult.getErrors();

        Assert.assertNotNull(jsonApiObjectResult);
        Assert.assertNotNull(cloneError.get(0));
        Assert.assertEquals("200", cloneError.get(0).getId());
        Assert.assertEquals("2000", cloneError.get(0).getCode());
        Assert.assertEquals("Error detail", cloneError.get(0).getDetail());
        Assert.assertEquals("Error title", cloneError.get(0).getTitle());
        Assert.assertEquals("/test/attributes/tests",
                cloneError.get(0).getSource().getPointer());

        // Check result for json with multiple errors
        jsonApiString = "{ 'errors': [ { 'id': '200' }, { 'id' : '300'} ] }";
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString,
                EntityTestAttr1.class);

        Assert.assertNotNull(jsonApiObjectResult);
        Assert.assertEquals(2, jsonApiObjectResult.getErrors().size());

    }

    @Test
    public void convertErrorsStringToJsonApiTest() throws EasyJsonApiException {

        // Check result for empty
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElemExpected = null;
        JsonElement jsonElemResult = null;

        // Check result for empty object
        jsonApiObject = new JsonApi();
        jsonApiStringResult = jsonMaker.convertJsonApiToString(jsonApiObject);

        jsonElemExpected = jsonParser.parse("{}").getAsJsonObject();
        jsonElemResult = jsonParser.parse(jsonApiStringResult)
                .getAsJsonObject();

        Assert.assertEquals(jsonElemExpected, jsonElemResult);

        // Check result for object with one error
        jsonApiObject = new JsonApi();

        Error error = new Error("1000", "Error", HttpStatus.GONE, "300",
                "The first error!", Error.NULLABLE, Source.NULLABLE);

        jsonApiObject.addError(error);

        jsonApiStringResult = jsonMaker.convertJsonApiToString(jsonApiObject);

        jsonElemExpected = jsonParser
                .parse("{ 'errors': [ { 'id': '1000', 'title': 'Error', 'code': '300', 'detail': 'The first error!', 'status': '410' } ] }")
                .getAsJsonObject();
        jsonElemResult = jsonParser.parse(jsonApiStringResult)
                .getAsJsonObject();

        Assert.assertEquals(jsonElemExpected, jsonElemResult);

        // Check result for object with multiple errors
        jsonApiObject = new JsonApi();

        Error errorSecond = new Error("2000", "Error", HttpStatus.OK, "400",
                "The second error!", Error.NULLABLE, Source.NULLABLE);

        jsonApiObject.addError(error);
        jsonApiObject.addError(errorSecond);

        jsonApiStringResult = jsonMaker.convertJsonApiToString(jsonApiObject);

        jsonElemExpected = jsonParser
                .parse("{ 'errors': [ { 'id': '1000', 'title': 'Error', 'code': '300', 'detail': 'The first error!', 'status': '410' }, { 'id': '2000', 'title': 'Error', 'code': '400', 'detail': 'The second error!', 'status': '200' } ] }")
                .getAsJsonObject();
        jsonElemResult = jsonParser.parse(jsonApiStringResult)
                .getAsJsonObject();

        Assert.assertEquals(jsonElemExpected, jsonElemResult);

    }

    @Test
    public void convertInvalidJsonApiToStringTest()
            throws EasyJsonApiException {

        // Check result for null object
        jsonApiObject = null;
        String result = jsonMaker.convertJsonApiToString(jsonApiObject,
                EntityTestAttr1.class);

        Assert.assertNull(result);
    }

    @Test
    public void convertInvalidJsonStringToJsonApiTest()
            throws EasyJsonApiException {

        // Check result for empty json
        jsonApiString = "";
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString,
                EntityTestAttr1.class);

        Assert.assertNull(jsonApiObjectResult);

        // Check result for null json
        jsonApiString = null;
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString,
                EntityTestAttr1.class);

        Assert.assertNull(jsonApiObjectResult);

        // Check result for json with invalid parameters
        jsonApiString = "{ }";
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString,
                EntityTestAttr1.class);

        Assert.assertNull(jsonApiObjectResult);

    }

    @Test(expected = EasyJsonApiMalformedJsonException.class)
    public void convertStringToJsonApiMalformatedJsonTest()
            throws EasyJsonApiException {

        // Check result for invalid json
        jsonApiString = "{ 'data': [ { ] }";
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString,
                EntityTestAttr1.class);

    }

    @Test(expected = EasyJsonApiMalformedJsonException.class)
    public void convertStringToJsonApiMalformatedJsonTest2()
            throws EasyJsonApiException {

        // Check result for invalid json
        jsonApiString = "{ 'dat: [ { ] }";
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString,
                EntityTestAttr1.class);

    }

    @Test
    public void convertWithDefaultConfigurationJsonApiToStringTest()
            throws EasyJsonApiMalformedJsonException {

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElemExpected = null;
        JsonElement jsonElemResult = null;

        JsonApi request = new JsonApi();

        Map<String, String> attrMap = new HashMap<>();
        attrMap.put("Test", "100");
        attrMap.put("Test2", "200");

        Data dataRequest = new Data("Test", "REQUEST_JSON_API", attrMap,
                Data.NULLABLE, Data.NULLABLE);

        request.addData(dataRequest);

        // Set the configuration to null for using default map type
        jsonMaker.setConfig(null);
        jsonApiStringResult = jsonMaker.convertJsonApiToString(request);

        jsonElemExpected = jsonParser
                .parse("{ 'data': [ { 'id': 'Test', 'type': 'REQUEST_JSON_API', 'attributes': { 'Test': '100', 'Test2': '200' } } ] }")
                .getAsJsonObject();
        jsonElemResult = jsonParser.parse(jsonApiStringResult)
                .getAsJsonObject();

        Assert.assertNotNull(jsonApiStringResult);
        Assert.assertEquals(jsonElemExpected, jsonElemResult);

    }

    @Test
    public void convertWithDefaultConfigurationStringToJsonApiTest()
            throws EasyJsonApiException {

        jsonApiString = "{ 'data': [ { 'type': 'TEST', 'id': '1', 'attributes': { 'attr1': 'Test Unit 2' } } ] }";

        jsonMaker.setConfig(null);
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString);

        List<Data> cloneData = jsonApiObjectResult.getData();

        Assert.assertNotNull(jsonApiObjectResult);
        Assert.assertEquals("TEST", cloneData.get(0).getType());
        Assert.assertEquals("1", cloneData.get(0).getId());
        Assert.assertEquals("Test Unit 2",
                ((Map<?, ?>) cloneData.get(0).getAttr()).get("attr1"));

    }

    @Before
    public void setUp() throws EasyJsonApiInvalidPackageException {
        jsonMakerConfig = new EasyJsonApiConfig(
                "org.easyJsonApi.entities.test");
        jsonMaker = EasyJsonApi.getInstance();
        jsonMaker.setConfig(jsonMakerConfig);

        jsonApiString = "";
    }

}
