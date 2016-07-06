/*
 * #%L
 * com.github.easyjsonapi:easyJsonApi
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
package com.github.easyjsonapi.core;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/configs/spring-eja-config.xml" })
public class EasyJsonApiSpringTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private EasyJsonApi easyJsonApi;

    @Test
    public void easyJsonApiInstanceTest() {

        EasyJsonApi easyJsonApiUsingContext = (EasyJsonApi) context.getBean("easyJsonApi");

        Assert.assertNotNull(easyJsonApiUsingContext);
        Assert.assertNotNull(easyJsonApiUsingContext.getEasyJsonApiConfig());
        Assert.assertNotNull(easyJsonApi);
        Assert.assertNotNull(easyJsonApi.getEasyJsonApiConfig());

    }

}
