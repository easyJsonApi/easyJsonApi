package org.easyJsonApi.entities;

import com.google.gson.annotations.SerializedName;

public class Error {

    @SerializedName(value = "id")
    private String id;

    @SerializedName(value = "status")
    private HttpStatus status;

    @SerializedName(value = "code")
    private String code;

    @SerializedName(value = "title")
    private String title;

    @SerializedName(value = "detail")
    private String detail;

    @SerializedName(value = "source")
    private Source source;

    @SerializedName(value = "meta")
    private Object meta;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the status
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * @return the source
     */
    public Source getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(Source source) {
        this.source = source;
    }

    /**
     * @return the meta
     */
    public Object getMeta() {
        return meta;
    }

    /**
     * @param meta the meta to set
     */
    public void setMeta(Object meta) {
        this.meta = meta;
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
