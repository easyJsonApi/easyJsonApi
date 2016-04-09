package org.easyJsonApi.entities.test;

import java.util.HashMap;
import java.util.Map;

public class EntityDependencyTest {

    private Map<String, String> attr;

    /**
     * @return the attr
     */
    public Map<String, String> getAttr() {
        if (attr == null) {
            attr = new HashMap<>();
        }
        return attr;
    }

    /**
     * @param attr the attr to set
     */
    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }

}
