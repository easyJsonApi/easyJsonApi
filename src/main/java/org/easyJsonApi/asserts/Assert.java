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

/**
 * Assert class allows you to verify usual validations the developer needs to
 * do. You have a various number static methods give the opportunity to keep the
 * code simple and clean.
 * 
 * e.g:
 * -isEmpty: validate if string is null or empty
 * -isNull: validate if object is null
 * -notEmpty: the opposite of isEmpty
 * -notNull: the opposite of notNull
 * 
 * @author Nuno Bento (nbento.neves@gmail.com)
 */
public class Assert {

    /**
     * Check if string is null or empty
     * 
     * @param value the string value
     * @return true if string is null or empty and false when occur the opposite
     */
    public static boolean isEmpty(String value) {

        if (value != null && !value.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Check if object is null
     * 
     * @param value the object value
     * @return true if object is null and false when occur the opposite
     */
    public static boolean isNull(Object value) {
        if (value == null) {
            return true;
        }

        return false;
    }

    /**
     * Check if list of objects are null
     * 
     * @param values the list with objects
     * @return true if list with objects are null and false when occur the
     *         opposite
     */
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

    /**
     * Check if string is not null or empty
     * 
     * @param value the string value
     * @return true if string has value and false when string is null or empty
     */
    public static boolean notEmpty(String value) {
        return !isEmpty(value);
    }

    /**
     * Check if object is null
     * 
     * @param value the object value
     * @return true if object has value and false when object is null
     */
    public static boolean notNull(Object value) {
        return !isNull(value);
    }

}
