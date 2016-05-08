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

import org.junit.Assert;
import org.junit.Test;

public class ErrorTest {

    @Test
    public void cloneTest() throws CloneNotSupportedException {

        Error errorType = new Error("100", "First error!", HttpStatus.OK,
                Error.NULLABLE, Source.NULLABLE);

        Error errorClone = (Error) errorType.clone();

        Assert.assertEquals(errorType.getId(), errorClone.getId());
        Assert.assertEquals(errorType.getTitle(), errorClone.getTitle());
        Assert.assertNotEquals(System.identityHashCode(errorType.hashCode()),
                System.identityHashCode(errorClone.hashCode()));

    }

    @Test(expected = IllegalAccessError.class)
    public void invalidErrorInstance() {

        new Error("100", "First error!", HttpStatus.OK, new ArrayList<>(),
                Source.NULLABLE);

    }
}