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

import java.util.ArrayList;
import java.util.List;

import com.github.easyjsonapi.asserts.Assert;
import com.google.gson.annotations.SerializedName;

/**
 * Entity represents the contract object in json api specification
 * 
 * @author Nuno Bento (nbento.neves@gmail.com)
 */
public class JsonApi {

    @SerializedName(value = "data")
    private List<EJAData> data;

    @SerializedName(value = "errors")
    private List<EJAError> errors;

    /**
     * Add {@link EJAData} inside the data list objects inside {@link JsonApi}
     * 
     * @param data
     *            the data to insert into {@link JsonApi}
     */
    public void addData(EJAData data) {

        if (Assert.isNull(this.data)) {
            this.data = new ArrayList<>();
        }

        this.data.add(data);

    }

    /**
     * Add {@link EJAError} inside the errors list objects inside {@link JsonApi}
     * 
     * @param error
     *            the error to insert into {@link JsonApi}
     */
    public void addError(EJAError error) {

        if (Assert.isNull(this.errors)) {
            this.errors = new ArrayList<>();
        }

        this.errors.add(error);

    }

    /**
     * Get the cloned data inside the JsonApi.
     * 
     * @return the data inside {@link JsonApi}
     */
    public List<EJAData> getData() {

        if (Assert.isNull(data)) {
            data = new ArrayList<>();
        }

        List<EJAData> cloneData = new ArrayList<>(this.data);

        return cloneData;
    }

    /**
     * Get the cloned error inside the JsonApi.
     * 
     * @return the errors inside {@link JsonApi}
     */
    public List<EJAError> getErrors() {

        if (Assert.isNull(errors)) {
            errors = new ArrayList<>();
        }

        List<EJAError> cloneErrors = new ArrayList<>(this.errors);

        return cloneErrors;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "JsonApi [data=" + data + ", errors=" + errors + "]";
    }

}
