package org.easyJsonApi.adapters;

import org.easyJsonApi.tools.EasyJsonApi;
import org.easyJsonApi.tools.EasyJsonApiConfig;

/**
 * Interface with contact for {@link EasyJsonApi}
 * 
 * @author nbento.neves@gmail.com
 */
public interface TypeControl {

    public void setClassesUsed(Class<?>... clazz);

    public void setConfig(EasyJsonApiConfig config);

}
