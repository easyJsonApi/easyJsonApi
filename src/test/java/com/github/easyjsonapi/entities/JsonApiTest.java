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
package com.github.easyjsonapi.entities;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.github.easyjsonapi.entities.DataEJA;
import com.github.easyjsonapi.entities.ErrorEJA;
import com.github.easyjsonapi.entities.HttpStatusEJA;
import com.github.easyjsonapi.entities.JsonApi;
import com.github.easyjsonapi.entities.NullableEJA;
import com.github.easyjsonapi.entities.test.EntityDependencyTest;
import com.github.easyjsonapi.entities.test.EntityTestAttr1;

public class JsonApiTest {

    @Test
    public void addDataJsonApiTest() {

        JsonApi jsonApi = new JsonApi();

        jsonApi.addData(new DataEJA("100", "books", NullableEJA.OBJECT));

        List<DataEJA> dataList = jsonApi.getData();

        Assert.assertEquals(1, dataList.size());

    }

    @Test
    public void addErrorJsonApiTest() {

        JsonApi jsonApi = new JsonApi();

        jsonApi.addError(new ErrorEJA("100", "books", HttpStatusEJA.ACCEPTED, NullableEJA.OBJECT, NullableEJA.SOURCE));

        List<ErrorEJA> errorList = jsonApi.getErrors();

        Assert.assertEquals(1, errorList.size());

    }

    // FIXME: Review test
    @Test
    public void getDataTest() {

        JsonApi jsonApi = new JsonApi();

        EntityDependencyTest testDependency = new EntityDependencyTest();
        testDependency.getAttr().put("TestMap", "1");

        EntityTestAttr1 testEntity = new EntityTestAttr1();
        testEntity.setAttr1("Test Unit");
        testEntity.setAttr2(new BigDecimal(100));
        testEntity.setAttr3(testDependency);

        // Data dataJsonApi = new Data("1", "TEST", testEntity, Data.NULLABLE, Data.NULLABLE);
        DataEJA dataJsonApi = new DataEJA("1", "TEST", testEntity);

        jsonApi.addData(dataJsonApi);

        List<DataEJA> dataCloned = jsonApi.getData();

        Assert.assertEquals(1, dataCloned.size());
        Assert.assertEquals("1", dataCloned.get(0).getId());
        Assert.assertEquals("TEST", dataCloned.get(0).getType());
        Assert.assertNotEquals(System.identityHashCode(dataJsonApi), System.identityHashCode(dataCloned));

        // Remove the first element
        dataCloned.remove(0);

        Assert.assertEquals(0, dataCloned.size());

        List<DataEJA> dataClonedFirst = jsonApi.getData();
        List<DataEJA> dataClonedSecond = jsonApi.getData();

        Assert.assertNotEquals(System.identityHashCode(dataClonedFirst), System.identityHashCode(dataClonedSecond));
        // Assert.assertNotEquals(System.identityHashCode(dataClonedFirst.get(0).getAttr()),
        // System.identityHashCode(dataClonedSecond.get(0).getAttr()));

        Assert.assertEquals(1, dataClonedFirst.size());

        ((EntityTestAttr1) dataClonedFirst.get(0).getAttr()).setAttr1("INVALID TEST");

        // Assert.assertNotEquals(((EntityTestAttr1)
        // dataClonedFirst.get(0).getAttr()).getAttr1(),
        // ((EntityTestAttr1) dataClonedSecond.get(0).getAttr()).getAttr1());

    }

}
