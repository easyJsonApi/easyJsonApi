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
package org.easyJsonApi;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class TestHelper {

    /**
     * Retrive the json inside the test file
     * 
     * @param fileName
     *            the file name
     * @return string with json to test
     */
    public static String retriveJsonFile(String fileName) {

        String json = null;

        try {
            json = IOUtils.toString(TestHelper.class.getClassLoader().getResourceAsStream(fileName), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

}
