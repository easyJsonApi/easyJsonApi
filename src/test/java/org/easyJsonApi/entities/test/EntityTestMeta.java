package org.easyJsonApi.entities.test;

import org.easyJsonApi.annotations.Meta;

@Meta
public class EntityTestMeta {

    private String metadata;

    /**
     * @return the metadata
     */
    public String getMetadata() {
        return metadata;
    }

    /**
     * @param metadata the metadata to set
     */
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

}
