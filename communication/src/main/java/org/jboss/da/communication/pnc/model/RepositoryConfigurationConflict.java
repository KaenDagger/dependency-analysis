package org.jboss.da.communication.pnc.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 *
 * @author Honza Brázdil &lt;jbrazdil@redhat.com&gt;
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryConfigurationConflict {

    private final int repositoryConfigurationId;

    @JsonCreator
    public RepositoryConfigurationConflict(
            @JsonProperty("repositoryConfigurationId") int repositoryConfigurationId) {
        this.repositoryConfigurationId = repositoryConfigurationId;
    }

}
