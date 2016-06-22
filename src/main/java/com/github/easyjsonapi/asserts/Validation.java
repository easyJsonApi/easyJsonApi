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
package com.github.easyjsonapi.asserts;

import java.util.Map;

import com.github.easyjsonapi.annotations.Attributes;
import com.github.easyjsonapi.annotations.Meta;
import com.github.easyjsonapi.annotations.MetaRelationship;
import com.github.easyjsonapi.exceptions.EasyJsonApiEntityException;

public class Validation {

    /**
     * Check if object is valid. One object is valid when has one of the rules
     * below:
     *
     * - is an instance of {@link Map}
     * - has the annotation {@link Attributes}
     * - has the annotation {@link Meta}
     * 
     * @param objectAnnotated
     *            the object needs validate
     */
    public static void checkValidObject(Object objectAnnotated) {

        boolean notAnnotated = true;

        if (Assert.isNull(objectAnnotated)) {
            notAnnotated = false;
        } else if (objectAnnotated instanceof Map) {
            notAnnotated = false;
        } else if (Assert.notNull(objectAnnotated.getClass().getAnnotation(Attributes.class))) {
            notAnnotated = false;
        } else if (Assert.notNull(objectAnnotated.getClass().getAnnotation(Meta.class))) {
            notAnnotated = false;
        } else if (Assert.notNull(objectAnnotated.getClass().getAnnotation(MetaRelationship.class))) {
            notAnnotated = false;
        }

        if (notAnnotated) {
            throw new EasyJsonApiEntityException("Invalid object inserted! Please check the annotation inside the pojo!");
        }

    }

}
