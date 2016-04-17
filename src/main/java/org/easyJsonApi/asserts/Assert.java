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

public class Assert {

    public static boolean isEmpty(String value) {

        if (value != null && !value.isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean isNull(Object value) {
        if (value == null) {
            return true;
        }

        return false;
    }

    public static boolean isNull(Object... values) {

        boolean anyNotNullable = false;

        int index = 0;

        while (index < values.length && !anyNotNullable) {
            if (notNull(values[index])) {
                anyNotNullable = true;
            }
            index++;
        }

        return !anyNotNullable;
    }

    public static boolean notEmpty(String value) {
        return !isEmpty(value);
    }

    public static boolean notNull(Object value) {
        return !isNull(value);
    }

}
