package org.easyJsonApi.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Pojo represents the Data object in json api specification
 * 
 * @author nbento.neves@gmail.com
 */
public class Data {

    @SerializedName(value = "type")
    private String type;

    @SerializedName(value = "id")
    private String id;

    @SerializedName(value = "attributes")
    private Object attr;

    @SerializedName(value = "relationships")
    private Object rels;

    @SerializedName(value = "links")
    private Object links;

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

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
     * @return the attr
     */
    public Object getAttr() {
        return attr;
    }

    /**
     * @param attr the attr to set
     */
    public void setAttr(Object attr) {
        this.attr = attr;
    }

    /**
     * @return the rels
     */
    public Object getRels() {
        return rels;
    }

    /**
     * @param rels the rels to set
     */
    public void setRels(Object rels) {
        this.rels = rels;
    }

    /**
     * @return the links
     */
    public Object getLinks() {
        return links;
    }

    /**
     * @param links the links to set
     */
    public void setLinks(Object links) {
        this.links = links;
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
