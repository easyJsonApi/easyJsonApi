package org.easyJsonApi.entities;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Pojo represents the Request object in json api specification
 * 
 * @author nbento.neves@gmail.com
 */
public class JsonApi {

    @SerializedName(value = "data")
    private List<Data> data;

    @SerializedName(value = "errors")
    private List<Error> errors;

    /**
     * @return the data
     */
    public List<Data> getData() {
        if (data == null) {
            data = new ArrayList<>();
        }
        return data;
    }

    /**
     * @return the errors
     */
    public List<Error> getErrors() {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        return errors;
    }

    /**
     * @param errors the errors to set
     */
    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<Data> data) {
        this.data = data;
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
