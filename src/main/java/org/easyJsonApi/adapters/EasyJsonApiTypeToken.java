package org.easyJsonApi.adapters;

/**
 * Enum allows mapping type token
 * 
 * @author nbento.neves@gmail.com
 */
public enum EasyJsonApiTypeToken {

    TOKEN_ATTR("TOKEN_ATTR"), TOKEN_RELS("TOKEN_RELS"), TOKEN_LINKS("TOKEN_LINKS"), TOKEN_META("TOKEN_META");

    private String key;

    private EasyJsonApiTypeToken(String key) {
        this.key = key;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

}
