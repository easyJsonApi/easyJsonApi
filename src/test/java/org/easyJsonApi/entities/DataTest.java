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
import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

public class DataTest {

    @Test
    public void cloneTest() throws CloneNotSupportedException {

        Data dataType = new Data("1", "CLONE", Data.NULLABLE, Data.NULLABLE,
                Data.NULLABLE);

        Data dataClone = (Data) dataType.clone();

        Assert.assertEquals(dataType.getId(), dataClone.getId());
        Assert.assertEquals(dataType.getType(), dataClone.getType());
        Assert.assertNotEquals(System.identityHashCode(dataType.hashCode()),
                System.identityHashCode(dataClone.hashCode()));

    }

    @Test(expected = IllegalAccessError.class)
    public void invalidDataInstance() {

        new Data("100", "INVALID_DATA", new HashMap<>(), new LinkedList<>(),
                new ArrayList<>());

    }

    @Test(expected = IllegalAccessError.class)
    public void invalidDataInstanceAttrTest() {

        new Data("100", "INVALID_DATA", new ArrayList<>(), Data.NULLABLE,
                Data.NULLABLE);

    }

    @Test(expected = IllegalAccessError.class)
    public void invalidDataInstanceLinksTest() {

        new Data("100", "INVALID_DATA", Data.NULLABLE, new ArrayList<>(),
                Data.NULLABLE);

    }

    @Test(expected = IllegalAccessError.class)
    public void invalidDataInstanceRelsTest() {

        new Data("100", "INVALID_DATA", Data.NULLABLE, Data.NULLABLE,
                new ArrayList<>());

    }

}
