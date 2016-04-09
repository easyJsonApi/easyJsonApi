package org.easyJsonApi.entities.test;

import java.math.BigDecimal;

import org.easyJsonApi.annotations.Attributes;

@Attributes
public class EntityTestAttr1 {

    private String attr1;

    private BigDecimal attr2;

    private EntityDependencyTest attr3;

    /**
     * @return the attr1
     */
    public String getAttr1() {
        return attr1;
    }

    /**
     * @param attr1 the attr1 to set
     */
    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    /**
     * @return the attr2
     */
    public BigDecimal getAttr2() {
        return attr2;
    }

    /**
     * @param attr2 the attr2 to set
     */
    public void setAttr2(BigDecimal attr2) {
        this.attr2 = attr2;
    }

    /**
     * @return the attr3
     */
    public EntityDependencyTest getAttr3() {
        return attr3;
    }

    /**
     * @param attr3 the attr3 to set
     */
    public void setAttr3(EntityDependencyTest attr3) {
        this.attr3 = attr3;
    }

}
