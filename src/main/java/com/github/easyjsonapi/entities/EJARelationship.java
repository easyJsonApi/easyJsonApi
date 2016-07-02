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

import java.util.HashSet;
import java.util.Set;

import com.github.easyjsonapi.asserts.Assert;
import com.github.easyjsonapi.asserts.Validation;
import com.google.gson.annotations.SerializedName;

/**
 * Entity represents relationship resource object in json api specification
 * 
 * @author Nuno Bento (nbento.neves@gmail.com)
 */
public class EJARelationship {

    @SerializedName(value = "data")
    private final Set<EJADataLinkage> dataLinkage;

    @SerializedName(value = "links")
    private final EJALink links;

    @SerializedName(value = "meta")
    private final Object meta;

    private final String name;

    public EJARelationship(String name, EJALink links, Object meta) {
        super();
        Validation.checkValidObject(meta);
        this.dataLinkage = new HashSet<>();
        this.name = name;
        this.links = links;
        this.meta = meta;
    }

    /**
     * Add one {@link EJADataLinkage}
     * 
     * @param dataLinkage
     *            the data linkage
     */
    public void addDataLinkage(EJADataLinkage dataLinkage) {
        if (Assert.notNull(this.dataLinkage)) {
            this.dataLinkage.add(dataLinkage);
        }
    }

    /**
     * @return the dataLinkage
     */
    public Set<EJADataLinkage> getDataLinkage() {
        return dataLinkage;
    }

    /**
     * @return the links
     */
    public EJALink getLinks() {
        return links;
    }

    /**
     * @return the meta
     */
    public Object getMeta() {
        return meta;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

}
