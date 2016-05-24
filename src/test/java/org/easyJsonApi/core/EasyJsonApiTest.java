/*
\ * #%L
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
package org.easyJsonApi.core;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easyJsonApi.TestHelper;
import org.easyJsonApi.core.EasyJsonApi;
import org.easyJsonApi.core.EasyJsonApiConfig;
import org.easyJsonApi.entities.Data;
import org.easyJsonApi.entities.Error;
import org.easyJsonApi.entities.HttpStatus;
import org.easyJsonApi.entities.JsonApi;
import org.easyJsonApi.entities.Nullable;
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

    private final String JSON_TEST_FOLDER = "jsonTest/EasyJsonApiTest/";

    private JsonApi jsonApiObject;

    private JsonApi jsonApiObjectResult;

    private String jsonApiString;

    private String jsonApiStringResult;

    private EasyJsonApi jsonMaker;

    private EasyJsonApiConfig jsonMakerConfig;

    private final Logger logger = LoggerFactory.getLogger(EasyJsonApiTest.class);

    @Test
    public void convertDataAttributesJsonApiToStringTest() throws EasyJsonApiException {

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElemExpected = null;
        JsonElement jsonElemResult = null;

        // Check result for empty object
        jsonApiObject = new JsonApi();
        jsonApiStringResult = jsonMaker.convertJsonApiToString(jsonApiObject, EntityTestAttr1.class);

        logger.info(jsonApiStringResult);

        jsonElemExpected = jsonParser.parse("{ }").getAsJsonObject();
        jsonElemResult = jsonParser.parse(jsonApiStringResult).getAsJsonObject();

        Assert.assertEquals(jsonElemExpected, jsonElemResult);

        // Check result for object with all attributes using EntityTestAttr1
        jsonApiObject = new JsonApi();

        EntityTestAttr1 entityTest = new EntityTestAttr1();
        entityTest.setAttr1("EasyJsonApi api book");
        entityTest.setAttr2(new BigDecimal(100));

        // Data dataRequest = new Data("Test", "REQUEST_JSON_API", entityTest, Data.NULLABLE, Data.NULLABLE);
        Data dataRequest = new Data("1", "books", entityTest);
        jsonApiObject.addData(dataRequest);

        jsonApiStringResult = jsonMaker.convertJsonApiToString(jsonApiObject, EntityTestAttr1.class);

        logger.info(jsonApiStringResult);

        jsonApiString = TestHelper.retriveJsonFile(JSON_TEST_FOLDER + "convertDataAttributesJsonApiToStringTest.json");

        jsonElemExpected = jsonParser.parse(jsonApiString).getAsJsonObject();
        jsonElemResult = jsonParser.parse(jsonApiStringResult).getAsJsonObject();

        Assert.assertEquals(jsonElemExpected, jsonElemResult);

    }

    @Test
    public void convertDataAttributesStringToJsonApiTest() throws EasyJsonApiException {

        // Check result for json with data empty
        jsonApiString = "{ 'data': [ ] }";
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString, EntityTestAttr1.class);

        Assert.assertNull(jsonApiObjectResult);

        // Check result for json with data empty but array has one instance
        jsonApiString = "{ 'data': [ { } ] }";
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString, EntityTestAttr1.class);

        Assert.assertNull(jsonApiObjectResult);

        // Check result for json with attributes empty
        jsonApiString = "{ 'data': [ { 'type': 'books', 'id': '1', 'attributes': { } } ] }";
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString, EntityTestAttr1.class);

        List<Data> cloneData = jsonApiObjectResult.getData();

        Assert.assertNotNull(jsonApiObjectResult);
        Assert.assertNotNull(cloneData.get(0));
        Assert.assertEquals("1", cloneData.get(0).getId());
        Assert.assertEquals("books", cloneData.get(0).getType());

        jsonApiString = TestHelper.retriveJsonFile(JSON_TEST_FOLDER + "convertDataAttributesStringToJsonApiTest.json");

        // Check result for json with all attributes using EntityTestAttr1
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString, EntityTestAttr1.class);

        cloneData = jsonApiObjectResult.getData();

        Assert.assertNotNull(jsonApiObjectResult);
        Assert.assertNotNull(cloneData.get(0));
        Assert.assertEquals("1", cloneData.get(0).getId());
        Assert.assertEquals("books", cloneData.get(0).getType());
        Assert.assertEquals("Spring book", ((EntityTestAttr1) cloneData.get(0).getAttr()).getAttr1());
        Assert.assertEquals("100", ((EntityTestAttr1) cloneData.get(0).getAttr()).getAttr2().toPlainString());
        Assert.assertEquals("David", ((EntityTestAttr1) cloneData.get(0).getAttr()).getAttr3().getAttr().get("author"));

        jsonApiString = TestHelper.retriveJsonFile(JSON_TEST_FOLDER + "convertDataAttributesStringToJsonApiTest2.json");

        // Check result for json with all attributes using EntityTestAttr2
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString, EntityTestAttr2.class);

        cloneData = jsonApiObjectResult.getData();

        Assert.assertNotNull(jsonApiObjectResult);
        Assert.assertNotNull(cloneData.get(0));
        Assert.assertEquals("1", cloneData.get(0).getId());
        Assert.assertEquals("books", cloneData.get(0).getType());
        Assert.assertEquals("My best book", ((EntityTestAttr2) cloneData.get(0).getAttr()).getAttr1());

    }

    @Test
    public void convertErrorsJsonApiToStringTest() throws EasyJsonApiException {

        // Check result for json with errors empty
        jsonApiString = "{ 'errors': [ { } ] }";
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString, EntityTestAttr1.class);

        Assert.assertNull(jsonApiObjectResult);

        // Check result for json with error
        jsonApiString = TestHelper.retriveJsonFile(JSON_TEST_FOLDER + "convertErrorsJsonApiToStringTest.json");
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString, EntityTestAttr1.class);

        List<Error> cloneError = jsonApiObjectResult.getErrors();

        Assert.assertNotNull(jsonApiObjectResult);
        Assert.assertNotNull(cloneError.get(0));
        Assert.assertEquals("404", cloneError.get(0).getId());
        Assert.assertEquals("4000", cloneError.get(0).getCode());
        Assert.assertEquals("Invalid book searched", cloneError.get(0).getDetail());
        Assert.assertEquals("Invalid operation", cloneError.get(0).getTitle());
        Assert.assertEquals("/books/attributes/tests", cloneError.get(0).getSource().getPointer());

        // Check result for json with multiple errors
        jsonApiString = TestHelper.retriveJsonFile(JSON_TEST_FOLDER + "convertErrorsJsonApiToStringTest2.json");
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString, EntityTestAttr1.class);

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
        jsonElemResult = jsonParser.parse(jsonApiStringResult).getAsJsonObject();

        Assert.assertEquals(jsonElemExpected, jsonElemResult);

        // Check result for object with one error
        jsonApiObject = new JsonApi();

        Error error = new Error("1", "Invalid operation", HttpStatus.NOT_ACCEPTABLE, "4000", "Not acceptable operation!", Nullable.OBJECT,
                Nullable.SOURCE);

        jsonApiObject.addError(error);

        jsonApiStringResult = jsonMaker.convertJsonApiToString(jsonApiObject);

        jsonApiString = TestHelper.retriveJsonFile(JSON_TEST_FOLDER + "convertErrorsStringToJsonApiTest.json");

        jsonElemExpected = jsonParser.parse(jsonApiString).getAsJsonObject();
        jsonElemResult = jsonParser.parse(jsonApiStringResult).getAsJsonObject();

        Assert.assertEquals(jsonElemExpected, jsonElemResult);

        // Check result for object with multiple errors
        jsonApiObject = new JsonApi();

        Error errorSecond = new Error("2", "Invalid user", HttpStatus.BAD_REQUEST, "4000", "Bad request!", Nullable.OBJECT, Nullable.SOURCE);

        jsonApiObject.addError(error);
        jsonApiObject.addError(errorSecond);

        jsonApiStringResult = jsonMaker.convertJsonApiToString(jsonApiObject);

        jsonApiString = TestHelper.retriveJsonFile(JSON_TEST_FOLDER + "convertErrorsStringToJsonApiTest2.json");

        jsonElemExpected = jsonParser.parse(jsonApiString).getAsJsonObject();
        jsonElemResult = jsonParser.parse(jsonApiStringResult).getAsJsonObject();

        Assert.assertEquals(jsonElemExpected, jsonElemResult);

    }

    @Test
    public void convertInvalidJsonApiToStringTest() throws EasyJsonApiException {

        // Check result for null object
        jsonApiObject = null;
        String result = jsonMaker.convertJsonApiToString(jsonApiObject, EntityTestAttr1.class);

        Assert.assertNull(result);
    }

    @Test
    public void convertInvalidJsonStringToJsonApiTest() throws EasyJsonApiException {

        // Check result for empty json
        jsonApiString = "";
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString, EntityTestAttr1.class);

        Assert.assertNull(jsonApiObjectResult);

        // Check result for null json
        jsonApiString = null;
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString, EntityTestAttr1.class);

        Assert.assertNull(jsonApiObjectResult);

        // Check result for json with invalid parameters
        jsonApiString = "{ }";
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString, EntityTestAttr1.class);

        Assert.assertNull(jsonApiObjectResult);

    }

    @Test(expected = EasyJsonApiMalformedJsonException.class)
    public void convertStringToJsonApiMalformatedJsonTest() throws EasyJsonApiException {

        // Check result for invalid json
        jsonApiString = "{ 'data': [ { ] }";
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString, EntityTestAttr1.class);

    }

    @Test(expected = EasyJsonApiMalformedJsonException.class)
    public void convertStringToJsonApiMalformatedJsonTest2() throws EasyJsonApiException {

        // Check result for invalid json
        jsonApiString = "{ 'dat: [ { ] }";
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString, EntityTestAttr1.class);

    }

    @Test
    public void convertWithDefaultConfigurationJsonApiToStringTest() throws EasyJsonApiException {

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElemExpected = null;
        JsonElement jsonElemResult = null;

        JsonApi request = new JsonApi();

        Map<String, String> attrMap = new HashMap<>();
        attrMap.put("name", "Miguel");
        attrMap.put("age", "20");

        Data dataRequest = new Data("1", "books", attrMap);

        request.addData(dataRequest);

        // Set the configuration to null for using default map type
        jsonMaker.setConfig(null);
        jsonApiStringResult = jsonMaker.convertJsonApiToString(request);

        jsonApiString = TestHelper.retriveJsonFile(JSON_TEST_FOLDER + "convertWithDefaultConfigurationJsonApiToStringTest.json");

        jsonElemExpected = jsonParser.parse(jsonApiString).getAsJsonObject();
        jsonElemResult = jsonParser.parse(jsonApiStringResult).getAsJsonObject();

        Assert.assertNotNull(jsonApiStringResult);
        Assert.assertEquals(jsonElemExpected, jsonElemResult);

    }

    @Test
    public void convertWithDefaultConfigurationStringToJsonApiTest() throws EasyJsonApiException {

        jsonApiString = TestHelper.retriveJsonFile(JSON_TEST_FOLDER + "convertWithDefaultConfigurationStringToJsonApiTest.json");

        jsonMaker.setConfig(null);
        jsonApiObjectResult = jsonMaker.convertStringToJsonApi(jsonApiString);

        List<Data> cloneData = jsonApiObjectResult.getData();

        Assert.assertNotNull(jsonApiObjectResult);
        Assert.assertEquals("books", cloneData.get(0).getType());
        Assert.assertEquals("1", cloneData.get(0).getId());
        Assert.assertEquals("Teresa", ((Map<?, ?>) cloneData.get(0).getAttr()).get("name"));

    }

    @Before
    public void setUp() throws EasyJsonApiInvalidPackageException {
        jsonMakerConfig = new EasyJsonApiConfig("org.easyJsonApi.entities.test");
        jsonMaker = EasyJsonApi.getInstance();
        jsonMaker.setConfig(jsonMakerConfig);

        jsonApiString = "";
    }

}
