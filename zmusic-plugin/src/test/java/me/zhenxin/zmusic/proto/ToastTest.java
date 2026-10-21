package me.zhenxin.zmusic.proto;

import me.zhenxin.zmusic.api.Version;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToastTest {

    @Test
    void selectsPaperImplementationByMinecraftVersion() {
        assertEquals("1_21_4", Toast.paperImplVersion(version("1.20.5-R0.1-SNAPSHOT")));
        assertEquals("1_21_4", Toast.paperImplVersion(version("1.21.4-R0.1-SNAPSHOT")));
        assertEquals("1_21_4", Toast.paperImplVersion(version("1.21.5-R0.1-SNAPSHOT")));
        assertEquals("1_21_7", Toast.paperImplVersion(version("1.21.6-R0.1-SNAPSHOT")));
        assertEquals("1_21_7", Toast.paperImplVersion(version("1.21.7-R0.1-SNAPSHOT")));
        assertEquals("1_21_11", Toast.paperImplVersion(version("1.21.8-R0.1-SNAPSHOT")));
        assertEquals("1_21_11", Toast.paperImplVersion(version("1.21.10-R0.1-SNAPSHOT")));
        assertEquals("1_21_11", Toast.paperImplVersion(version("1.21.11-R0.1-SNAPSHOT")));
        assertEquals("26_1_2", Toast.paperImplVersion(version("26.1.1.build.29-alpha")));
        assertEquals("26_1_2", Toast.paperImplVersion(version("26.1.2.build.74-stable")));
        assertEquals("26_1_2", Toast.paperImplVersion(version("26.2.build.134-stable")));
        assertEquals("26_3", Toast.paperImplVersion(version("26.3.build.209-stable")));
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
