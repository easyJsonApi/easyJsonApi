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
 * Entity represents Link resource object in json api specification
 * 
 * @author Nuno Bento (nbento.neves@gmail.com)
 * @see {@link Relationship}
 * @see {@link LinkRelated}
 */
public final class Link {

    @SerializedName(value = "related")
    private final LinkRelated linkRelated;

    @SerializedName(value = "self")
    private final String self;

    public Link(LinkRelated linkRelated, String self) {
        super();
        this.linkRelated = linkRelated;
        this.self = self;
    }

    /**
     * @return the linkRelated
     */
    public LinkRelated getLinkRelated() {
        return linkRelated;
    }

    /**
     * @return the self
     */
    public String getSelf() {
        return self;
    }

}
