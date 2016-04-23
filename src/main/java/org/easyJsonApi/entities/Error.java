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
 * Entity represents Error resource object in json api specification
 * 
 * @author Nuno Bento (nbento.neves@gmail.com)
 */
public class Error implements Cloneable {

    @SerializedName(value = "code")
    private String code;

    @SerializedName(value = "detail")
    private String detail;

    @SerializedName(value = "id")
    private String id;

    @SerializedName(value = "meta")
    private Object meta;

    @SerializedName(value = "source")
    private Source source;

    @SerializedName(value = "status")
    private HttpStatus status;

    @SerializedName(value = "title")
    private String title;

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

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param meta the meta to set
     */
    public void setMeta(Object meta) {
        this.meta = meta;
    }

    /**
     * @param source the source to set
     */
    public void setSource(Source source) {
        this.source = source;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "JsonApiError [id=" + id + ", status=" + status + ", code=" + code + ", title=" + title + ", detail=" + detail + ", source=" + source
                + ", meta=" + meta + "]";
    }

}
