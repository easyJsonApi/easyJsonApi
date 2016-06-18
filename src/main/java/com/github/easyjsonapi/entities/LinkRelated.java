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

import com.github.easyjsonapi.asserts.Validation;
import com.google.gson.annotations.SerializedName;

/**
 * Entity represents related resource object in json api specification
 * 
 * @author Nuno Bento (nbento.neves@gmail.com)
 * @see {@link Link}
 */
public final class LinkRelated {

    @SerializedName(value = "href")
    private final String href;

    @SerializedName(value = "meta")
    private final Object meta;

    public LinkRelated(String href, Object meta) {
        super();
        Validation.checkValidObject(meta);
        this.href = href;
        this.meta = meta;
    }

    /**
     * @return the href
     */
    public String getHref() {
        return href;
    }

    /**
     * @return the meta
     */
    public Object getMeta() {
        return meta;
    }

}
