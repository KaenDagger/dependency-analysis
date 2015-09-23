package org.jboss.da.reports.impl;

import org.apache.maven.scm.ScmException;
import org.jboss.da.communication.aprox.api.AproxConnector;
import org.jboss.da.communication.aprox.model.GAVDependencyTree;
import org.jboss.da.communication.CommunicationException;
import org.jboss.da.communication.model.GAV;
import org.jboss.da.communication.pom.PomAnalysisException;
import org.jboss.da.listings.api.service.BlackArtifactService;
import org.jboss.da.listings.api.service.WhiteArtifactService;
import org.jboss.da.reports.api.ArtifactReport;
import org.jboss.da.reports.api.Product;
import org.jboss.da.reports.api.ReportsGenerator;
import org.jboss.da.reports.api.SCMLocator;
import org.jboss.da.reports.api.VersionLookupResult;
import org.jboss.da.reports.backend.api.VersionFinder;
import org.slf4j.Logger;

import javax.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.jboss.da.reports.backend.api.DependencyTreeGenerator;

/**
 * The implementation of reports, which provides information about
 * built/not built artifacts/blacklisted artifacts
 * 
 * @author Jakub Bartecek <jbartece@redhat.com>
 * @author Honza Brázdil <jbrazdil@redhat.com>
 *
 */
public class ReportsGeneratorImpl implements ReportsGenerator {

    @Inject
    private Logger log;

    @Inject
    private VersionFinder versionFinderImpl;

    @Inject
    private BlackArtifactService blackArtifactService;

    @Inject
    private WhiteArtifactService whiteArtifactService;

    @Inject
    private DependencyTreeGenerator dependencyTreeGenerator;

    @Override
    public Optional<ArtifactReport> getReportFromSCM(SCMLocator scml) throws ScmException,
            PomAnalysisException, CommunicationException {
        if (scml == null)
            throw new IllegalArgumentException("SCM information can't be null");

        Optional<GAVDependencyTree> dt = dependencyTreeGenerator.getDependencyTree(scml);

        if (!dt.isPresent())
            return Optional.empty();

        VersionLookupResult result = versionFinderImpl.lookupBuiltVersions(dt.get().getGav());
        ArtifactReport report = toArtifactReport(dt.get().getGav(), result);

        addDependencyReports(report, dt.get().getDependencies());

        return Optional.of(report);
    }

    @Override
    public Optional<ArtifactReport> getReport(GAV gav, List<Product> products) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<ArtifactReport> getReport(GAV gav) throws CommunicationException {
        if (gav == null)
            throw new IllegalArgumentException("GAV can't be null");

        Optional<GAVDependencyTree> dt = dependencyTreeGenerator.getDependencyTree(gav);
        if (!dt.isPresent())
            return Optional.empty();

        VersionLookupResult result = versionFinderImpl.lookupBuiltVersions(gav);
        ArtifactReport report = toArtifactReport(gav, result);

        addDependencyReports(report, dt.get().getDependencies());

        return Optional.of(report);
    }

    private ArtifactReport toArtifactReport(GAV gav, VersionLookupResult result) {
        ArtifactReport report = new ArtifactReport(gav);
        report.addAvailableVersions(result.getAvailableVersions());
        report.setBestMatchVersion(result.getBestMatchVersion());
        report.setBlacklisted(blackArtifactService.isArtifactPresent(gav));
        report.setWhiteListed(whiteArtifactService.isArtifactPresent(gav));
        return report;
    }

    private void addDependencyReports(ArtifactReport ar, Set<GAVDependencyTree> dependencyTree)
            throws CommunicationException {
        for (GAVDependencyTree dt : dependencyTree) {
            VersionLookupResult result = versionFinderImpl.lookupBuiltVersions(dt.getGav());

            ArtifactReport dar = toArtifactReport(dt.getGav(), result);
            addDependencyReports(dar, dt.getDependencies());
            ar.addDependency(dar);
        }
    }

}
