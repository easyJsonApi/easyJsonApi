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
package com.github.easyjsonapi.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Entity represents source resource object in json api specification
 * 
 * @author Nuno Bento (nbento.neves@gmail.com)
 */
public class EJASource {

    @SerializedName(value = "parameter")
    private String parameter;

    @SerializedName(value = "pointer")
    private String pointer;

    /**
     * @return the parameter
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * @return the pointer
     */
    public String getPointer() {
        return pointer;
    }

    /**
     * @param parameter
     *            the parameter to set
     */
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    /**
     * @param pointer
     *            the pointer to set
     */
    public void setPointer(String pointer) {
        this.pointer = pointer;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Source [parameter=" + parameter + ", pointer=" + pointer + "]";
    }

}
