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
package com.github.easyjsonapi.exceptions;

/**
 * Generic exception for EasyJsonApi
 * 
 * @author Nuno Bento (nbento.neves@gmail.com)
 */
public class EasyJsonApiException extends Exception {

    /**
     * UID Generated
     */
    private static final long serialVersionUID = -5544661923448271394L;

    public EasyJsonApiException(String message) {
        super(message);
    }

    public EasyJsonApiException(String message, Throwable cause) {
        super(message, cause);
    }

}
