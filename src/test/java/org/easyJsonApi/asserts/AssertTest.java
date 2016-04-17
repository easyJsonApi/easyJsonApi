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
package org.easyJsonApi.asserts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AssertTest {

    @Test
    public void isEmptyTest() {

        assertTrue(Assert.isEmpty(""));
        assertTrue(Assert.isEmpty(null));
        assertFalse(Assert.isEmpty("Test"));

    }

    @Test
    public void isNullTest() {

        assertFalse(Assert.isNull("test", null, ""));
        assertFalse(Assert.isNull("test", "5", ""));
        assertTrue(Assert.isNull(null, null, null));
        assertFalse(Assert.isNull("test"));

        Object obj = null;

        assertTrue(Assert.isNull(obj));

    }

    @Test
    public void notEmptyTest() {

        assertTrue(Assert.notEmpty("Test"));
        assertFalse(Assert.notEmpty(""));

    }

    @Test
    public void notNullTest() {

        assertTrue(Assert.notNull("test"));

    }

}
