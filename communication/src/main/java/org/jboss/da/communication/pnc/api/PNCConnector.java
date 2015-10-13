package org.jboss.da.communication.pnc.api;

import org.jboss.da.communication.pnc.model.BuildConfiguration;
import org.jboss.da.communication.pnc.model.BuildConfigurationCreate;
import org.jboss.da.communication.pnc.model.BuildConfigurationSet;
import org.jboss.da.communication.pnc.model.Product;
import org.jboss.da.communication.pnc.model.ProductVersion;
import java.util.List;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public interface PNCConnector {

    List<BuildConfiguration> getBuildConfigurations() throws Exception;

    /**
     * Gets all BuildConfigurations from PNC with the specific SCM URL and SCM Revision
     * 
     * @param scmUrl SCM URL expected in BC
     * @param scmRevision SCM revision expected in BC
     * @return List of BCs with specified SCM URL and SCM revision or empty list if no BC was found
     * @throws Exception Thrown if communication with PNC failed
     */
    List<BuildConfiguration> getBuildConfigurations(String scmUrl, String scmRevision)
            throws Exception;

    BuildConfiguration createBuildConfiguration(BuildConfigurationCreate bc) throws Exception;

    /**
     * Deletes BuildConfiguration from PNC by ID of the BC
     * 
     * @param bc BC to be deleted
     * @return True, if the BC was successfully removed, otherwise false
     * @throws Exception Thrown if the communication with PNC failed
     */
    boolean deleteBuildConfiguration(BuildConfiguration bc) throws Exception;

    /**
     * Deletes BuildConfiguration from PNC by ID of the BC
     * 
     * @param bcId ID of BC to be deleted
     * @return True, if the BC was successfully removed, otherwise false
     * @throws Exception Thrown if the communication with PNC failed
     */
    boolean deleteBuildConfiguration(int bcId) throws Exception;

    BuildConfigurationSet createBuildConfigurationSet(BuildConfigurationSet bcs) throws Exception;

    /**
     * Finds BuildConfigurationSet with specific product version id and build configurations ids from pnc
     * 
     * @param productVersionId
     * @param buildConfigurationIds
     * @return BuildConfigurationSet or null if it is not found
     */
    BuildConfigurationSet findBuildConfigurationSet(int productVersionId,
            List<Integer> buildConfigurationIds);

    Product createProduct(Product p) throws Exception;

    ProductVersion createProductVersion(ProductVersion pv) throws Exception;
}
