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
package com.github.easyjsonapi.entities.test;

import java.math.BigDecimal;

import com.github.easyjsonapi.annotations.Attributes;

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
     * @return the attr2
     */
    public BigDecimal getAttr2() {
        return attr2;
    }

    /**
     * @return the attr3
     */
    public EntityDependencyTest getAttr3() {
        return attr3;
    }

    /**
     * @param attr1 the attr1 to set
     */
    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    /**
     * @param attr2 the attr2 to set
     */
    public void setAttr2(BigDecimal attr2) {
        this.attr2 = attr2;
    }

    /**
     * @param attr3 the attr3 to set
     */
    public void setAttr3(EntityDependencyTest attr3) {
        this.attr3 = attr3;
    }

}
