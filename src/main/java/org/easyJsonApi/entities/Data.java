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

import com.google.gson.annotations.SerializedName;

/**
 * Entity represents Data resource object in json api specification
 * 
 * @author Nuno Bento (nbento.neves@gmail.com)
 */
public class Data {

    @SerializedName(value = "attributes")
    private Object attr;

    @SerializedName(value = "id")
    private String id;

    @SerializedName(value = "links")
    private Object links;

    @SerializedName(value = "relationships")
    private Object rels;

    @SerializedName(value = "type")
    private String type;

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

    /**
     * @param attr the attr to set
     */
    public void setAttr(Object attr) {
        this.attr = attr;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param links the links to set
     */
    public void setLinks(Object links) {
        this.links = links;
    }

    /**
     * @param rels the rels to set
     */
    public void setRels(Object rels) {
        this.rels = rels;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Data [type=" + type + ", id=" + id + ", attr=" + attr + ", rels=" + rels + ", links=" + links + "]";
    }

}
