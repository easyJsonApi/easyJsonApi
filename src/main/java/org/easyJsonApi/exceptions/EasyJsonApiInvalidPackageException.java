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
package org.easyJsonApi.exceptions;

/**
 * Exception when it was defined invalid package to search meta annotations
 * 
 * @author Nuno Bento (nbento.neves@gmail.com)
 */
public class EasyJsonApiInvalidPackageException extends EasyJsonApiException {

    /**
     * UID Generated
     */
    private static final long serialVersionUID = -1850836160599521183L;

    public EasyJsonApiInvalidPackageException(String message) {
        super(message);
    }

    public EasyJsonApiInvalidPackageException(String message, Throwable cause) {
        super(message, cause);
    }

}
