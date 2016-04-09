package org.easyJsonApi.entities;

import com.google.gson.annotations.SerializedName;

public class Link {

    @SerializedName(value = "about")
    private String about;

    /**
     * @return the about
     */
    public String getAbout() {
        return about;
    }

    /**
     * @param about the about to set
     */
    public void setAbout(String about) {
        this.about = about;
    }

}
