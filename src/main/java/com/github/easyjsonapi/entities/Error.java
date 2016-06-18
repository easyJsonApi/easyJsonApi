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
 * Entity represents error resource object in json api specification
 * 
 * @author Nuno Bento (nbento.neves@gmail.com)
 * @see {@link JsonApi}
 * @see {@link Source}
 * @see {@link HttpStatus}
 */
public final class Error implements Cloneable {

    @SerializedName(value = "code")
    private final String code;

    @SerializedName(value = "detail")
    private final String detail;

    @SerializedName(value = "id")
    private final String id;

    @SerializedName(value = "meta")
    private final Object meta;

    @SerializedName(value = "source")
    private final Source source;

    @SerializedName(value = "status")
    private final HttpStatus status;

    @SerializedName(value = "title")
    private final String title;

    public Error(String id, String title, HttpStatus status, Object meta, Source source) {
        Validation.checkValidObject(meta);
        this.code = null;
        this.detail = null;
        this.id = id;
        this.meta = meta;
        this.source = source;
        this.status = status;
        this.title = title;
    }

    public Error(String id, String title, HttpStatus status, String code, String detail, Object meta, Source source) {
        Validation.checkValidObject(meta);
        this.code = code;
        this.detail = detail;
        this.id = id;
        this.meta = meta;
        this.source = source;
        this.status = status;
        this.title = title;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the meta
     */
    public Object getMeta() {
        return meta;
    }

    /**
     * @return the source
     */
    public Source getSource() {
        return source;
    }

    /**
     * @return the status
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "JsonApiError [id=" + id + ", status=" + status + ", code=" + code + ", title=" + title + ", detail=" + detail + ", source=" + source
                + ", meta=" + meta + "]";
    }

}
