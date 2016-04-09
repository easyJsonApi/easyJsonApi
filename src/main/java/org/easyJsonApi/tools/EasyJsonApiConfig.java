package org.easyJsonApi.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easyJsonApi.adapters.EasyJsonApiTypeToken;
import org.easyJsonApi.annotations.Attributes;
import org.easyJsonApi.asserts.Assert;
import org.easyJsonApi.exceptions.EasyJsonApiInvalidPackageException;

import com.google.common.reflect.ClassPath;

/**
 * The EasyJsonApiConfig it is necessary to configure the {@link EasyJsonApi}.
 * This class allows to parsing the packages defined into the
 * {@link EasyJsonApiConfig#packagesSearched} inside the class
 * 
 * @author Nuno Bento
 * @version %I%, %G%
 */
public class EasyJsonApiConfig {

    private String[] packagesSearched;

    private Map<EasyJsonApiTypeToken, List<Class<?>>> classesParsed = new HashMap<>();

    /**
     * The default constructor
     */
    public EasyJsonApiConfig() {}

    /**
     * The constructor with packages attributes
     * 
     * @param packages the packages needs to be search and parsing
     * @throws EasyJsonApiInvalidPackageException If packages searched be
     *             invalid
     */
    public EasyJsonApiConfig(String... packages) throws EasyJsonApiInvalidPackageException {
        setPackagesToSearch(packages);
    }

    /**
     * Set the packages to search
     * 
     * @param packages the packages needs to be search and parsing
     * @throws EasyJsonApiInvalidPackageException If packages searched be
     *             invalid
     */
    public void setPackagesToSearch(String... packages) throws EasyJsonApiInvalidPackageException {

        this.packagesSearched = packages;

        for (String packageToSearch : packages) {
            try {
                ClassPath classpath = ClassPath.from(getClass().getClassLoader());

                List<Class<?>> attrClasses = new ArrayList<>();
                List<Class<?>> relsClasses = new ArrayList<>();
                List<Class<?>> linksClasses = new ArrayList<>();

                for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClasses(packageToSearch)) {

                    Class<?> clazz = classInfo.load();

                    if (Assert.notNull(clazz.getAnnotation(Attributes.class))) {
                        attrClasses.add(clazz);
                    }
                    // TODO: Make the users annotations
                }

                this.classesParsed.put(EasyJsonApiTypeToken.TOKEN_ATTR, attrClasses);
                this.classesParsed.put(EasyJsonApiTypeToken.TOKEN_RELS, relsClasses);
                this.classesParsed.put(EasyJsonApiTypeToken.TOKEN_LINKS, linksClasses);

            } catch (IOException ex) {
                throw new EasyJsonApiInvalidPackageException("Invalid packages inserted!", ex);
            }
        }
    }

    /**
     * Get the array with packages searched
     * 
     * @return the packagesSearched the packages searched
     */
    public String[] getPackagesSearched() {
        return packagesSearched;
    }

    /**
     * Get the array with all classes parsed
     * 
     * @return the classesParsed the classes parsed
     */
    public Map<EasyJsonApiTypeToken, List<Class<?>>> getClassesParsed() {
        return classesParsed;
    }

}
