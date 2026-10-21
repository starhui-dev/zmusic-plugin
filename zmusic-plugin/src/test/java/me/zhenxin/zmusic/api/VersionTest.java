package me.zhenxin.zmusic.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VersionTest {

    @Test
    void treatsCalverServerAsNewerThanLegacyMinecraftVersions() {
        Version version = version("26.1.2-R0.1-SNAPSHOT");

        assertEquals("26.1.2", version.getVersion());
        assertFalse(version.isLowerThan("1.8"));
        assertFalse(version.isLowerThan("1.9"));
        assertFalse(version.isLowerThan("1.12"));
        assertTrue(version.isHigherThan("1.12"));
    }

    @Test
    void keepsLegacyVersionComparisonBehavior() {
        Version version = version("1.7.10-R0.1-SNAPSHOT");

        assertTrue(version.isLowerThan("1.8"));
        assertFalse(version.isHigherThan("1.8"));
        assertTrue(version.isEquals("1.7.10"));
    }

    @Test
    void comparesMissingSegmentsAsZero() {
        Version version = version("1.9-R0.1-SNAPSHOT");

        assertFalse(version.isLowerThan("1.9.0"));
        assertFalse(version.isHigherThan("1.9.0"));
    }

    @Test
    void parsesPaperBuildVersion() {
        Version version = version("26.3.build.209-stable");

        assertEquals("26.3", version.getVersion());
        assertTrue(version.isEquals("26.3"));
        assertFalse(version.isLowerThan("26.3"));
        assertTrue(version.isHigherThan("1.21.11"));
        assertEquals("26.3", version("26.3.local-SNAPSHOT").getVersion());
    }

    @Test
    void comparesSegmentsNumerically() {
        Version version = version("1.21.10-R0.1-SNAPSHOT");

        assertTrue(version.isHigherThan("1.21.8"));
        assertFalse(version.isLowerThan("1.21.8"));
    }

    @Test
    void ignoresPreReleaseSuffix() {
        assertEquals("1.21.9", version("1.21.9-pre2-R0.1-SNAPSHOT").getVersion());
    }

    private static Version version(String rawVersion) {
        return new Version() {
            @Override
            public String getRawVersion() {
                return rawVersion;
            }
        };
    }
}
