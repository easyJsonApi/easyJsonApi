package org.easyJsonApi.entities;

import com.google.gson.annotations.SerializedName;

public class Source {

    @SerializedName(value = "pointer")
    private String pointer;

    @SerializedName(value = "parameter")
    private String parameter;

    /**
     * @return the pointer
     */
    public String getPointer() {
        return pointer;
    }

    /**
     * @param pointer the pointer to set
     */
    public void setPointer(String pointer) {
        this.pointer = pointer;
    }

    /**
     * @return the parameter
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * @param parameter the parameter to set
     */
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

}
