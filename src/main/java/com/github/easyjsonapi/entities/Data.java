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

import com.github.easyjsonapi.asserts.Assert;
import com.github.easyjsonapi.asserts.Validation;
import com.google.gson.annotations.SerializedName;

/**
 * Entity represents data resource object in json api specification
 * 
 * @author Nuno Bento (nbento.neves@gmail.com)
 * @see {@link JsonApi}
 * @see {@link Relationships}
 */
public final class Data implements Cloneable {

    @SerializedName(value = "attributes")
    private final Object attr;

    @SerializedName(value = "id")
    private final String id;

    // @SerializedName(value = "links")
    // private final Object links;

    @SerializedName(value = "relationships")
    private Relationships rels;

    @SerializedName(value = "type")
    private final String type;

    public Data(String id, String type, Object attr) {
        Validation.checkValidObject(attr);
        this.id = id;
        this.type = type;
        this.attr = attr;
        this.rels = null;
    }

    public Data(String id, String type, Object attr, Relationships rels) {
        Validation.checkValidObject(attr);
        this.id = id;
        this.type = type;
        this.attr = attr;
        this.rels = rels;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * @return the attr
     */
    public Object getAttr() {
        return attr;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the rels
     */
    public Relationships getRels() {
        if (Assert.isNull(rels)) {
            this.rels = new Relationships();
        }
        return rels;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

}
