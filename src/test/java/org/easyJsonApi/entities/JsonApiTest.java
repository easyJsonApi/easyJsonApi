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
package org.easyJsonApi.entities;

import java.math.BigDecimal;
import java.util.List;

import org.easyJsonApi.entities.test.EntityDependencyTest;
import org.easyJsonApi.entities.test.EntityTestAttr1;
import org.easyJsonApi.exceptions.EasyJsonApiEntityException;
import org.junit.Assert;
import org.junit.Test;

public class JsonApiTest {

    @Test
    public void getDataTest() throws EasyJsonApiEntityException {

        JsonApi jsonApi = new JsonApi();

        EntityDependencyTest testDependency = new EntityDependencyTest();
        testDependency.getAttr().put("TestMap", "1");

        EntityTestAttr1 testEntity = new EntityTestAttr1();
        testEntity.setAttr1("Test Unit");
        testEntity.setAttr2(new BigDecimal(100));
        testEntity.setAttr3(testDependency);

        Data dataJsonApi = new Data("1", "TEST", testEntity, Data.NULLABLE,
                Data.NULLABLE);

        jsonApi.addData(dataJsonApi);

        List<Data> dataCloned = jsonApi.getData();

        Assert.assertEquals(1, dataCloned.size());
        Assert.assertEquals("1", dataCloned.get(0).getId());
        Assert.assertEquals("TEST", dataCloned.get(0).getType());
        Assert.assertNotEquals(System.identityHashCode(dataJsonApi),
                System.identityHashCode(dataCloned));

        // Remove the first element
        dataCloned.remove(0);

        Assert.assertEquals(0, dataCloned.size());

        List<Data> dataClonedFirst = jsonApi.getData();
        List<Data> dataClonedSecond = jsonApi.getData();

        Assert.assertNotEquals(System.identityHashCode(dataClonedFirst),
                System.identityHashCode(dataClonedSecond));
        // Assert.assertNotEquals(System.identityHashCode(dataClonedFirst.get(0).getAttr()),
        // System.identityHashCode(dataClonedSecond.get(0).getAttr()));

        Assert.assertEquals(1, dataClonedFirst.size());

        ((EntityTestAttr1) dataClonedFirst.get(0).getAttr())
                .setAttr1("INVALID TEST");

        // Assert.assertNotEquals(((EntityTestAttr1)
        // dataClonedFirst.get(0).getAttr()).getAttr1(),
        // ((EntityTestAttr1) dataClonedSecond.get(0).getAttr()).getAttr1());

    }

}
