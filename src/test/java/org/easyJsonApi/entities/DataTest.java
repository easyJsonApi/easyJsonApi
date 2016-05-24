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

import java.util.ArrayList;
import java.util.LinkedList;

import org.easyJsonApi.exceptions.EasyJsonApiEntityException;
import org.junit.Assert;
import org.junit.Test;

public class DataTest {

    @Test
    public void closeDataTest() throws CloneNotSupportedException {

        Data dataType = new Data("1", "books", Nullable.OBJECT);

        Data dataClone = (Data) dataType.clone();

        Assert.assertEquals(dataType.getId(), dataClone.getId());
        Assert.assertEquals(dataType.getType(), dataClone.getType());
        Assert.assertNotEquals(System.identityHashCode(dataType.hashCode()), System.identityHashCode(dataClone.hashCode()));

    }

    @Test(expected = EasyJsonApiEntityException.class)
    public void invalidDataInstance() {

        new Data("100", "books", new LinkedList<>(), Nullable.RELATIONSHIPS);

    }

    @Test(expected = EasyJsonApiEntityException.class)
    public void invalidDataInstanceAttrTest() {

        new Data("200", "books", new ArrayList<>());

    }

}
