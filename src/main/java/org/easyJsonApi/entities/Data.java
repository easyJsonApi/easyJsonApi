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

import java.util.Map;

import org.easyJsonApi.annotations.Attributes;
import org.easyJsonApi.annotations.Links;
import org.easyJsonApi.annotations.Relationships;
import org.easyJsonApi.asserts.Assert;

import com.google.gson.annotations.SerializedName;

/**
 * Entity represents Data resource object in json api specification
 * 
 * @author Nuno Bento (nbento.neves@gmail.com)
 */
public final class Data implements Cloneable {

    public final static Object NULLABLE = null;

    @SerializedName(value = "attributes")
    private final Object attr;

    @SerializedName(value = "id")
    private final String id;

    @SerializedName(value = "links")
    private final Object links;

    @SerializedName(value = "relationships")
    private final Object rels;

    @SerializedName(value = "type")
    private final String type;

    public Data(String id, String type, Object attr, Object rels,
            Object links) {

        checkValidData(attr);
        checkValidData(rels);
        checkValidData(links);
        this.id = id;
        this.type = type;
        this.attr = attr;
        this.rels = rels;
        this.links = links;

    }

    /**
     * Check if object is valid. One object is valid when has one of the rules
     * below:
     * - is null
     * - is an instance of {@link Map}
     * - has the annotation {@link Attributes}
     * - has the annotation {@link Relationships}
     * - has the annotation {@link Links}
     * 
     * @param objectAnnotated the object needs validate
     */
    private void checkValidData(Object objectAnnotated) {

        boolean notAnnotated = true;

        if (Assert.isNull(objectAnnotated)) {
            notAnnotated = false;
        } else if (objectAnnotated instanceof Map) {
            notAnnotated = false;
        } else if (Assert.notNull(
                objectAnnotated.getClass().getAnnotation(Attributes.class))) {
            notAnnotated = false;
        } else if (Assert.notNull(objectAnnotated.getClass()
                .getAnnotation(Relationships.class))) {
            notAnnotated = false;
        } else if (Assert.notNull(
                objectAnnotated.getClass().getAnnotation(Links.class))) {
            notAnnotated = false;
        }

        if (notAnnotated) {
            throw new IllegalAccessError("Invalid object inserted!");
        }

    }

    /*
     * (non-Javadoc)
     * 
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
     * @return the links
     */
    public Object getLinks() {
        return links;
    }

    /**
     * @return the rels
     */
    public Object getRels() {
        return rels;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Data [type=" + type + ", id=" + id + ", attr=" + attr
                + ", rels=" + rels + ", links=" + links + "]";
    }

}
