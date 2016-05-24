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
package org.easyJsonApi.core;

import org.easyJsonApi.core.EasyJsonApiConfig;
import org.easyJsonApi.exceptions.EasyJsonApiInvalidPackageException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class EasyJsonApiConfigTest {

    @Test
    public void constructionTest() throws EasyJsonApiInvalidPackageException {

        EasyJsonApiConfig configDefaultConstruct = new EasyJsonApiConfig();

        Assert.assertNotNull(configDefaultConstruct);

        EasyJsonApiConfig configConstructWithString = new EasyJsonApiConfig("org.easyJsonApi.entities.test");

        Assert.assertNotNull(configConstructWithString);
        Assert.assertNotEquals(0, configConstructWithString.getPackagesSearched().length);

    }

    // FIXME: Find solution to test this exception
    @Ignore
    @Test(expected = EasyJsonApiInvalidPackageException.class)
    public void setInvalidPackagesToSearchTest() throws EasyJsonApiInvalidPackageException {

        new EasyJsonApiConfig("invalid.test");

    }

}
