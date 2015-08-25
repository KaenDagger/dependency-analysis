package org.jboss.da.rest.reports.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
@XmlRootElement(name = "report")
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
public class Report {

    @Getter
    @NonNull
    private final String groupId;

    @Getter
    @NonNull
    private final String artifactId;

    @Getter
    @NonNull
    private final String version;

    @Getter
    @NonNull
    private final List<String> availableVersions;

    @Getter
    private final String bestMatchVersion;

    @Getter
    private final boolean dependencyVersionsSatisfied;

    @Getter
    @NonNull
    private final List<Report> dependencies;

    @Getter
    private final boolean blacklisted;

    @Getter
    private final boolean whitelisted;

    @Getter
    private final int notBuiltDependencies;
}
