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
package org.easyJsonApi.entities;

import java.util.ArrayList;
import java.util.List;

import org.easyJsonApi.asserts.Assert;
import org.easyJsonApi.exceptions.EasyJsonApiEntityException;

import com.google.gson.annotations.SerializedName;

/**
 * Entity represents the contract object in json api specification
 * 
 * @author Nuno Bento (nbento.neves@gmail.com)
 */
public class JsonApi {

    @SerializedName(value = "data")
    private List<Data> data;

    @SerializedName(value = "errors")
    private List<Error> errors;

    /**
     * Add {@link Data} inside the data list objects inside {@link JsonApi}
     * 
     * @param data the data to insert into {@link JsonApi}
     */
    public void addData(Data data) {

        if (Assert.isNull(this.data)) {
            this.data = new ArrayList<>();
        }

        this.data.add(data);

    }

    /**
     * Add {@link Error} inside the errors list objects inside {@link JsonApi}
     * 
     * @param error the error to insert into {@link JsonApi}
     */
    public void addError(Error error) {

        if (Assert.isNull(this.errors)) {
            this.errors = new ArrayList<>();
        }

        this.errors.add(error);

    }

    /**
     * Get the cloned data inside the JsonApi.
     * 
     * @return the data inside {@link JsonApi}
     * @throws EasyJsonApiEntityException
     */
    public List<Data> getData() throws EasyJsonApiEntityException {

        if (Assert.isNull(data)) {
            data = new ArrayList<>();
        }

        List<Data> cloneData = null;

        try {
            cloneData = new ArrayList<>(data.size());
            for (Data data : this.data) {
                cloneData.add((Data) data.clone());
            }
        } catch (CloneNotSupportedException ex) {
            throw new EasyJsonApiEntityException("Problem when try to clone the data attribute!", ex);
        }

        return cloneData;
    }

    /**
     * Get the cloned error inside the JsonApi.
     * 
     * @return the errors inside {@link JsonApi}
     * @throws EasyJsonApiEntityException
     */
    public List<Error> getErrors() throws EasyJsonApiEntityException {

        if (Assert.isNull(errors)) {
            errors = new ArrayList<>();
        }

        List<Error> cloneErrors = null;
        try {

            cloneErrors = new ArrayList<>(errors.size());
            for (Error error : this.errors) {
                cloneErrors.add((Error) error.clone());
            }

        } catch (CloneNotSupportedException ex) {
            throw new EasyJsonApiEntityException("Problem when try to clone the data attribute!", ex);
        }

        return cloneErrors;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "JsonApi [data=" + data + ", errors=" + errors + "]";
    }

}
